package com.bluemobi.serviceimpl.bts;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appcore.dao.MyBatisBaseDao;
import com.appcore.service.impl.MybatisBaseServiceImpl;
import com.bluemobi.constant.OrderConstant;
import com.bluemobi.dao.bts.BtsOrderDao;
import com.bluemobi.po.bts.BtsCart;
import com.bluemobi.po.bts.BtsConsignee;
import com.bluemobi.po.bts.BtsOrder;
import com.bluemobi.po.bts.BtsOrderItem;
import com.bluemobi.po.bts.BtsOrderLog;
import com.bluemobi.po.cas.CasUser;
import com.bluemobi.po.goods.GoodsContent;
import com.bluemobi.po.goods.GoodsContentSku;
import com.bluemobi.po.groupon.GrouponBulk;
import com.bluemobi.po.groupon.GrouponBulkLog;
import com.bluemobi.po.groupon.GrouponGrab;
import com.bluemobi.po.trend.TrendRegion;
import com.bluemobi.service.bts.BtsCartService;
import com.bluemobi.service.bts.BtsConsigneeService;
import com.bluemobi.service.bts.BtsOrderItemService;
import com.bluemobi.service.bts.BtsOrderLogService;
import com.bluemobi.service.bts.BtsOrderService;
import com.bluemobi.service.cas.CasUserService;
import com.bluemobi.service.goods.GoodsContentService;
import com.bluemobi.service.goods.GoodsContentSkuService;
import com.bluemobi.service.groupon.GrouponBulkLogService;
import com.bluemobi.service.groupon.GrouponBulkService;
import com.bluemobi.service.groupon.GrouponGrabService;
import com.bluemobi.service.trend.TrendRegionService;
import com.bluemobi.to.ResultTO;
import com.bluemobi.to.bts.AbstractOrderRequestTO;
import com.bluemobi.to.bts.CartOrderRequestTO;
import com.bluemobi.to.bts.OrderDetailTO;
import com.bluemobi.to.bts.SkuOrderRequestTO;
import com.bluemobi.to.goods.GoodsContentAndSkuTO;

/**
 * 【订单表】 服务类 实现类
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2015-11-17 13:27:04
 * 
 */
@Service(value = "btsOrderService")
public class BtsOrderServiceImpl extends MybatisBaseServiceImpl implements
		BtsOrderService {

	private static final Object lock = new Object();

	@Autowired
	private BtsOrderDao btsOrderDao;
	@Autowired
	private BtsConsigneeService btsConsigneeService;
	@Autowired
	private GoodsContentSkuService goodsContentSkuService;
	@Autowired
	private GrouponBulkService grouponBulkService;
	@Autowired
	private GrouponGrabService grouponGrabService;
	@Autowired
	private GrouponBulkLogService grouponBulkLogService;
	@Autowired
	private CasUserService casUserService;
	@Autowired
	private TrendRegionService trendRegionService;
	@Autowired
	private BtsOrderItemService btsOrderItemService;
	@Autowired
	private GoodsContentService goodsContentService;
	@Autowired
	private BtsCartService btsCartService;
	@Autowired
	private BtsOrderLogService btsOrderLogService;

	@Override
	public MyBatisBaseDao getDao() {
		return btsOrderDao;
	}

	@Override
	public ResultTO createOrderFromGoodsContent(int userId,
			SkuOrderRequestTO skuOrderReq) {
		synchronized (lock) {
			// 1. 参数为空校验
			if (skuOrderReq.getSkuId() == null || skuOrderReq.getSkuId() == 0) {
				return ResultTO.newFailResultTO("商品编号不正确", null);
			}
			if (skuOrderReq.getQuantity() == null
					|| skuOrderReq.getQuantity() == 0) {
				return ResultTO.newFailResultTO("商品购买数量不正确", null);
			}
			if (skuOrderReq.getOrderType() == null
					|| skuOrderReq.getOrderType() < 0
					|| skuOrderReq.getOrderType() > 2) {
				return ResultTO.newFailResultTO("非法订单类型", null);
			}
			if (skuOrderReq.getPaymentId() == null
					|| !OrderConstant.paymentMap.keySet().contains(
							skuOrderReq.getPaymentId())) {
				return ResultTO.newFailResultTO("不支持的支付方式", null);
			}

			// 2. 校验商品信息
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("skuId", skuOrderReq.getSkuId());
			GoodsContentSku sku = goodsContentSkuService
					.selectObject(parameter);
			if (sku == null) {
				return ResultTO.newFailResultTO("商品不存在", null);
			}
			if (sku.getStock() < skuOrderReq.getQuantity()) {
				return ResultTO.newFailResultTO("商品库存不足", null);
			}
			// 3. 检查收货人信息
			BtsConsignee consignee = btsConsigneeService.getConsigneeForOrder(
					userId, skuOrderReq.getConsigneeId());
			if (consignee == null) {
				return ResultTO.newFailResultTO("收货人信息验证失败", null);
			}

			// 4. 判断订单类型
			Map<GoodsContentSku, Integer> skuAndQuantityMap = new HashMap<GoodsContentSku, Integer>();
			skuAndQuantityMap.put(sku, skuOrderReq.getQuantity());
			boolean isSuccess = false;
			OrderDetailTO order;
			if (OrderConstant.GROUPONBULK_ORDER_TYPE == skuOrderReq
					.getOrderType()) {// 团购订单
				GrouponBulk bulk = grouponBulkService.searchBySku(sku);
				if (bulk == null) {
					return ResultTO.newFailResultTO("没有该商品对应的团购活动", null);
				}
				if (bulk.getNumber() > skuOrderReq.getQuantity()) {
					return ResultTO.newFailResultTO(
							"团购活动起团数量为" + bulk.getNumber(), null);
				}
				parameter.clear();
				parameter.put("bulkId", bulk.getBulkId());
				List<GrouponBulkLog> logList = grouponBulkLogService
						.selectObjectList(parameter);
				if (bulk.getInventorySum() == logList.size()) {
					return ResultTO.newFailResultTO(
							"团购活动数量已满" + bulk.getNumber(), null);
				}
				sku.setPrice(new BigDecimal(bulk.getPrice()));
				order = createOrder(userId, skuAndQuantityMap, skuOrderReq,
						consignee);
				if (order != null) {
					isSuccess = createGouponBulkLog(userId, bulk,
							skuOrderReq.getQuantity());
				}

			} else if (OrderConstant.GROUPONGRAB_ORDER_TYPE == skuOrderReq
					.getOrderType()) {// 抢购订单
				GrouponGrab grab = grouponGrabService.searchBySku(sku);
				if (grab == null) {
					return ResultTO.newFailResultTO("没有该商品对应的抢购活动", null);
				}
				parameter.clear();
				parameter.put("bulkId", grab.getBulkId());
				List<GrouponBulkLog> logList = grouponBulkLogService
						.selectObjectList(parameter);
				if (grab.getInventorySum() == logList.size()) {
					return ResultTO.newFailResultTO("抢购活动数量已满", null);
				}
				sku.setPrice(new BigDecimal(grab.getPrice()));
				order = createOrder(userId, skuAndQuantityMap, skuOrderReq,
						consignee);
				if (order != null) {
					isSuccess = createGouponGrabLog(userId, grab,
							skuOrderReq.getQuantity());
				}
			} else {// 普通订单
				order = createOrder(userId, skuAndQuantityMap, skuOrderReq,
						consignee);
			}
			if (isSuccess) {
				return ResultTO.newSuccessResultTO("操作成功", order);
			} else {
				return ResultTO.newFailResultTO("系统异常,请联系管理员", null);
			}
		}
	}

	private boolean createGouponGrabLog(int userId, GrouponGrab grab,
			int quantity) {
		GrouponBulkLog grouponLog = new GrouponBulkLog();
		grouponLog.setCtime(Calendar.getInstance().getTime());
		grouponLog.setGoodsName(grab.getGoodsName());
		grouponLog.setSku(grab.getSku());
		grouponLog.setNumber(quantity);
		grouponLog.setPrice(grab.getPrice());
		grouponLog.setUserid(userId);
		return grouponBulkLogService.insert(grouponLog) == 1;
	}

	private boolean createGouponBulkLog(int userId, GrouponBulk bulk,
			int quantity) {
		GrouponBulkLog grouponLog = new GrouponBulkLog();
		grouponLog.setCtime(Calendar.getInstance().getTime());
		grouponLog.setGoodsName(bulk.getGoodsName());
		grouponLog.setSku(bulk.getSku());
		grouponLog.setNumber(quantity);
		grouponLog.setPrice(bulk.getPrice());
		grouponLog.setUserid(userId);
		return grouponBulkLogService.insert(grouponLog) == 1;
	}

	private OrderDetailTO createOrder(int userId,
			Map<GoodsContentSku, Integer> skuAndQuantityMap,
			AbstractOrderRequestTO orderReq, BtsConsignee consignee) {

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("userid", userId);
		CasUser user = casUserService.selectObject(parameter);
		parameter.clear();
		parameter.put("regionId", consignee.getRegionId());
		TrendRegion region = trendRegionService.selectObject(parameter);
		BtsOrder order = new BtsOrder();
		order.setPaymentId(orderReq.getPaymentId().shortValue());
		order.setPayStatus(OrderConstant.PAY_WAITED_STATUS);
		order.setPayType(OrderConstant.paymentMap.get(orderReq.getPaymentId()));
		order.setOrderType(orderReq.getOrderType().byteValue());
		order.setUserid(userId);
		order.setUsername(user.getUsername());
		order.setConsigneeAddress(consignee.getAddress());
		order.setConsigneeEmail(consignee.getEmail());
		order.setConsigneeMobile(consignee.getMobile());
		order.setConsigneeName(consignee.getName());
		order.setConsigneeRegionId(consignee.getRegionId());
		order.setConsigneeRegionName(region.getRegionName());
		order.setConsigneeZipcode(consignee.getZipcode());
		order.setCount(calculateTotalCount(skuAndQuantityMap.values()));
		order.setCtime(Calendar.getInstance().getTime());
		order.setTotalAmount(calculateTotalAmount(skuAndQuantityMap));
		order.setOrderNumber(generateOrderNumber());
		if (order.getPayType() == OrderConstant.ONLINE_PAYMENT_TYPE) {
			order.setStatus(OrderConstant.WAIT_PAY_ORDER_STATUS);
		} else {
			order.setStatus(OrderConstant.WAIT_DELIVERY_ORDER_STATUS);
		}

		int ret = this.btsOrderDao.insert(order);
		OrderDetailTO orderDetail = new OrderDetailTO();
		if (ret == 1) {
			List<BtsOrderItem> itemList = new ArrayList<BtsOrderItem>(
					skuAndQuantityMap.size());
			BtsOrderItem item;
			for (Map.Entry<GoodsContentSku, Integer> entry : skuAndQuantityMap
					.entrySet()) {
				item = new BtsOrderItem();
				item.setOrderId(order.getOrderId());
				item.setContentId(entry.getKey().getSkuId().intValue());
				item.setSku(entry.getKey().getSku());
				parameter.clear();
				parameter.put("contentId", entry.getKey().getContentId());
				GoodsContent goodsContent = goodsContentService
						.selectObject(parameter);
				item.setGoodsName(goodsContent.getName());
				item.setGoodsLength(entry.getKey().getLength());
				item.setGoodsHeight(entry.getKey().getHeight());
				item.setGoodsWeight(entry.getKey().getWeight());
				item.setGoodsWide(entry.getKey().getWide());
				item.setBuyNum(entry.getValue().shortValue());
				item.setBuyPrice(entry.getKey().getPrice());
				item.setCtime(Calendar.getInstance().getTime());
				parameter.clear();
				parameter.put("contentId", entry.getKey().getSkuId());
				List<GoodsContentAndSkuTO> skuList = goodsContentService
						.selectAllContentAndSku(parameter);
				item.setDescription(JSONObject.fromObject(skuList.get(0))
						.toString());
				item.setGoodsImage("");
				ret = btsOrderItemService.insert(item);
				if (ret == 1) {
					itemList.add(item);

				}
			}

			BtsOrderLog orderLog = new BtsOrderLog();
			orderLog.setAdminUserid(userId);
			orderLog.setContent("userid为" + userId + "的用户提交了订单号为"
					+ order.getOrderNumber() + "的订单");
			orderLog.setCtime(Calendar.getInstance().getTime());
			orderLog.setIp(orderReq.getIp());
			orderLog.setOrderId(order.getOrderId().intValue());
			orderLog.setOrderNumber(order.getOrderNumber());
			orderLog.setType((byte) 1);
			ret = btsOrderLogService.insert(orderLog);
			try {
				BeanUtils.copyProperties(orderDetail, order);
				orderDetail.setItems(itemList);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return ret == 1 ? orderDetail : null;
	}

	private BigDecimal calculateTotalAmount(
			Map<GoodsContentSku, Integer> skuAndQuantityMap) {
		BigDecimal total = new BigDecimal(0);
		for (Map.Entry<GoodsContentSku, Integer> entry : skuAndQuantityMap
				.entrySet()) {
			total.add(entry.getKey().getPrice()
					.multiply(new BigDecimal(entry.getValue())));
		}
		return total;
	}

	private Integer calculateTotalCount(Collection<Integer> quantities) {
		int total = 0;
		for (Integer quantity : quantities) {
			total += quantity;
		}
		return total;
	}

	@Override
	public ResultTO createOrderFromCart(int userId,
			CartOrderRequestTO cartOrderReq) {
		synchronized (lock) {
			// 1. 参数为空校验
			if (cartOrderReq.getCartList() == null
					|| cartOrderReq.getCartList().length == 0) {
				return ResultTO.newFailResultTO("购物车商品数量不正确", null);
			}
			if (cartOrderReq.getPaymentId() == null
					|| !OrderConstant.paymentMap.keySet().contains(
							cartOrderReq.getPaymentId())) {
				return ResultTO.newFailResultTO("不支持的支付方式", null);
			}

			// 2. 校验购物车商品信息
			List<BtsCart> cartList = btsCartService
					.selectSkuFromCart(cartOrderReq.getCartList());
			if (cartList == null || cartList.size() == 0) {
				return ResultTO.newFailResultTO("购物车不存在", null);
			}

			List<GoodsContentSku> skuList = goodsContentSkuService
					.selectSkuFromCart(cartList);
			if (skuList == null || skuList.size() == 0) {
				return ResultTO.newFailResultTO("购物车中的商品不存在", null);
			}

			for (int i = 0; i < cartList.size(); i++) {
				if (skuList.get(i) == null) {
					return ResultTO.newFailResultTO("商品不存在", null);
				}
				if (cartList.get(i).getQuantity() > skuList.get(i).getStock()) {
					return ResultTO.newFailResultTO("商品库存不足", null);
				}

			}

			// 3. 检查收货人信息
			BtsConsignee consignee = btsConsigneeService.getConsigneeForOrder(
					userId, cartOrderReq.getConsigneeId());
			if (consignee == null) {
				return ResultTO.newFailResultTO("收货人信息验证失败", null);
			}

			// 4. 创建订单
			Map<GoodsContentSku, Integer> skuAndQuantityMap = new HashMap<GoodsContentSku, Integer>();
			for (int i = 0; i < skuList.size(); i++) {
				skuAndQuantityMap.put(skuList.get(i), cartList.get(i)
						.getQuantity().intValue());
			}

			OrderDetailTO order = createOrder(userId, skuAndQuantityMap,
					cartOrderReq, consignee);

			// 5. 生成返回结果
			if (order != null) {
				// 删除购物车
				StringBuffer cartIds = new StringBuffer();
				for (BtsCart cart : cartList) {
					cartIds.append(cart.getCartId());
					cartIds.append(",");
				}
				cartIds.deleteCharAt(cartIds.length() - 1);
				btsCartService.deleteBtsCartByIds(userId, cartIds.toString());

				return ResultTO.newSuccessResultTO("操作成功", order);
			} else {
				return ResultTO.newFailResultTO("系统异常,请联系管理员", null);
			}
		}

	}

	private String generateOrderNumber() {
		Random r = new Random();
		StringBuffer orderNumber = new StringBuffer();
		orderNumber.append(System.currentTimeMillis());
		for (int i = 0; i < 3; i++) {
			orderNumber.append(r.nextInt(10) + 1);
		}
		return orderNumber.toString();
	}

	@Override
	public ResultTO deleteOrderForUser(Long orderId) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("orderId", orderId);
		parameter.put("isDel", 1);
		int ret = this.btsOrderDao.update(parameter);
		if (ret == 1) {
			return ResultTO.newSuccessResultTO("操作成功", null);
		} else {
			return ResultTO.newFailResultTO("系统异常,请联系管理员", null);
		}
	}

	@Override
	public ResultTO cancelOrderForUser(Long orderId) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("orderId", orderId);
		BtsOrder order = this.btsOrderDao.selectObject(parameter);
		if (checkOrderStatus(order)) {
			parameter.put("isCancel", 1);
			int ret = this.btsOrderDao.update(parameter);
			if (ret == 1) {
				return ResultTO.newSuccessResultTO("操作成功", null);
			} else {
				return ResultTO.newFailResultTO("系统异常,请联系管理员", null);
			}
		} else {
			return ResultTO.newFailResultTO("该订单不能取消", null);
		}
	}

	private boolean checkOrderStatus(BtsOrder order) {
		if (order.getPayType() == OrderConstant.OFFLINE_PAYMENT_TYPE) {
			if (order.getStatus() <= OrderConstant.WAIT_DELIVERY_ORDER_STATUS) {
				return true;
			}
		} else {
			if (order.getStatus() <= OrderConstant.WAIT_PAY_ORDER_STATUS) {
				return true;
			}
		}
		return false;
	}

	@Override
	public ResultTO confirmOrderForUser(Long orderId) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("orderId", orderId);
		BtsOrder order = this.btsOrderDao.selectObject(parameter);
		if (order.getStatus() == OrderConstant.DELIVERIED_ORDER_STATUS) {
			parameter.put("status", OrderConstant.SIGNED_ORDER_STATUS);
			int ret = this.btsOrderDao.update(parameter);
			if (ret == 1) {
				return ResultTO.newSuccessResultTO("操作成功", null);
			} else {
				return ResultTO.newFailResultTO("系统异常,请联系管理员", null);
			}
		} else {
			return ResultTO.newFailResultTO("订单状态异常", null);
		}
	}

}

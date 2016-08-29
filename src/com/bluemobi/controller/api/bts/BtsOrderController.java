package com.bluemobi.controller.api.bts;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.appcore.page.Page;
import com.bluemobi.controller.AbstractAPIController;
import com.bluemobi.service.bts.BtsOrderService;
import com.bluemobi.to.ResultTO;
import com.bluemobi.to.bts.CartOrderRequestTO;
import com.bluemobi.to.bts.SkuOrderRequestTO;

/**
 * 用户订单控制器 -- API接口
 * 
 * @ClassName OrderController
 * @author liuyt
 * @date 2015-11-17 下午1:43:35
 * @version
 */
@Controller
@RequestMapping("api/order")
public class BtsOrderController extends AbstractAPIController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(BtsOrderController.class);

    @Autowired
    private BtsOrderService btsOrderService;

    /**
     * 商品立即购买
     * 
     * @author liuyt
     * @date 2015-11-17 下午3:01:02
     * @param skuId
     *            skuId
     * @param quantity
     *            购买数量
     * @param consigneeId
     *            送货地址ID(为空的话,则用户的默认收货地址)
     * @param orderType
     *            订单类型(0:普通订单 1:团购订单 2:抢购订单)
     * @return
     * @version
     */
    @RequestMapping(value = "quickPurchase", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO createOrderForQuickPurchase(SkuOrderRequestTO skuOrderReq) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(
                    "进入商品立即购买接口, skuId【{}】购买数量【{}】收货人id【{}】订单类型【{}】",
                    new Object[] { skuOrderReq.getSkuId(),
                            skuOrderReq.getQuantity(),
                            skuOrderReq.getConsigneeId(),
                            skuOrderReq.getOrderType() });
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        skuOrderReq.setIp(request.getRemoteAddr());
        return btsOrderService.createOrderFromGoodsContent(getUserid(),
                skuOrderReq);
    }

    /**
     * 购物车购买
     * 
     * @author liuyt
     * @date 2015-11-17 下午3:21:24
     * @param cartIds
     *            购物车ID列表
     * @param consigneeId
     *            送货地址ID(为空的话,则用户的默认收货地址)
     * @return
     * @version
     */
    @RequestMapping(value = "cartPurchase", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO createOrderForCart(CartOrderRequestTO cartOrderReq) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("进入购物车购买接口, 购物车ID列表【{}】送货地址【{}】", new Object[] {
                    cartOrderReq.getCartList(), cartOrderReq.getConsigneeId() });
        }
        return btsOrderService.createOrderFromCart(getUserid(), cartOrderReq);
    }

    /**
     * 用户删除订单
     * @author liuyt
     * @date 2015-11-19 下午4:04:35
     * @param orderId
     * @return
     * @version
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO deleteOrder(Long orderId) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("进入订单删除接口, 订单id【{}】", new Object[] { orderId });
        }
        return btsOrderService.deleteOrderForUser(orderId);
    }
    
    /**
     * 用户取消订单
     * @author liuyt
     * @date 2015-11-19 下午4:04:35
     * @param orderId
     * @return
     * @version
     */
    @RequestMapping(value = "cancel", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO cancelOrder(Long orderId) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("进入订单取消接口, 订单id【{}】", new Object[] { orderId });
        }
        return btsOrderService.cancelOrderForUser(orderId);
    }
    
    /**
     * 分页获取订单列表
     * @author liuyt
     * @date 2015-11-19 下午4:07:44
     * @param status
     * @param pageSize
     * @param pageIndex
     * @return
     * @version
     */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getPage(Integer status, int pageSize, int pageIndex) {
        Map<String, Object> map = new HashMap<String, Object>();
        if(status > -1) {
           map.put("status", status);
        }
        Page<Map<String, Object>> pages = btsOrderService.page(map,
                pageIndex, pageSize);
        Map<String, Object> mapResult = new HashMap<String, Object>();
        mapResult.put("data", pages.getData());
        mapResult.put("count", pages.getCount());
        return mapResult;
    }
    
    @RequestMapping(value = "confirm", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO confirmOrder(Long orderId) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("进入订单确认收货接口, 订单id【{}】", new Object[] { orderId });
        }
        return btsOrderService.confirmOrderForUser(orderId);
    }

}

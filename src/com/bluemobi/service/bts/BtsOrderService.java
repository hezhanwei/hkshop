package com.bluemobi.service.bts;

import com.appcore.service.MybatisBaseService;
import com.bluemobi.to.ResultTO;
import com.bluemobi.to.bts.CartOrderRequestTO;
import com.bluemobi.to.bts.SkuOrderRequestTO;

/**
 * 【订单表】 服务类 接口
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2015-11-17 13:27:04
 * 
 */
public interface BtsOrderService extends MybatisBaseService {

    /**
     * 根据商品创建订单
     * @author liuyt
     * @date 2015-11-18 上午11:43:27
     * @return
     * @version
     * @param userId 
     * @param skuOrderReq TODO
     */
    ResultTO createOrderFromGoodsContent(int userId, SkuOrderRequestTO skuOrderReq);

    /**
     * 根据购物车创建订单
     * @author liuyt
     * @date 2015-11-18 上午11:43:53
     * @param cartOrderReq TODO
     * @return
     * @version
     */
    ResultTO createOrderFromCart(int userId, CartOrderRequestTO cartOrderReq);

    /**
     * 用户删除订单
     * @author liuyt
     * @date 2015-11-19 下午4:14:40
     * @param orderId
     * @return
     * @version
     */
    ResultTO deleteOrderForUser(Long orderId);

    /**
     * 用户取消订单
     * @author liuyt
     * @date 2015-11-19 下午4:14:49
     * @param orderId
     * @return
     * @version
     */
    ResultTO cancelOrderForUser(Long orderId);

    /**
     * 用户确认收货订单
     * @author liuyt
     * @date 2015-11-20 上午11:25:46
     * @param orderId
     * @return
     * @version
     */
    ResultTO confirmOrderForUser(Long orderId);

}

package com.bluemobi.service.goods;

import java.util.List;

import java.util.Map;

import com.appcore.service.MybatisBaseService;
import com.bluemobi.po.bts.BtsCart;
import com.bluemobi.po.goods.GoodsContentSku;

/**
 * 【商品主表】 服务类 接口
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2015-11-02 10:14:35
 * 
 */
public interface GoodsContentSkuService extends MybatisBaseService {

    /**
     * 根据给定的购物车, 查询购物车中的sku信息
     * 
     * @author liuyt
     * @date 2015-11-19 下午5:59:59
     * @param cartList
     * @return
     * @version
     */
    List<GoodsContentSku> selectSkuFromCart(List<BtsCart> cartList);

    /**
     * 根据商品id批量删除sku
     * 
     * @auther zhangzheng
     * @date 2015-11-19 下午5:46:42
     * @param paramMap
     * @return
     */
    int deleteByGoodsContentId(Map<String, Object> paramMap);

}

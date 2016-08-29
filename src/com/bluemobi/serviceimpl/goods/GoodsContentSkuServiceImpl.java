package com.bluemobi.serviceimpl.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appcore.dao.MyBatisBaseDao;
import com.appcore.service.impl.MybatisBaseServiceImpl;
import com.bluemobi.dao.goods.GoodsContentSkuDao;
import com.bluemobi.po.bts.BtsCart;
import com.bluemobi.po.goods.GoodsContentSku;
import com.bluemobi.service.goods.GoodsContentSkuService;

/**
 * 【商品主表】 服务类 实现类
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2015-11-02 10:14:35
 * 
 */
@Service(value = "goodsContentSkuService")
public class GoodsContentSkuServiceImpl extends MybatisBaseServiceImpl
        implements GoodsContentSkuService {

    @Autowired
    private GoodsContentSkuDao goodsContentSkuDao;

    @Override
    public MyBatisBaseDao getDao() {
        return goodsContentSkuDao;
    }

    @Override
    public List<GoodsContentSku> selectSkuFromCart(List<BtsCart> cartLIst) {
        List<Integer> skuIdList = new ArrayList<Integer>();
        for (BtsCart cart : cartLIst) {
            skuIdList.add(cart.getSkuId().intValue());
        }
        Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("skuIds", skuIdList);
        return this.goodsContentSkuDao.selectFromCart(parameter);
    }

    @Override
    public int deleteByGoodsContentId(Map<String, Object> paramMap) {
        return goodsContentSkuDao.deleteByGoodsContentId(paramMap);
    }

}

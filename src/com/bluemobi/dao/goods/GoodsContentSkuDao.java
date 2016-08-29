package com.bluemobi.dao.goods;


import java.util.List;
import java.util.Map;
import com.appcore.dao.MyBatisBaseDao;
import com.bluemobi.po.goods.GoodsContentSku;

/**
 * 【商品主表】 数据访问对象 接口
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2015-11-17 17:42:48
 * 
 */
public interface GoodsContentSkuDao extends MyBatisBaseDao {
    
    List<GoodsContentSku> selectFromCart(Map<String, Object> parameter);

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

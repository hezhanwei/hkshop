package com.bluemobi.dao.goods;

import java.util.List;
import java.util.Map;

import com.appcore.dao.MyBatisBaseDao;
import com.bluemobi.po.goods.GoodsProperty;

/**
 * 【商品属性表，用以记录商品属性值】 数据访问对象 接口
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2015-11-17 17:42:48
 * 
 */
public interface GoodsPropertyDao extends MyBatisBaseDao {

    /**
     * 批量新增属性与商品关联
     * 
     * @auther zhangzheng
     * @date 2015-11-17 下午6:24:38
     * @param list
     */
    void insertGoodsProperties(List<GoodsProperty> list);
    
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

package com.bluemobi.dao.trend;

import java.util.List;
import java.util.Map;

import com.appcore.dao.MyBatisBaseDao;
import com.bluemobi.to.trend.PropertyAndPropertyValueTO;

/**
 * 【属性资源表】 数据访问对象 接口
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2015-07-30 10:42:29
 * 
 */
public interface TrendPropertyDao extends MyBatisBaseDao {

    Map<String, Object> selectMapPropertyAndValue(Map<String, Object> map);

    /**
     * 根据分类id查询和该分类绑定的所有属性
     * 
     * @auther zhangzheng
     * @date 2015-11-12 下午2:20:12
     * @param paramMap
     * @return
     */
    List<PropertyAndPropertyValueTO> selectPropertyByCategoryId(Map<String, Object> paramMap);

    /**
     * 根据商品id查询该商品绑定的所有属性
     * 
     * @auther zhangzheng
     * @date 2015-11-18 下午1:53:22
     * @param paramMap
     * @return
     */
    List<PropertyAndPropertyValueTO> selectPropertyByGoodsContentId(Map<String, Object> paramMap);

}

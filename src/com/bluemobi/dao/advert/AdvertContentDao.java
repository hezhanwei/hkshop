package com.bluemobi.dao.advert;

import java.util.Map;

import com.appcore.dao.MyBatisBaseDao;
import com.bluemobi.to.advert.AdvertDetailTO;

/**
 * 【】 数据访问对象 接口
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2015-10-13 11:11:01
 * 
 */
public interface AdvertContentDao extends MyBatisBaseDao {

    /**
     * 查询广告详情
     * 
     * @auther zhangzheng
     * @date 2015-12-3 上午11:38:43
     * @param paramMap
     * @return
     */
    AdvertDetailTO selectAdvertDetail(Map<String, Object> paramMap);

}

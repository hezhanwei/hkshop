package com.bluemobi.service.advert;

import java.util.Map;

import com.appcore.service.MybatisBaseService;
import com.bluemobi.to.advert.AdvertDetailTO;

/**
 * 【】 服务类 接口
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2015-10-13 11:11:01
 * 
 */
public interface AdvertContentService extends MybatisBaseService {

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

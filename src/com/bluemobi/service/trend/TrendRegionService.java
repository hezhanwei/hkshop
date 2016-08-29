package com.bluemobi.service.trend;


import com.appcore.service.MybatisBaseService;
import com.bluemobi.po.trend.TrendRegion;

/**
 * 【】 服务类 接口
 * @author AutoCode 309444359@qq.com
 * @date 2015-07-17 11:22:42
 *
 */
public interface TrendRegionService extends MybatisBaseService{    
    
    /**
     * 添加地区
     * @author HeWeiwen
     * 2015-7-30
     * @param region
     */
    void insertRegion(TrendRegion region);
    
    /**
     * 修改地区
     * @author HeWeiwen
     * 2015-7-30
     * @param region
     */
    void updateRegion(TrendRegion region);



}


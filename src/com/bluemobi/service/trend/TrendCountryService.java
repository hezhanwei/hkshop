package com.bluemobi.service.trend;

import java.util.List;

import com.appcore.service.MybatisBaseService;
import com.bluemobi.po.trend.TrendCountry;

/**
 * 【国家表】 服务类 接口
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-24 14:41:28
 * 
 */
public interface TrendCountryService extends MybatisBaseService {

	/**
	 * 获取国家分类
	 * @data 2016年8月24日 下午2:44:41
	 * @param trendCountry
	 * @return
	 */
	List<TrendCountry> getTrendCountrys(TrendCountry trendCountry);
}

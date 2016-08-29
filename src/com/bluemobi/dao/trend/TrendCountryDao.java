package com.bluemobi.dao.trend;

import java.util.List;

import com.appcore.dao.MyBatisBaseDao;
import com.bluemobi.po.trend.TrendCountry;

/**
 * 【国家表】 数据访问对象 接口
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-24 14:41:28
 * 
 */
public interface TrendCountryDao extends MyBatisBaseDao {

	/**
	 * 获取国家分类
	 * @data 2016年8月24日 下午2:44:41
	 * @param trendCountry
	 * @return
	 */
	List<TrendCountry> getTrendCountrys(TrendCountry trendCountry);
}

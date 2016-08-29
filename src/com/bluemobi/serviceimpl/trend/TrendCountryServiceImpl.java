package com.bluemobi.serviceimpl.trend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appcore.dao.MyBatisBaseDao;
import com.appcore.service.impl.MybatisBaseServiceImpl;
import com.bluemobi.dao.trend.TrendCountryDao;
import com.bluemobi.po.trend.TrendCountry;
import com.bluemobi.service.trend.TrendCountryService;

/**
 * 【国家表】 服务类 实现类
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-24 14:41:28
 * 
 */
@Service(value = "trendCountryService")
public class TrendCountryServiceImpl extends MybatisBaseServiceImpl implements TrendCountryService {

    @Autowired
    private TrendCountryDao trendCountryDao;

    @Override
    public MyBatisBaseDao getDao() {
        return trendCountryDao;
    }

	@Override
	public List<TrendCountry> getTrendCountrys(TrendCountry trendCountry) {
		return trendCountryDao.getTrendCountrys(trendCountry);
	}

}

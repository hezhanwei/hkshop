package com.bluemobi.serviceimpl.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appcore.dao.MyBatisBaseDao;
import com.appcore.service.impl.MybatisBaseServiceImpl;
import com.bluemobi.dao.store.StoreRecommendDao;
import com.bluemobi.service.store.StoreRecommendService;

/**
 * 【每日好店】 服务类 实现类
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-24 17:16:56
 * 
 */
@Service(value = "storeRecommendService")
public class StoreRecommendServiceImpl extends MybatisBaseServiceImpl implements StoreRecommendService {

    @Autowired
    private StoreRecommendDao storeRecommendDao;

    @Override
    public MyBatisBaseDao getDao() {
        return storeRecommendDao;
    }

}

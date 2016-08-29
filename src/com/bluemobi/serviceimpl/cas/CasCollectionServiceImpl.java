package com.bluemobi.serviceimpl.cas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appcore.dao.MyBatisBaseDao;
import com.appcore.service.impl.MybatisBaseServiceImpl;
import com.bluemobi.dao.cas.CasCollectionDao;
import com.bluemobi.service.cas.CasCollectionService;

/**
 * 【收藏表】 服务类 实现类
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-11 16:07:12
 * 
 */
@Service(value = "casCollectionService")
public class CasCollectionServiceImpl extends MybatisBaseServiceImpl implements CasCollectionService {

    @Autowired
    private CasCollectionDao casCollectionDao;

    @Override
    public MyBatisBaseDao getDao() {
        return casCollectionDao;
    }

}

package com.bluemobi.serviceimpl.cms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appcore.dao.MyBatisBaseDao;
import com.appcore.service.impl.MybatisBaseServiceImpl;
import com.bluemobi.dao.cms.CmsBannerDao;
import com.bluemobi.service.cms.CmsBannerService;

/**
 * 【首页banner表】 服务类 实现类
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-08 17:19:10
 * 
 */
@Service(value = "cmsBannerService")
public class CmsBannerServiceImpl extends MybatisBaseServiceImpl implements CmsBannerService {

    @Autowired
    private CmsBannerDao cmsBannerDao;

    @Override
    public MyBatisBaseDao getDao() {
        return cmsBannerDao;
    }

}

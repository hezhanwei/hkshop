package com.bluemobi.serviceimpl.cms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appcore.dao.MyBatisBaseDao;
import com.appcore.service.impl.MybatisBaseServiceImpl;
import com.bluemobi.dao.cms.CmsArticleDao;
import com.bluemobi.service.cms.CmsArticleService;

/**
 * 【平台文章表】 服务类 实现类
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-29 14:29:33
 * 
 */
@Service(value = "cmsArticleService")
public class CmsArticleServiceImpl extends MybatisBaseServiceImpl implements CmsArticleService {

    @Autowired
    private CmsArticleDao cmsArticleDao;

    @Override
    public MyBatisBaseDao getDao() {
        return cmsArticleDao;
    }

}

package com.bluemobi.serviceimpl.bts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appcore.dao.MyBatisBaseDao;
import com.appcore.service.impl.MybatisBaseServiceImpl;
import com.bluemobi.dao.bts.BtsOrderAttachmentDao;
import com.bluemobi.service.bts.BtsOrderAttachmentService;

/**
 * 【订单附件关系表】 服务类 实现类
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2015-11-17 13:27:05
 * 
 */
@Service(value = "btsOrderAttachmentService")
public class BtsOrderAttachmentServiceImpl extends MybatisBaseServiceImpl implements BtsOrderAttachmentService {

    @Autowired
    private BtsOrderAttachmentDao btsOrderAttachmentDao;

    @Override
    public MyBatisBaseDao getDao() {
        return btsOrderAttachmentDao;
    }

}

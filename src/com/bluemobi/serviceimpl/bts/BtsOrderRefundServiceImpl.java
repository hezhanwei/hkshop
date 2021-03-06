package com.bluemobi.serviceimpl.bts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appcore.dao.MyBatisBaseDao;
import com.appcore.service.impl.MybatisBaseServiceImpl;
import com.bluemobi.dao.bts.BtsOrderRefundDao;
import com.bluemobi.service.bts.BtsOrderRefundService;

/**
 * 【退货流程日志表】 服务类 实现类
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2015-11-17 13:27:05
 * 
 */
@Service(value = "btsOrderRefundService")
public class BtsOrderRefundServiceImpl extends MybatisBaseServiceImpl implements BtsOrderRefundService {

    @Autowired
    private BtsOrderRefundDao btsOrderRefundDao;

    @Override
    public MyBatisBaseDao getDao() {
        return btsOrderRefundDao;
    }

}

package com.bluemobi.serviceimpl.bts;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appcore.dao.MyBatisBaseDao;
import com.appcore.service.impl.MybatisBaseServiceImpl;
import com.bluemobi.dao.bts.BtsConsigneeDao;
import com.bluemobi.po.bts.BtsConsignee;
import com.bluemobi.service.bts.BtsConsigneeService;

/**
 * 【】 服务类 实现类
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2015-11-17 17:01:42
 * 
 */
@Service(value = "btsConsigneeService")
public class BtsConsigneeServiceImpl extends MybatisBaseServiceImpl implements BtsConsigneeService {

    @Autowired
    private BtsConsigneeDao btsConsigneeDao;

    @Override
    public MyBatisBaseDao getDao() {
        return btsConsigneeDao;
    }

    @Override
    public BtsConsignee getConsigneeForOrder(int userId, int consigneeId) {
        Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("userid", userId);
        parameter.put("consigneeId", consigneeId);
        return this.btsConsigneeDao.selectForOrder(parameter);
    }

}

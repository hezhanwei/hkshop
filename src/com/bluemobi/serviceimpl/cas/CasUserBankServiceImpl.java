package com.bluemobi.serviceimpl.cas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appcore.dao.MyBatisBaseDao;
import com.appcore.service.impl.MybatisBaseServiceImpl;
import com.bluemobi.dao.cas.CasUserBankDao;
import com.bluemobi.po.cas.CasUserBank;
import com.bluemobi.service.cas.CasUserBankService;

/**
 * 【银行卡信息表】 服务类 实现类
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-11 14:25:41
 * 
 */
@Service(value = "casUserBankService")
public class CasUserBankServiceImpl extends MybatisBaseServiceImpl implements CasUserBankService {

    @Autowired
    private CasUserBankDao casUserBankDao;

    @Override
    public MyBatisBaseDao getDao() {
        return casUserBankDao;
    }

	@Override
	public List<CasUserBank> getCasUserBanks(long userId) {
		return casUserBankDao.getCasUserBanks(userId);
	}

}

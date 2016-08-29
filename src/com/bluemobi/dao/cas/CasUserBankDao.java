package com.bluemobi.dao.cas;

import java.util.List;

import com.appcore.dao.MyBatisBaseDao;
import com.bluemobi.po.cas.CasUserBank;

/**
 * 【银行卡信息表】 数据访问对象 接口
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-11 14:25:41
 * 
 */
public interface CasUserBankDao extends MyBatisBaseDao {
	List<CasUserBank> getCasUserBanks(long userId);
}

package com.bluemobi.service.cas;

import java.util.List;

import com.appcore.service.MybatisBaseService;
import com.bluemobi.po.cas.CasUserBank;

/**
 * 【银行卡信息表】 服务类 接口
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-11 14:25:41
 * 
 */
public interface CasUserBankService extends MybatisBaseService {

	List<CasUserBank> getCasUserBanks(long userid);
}

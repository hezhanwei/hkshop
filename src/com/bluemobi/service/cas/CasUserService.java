package com.bluemobi.service.cas;

import com.appcore.service.MybatisBaseService;
import com.bluemobi.to.ResultTO;
import com.bluemobi.to.cas.UpdatePwdTO;

/**
 * 【用户表】 服务类 接口
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2015-08-31 10:23:11
 * 
 */
public interface CasUserService extends MybatisBaseService {

    ResultTO updateStatus(long userId);

    ResultTO updatePwd(UpdatePwdTO pwdTO);

}

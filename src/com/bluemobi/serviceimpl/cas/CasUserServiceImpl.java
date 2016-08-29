package com.bluemobi.serviceimpl.cas;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appcore.dao.MyBatisBaseDao;
import com.appcore.service.impl.MybatisBaseServiceImpl;
import com.appcore.util.StringUtil;
import com.bluemobi.dao.cas.CasUserDao;
import com.bluemobi.po.cas.CasUser;
import com.bluemobi.service.cas.CasUserService;
import com.bluemobi.to.ResultTO;
import com.bluemobi.to.cas.UpdatePwdTO;

/**
 * 【用户表】 服务类 实现类
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2015-08-31 10:23:11
 * 
 */
@Service(value = "casUserService")
public class CasUserServiceImpl extends MybatisBaseServiceImpl implements
        CasUserService {

    @Autowired
    private CasUserDao casUserDao;
    
    @Override
    public MyBatisBaseDao getDao() {
        return casUserDao;
    }
    
    private CasUser getUser(long userId) {
        Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("userid", userId);
        return this.selectObject(parameter);
    }

    @Override
    public ResultTO updateStatus(long userId) {
        CasUser user = getUser(userId);
        user.setStatus((byte) (user.getStatus().byteValue() == 1 ? 0 : 1));
        int r = this.update(user);
        if (r == 0) {
            return ResultTO.newFailResultTO("更新失败, 请联系管理员", null);
        } else {
            return ResultTO.newSuccessResultTO("", null);
        }
    }

    @Override
    public ResultTO updatePwd(UpdatePwdTO pwdTO) {
        CasUser user = getUser(pwdTO.getUserId());

        // 1. 校验原密码是否正确
        String passwordMd5 = StringUtil.md5(StringUtil.md5(pwdTO
                .getOldPassword()) + user.getSalt());
        if (!passwordMd5.equals(user.getPassword())) {
            return ResultTO.newFailResultTO("原密码不正确,请检查后重新输入!", null);
        }

        // 2. 校验新密码和重复新密码是否一致
        if (!pwdTO.getPassword().equals(pwdTO.getRepassword())) {
            return ResultTO.newFailResultTO("新密码和重复密码不一致,请检查后重新输入!", null);
        }

        // 3. 如果新密码和原密码一致，则不操作，直接返回成功结果
        if (pwdTO.getPassword().equals(pwdTO.getOldPassword())) {
            return ResultTO.newSuccessResultTO("更新成功!", null);
        }

        // 4. 更新密码
        user = new CasUser();
        user.setUserid(pwdTO.getUserId());
        user.setPassword(StringUtil.md5(StringUtil.md5(pwdTO.getPassword())
                + user.getSalt()));
        user.setMtime(Calendar.getInstance().getTime());
        int ret = this.update(user);
        if (ret == 1) {
            return ResultTO.newSuccessResultTO("更新成功!", null);
        } else {
            return ResultTO.newFailResultTO("更新失败,请联系管理员!", null);
        }
    }

}

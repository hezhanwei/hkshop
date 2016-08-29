package com.bluemobi.controller.back.cas;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appcore.page.Page;
import com.bluemobi.controller.AbstractWebController;
import com.bluemobi.po.cas.CasUser;
import com.bluemobi.service.cas.CasUserService;
import com.bluemobi.to.ResultTO;

/**
 *  会员管理
 * @author zhangzheng, liuyt , heweiwen
 * 2015-12-4 下午3:17:23
 */
@Controller
@RequestMapping("casuser")
public class CasUserController extends AbstractWebController {
    @Autowired
    private CasUserService casUserService;

    /** 会员已删除标识 */
    private static final byte IS_DELETEED = 1;

    /** 会员id的属性名 */
    private static final String USERID_FIELD = "userid";

    /**
     * 初始化会员管理页面
     * 
     * @param model
     * @return
     */
    @RequestMapping("index")
    public String index(Model model) {
        initIndex(model);
        return "cas/casUser.index";
    }

    /**
     * 分页查询会员信息
     * 
     * @param userStatus
     *            会员激活状态
     * @param key
     *            查询关键字
     * @param pageSize
     * @param pageIndex
     * @return
     */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> page(String userStatus, String key,
            int pageSize, int pageIndex) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(userStatus)) {
            map.put("status", Integer.parseInt(userStatus));
        }
        map.put("key", key);
        Page<Map<String, Object>> userPages = casUserService.page(map,
                pageIndex, pageSize);
        Map<String, Object> mapResult = new HashMap<String, Object>();
        mapResult.put("data", userPages.getData());
        mapResult.put("count", userPages.getCount());
        return mapResult;
    }

    /**
     * 会员详情初始化
     * 
     * @param userid
     * @param model
     * @return
     */
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public String indexUserDetail(int userid, Model model,
            HttpServletRequest req) {
        Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.put(USERID_FIELD, userid);
        CasUser user = casUserService.selectObject(parameter);
        // 加载公共数据
        initIndex(model);
        model.addAttribute("user", user);
        return "cas/casUser.detail";
    }

    /**
     * 编辑会员初始化
     * 
     * @param userid
     * @param model
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String indexUserEdit(int userid, Model model) {
        Map<String, Object> userMap = new HashMap<String, Object>();
        userMap.put(USERID_FIELD, userid);
        CasUser user = casUserService.selectObject(userMap);

        // 加载公共数据
        initIndex(model);
        model.addAttribute("user", user);

        return "cas/casUser.edit";
    }

    /**
     * 编辑会员
     * 
     * @param user
     * @param userDetail
     * @param birthday
     * @param result
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO editIndex(@ModelAttribute CasUser user, BindingResult result) {
        user.setMtime(new Date());
        if ("".equals(user.getPassword())) {
            user.setPassword(null);
        }
        casUserService.update(user);
        return ResultTO.newSuccessResultTO("修改成功", null);
    }

    /**
     * 删除会员
     * 
     * @param userid
     * @param model
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO deleteUser(Long userid) {
        Map<String, Object> userMap = new HashMap<String, Object>();
        userMap.put(USERID_FIELD, userid);
        CasUser user = casUserService.selectObject(userMap);
        if (user == null) {
            return ResultTO.newFailResultTO("删除失败, 用户不存在!", null);
        }
        if (user.getIsDel() == 1) {
            return ResultTO.newFailResultTO("删除失败, 用户已删除!", null);
        }
        user = new CasUser();
        user.setUserid(userid);
        user.setIsDel(IS_DELETEED);
        int count = casUserService.update(user);
        if (count == 1) {
            return ResultTO.newSuccessResultTO("逻辑删除成功", null);
        } else {
            return ResultTO.newFailResultTO("删除失败,请联系管理员", null);
        }
    }

    /**
     * 验证用户名是否可注册接口
     * 
     * @param username
     * @return
     */
    @RequestMapping(value = "sign/isUsernameAvailable", method = RequestMethod.GET)
    @ResponseBody
    public Boolean isUsernameAvailable(String username) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", username);
        CasUser casUser = casUserService.selectObject(map);
        if (casUser != null) {
            return false;
        }
        return true;
    }

    /**
     * 更新会员激活状态
     * 
     * @param userid
     * @param model
     * @return
     */
    @RequestMapping(value = "status/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO updateStatus(long userid, Model model,
            HttpServletRequest req) {
        return casUserService.updateStatus(userid);
    }
}

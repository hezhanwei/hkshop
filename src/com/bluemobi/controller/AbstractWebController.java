package com.bluemobi.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.appcore.context.AppContext;
import com.appcore.security.AccessToken;
import com.appcore.service.TokenService;
import com.appcore.util.AjaxUtil;
import com.appcore.util.CookieUtil;
import com.appcore.util.SessionUtil;
import com.bluemobi.constant.AdminConstant;
import com.bluemobi.po.admin.AdminUser;
import com.bluemobi.po.system.SystemNavigation;

/**
 * 抽象的web控制器
 * @Description
 * @author haojian 309444359@qq.com
 * @date 2015-10-26 下午5:14:41 
 *
 */
public abstract class AbstractWebController extends AbstractController{
	
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractWebController.class);
    
    /**
     * 获取userId
     * @author haojian
     * @date 2015-10-15 上午10:09:30 
     * @return
     * @return int
     */
    public int getUserid() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        // 1、校验客户端cookie里面的token是否为空
        String tokenId = CookieUtil.getCookieValue(request, SessionUtil.bm_token);
        
        if(tokenId==null||"".equals(tokenId)){
            LOGGER.error("来自【{}】的请求中没有tokenId", request.getRemoteAddr());
            return 0;
        }

        // 2、校验服务端自定义session里面的accessToken对象是否为空
        TokenService tokenService = (TokenService) AppContext.getBean("tokenService");
        AccessToken accessToken = tokenService.checkToken(tokenId);
        
        int userid = Integer.valueOf(accessToken.getUserId());

        return userid;
    }
    
    /**
     * 获取用户名
     * @author haojian
     * @date 2015-12-2 上午9:48:47 
     * @param request
     * @return
     * @return String
     */
    public String getUsername(){
        AdminUser adminUser = getAdminUser();
        if(adminUser!=null){
            return adminUser.getUsername();
        }else{
            return String.valueOf(getUserid());
        }
    }
    
    /**
     * 获取管理员用户
     * @author haojian
     * @date 2015-12-2 上午9:48:53 
     * @param request
     * @return
     * @return AdminUser
     */
    public AdminUser getAdminUser(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        AdminUser adminUser = (AdminUser) SessionUtil.getAttribute(request, AdminConstant.KEY_ADMIN_USER);
        return adminUser;
    
    }

    /**
     * 初始化界面调用
     * 
     * @author HeWeiwen 2015-7-17
     * @param model
     * @param req
     */
    public void initIndex(Model model) {
        
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        
        //1,检测是否是ajax请求，是否需要重新加载数据
        if (AjaxUtil.checkIsAjax(request)) {
            LOGGER.debug("ajax请求，不查询导航栏！");
            return;
        }
        //2,通过tokenID获得用户对象
        AdminUser user = (AdminUser) SessionUtil.getAttribute(request, AdminConstant.KEY_ADMIN_USER);
        model.addAttribute("loggedInUser", user);
        
        //3,通过用户信息获得所有用户权限菜单数据
        List<SystemNavigation> navigationList = (List<SystemNavigation>)SessionUtil.getAttribute(request, AdminConstant.KEY_NAVIGATION_LIST);
        model.addAttribute("userNavsList", navigationList);
    }

   
}

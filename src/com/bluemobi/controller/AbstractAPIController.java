package com.bluemobi.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.appcore.context.AppContext;
import com.appcore.security.AccessToken;
import com.appcore.service.TokenService;
import com.appcore.util.SessionUtil;
import com.bluemobi.constant.AdminConstant;
import com.bluemobi.po.admin.AdminUser;
import com.bluemobi.po.cas.CasUser;

/**
 * 抽象的API控制器
 * @Description
 * @author haojian 309444359@qq.com
 * @date 2015-10-15 上午10:08:40 
 *
 */
public abstract class AbstractAPIController extends AbstractController{

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAPIController.class);

    /**
     * 获取userId
     * @author haojian
     * @date 2015-10-15 上午10:09:30 
     * @return
     * @return int
     */
    @Override
    public int getUserid() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        // 1、校验客户端cookie里面的token是否为空
        String tokenId = request.getParameter("tokenId");
        
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
        CasUser casUser = getCasUser();
        if(casUser!=null){
            return casUser.getUsername();
        }else{
            return String.valueOf(getUserid());
        }
        
    }
    
    /**
     * 获取普通用户
     * @author haojian
     * @date 2015-12-2 上午9:48:53 
     * @param request
     * @return
     * @return CasUser
     */
    public CasUser getCasUser(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        CasUser casUser = (CasUser) SessionUtil.getAttribute(request, AdminConstant.KEY_ADMIN_USER);
        return casUser;
    
    }
    

    
}

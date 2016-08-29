package com.bluemobi.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.appcore.security.AccessToken;
import com.appcore.service.TokenService;
import com.appcore.util.AjaxUtil;
import com.appcore.util.CookieUtil;
import com.appcore.util.SessionUtil;
import com.bluemobi.conf.Config;
import com.bluemobi.constant.AdminConstant;
import com.bluemobi.po.admin.AdminPermission;
import com.bluemobi.po.admin.AdminUser;
import com.bluemobi.service.admin.AdminPermissionService;

/**
 * AccessFilter过滤
 * 
 * @author heweiwen 2015-6-18 下午4:04:23
 */
public class AccessFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(AccessFilter.class);
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AdminPermissionService adminPermissionService;
    /** 静态资源后缀列表 */
    private List<String> staticResourceSuffixList;
    /** api接口url前缀 */
    private String apiUrlPreffix;
    /** 访客访问url列表 */
    private List<String> visitUrlList;
    /** 用户开放url列表(不需要特殊授权) */
    private List<String> userOpenUrlList;
    

    public void destroy() {
        LOGGER.info("AccessFilter构造方法");
    }

    /**
     * 过滤器
     */
    @SuppressWarnings("unchecked")
    public void doFilter(ServletRequest req, ServletResponse resp,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        // 1. 处理静态资源
        boolean isAllow = false;
        for (String staticResourceSuffix : staticResourceSuffixList) {
            if (request.getRequestURI().endsWith(staticResourceSuffix)) {
                isAllow = true;
                break;
            }
        }
        if (isAllow) {
            chain.doFilter(req, resp);
            return;
        }

        // 2. 处理api接口url
        if (request.getRequestURI().startsWith(
                request.getContextPath() + apiUrlPreffix)) {
            LOGGER.info("放开对【{}】API接口数据的过滤",
                    new Object[] { request.getRequestURI() });
            chain.doFilter(req, resp);
            return;
        }
        
        // 3. 处理访客访问url
        for (String visitUrl : visitUrlList) {
            if (request.getRequestURI().equals(
                    request.getContextPath() + visitUrl)) {
                isAllow = true;
                break;
            }
        }
        if (isAllow) {
            chain.doFilter(req, resp);
            return;
        }


        // 5. 处理需要权限控制的url
        String uri = request.getRequestURI().substring(
                request.getContextPath().length());
        // 5.1 从cookie中获取tokenId
        String tokenId = CookieUtil.getCookieValue(request,
                SessionUtil.bm_token);
        if (tokenId == null) {
            LOGGER.error("没有取到cookie里面的token！需要先登录！");
            processRedirect(req, resp, uri);
            return;
        }
        // 5.2 校验用户是否登陆
        AccessToken accessToken = tokenService.checkToken(tokenId);
        if (accessToken == null) {
            LOGGER.error("服务端自定义session已过期！需要重新登录！");
            processRedirect(req, resp, uri);
            return;
        }
        // 5.3 获得用户信息
        AdminUser user = (AdminUser) SessionUtil.getAttribute(request,
                AdminConstant.KEY_ADMIN_USER);
        if (user == null) {
            LOGGER.error("AdminUser为空！需要重新登录！");
            response.sendRedirect(Config.BASE_URL + Config.SEND_REDIRECT + uri);
            return;
        }
        
        // 5.4. 用户开放url列表
        for (String openUrl : userOpenUrlList) {
            if (request.getRequestURI().equals(
                    request.getContextPath() + openUrl)) {
                isAllow = true;
                break;
            }
        }
        if (isAllow) {
            chain.doFilter(req, resp);
            return;
        }
        
        // 5.5,获得所有权限数据（反向匹配，模糊匹配）
        // 5.6,判断URI请求是否在权限数据中（不在数据中 直接放开过滤）
        if (adminPermissionService.isAddPermissionByURI(uri)) {
            LOGGER.info("【{}】请求不在权限数据中，放开对它的过滤", new Object[]{uri});
            chain.doFilter(req, resp);
            return;
        }
        
        // 5.7 获得当前用户权限数据（绝对匹配数据）
        Map<String, AdminPermission> permissionMap = (HashMap<String, AdminPermission>) SessionUtil
                .getAttribute(request, AdminConstant.KEY_PERMISSION_MAP);
        if (permissionMap.containsKey(uri)) {
        	request.setAttribute("currentNavId", permissionMap.get(uri).getNavigationId());
            chain.doFilter(req, resp);
            return;
        } else {
        	LOGGER.info("【{}】请求不在权限数据中，放开对它的过滤", new Object[]{uri});
            chain.doFilter(req, resp);
            return;
        	
            /*LOGGER.error("您无访问权限！请联系管理员！");
            HttpServletRequest httpReq = (HttpServletRequest) req;
            if (AjaxUtil.checkIsAjax(httpReq)) {
                response.setContentType("text/html; charset=utf-8");
                PrintWriter pw = response.getWriter();
                pw.print("<script>alert('您无访问权限！请联系管理员！');</script>");
                pw.flush();
                
            } else {
                response.sendRedirect(Config.BASE_URL + "/admin");
            }
            return;*/
        }
    }

	private void processRedirect(ServletRequest request, ServletResponse response,
			String uri) throws IOException {
		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpServletResponse httpRep = (HttpServletResponse) response;

		if (AjaxUtil.checkIsAjax(httpReq)) {
			//session过期状态
		    httpRep.setHeader("sessionstatus", "timeout");
		    //根据uri判断是否为菜单url,依据为带有2个/. 如果是,则在header中增加redirectUri变量
		    //以便前端js获取使用(具体使用参见common.js的processSessionTimeout方法)
		    if(uri.split("/").length == 2) {
		    	httpRep.setHeader("redirectUri", uri);
		    }
		} else {
			httpRep.sendRedirect(Config.BASE_URL + Config.SEND_REDIRECT
		            + uri);
		}
	}

    /**
     * 初始化安全和不安全页面
     */
    public void init(FilterConfig conf) throws ServletException {
        LOGGER.info("init方法");
    }

    public List<String> getStaticResourceSuffixList() {
        return staticResourceSuffixList;
    }

    public void setStaticResourceSuffixList(
            List<String> staticResourceSuffixList) {
        this.staticResourceSuffixList = staticResourceSuffixList;
    }

    public String getApiUrlPreffix() {
        return apiUrlPreffix;
    }

    public void setApiUrlPreffix(String apiUrlPreffix) {
        this.apiUrlPreffix = apiUrlPreffix;
    }

    public List<String> getVisitUrlList() {
        return visitUrlList;
    }

    public void setVisitUrlList(List<String> visitUrlList) {
        this.visitUrlList = visitUrlList;
    }

    public List<String> getUserOpenUrlList() {
        return userOpenUrlList;
    }

    public void setUserOpenUrlList(List<String> userOpenUrlList) {
        this.userOpenUrlList = userOpenUrlList;
    }

}

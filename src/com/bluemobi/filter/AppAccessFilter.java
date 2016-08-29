package com.bluemobi.filter;
import java.io.IOException;
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

public class AppAccessFilter implements Filter {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AppAccessFilter.class);
    
    /**安全的URI地址集合*/
    private static String[] SECURITY_URI_ARRAY;

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest req, ServletResponse resp,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        if (request.getRequestURI().contains("/error/")) {
            return;
        }
        String httpURL = request.getContextPath() + "/api";
        if (!request.getRequestURI().startsWith(httpURL)) {
            chain.doFilter(req, resp);
            return;
        }
        // 设置编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        boolean needSecurity = false;
        request.setAttribute("webroot", request.getContextPath());
        // 判断请求URI是否在配置的需要登录验证的URI列表中
        for (int i = 0; i < SECURITY_URI_ARRAY.length; i++) {
            String sUri = SECURITY_URI_ARRAY[i];
            if ("".equals(sUri))
                continue;
            if (request.getRequestURI().startsWith(request.getContextPath() + sUri.replace("*", ""))) {
                needSecurity = true;
                break;
            }
        }
        if (!needSecurity) {
            // 不需要验证的请求
            chain.doFilter(req, resp);
        } else {
            try {
                // 需要监视的请求
                chain.doFilter(req, resp);
            } catch (Exception e) {
                LOGGER.error("需要监视的请求出错了【{}】",e);
                e.getLocalizedMessage();
                response.sendRedirect(request.getContextPath()+ "/error/api_request_error.jsp");
            }
        }

    }

    /**
     * 初始化file配置
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fileConfig) throws ServletException {
        // 获取需要登录验证的URI
        String security_uri_conf = fileConfig.getInitParameter("SECURITY_URI");
        // URI以逗号分隔
        SECURITY_URI_ARRAY = security_uri_conf.split(",");
    }

    public void destroy() {
        LOGGER.info("destroy");
    }

    /**
     * Default constructor.
     */
    public AppAccessFilter() {
    }

}

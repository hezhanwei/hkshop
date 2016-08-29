package com.bluemobi.controller.api.feedback;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.appcore.security.AccessToken;
import com.appcore.service.TokenService;
import com.bluemobi.service.feedback.FeedbackContentService;
import com.bluemobi.to.ResultTO;

/**
 * 留言反馈模块 控制器
 * 
 * @author AutoCode E-mail:309444359@qq.com
 * @date 2015-10-13 15:38:05
 */
@Controller
@RequestMapping("api/feeback")
public class FeedbackController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(FeedbackController.class);

    @Autowired
    private FeedbackContentService feebackContentService;
    @Autowired
    private TokenService tokenService;

    private static final String ANONYMOUS_USER_ID = "0";

    /**
     * 添加留言反馈
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO add(String body, String tokenId) {
        LOGGER.debug("参数： " + "body: " + body + "  tokenId:" + tokenId);
        ResultTO resultTO = null;
        if (StringUtils.isEmpty(body)) {
            return ResultTO.newFailResultTO("留言内容为空", null);
        }

        String userId = ANONYMOUS_USER_ID;
        if (!StringUtils.isEmpty(tokenId)) {
            AccessToken accessToken = tokenService.checkToken(tokenId);
            userId = accessToken == null ? ANONYMOUS_USER_ID : accessToken
                    .getUserId();
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        resultTO = feebackContentService.insertFeedbackContent(body, userId,
                request.getRemoteAddr());
        return resultTO;
    }

}

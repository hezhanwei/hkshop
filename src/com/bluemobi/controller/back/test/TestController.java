package com.bluemobi.controller.back.test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appcore.service.TokenService;
import com.appcore.util.CookieUtil;
import com.appcore.util.SessionUtil;
import com.appcore.util.UUIDService;
import com.bluemobi.po.test.Test;
import com.bluemobi.po.test.Ticket;
import com.bluemobi.service.test.TestService;
import com.bluemobi.to.ResultTO;

/**
 * 测试
 * 
 * @author haojian
 * @date 2015-6-9 上午10:52:37
 * 
 */
@Controller
@RequestMapping("test")
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private TestService testService;
    @Autowired
    private TokenService tokenService;

    

    @RequestMapping(value = "buyTicket", method = RequestMethod.GET)
    @ResponseBody
    public ResultTO buyTicket() {

        String msg = testService.buyTicket(1);

        LOGGER.info(msg);

        ResultTO resultTO = ResultTO.newSuccessResultTO("success",msg);
        
        return resultTO;
    }

    @RequestMapping(value = "getTicketInfo", method = RequestMethod.GET)
    @ResponseBody
    public ResultTO getTicketInfo() {

        Ticket ticket = testService.getTicketInfo();

        ResultTO resultTO = ResultTO.newSuccessResultTO("success", ticket);
       
        return resultTO;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public Object test(HttpServletRequest request, HttpServletResponse response, String cmd) {

        LOGGER.info("测试命令：【" + cmd + "】");
        if ("createTokenByUserId".equalsIgnoreCase(cmd)) {

            tokenService.createTokenByUserId("1001");

        } else if ("createTokenByTokenId".equalsIgnoreCase(cmd)) {

            tokenService.createTokenByTokenId("", "1001");

        } else if ("writeCookie".equalsIgnoreCase(cmd)) {

            CookieUtil.writeCookie(response, "bm_token", UUIDService.getUUID(), 24 * 3600 * 3, "/");

        } else if ("printCookie".equalsIgnoreCase(cmd)) {
            Cookie[] cc = request.getCookies();
            for (Cookie c : cc) {
                LOGGER.info(c.getDomain() + " , " + c.getComment() + " , " + c.getPath() + " , " + c.getName() + " , " + c.getValue());
            }

            String token = CookieUtil.getCookieValue(request, SessionUtil.bm_token);
            LOGGER.info("token===" + token);

        } else if ("removeCookie".equalsIgnoreCase(cmd)) {

            CookieUtil.removeCookie("bm_token", request, response);

        } else if ("ip".equalsIgnoreCase(cmd)) {
            String realIp = SessionUtil.getPosterIp(request);
            String remoteAddr = request.getRemoteAddr();
            String remoteHost = request.getRemoteHost();

            LOGGER.info("realIp==" + realIp);
            LOGGER.info("remoteAddr==" + remoteAddr);
            LOGGER.info("remoteHost==" + remoteHost);
        }

        ResultTO resultTO = ResultTO.newSuccessResultTO("success", "命令：【" + cmd + "】, 测试成功！");

        return resultTO;

    }

    @RequestMapping(value = "insert", method = RequestMethod.GET)
    @ResponseBody
    public Object insert() {
        LOGGER.info("-----insert-----");
        for (int i = 10; i < 300; i++) {
            Test t = new Test();
            t.setName("hao-" + i);
            t.setAge(i % 30);
            t.setAddress("上海杨浦-" + i);
            testService.insert(t);
            if (i % 10000 == 0) {
                LOGGER.info(new Date() + "[" + i + "]");
            }
        }
        return "-----insert success-----";
    }
    
    @RequestMapping(value = "testTransaction", method = RequestMethod.GET)
    @ResponseBody
    public Object testTransaction(HttpServletRequest request) {
        
        boolean hasException = Boolean.valueOf(request.getParameter("hasException"));
        
        LOGGER.info("-----testTransaction-----");
        Test t = new Test();
        t.setId(1001);
        t.setAge(28);
        t.setAddress("shanghai");
        t.setName("hao");
        testService.testTransaction(t, hasException);
        
        return "-----insert success-----";
        
    }
    
    

    @RequestMapping(value = "update", method = RequestMethod.GET)
    @ResponseBody
    public Object update(HttpServletRequest request) {
        LOGGER.info("-----update-----");
        
        long sleepTime = Integer.valueOf(request.getParameter("sleepTime"))*1000L;
        
        int age = Integer.valueOf(request.getParameter("age"));
        
        Map<String, Integer> pMap = new HashMap<String, Integer>();
        pMap.put("id", 1);
        Test t = testService.selectObject(pMap);
        t.setName("hao-change");
        t.setAge(age);
        t.setAddress(Thread.currentThread().toString());
        testService.testUpdate(t, sleepTime);
        t = testService.selectObject(pMap);
        return t;
    }

    @RequestMapping(value = "selectObject", method = RequestMethod.GET)
    @ResponseBody
    public Object selectObject(HttpServletResponse response) {
        LOGGER.info("-----selectObject-----");
        Map<String, Integer> pMap = new HashMap<String, Integer>();
        pMap.put("id", 100);
        Test test = testService.selectObject(pMap);
        return test;
    }

    @RequestMapping(value = "selectObjectList", method = RequestMethod.GET)
    @ResponseBody
    public Object selectObjectList(HttpServletRequest request) {
        LOGGER.info("-----selectObjectList-----");
        Map<String, String> map = new HashMap<String, String>();
        map.put("nameNotNull", "hao-200015");
        List<Test> list = testService.selectObjectList(map);
        return list;
    }

    @RequestMapping(value = "selectMap", method = RequestMethod.GET)
    @ResponseBody
    public Object selectMap() {
        LOGGER.info("-----selectMap-----");
        Map<String, Integer> pMap = new HashMap<String, Integer>();
        pMap.put("id", 100);
        Map<String, Object> map = testService.selectMap(pMap);
        return map;
    }

    @RequestMapping(value = "selectMapList", method = RequestMethod.GET)
    @ResponseBody
    public Object selectMapList() {
        LOGGER.info("-----selectMapList-----");
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("id", 1005);
        List<Map<String, Object>> list = testService.selectMapList(map);
        return list;
    }

    @RequestMapping(value = "page", method = RequestMethod.GET)
    @ResponseBody
    public Object page(int pageIndex, int pageSize) {
        Map<String, Object> map = new HashMap<String, Object>();
        return testService.page(map, pageIndex, pageSize);
    }
    
  
    
    
    

}

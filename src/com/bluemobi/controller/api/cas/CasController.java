package com.bluemobi.controller.api.cas;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.jboss.netty.util.internal.ConcurrentHashMap;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.appcore.core.pool.TaskPoolFactory;
import com.appcore.service.TokenService;
import com.appcore.util.RandomUtil;
import com.appcore.util.StringUtil;
import com.appcore.util.TimeUtil;
import com.appcore.util.UUIDService;
import com.bluemobi.constant.BaseConstant;
import com.bluemobi.controller.AbstractAPIController;
import com.bluemobi.po.cas.CasCode;
import com.bluemobi.po.cas.CasUser;
import com.bluemobi.po.cas.CasUserBank;
import com.bluemobi.po.cas.CasUserDetail;
import com.bluemobi.service.cas.CasCodeService;
import com.bluemobi.service.cas.CasUserBankService;
import com.bluemobi.service.cas.CasUserDetailService;
import com.bluemobi.service.cas.CasUserService;
import com.bluemobi.to.ResultTO;
import com.bluemobi.to.cas.TempUser;
import com.bluemobi.to.cas.UpdatePwdTO;
import com.bluemobi.util.MessageSender;
import com.bluemobi.util.MsgSender;
import com.bluemobi.util.SimpleMail;

/**
 * 用户登陆注册找回密码——接口API
 * 
 * @author heweiwen 2015-7-21 下午4:03:01
 */
@Controller
@RequestMapping("api/cas")
public class CasController extends AbstractAPIController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CasController.class);

    @Autowired
    private CasUserService casUserService;
    @Autowired
    private CasUserDetailService casUserDetailService;
    @Autowired
    private CasCodeService casCodeService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private CasUserBankService casUserBankService;

    /** 邮件名校验正则表达式 */
    private static final String CHECK_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    /** 手机号码校验正则表达式 */
    private static final String CHECK_PHONE = "^1[34578][0-9]{1}[0-9]{8}$";
    /** 验证码过期时间（毫秒） */
    private static final long EXP_TIME = 120000L;
    /** 1：用户名注册 */
    private static final int SIMPLE_NAME = 1;
    /** 2：手机注册 */
    private static final int SIMPLE_PHONE = 2;
    /** 9：邮箱注册 */
    private static final int SIMPLE_EMAIL = 9;

    /** 定义公共名字 */
    private static final String USERNAME = "username";
    private static final String USERID = "userid";

    /** 用户手机验证码临时缓存 */
    private static final ConcurrentMap<String, TempUser> TEMP_USER_MAP = new ConcurrentHashMap<String, TempUser>();

    /**
     * 登录
     * 
     * @author HeWeiwen 2015-7-13
     * @param userStrName
     *            （用户名，邮件名，手机号码）
     * @param password
     * @param simple
     *            （登录类型：1：用户名注册；2：手机注册；9：邮箱注册；）
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO login(HttpServletRequest request, String username,
            String password, String code) {
        LOGGER.info("-----login-----");
        ResultTO resultTO = null;
        // 1,校验验证码的时效性
        if (null != code && !"".equals(code)) {
            // 校验验证码
            resultTO = checkCode(username, code);
            if (BaseConstant.STATUS_FAILURE == resultTO.getStatus()) {
                return resultTO;
            }
        }
        Map<String, String> userMap = new HashMap<String, String>();
        userMap.put(USERNAME, username);
        // 2,根据用户名查询用户对象
        List<CasUser> casUserList = casUserService.selectObjectList(userMap);
        // 3,无用户时手机号码验证码注册登录
        if (casUserList.isEmpty()) {
            // 查询无用户（手机号码+验证码），注册登录
            resultTO = loginCheckUserIsNull(request, username, code, password);
            return resultTO;
        }
        CasUser casUser = casUserList.get(0);
        // 4,第二次（手机号码+验证码）登录时操作
        if (!"".equals(code) && checkUserNameType(username) == SIMPLE_PHONE) {
            // 登录成功，返回tokenId
            resultTO = loginContinue(casUser);
            return resultTO;
        }
        // 5,用户名+密码登录
        String passwordMd5Md5 = StringUtil.md5(StringUtil.md5(password)
                + casUser.getSalt());
        if (!passwordMd5Md5.equals(casUser.getPassword())) {
            resultTO.setMsg("密码不正确");
            resultTO.setStatus(BaseConstant.STATUS_FAILURE);
        } else {
            // 6,登录成功，返回tokenId
            resultTO = loginContinue(casUser);
        }
        return resultTO;
    }

    /**
     * 注册
     * 
     * @author HeWeiwen 2015-9-2
     * @param username
     *            （用户名（可能是：常规用户名、手机号、邮箱））
     * @param password
     *            (密码)
     * @param simple
     *            （注册方式。1：用户名注册；2：手机注册；9：邮箱注册；）
     * @param repassword
     *            (重复输入的密码)
     * @param code
     *            (短信验证码)
     * @param request
     *            扩展参数用这种方式获得：（String gender = request.getParameter("gender") ==
     *            null ? "" : request.getParameter("gender");）
     * @return
     */
    @RequestMapping(value = "signup", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO signup(HttpServletRequest request, Integer simple,
            String username, String password, String repassword, String code) {
        LOGGER.info("-----signup-----");
        Map<String, Object> reqMap = new HashMap<String, Object>();
        CasUser casUser = new CasUser();
        ResultTO resultTO = null;
        // 1,用户名注册
        if (simple == SIMPLE_NAME) {
            if (!"".equals(repassword) && !repassword.equals(password)) {
                // 1.1,校验密码的一致性
                return ResultTO.newFailResultTO("用户名注册密码不一致", null);
            }
            casUser.setUsername(username);
        } else if (simple == SIMPLE_PHONE) {// 2,手机号码注册
            // 2.1,校验验证码
            resultTO = checkCode(username, code);
            if (BaseConstant.STATUS_FAILURE == resultTO.getStatus()) {
                return resultTO;
            }
            casUser.setPhone(username);
        } else if (simple == SIMPLE_EMAIL) {// 3,邮箱注册
            // 3.1,校验密码的一致性
            if (!password.equals(repassword)) {
                return ResultTO.newFailResultTO("邮箱注册密码不一致", null);
            }
            casUser.setEmail(username);
        }
        // 4,校验用户是否已存在
        reqMap.put(USERNAME, username);
        List<Map<String, Object>> reqUser = casUserService
                .selectMapList(reqMap);
        if (!reqUser.isEmpty()) {
            return ResultTO.newFailResultTO("该用户已经存在！请登陆！", null);
        }
        // 5，用户信息录入
        int resultUser = casUserInsert(casUser, repassword, username);
        if (resultUser == 1) {
            // 6,注册成功后相关逻辑
            resultTO = signupContinue(simple, username, code);
        } else {
            resultTO.setStatus(BaseConstant.STATUS_FAILURE);
            resultTO.setMsg("注册失败！");
        }
        return resultTO;
    }

    /**
     * 找回密码接口
     * 
     * @author HeWeiwen 2015-9-2
     * @param username
     *            （用户名（即手机号）
     * @param password
     *            （密码）
     * @param repassword
     *            （重复输入的密码）
     * @param code
     *            （短信验证码）
     * @return
     */
    @RequestMapping(value = "forgotpassword", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO forGotPassword(String username, String phone,
            String password, String repassword, String code) {
        LOGGER.info("-----forgotpassword-----");
        ResultTO resultTO = null;
        // 1,校验验证码（手机号码）
        resultTO = checkCode(phone, code);
        if (BaseConstant.STATUS_FAILURE == resultTO.getStatus()) {
            return resultTO;
        }
        Map<String, String> userMap = new HashMap<String, String>();
        userMap.put("phone", phone);

        // 2,根据唯一手机号码查询用户对象
        List<CasUser> casUserList = casUserService.selectObjectList(userMap);
        if (casUserList.isEmpty()) {
            return ResultTO.newFailResultTO("用户不存在", null);
        }
        CasUser casUser = casUserList.get(0);
        // 3,判断用户名是否为空，如果不为空，比较用户信息
        String usernameNew = username;
        if (null != usernameNew && !"".equals(usernameNew)) {
            // 比较用户信息是否一致
            if (!usernameNew.equals(casUser.getUsername())) {
                return ResultTO.newFailResultTO("手机号码与用户信息和不一致", null);
            }
        }
        // 4,校验密码的一致性
        if (!"".equals(repassword) && !repassword.equals(password)) {
            return ResultTO.newFailResultTO("找回密码接口密码不一致", null);
        }
        // 5,密码进行MD5加密
        String passwordMd5Md5 = StringUtil.md5(StringUtil.md5(password)
                + casUser.getSalt());
        casUser.setPassword(passwordMd5Md5);
        // 6,持久化数据(修改密码)
        int resultUser = casUserService.update(casUser);
        if (resultUser == 1) {
            resultTO.setStatus(BaseConstant.STATUS_SUCCESS);
            resultTO.setMsg("成功找回密码！");
        } else {
            resultTO.setStatus(BaseConstant.STATUS_FAILURE);
            resultTO.setMsg("找回密码失败！");
        }

        return resultTO;
    }

    /**
     * 发送短信验证码
     * 
     * @author HeWeiwen 2015-9-1
     * @param sendto
     *            用户电话号码
     * @param action
     *            发送动作类型（register：注册；login：登录；'forgotpassword'：忘记密码；绑定手机：'bind'）
     * @return
     */
    @RequestMapping(value = "sendcode", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO sendCode(String sendto, String action) {
        LOGGER.info("-----sendcode-----");
        // 1,生产验证码
        String smsCode = RandomUtil.getRandomInt(100000, 999999) + "";// 6位随机数字
        // 2,校验用户类型
        int simple = checkUserNameType(sendto);
        // 3,发送邮件
        if (simple == SIMPLE_EMAIL) {
            SimpleMail cn = new SimpleMail();
            // 设置发件人地址、收件人地址和邮件标题
            cn.setAddress(sendto);
            cn.runOrder("您的验证码是:" + smsCode);
        } else if (simple == SIMPLE_PHONE) {
            // 4,发送短信
//            MessageSender.sendAuthCode(sendto, smsCode);
        	String result = MsgSender.sendAuthCode(smsCode, sendto);
        	if (MsgSender.MSG_SEND_RESULT_NG.equals(result)) {
        		LOGGER.info("-----sendcode-----短信发送失败");
        		return ResultTO.newFailResultTO("短信发送失败", null);
			}
        }

        // 5,存储到Map临时缓存对象
        putMapUserTemp(sendto, smsCode);
        // 6,持久化验证码数据
        putDateUserTemp(sendto, smsCode, action);

        LOGGER.info("-----sendcode----验证码是code:【{}】", smsCode);
        return ResultTO.newSuccessResultTO("", null);
    }
    
    /**
     * 校验验证码合法性
     * @author HeWeiwen
     * 2015-10-20
     * @param action (String:register：注册；login：登录；bind：绑定（邮箱、或手机）；forgotpassowrd：找回密码)
     * @param sendto (String:发送对象，手机号或邮箱 )
     * @param code (String:验证码)
     * @return
     */
    @RequestMapping(value = "CheckCode", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO checkCode( String action,String sendto,String code) {
        LOGGER.info("-----CheckCode-----");
        //1,校验验证码的合法性
        ResultTO resultTO = checkCode(sendto, code);
        //2,组织返回参数对象
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("sendto", sendto);
        resultMap.put("action", action);
        resultMap.put("code", code);
        resultTO.setData(resultMap);
        return resultTO;
    }

    private static class CleanTempUserMapTask implements Runnable {

        private CleanTempUserMapTask() {

        }

        @Override
        public void run() {
            for (TempUser data : TEMP_USER_MAP.values()) {
                // 获得当前的毫秒数
                int cTimeSecond = TimeUtil.currentTimeSecond();
                if (cTimeSecond > data.getPtime()) {
                    CasController.LOGGER.info(
                            "清理短信验证码Map值【{}】，清理时间【{}】",
                            new Object[] {
                                    data.getPhone() + "_" + data.getSmsCode(),
                                    TimeUtil.getFormatTime(cTimeSecond,
                                            "yyyy-MM-dd HH:mm:ss") });
                    CasController.TEMP_USER_MAP.remove(data.getPhone() + "_"
                            + data.getSmsCode());
                }
            }
        }
    }

    static {
        CleanTempUserMapTask task = new CleanTempUserMapTask();
        TaskPoolFactory.scheduleAtFixedRate("CleanTempUserMapTask", task, 5L,
                5L, TimeUnit.MINUTES);
    }

    /**
     * 判断用户数据类型
     * 
     * @author HeWeiwen 2015-9-1
     * @param username
     * @return
     */
    private int checkUserNameType(String username) {
        boolean isMatched = true;
        // 1,初始为用户名
        int simple = SIMPLE_NAME;
        // 2,校验用户名是否是邮件
        Pattern regexEmail = Pattern.compile(CHECK_EMAIL);
        Matcher matcherEmail = regexEmail.matcher(username);
        isMatched = matcherEmail.matches();
        if (!isMatched) {
            // 校验用户名是否是手机号码
            Pattern regexPhone = Pattern.compile(CHECK_PHONE);
            Matcher matcherPhone = regexPhone.matcher(username);
            isMatched = matcherPhone.matches();
            if (!isMatched) {
                simple = SIMPLE_NAME;
            } else {
                simple = SIMPLE_PHONE;
            }
        } else {
            simple = SIMPLE_EMAIL;
        }
        return simple;
    }

    /**
     * 校验验证码合法性（公共方法）
     * 
     * @author HeWeiwen 2015-9-6
     * @param code
     * @param resultTO
     * @return
     */
    private ResultTO checkCode(String username, String code) {
        ResultTO resultTO = null;
        // 1,从缓存中获取验证码信息
        TempUser userTemp = TEMP_USER_MAP.get(username + "_" + code);
        // 2,当内存不存在时，从数据库中获得
        if (null == userTemp) {
            Map<String, String> codeMap = new HashMap<String, String>();
            codeMap.put("code", code);
            codeMap.put("sendTo", username);
            CasCode casCode = (CasCode) (casCodeService.selectObjectList(
                    codeMap).size() > 0 ? casCodeService.selectObjectList(
                    codeMap).get(0) : null);
            if (null == casCode) {
                resultTO = ResultTO.newFailResultTO("验证码不存在", null);
            } else if (new Date().getTime() > casCode.getExptime().getTime()) {// 3,当前时间大于验证码过期时间
                resultTO = ResultTO.newFailResultTO("验证码时间(超过120秒)，过期", null);
            }else {
                resultTO = ResultTO.newSuccessResultTO("校验成功", null);
            }
        } else {
            // 4,当前时间大于验证码过期时间
            if (System.currentTimeMillis() > userTemp.getPtime()) {
                resultTO = ResultTO.newFailResultTO("验证码时间(超过120秒)，过期", null);
            }else {
                resultTO = ResultTO.newSuccessResultTO("校验成功", null);
            }
        }
        return resultTO;
    }

    /**
     * 存储到Map临时缓存对象
     * 
     * @author HeWeiwen 2015-9-6
     * @param sendto
     * @param smsCode
     * @return
     */
    private void putMapUserTemp(String sendto, String smsCode) {
        TempUser tempUser = new TempUser();
        // 获得当前的毫秒数
        long cTime = System.currentTimeMillis();
        long pTime = cTime + EXP_TIME;// 加上过期时间120毫秒数
        tempUser.setSmsCode(smsCode);
        tempUser.setPhone(sendto);
        tempUser.setCtime(cTime);
        tempUser.setPtime(pTime);
        // 将验证码对象存入临时缓存中
        TEMP_USER_MAP.put(sendto + "_" + smsCode, tempUser);
    }

    /**
     * 验证码数据持久化
     * 
     * @author HeWeiwen 2015-9-6
     * @param sendto
     * @param smsCode
     * @param action
     */
    private void putDateUserTemp(String sendto, String smsCode, String action) {
        // 1，获得当前的毫秒数
        long cTime = System.currentTimeMillis();
        long pTime = cTime + EXP_TIME;// 加上过期时间120毫秒数
        CasCode code = new CasCode();
        code.setUserid(new Long(0));
        int simple = checkUserNameType(sendto);
        // 2，判断类型，记录对应类型
        if (simple == SIMPLE_EMAIL) {
            code.setType("Email");
        } else if (simple == SIMPLE_PHONE) {
            code.setType("phone");
        }
        code.setAction(action);
        code.setSendTo(sendto);
        code.setCode(smsCode);
        code.setCtime(new Date());
        code.setExptime(new Date(pTime));
        // 将验证码对象插入数据库
        casCodeService.insert(code);
    }

    /**
     * 录入用户信息
     * 
     * @author HeWeiwen 2015-9-17
     * @param request
     * @param casUser
     * @param password
     * @param username
     * @return 扩展参数获取<范例> String gender = request.getParameter("gender") == null
     *         ? "" : request.getParameter("gender"); 扩展字段录入<范例>
     *         casUser.setGender((byte)(Integer.parseInt(gender)));
     */
    private int casUserInsert(CasUser casUser, String password, String username) {
        // 混淆码
        String salt = UUIDService.getUUID().subSequence(0, 6).toString();
        casUser.setSalt(salt);
        casUser.setUsername(username);
        casUser.setCtime(new Date());
        casUser.setStatus((byte) 0);// 用户状态。1：已激活；0：未激活；
        casUser.setIsVerify((byte) 0);// 是否审核。1：是；0：否；
        // 如果是手机号码验证码注册时不设置密码
        if (!"".equals(password)) {
            // 密码进行MD5加密
            String passwordMd5Md5 = StringUtil.md5(StringUtil.md5(password)
                    + salt);
            casUser.setPassword(passwordMd5Md5);
            casUser.setEncrypt("Md5Md5");
        }
        int resultUser = casUserService.insert(casUser);
        Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put(USERNAME, username);
        CasUser userMap = (CasUser) (casUserService.selectObjectList(reqMap)
                .size() > 0 ? casUserService.selectObjectList(reqMap).get(0)
                : null);
        CasUserDetail casUserDetail = new CasUserDetail();
        casUserDetail.setUserid(userMap.getUserid());
        casUserDetailService.insert(casUserDetail);

        return resultUser;
    }

    /**
     * 登陆完成后续相关操作
     * 
     * @author HeWeiwen 2015-9-17
     * @param casUser
     * @return
     */
    private ResultTO loginContinue(CasUser casUser) {
        // 登录成功，返回tokenId
        Map<String, String> mapResult = new HashMap<String, String>();
        mapResult.put(USERID, casUser.getUserid().toString());
        mapResult.put(USERNAME, casUser.getUsername());
        mapResult.put("nickname", casUser.getNickname());
        mapResult.put("avatar", casUser.getAvatar());
        mapResult.put("tokenId",
                tokenService
                        .createTokenByUserId(casUser.getUserid().toString())
                        .getTokenId());
        return ResultTO.newSuccessResultTO("登录成功", mapResult);
    }

    /**
     * 当用户为空时的相关操作
     * 
     * @author HeWeiwen 2015-9-17
     * @param request
     * @param username
     * @param code
     * @param password
     * @return
     */
    private ResultTO loginCheckUserIsNull(HttpServletRequest request,
            String username, String code, String password) {
        ResultTO resultTO = null;
        // 校验用户类型
        int simple = checkUserNameType(username);
        // 查询无用户（手机号码加验证码），就注册
        if (simple == SIMPLE_PHONE && !"".equals(code)) {
            CasUser casUser = new CasUser();
            casUser.setPhone(username);
            // 用户信息录入
            int resultUser = casUserInsert(casUser, password, username);
            if (resultUser == 1) {
                Map<String, String> userMap = new HashMap<String, String>();
                userMap.put(USERNAME, username);
                // 根据用户名查询用户对象
                CasUser user = (CasUser) casUserService.selectObjectList(
                        userMap).get(0);
                // 注册后执行登录超赞，返回tokenId
                resultTO = loginContinue(user);
            } else {
                resultTO = ResultTO.newSuccessResultTO("用户注册失败（登陆时）", null);
            }
        } else {
            resultTO = ResultTO.newFailResultTO("用户不存在", null);
        }
        return resultTO;
    }

    /**
     * 注册成功后相关逻辑
     * 
     * @author HeWeiwen 2015-9-17
     * @param simple
     * @param username
     * @param code
     * @return
     */
    private ResultTO signupContinue(Integer simple, String username, String code) {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put(USERNAME, username);
        Map<String, Object> respUser = casUserService.selectMapList(reqMap)
                .size() > 0 ? casUserService.selectMapList(reqMap).get(0)
                : null;
        if (null == respUser) {
            ResultTO.newFailResultTO("注册失败！用户返回参数查询失败", "");
        }
        // 注册成功，返回tokenId
        Map<String, String> mapResult = new HashMap<String, String>();
        mapResult.put("simple", simple.toString());
        mapResult.put(USERNAME, username);
        mapResult.put("code", code);
        mapResult.put(USERID, respUser.get(USERID).toString());
        mapResult.put("tokenId",tokenService.createTokenByUserId(
                        respUser.get(USERID).toString()).getTokenId());
        return ResultTO.newSuccessResultTO("注册成功！", mapResult);
    }

    /**
     * 修改用户信息
     * 
     * @author liuyt 2015-10-15
     * @param user
     * @return resultTO
     */
    @RequestMapping(value = "updateInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO casUserUpdateInfo(CasUser user) {
        ResultTO resultTO = null;
        user.setUserid((long) getUserid());
        user.setMtime(Calendar.getInstance().getTime());
        int ret = casUserService.update(user);
        if (ret == 1) {
            resultTO = ResultTO.newSuccessResultTO("修改成功！", null);
        } else {
            resultTO = ResultTO.newFailResultTO("修改失败！", null);
        }
        return resultTO;
    }

    /**
     * 获取用户信息
     * 
     * @author liuyt 2015-10-15
     * @param user
     * @return resultTO
     */
    @RequestMapping(value = "getUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public CasUser casUserGetInfo() {
        long userId = getUserid();
        Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("userid", userId);
        CasUser user = casUserService.selectObject(parameter);
        return user;
    }

    /**
     * 修改密码
     * 
     * @author liuyt 2015-10-15
     * @param pwdVo
     * @return resultTO
     */
    @RequestMapping(value = "resetPassword", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO casUserUpdatePwd(UpdatePwdTO pwdTO) {
        pwdTO.setUserId((long) getUserid());
        return casUserService.updatePwd(pwdTO);
    }

    /**
     * 修改头像
     * 
     * @author liuyt 2015-10-15
     * @param avatars
     * @return resultTO
     */
    @RequestMapping(value = "uploadAvatar", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO casUserUploadAvatar(MultipartFile[] avatars) {
        ResultTO resultTO = null;
        Map<String, Object> uploadResultMap = null;
        try {
            uploadResultMap = uploadImage(avatars,
                    BaseConstant.USER_AVATAR_IMAGE);
        } catch (Exception e) {
            LOGGER.error("Exception:" + e);
            e.printStackTrace();
            return ResultTO.newFailResultTO("上传失败!", null);
        }
        if ((Boolean) uploadResultMap.get("flag")) {
            long userId = getUserid();
            Map<String, Object> parameter = new HashMap<String, Object>();
            parameter.put("userid", userId);
            CasUser user = casUserService.selectObject(parameter);
            user.setAvatar(uploadResultMap.get("imageUrl").toString());
            user.setMtime(Calendar.getInstance().getTime());
            int ret = casUserService.update(user);
            if (ret == 1) {
                resultTO = ResultTO.newSuccessResultTO("上传成功!", null);
            } else {
                resultTO = ResultTO.newFailResultTO("上传失败!", null);
            }
        }
        return resultTO;
    }

    /**
     * 新增【银行卡信息表】数据
     * @param casUserBank
     * @return ResultTO
     * @author AutoCode
     * @date 2016-08-11 14:25:41
     */
    @RequestMapping(value = "addCasUserBank", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO addCasUserBank(@ModelAttribute CasUserBank casUserBank, BindingResult result) {
        try {
            casUserBankService.insert(casUserBank);
            LOGGER.info("用户【{}】添加银行卡信息表数据【{}】成功", new Object[] { this.getUserid(), casUserBank } );
        } catch (Exception e) {
            LOGGER.error("用户【{}】添加银行卡信息表数据【{}】失败 Exception:【{}】", new Object[] { this.getUserid(), casUserBank, e });
            return ResultTO.newFailResultTO("添加失败", null);
        }
        return ResultTO.newSuccessResultTO("添加成功", null);
    }
    
    /**
     * 获取用户银行信息列表
     * @return
     */
    @RequestMapping(value = "getUserBankInfo", method = RequestMethod.POST)
    @ResponseBody
    public List<CasUserBank> getUserBankInfo() {
        List<CasUserBank> casUserBanks = casUserBankService.getCasUserBanks(getUserid());
        if (casUserBanks!=null && casUserBanks.size()>0) {
			return casUserBanks;
		}
        return null;
    }
    
}

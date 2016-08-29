package com.bluemobi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 信息处理类
 * 
 * @author hezhanwei
 */
public class MsgSender {
    /** 日志生成器 */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(MsgSender.class);

    private MsgSender() {
    }

    /** 短信发送失败标识 */
    public static final String MSG_SEND_RESULT_NG = "001";
    /** 短信发送成功标识 */
    public static final String MSG_SEND_RESULT_OK = "002";
    /** 短信返回状态 */
    public static final String MSG_SEND_STATUS = "0";

    /**
     * 发送短信
     * @param sendCode 验证码
     * @param mobileNumber 手机号,多个手机号码以英文逗号隔开。
     * @return
     */
    public static String sendAuthCode(String sendCode, String mobileNumber) {
    	
    	String spID = MessageSendConfig.MSG_SPID;
    	String password = MessageSendConfig.MSG_PASSWORD;
    	String accessCode = MessageSendConfig.MSG_ACCESSCODE;
    	
    	String content = MessageSendConfig.MSG_CONTENT_TEMPLATE;
    	content = content.replaceAll(MessageSendConfig.MSG_SENDCODE, sendCode);
    	
    	//短信提交
    	MsgSendClient msgSendClient = new MsgSendClient();
    	String submitresult=msgSendClient.Submit(spID,
    			password, accessCode, content, mobileNumber);
    	
    	String status = submitresult.substring(0, 1);//截取字符状态
    	 
    	String sendFlg = MSG_SEND_RESULT_OK;// 短信发送成功标识
    	if (!MSG_SEND_STATUS.equals(status)) {
    		sendFlg = MSG_SEND_RESULT_NG;
    		LOGGER.info("----sendAuthCode---短信发送失败:【{}】",submitresult);
		}
    	return sendFlg;
    }
}

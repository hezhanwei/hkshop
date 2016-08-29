package com.bluemobi.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appcore.model.AbstractObject;
import com.bluemobi.push.jpush.JpushConfig;

/**
 * 短信信息加载
 * @author hezhanwei
 *
 */
public class MessageSendConfig extends AbstractObject{

	
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JpushConfig.class);

    private static String fileName = "message.properties";
    
    private static Properties p = getProperties(fileName);
    
    
    /**短信账户ID*/
    public static final String MSG_SPID = p.getProperty("MSG_SPID");
    /**短信账户密码*/
    public static final String MSG_PASSWORD = p.getProperty("MSG_PASSWORD");
    /**短信下发号码*/
    public static final String MSG_ACCESSCODE = p.getProperty("MSG_ACCESSCODE");
    /**短信内容模板*/
    public static final String MSG_CONTENT_TEMPLATE = p.getProperty("MSG_CONTENT_TEMPLATE");
    /**替换内容*/
    public static final String MSG_SENDCODE = p.getProperty("MSG_SENDCODE");
    
    /**
     * 
     * @param fileName
     * @return
     */
    public static Properties getProperties(String fileName){
        
        LOGGER.info("开始读取文件【{}】...", new Object[]{fileName} );
        
        InputStream is = JpushConfig.class.getClassLoader().getResourceAsStream(fileName);
        Properties properties=new Properties();
        try {
            properties.load(is);
            if(is!=null){
                is.close();
            }
        } catch (IOException e) {
            LOGGER.error("Exception:【{}】"+e);
        }
        
        LOGGER.info("读取文件【{}】结束...", new Object[]{fileName} );
        
        return properties;
    }

}

package com.bluemobi.push.jpush;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appcore.model.AbstractObject;

/**
 * 极光推送配置加载
 * @Description
 * @author haojian 309444359@qq.com
 * @date 2015-10-16 下午5:42:41 
 *
 */
public class JpushConfig extends AbstractObject{
    
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(JpushConfig.class);

    private static String fileName = "jpush.properties";
    
    private static Properties p = getProperties(fileName);
    
    
    /**应用唯一标示*/
    public static final String APP_KEY = p.getProperty("APP_KEY");
    /**秘钥*/
    public static final String MASTER_SECRET = p.getProperty("MASTER_SECRET");
    /**最大重试次数*/
    public static final int MAX_RETRY_TIMES = Integer.parseInt(p.getProperty("MAX_RETRY_TIMES"));
    
    /**
     * 读取propertity文件的方法
     * @author haojian
     * Apr 27, 2012 3:00:56 PM
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

package com.winhong.plugins.cicd.uapconfig;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winhong.plugins.cicd.filter.UAPLoginFilter;
import com.winhong.uap.sdk.model.User;

public class SessionUtils {
	final static Logger logger =LoggerFactory.getLogger(UAPLoginFilter.class);
	private static final String CONFIG_FILE = "/WinGrow/config/wingrowUAP.properties";
    private static final String CURRENT_USER      = "current_user";                      // 用户信息
    private static Properties prop = null;
    
    static{
        prop = new Properties();
    	try {
			prop.load(SessionUtils.class.getResourceAsStream(CONFIG_FILE));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public static Properties getFilterProp(){
    	return prop;
    }
    public static User getUser() throws Exception{
    	
    	System.out.println(prop.getProperty("casServerLoginUrl"));
		return null;
    }

    
}

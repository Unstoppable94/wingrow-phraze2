package com.winhong.plugins.cicd.uapconfig;

import java.io.IOException;
import java.util.Properties;

import com.winhong.uap.sdk.RestFactory;
import com.winhong.uap.sdk.api.RestClient.RestClientV1;

public class UapUtils {
	private static final String UAP_CLIENT_CONFIG_FILE = "/WinGrow/config/uapClient.properties";
	public static UapUtils uapUtils = new UapUtils();
	public static RestClientV1 rest;
	private static Properties pro;
	static{
        pro = new Properties();
    	try {
			pro.load(SessionUtils.class.getResourceAsStream(UAP_CLIENT_CONFIG_FILE));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private UapUtils() {}
	
	public static RestClientV1 getRestClientV1() {
		
        String url = (String)pro.get("uap_url");
        System.out.println(url+ "==========");
        String account = (String)pro.get("uap_account");
        String password = (String)pro.get("uap_password");
        try {
			//String decrypt = EncryptUntil.decrypt(password, "CSC201609!@#");
			rest = RestFactory.builderV1()
			        .endpoint(url).useNonStrictSSLClient(true)
					//.credentials(account, "secret")
					.authenticate();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return rest;
	}

}

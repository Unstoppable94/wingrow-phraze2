package com.winhong.plugins.cicd.system;

import com.google.gson.annotations.Expose;

public class RancherConfig {

	@Expose
	private String serverUrl;
	
	@Expose
	private String accessKey;
	
	@Expose
	private String secureKey;
	
	@Expose
	private String envID;

	
	 


	public String getEnvID() {
		return envID;
	}



	public void setEnvID(String envID) {
		this.envID = envID;
	}



	public static String getCliJsonTemplate() {
		return cliJsonTemplate;
	}



	public static void setCliJsonTemplate(String cliJsonTemplate) {
		RancherConfig.cliJsonTemplate = cliJsonTemplate;
	}



	public String getServerUrl() {
		return serverUrl;
	}



	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}



	public String getAccessKey() {
		return accessKey;
	}



	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}



	public String getSecureKey() {
		return secureKey;
	}



	public void setSecureKey(String secureKey) {
		this.secureKey = secureKey;
	}



	public RancherConfig() {
		super();
 	}



	public RancherConfig(String serverUrl, String accessKey, String secureKey,String envid) {
		super();
		this.serverUrl = serverUrl;
		this.accessKey = accessKey;
		this.secureKey = secureKey;
		this.envID=envid;
	}
	
	//{"accessKey":"BEFFE0ACF8339FFB5861","secretKey":"UDbYKhPMY8eHDASoEnLF9kQCX96K6QhuENWgZxam",
	//"url":"http://192.168.217.225:8080/v2-beta/schemas","environment":"1a5"}
	
	private static String cliJsonTemplate="{\"accessKey\":\"#accessKey\",\"secretKey\":\"#secretKey\",\"url\":\"#url/v2-beta/schemas\",\"environment\":\"#envid\"}";
	
	public String genCLiJson(){
		if (this.accessKey==null)
			return cliJsonTemplate;
		String temp=cliJsonTemplate.replace("#accessKey", this.accessKey);
		temp=temp.replace("#secretKey", this.secureKey);
		temp=temp.replace("#url", this.serverUrl);
		temp=temp.replace("#envid", this.envID);
		return temp;
		
	}

}

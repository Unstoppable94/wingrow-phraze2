package com.winhong.plugins.cicd.system;

import com.google.gson.annotations.Expose;


public class SonarConfig {

	@Expose
	private String sonarUrl;
	
	@Expose
	private String user;
	
	@Expose
	private String password;
	
	public SonarConfig() {
		super();
 	}

	public String getSonarUrl() {
		return sonarUrl;
	}

	public void setSonarUrl(String sonarUrl) {
		this.sonarUrl = sonarUrl;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public SonarConfig(String sonarUrl, String user, String password) {
		super();
		this.sonarUrl = sonarUrl;
		this.user = user;
		this.password = password;
	}
	


	
}

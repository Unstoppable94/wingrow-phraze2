package com.winhong.plugins.cicd.system;

import com.google.gson.annotations.Expose;


public class GitLabConfig {

	@Expose
	private String url;
	
	@Expose
	private String user;
	
	@Expose
	private String password;
	
	public GitLabConfig() {
		super();
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


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}

	 
	
}

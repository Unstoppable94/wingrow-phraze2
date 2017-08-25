package com.winhong.plugins.cicd.system;

import com.google.gson.annotations.Expose;


public class JenkinsConfig {

	@Expose
	private String url;
	
	@Expose
	private String user;
	
	@Expose
	private String password;
	
	@Expose
	private JdkConfig jdk;
	
	@Expose
	private MavenConfig maven;
	
	public JenkinsConfig() {
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
		//http://admin:admin@192.168.101.6:8080
		if (url.indexOf("@") <0) {
				int i=url.indexOf("//");
				String u=url.substring(0,i+2)+user+":"+password+"@"+url.substring(i+2);
				url=u;
		}
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public JdkConfig getJdk() {
		return jdk;
	}


	public void setJdk(JdkConfig jdk) {
		this.jdk = jdk;
	}


	public MavenConfig getMaven() {
		return maven;
	}


	public void setMaven(MavenConfig maven) {
		this.maven = maven;
	}

	 
	
}

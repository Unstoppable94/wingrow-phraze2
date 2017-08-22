package com.winhong.plugins.cicd.system;

import com.google.gson.annotations.Expose;

public class SMTPConfig {
	
	
   @Expose 
   private boolean ssl=false;
   
   @Expose 
   private int port=25;
   
   
	@Expose
	private String host;
	
	@Expose
	private    String user;
	
	@Expose
	private  String password;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
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

	public SMTPConfig() {
		super();
 	}

	public boolean isSsl() {
		return ssl;
	}

	public void setSsl(boolean ssl) {
		this.ssl = ssl;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	

}

package com.winhong.plugins.cicd.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.stream.JsonReader;
import com.winhong.plugins.cicd.tool.Tools;

public class RegistryConfig {

	@Expose
	private String server;

	@Expose
	private String auth;

	@Expose
	private String email;

	@Expose
	private boolean reboot;

	@Expose
	private boolean secure;

	public boolean isSecure() {
		return secure;
	}

	public void setSecure(boolean secure) {
		this.secure = secure;
	}

	public RegistryConfig() {
		super();
	}

	public RegistryConfig(String server, String auth, String email, boolean b) {
		super();
		this.server = server;
		this.auth = auth;
		this.email = email;
		this.secure=b;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isReboot() {
		return reboot;
	}

	public void setReboot(boolean reboot) {
		this.reboot = reboot;
	}

}

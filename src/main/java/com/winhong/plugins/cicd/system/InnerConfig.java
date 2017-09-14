package com.winhong.plugins.cicd.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.stream.JsonReader;
import com.winhong.plugins.cicd.tool.Tools;


 
 
public class InnerConfig {

	private final static String filename="WinGrow/config/WinhongInner.json";
	private static InnerConfig config;
	
	@Expose
	private String JenkinsDir;
	
	@Expose
	private GitLabConfig gitlab=new GitLabConfig();
	
	//@Expose
	//private JenkinsConfig jenkins=new JenkinsConfig();
	@Expose
	int PasswordExprired=15;
	
	
	@Expose
	private ArrayList<JdkConfig> jdk=new ArrayList<JdkConfig>();
	
	@Expose
	private ArrayList<MavenConfig> maven=new ArrayList<MavenConfig>();
	
	@Expose
	EmailTemplate successTemplate ;
	
	@Expose
	EmailTemplate failedTemplate  ;
	
	@Expose
	EmailTemplate recoveryTemplate ;
	
	
	/**
	 * 数据目录，需要进行GIT同步的目录
	 */
	@Expose
	private String  dataDir;
	
	
	public ArrayList<JdkConfig> getJdk() {
		return jdk;
	}

	public void setJdk(ArrayList<JdkConfig> jdk) {
		this.jdk = jdk;
	}

	public ArrayList<MavenConfig> getMaven() {
		return maven;
	}

	public void setMaven(ArrayList<MavenConfig> maven) {
		this.maven = maven;
	}

	public InnerConfig() {
		super();
	}
	
	public static void init() throws IOException {
		
		// inner config save without encrypt
		 config=(InnerConfig) Tools.objectFromJsonResource(filename,InnerConfig.class,false);
		 
		 //replace with system environments
		 //dataDir":"/Users/xiehq/git/wingrow-test/Data",
		 String WINGROW_DATA = System.getenv("WINGROW_DATA");
		 if (WINGROW_DATA!=null && WINGROW_DATA.isEmpty() == false) 
			 config.setDataDir(WINGROW_DATA);
	}
	public static InnerConfig defaultConfig() throws IOException{
		if (config==null) {
			 init();
			  
		}
		return config;
	}
	
    
	 
	public EmailTemplate getSuccessTemplate() {
		return successTemplate;
	}

	public EmailTemplate getFailedTemplate() {
		return failedTemplate;
	}

	public EmailTemplate getRecoveryTemplate() {
		return recoveryTemplate;
	}

	public GitLabConfig getGitlab() {
		return gitlab;
	}

	public void setGitlab(GitLabConfig gitlab) {
		this.gitlab = gitlab;
	}

//	public JenkinsConfig getJenkins() {
//		return jenkins;
//	}
//
//	public void setJenkins(JenkinsConfig jenkins) {
//		this.jenkins = jenkins;
//	}

	public   String getJenkinsDir() {
		return JenkinsDir;
	}

	public   void setJenkinsDir(String jenkinsDir) {
		JenkinsDir = jenkinsDir;
	}

	public static String getFilename() {
		return filename;
	}

	public String getDataDir() {
		return dataDir;
	}

	public void setDataDir(String dataDir) {
		this.dataDir = dataDir;
	}

	public void setSuccessTemplate(EmailTemplate successTemplate) {
		this.successTemplate = successTemplate;
	}

	public void setFailedTemplate(EmailTemplate failedTemplate) {
		this.failedTemplate = failedTemplate;
	}

	public void setRecoveryTemplate(EmailTemplate recoveryTemplate) {
		this.recoveryTemplate = recoveryTemplate;
	}

	public static InnerConfig getConfig() {
		return config;
	}

	public static void setConfig(InnerConfig config) {
		InnerConfig.config = config;
	}

	public int getPasswordExprired() {
		return PasswordExprired;
	}

	public void setPasswordExprired(int passwordExprired) {
		PasswordExprired = passwordExprired;
	}
	
	
 

}

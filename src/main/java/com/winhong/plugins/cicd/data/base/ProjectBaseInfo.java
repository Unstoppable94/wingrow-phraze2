package com.winhong.plugins.cicd.data.base;

import java.util.Date;

import com.google.gson.annotations.Expose;

public class ProjectBaseInfo {

	/**
	 * 内部ID,不可修改，对应Jenkins的
	 */
	@Expose
	private String id;
	
	/**
	 * 显示名称，用于web显示，可以修改
	 */
	@Expose
	private String name;

	@Expose
	private String description;
	
	@Expose
	private String SCMUrl;
	
	@Expose
	private String SCMUser;
	@Expose
	private String SCMPassword;
	
	@Expose
	private String SCMBranch="master";
	
	@Expose
	private String SCMTYPE="git"; //git or svn
	
	@Expose
	private String trigger="manual";
	
	@Expose
	private String triggerProperty;
	
	
	@Expose
	private String exraProperties="MAVEN_OPTS=-Xms256m -Xmx1512m";
	
 
	@Expose
	private long createTime;
	
	@Expose
	private long lastModifyTime;
	
	/**
	 * 最后修改者，从session获取？
	 */
	@Expose String lastModifyer;
	
	/**
	 * 构建失败后发送的邮件地址
	 */
	@Expose
	private String mailOnfail;
	
	
	/**
	 * 构建成功后发送的邮件地址
	 */
	@Expose
	private String mailOnSuccess;
	
	/**
	 * 构建失败后，首次成功后发送的邮件地址
	 */
	@Expose
	private String mailOnReovery;
	
	
	
	/**
	 * 归属项目组
	 */
	@Expose
	private String groupId="default";
	
	/**
	 * 默认最大执行时间，默认30分钟
	 */
	@Expose
	private int maxExcutiontime=30;
	
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSCMUrl() {
		return SCMUrl;
	}

	public void setSCMUrl(String sCMUrl) {
		SCMUrl = sCMUrl;
	}

	public String getSCMUser() {
		return SCMUser;
	}

	public void setSCMUser(String sCMUser) {
		SCMUser = sCMUser;
	}

	 

	public String getSCMPassword() {
		return SCMPassword;
	}

	public void setSCMPassword(String sCMPassword) {
		SCMPassword = sCMPassword;
	}

	public String getSCMBranch() {
		return SCMBranch;
	}

	public void setSCMBranch(String sCMBranch) {
		SCMBranch = sCMBranch;
	}

	 
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTrigger() {
		return trigger;
	}

	public void setTrigger(String trigger) {
		//TODO CHECK
		this.trigger = trigger;
	}

	public String getExraProperties() {
		return exraProperties;
	}

	public void setExraProperties(String exraProperties) {
		this.exraProperties = exraProperties;
	}

	 
 

	 

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(long lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getSCMTYPE() {
		return SCMTYPE;
	}

	public void setSCMTYPE(String sCMTYPE) {
		SCMTYPE = sCMTYPE;
	}

	public String getLastModifyer() {
		return lastModifyer;
	}

	public void setLastModifyer(String lastModifyer) {
		this.lastModifyer = lastModifyer;
	}

	public String getMailOnfail() {
		return mailOnfail;
	}

	public void setMailOnfail(String mailOnfail) {
		this.mailOnfail = mailOnfail;
	}

	public String getMailOnSuccess() {
		return mailOnSuccess;
	}

	public void setMailOnSuccess(String mailOnSuccess) {
		this.mailOnSuccess = mailOnSuccess;
	}

	public String getMailOnReovery() {
		return mailOnReovery;
	}

	public void setMailOnReovery(String mailOnReovery) {
		this.mailOnReovery = mailOnReovery;
	}

	public int getMaxExcutiontime() {
		return maxExcutiontime;
	}

	public void setMaxExcutiontime(int maxExcutiontime) {
		this.maxExcutiontime = maxExcutiontime;
	}

	public ProjectBaseInfo() {
		super();
		if (id==null)
		id=genProjectid();
 	}

	public static String genProjectid(){
		return "pro"+System.currentTimeMillis();
	}
	public String getTriggerProperty() {
		return triggerProperty;
	}

	public void setTriggerProperty(String triggerProperty) {
		this.triggerProperty = triggerProperty;
	}

	 

	 
	 

	
}

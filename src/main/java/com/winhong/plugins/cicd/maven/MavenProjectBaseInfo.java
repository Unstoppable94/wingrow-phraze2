package com.winhong.plugins.cicd.Maven;

 
import com.google.gson.annotations.Expose;
import com.winhong.plugins.cicd.data.base.ProjectBaseInfo;
  
public class MavenProjectBaseInfo extends ProjectBaseInfo {
	
	
	/**
	 * 必须是系统已经配置的Maven ID
	 */
	@Expose
	private String mavenId;
	
	/**
	 * 必须是系统已经配置的jdk
	 */
	@Expose
	private String jdk;

	
	/**
	 * 暂未使用
	 */
	@Expose
	private String cloud;
	
	
	/**
	 * 暂未使用
	 */
	@Expose
	private String imageName;
	
	public String getMavenId() {
		return mavenId;
	}

	public void setMavenId(String mavenId) {
		this.mavenId = mavenId;
	}

	public String getCloud() {
		return cloud;
	}

	public void setCloud(String cloud) {
		//TODO
		this.cloud = cloud;
	}

	 
	public String getJdk() {
		return jdk;
	}

	public void setJdk(String jdk) {
		//TODO
		this.jdk = jdk;
	}

	
	
	
	public MavenProjectBaseInfo() {
		super();
 	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
 
 
}

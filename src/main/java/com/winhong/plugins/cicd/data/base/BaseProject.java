package com.winhong.plugins.cicd.data.base;

 
public class BaseProject {

	private ProjectBaseInfo baseInfo = new ProjectBaseInfo();

	public BaseProject() {
		super();
	}

	public ProjectBaseInfo getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(ProjectBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	
}

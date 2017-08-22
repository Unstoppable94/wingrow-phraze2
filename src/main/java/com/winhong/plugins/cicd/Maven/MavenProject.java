package com.winhong.plugins.cicd.Maven;

import com.google.gson.annotations.Expose;
import com.winhong.plugins.cicd.data.base.BaseProject;

public class MavenProject extends BaseProject{

 
	@Expose
	private MavenProjectBaseInfo baseInfo =new MavenProjectBaseInfo();
	
	@Expose
	private MavenWorkflow workflow=new MavenWorkflow();

	public MavenProjectBaseInfo getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(MavenProjectBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public MavenWorkflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(MavenWorkflow workflow) {
		this.workflow = workflow;
	}

	public MavenProject() {
		super();
 	}
	
	
	
	

}

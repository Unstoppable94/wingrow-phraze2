package com.winhong.plugins.cicd.docker;

import com.winhong.plugins.cicd.data.base.BaseProject;
 import com.winhong.plugins.cicd.system.ProjectType;
import com.winhong.plugins.cicd.tool.Tools;

public class TranditionalDockerProject extends BaseProject{

	
 
	
 
//	@Expose
//private MavenProjectBaseInfo baseInfo =new MavenProjectBaseInfo();
	
	//@Expose
	//private MavenWorkflow workflow=new MavenWorkflow();

	 

	 

	public TranditionalDockerProject() {
		
		super();
		this.getBaseInfo().setProjectType(ProjectType.TraditionalDocker);
 		
		this.setWorkflow(new TranditionalWorkflow());
		
 	}

	@Override
	public String genJson() {
			return Tools.getJson(this);
	}
	
}

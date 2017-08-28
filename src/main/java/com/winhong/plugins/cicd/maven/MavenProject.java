package com.winhong.plugins.cicd.maven;

import com.google.gson.annotations.Expose;
import com.winhong.plugins.cicd.data.base.BaseProject;
import com.winhong.plugins.cicd.system.ProjectType;
import com.winhong.plugins.cicd.tool.Tools;

public class MavenProject extends BaseProject{

	
 
	
 
//	@Expose
//private MavenProjectBaseInfo baseInfo =new MavenProjectBaseInfo();
	
	//@Expose
	//private MavenWorkflow workflow=new MavenWorkflow();

	 

	 

	public MavenProject() {
		
		super();
		this.getBaseInfo().setProjectType(ProjectType.MavenProject);
		this.getBaseInfo().setExraProperties("MAVEN_OPTS=-Xms256m -Xmx1512m");   
		
		this.setWorkflow(new MavenWorkflow());
		
 	}

	@Override
	public String genJson() {
			return Tools.getJson(this);
	}
	

}

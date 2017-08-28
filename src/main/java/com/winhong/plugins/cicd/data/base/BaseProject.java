package com.winhong.plugins.cicd.data.base;

import com.google.gson.annotations.Expose;
import com.winhong.plugins.cicd.system.ProjectType;
import com.winhong.plugins.cicd.tool.Tools;

public abstract class BaseProject {

	@Expose
	private ProjectBaseInfo baseInfo = new ProjectBaseInfo();
	
	@Expose
	private Workflow workflow;

	public BaseProject() {
		super();
	}

	public ProjectBaseInfo getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(ProjectBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	
	public static String NewProject() throws InstantiationException, IllegalAccessException {
		return NewProject(ProjectType.getDefaultType());
	}
	
	public static String NewProject(String type) throws InstantiationException, IllegalAccessException {
		Class<BaseProject> cls=ProjectType.getClass(type);
		return Tools.getJson(cls.newInstance().genJson());
	}
	
	public static String getInitWorkflow(String type) throws InstantiationException, IllegalAccessException {
		Class<BaseProject> cls=ProjectType.getClass(type);
		BaseProject base=cls.newInstance();
		return Tools.getJson(base.workflow);
	}

	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}
	
	public abstract String genJson() ;
	
}

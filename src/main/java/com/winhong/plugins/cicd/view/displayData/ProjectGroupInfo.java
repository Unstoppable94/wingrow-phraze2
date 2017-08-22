package com.winhong.plugins.cicd.view.displayData;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.winhong.plugins.cicd.view.innerData.ProjectStatusStat;

public class ProjectGroupInfo{
	
	@Expose
	ProjectStatusStat stat;
	
	@Expose
	ArrayList<JobView> projects;
	
	public ProjectGroupInfo() {
		super();
	}

	public ArrayList<JobView> getProjects() {
		return projects;
	}
	public void setProjects(ArrayList<JobView> projects) {
		this.projects = projects;
	}
	public String getName() {
		return name;
	}
	@Expose
	private String name;
	
	@Expose
	private String description;
	
	@Expose
	private long createtime;
	
	@Expose
	private long lastModifyTime;
	
	public ProjectStatusStat getStat() {
		return stat;
	}
	public void setStat(ProjectStatusStat stat) {
		this.stat = stat;
	}
	 
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getCreatetime() {
		return createtime;
	}
	public void setCreatetime(long createtime) {
		this.createtime = createtime;
	}
	public long getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(long lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	 

	
}
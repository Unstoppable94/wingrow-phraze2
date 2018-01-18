package com.winhong.plugins.cicd.view.displayData;

import com.google.gson.annotations.Expose;
import com.winhong.plugins.cicd.view.innerData.Artifact;

public class JobView {

	public JobView() {
		// TODO Auto-generated constructor stub
	}

	@Expose
	private String id;
	
	@Expose String name;
	
	//项目类型ID判断，流水线搜索
	@Expose
	private String typeId;

	@Expose
	private String projectUrl;

	@Expose
	private String status;

	@Expose
	private long lastBuild;

	@Expose
	private long duration;

	@Expose
	private String artifact;

	@Expose
	private String imageCmd;

	/**
	 * ProjectType ,defined in class:ProjectType ,add in phase 2
	 */
	@Expose
	private String projectType;
	
	
	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProjectUrl() {
		return projectUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProjectUrl(String projectUrl) {
		this.projectUrl = projectUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getLastBuild() {
		return lastBuild;
	}

	public void setLastBuild(long lastBuild) {
		this.lastBuild = lastBuild;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	 

	public String getArtifact() {
		return artifact;
	}

	public void setArtifact(String artifact) {
		this.artifact = artifact;
	}

	public String getImageCmd() {
		return imageCmd;
	}

	public void setImageCmd(String imageCmd) {
		this.imageCmd = imageCmd;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getTypeId() {
		return typeId;
	}
}

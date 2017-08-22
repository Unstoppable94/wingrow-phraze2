package com.winhong.plugins.cicd.view.innerData;

import java.util.Date;

import com.google.gson.annotations.Expose;

public class RssBuild {

	@Expose
	private String project;
	
	@Expose
	private int number;
	@Expose
	private String buildLink;
	@Expose
	private Date published;
	@Expose
	private Date updated;
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getBuildLink() {
		return buildLink;
	}
	public void setBuildLink(String buildLink) {
		this.buildLink = buildLink;
	}
	public Date getPublished() {
		return published;
	}
	public void setPublished(Date published) {
		this.published = published;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	public RssBuild() {
		super();
 	}
	
	
	public RssBuild(String project, int number, String buildLink,
			Date published, Date updated) {
		super();
		this.project = project;
		this.number = number;
		this.buildLink = buildLink;
		this.published = published;
		this.updated = updated;
	}
	
	
	
 

}

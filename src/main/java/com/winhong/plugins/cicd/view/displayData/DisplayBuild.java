package com.winhong.plugins.cicd.view.displayData;

import com.google.gson.annotations.Expose;


/**
 * builds 列表信息类，用于控制json
 * 
 * @author xiehuiqiang
 *
 */
/**
 * @author xiehuiqiang
 *
 */
public class DisplayBuild {

	@Expose
	public long number;
	@Expose
	public long buildDate;
	@Expose
	public String status;
	@Expose
	public String logUrl;
	@Expose
	public long duration;
	@Expose
	public String artifactUrl;
	@Expose
	public String imageUrl;

	@Expose
	public String lastStage;

	@Expose
	public String lastStageStatus;

	 

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public long getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(long buildDate) {
		this.buildDate = buildDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLogUrl() {
		return logUrl;
	}

	public void setLogUrl(String logUrl) {
		this.logUrl = logUrl;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getArtifactUrl() {
		return artifactUrl;
	}

	public void setArtifactUrl(String artifactUrl) {
		this.artifactUrl = artifactUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getLastStage() {
		return lastStage;
	}

	public void setLastStage(String lastStage) {
		this.lastStage = lastStage;
	}

	public String getLastStageStatus() {
		return lastStageStatus;
	}

	public void setLastStageStatus(String lastStageStatus) {
		this.lastStageStatus = lastStageStatus;
	}

	public DisplayBuild() {
		super();
 	}

}
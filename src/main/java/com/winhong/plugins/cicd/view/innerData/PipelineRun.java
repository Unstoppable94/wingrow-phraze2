package com.winhong.plugins.cicd.view.innerData;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class PipelineRun {

	@Expose
	private String name;
	@Expose
	private int id;

	/**
	 * FAILED SUCCESS IN_PROGRESS PAUSED_PENDING_INPUT
	 */
	@Expose
	private String status;

	@Expose
	private String startTimeMillis;
	
	/**
	 * 不一定有值
	 */
	@Expose
	private String endTimeMillis;
	
	@Expose
	private String durationMillis;
	
	

	@Expose
	private String  queueDurationMillis;

	@Expose
	private String pauseDurationMillis;
	
	@Expose
	private ArrayList<Stage> stages=new ArrayList<Stage>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStartTimeMillis() {
		return startTimeMillis;
	}

	public void setStartTimeMillis(String startTimeMillis) {
		this.startTimeMillis = startTimeMillis;
	}

	public String getEndTimeMillis() {
		return endTimeMillis;
	}

	public void setEndTimeMillis(String endTimeMillis) {
		this.endTimeMillis = endTimeMillis;
	}

	public String getDurationMillis() {
		return durationMillis;
	}

	public void setDurationMillis(String durationMillis) {
		this.durationMillis = durationMillis;
	}

	public String getQueueDurationMillis() {
		return queueDurationMillis;
	}

	public void setQueueDurationMillis(String queueDurationMillis) {
		this.queueDurationMillis = queueDurationMillis;
	}

	public String getPauseDurationMillis() {
		return pauseDurationMillis;
	}

	public void setPauseDurationMillis(String pauseDurationMillis) {
		this.pauseDurationMillis = pauseDurationMillis;
	}

	public ArrayList<Stage> getStages() {
		return stages;
	}

	public void setStages(ArrayList<Stage> stages) {
		this.stages = stages;
	}

	public PipelineRun() {
		super();
 	}
	
	
	

}

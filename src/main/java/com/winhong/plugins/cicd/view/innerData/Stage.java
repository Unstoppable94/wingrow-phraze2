package com.winhong.plugins.cicd.view.innerData;

import com.google.gson.annotations.Expose;

public class Stage {

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

	@Expose
	private long durationMillis;
	@Expose
	private long pauseDurationMillis;

	@Expose
	private String execNode;

	public Stage() {
		super();
		// TODO Auto-generated constructor stub
	}

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

	public long getDurationMillis() {
		return durationMillis;
	}

	public void setDurationMillis(long durationMillis) {
		this.durationMillis = durationMillis;
	}

	public long getPauseDurationMillis() {
		return pauseDurationMillis;
	}

	public void setPauseDurationMillis(long pauseDurationMillis) {
		this.pauseDurationMillis = pauseDurationMillis;
	}

	public String getExecNode() {
		return execNode;
	}

	public void setExecNode(String execNode) {
		this.execNode = execNode;
	}

}

package com.winhong.plugins.cicd.view.innerData;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

/**
 * Jenkins Build 信息。注意这里只覆盖了用到的部分，不完整
 * @author xiehuiqiang
 *
 */
public class Build {

	
	/**
	 * 是否构建中
	 */
	@Expose
	private boolean building;
	
	/**
	 * 执行时长（毫秒）
	 */
	@Expose
	private long duration;
	
	/**
	 * 结果：
	 * FAILURE,失败
	 * ABORTED(语法错误等，没有执行）,
	 * SUCCESS 成功
	 * NULL, 执行中
	 */
	@Expose
	private String result;
	
	/**
	 * 启动时间
	 */
	@Expose
	private long timestamp;
	
	@Expose
	private String fullDisplayName;
	
	/**
	 * 序号
	 */
	@Expose
	private int number;
	
	@Expose
	private ArrayList<Artifact> artifacts=new ArrayList<Artifact>();
	
	 

	public Build() {
		super();
 	}

	public boolean isBuilding() {
		return building;
	}

	public void setBuilding(boolean building) {
		this.building = building;
	}

	 

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getFullDisplayName() {
		return fullDisplayName;
	}

	public void setFullDisplayName(String fullDisplayName) {
		this.fullDisplayName = fullDisplayName;
	}

	 
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public ArrayList<Artifact> getArtifacts() {
		return artifacts;
	}

	public void setArtifacts(ArrayList<Artifact> artifacts) {
		this.artifacts = artifacts;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	 
	
	

}

package com.winhong.plugins.cicd.view.innerData;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.winhong.plugins.cicd.view.innerData.Build;
import com.winhong.plugins.cicd.view.innerData.HealthReport;

public class Job {

	@Expose
	private String displayName;
	@Expose
	private String name;
	
	 
	
	@Expose
	private boolean buildable;
	
	/**
	 * Jenkins 用color 代表最后一次build状态
	 * red: 失败
	 * blue: 成功
	 * aborted： 无法执行
	 * notbuilt： 没有执行过
	 * XXX_anime： 执行中，XXX为red，blue，aborted，notbuilt，anime只在执行时候显示，等待时候inqueue不会出现
	 */
	@Expose
	private String color;
	
	private  HealthReport healthReport;
	/**
	 * 是否等待中
	 */
	@Expose
	private boolean inQueue;
	
	@Expose
	private Build lastBuild;
	@Expose
	private Build lastCompletedBuild;
	@Expose
	private Build lastFailedBuild;
	@Expose
	private Build lastStableBuild;
	@Expose
	private Build lastSuccessfulBuild;
	@Expose
	private Build lastUnsuccessfulBuild;
	
 	@Expose
 	private int nextBuildNumber;
 	
	@Expose
	private ArrayList<Build> builds=new ArrayList<Build>();
	
	
	public HealthReport getHealthReport() {
		return healthReport;
	}
	public void setHealthReport(HealthReport healthReport) {
		this.healthReport = healthReport;
	}
	public ArrayList<Build> getBuilds() {
		return builds;
	}
	public void setBuilds(ArrayList<Build> builds) {
		this.builds = builds;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isBuildable() {
		return buildable;
	}
	public void setBuildable(boolean buildable) {
		this.buildable = buildable;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public boolean isInQueue() {
		return inQueue;
	}
	public void setInQueue(boolean inQueue) {
		this.inQueue = inQueue;
	}
	public Build getLastBuild() {
		return lastBuild;
	}
	public void setLastBuild(Build lastBuild) {
		this.lastBuild = lastBuild;
	}
	public Build getLastCompletedBuild() {
		return lastCompletedBuild;
	}
	public void setLastCompletedBuild(Build lastCompletedBuild) {
		this.lastCompletedBuild = lastCompletedBuild;
	}
	public Build getLastFailedBuild() {
		return lastFailedBuild;
	}
	public void setLastFailedBuild(Build lastFailedBuild) {
		this.lastFailedBuild = lastFailedBuild;
	}
	public Build getLastStableBuild() {
		return lastStableBuild;
	}
	public void setLastStableBuild(Build lastStableBuild) {
		this.lastStableBuild = lastStableBuild;
	}
	public Build getLastSuccessfulBuild() {
		return lastSuccessfulBuild;
	}
	public void setLastSuccessfulBuild(Build lastSuccessfulBuild) {
		this.lastSuccessfulBuild = lastSuccessfulBuild;
	}
	public Build getLastUnsuccessfulBuild() {
		return lastUnsuccessfulBuild;
	}
	public void setLastUnsuccessfulBuild(Build lastUnsuccessfulBuild) {
		this.lastUnsuccessfulBuild = lastUnsuccessfulBuild;
	}
	public Job() {
		super();
 	}
	public int getNextBuildNumber() {
		return nextBuildNumber;
	}
	public void setNextBuildNumber(int nextBuildNumber) {
		this.nextBuildNumber = nextBuildNumber;
	}
	

	
	
	 

}

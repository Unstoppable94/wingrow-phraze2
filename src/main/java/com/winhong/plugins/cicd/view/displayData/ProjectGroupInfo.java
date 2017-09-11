package com.winhong.plugins.cicd.view.displayData;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.winhong.plugins.cicd.view.innerData.ProjectStatusStat;

public class ProjectGroupInfo{
	
	@Expose
	int total;
	
	@Expose
	ProjectStatusStat stat;
	
	@Expose
	private ArrayList<JobView> results = new ArrayList<JobView>();
	
	public ProjectGroupInfo() {
		super();
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


	public int getTotal() {
		return total;
	}


	public void setTotal(int total) {
		this.total = total;
	}


	public ArrayList<JobView> getResults() {
		return results;
	}


	public void setResults(ArrayList<JobView> results) {
		this.results = results;
	}
	 
	

	 
	 
	
}
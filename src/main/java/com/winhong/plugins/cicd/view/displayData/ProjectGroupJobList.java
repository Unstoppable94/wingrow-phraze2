package com.winhong.plugins.cicd.view.displayData;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class ProjectGroupJobList {
	@Expose
	private int total;

	@Expose
	private ArrayList<JobView> jobs = new ArrayList<JobView>();

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public ArrayList<JobView> getJobs() {
		return jobs;
	}

	public void setJobs(ArrayList<JobView> jobs) {
		this.jobs = jobs;
	}

}
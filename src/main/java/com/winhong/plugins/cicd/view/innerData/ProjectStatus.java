package com.winhong.plugins.cicd.view.innerData;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class ProjectStatus {

	private String _class;

	public String get_class() {
		return _class;
	}

	public void set_class(String _class) {
		this._class = _class;
	}

	public ArrayList<Job> getJobs() {
		return jobs;
	}

	public void setJobs(ArrayList<Job> jobs) {
		this.jobs = jobs;
	}

	@Expose
	private ArrayList<Job> jobs;

	public ProjectStatus() {
		super();
	}

}

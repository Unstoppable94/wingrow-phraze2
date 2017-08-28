package com.winhong.plugins.cicd.data.base;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public abstract  class Workflow {
	
	private  String pipeScript ;
	
	@Expose
	@SerializedName(value = "formProperties")
	private ArrayList<Stage> stages =new ArrayList<Stage>();

	
	public ArrayList<Stage> getStages() {
		return stages;
	}


	public void setStages(ArrayList<Stage> stages) {
		this.stages = stages;
	}


	 

	public Workflow() {
		super();
 	}


	public Workflow(ArrayList<Stage> stages) {
		super();
		this.stages = stages;
	}
	
	
	public String getPipeScript() {
		return pipeScript;
	}


	public void setPipeScript(String pipeScript) {
		this.pipeScript = pipeScript;
	}
	
	
}

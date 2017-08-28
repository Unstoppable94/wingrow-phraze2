package com.winhong.plugins.cicd.docker;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.winhong.plugins.cicd.data.base.Stage;
import com.winhong.plugins.cicd.data.base.Workflow;
import com.winhong.plugins.cicd.dockerStep.Build;

public class TranditionalWorkflow extends Workflow { 

	public TranditionalWorkflow() {
		super();
		Init("");

	}

	public TranditionalWorkflow(String projectId) {
		super();
		Init(projectId);

	}

	private void Init(String string) {
		ArrayList<Stage> s = new ArrayList<Stage>();
		s.add(new Build()); 
		this.setStages(s);

	}

	

	public String getJson() {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();

		return gson.toJson(this);
	}

	public static TranditionalWorkflow genByJson(String json) {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		return gson.fromJson(json, TranditionalWorkflow.class);
	}

	

}
package com.winhong.plugins.cicd.docker;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.winhong.plugins.cicd.data.base.Stage;
import com.winhong.plugins.cicd.data.base.Workflow;
import com.winhong.plugins.cicd.dockerStep.Build;
import com.winhong.plugins.cicd.dockerStep.Compile;

public class MultiStageWorkflow extends Workflow { 

	public MultiStageWorkflow() {
		super();
		Init("");

	}

	public MultiStageWorkflow(String projectId) {
		super();
		Init(projectId);

	}

	private void Init(String string) {
		ArrayList<Stage> s = new ArrayList<Stage>();
		s.add(new Compile());
		s.add(new Build()); 
		this.setStages(s);

	}

	

	public String getJson() {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();

		return gson.toJson(this);
	}

	public static MultiStageWorkflow genByJson(String json) {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		return gson.fromJson(json, MultiStageWorkflow.class);
	}

	

}
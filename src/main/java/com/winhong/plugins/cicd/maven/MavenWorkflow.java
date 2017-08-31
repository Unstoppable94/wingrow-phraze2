package com.winhong.plugins.cicd.maven;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.winhong.plugins.cicd.data.base.Stage;
import com.winhong.plugins.cicd.data.base.Workflow;
import com.winhong.plugins.cicd.mavenStep.Artifact;
import com.winhong.plugins.cicd.mavenStep.Compile;
import com.winhong.plugins.cicd.mavenStep.Findbugs;
import com.winhong.plugins.cicd.mavenStep.MavenTest;
import com.winhong.plugins.cicd.mavenStep.OSWAPDepend;
import com.winhong.plugins.cicd.mavenStep.Sonar;
import com.winhong.plugins.cicd.step.CreateImage;
import com.winhong.plugins.cicd.step.DeployToRancher;
import com.winhong.plugins.cicd.step.PushImage;

public class MavenWorkflow extends Workflow {

	



	public MavenWorkflow() {
		super();
		Init("");

	}

	public MavenWorkflow(String projectId) {
		super();
		Init(projectId);

	}

	private void Init(String string) {
		ArrayList<Stage> s = new ArrayList<Stage>();
		s.add(new Compile());
		s.add(new Findbugs());
		s.add(new OSWAPDepend());
		s.add(new MavenTest());
		s.add(new Artifact());
		s.add(new Sonar());
		s.add(new CreateImage());
		s.add(new PushImage());
		s.add(new DeployToRancher());
		this.setStages(s);

	}

	
	 
	

	public String getJson() {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();

		return gson.toJson(this);
	}

	public static MavenWorkflow genByJson(String json) {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		return gson.fromJson(json, MavenWorkflow.class);
	}

	

}

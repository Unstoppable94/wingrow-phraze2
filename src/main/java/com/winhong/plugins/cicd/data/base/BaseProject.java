package com.winhong.plugins.cicd.data.base;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.Expose;
import com.winhong.plugins.cicd.system.ProjectType;
import com.winhong.plugins.cicd.tool.JenkinsClient;
import com.winhong.plugins.cicd.tool.Tools;

public abstract class BaseProject {
	private static final Logger log = LoggerFactory.getLogger(BaseProject.class);
	@Expose
	private ProjectBaseInfo baseInfo = new ProjectBaseInfo();

	@Expose
	private Workflow workflow;

	public BaseProject() {
		super();
	}

	public ProjectBaseInfo getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(ProjectBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public static String NewProject() throws InstantiationException, IllegalAccessException {
		return NewProject(ProjectType.getDefaultType());
	}

	public static String NewProject(String type) throws InstantiationException, IllegalAccessException {
		Class<BaseProject> cls = ProjectType.getClass(type);
		return Tools.getJson(cls.newInstance().genJson());
	}

	public static String getInitWorkflow(String type) throws InstantiationException, IllegalAccessException {
		Class<BaseProject> cls = ProjectType.getClass(type);
		BaseProject base = cls.newInstance();
		return Tools.getJson(base.workflow);
	}

	public Workflow getWorkflow() {
		return workflow;
	}

	public void reInitWorkflow() throws InstantiationException, IllegalAccessException {

		Class<BaseProject> cls = ProjectType.getClass(this.getBaseInfo().getProjectType());
		BaseProject base = cls.newInstance();

		ArrayList<Stage> stages = base.getWorkflow().getStages();
		ArrayList<Stage> oldStages = this.getWorkflow().getStages();
		if (oldStages != null) {
			for (int i = 0; i < stages.size(); i++) {
				// stages.
				String stageid = stages.get(i).getId();
				for (int j = 0; j < oldStages.size(); j++) {
					Stage old = oldStages.get(j);
					if (old.getId().equals(stageid)) {
						stages.get(i).cloneValue(old);
					}
				}
				// .cloneValue(this.get)
			}
		}
		this.getWorkflow().setStages(stages);
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

	public abstract String genJson();

	public static BaseProject createProjectFromJson(String json) {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create();
		JsonParser parser = new JsonParser();

		JsonObject obj = parser.parse(json).getAsJsonObject();
		JsonObject el = (JsonObject) obj.get("baseInfo");
		String projectType = el.get("projectType").getAsString();
		log.debug("projectType:" + projectType);
		return (BaseProject) gson.fromJson(json, ProjectType.getClass(projectType));

	}

}

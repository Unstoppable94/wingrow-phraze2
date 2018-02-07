package com.winhong.cicdweb;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winhong.plugins.cicd.action.NotifyAction;
import com.winhong.plugins.cicd.action.ProjectAction;
import com.winhong.plugins.cicd.data.base.BaseProject;
import com.winhong.plugins.cicd.system.ProjectType;
import com.winhong.plugins.cicd.tool.Tools;
import com.winhong.plugins.cicd.view.ProjectView;

@Path("/project")
@Consumes("application/json;charset=UTF-8")
public class ProjectRest {

	public ProjectRest() {
		// TODO Auto-generated constructor stub
	}

	private static final Logger log = LoggerFactory.getLogger(ProjectRest.class);

	@GET
	@Produces("application/json;charset=UTF8")
	public String listAllProject(@QueryParam("projectName") String projectName,
			@QueryParam("projectType") String projectType, @QueryParam("firstResult") int firstResult,
			@QueryParam("maxResult") int maxResult, @QueryParam("action") String action) {
		try {
			if (action != null && action.equalsIgnoreCase("new")) {
				// create new project ,return default project to frontend
				String type = ProjectType.getDefaultType();
				if (projectType != null && projectType.isEmpty() == false)
					type = projectType;
				BaseProject project = (BaseProject) ProjectType.getClass(type).newInstance();
 				// log.debug(s);
				// log.debug(tools.ToGBK(s));
				// log.debug(Tools.ToUTF8(s));

				return Tools.ToUTF8(Tools.getJson(project));
			}
			if (action != null && action.equalsIgnoreCase("newWorkflow")) {
				// create new project ,return default project to frontend
				if (projectType != null && projectType.isEmpty() == false)
					return BaseProject.getInitWorkflow(projectType);
				else
					return WebTools.Error("必须输入项目类型");
			}

			log.debug("firstResult:" + firstResult + "maxResult:" + maxResult);

			return Tools.ToUTF8(ProjectView.getProjectList(projectName, projectType, firstResult, maxResult));
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getLocalizedMessage());
			return WebTools.Error(e);
		}
	}

	@GET
	@Path("/{projectName}/triggerbuild")
	@Produces("application/json;charset=UTF-8")
	public String TriggerProjectBuild(@PathParam("projectName") String projectName) {
		try {

			// MavenProject project=new MavenProject();
			if (ProjectAction.triggerBuild(projectName)) {
				//after build call send email
				//NotifyAction.checkProjectStatus();
				return "{\"message\":\"success\"}";
			} else {
				return WebTools.Error("unknow error");
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getLocalizedMessage());
			return WebTools.Error(e);
		}
	}

	@GET
	@Path("/{projectName}")
	@Produces("application/json;charset=UTF-8")
	public String listSingleProject(@PathParam("projectName") String projectName) {
		try {
			log.debug("projectName:" + projectName);
			String s = ProjectAction.getProjectAsString(projectName);
			String returnStr = new String(s.getBytes(), "UTF-8");
			return returnStr;
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getLocalizedMessage());
			return WebTools.Error(e);
		}
	}

	@POST
	@Produces("application/json;charset=UTF-8")
	@Consumes("application/json")
	public String addProject(String json) {
		try {
			BaseProject project = BaseProject.createProjectFromJson(json);
			//(BaseProject) Tools.objectFromJsonString(json, BaseProject.class);

			log.debug(Tools.getJson(project));

			if (ProjectAction.AddProject(project))
				return Tools.ToUTF8(ProjectAction.getProjectAsString(project.getBaseInfo().getId()));
			return WebTools.Error("创建失败，unknown error");
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getLocalizedMessage());
			return WebTools.Error(e);
		}
	}

	@PUT
	@Path("/{projectName}")
	@Produces("application/json;charset=UTF-8")
	@Consumes("application/json")
	public String modifyProject(@PathParam("projectName") String projectId, String json) {
		try {
			BaseProject project = BaseProject.createProjectFromJson(json);

			if (ProjectAction.ModifyProject(project))
				return Tools.ToUTF8(ProjectAction.getProjectAsString(projectId));
			return WebTools.Error("修改失败，unknown error");
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getLocalizedMessage());
			return WebTools.Error(e);
		}
	}

	@DELETE
	@Path("/{projectName}")
	@Produces("application/json;charset=UTF-8")
	@Consumes("application/json")
	public String deleteProject(@PathParam("projectName") String projectName, String json) {
		try {
			log.debug("delete project" + projectName);
			if (ProjectAction.DeleteProject(projectName))
				return "{}";
			return WebTools.Error("删除失败，unknown error");
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getLocalizedMessage());
			return WebTools.Error(e);
		}
	}

	//
	@GET
	@Path("/{projectId}/build/")
	@Produces("application/json;charset=UTF-8")
	public String getProjectbuidinfo(@PathParam("projectId") String projectId,
			@QueryParam("buildNumber") int buildNumber, @QueryParam("buildStatus") String buildStatus,
			@QueryParam("latestStage") String latestStage, @QueryParam("firstResult") int start,
			@QueryParam("maxResult") int maxLine) {
		try {
			log.debug("projectId:" + projectId + " buildNumber:" + buildNumber + " buildStatus:" + buildStatus
					+ " latestStage:" + latestStage + " firstResult:" + start + " maxResult" + maxLine);
			if (buildNumber > 0)
				return ProjectView.getSingleBuildsInfo(projectId, buildNumber);
			else if ((buildStatus == null || buildStatus.equals(""))
					&& (latestStage == null || latestStage.equals(""))) {
				log.debug("number only...getBuildsInfo");
				return Tools.ToUTF8(ProjectView.getBuildsInfo(projectId, start, maxLine));
			}

			else {
				return Tools.ToUTF8(ProjectView.getBuildsInfo(projectId, start, maxLine, buildStatus, latestStage));
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getLocalizedMessage());
			return WebTools.Error(e);
		}
	}

	@GET
	@Path("/{projectId}/build/{buildNumber}")
	@Produces("application/json;charset=UTF-8")
	public String getProjectSpecialBuild(@PathParam("projectId") String projectId,
			@PathParam("buildNumber") int buildNumber) {
		try {

			return Tools.ToUTF8(ProjectView.getSingleRun(projectId, buildNumber));

		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getLocalizedMessage());
			return WebTools.Error(e);
		}
	}

}

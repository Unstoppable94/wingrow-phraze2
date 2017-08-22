package com.winhong.plugins.cicd.view;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.io.FeedException;
import com.winhong.plugins.cicd.Maven.MavenProject;
import com.winhong.plugins.cicd.action.GroupAction;
import com.winhong.plugins.cicd.action.ProjectAction;
import com.winhong.plugins.cicd.action.UserAction;
import com.winhong.plugins.cicd.data.base.ProjectGroupJsonConfig;
import com.winhong.plugins.cicd.system.InnerConfig;
import com.winhong.plugins.cicd.tool.Tools;
import com.winhong.plugins.cicd.user.User;
import com.winhong.plugins.cicd.view.displayData.JobView;
import com.winhong.plugins.cicd.view.displayData.ProjectGroupInfo;
import com.winhong.plugins.cicd.view.innerData.Artifact;
import com.winhong.plugins.cicd.view.innerData.Build;
import com.winhong.plugins.cicd.view.innerData.Job;
import com.winhong.plugins.cicd.view.innerData.JobListOfView;
import com.winhong.plugins.cicd.view.innerData.ProjectStatusStat;
import com.winhong.plugins.cicd.view.innerData.View;

/**
 * Group 等于Jenkins View 理论上支持一个project 属于多个Group/view
 * 
 * @author xiehuiqiang
 *
 */
public class ProjectGroup {

	private static final Logger log = LoggerFactory
			.getLogger(ProjectGroup.class);

	public ProjectGroup() {
		super();
	}

	// 系统init
	static {

		try {
			File file = new File(
					GroupAction
							.getProjectGroupJsonfilename(GroupAction.defaultGroup));
			ProjectGroup.log.debug("check default Group");

			if (file.exists() == false) {
				ProjectGroup.log.debug("creating default Group");
				ProjectGroupJsonConfig def = new ProjectGroupJsonConfig();
				def.setId(GroupAction.defaultGroup);
				def.setName("default");
				def.setDescription("default group,don't modify the group");
				GroupAction.createGroup(def);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("创建默认组失败" + e.getLocalizedMessage());
		}

	}

	private final static String viewsUrl = "/api/json?tree=views[name,url,description]";
	private final static String projectGroupDir = "/projectGroup/";

	private static final int defaultMaxLine = 10;

	public static String listAllGroup(int start, int maxLine)
			throws MalformedURLException, IOException {
		return listAllGroup(null, start, maxLine);
	}

	public static String listAllGroup(String groupName, int start, int maxLine)
			throws MalformedURLException, IOException {

		InnerConfig config = InnerConfig.defaultConfig();
		File folder = new File(config.getDataDir() + projectGroupDir);

		File[] listOfFiles = folder.listFiles();
		ArrayList<ProjectGroupJsonConfig> groups = new ArrayList<ProjectGroupJsonConfig>();
		log.debug("groupName:" + groupName);

		log.debug("groupName:" + folder.getAbsolutePath());

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String fileName = listOfFiles[i].getName();
				log.debug("filename:" + fileName);
				if (fileName.endsWith(".json")) {
					if (groupName != null && groupName != "")
						if (URLDecoder.decode(fileName, "UTF-8").indexOf(
								groupName) < 0) {
							continue;
						}
					ProjectGroupJsonConfig u = (ProjectGroupJsonConfig) Tools
							.objectFromJsonFile(
									listOfFiles[i].getAbsolutePath(),
									ProjectGroupJsonConfig.class);
					log.debug("add group:" + u.getName());

					groups.add(u);
				}

			}
		}

		projectGroupList group = new ProjectGroup().new projectGroupList();

		if (start < 0)
			start = 0;

		if (maxLine == 0)
			maxLine = defaultMaxLine;

		for (int i = start; i < start + maxLine; i++) {
			// 避免数组错误
			if (i >= groups.size() || i < 0)
				break;
			group.results.add(groups.get(i));

		}

		group.total = groups.size();

		return Tools.getJson(group);

	}

	private class projectGroupList {

		@Expose
		private int total;

		@Expose
		private ArrayList<ProjectGroupJsonConfig> results = new ArrayList<ProjectGroupJsonConfig>();

		public int getTotal() {
			return total;
		}

		public void setTotal(int total) {
			this.total = total;
		}

		public ArrayList<ProjectGroupJsonConfig> getResults() {
			return results;
		}

		public void setResults(ArrayList<ProjectGroupJsonConfig> results) {
			this.results = results;
		}

		public projectGroupList() {
			super();
		}

	}

	private final static String projectStatusurl = "/view/#jobname/api/json?tree=jobs[name,displayName,color,inQueue,inQueue,lastBuild[name,building,result,number,duration,timestamp,building,artifacts[fileName,relativePath]]]";

	private final static String jobDashboardUrl = "todo/";

	public static String getProjectGroupStatInfo(String viewName)
			throws Exception {

		InnerConfig config = InnerConfig.defaultConfig();

		// status=gson.fromJson(json, new
		// TypeToken<ArrayList<StatusOfStat>>(){}.getType());
		String url = config.getJenkins().getUrl()
				+ projectStatusurl.replace("#jobname", viewName);

		@SuppressWarnings("unchecked")
		JobListOfView v = (JobListOfView) Tools.objectFromJsonUrl(url,
				JobListOfView.class);

		// Job job=(Job) tools.objectFromJsonUrl(url, Job.class);
		// project.getBaseInfo().
		ArrayList<JobView> list = new ArrayList<JobView>();
		ArrayList<Job> jobs = v.getJobs();

		for (int i = 0; i < jobs.size(); i++) {
			Job job = jobs.get(i);
			// null is all
			JobView j = new JobView();

			j.setId(job.getName());
			j.setName(job.getDisplayName());
			j.setProjectUrl(jobDashboardUrl + job.getName());
			
			//j.setStatus(job.getColor());
			Build build = job.getLastBuild();
			if (build == null) {
				j.setStatus(Tools.NOTBUILD);
			} else {
				j.setLastBuild(build.getTimestamp());
				
				j.setStatus(Tools.colorToStatus(job.getColor()));
				
				j.setDuration(build.getDuration());
				MavenProject pro = ProjectAction.getMavenProject(job.getName());
				// String imageName=pro.getWorkflow().getStages().get
				// TODO 识别docker iamge 及maven artifacts
				// if (build.getArtifacts().size() == 2) {
				// // TODO 修改image 下载命令
				// j.setImageCmd(build.getArtifacts().get(1).getDisplayPath());
				// }
				//if (build.getArtifacts().size() == 1)
				//	j.setArtifact(build.getArtifacts().get(0));
				for (int k = 0; k < build.getArtifacts().size(); k++) {
					Artifact art = build.getArtifacts().get(k);
					String filename = art.getFileName();
					if (art.getFileName().startsWith("image--")) {

						String imageName = filename.substring(7,
								filename.length() - 4);
						j.setImageCmd("docker pull "
								+ URLDecoder.decode(imageName));
					} else {
						j.setArtifact(  "/job/" + job.getName() + "/" + build.getNumber()
								+ "/artifact/"
								+ build.getArtifacts().get(k).getRelativePath());
						 
					}

				}

			}
			list.add(j);

		}
		// ProjectStatusStat
		ProjectStatusStat stat = Dashboard
				.getViewProjectStatusAndReturn(viewName);

		String file = GroupAction.getProjectGroupJsonfilename(viewName);

		// getMavenProjectAsString(projectId)InnerConfig.defaultConfig().getDataDir()+projectGroupDir+viewName+".json";
		// String
		// temp=InnerConfig.defaultConfig().getDataDir()+projectGroupDir+name+".json";
		// file=getProjectGroupJsonfilename(name)

		// File file=new File(temp);

		ProjectGroupInfo info = (ProjectGroupInfo) Tools.objectFromJsonFile(
				file, ProjectGroupInfo.class);

		info.setStat(stat);
		info.setProjects(list);
		// 获取项目组信息

		return Tools.getJson(info);

	}

	private class JenkinsVews {

		@Expose
		ArrayList<View> views = new ArrayList<View>();
		@Expose
		String _class;

		public ArrayList<View> getViews() {
			return views;
		}

		@SuppressWarnings("unused")
		public void setViews(ArrayList<View> views) {
			this.views = views;
		}

		@SuppressWarnings("unused")
		public String get_class() {
			return _class;
		}

		@SuppressWarnings("unused")
		public void set_class(String _class) {
			this._class = _class;
		}

		@SuppressWarnings("unused")
		public JenkinsVews() {
			super();
		}

	}

}

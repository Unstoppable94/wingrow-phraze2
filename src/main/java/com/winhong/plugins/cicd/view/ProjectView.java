package com.winhong.plugins.cicd.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import com.winhong.plugins.cicd.action.GroupAction;
import com.winhong.plugins.cicd.action.ProjectAction;
import com.winhong.plugins.cicd.data.base.BaseProject;
import com.winhong.plugins.cicd.data.base.ProjectBaseInfo;
import com.winhong.plugins.cicd.data.base.ProjectGroupJsonConfig;
import com.winhong.plugins.cicd.maven.MavenProject;
import com.winhong.plugins.cicd.system.Config;
import com.winhong.plugins.cicd.system.InnerConfig;
import com.winhong.plugins.cicd.system.ProjectType;
import com.winhong.plugins.cicd.tool.JenkinsClient;
import com.winhong.plugins.cicd.tool.Tools;
import com.winhong.plugins.cicd.view.displayData.DisplayBuild;
import com.winhong.plugins.cicd.view.innerData.Artifact;
import com.winhong.plugins.cicd.view.innerData.Build;
import com.winhong.plugins.cicd.view.innerData.Job;
import com.winhong.plugins.cicd.view.innerData.JobListOfView;
import com.winhong.plugins.cicd.view.innerData.PipelineRun;
import com.winhong.plugins.cicd.view.innerData.Stage;
import com.winhong.plugins.cicd.view.innerData.StatusOfStat;

public class ProjectView {

	// private BaseProject project;

	private static final Logger log = LoggerFactory.getLogger(ProjectView.class);

	private String projectid;

	private static final String buildsUrl = "/api/json?tree=builds[name,building,result,number,duration,timestamp,building,artifacts[fileName,relativePath]]";

	private static final String simpleJobsUrl = "/api/json?tree=jobs[name]";

	private static int maxrows = 300;

	private static final int defaultMaxLine = 10;

	public static String getProjectList(int start, int maxLine) throws Exception {
		return getProjectList(null, null,start, maxLine);
	}

	// public static String getProjectList(String projectName, int start,
	// int maxLine) throws Exception {
	// if (start < 0)
	// start = 0;
	//
	// if (maxLine == 0)
	// maxLine = defaultMaxLine;
	// InnerConfig config = InnerConfig.defaultConfig();
	//
	// // status=gson.fromJson(json, new
	// // TypeToken<ArrayList<StatusOfStat>>(){}.getType());
	// String url = config.getJenkins().getUrl() + simpleJobsUrl;
	// @SuppressWarnings("unchecked")
	// jobList v = (jobList) tools.objectFromJsonUrl(url, jobList.class);
	// log.debug("url:"+url);
	//
	//
	//
	// jobList retList=new ProjectView().new jobList();
	// for (int i = start; i < start + maxLine; i++) {
	// // 避免数组错误
	// if (i >= v.jobs.size() || i < 0)
	// break;
	// simpleJobinfo job = v.jobs.get(i);
	// log.debug("jobname="+job.getName());
	//
	// MavenProject p = (MavenProject) tools.objectFromJsonString(
	// ProjectAction.readLatestMavenProject(job.name),
	// MavenProject.class);
	//
	// //供前台显示，需要进行转换,jenkins job.name=project.id
	//
	// job.setId(p.getBaseInfo().getId());
	// job.setName(p.getBaseInfo().getName());
	// job.createTime = p.getBaseInfo().getCreateTime();
	// //todo 获取group name
	//
	// job.groupId = p.getBaseInfo().getGroupId();
	//
	// retList.jobs.add(job);
	// // v.jobs.set(i, job);
	//
	// }
	//
	// retList.total = v.jobs.size();
	// //v.jobs.s
	// // replace to results to reduce web modify job
	// return tools.getJson(retList).replaceFirst("jobs", "results");
	//
	// }

	private static final String projectDateDir = "/projects/";
 
 
	/**
	 * @param projectName Project Name
	 * @param ProjectType Project Type
	 * @param start start line number
	 * @param maxLine max show lines 
	 * @return
	 * @throws Exception
	 */
	public static String getProjectList(String projectName,String projectType, int start, int maxLine) throws Exception {

		InnerConfig config = InnerConfig.defaultConfig();
		// File folder = new File(config.getDataDir()
		// + projectDateDir);

		// File[] listOfFiles = folder.listFiles();
		String url = simpleJobsUrl;

		ArrayList<BaseProject> groups = new ArrayList<BaseProject>();

		String output = JenkinsClient.defaultClient().httpSimpleGet(url);

		jobList v = (jobList) Tools.objectFromJsonString(output, jobList.class);
		log.debug("url:" + url);

		ArrayList<simpleJobinfo> jenkinsJobs = v.jobs;
		// filter
		for (int i = 0; i < jenkinsJobs.size(); i++) {

			File mavenfile = new File(config.getDataDir() + projectDateDir + jenkinsJobs.get(i).getName() + ProjectAction.Latestjsonfile);
			if (mavenfile.exists()) {
				//just need baseinfo ,so use any project is fine
				MavenProject maven = (MavenProject) Tools.objectFromJsonFile(mavenfile.getAbsolutePath(),
						MavenProject.class);
				log.debug("get maven" + maven.getBaseInfo().getName());
				boolean namefilter=false;
				if (projectName != null && projectName.isEmpty()==false) {
					if (maven.getBaseInfo().getName().indexOf(projectName) >= 0)
						namefilter=true;
				} else {

					namefilter=true;
					
				}
				if (namefilter==false)
						continue;
				boolean typefilter=false;
				log.debug("maven.getBaseInfo():"+maven.getBaseInfo().getId());
				if (projectType != null && projectType.isEmpty()==false && maven.getBaseInfo().getProjectType()!=null) {
					if (maven.getBaseInfo().getProjectType().equals(projectType))
						typefilter=true;
				} else if (maven.getBaseInfo().getProjectType()!=null) {
					typefilter=true;
				}
				if (typefilter==true)
						groups.add(maven);
			}

		}

		jobList retList = new ProjectView().new jobList();

		if (start < 0)
			start = 0;

		if (maxLine == 0)
			maxLine = defaultMaxLine;

		for (int i = start; i < start + maxLine; i++) {
			// 避免数组错误
			if (i >= groups.size() || i < 0)
				break;
			simpleJobinfo j = new ProjectView().new simpleJobinfo();
			ProjectBaseInfo base = groups.get(i).getBaseInfo();
			
			j.setId(base.getId());
			j.setGroupId(base.getGroupId());

			try {
				ProjectGroupJsonConfig group;

				group = (ProjectGroupJsonConfig) GroupAction.getProjectGroupObject(j.getGroupId());
				j.setGroupName(group.getName());
			} catch (Exception e) {
				log.error("Group id " + j.groupId + "定义文件没有找到");
				j.setGroupName(j.groupId);
				// throw e;
			}

			j.setCreateTime(base.getCreateTime());
			j.setName(base.getName());
			if (base.getProjectType()!=null)
			j.setProjectType(ProjectType.getDisplayName(base.getProjectType()));
			else {
				j.setProjectType("类型未知");

			}
			retList.jobs.add(j);

		}

		retList.total = groups.size();

		// v.jobs.s
		// replace to results to reduce web modify job
		return Tools.getJson(retList).replaceFirst("jobs", "results");

	}

	public static String getBuildsInfo(String projectid, int start, int maxLine, String buildStatus, String latestStage)
			throws MalformedURLException, IOException, InstantiationException, IllegalAccessException {
		if (maxLine == 0)
			maxLine = defaultMaxLine;

		// status=gson.fromJson(json, new
		// TypeToken<ArrayList<StatusOfStat>>(){}.getType());
		String url = "/job/" + projectid + buildsUrl;

		String output = JenkinsClient.defaultClient().httpSimpleGet(url);

		Job job = (Job) Tools.objectFromJsonString(output, Job.class);
		// project.getBaseInfo().
		ArrayList<DisplayBuild> list = new ArrayList<DisplayBuild>();
		buildList returnlist = new ProjectView().new buildList();

		// returnlist.total=

		ArrayList<Build> builds = job.getBuilds();

		for (int i = 0; i < builds.size(); i++) {
			Build build = builds.get(i);

			// filter
			if (buildStatus != null && !buildStatus.equals("") && !buildStatus.equalsIgnoreCase("ALL")) {
				if(buildStatus.equals("failed")) {
					buildStatus = "FAILURE";
				}
				if(buildStatus.equals("success")) {
					buildStatus = "SUCCESS";
				}
				if(buildStatus.equals("stop")) {
					buildStatus = "ABORTED";
				}
				if (!build.getResult().equals(buildStatus)) {
					continue;
				}
			}

			DisplayBuild b = new DisplayBuild();

			b.number = build.getNumber();

			// System.out.println("getDuration:"+build.getDuration()+","+build.getTimestamp()+","+build.getArtifacts().size());

			b.buildDate = build.getTimestamp();
			b.duration = build.getDuration();

			if (build.getResult() == null)
				b.status = Tools.IN_PROGRESS;
			else
				b.status = build.getResult();
			b.logUrl = "/job/" + projectid + "/" + b.number + "/consoleText";

			for (int k = 0; k < build.getArtifacts().size(); k++) {
				Artifact art = build.getArtifacts().get(k);
				String filename = art.getFileName();
				if (art.getFileName().startsWith("image--")) {

					String imageName = filename.substring(7, filename.length() - 4);
					b.imageUrl = ("docker pull " + URLDecoder.decode(imageName));
				} else {
					b.artifactUrl = "/job/" + projectid + "/" + b.number + "/artifact/"
							+ build.getArtifacts().get(k).getRelativePath();

					// j.setArtifact(art);
					// url=""
				}

			}

			// TODO get imageurl
			// b.imageUrl = "";
			// get last stageinfo
			String singleRunUrl = "/job/" + projectid + "/" + build.getNumber() + "/wfapi/describe";
			output = JenkinsClient.defaultClient().httpSimpleGet(singleRunUrl);

			PipelineRun run = (PipelineRun) Tools.objectFromJsonString(output, PipelineRun.class);
			if (run.getStages().size() > 1) {
				Stage stage = run.getStages().get(run.getStages().size() - 1);

				if (latestStage != null && !latestStage.equals("")) {
					if (!stage.getName().equalsIgnoreCase(latestStage)) {
						continue;
					}
				}
				b.setLastStage(stage.getName());
				b.setLastStageStatus(stage.getStatus());

				//list.add(b);
			}
			// if (list.size()>(start+maxLine))
			// break;
			list.add(b);
		}

		// int start,int maxLine
		int listsize = list.size();
		if (start > listsize)
			start = listsize - defaultMaxLine;
		if (start < 0)
			start = 0;
		int end = start + maxLine;
		if (end >= listsize)
			end = listsize - 1;
		if(end <= 0){
			end = listsize;
		}
		if (end < start)
			end = start;

		//
		returnlist.results = new ArrayList<DisplayBuild>(list.subList(start, end));
		returnlist.total = list.size();
		return Tools.getJson(returnlist);

	}

	public static String getLatestBuild(String projectid)
			throws MalformedURLException, IOException, InstantiationException, IllegalAccessException {

		// status=gson.fromJson(json, new
		// TypeToken<ArrayList<StatusOfStat>>(){}.getType());
		String url = Config.getJenkinsConfig().getUrl() + "/job/" + projectid + buildsUrl + "{0,1}";
		log.debug(url);
		Job job = (Job) Tools.objectFromJsonString(url, Job.class);
		if (job.getBuilds().size() > 0)
			return Tools.getJson(job.getBuilds().get(0));
		return "";
	}

	/**
	 * 获取build 信息
	 * 
	 * @param start
	 *            开始
	 * @param maxLine
	 *            返回行数
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static String getBuildsInfo(String projectid, int start, int maxLine)
			throws MalformedURLException, IOException, InstantiationException, IllegalAccessException {
		if (maxLine <= 0) {
			maxLine = defaultMaxLine;
		}
		if (start <= 0) {
			start = 0;

		}

		// status=gson.fromJson(json, new
		// TypeToken<ArrayList<StatusOfStat>>(){}.getType());
		String url = "/job/" + projectid + buildsUrl + "{" + start + "," + (start + maxLine) + "}";

		String output = JenkinsClient.defaultClient().httpSimpleGet(url);

		Job job = (Job) Tools.objectFromJsonString(output, Job.class);

		// project.getBaseInfo().
		ArrayList<DisplayBuild> list = new ArrayList<DisplayBuild>();
		buildList returnlist = new ProjectView().new buildList();

		// returnlist.total=

		ArrayList<Build> builds = job.getBuilds();

		for (int i = 0; i < builds.size(); i++) {
			Build build = builds.get(i);

			DisplayBuild b = new DisplayBuild();

			b.number = build.getNumber();

			// System.out.println("getDuration:"+build.getDuration()+","+build.getTimestamp()+","+build.getArtifacts().size());

			b.buildDate = build.getTimestamp();
			b.duration = build.getDuration();

			if (build.getResult() == null)
				b.status = Tools.IN_PROGRESS;
			else
				b.status = build.getResult();

			b.logUrl = "/job/" + projectid + "/" + b.number + "/consoleText";

			for (int k = 0; k < build.getArtifacts().size(); k++) {
				Artifact art = build.getArtifacts().get(k);
				String filename = art.getFileName();
				if (art.getFileName().startsWith("image--")) {

					String imageName = filename.substring(7, filename.length() - 4);
					b.imageUrl = ("docker pull " + URLDecoder.decode(imageName));
				} else {
					b.artifactUrl = "/job/" + projectid + "/" + b.number + "/artifact/"
							+ build.getArtifacts().get(k).getRelativePath();

					// j.setArtifact(art);
					// url=""
				}

			}

			// get last stageinfo
			String singleRunUrl = "/job/" + projectid + "/" + build.getNumber() + "/wfapi/describe";
			output = JenkinsClient.defaultClient().httpSimpleGet(singleRunUrl);

			PipelineRun run = (PipelineRun) Tools.objectFromJsonString(output, PipelineRun.class);
			if (run.getStages().size() > 1) {
				Stage stage = run.getStages().get(run.getStages().size() - 1);

				b.setLastStage(stage.getName());
				b.setLastStageStatus(stage.getStatus());
			}

			list.add(b);
			// if (list.size()>(start+maxLine))
			// break;
		}

		returnlist.results = (ArrayList<DisplayBuild>) list;

		// get totle builds number

		String totolUrl = "/job/" + projectid + "/api/json?depth=0";
		log.debug("totolUrl:" + totolUrl);
		output = JenkinsClient.defaultClient().httpSimpleGet(totolUrl);

		Job job2 = (Job) Tools.objectFromJsonString(output, Job.class);

		returnlist.total = job2.getBuilds().size();

		return Tools.getJson(returnlist);

	}

	/**
	 * 获取单个build的信息
	 * 
	 * @param projectid
	 * @param buildNumber
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static String getSingleBuildsInfo(String projectid, int buildNumber)
			throws MalformedURLException, IOException, InstantiationException, IllegalAccessException {

		// status=gson.fromJson(json, new
		// TypeToken<ArrayList<StatusOfStat>>(){}.getType());
		String url = "/job/" + projectid + "/" + buildNumber + "/api/json";
		String output = JenkinsClient.defaultClient().httpSimpleGet(url);

		Build build = (Build) Tools.objectFromJsonString(output, Build.class);

		DisplayBuild b = new DisplayBuild();

		b.number = build.getNumber();

		b.buildDate = build.getTimestamp();
		b.duration = build.getDuration();

		if (build.getResult() == null)
			b.status = Tools.IN_PROGRESS;
		else
			b.status = build.getResult();

		b.logUrl = "/job/" + projectid + "/" + b.number + "/consoleText";

		for (int k = 0; k < build.getArtifacts().size(); k++) {
			Artifact art = build.getArtifacts().get(k);
			String filename = art.getFileName();
			if (art.getFileName().startsWith("image--")) {

				String imageName = filename.substring(7, filename.length() - 4);
				b.imageUrl = ("docker pull " + URLDecoder.decode(imageName));
			} else {
				b.artifactUrl = "/job/" + projectid + "/" + b.number + "/artifact/"
						+ build.getArtifacts().get(k).getRelativePath();

			}

		}
		// get last stageinfo
		String singleRunUrl = "/job/" + projectid + "/" + build.getNumber() + "/wfapi/describe";
		output = JenkinsClient.defaultClient().httpSimpleGet(singleRunUrl);

		PipelineRun run = (PipelineRun) Tools.objectFromJsonString(output, PipelineRun.class);
		if (run.getStages().size() > 1) {
			Stage stage = run.getStages().get(run.getStages().size() - 1);

			b.setLastStage(stage.getName());
			b.setLastStageStatus(stage.getStatus());
		}

		ArrayList<DisplayBuild> list = new ArrayList<DisplayBuild>();
		list.add(b);
		buildList returnlist = new ProjectView().new buildList();
		returnlist.results = list;
		returnlist.total = 1;

		// return b;

		return Tools.getJson(returnlist);

	}

	private static final String runUrl = "/job/jobname/number/wfapi/describe";

	/**
	 * 获取单个run info
	 * 
	 * @param projectId
	 *            项目ID
	 * @param buildNumber
	 *            BUILD NUMBER
	 * @return json
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static String getSingleRun(String projectId, int buildNumber)
			throws MalformedURLException, IOException, InstantiationException, IllegalAccessException {
		String url =   runUrl.replace("jobname", projectId).replace("number", "" + buildNumber);
		//run.getStages().size() 
		String output = JenkinsClient.defaultClient().httpSimpleGet(url);

		PipelineRun run = (PipelineRun) Tools.objectFromJsonString(output, PipelineRun.class);
		run.setName(projectId);
		return Tools.getJson(run);

	}

	private final static String wfrunUrl = "/wfapi/runs";

	/**
	 * 返回job的最近失败run信息
	 * 
	 * @return json 格式的run信息
	 * @throws MalformedURLException
	 *             异常
	 * @throws IOException
	 *             异常
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public String getRecentFailRuns()
			throws MalformedURLException, IOException, InstantiationException, IllegalAccessException {
		return getRecentRuns("FAILED,ABORTED");
	}

	/**
	 * 返回job的最近成功run信息
	 * 
	 * @return json 格式的run信息
	 * @throws MalformedURLException
	 *             异常
	 * @throws IOException
	 *             异常
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public String getRecentSucessRuns()
			throws MalformedURLException, IOException, InstantiationException, IllegalAccessException {
		return getRecentRuns("SUCCESS");
	}

	/**
	 * 返回job的最近所有run信息
	 * 
	 * @return json 格式的run信息
	 * @throws MalformedURLException
	 *             异常
	 * @throws IOException
	 *             异常
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public String getRecentAllRuns()
			throws MalformedURLException, IOException, InstantiationException, IllegalAccessException {
		return getRecentRuns(null);
	}

	/**
	 * 返回job的最近失败run信息
	 * 
	 * @return json 格式的run信息
	 * @throws MalformedURLException
	 *             异常
	 * @throws IOException
	 *             异常
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public String getRecentRuns(String Status)
			throws MalformedURLException, IOException, InstantiationException, IllegalAccessException {

		// status=gson.fromJson(json, new
		// TypeToken<ArrayList<StatusOfStat>>(){}.getType());
		String url = "/job/" + projectid + wfrunUrl;

		String output = JenkinsClient.defaultClient().httpSimpleGet(url);

		@SuppressWarnings("unchecked")
		ArrayList<PipelineRun> runs = (ArrayList<PipelineRun>) Tools.objectFromJsonString(output,
				new TypeToken<ArrayList<PipelineRun>>() {
				}.getType());

		// Job job=(Job) tools.objectFromJsonUrl(url, Job.class);
		// project.getBaseInfo().
		ArrayList<PipelineRun> list = new ArrayList<PipelineRun>();
		for (int i = 0; i < runs.size(); i++) {
			PipelineRun run = runs.get(i);
			// null is all
			if (Status == null || Status.indexOf(run.getStatus()) >= 0) {
				list.add(run);
			}
			if (list.size() == maxrows)
				break;

		}
		return Tools.getJson(list);

	}

	public String getProjectid() {
		return projectid;
	}

	public ProjectView(String projectid) {
		super();
		this.projectid = projectid;
	}

	public ProjectView() {
		super();
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}

	private class jobList {
		@Expose
		private int total;

		@Expose
		private ArrayList<simpleJobinfo> jobs = new ArrayList<simpleJobinfo>();

	}

	private class buildList {
		@Expose
		private int total;

		@Expose
		private ArrayList<DisplayBuild> results;

	}

	private class simpleJobinfo {
		@Expose
		String Id;

		@Expose
		String name;

		@Expose
		long createTime;

		@Expose
		String groupId;

		@Expose
		String groupName;
		
		@Expose
		String projectType;
		

		public String getProjectType() {
			return projectType;
		}

		public void setProjectType(String projectType) {
			this.projectType = projectType;
		}

		public String getGroupName() {
			return groupName;
		}

		public void setGroupName(String groupName) {
			this.groupName = groupName;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public long getCreateTime() {
			return createTime;
		}

		public void setCreateTime(long createTime) {
			this.createTime = createTime;
		}

		public String getId() {
			return Id;
		}

		public void setId(String id) {
			Id = id;
		}

		public String getGroupId() {
			return groupId;
		}

		public void setGroupId(String groupId) {
			this.groupId = groupId;
		}

		public simpleJobinfo() {
			super();
		}

	}

}

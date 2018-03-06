package com.winhong.plugins.cicd.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.winhong.plugins.cicd.action.ProjectAction;
import com.winhong.plugins.cicd.data.base.BaseProject;
import com.winhong.plugins.cicd.system.InnerConfig;
import com.winhong.plugins.cicd.tool.JenkinsClient;
import com.winhong.plugins.cicd.tool.Tools;
 import com.winhong.plugins.cicd.view.innerData.Job;
import com.winhong.plugins.cicd.view.innerData.PipelineRun;
import com.winhong.plugins.cicd.view.innerData.ProjectStatus;
import com.winhong.plugins.cicd.view.innerData.ProjectStatusStat;
import com.winhong.plugins.cicd.view.innerData.RssBuild;
import com.winhong.plugins.cicd.view.innerData.RunsStatusStat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dashboard {

	private static int MaxNumberOfList = 10;

	private static final Logger log = LoggerFactory.getLogger(Dashboard.class);

	public Dashboard() {
	}

	private final static String getAllJobsUrl = "/api/json?tree=jobs[name,color,inQueue]";

	/**
	 * 用json 模式返回项目状态统计
	 * 
	 * @return json
	 * @throws MalformedURLException
	 *             URL格式异常
	 * @throws IOException
	 *             IO异常
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static String getAllProjectStatus() throws MalformedURLException,
			IOException, InstantiationException, IllegalAccessException {
		return getProjectStatus("");
		
	}

	
	
	/**
	 * 获取view（group）下项目状态
	 * @param viewName view/GROUP name
	 * @return json 
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static String getViewProjectStatus(String viewName) throws MalformedURLException,
	IOException, InstantiationException, IllegalAccessException {
		return getProjectStatus("/view/"+viewName);

	}
	
	
	public static String getProjectStatus(String viewPath) throws MalformedURLException,
	IOException, InstantiationException, IllegalAccessException {

		//InnerConfig config = InnerConfig.defaultConfig();
		String url = viewPath+getAllJobsUrl;
		
		
		String output=JenkinsClient.defaultClient().httpSimpleGet(url);

 		
		ProjectStatus status = (ProjectStatus) Tools.objectFromJsonString(output,
				ProjectStatus.class);
		ArrayList<Job> jobs = status.getJobs();

		ProjectStatusStat stat = new ProjectStatusStat();

		for (int i = 0; i < jobs.size(); i++) {
			Job j = jobs.get(i);
			stat.total++;
			if (j.getColor().endsWith("anime"))
				stat.running++;
			else if (j.getColor().startsWith("red"))
				stat.failed++;
			else if (j.getColor().startsWith("aborted")) {
				stat.aborted++;
			} else if (j.getColor().startsWith("notbuilt")) // failed
				stat.nobuilt++;
			else if (j.isInQueue()) {
				stat.inqueue++;
			} else {
				stat.success++;
			}

		}

		return Tools.getJson(stat);
	}
	
	
	
	public static ProjectStatusStat getViewProjectStatusAndReturn(String viewName) throws MalformedURLException,
	IOException, InstantiationException, IllegalAccessException {
		return getProjectStatusAndReturn("/view/"+viewName);

	}
	
	
	public static ProjectStatusStat getProjectStatusAndReturn(String viewPath) throws MalformedURLException,
	IOException, InstantiationException, IllegalAccessException {

		 
		String url = viewPath+getAllJobsUrl;
		ProjectStatus status = (ProjectStatus) 
				Tools.objectFromJsonString(
				JenkinsClient.defaultClient().httpSimpleGet(url),
				ProjectStatus.class);
		ArrayList<Job> jobs = status.getJobs();

		ProjectStatusStat stat = new ProjectStatusStat();

		for (int i = 0; i < jobs.size(); i++) {
			Job j = jobs.get(i);
			stat.total++;
			if (j.getColor().endsWith("anime"))
				stat.running++;
			else if (j.getColor().startsWith("red"))
				stat.failed++;
			else if (j.getColor().startsWith("aborted")) {
				stat.aborted++;
			} else if (j.getColor().startsWith("notbuilt")) // failed
				stat.nobuilt++;
			else if (j.isInQueue()) {
				stat.inqueue++;
			} else {
				stat.success++;
			}

		}

		return stat;
	}
	

	private static final String runUrl = "/job/jobname/number/wfapi/describe";

	/**
	 * 获取最近失败的Jenkins build 信息，来自Jenkins Rssfailed
	 * 
	 * @return json 结果
	 * @throws Exception 
	 */
	public static String getFailedBuids(int maxNumber) throws Exception {

		return getRssBuids(JenkinsRss.failRss,maxNumber);

	}

	/**
	 * 获取每个项目最后的build信息，来自Jenkins
	 * rssLatest,注意只要进了queue或者执行，Jenkins就会更新此RSS，但不会更新rssAll RSS ALL 一定是执行了的
	 * 
	 * @return json 结果
	 * @throws Exception 
	 */
	public static String getLatestBuidOfAllProjects(int maxNumber)
			throws Exception {
		return getRssBuids(JenkinsRss.rssLatest,maxNumber);
	}

	/**
	 * 获取最近的build 信息，来自Jenkins rssAll RSS ALL 一定是执行了的
	 * 
	 * @return json 结果
	 * @throws Exception 
	 */
	public static String getLatestExecutedBuid(int maxNumber) throws Exception {
		return getRssBuids(JenkinsRss.allRss,maxNumber);
	}

	/**
	 * 根据Jenkins rss 信息获取相关build 信息
	 * 
	 * @param rss
	 *            rss URL
	 * @return json字符串
	 * @throws Exception 
	 */
	protected static String getRssBuids(String rss,int maxNumber)
			throws Exception {
		//if (maxNumber<=0)
			//maxNumber=MaxNumberOfList;
			
		ArrayList<RssBuild> failbuilds = JenkinsRss.getInfo(rss);
		//maxNumber = failbuilds.size();
		ArrayList<PipelineRun> list = new ArrayList<PipelineRun>();
		for (int i = 0; i < failbuilds.size(); i++) {
			if (i == maxNumber) {
				break;
			}
			RssBuild build = failbuilds.get(i);
			String buildLink = build.getBuildLink();
			String[] urls = buildLink.split("/");
			int length = urls.length;
			log.debug("the project id: "+urls[length-2]);
			//http://jenkins:w12sedwiokd@192.168.101.98:8080//rssAll
			//curl http://jenkins:jenkins@localhost:8080/jenkins/job/pro1509520239094/15/wfapi/describe
			String url =  runUrl.replace("jobname", urls[length-2]).replace(
					"number", "" + build.getNumber());
			try {
			
			String output=JenkinsClient.defaultClient().httpSimpleGet(url);

	 		
		 
			
			PipelineRun run = (PipelineRun) Tools.objectFromJsonString(output,
					PipelineRun.class);
			// 为了显示需求，修改name为项目名称，默认为#112 112为build number
			//BaseProject pro=ProjectAction.getProject(build.getProject());
			BaseProject pro=ProjectAction.getProject(urls[length-2]);
			run.setName(pro.getBaseInfo().getName());
			list.add(run);
			}catch(Exception e) {
				log.warn("get url:"+url+" failed by :"+e.getLocalizedMessage());

			}
		}

		return Tools.getJson(list);

	}

	private static String statShellpath = "/stat/stat.sh";

	 

	public static String getTimeRangeStatfromShell(long beginTime, long endTime)
			throws IOException {
		return getTimeRangeStatfromShell(new Timestamp(beginTime),
				new Timestamp(endTime));
	}

	/**
	 * 调用shell 获取指定时间段内的执行情况
	 * 
	 * @param beginTime
	 *            开始时间 格式要求YYYY-MM-DD HH:MI
	 * @param endTime
	 *            结束时间 格式要求YYYY-MM-DD HH:MI
	 * @return JSON 格式的统计信息
	 * @throws IOException
	 *             IO异常
	 */
	public static String getTimeRangeStatfromShell(Timestamp beginTime,
			Timestamp endTime) throws IOException {

		InnerConfig config = InnerConfig.defaultConfig();
	 

		String[] command = { config.getJenkinsDir() + statShellpath,
				String.valueOf(beginTime.getTime()/1000), String.valueOf(endTime.getTime()/1000) };
		//log.debug("command=="+command.toString());
		Process process = Runtime.getRuntime().exec(command);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				process.getInputStream()));
		String s;
		StringBuffer buffer = new StringBuffer();
		while ((s = reader.readLine()) != null) {
			buffer.append(s);
		}

		RunsStatusStat stat = new RunsStatusStat();

		String re[] = buffer.toString().split(" ");
		for (int i = 0; i < re.length; i = i + 2) {
			if (re[i].equals("FAILURE")) {
				stat.setFailed(Integer.parseInt(re[i + 1]));

			} else if (re[i].equals("SUCCESS")) {
				stat.setSuccess(Integer.parseInt(re[i + 1]));

			} else if (re[i].equals("RUNNING")) {
				stat.setRunning(Integer.parseInt(re[i + 1]));

			} else if (re[i].equals("ABORTED")) {
				stat.setAborted(Integer.parseInt(re[i + 1]));

			}
		}

		return Tools.getJson(stat);
	}

	
	
	private static String detailShellpath = "/stat/numberbytime.sh";

	
	public static String getTimeRangeDetailfromShell(long beginTime,
			long endTime) throws IOException {
		log.debug("begin="+beginTime);

		log.debug("begin="+new Timestamp(beginTime).toLocalDateTime().toString());
		
		return getTimeRangeDetailfromShell(new Timestamp(beginTime),
				new Timestamp(endTime));
	}

	/**
	 * 调用shell 获取指定时间段内的执行明细情况
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return JSON 格式的统计信息
	 * @throws IOException
	 *             IO异常
	 */
	public static String getTimeRangeDetailfromShell(Timestamp beginTime,
			Timestamp endTime) throws IOException {

		InnerConfig config = InnerConfig.defaultConfig();
		 
		String[] command = { config.getJenkinsDir() + detailShellpath,
				String.valueOf(beginTime.getTime()/1000), String.valueOf(endTime.getTime()/1000) };
		
		//转为秒
		log.debug( config.getJenkinsDir() + detailShellpath+" "+
				String.valueOf(beginTime.getTime()/1000)+" "+String.valueOf(endTime.getTime()/1000));
		
		
		Process process = Runtime.getRuntime().exec(command);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				process.getInputStream()));
		String s;
		StringBuffer buffer = new StringBuffer();
		while ((s = reader.readLine()) != null) {
			buffer.append(s);
		}
		return buffer.toString();
	}
}

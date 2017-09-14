package com.winhong.plugins.cicd.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.FileAlreadyExistsException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.stream.JsonReader;
import com.winhong.plugins.cicd.data.base.ProjectGroupJsonConfig;
import com.winhong.plugins.cicd.system.Config;
import com.winhong.plugins.cicd.system.InnerConfig;
import com.winhong.plugins.cicd.tool.JenkinsClient;
import com.winhong.plugins.cicd.tool.Tools;
import com.winhong.plugins.cicd.view.innerData.Job;
import com.winhong.plugins.cicd.view.innerData.JobListOfView;

public class GroupAction {

	private static final Logger log = LoggerFactory
			.getLogger(GroupAction.class);

	public final static String defaultGroup = "default";

	private static String projectGroupDir = "/projectGroup/";

	public GroupAction() {
		super();
	}
 

	/**
	 * 创建新的group
	 * 
	 * @param name
	 *            名称
	 * @param description
	 *            描述
	 * @return 是否成功
	 * @throws IOException
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static boolean createGroup(ProjectGroupJsonConfig group)
			throws IOException, InstantiationException, IllegalAccessException {
		JenkinsClient c = JenkinsClient.defaultClient();
		if (c.addOrModifyView(group.getId(),
				group.getName() + "-" + group.getDescription()))
			return modifyProjectGroupJson(group, newAction);

		return false;

	}

	public static boolean modifyGroup(ProjectGroupJsonConfig group)
			throws IOException, InstantiationException, IllegalAccessException {
		JenkinsClient c = JenkinsClient.defaultClient();
		if (c.addOrModifyView(group.getId(),
				group.getName() + "-" + group.getDescription()))
			return modifyProjectGroupJson(group, modifyAction);

		return false;

	}

	private final static String projectStatusurl = "/view/#jobname/api/json?tree=jobs[name]";

	public static boolean deleteGroup(String id) throws IOException, InstantiationException, IllegalAccessException {

		if (id.equals(defaultGroup)) {
			throw new IOException("Default group can't be delete!");
		}

		// ProjectGroupJsonConfig group

		// 移动所有项目到默认group
 		String url =   projectStatusurl.replace("#jobname", id);

 		String output=JenkinsClient.defaultClient().httpSimpleGet(url);
		@SuppressWarnings("unchecked")
		JobListOfView v = (JobListOfView) Tools.objectFromJsonString(output, 
				JobListOfView.class);
		

		ArrayList<Job> jobs = v.getJobs();
		for (int i = 0; i < jobs.size(); i++) {
			Job job = jobs.get(i);
			changeGroupOfProject(job.getName(), id, defaultGroup);

		}

		if (JenkinsClient.defaultClient().deleteview(id)) {
			// 删除文件
			File file = new File(getProjectGroupJsonfilename(id));

			file.renameTo(new File(file.getAbsolutePath() + "@"
					+ System.currentTimeMillis() + "_deleted"));

		}

		return false;

	}

	/**
	 * 返回项目组信息
	 * 
	 * @param name
	 *            项目组
	 * @return
	 * @throws IOException
	 */
	public static String getProjectGroup(String id) throws IOException {

		File file = new File(getProjectGroupJsonfilename(id));
		return Tools.readFile(file).toString();

	}

	/**
	 * 返回项目组信息
	 * 
	 * @param name
	 *            项目组
	 * @return
	 * @throws IOException
	 */
	public static ProjectGroupJsonConfig getProjectGroupObject(String id)
			throws IOException {

		return (ProjectGroupJsonConfig) Tools.objectFromJsonFile(
				getProjectGroupJsonfilename(id), ProjectGroupJsonConfig.class);
		// File file=new File();
		// return tools.readFile(file).toString();

	}

	/**
	 * 将项目在组之间迁移,注意：项目是必须在某个组内
	 * 
	 * @param projectId
	 *            项目ID
	 * @param oldGroup
	 *            old group id
	 * @param newGroup
	 *            new group id
	 * @return
	 * @throws IOException
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static boolean changeGroupOfProject(String projectId,
			String oldGroup, String newGroup) throws IOException, InstantiationException, IllegalAccessException {
		JenkinsClient c = JenkinsClient.defaultClient();
		boolean ret = c.addJobToView(newGroup, projectId);
		if (oldGroup != null && oldGroup != "")
			return c.removeJobFromView(oldGroup, projectId);
		return ret;
	}

	private static int deleteAction = 1;
	private static int newAction = 2;
	private static int modifyAction = 3;
	private static int addOrModifAction = 4;

	public static String getProjectGroupJsonfilename(String name)
			throws IOException {
		String temp = InnerConfig.defaultConfig().getDataDir()
				+ projectGroupDir + URLEncoder.encode(name, "UTF-8") + ".json";
		return temp;
	}

	/**
	 * 
	 * 修改json文件
	 * 
	 * @param name
	 * @param description
	 * @param action
	 * @return
	 * @throws NullPointerException
	 * @throws IOException
	 */
	private synchronized static boolean modifyProjectGroupJson(
			ProjectGroupJsonConfig group, int action)
			throws NullPointerException, IOException {

		// ClassLoader classLoader = tools.class.getClassLoader();
		// URL res = classLoader.getResource(projectGroupDir+name+".json");
		// 转换文件为URLencoding，避免中文问题

		File file = new File(getProjectGroupJsonfilename(group.getId()));

		if (file.exists() == false) {
			if (action == modifyAction)
				throw new FileNotFoundException(file.getAbsolutePath());
			else {
				long modifytime = System.currentTimeMillis();
				group.setLastModifyTime(modifytime);
				group.setCreatetime(modifytime);
				String t = Tools.getJson(group);
				log.debug("filepath:" + file.getAbsolutePath());
				Tools.saveStringToFile(t, file.getAbsolutePath());

				return true;

			}
		} else if (action == modifyAction || action == addOrModifAction) {
			long modifytime = System.currentTimeMillis();
			file.renameTo(new File(file.getAbsolutePath() + "@" + modifytime));
			group.setLastModifyTime(modifytime);
			Tools.saveStringToFile(Tools.getJson(group),
					getProjectGroupJsonfilename(group.getId()));
			return true;

		} else {
			throw new FileAlreadyExistsException(
					getProjectGroupJsonfilename(group.getId()));

		}

	}

}

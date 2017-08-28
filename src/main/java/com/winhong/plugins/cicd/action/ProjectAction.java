package com.winhong.plugins.cicd.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winhong.plugins.cicd.data.base.BaseProject;
import com.winhong.plugins.cicd.data.base.ProjectBaseInfo;
import com.winhong.plugins.cicd.data.base.Stage;
import com.winhong.plugins.cicd.data.base.Trigger;
import com.winhong.plugins.cicd.maven.MavenProject;
import com.winhong.plugins.cicd.property.Property;
import com.winhong.plugins.cicd.system.Config;
import com.winhong.plugins.cicd.system.InnerConfig;
import com.winhong.plugins.cicd.system.ProjectType;
import com.winhong.plugins.cicd.system.SonarConfig;
import com.winhong.plugins.cicd.tool.JenkinsClient;
import com.winhong.plugins.cicd.tool.Tools;
import com.winhong.plugins.cicd.view.ProjectView;
import com.winhong.plugins.cicd.view.displayData.DisplayBuild;

public class ProjectAction {

	public ProjectAction() {
		// TODO Auto-generated constructor stub
	}
	private static final String jsonfile="/pro.json";
	private static final Logger log = LoggerFactory
			.getLogger(ProjectAction.class);

	//private static final String jobConfigFile = "WinGrow/config/jobConfig.xml";

	private static final String projectDateDir = "/projects/";

	private static final String hisProjectDateDir = "/deletedProjects/";

	
 
	
	public static boolean AddProject(BaseProject project)
			throws Exception {
		
		
		String projectId = project.getBaseInfo().getId();
		if (projectId==null || projectId.equals("")){
			projectId=ProjectBaseInfo.genProjectid();
			project.getBaseInfo().setId(projectId);
		}
			log.debug("projectId:"+projectId);

		File dir = getProjectDirName(projectId);
		
 		if (dir.exists() ){
 			log.debug("dir:"+dir.getAbsolutePath());

			throw new Exception("项目已经存在:"+dir.getAbsolutePath());
		}
		project.getBaseInfo().setCreateTime(System.currentTimeMillis());
		 return AddOrModifyProject(project);
		
	}
	
	
	public static boolean ModifyProject(BaseProject project)
			throws Exception {
		
		String projectId = project.getBaseInfo().getId();
		File dir = getProjectDirName(projectId);

		if (!dir.exists() ){
			throw new Exception("项目不存在");
		}
		project.getBaseInfo().setCreateTime(System.currentTimeMillis());
		 return AddOrModifyProject(project);
		
	}
	
	
	public static File getProjectDirName(String id) throws FileNotFoundException, UnsupportedEncodingException{
		InnerConfig config = InnerConfig.defaultConfig();

		return new File(config.getDataDir() + projectDateDir 
				+URLEncoder.encode(id,"UTF-8"));
		//+".json"
		
 	}
	/**
	 * 新增或者修改项目
	 * 
	 * @param project
	 * @return
	 * @throws Exception
	 */
	private static boolean AddOrModifyProject(BaseProject project)
			throws Exception {

		//InnerConfig config = InnerConfig.defaultConfig();

		String id = project.getBaseInfo().getId();
		File dir = getProjectDirName(id);
		if (dir.exists() == false)
			dir.mkdirs();
		String currentMs = String.valueOf(System.currentTimeMillis());
		project.getBaseInfo().setLastModifyTime(Long.parseLong(currentMs));
		String content = genProjectXml(project);
		//
		String LatestJsonfilename = dir.getAbsolutePath()
				+ jsonfile+"@Latest";

		
		JenkinsClient client = JenkinsClient.defaultClient();

		// 调整归属group/view
		if (client.jobExist(id)) {
			String str = readLatestProject(id);
			MavenProject old = (MavenProject) Tools.objectFromJsonString(str,
					MavenProject.class);

			if (!client.modifyJob(id, content))
					return false;
			if (!old.getBaseInfo().getGroupId()
					.equals(project.getBaseInfo().getGroupId())){
				log.debug("change groupg ");
				if (!GroupAction.changeGroupOfProject(id, old.getBaseInfo()
						.getGroupId(), project.getBaseInfo().getGroupId()))
					return false;
			}

		} else {

			if (client.addJob(project.getBaseInfo().getId(), content)){
				if (!GroupAction.changeGroupOfProject(id, "", project.getBaseInfo()
						.getGroupId()))
					return false;
			}
			else{
				return false;
			}

		}

		// Save Change
		String filename = dir.getAbsolutePath() + "/config.xml@" + currentMs;
		log.debug("save file:"+filename);
		
		Tools.saveStringToFile(content, filename);
		
		String json=Tools.getJson(project);
		
		String Jsonfilename = dir.getAbsolutePath() + jsonfile+"@"
				+ currentMs;
		
		log.debug("save file:"+Jsonfilename);

		
		
		Tools.saveStringToFile(json, Jsonfilename);
		
		File latest = new File(LatestJsonfilename);
		//Path newLink = latest.toPath();
		//Path existingFile = new File(Jsonfilename).toPath();

		if (latest.exists())
			latest.delete();
		Tools.saveStringToFile(json, LatestJsonfilename);
		//Files.createLink(newLink, existingFile);
		//修改credential
		 ProjectBaseInfo baseinfo = project.getBaseInfo();
		//@TODO create credential fail 
 		client.createCredential(baseinfo.getId(),baseinfo.getSCMUser(),baseinfo.getSCMPassword(), "scm");
				 
		return true;

	}

	/**
	 * 删除项目
	 * 
	 * @param project
	 * @return
	 * @throws Exception
	 */
	public static boolean DeleteProject(String projectId) throws Exception {

		InnerConfig config = InnerConfig.defaultConfig();

		//InnerConfig config = InnerConfig.defaultConfig();

		File dir = new File(config.getDataDir() + hisProjectDateDir);
 		//File dir = getProjectDirName(projectId);
 		if (dir.exists() == false)
			dir.mkdirs();

		// String projectId=project.getBaseInfo().getId();

		JenkinsClient client = JenkinsClient.defaultClient();
		File oldProjectdir = getProjectDirName(projectId);

		File Target = new File(dir, URLEncoder.encode(projectId) + "@"
				+ System.currentTimeMillis());
		if (client.deleteJob(projectId)) {
			// 保存历史
			log.debug(oldProjectdir.toPath().toString());
			log.debug(Target.toPath().toString());
			Files.move(oldProjectdir.toPath(), Target.toPath());
			return true;
		}
		return false;

	}

	
	/**
	 * 根据ID 获取project 信息
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	public static String getProjectAsString(String projectId) throws Exception{
		return readLatestProject( projectId);
		
	}
	
	/**
	 * 根据ID 获取project 信息
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	public static BaseProject getProject(String projectId) throws Exception{
		 String s=readLatestProject( projectId);
		 return (BaseProject) Tools.objectFromJsonString(s, BaseProject.class);
		
	}
	
	/**
	 * 读取项目最后文件
	 * 
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	public static String readLatestProject(String projectId)
			throws Exception {
		//InnerConfig config = InnerConfig.defaultConfig();

		File dir = getProjectDirName(projectId);
		
		String LatestJsonfilename = dir.getAbsolutePath()
				+ jsonfile+"@Latest";
		log.debug("filename:"+LatestJsonfilename);
		return Tools.readFile(new File(LatestJsonfilename)).toString();
	}

	private static String genProjectXml(BaseProject project)
			throws IOException, InstantiationException, IllegalAccessException {

		String filename=ProjectType.getConfigXml(project.getBaseInfo().getProjectType());
		log.debug("config xml:"+filename);
		String projectXml = Tools.readResource(filename);

		ProjectBaseInfo baseinfo = project.getBaseInfo();
		String triggerType = baseinfo.getTrigger();
		if (triggerType != null
				&& (triggerType.equalsIgnoreCase("period") || triggerType
						.equalsIgnoreCase("crontab"))) {

			String t = baseinfo.getTriggerProperty();

			Trigger trigger = new Trigger(triggerType, t);

			projectXml = projectXml
					.replace("<triggers/>", trigger.getTrigger());
		}
		// replace baseinfo

		if (baseinfo.getDescription() != null)
			projectXml = projectXml.replace("#description",
					baseinfo.getDescription());
		else
			projectXml = projectXml.replace("#description", "");
		if (baseinfo.getName()!=null)
		projectXml = projectXml.replace("#displayName", baseinfo.getName());
		else
			projectXml = projectXml.replace("#displayName", baseinfo.getId());
			
		projectXml = projectXml.replace("$SCMUrl", baseinfo.getSCMUrl());
		projectXml = projectXml.replace("$SCMPassword","*****");
		projectXml = projectXml.replace("$SCMTYPE", baseinfo.getSCMTYPE());
		projectXml = projectXml.replace("$SCMUser", baseinfo.getSCMUser());
		projectXml = projectXml.replace("$SCMBranch", baseinfo.getSCMBranch());
		projectXml = projectXml.replace("$SCMcredential", baseinfo.getId()+"-scm");

		
		projectXml = projectXml.replace("$createTime",
				String.valueOf(baseinfo.getCreateTime()));
		projectXml = projectXml.replace("$lastModifyTime",
				String.valueOf(baseinfo.getLastModifyTime()));

		projectXml = projectXml.replace("$maxExcutiontime",
				String.valueOf(baseinfo.getMaxExcutiontime()));

		if (baseinfo.getMailOnSuccess() != null)
			projectXml = projectXml.replace("$mailOnSuccess",
					baseinfo.getMailOnSuccess());
		else
			projectXml = projectXml.replace("$mailOnSuccess", "");

		if (baseinfo.getMailOnfail() != null)

			projectXml = projectXml.replace("$mailOnfail",
					baseinfo.getMailOnfail());

		else
			projectXml = projectXml.replace("$mailOnfail", "");

		if (baseinfo.getMailOnReovery() != null)

			projectXml = projectXml.replace("$mailOnfail",
					baseinfo.getMailOnReovery());

		else
			projectXml = projectXml.replace("$mailOnReovery", "");

		if (baseinfo.getExraProperties() != null)
			projectXml = projectXml.replace("$exraProperties",
					baseinfo.getExraProperties());
		else
			projectXml = projectXml.replace("$exraProperties", "");

		// Maven Project replace
		//projectXml = projectXml.replace("$mavenId", baseinfo.getMavenId());
		//projectXml = projectXml.replace("$jdk", baseinfo.getJdk());

		// replace stages values
		ArrayList<Stage> stages = project.getWorkflow().getStages();
		for (int i = 0; i < stages.size(); i++) {
			Stage stage = stages.get(i);
			ArrayList<Property> properties = stage.getElements();
			for (int j = 0; j < properties.size(); j++) {
				Property p = properties.get(j);
				String temp = p.getValue();
				if (temp == null)
					temp = "";
				projectXml = projectXml.replace(
						"$" + stage.getId() + "_" + p.getId(), temp);
			}
		}
		
		//sonar connect setting
		SonarConfig sonar=Config.getSonarConfig();
		if (sonar.getSonarUrl()!=null)
		projectXml = projectXml.replace("$sonar_url",sonar.getSonarUrl());
		projectXml = projectXml.replace("$SonarCredential","sonar-credential");

		//SonarUrl=
		//		=$SonarCredential
				
		return projectXml;
	}


	 
	
	public static boolean      triggerBuild(String projectName) throws MalformedURLException, IOException, InstantiationException, IllegalAccessException {
		JenkinsClient client = JenkinsClient.defaultClient();
		return (client.triggerBuild(projectName));
		 
	}
}

package com.winhong.plugins.cicd.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.winhong.plugins.cicd.data.base.EnumList;
import com.winhong.plugins.cicd.data.base.ProjectTypeDefine;
import com.winhong.plugins.cicd.docker.MultiStageDockerProject;
import com.winhong.plugins.cicd.docker.TranditionalDockerProject;
import com.winhong.plugins.cicd.maven.MavenProject;

public class ProjectType {
	
	public static final String MavenProject = "mavenProject";
	public static final String TraditionalDocker="traditionalDocker";
	public static final String MultistageDocker="multistageDocker";
	
	static HashMap<String, ProjectTypeDefine> types=new HashMap<String, ProjectTypeDefine>();
	//static HashMap<String, String> typesDisplay=new HashMap<String, String>();
	static ArrayList<EnumList> relist = new ArrayList<EnumList>();
	static {
		types.put(MavenProject, new ProjectTypeDefine("Maven",MavenProject.class,"WinGrow/config/mavenJobConfig.xml"));
		 
		types.put(TraditionalDocker, new ProjectTypeDefine("传统Docker",TranditionalDockerProject.class,"WinGrow/config/TraditionalJobConfig.xml"));
		types.put(MultistageDocker, new ProjectTypeDefine("多Stage Docker",MultiStageDockerProject.class,"WinGrow/config/jobConfig.xml"));

		initEnum();
		//types.put("mavenProject", MavenProject.class);
		
	}
	
	public static Class getClass(String type) {
		return types.get(type).getCls();
	}
	
	
	public static String getConfigXml(String type) {
		return types.get(type).getConfigXml();
	}
	
	public static String getDefaultType() {
		return MultistageDocker;
	}
	
	public static void initEnum() {
		
		// if (registryList.size()==0){
		try {

			Iterator<String> keys = types.keySet().iterator();
			 while(keys.hasNext()) { 
				String k=keys.next();
				relist.add(new EnumList(k,types.get(k).getDisplayName() ));
			}
		} catch (Exception e) {
 			e.printStackTrace();
 		}

	}
	public static  ArrayList<EnumList>  getProjectTypeList() {
		return relist;
	}


	public static String getDisplayName(String type) {
		return types.get(type).getDisplayName();
	}

	
}

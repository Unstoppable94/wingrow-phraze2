package com.winhong.plugins.cicd.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

 import com.winhong.plugins.cicd.data.base.BaseProject;
import com.winhong.plugins.cicd.data.base.EnumList;
import com.winhong.plugins.cicd.data.base.ProjectTypeDefine;
import com.winhong.plugins.cicd.data.base.Stage;
import com.winhong.plugins.cicd.data.base.Workflow;
import com.winhong.plugins.cicd.docker.MultiStageDockerProject;
import com.winhong.plugins.cicd.docker.TranditionalDockerProject;
import com.winhong.plugins.cicd.maven.MavenProject;

public class ProjectType {
	
	public static final String MavenProject = "mavenProject";
	public static final String TraditionalDocker="traditionalDocker";
	public static final String MultistageDocker="multistageDocker";
	private static final Logger log = LoggerFactory.getLogger(ProjectType.class);
	
	static HashMap<String, ProjectTypeDefine> types=new HashMap<String, ProjectTypeDefine>();
	//static HashMap<String, String> typesDisplay=new HashMap<String, String>();
	static ArrayList<EnumList> relist = new ArrayList<EnumList>();
	
	
	static ArrayList<EnumList> stageDisplaylist = new ArrayList<EnumList>();
	
	static {
		types.put(MavenProject, new ProjectTypeDefine("Maven",MavenProject.class,"WinGrow/config/MavenJobConfig.xml"));
		 
		types.put(TraditionalDocker, new ProjectTypeDefine("传统Docker",TranditionalDockerProject.class,"WinGrow/config/TraditionalJobConfig.xml"));
		types.put(MultistageDocker, new ProjectTypeDefine("多Stage Docker",MultiStageDockerProject.class,"WinGrow/config/DockerJobConfig.xml"));

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
				ProjectTypeDefine type=types.get(k);
				Class<BaseProject> cls=type.getCls();
				Workflow workflow = cls.newInstance().getWorkflow();
				ArrayList<Stage> stages = workflow.getStages();
				for (int i=0;i<stages.size();i++) {
					String id=stages.get(i).getId();
					String name=stages.get(i).getName();
					boolean notfound=true;
					for (int j=0;j<stageDisplaylist.size();j++) {
						if (stageDisplaylist.get(j).getName().equals(name)){
							notfound=false;
							if (stageDisplaylist.get(j).getId().equals(id)==false ) {
								log.error("stage name:"+name+"have different id:"+stageDisplaylist.get(j).getId()+","+id);
							}
							break;
						}
							
					}
					if (notfound) {
						stageDisplaylist.add(new EnumList(id,name));
					}
				}
				 
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


	public static ArrayList<EnumList> getStageDisplaylist() {
		return stageDisplaylist;
	}


	public static void setStageDisplaylist(ArrayList<EnumList> stageDisplaylist) {
		ProjectType.stageDisplaylist = stageDisplaylist;
	}

	
}

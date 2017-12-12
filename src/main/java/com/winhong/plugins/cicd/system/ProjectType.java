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
		types.put(MavenProject, new ProjectTypeDefine("Maven项目",MavenProject.class,"WinGrow/config/MavenJobConfig.xml"));
		 
		types.put(TraditionalDocker, new ProjectTypeDefine("传统Docker项目",TranditionalDockerProject.class,"WinGrow/config/TraditionalJobConfig.xml"));
		types.put(MultistageDocker, new ProjectTypeDefine("多Stage Docker项目",MultiStageDockerProject.class,"WinGrow/config/MultiStageDocker.xml"));

		try {
			initEnum();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			System.exit(-1);
		}
 		
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
	
	public static void initEnum() throws InstantiationException, IllegalAccessException {
		
 
			Iterator<String> keys = types.keySet().iterator();
			 while(keys.hasNext()) { 
				String k=keys.next();
				if(k == MavenProject){
					String mavendes = "Maven项目：Maven 提供了标准的软件生命周期模型和构建模型，通过配置就能对项目进行全面的管理。";
					relist.add(new EnumList(k,types.get(k).getDisplayName(), mavendes));
				}
				if(k == TraditionalDocker){
					
					String mulitstagedes = "传统docker项目的编译镜像和部署镜像分为俩个镜像，编译使用的镜像使用系统默认镜像，"
							+ "不需要配置，用来发布的镜像需要制定dockerfile文件利用编译生成的程序包进行构建。";
					relist.add(new EnumList(k,types.get(k).getDisplayName(), mulitstagedes));
				}
				if(k == MultistageDocker){
					String traditonaldes = "使用多stage docker构建，您可以在Dockerfile中使用多个FROM语句。每个FROM指令可以使用不同的基础，并且每个指令都开始构建的新阶段。"
							+ "您可以选择性地将文物从一个阶段复制到另一个阶段，在最终图像中留下您不需要的任何内容。";
					relist.add(new EnumList(k,types.get(k).getDisplayName(), traditonaldes));
				}
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
						stageDisplaylist.add(new EnumList(id,name ));
					}
				}
				 
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

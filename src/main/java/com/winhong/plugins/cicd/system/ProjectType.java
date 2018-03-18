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
					String mavendes = "Maven项目提供了标准的软件生命周期模型和构建模型，通过配置就能对项目进行全面的管理，"
							+ "Maven将构建的过程抽象成一个个的生命周期过程，在不同的阶段使用不同的已实现插件来完成相应的实际工作，这种设计方法极大的避免了设计和脚本编码的重复，极大的实现了复用。";
					relist.add(new EnumList(k,types.get(k).getDisplayName(), mavendes));
				}
				if(k == TraditionalDocker){
					
					String mulitstagedes = "传统docker项目的编译镜像和部署镜像分为两个镜像，编译和发布使用同一个镜像，"
							+ "需要在流水线中设置用来编译和发布的镜像参数。";
					relist.add(new EnumList(k,types.get(k).getDisplayName(), mulitstagedes));
				}
				if(k == MultistageDocker){
					String traditonaldes = "多stage docker构建需要Docker引擎的版本为17.05或更高版本，"
							+ "使用多stage docker构建，您可以在Dockerfile中使用多个FROM语句。每个FROM指令可以使用不同的基础镜像，并且每个指令都开始新的构建阶段，"
							+ "您可以选择性地将构建物从一个阶段复制到另一个阶段，在最终发布的镜像中只留下需要部署的代码包。";
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

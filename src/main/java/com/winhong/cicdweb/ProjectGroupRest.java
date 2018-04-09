package com.winhong.cicdweb;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winhong.plugins.cicd.action.GroupAction;
import com.winhong.plugins.cicd.data.base.ProjectGroupJsonConfig;
import com.winhong.plugins.cicd.tool.Tools;
import com.winhong.plugins.cicd.view.ProjectGroup;
import com.winhong.plugins.cicd.view.displayData.ProjectGroupInfo;

@Path("/projectgroup")
@Consumes("application/json;charset=UTF-8")
public class ProjectGroupRest {

	public ProjectGroupRest() {
		// TODO Auto-generated constructor stub
	}
	private static final Logger log = LoggerFactory.getLogger(ProjectGroupRest.class);

 
	
	@GET
	@Produces("application/json;charset=UTF-8")
	@Consumes("application/json;charset=UTF-8")
	public String listAllGroup(@QueryParam("firstResult")int firstResult,
			@QueryParam("maxResult")int maxResult,@QueryParam("name")String name, @Context HttpServletRequest req){
		try {
			Cookie[] cookies = req.getCookies();
			if(cookies != null){
				String account = "";
				String accountType = "";
				for(int i=0; i<cookies.length; i++){
					Cookie c = cookies[i];
					if(c.getName().equalsIgnoreCase("account")){
						account = c.getValue();
						System.out.println("account cookie:++++:" + account);
					}
					if(c.getName().equalsIgnoreCase("accountType")){
						accountType = c.getValue();
						System.out.println("accountType cookie:++++:" + accountType);
					}
				}
			}
			log.debug("firstResult:"+firstResult+"maxResult:"+maxResult);
			//TODO group name
			if (name==null ||name.equals(""))
				return Tools.ToUTF8(ProjectGroup.listAllGroup(firstResult, maxResult));
			else
				return Tools.ToUTF8(ProjectGroup.listAllGroup(  name, firstResult,maxResult));
		} catch (Exception e) {
 			e.printStackTrace();
			log.debug(e.getLocalizedMessage());
 			return WebTools.Error(e);
		}
 	}
//
	@GET
	@Path("/{groupId}/info")
	@Produces("application/json;charset=UTF-8")
 	public String getProjectGroupStat(@PathParam("groupId")String groupId){
		try {
			
			return Tools.ToUTF8(ProjectGroup.getProjectGroupStatInfo(groupId));
		} catch (Exception e) {
 			e.printStackTrace();
			log.debug(e.getLocalizedMessage());
 			return WebTools.Error(e);
		}
	}
	
	
	@GET
	@Path("/{groupId}")
	@Produces("application/json;charset=UTF-8")
 	public String getProjectGroup(@PathParam("groupId")String groupId){
		try {
			String s=GroupAction.getProjectGroup(groupId);
			log.debug(s);
			return Tools.ToUTF8(s);
 		} catch (Exception e) {
 			e.printStackTrace();
			log.debug(e.getLocalizedMessage());
 			return WebTools.Error(e);
		}
	}
	
	

	@POST
	@Produces("application/json;charset=UTF-8")
	@Consumes("application/json")
	public String createGroup(String json){
		try {
			
			ProjectGroupJsonConfig pg=(ProjectGroupJsonConfig)
					Tools.objectFromJsonString(json, ProjectGroupJsonConfig.class);
			
			pg.setId("Group"+System.currentTimeMillis());
			String groupId = pg.getId();
			//判断是否项目同名
			int size = ProjectGroup.listAllGroupTo(pg.getName()).size();
			if(size <= 0){
				GroupAction.createGroup(pg);
				return Tools.ToUTF8(getProjectGroup(groupId));
			}
			//return "{\"result\":\"项目已存在\"}";
			return WebTools.Error("创建失败，项目已经存在");
						
		} catch (Exception e) {
 			e.printStackTrace();
			log.debug(e.getLocalizedMessage());
 			return WebTools.Error(e);
		}
	}
	
	@PUT
	@Path("/{groupId}")
	@Produces("application/json;charset=UTF-8")
	@Consumes("application/json")
	public String modifyGroup(@PathParam("groupId") String groupId, String json) {
		try {

			ProjectGroupJsonConfig pg = (ProjectGroupJsonConfig) Tools
					.objectFromJsonString(json, ProjectGroupJsonConfig.class);
			log.debug("groupId:" + groupId);
			if (pg.getId().equals(groupId) == false) {
				throw new Exception("ID/name 不匹配");
			}
			String creatTime = pg.getId().substring(5);
			pg.setCreatetime(Long.parseLong(creatTime));
			GroupAction.modifyGroup(pg);
			return Tools.ToUTF8(getProjectGroup(groupId));

		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getLocalizedMessage());
			return WebTools.Error(e);
		}
	}
//	
	@DELETE
	@Path("/{groupId}")
	@Produces("application/json;charset=UTF-8")
	public String deleteGroup(@PathParam("groupId")String groupId){
		try {
			
			 
			GroupAction.deleteGroup(groupId);
			return "{}";
						
		} catch (Exception e) {
 			e.printStackTrace();
			log.debug(e.getLocalizedMessage());
 			return WebTools.Error(e);
		}
	}
	
	//流水线创建中项目组的列表展示
	@GET
	@Path("/all")
	@Produces("application/json;charset=UTF-8")
	@Consumes("application/json;charset=UTF-8")
	public String allGroup(){
		try {
			//log.debug("firstResult:"+firstResult+"maxResult:"+maxResult);
			//TODO group name
			return Tools.ToUTF8(ProjectGroup.allGroup());
			
		} catch (Exception e) {
 			e.printStackTrace();
			log.debug(e.getLocalizedMessage());
 			return WebTools.Error(e);
		}
 	}
}

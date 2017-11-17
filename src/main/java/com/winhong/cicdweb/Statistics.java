package com.winhong.cicdweb;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.winhong.plugins.cicd.action.StatisticsAction;
import com.winhong.plugins.cicd.data.base.BuildStatPraram;
import com.winhong.plugins.cicd.data.base.ProjectStatPram;
import com.winhong.plugins.cicd.tool.DateUtil;
import com.winhong.plugins.cicd.tool.JenkinsClient;

@Path("statistics")
public class Statistics {
	private static final Logger log = LoggerFactory.getLogger(Statistics.class);
	
	@GET
	@Path("projectStat")
	@Produces("application/json;charset=UTF-8")
	@Consumes("application/json")
	public String showProjectStatis(@QueryParam("groupIds") String groupIds){
		ProjectStatPram pro = new ProjectStatPram();
		if(groupIds == null){
			String content = "{\"groupIds\":[]}";
			return StatisticsAction.showprojectStat(content);
		}
		String[] groupId = {groupIds};
		pro.setGroupIds(groupId);
		Gson gson = new Gson();
		String content = gson.toJson(pro, ProjectStatPram.class);
		return StatisticsAction.showprojectStat(content);
	}
	@GET
	@Path("buildStat")
	@Produces("application/json;charset=UTF-8")
	@Consumes("application/json")
	public String showBuildStatis(@QueryParam("beginTime") String beginTime,
			@QueryParam("endTime") String endTime){
		if(beginTime != null && endTime != null){
			beginTime = DateUtil.stampToDate(beginTime);
			endTime = DateUtil.stampToDate(endTime);
		}
		BuildStatPraram bs = new BuildStatPraram();
		bs.setBeginTime(beginTime);
		bs.setEndTime(endTime);
		Gson gson = new Gson();
		String json = gson.toJson(bs, BuildStatPraram.class);
		return StatisticsAction.showBuildStat(json);
	}
	
	@GET
	@Path("buildDetail")
	@Produces("application/json;charset=UTF-8")
	@Consumes("application/json")
	public String showbuildDetail(@QueryParam("start") String start,
			@QueryParam("end") String end,
			@QueryParam("groupId") String groupId){
		if(groupId == null || groupId.trim() == ""){
			groupId = "default";
		}
		return StatisticsAction.getBuildDetial(groupId, start, end);

	}
	@GET
	@Path("groupByStatus")
	@Produces("application/json;charset=UTF-8")
	@Consumes("application/json")
	public String groupByStatus(@QueryParam("start") String start,
			@QueryParam("end") String end,
			@QueryParam("groupId") String groupId){
		if(groupId == null || groupId.trim() == ""){
			groupId = "default";
		}
		return StatisticsAction.groupByStatus(groupId, start, end);

	}
}

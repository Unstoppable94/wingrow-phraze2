package com.winhong.cicdweb;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winhong.plugins.cicd.action.StatisticsAction;
import com.winhong.plugins.cicd.tool.JenkinsClient;

@Path("statistics")
public class Statistics {
	private static final Logger log = LoggerFactory.getLogger(Statistics.class);
	
	@POST
	@Path("projectStat")
	@Produces("application/json;charset=UTF-8")
	@Consumes("application/json")
	public String showProjectStatis(String json){
		return StatisticsAction.showprojectStat(json);
	}
	@POST
	@Path("buildStat")
	@Produces("application/json;charset=UTF-8")
	@Consumes("application/json")
	public String showBuildStatis(String json){
		return StatisticsAction.showBuildStat(json);
	}
}

package com.winhong.plugins.cicd.action;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winhong.plugins.cicd.tool.JenkinsClient;

public class StatisticsAction {
	
	public static Logger log = LoggerFactory.getLogger(StatisticsAction.class);
	public static String showprojectStat(String json){
		JenkinsClient jks = null;
		String result = null;
		try {
			jks = JenkinsClient.defaultClient();
			result = jks.projectStatShow(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}
		 
		return result;
	}
	public static String showBuildStat(String content){
		JenkinsClient jks = null;
		String result = null;
		try {
			jks = JenkinsClient.defaultClient();
			result = jks.projectBuildStat(content);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return result;
	}
}

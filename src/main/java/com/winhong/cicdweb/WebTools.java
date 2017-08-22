package com.winhong.cicdweb;

import com.winhong.plugins.cicd.tool.Tools;


public class WebTools {

	public WebTools() {
		// TODO Auto-generated constructor stub
	}

	public static String Error(Exception e){
		return Tools.ToUTF8("{\"exception\":\""+e.getLocalizedMessage()+"\"}");
	}

	public static String Error(String string) {
		return Tools.ToUTF8("{\"exception\":\""+string+"\"}");
	}
}

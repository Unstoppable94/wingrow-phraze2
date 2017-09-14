package com.winhong.plugins.cicd.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.stream.JsonReader;
import com.winhong.plugins.cicd.data.base.EnumList;
import com.winhong.plugins.cicd.tool.Tools;

public class RegistryList {

	private static final Logger log = LoggerFactory
			.getLogger(RegistryList.class);
	
	
 	
	
 
	
	public RegistryList() {
	}

	@Expose
	private   ArrayList<RegistryConfig> registries=new ArrayList<RegistryConfig>();
	
	 

	 
	public ArrayList<RegistryConfig> getRegistries() {
		return registries;
	}
	 
	public void setRegistries(ArrayList<RegistryConfig> registries) {
		this.registries = registries;
	}
	
	
	
 	
	public String getConfigJson(){
		String temp="{\"auths\": {";
		for (int i=0;i<registries.size();i++){
			RegistryConfig config=registries.get(i);
			temp+="\""+config.getServer()+"\": {\"auth\":\""+config.getAuth()+"\",\"email\":\""
					+config.getEmail()+"\"},";
			
		}
		temp=temp.substring(0,temp.length()-1);
		temp+="}}";
		return temp;
	}
	

	public String inscure="\"#url\"";
	
	public String getInscureString(){
		String temp="";
		for (int i=0;i<registries.size();i++){
			RegistryConfig config=registries.get(i);
			if (config.isSecure()==false)
			temp+= inscure.replace("#url", config.getServer())+",";
			
		}
		if (temp.isEmpty())
				return "";
		return temp.substring(0,temp.length()-1);
	}
	
}

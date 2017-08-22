package com.winhong.plugins.cicd.data.base;

import com.google.gson.annotations.Expose;

public class JDK {
	


	
	/**
	 * Jenkins 内定义好的JDK ID,global tools 里面的JDK name
	 * config.xml jdk 段落
	 */
	@Expose
	private String id;
	
	@Expose
	private String description;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public JDK() {
		super();
	}
	
	

}

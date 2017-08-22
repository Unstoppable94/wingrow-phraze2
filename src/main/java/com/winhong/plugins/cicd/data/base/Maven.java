package com.winhong.plugins.cicd.data.base;

import com.google.gson.annotations.Expose;

public class Maven {

	/**
	 * Jenkin global tools 里面定义的Maven name
	 * hudson.tasks.Maven.xml
	 */
	@Expose
	private String id;
	
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

	@Expose
	private String description;

	public Maven() {
		super();
	}
	
	
	
	
}

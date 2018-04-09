package com.winhong.plugins.cicd.view.displayData;

import com.google.gson.annotations.Expose;

public class RancherEnvironment {

	@Expose
	private String id;
	
	@Expose 
	private String name;
	
	@Expose String description;
	public RancherEnvironment(String aid, String aName) {
		super();
		id=aid;
		name=aName;
 	}
	public RancherEnvironment(String aid, String aName, String description) {
		super();
		id=aid;
		name=aName;
		this.description = description;
 	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	
}

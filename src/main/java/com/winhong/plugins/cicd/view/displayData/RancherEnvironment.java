package com.winhong.plugins.cicd.view.displayData;

import com.google.gson.annotations.Expose;

public class RancherEnvironment {

	@Expose
	private String id;
	
	@Expose 
	private String name;

	public RancherEnvironment(String aid, String aName) {
		super();
		id=aid;
		name=aName;
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
	
	
}

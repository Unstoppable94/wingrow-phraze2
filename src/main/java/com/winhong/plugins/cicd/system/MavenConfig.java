package com.winhong.plugins.cicd.system;

import com.google.gson.annotations.Expose;

public class MavenConfig {

	@Expose
	private String id;

	@Expose
	private String version;
	
	@Expose
	private String path;
	
	public MavenConfig() {
 	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public MavenConfig(String id, String version, String path) {
		super();
		this.id = id;
		this.version = version;
		this.path = path;
	}

	
}

package com.winhong.plugins.cicd.view.innerData;

import com.google.gson.annotations.Expose;

public class PipielineArtifact {

	// http://10.211.55.6:8080/job/maskio/144/wfapi/artifacts

	@Expose
	private String id;

	@Expose
	private String name;

	@Expose
	private String path;

	@Expose
	private String url;

	@Expose
	private long size;

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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public PipielineArtifact() {
		super();
 	}
	
	
	

}

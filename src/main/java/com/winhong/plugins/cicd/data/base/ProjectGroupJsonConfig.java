package com.winhong.plugins.cicd.data.base;

import com.google.gson.annotations.Expose;

public class ProjectGroupJsonConfig {
	
	
	@Expose 
	private String id;
	
	@Expose
	private String name;
	
	@Expose
	private String description;
	
	@Expose
	private long createtime;
	
	@Expose
	private long lastModifyTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getCreatetime() {
		return createtime;
	}

	public void setCreatetime(long createtime) {
		this.createtime = createtime;
	}

	public long getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(long lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public ProjectGroupJsonConfig() {
		super();
 	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}

package com.winhong.plugins.cicd.system;

import com.google.gson.annotations.Expose;

public class EmailTemplate {

	
	@Expose
	private String title;
	
	@Expose
	private String content;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	

	
	
}

package com.winhong.plugins.cicd.view.innerData;

import com.google.gson.annotations.Expose;

public class View {

	
 	@Expose
	private String _class;
	
	@Expose
	private String name;
	
	@Expose
	private String url;
	
	@Expose
	private String description;
	
	
	
	public View() {
 	}


	public String get_class() {
		return _class;
	}


	public void set_class(String _class) {
		this._class = _class;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	

}

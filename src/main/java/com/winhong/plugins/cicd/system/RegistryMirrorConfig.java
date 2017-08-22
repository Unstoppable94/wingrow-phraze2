package com.winhong.plugins.cicd.system;

import com.google.gson.annotations.Expose;

public class RegistryMirrorConfig {
	
	@Expose
	private String url;
	
	@Expose
	private boolean scure;

	 

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isScure() {
		return scure;
	}

	public void setScure(boolean scure) {
		this.scure = scure;
	}

	public RegistryMirrorConfig() {
		super();
 	}

	 

}

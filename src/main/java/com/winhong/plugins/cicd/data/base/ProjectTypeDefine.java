package com.winhong.plugins.cicd.data.base;

public class ProjectTypeDefine {
	String displayName;
	Class cls;
	String configXml;
	
	public ProjectTypeDefine(String displayName, Class cls, String xml) {
		super();
		this.displayName = displayName;
		this.cls = cls;
		this.configXml = xml;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Class getCls() {
		return cls;
	}

	public void setCls(Class cls) {
		this.cls = cls;
	}

	public String getConfigXml() {
		return configXml;
	}

	public void setConfigXml(String configXml) {
		this.configXml = configXml;
	}

	 
	
}

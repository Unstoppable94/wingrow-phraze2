package com.winhong.plugins.cicd.data.base;

import java.util.List;

public class BuildStatPraram {
	private String beginTime;
	private String endTime;
	public BuildStatPraram() {
		// TODO Auto-generated constructor stub
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
}

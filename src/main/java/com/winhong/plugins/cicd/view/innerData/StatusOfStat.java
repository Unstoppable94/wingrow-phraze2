package com.winhong.plugins.cicd.view.innerData;

import com.google.gson.annotations.Expose;

public class StatusOfStat {

	public StatusOfStat() {
		super();
	}
	
	@Expose
	private String status;
	
	@Expose
	private long number;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public StatusOfStat(String status, long number) {
		super();
		this.status = status;
		this.number = number;
	}
	
	

}

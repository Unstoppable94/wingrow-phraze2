package com.winhong.plugins.cicd.view.innerData;

import com.google.gson.annotations.Expose;

public class HealthReport {

	 
	/**
	 * Jenkins 给出的分数
	 */
	@Expose
	private int score;

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public HealthReport() {
		super();
 	}

	 
	 
	 
	 

}

package com.winhong.plugins.cicd.view.innerData;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class JobListOfView {

 		
		@Expose
		ArrayList<Job> jobs=new ArrayList<Job> ();

		@SuppressWarnings("unused")
		public ArrayList<Job> getJobs() {
			return jobs;
		}

		@SuppressWarnings("unused")
		public void setJobs(ArrayList<Job> jobs) {
			this.jobs = jobs;
		}

		public JobListOfView() {
			super();
 		}

		public JobListOfView(ArrayList<Job> jobs) {
			super();
			this.jobs = jobs;
		}

		 
		
		
	 

}

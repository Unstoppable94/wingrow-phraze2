package com.winhong.plugins.cicd.view.innerData;

import com.google.gson.annotations.Expose;

 
public class ProjectStatusStat{
		
		@Expose
		public int total;
		@Expose
		public int failed;
		@Expose
		public int running;
		@Expose
		public int inqueue;
		@Expose
		public int nobuilt;
		
		@Expose 
		public int success;

		@Expose 
		public int aborted;
		
		
		
		public int getAborted() {
			return aborted;
		}
		public void setAborted(int aborted) {
			this.aborted = aborted;
		}
		public int getSuccess() {
			return success;
		}
		public void setSuccess(int success) {
			this.success = success;
		}
		
		
		
		public ProjectStatusStat() {
			super();
 		}
		public int getTotal() {
			return total;
		}
		public void setTotal(int total) {
			this.total = total;
		}
		public int getFailed() {
			return failed;
		}
		public void setFailed(int failed) {
			this.failed = failed;
		}
		public int getRunning() {
			return running;
		}
		public void setRunning(int running) {
			this.running = running;
		}
		public int getInqueue() {
			return inqueue;
		}
		public void setInqueue(int inqueue) {
			this.inqueue = inqueue;
		}
		public int getNobuilt() {
			return nobuilt;
		}
		public void setNobuilt(int nobuilt) {
			this.nobuilt = nobuilt;
		}
		
		
	}

 
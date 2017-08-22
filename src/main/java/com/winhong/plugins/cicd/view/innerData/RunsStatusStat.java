package com.winhong.plugins.cicd.view.innerData;

import com.google.gson.annotations.Expose;

 
public class RunsStatusStat{
		
		@Expose
		public int total;
		@Expose
		public int failed;
		@Expose
		public int running;
		
		
		@Expose 
		public int success;

		@Expose 
		public int aborted;
		
		
		
		public int getAborted() {
			return aborted;
		}
		public void setAborted(int aborted) {
			this.aborted = aborted;
			this.total=this.aborted+this.failed+this.running+this.success;
		}
		public int getSuccess() {
			return success;
		}
		public void setSuccess(int success) {
			this.success = success;
			this.total=this.aborted+this.failed+this.running+this.success;

		}
		
		
		
		public RunsStatusStat() {
			super();
 		}
		public int getTotal() {
			return  this.aborted+this.failed+this.running+this.success;
		}
		public void setTotal(int total) {
			this.total = total;
		}
		public int getFailed() {
			return failed;
		}
		public void setFailed(int failed) {
			this.failed = failed;
			this.total=this.aborted+this.failed+this.running+this.success;

		}
		public int getRunning() {
			return running;
		}
		public void setRunning(int running) {
			this.running = running;
			this.total=this.aborted+this.failed+this.running+this.success;

		}
		 
		
		
	}

 
package com.winhong.plugins.cicd.user;

import com.google.gson.annotations.Expose;

public class User {

	public static  final String adminRole="admin";
	public static  final String operatorRole="operator";
	public static  final String readonly="readonly";

		
		@Expose
		String id;
		
		public String getId() {
			return id;
		}


		public void setId(String id) {
			this.id = id;
		}


		@Expose
		private String username;
		
		
		@Expose
		private String password;
		
		
		@Expose
		private String role;
		
		
		public User() {
	 	}

		
		/**
		 * 
		 */
		@Expose
		long createTime;
		
		@Expose
		long latestModifyTime;
		

		public long getCreateTime() {
			return createTime;
		}


		public void setCreateTime(long createTime) {
			this.createTime = createTime;
		}


		public long getLatestModifyTime() {
			return latestModifyTime;
		}


		public void setLatestModifyTime(long latestModifyTime) {
			this.latestModifyTime = latestModifyTime;
		}


		public String getUsername() {
			return username;
		}


		public void setUsername(String username) {
			this.username = username;
		}


		public String getPassword() {
			return password;
		}


		public void setPassword(String password) {
			this.password = password;
		}


		public String getRole() {
			return role;
		}


		public void setRole(String role) {
			this.role = role;
		}
		
		
}

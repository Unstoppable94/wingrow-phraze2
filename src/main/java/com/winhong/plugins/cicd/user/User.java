package com.winhong.plugins.cicd.user;

import com.google.gson.annotations.Expose;

public class User {

	public static  final String adminRole="admin";
	public static  final String operatorRole="operator";
	public static  final String readonly="readonly";
	public static  final String LDAP="ldap";
	public static  final String LOCAL="local";
	
		@Expose
		String id;
		
		

		@Expose
		private String username;
		
		
		@Expose
		private String password;
		
		
		@Expose
		private String role;
		
	
		
		/**
		 * 
		 */
		@Expose
		long createTime;
		
		@Expose
		long latestModifyTime;
		
		@Expose
		String userType;
		
		@Expose
		long passwordExpired;
		
		
		public User() {
	 	}

		

		 


		public String getUserType() {
			return userType;
		}






		public void setUserType(String userType) {
			this.userType = userType;
		}






		public long getPasswordExpired() {
			return passwordExpired;
		}



		public void setPasswordExpired(long passwordExpired) {
			this.passwordExpired = passwordExpired;
		}



		public static String getAdminrole() {
			return adminRole;
		}



		public static String getOperatorrole() {
			return operatorRole;
		}



		public static String getReadonly() {
			return readonly;
		}



		public static String getLdap() {
			return LDAP;
		}



		public static String getLocal() {
			return LOCAL;
		}



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
		
		
		public String getId() {
			return id;
		}


		public void setId(String id) {
			this.id = id;
		}

		
}

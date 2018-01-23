package com.winhong.plugins.cicd.data.base;

import java.util.ArrayList;

public class RegistryOuth {
	
		public String server;
		public String auth;
		public RegistryOuth() {
			// TODO Auto-generated constructor stub
		}
		public RegistryOuth(String server, String auth) {
			this.server = server;
			this.auth = auth;
		}
		public void setServer(String server) {
			this.server = server;
		}
		public String getServer() {
			return server;
		}
		public void setAuth(String auth) {
			this.auth = auth;
		}
		public String getAuth() {
			return auth;
		}
}

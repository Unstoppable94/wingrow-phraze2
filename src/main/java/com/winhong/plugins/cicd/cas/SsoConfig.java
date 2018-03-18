package com.winhong.plugins.cicd.cas;

import javax.servlet.Filter;

import com.winhong.uap.cas.client.authentication.AuthenticationFilter;

public class SsoConfig {
	public static Filter registerAuthenticationFilter() {
		AuthenticationFilter filter = new AuthenticationFilter();
		filter.setCasServerLoginUrl("");
		filter.setServerName("");
		return filter;
	}
}

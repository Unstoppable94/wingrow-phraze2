package com.winhong.plugins.cicd.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.SessionCookieConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Request;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpRequest;
import org.eclipse.jetty.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winhong.uap.cas.client.authentication.AttributePrincipal;
import com.winhong.uap.cas.client.jaas.CasLoginModule;
import com.winhong.uap.cas.client.util.AbstractCasFilter;
import com.winhong.uap.cas.client.validation.Assertion;
import com.winhong.uap.sdk.model.User;

public class UAPLoginFilter implements Filter {
	
	final static Logger logger =LoggerFactory.getLogger(UAPLoginFilter.class);
	
	private String username;
	private String accountType;
	private String account;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = ((HttpServletRequest) request);
		HttpServletResponse res = ((HttpServletResponse) response);
		System.out.println("uri==========="+req.getRequestURI());
		
		//
		UAPUserMange.getUser(UAPUserMange.getUsername(req), res);        
//        if(account.equals("admin")) {
//        	res.sendRedirect("/error.html");
//        }

		if(req.getRequestURI().contains("index.html")){
        	res.sendRedirect("/index1.html");
        	System.out.println("index1.html++++++++++++++++++++");
        }
        
		chain.doFilter(request, response);
	}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}



}

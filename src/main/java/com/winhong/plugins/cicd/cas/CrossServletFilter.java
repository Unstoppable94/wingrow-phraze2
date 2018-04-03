package com.winhong.plugins.cicd.cas;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MultivaluedMap;

public class CrossServletFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
        HttpServletResponse res = (HttpServletResponse) response;  
        res.setHeader("Access-Control-Allow-Origin", "*");  
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");  
        res.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, X-Codingpedia");  
        //res.setHeader("Access-Control-Allow-Credentials", "true");  
        chain.doFilter(request, response);  
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}

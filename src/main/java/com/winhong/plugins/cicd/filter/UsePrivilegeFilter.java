package com.winhong.plugins.cicd.filter;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.server.ContainerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winhong.plugins.cicd.action.UserAction;
import com.winhong.plugins.cicd.jwt.TokenUtil;
import com.winhong.plugins.cicd.user.User;

/**
 * Servlet Filter implementation class UsePrivilegeFilter
 */
//@Provider
//@Priority(Priorities.AUTHORIZATION)
public class UsePrivilegeFilter implements ContainerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(UsePrivilegeFilter.class);

	/**
	 * Default constructor.
	 */
	public UsePrivilegeFilter() {
		// TODO Auto-generated constructor stub
	}
 

 
	/**
	 * @param path
	 * @param method
	 * @param user
	 * @param requestContext
	 * @return
	 * @throws IOException
	 */
	private boolean priviliegeCheck(String path,
			String method,User user,ContainerRequestContext requestContext) throws IOException { 
		logger.debug("role:"+user.getRole());
		if (user.getRole().endsWith(User.adminRole))
			return true;
		

		// 禁止修改用户
		if (user.getRole().endsWith(User.operatorRole)) {
			if (path.indexOf("/user/") >= 0
					&& !method.equalsIgnoreCase("get")) {
				
				return false;
			}

			return true;
		} else {			
			// 禁止查看用户信息
			if (path.equalsIgnoreCase("/user")){
				return false;
			}
			if (method.equalsIgnoreCase("get"))
				return true;
		}
		
		return false;
		
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
			String method = requestContext.getMethod().toLowerCase();
			String path = ((ContainerRequest) requestContext).getPath(true).toLowerCase();
			logger.debug("path="+path);
			if (path.equalsIgnoreCase("login")  
					|| path.equalsIgnoreCase("logout")  
					|| path.equalsIgnoreCase("SelfInfo")  ){
 				return;
			}
			
			String authorizationHeader = ((ContainerRequest) requestContext).getHeaderString(JWTSecurityFilter.AuthHeader);
			if (authorizationHeader == null) {
				throw new WebApplicationException(Response.Status.UNAUTHORIZED);
			}

			String strToken = JWTSecurityFilter.extractJwtTokenFromAuthorizationHeader(authorizationHeader);
			User user=UserAction.getUserinfo(TokenUtil.getName(strToken));	 
			if (!priviliegeCheck(path,method,user,requestContext)) 
				throw new WebApplicationException(Response.Status.FORBIDDEN); //403
			
		}
		
 
	
}

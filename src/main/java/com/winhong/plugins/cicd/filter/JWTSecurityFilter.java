package com.winhong.plugins.cicd.filter;

import org.glassfish.jersey.server.ContainerRequest;

import com.winhong.cicdweb.ProjectRest;
import com.winhong.cicdweb.WebTools;
import com.winhong.plugins.cicd.action.UserAction;
import com.winhong.plugins.cicd.jwt.SecurityContextAuthorizer;
import com.winhong.plugins.cicd.jwt.TokenUtil;
import com.winhong.plugins.cicd.user.User;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Key;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * https://simplapi.wordpress.com/2013/01/24/jersey-jax-rs-implements-a-http-basic-auth-decoder/
 */
//Todo open for production
@Provider
@Priority(Priorities.AUTHENTICATION)
public class JWTSecurityFilter implements ContainerRequestFilter {

	final static Logger logger =LoggerFactory.getLogger(JWTSecurityFilter.class);
	
	public final static String AuthHeader="authorization";
	@Inject
	javax.inject.Provider<UriInfo> uriInfo;

	public static String extractJwtTokenFromAuthorizationHeader(String auth) {
		// Replacing "Bearer Token" to "Token" directly
		return auth.replaceFirst("[B|b][E|e][A|a][R|r][E|e][R|r] ", "").replace(" ", "");
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		String method = requestContext.getMethod().toLowerCase();
		String path = ((ContainerRequest) requestContext).getPath(true);
		logger.debug("path="+path);
		if ("POST".equalsIgnoreCase(method) && "login".equals(path) ) {
			// pass through the filter.
			 requestContext.setSecurityContext(new SecurityContextAuthorizer(uriInfo,
			 () -> "anonymous", new String[]{"anonymous"}));
			return;
		}

		String authorizationHeader = ((ContainerRequest) requestContext).getHeaderString(AuthHeader);
		if (authorizationHeader == null) {
			throw new WebApplicationException(Response.Status.UNAUTHORIZED);
		}

		String strToken = extractJwtTokenFromAuthorizationHeader(authorizationHeader);
		if (TokenUtil.isValid(strToken)) {
			String name = TokenUtil.getName(strToken);
			String[] roles = TokenUtil.getRoles(strToken);
			// int version = TokenUtil.getVersion(strToken);
			if (name != null && roles.length != 0) {
				// User user = null;

				User user = UserAction.getUserinfo(name);
				if (user == null || user.getUsername() == null) {
					// return WebTools.Error("用户不存在、或者密码错误");
					logger.info("User not found " + name);
					throw new WebApplicationException(Response.Status.UNAUTHORIZED);

				}
				if ( user.getPasswordExpired()>0 && exceptionPath(path,method)==false) {
					//throw new WebApplicationException();
					ResponseBuilder builder = null;
			        String response = "{\"exception\":\"请先修改密码！\"}";
			        builder = Response.status(Response.Status.OK).entity(response);
			        throw new WebApplicationException(builder.build());
			        
					//requestContext.setSecurityContext(new SecurityContextAuthorizer(uriInfo, () -> name, roles));
					//return;
				} 
				if ( user.getRole().equals(roles[0])) {

					requestContext.setSecurityContext(new SecurityContextAuthorizer(uriInfo, () -> name, roles));
					return;
				} else {
					logger.info(" roles did not match the token");
				}
				
			} else {
				logger.info("User not found");
			}

		} else {
			logger.info("token is invalid");
		}
		throw new WebApplicationException(Response.Status.UNAUTHORIZED);
	}
	
	
	public  boolean exceptionPath(String path,String method) {
		String[] allowPath= {"logout","selfInfo"};
		//boolean allow=false;
		for (int i=0; i<allowPath.length;i++ ) {
			if (path.endsWith(allowPath[i]))
				return true;
		}
		return false;
	}
	
}
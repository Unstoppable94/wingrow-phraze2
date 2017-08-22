package com.winhong.cicdweb;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winhong.plugins.cicd.user.User;

/**
 * Servlet Filter implementation class UsePrivilegeFilter
 */
@WebFilter(filterName = "/UsePrivilegeFilter", urlPatterns = { "/*" })

public class UsePrivilegeFilter implements Filter {

	private static final Logger log = LoggerFactory.getLogger(UsePrivilegeFilter.class);

	/**
	 * Default constructor.
	 */
	public UsePrivilegeFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	private static String loginurl = "/login.html";

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// place your code here
		// pass the request along the filter chain
		if ((request instanceof HttpServletRequest)) {
			
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			log.debug("filterurl--" + httpRequest.getPathInfo()+":"+
					httpRequest.getServletPath()
					+"=="+httpRequest.getQueryString()
					+"--"+httpRequest.getContextPath());
			if (httpRequest.getServletPath().equalsIgnoreCase("/webapi")){
			//if (httpRequest.getPathInfo() != null) {
				
				HttpSession session = httpRequest.getSession(true);
				User user = (User) session.getAttribute(Login.userAttr);
				if (httpRequest.getPathInfo().indexOf("/login") >= 0
						|| httpRequest.getPathInfo().indexOf("/logout") >= 0
						|| httpRequest.getPathInfo().indexOf("/SelfInfo") >= 0){
					chain.doFilter(request, response);
					return;
				}
					
				if (user==null && httpRequest.getPathInfo().equals("/login")==false){
					log.debug("user is null ** ");
					log.debug("httpRequest.getPathInfo()=="+httpRequest.getPathInfo());

					//((HttpServletResponse) response).sendRedirect(httpRequest.getContextPath()+loginurl);
					//todo 返回登录界面
					((HttpServletResponse) response).sendError(403,"请重新登录！");
 					throw new  PrivilegeException("请先登录！");
					
 				}
				if (!priviliegeCheck(httpRequest,
						(HttpServletResponse) response, user)) {
					((HttpServletResponse) response).sendError(403,"请勿越权！");
					throw new  PrivilegeException("请勿越权！"); 
				}
			}
			// chain.
		}
		chain.doFilter(request, response);
	}

	//@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

	/**
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 * @throws IOException
	 */
	private boolean priviliegeCheck(HttpServletRequest request,
			HttpServletResponse response, User user) throws IOException {
		
		
		if (user == null) {
			response.sendRedirect(loginurl);
			response.sendError(403,"请重新登录！");

			return false;

		}
		log.debug("role:"+user.getRole());
		if (user.getRole().endsWith(User.adminRole))
			return true;
		
	
		// 禁止修改用户
		if (user.getRole().endsWith(User.operatorRole)) {
			if (request.getPathInfo().indexOf("/user/") >= 0
					&& !request.getMethod().equalsIgnoreCase("get")) {
				response.sendError(403,"请勿越权！");

				return false;
			}

			return true;
		} else {
			
			//	return true;

			// 禁止查看用户信息
			if (request.getPathInfo().equalsIgnoreCase("/user")){
				response.sendError(403,"请勿越权！");
				return false;
			}
			if (request.getMethod().equalsIgnoreCase("get"))
				return true;

		}
		response.sendError(403,"请勿越权！");
		return false;

	}

}

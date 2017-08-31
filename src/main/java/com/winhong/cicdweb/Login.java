package com.winhong.cicdweb;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.annotations.Expose;
import com.winhong.plugins.cicd.action.UserAction;
import com.winhong.plugins.cicd.tool.Tools;
import com.winhong.plugins.cicd.user.User;

@Path("/")
@Consumes("application/json;charset=UTF-8")
public class Login {

	private static final Logger log = LoggerFactory.getLogger(UserManage.class);

	public static final String userAttr="user";
	public Login() {
		// TODO Auto-generated constructor stub
	}
	
	@POST
	@Path("login")
	@Produces("application/json;charset=utf-8")
	public String login(@FormParam("username") String username,
			@FormParam("password") String password,
			@Context final HttpServletResponse response,
			@Context HttpServletRequest req) throws Exception {
		try {
			log.debug("login ----------username:" + username);

			User user = UserAction.getUserinfo(username);
			if (user == null || user.getUsername() == null) {
				return WebTools.Error("用户不存在、或者密码错误");
			}

			if (user.getPassword().equals(password)) {
				HttpSession session = req.getSession(true);
				session.setAttribute(userAttr, user);
				log.debug("login successful----------username:" + username);

				response.sendRedirect(req.getContextPath()+"/index.html");
			}

			return WebTools.Error("用户不存在、或者密码错误");
			// return tools.getJson(InnerConfig.defaultConfig().getMaven());
		} catch (java.io.FileNotFoundException e) {
			return WebTools.Error("用户不存在、或者密码错误");
		} catch (Exception e) {

			e.printStackTrace();
			log.debug(e.getLocalizedMessage());
			return WebTools.Error(e);
		}

	}
	
	@POST
	@Path("/logout")
	@Produces("application/json;charset=utf-8")
	public String logout(@Context final HttpServletResponse response,
			@Context HttpServletRequest req) throws Exception {
		try {
			log.debug("logout");
			HttpSession session = req.getSession(true);
			session.removeAttribute(userAttr);
			//response.sendRedirect("../../login.html");

			return "{\"relocation\":\"./login.html\"}";
		//	return WebTools.Error("logint.html");
			// return tools.getJson(InnerConfig.defaultConfig().getMaven());

		} catch (Exception e) {

			e.printStackTrace();
			log.debug(e.getLocalizedMessage());
			return WebTools.Error(e);
		}

	}


	
	@GET
	@Path("/selfInfo")
	@Produces("application/json;charset=UTF-8")
	public String getUser(@Context final HttpServletResponse response,
			@Context HttpServletRequest req)   {
		try {
			User user =  (User) req.getSession().getAttribute(userAttr);
			
			User retUser = new User();
			if (user==null)
					return WebTools.Error("not login");
			retUser.setUsername(user.getUsername());
			retUser.setCreateTime(user.getCreateTime());
			retUser.setLatestModifyTime(user.getLatestModifyTime());
			retUser.setRole(user.getRole());
			retUser.setPassword(UserAction.PasswordMask);
			
			return Tools.getJson(retUser);

		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getLocalizedMessage());
			return WebTools.Error(e);
		}
	}
	
	@PUT
	@Path("selfInfo")
	@Produces("application/json;charset=utf-8")
	public String modifyProject(@PathParam("username") String username,
			String json,
			@Context final HttpServletResponse response,
			@Context HttpServletRequest req)  {
		try {
			HttpSession session = req.getSession(true);
			Password inputPass=(Password) Tools.objectFromJsonString(json, Password.class);
			
			
			User sessionUser = (User) session.getAttribute(userAttr);
			//session.removeAttribute(userAttr);
			if (!sessionUser.getPassword().equals(inputPass.oldPassword)){
				log.debug("old="+sessionUser.getPassword());
				log.debug("input="+inputPass.oldPassword);

				return WebTools.Error("旧密码不对！");
				
				 
			}
				
			sessionUser.setPassword(inputPass.password);
			
			User user = UserAction.modifyUser((sessionUser) );

			return Tools.getJson(user);

		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getLocalizedMessage());
			return WebTools.Error(e);
		}
	}
	
	private class Password{
		
		@Expose
		String oldPassword;
		
		@Expose
		String password;
		
		@Expose
		String passwordRepeat;
		public String getOldPassword() {
			return oldPassword;
		}
		public void setOldPassword(String oldPassword) {
			this.oldPassword = oldPassword;
		}
		 
		public String getPasswordRepeat() {
			return passwordRepeat;
		}
		public void setPasswordRepeat(String passwordRepeat) {
			this.passwordRepeat = passwordRepeat;
		}
		public Password() {
			super();
 		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
	 
	}
}

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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.annotations.Expose;
import com.winhong.plugins.cicd.action.UserAction;
import com.winhong.plugins.cicd.filter.JWTSecurityFilter;
import com.winhong.plugins.cicd.jwt.Token;
import com.winhong.plugins.cicd.jwt.TokenUtil;
import com.winhong.plugins.cicd.tool.Tools;
import com.winhong.plugins.cicd.user.User;

@Path("/")
@Consumes("application/json;charset=UTF-8")
public class Login {

	private static final Logger log = LoggerFactory.getLogger(UserManage.class);

	public static final String userAttr = "user";

	public Login() {
		// TODO Auto-generated constructor stub
	}

	// @QueryParam("username") String username, @QueryParam("password") String
	// password)
	// public String createGroup(String json)

	@POST
	@Path("login")
	@Produces("application/json;charset=utf-8")
	@Consumes("application/json")
	public String login(String json) throws Exception {
		try {
			User t = (User) Tools.objectFromJsonString(json, User.class);
			String username = t.getUsername();
			String password = t.getPassword();

			log.debug("login ----------username:" + username);

			User user = UserAction.Login(username, password);
			if (user == null || user.getUsername() == null) {
				log.info("login fail,username:" + username);
				return WebTools.Error("用户不存在、或者密码错误或者过期");
			}

			String role = user.getRole();
			String[] roles = { role };
			String jwtString = TokenUtil.getJWTString(username, roles);
			Token token = new Token();
			token.setAuthorization(jwtString);
			token.setExpires(TokenUtil.getExpiryDate().getTime());
			
			if (user.getUserType().equals(User.LOCAL) && user.getPasswordExpired()>0) {
				token.setMustChangePassword(true);
			}
			else
				token.setMustChangePassword(false);
			return Tools.getJson(token); 
		} catch (java.io.FileNotFoundException e) {
			return WebTools.Error("用户不存在、或者密码错误");
		} catch (Exception e) {

			e.printStackTrace();
			log.debug(e.getLocalizedMessage());
			return WebTools.Error(e);
		}

	}

	@POST
	@Path("logout")
	@Produces("application/json;charset=utf-8")
	public String logout(@Context HttpServletRequest req)
			throws Exception {
		try {
			String key = req.getHeader(JWTSecurityFilter.AuthHeader);
			log.debug("key="+key);
			TokenUtil.removeToken(key);
			return "{\"result\":\"ok\"}";

		} catch (Exception e) {

			e.printStackTrace();
			log.debug(e.getLocalizedMessage());
			return WebTools.Error(e);
		}

	}

	@GET
	@Path("selfInfo")
	@Produces("application/json;charset=UTF-8")
	public String getUser(@Context final HttpServletResponse response, @Context HttpServletRequest req) {
		try {
			String name=UserManage.getLoginUser(req);
			
			if (name == null||name.isEmpty())
				return WebTools.Error("not login");
			User user=UserAction.getUserinfo(name);


			//User retUser = new User();
			
			user.setPassword(UserAction.PasswordMask);

			return Tools.getJson(user);

		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getLocalizedMessage());
			return WebTools.Error(e);
		}
	}

	@PUT
	@Path("selfInfo")
	@Produces("application/json;charset=utf-8")
	public String modifySelf( String json,
			@Context final HttpServletResponse response, @Context HttpServletRequest req) {
		try {
			 
			String name=UserManage.getLoginUser(req);
			 
			Password inputPass = (Password) Tools.objectFromJsonString(json, Password.class);

			User sessionUser=UserAction.getUserinfo(name);
			// session.removeAttribute(userAttr);
			if (!sessionUser.getPassword().equals(inputPass.oldPassword)) {
				log.debug("old=" + sessionUser.getPassword());
				log.debug("input=" + inputPass.oldPassword);

				return WebTools.Error("旧密码不对！");

			}

			sessionUser.setPassword(inputPass.password);
			 
			sessionUser.setPasswordExpired(0);
			User user = UserAction.modifyUser(sessionUser,true);
			user.setPassword(UserAction.PasswordMask);
			return Tools.getJson(user);

		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getLocalizedMessage());
			return WebTools.Error(e);
		}
	}

	
	private class Password {

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

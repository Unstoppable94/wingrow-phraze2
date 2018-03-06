package com.winhong.cicdweb;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

import com.winhong.plugins.cicd.action.UserAction;
import com.winhong.plugins.cicd.filter.JWTSecurityFilter;
import com.winhong.plugins.cicd.jwt.TokenUtil;
import com.winhong.plugins.cicd.system.Config;
import com.winhong.plugins.cicd.system.InnerConfig;
import com.winhong.plugins.cicd.system.RancherConfig;
import com.winhong.plugins.cicd.system.RegistryConfig;
import com.winhong.plugins.cicd.system.RegistryMirrorConfig;
import com.winhong.plugins.cicd.system.SonarConfig;
import com.winhong.plugins.cicd.tool.Tools;
import com.winhong.plugins.cicd.user.User;
import com.winhong.plugins.cicd.view.UserView;

@Path("/user")
@Consumes("application/json;charset=UTF-8")
public class UserManage {

	public UserManage() {
		// TODO Auto-generated constructor stub
	}

	private static final Logger log = LoggerFactory.getLogger(UserManage.class);

	@GET
	@Produces("application/json;charset=UTF-8")
	public String listAllUser(@QueryParam("username") String username,@QueryParam("role") String role,@QueryParam("userType") String type,
			@QueryParam("firstResult") int firstResult,
			@QueryParam("maxResult") int maxResult) {
		try {

			log.debug("firstResult:" + firstResult + "maxResult:" + maxResult);
			String ret=Tools.ToUTF8(UserView.getUserList(username,role,type, firstResult,
					maxResult));
			log.debug("ret:"+ret);
			
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getLocalizedMessage());
			return WebTools.Error(e);
		}
	}

	

	

	@POST
	@Produces("application/json;charset=UTF-8")
	@Consumes("application/json")
	public String addUser(String json) {
		try {
			User inputUser = (User) Tools.objectFromJsonString(
					json, User.class);

			if (UserAction.userExist(inputUser.getUsername())) {
				log.debug("创建失败，用户已经存在:" + inputUser.getUsername());

				return WebTools.Error("创建失败，用户已经存在");
				
			}
			User user = UserAction.addUser(inputUser);
			user.setPassword(UserAction.PasswordMask);
			return Tools.getJson(user);

		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getLocalizedMessage());
			return WebTools.Error(e);
		}
	}

	
	@GET
	@Path("/{username}")
	@Produces("application/json;charset=UTF-8")
	@Consumes("application/json")
	public String getUser(@PathParam("username") String username) {
		try {
			 
			User user = UserAction.getUserinfo(username);

			return Tools.getJson(user);

		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getLocalizedMessage());
			return WebTools.Error(e);
		}
	}
	
	@PUT
	@Path("/{username}")
	@Produces("application/json;charset=UTF-8")
	@Consumes("application/json")
	public String ModifyUser(@PathParam("username") String username,@QueryParam("action") String action,
			String json,@Context HttpServletRequest req) {
		try {
			
			if (action!=null && action.equalsIgnoreCase("resetpassword")) {
				return "{\"newPassword\":\""+UserAction.resetPassword(username)+"\"}";
			}
			
			String LoginUser = getLoginUser(req);
 			 
			
			User input=(User) Tools
					.objectFromJsonString(json, User.class);
			
			if(input != null && input.getUsername().equals(UserAction.defaultAdmin) && input.getUserType().equals(User.LOCAL)) {
				input.setUserType(User.LDAP);
			}
			
			boolean pa=false;
			if (LoginUser!=null && LoginUser.equals(username))
					pa=true;
			else {
				String passowrd=input.getPassword();
				if (passowrd!=null && passowrd.equals(UserAction.PasswordMask)==false) {
					return WebTools.Error("除用户本身外，不允许修改用户密码！");
				}
			}
			User user = UserAction.modifyUser(input,pa);

			return Tools.getJson(user);

		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getLocalizedMessage());
			return WebTools.Error(e);
		}
	}

	
	@DELETE
	@Path("/{username}")
	@Produces("application/json;charset=UTF-8")
	@Consumes("application/json")
	public String deleteUser(@PathParam("username") String username,
			String json) {
		try {
			log.debug("delete user"+username);
			if (UserAction.deleteUser(username))
				return "{}";
			return WebTools.Error("删除失败，unknown error");
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getLocalizedMessage());
			return WebTools.Error(e);
		}
	}

	public static String getLoginUser( HttpServletRequest req) {
		String head=req.getHeader(JWTSecurityFilter.AuthHeader);
		if (head==null)
				return null;
		String jwsToken = JWTSecurityFilter.extractJwtTokenFromAuthorizationHeader(
				head);
		return TokenUtil.getName(jwsToken);
	}
}

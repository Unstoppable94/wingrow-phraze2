package com.winhong.plugins.cicd.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ContainerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winhong.plugins.cicd.uapconfig.UapUtils;
import com.winhong.uap.cas.client.authentication.AttributePrincipal;
import com.winhong.uap.cas.client.util.AbstractCasFilter;
import com.winhong.uap.cas.client.validation.Assertion;
import com.winhong.uap.sdk.common.Paginate;
import com.winhong.uap.sdk.model.User;

public class UAPUserMange {
	
	final static Logger logger =LoggerFactory.getLogger(UAPUserMange.class);
	private static final int LOCAL_USER = 0;
	private static final int LDAP_USER = 1;
	private static final String ADMIN_ROLE = "wingrow管理员";
	private static final String OPERATOR_ROLE = "wingrow普通用户";
	//获取本系统id
	//获取UAP登录的账户名称
	public static String getUsername(HttpServletRequest req){
		String userName = null;
        Assertion assertion = (Assertion)req.getSession().getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
        /*if (assertion == null) {
            req.getSession().invalidate();
            //throw new RuntimeException("用户不存在，请重新登录！");
        }*/
        if (assertion != null) {
        	Object obj = assertion.getPrincipal();
            if(obj != null){
                userName = ((AttributePrincipal)obj).getName();
            	//0本地用户，1ad用户
                //accountType = userName.substring(0, 1);
                //account = userName.substring(2);
            }
        }
		return userName;
	}
	
	//获取UAP用户在应用系统的相关权限
	public static void getUser(String userName, HttpServletRequest req ,HttpServletResponse res){
		//
		String method = req.getMethod().toLowerCase();
		String path = req.getRequestURI().toLowerCase();
		String accountType = userName.substring(0, 1);
		String account = userName.substring(2);
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", account);
		//系统id会写入配置文件
		params.put("systemId", "102");
		if(Integer.parseInt(accountType) == LOCAL_USER){
			Paginate<User> page = UapUtils.getRestClientV1().locals().users().getUsers(params);
			List<? extends User> users = page.getData();
			if(users != null && users.size() > 0){
				//如果获取到对应系统的账户，查看根据roleNames进行权限的下一步操作
				String roleNames = users.get(0).getRoleNames();
				System.out.println("ok:::::" + roleNames);
				try {
					if (!priviliegeCheck(path, method, roleNames, req)) 
						throw new WebApplicationException(Response.Status.FORBIDDEN);
				} catch (IOException e) {
					e.printStackTrace();
				} //403
//				res.addCookie(new Cookie("account", account));
//				res.addCookie(new Cookie("accountType", accountType));
			}else{
				try {
					res.sendRedirect("/error.html");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		}else{
			List<? extends User> users = UapUtils.getRestClientV1().ldaps().ldapUsers().getLdapUsers(params);
			if(users != null && users.size() > 0){
				
				String roleNames = users.get(0).getRoleNames();
				System.out.println("ok:::::" + roleNames);
				try {
					if (!priviliegeCheck(path, method, roleNames, req)) 
						throw new WebApplicationException(Response.Status.FORBIDDEN);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				try {
					res.sendRedirect("/error.html");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//System.out.println(roleId);
	}
	private static boolean priviliegeCheck(String path,
			String method,String roleNames,HttpServletRequest requestContext) throws IOException { 
		logger.debug("role:" + roleNames);
		if (roleNames.endsWith(ADMIN_ROLE))
			return true;
		

		// 禁止修改用户
		if (roleNames.endsWith(OPERATOR_ROLE)) {
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
	
	public static void main(String[] args) {
		for(int i=0;i<10;i++){
			Map<String, String> params = new HashMap<String, String>();
			params.put("id", "admin");
			params.put("systemId", "dddd");
			Paginate<User> page = UapUtils.getRestClientV1().locals().users().getUsers(params);
			List<? extends User> users = page.getData();
			if(users != null && users.size() > 0){
				System.out.println("ok"+users.get(0).getRoleNames());
			}

		}
	}
}

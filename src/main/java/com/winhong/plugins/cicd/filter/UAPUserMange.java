package com.winhong.plugins.cicd.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.winhong.plugins.cicd.uapconfig.UapUtils;
import com.winhong.uap.cas.client.authentication.AttributePrincipal;
import com.winhong.uap.cas.client.util.AbstractCasFilter;
import com.winhong.uap.cas.client.validation.Assertion;
import com.winhong.uap.sdk.common.Paginate;
import com.winhong.uap.sdk.model.User;

public class UAPUserMange {
	
	/*private static RestClientV1 rest = UapUtils.getRestClientV1();
	private static UserService userService = rest.locals().users();*/
	
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
	public static void getUser(String userName, HttpServletResponse res){
		//
		String accountType = userName.substring(0, 1);
		String account = userName.substring(2);
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", account);
		params.put("systemId", "102");
		if(Integer.parseInt(accountType) == 0){
			Paginate<User> page = UapUtils.getRestClientV1().locals().users().getUsers(params);
			List<? extends User> users = page.getData();
			if(users != null && users.size() > 0){
				System.out.println("ok"+users.get(0).getRoleNames());
			}else{
		        if(account.equals("admin")) {
	        	try {
					res.sendRedirect("/error.html");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }

			}
		}else{
			List<? extends User> users = UapUtils.getRestClientV1().ldaps().ldapUsers().getLdapUsers(params);
			if(users != null && users.size() > 0){
				System.out.println("ok"+users.get(0).getRoleNames());
			}
		}
		//System.out.println(roleId);
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

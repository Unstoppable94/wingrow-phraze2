package com.winhong.cicdweb;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.winhong.plugins.cicd.action.UserAction;
import com.winhong.plugins.cicd.jwt.Token;
import com.winhong.plugins.cicd.jwt.TokenUtil;
import com.winhong.plugins.cicd.tool.RandomString;
import com.winhong.plugins.cicd.tool.Tools;

 
public class LoginTest extends Login {
	Login login =new Login();
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		RandomString.init(16);
	}

	@Test
	public void testLogin() {
		try {
			String s=login.login( "{\"username\":\"admin\",\"password\":\"admin\"}");
			Token token=(Token) Tools.objectFromJsonString(s, Token.class);
			System.out.println(s);
			System.out.println(TokenUtil.getName(token.getAuthorization()));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
 	}

	@Test
	public void testLoginWithTempPass() {
		try {
			 String pass=UserAction.resetPassword("admin");
			String s=login.login( "{\"username\":\"admin\",\"password\":\""+pass+"\"}");
			Token token=(Token) Tools.objectFromJsonString(s, Token.class);
			System.out.println(s);
			System.out.println(TokenUtil.getName(token.getAuthorization()));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
 	}

	
	@Test
	public void testLoginLdap() {
		try {
			String s=login.login( "{\"username\":\"caiwl\",\"password\":\"root@123\"}");
			Token token=(Token) Tools.objectFromJsonString(s, Token.class);
			System.out.println(s);
			System.out.println(TokenUtil.getName(token.getAuthorization()));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
 	}
	
	@Test
	public void testLoginLdapfail() {
		try {
			String s=login.login( "{\"username\":\"caiwl\",\"password\":\"root@1223\"}");
			Token token=(Token) Tools.objectFromJsonString(s, Token.class);
			System.out.println(s);
			System.out.println(TokenUtil.getName(token.getAuthorization()));
			
		} catch (Exception e) {
			 
			e.printStackTrace();
		}
		
		
		fail();
	}
	@Test
	public void testLogout() {
		fail("Not yet implemented");
	}

	
	 
	
}

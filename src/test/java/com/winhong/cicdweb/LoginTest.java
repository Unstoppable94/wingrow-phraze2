package com.winhong.cicdweb;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.winhong.plugins.cicd.jwt.Token;
import com.winhong.plugins.cicd.jwt.TokenUtil;
import com.winhong.plugins.cicd.tool.Tools;

 
public class LoginTest extends Login {
	Login login =new Login();
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
		
		
		fail("Not yet implemented");
	}

	@Test
	public void testLogout() {
		fail("Not yet implemented");
	}

	
	 
	
}

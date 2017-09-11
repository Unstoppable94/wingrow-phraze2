package com.winhong.plugins.cicd.jwt;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class TokenUtilTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@Test
	public void testGetJWTStringStringStringArray() {
		String[] roles= {"test"}; 
		
		String t=TokenUtil.getJWTString("test", roles);
		System.out.println(t);
		if (TokenUtil.getName(t).equals("test")==false)
			fail("get name");
		if (TokenUtil.getRoles(t)[0].equals(roles[0])==false)
			fail("get name");
	}

	@Test
	public void testGetJWTStringStringStringArrayInt() {
		String[] roles= {"test"}; 
		
		String t=TokenUtil.getJWTString("test",roles, 10, 1);
		
		System.out.println(t);
		if (TokenUtil.getVersion(t)!=10)
			fail("getVersion");
		if (TokenUtil.isValid(t)==false) {
			fail("isValid");
		}
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			 
			e.printStackTrace();
			fail();
		}
		if (TokenUtil.isValid(t)) {
			fail("isValid");
		}
		
	}
 

}

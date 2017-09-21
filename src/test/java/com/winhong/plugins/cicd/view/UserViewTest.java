package com.winhong.plugins.cicd.view;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.winhong.plugins.cicd.action.UserAction;
import com.winhong.plugins.cicd.user.User;

public class UserViewTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		for (int i=0;i<10;i++){
			User user=new User();
			user.setUsername("测试"+i);
			UserAction.addUser(user);
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		for (int i=0;i<10;i++){
			User user=new User();
			user.setUsername("测试"+i);
			UserAction.deleteUser(user.getUsername());
		}
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetUserListIntInt() {
		 try {
			 System.out.print(
					 "Uselist="+
					 UserView.getUserList("测试2222",null,null,0, -1));
			 
		} catch (Exception e) {  
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testGetUserListStringIntInt() {
		 try {
				System.out.print(UserView.getUserList("admin",User.operatorRole,User.LOCAL,0, -1));
			} catch (Exception e) {  
				e.printStackTrace();
				fail();
			}
	}

}

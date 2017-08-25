package com.winhong.plugins.cicd.action;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URLEncoder;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.winhong.plugins.cicd.data.base.ProjectGroupJsonConfig;

public class GroupActionTest extends GroupAction {

	static ProjectGroupJsonConfig group=new ProjectGroupJsonConfig();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		group.setId("group1");
		group.setName("组一");
		group.setDescription("描述");
	
 		
 	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateOrModifyGroup() {
		try {
			GroupAction.createGroup(group);
			group.setName("ss");
			GroupAction.modifyGroup(group);
			//GroupAction.deleteGroup(group.getId());
		} catch (IOException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
 	}

//	@Test
//	public void testChangeProjectFromGroup() {
//		try {
//			GroupAction.changeGroupOfProject("maskio", "test","默认");
//
//			GroupAction.changeGroupOfProject("maskio", "默认","test");
//		} catch (IOException e) {
// 			e.printStackTrace();
// 			fail();
//		}
// 	}
//
//	@Test
//	public void deleteGroup() {
//		try {
//			GroupAction.deleteGroup(  "test");
//
// 		} catch (IOException e) {
// 			e.printStackTrace();
// 			fail();
//		}
// 	}
//	
	
	
}

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

import junit.framework.Assert;

public class GroupActionTest extends GroupAction {

	static ProjectGroupJsonConfig group=new ProjectGroupJsonConfig();
	
	//批量增加group
	@Test
	public static void addGroupBatch() throws Exception, IllegalAccessException, IOException{
		for(int i=1; i<=12; i++){
			ProjectGroupJsonConfig group = new ProjectGroupJsonConfig();
			group.setDescription("test");
			group.setName("group"+i);
			group.setId("id"+i);
			GroupAction.createGroup(group);
		}
	}
	
	@Test
	public void addGroup() throws Exception, IllegalAccessException, IOException{
			ProjectGroupJsonConfig group = new ProjectGroupJsonConfig();
			group.setDescription("test");
			group.setName("group"+13);
			group.setId("id"+13);
			boolean result = GroupAction.createGroup(group);
			Assert.assertTrue(result);
	}
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		group.setId("group12");
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
			GroupAction.deleteGroup(group.getId());
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

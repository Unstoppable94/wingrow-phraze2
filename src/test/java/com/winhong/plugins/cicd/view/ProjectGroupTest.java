package com.winhong.plugins.cicd.view;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.winhong.plugins.cicd.action.GroupAction;
import com.winhong.plugins.cicd.data.base.ProjectGroupJsonConfig;

public class ProjectGroupTest extends ProjectGroup {

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
	public void testListAllGroup() {
		try {
			System.out.println(ProjectGroup.listAllGroup(0,10 ));
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
	}
	
	
	 
	
	@Test
	public void testgetProjectStatus() {
		try {
			System.out.println(ProjectGroup.getProjectGroupStatInfo("default"));
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	public void testlistAllGroup() {
		try {
			GroupAction.createGroup(group);
			
			GroupAction.modifyGroup(group);
			System.out.println("ProjectGroup==="+ProjectGroup.listAllGroup(null,0,2));
			
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}finally{
			try {
				GroupAction.deleteGroup(group.getId());
			} catch (IOException | InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
	}
}

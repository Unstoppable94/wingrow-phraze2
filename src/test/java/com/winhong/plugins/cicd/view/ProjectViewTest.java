package com.winhong.plugins.cicd.view;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProjectViewTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
	public void testGetBuildsInfoSingle() {
		ProjectView v=new ProjectView("maskio");
		try {
			String s=v.getSingleBuildsInfo("maskio",214);
			System.out.println(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("testGetBuildsInfo");
		}
		
		
	}

	
	@Test
	public void testGetBuildsInfo() {
 		try {
			String s=ProjectView.getBuildsInfo("maskio",0, 0);
			System.out.println(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("testGetBuildsInfo");
		}
		
		
	}
	
	@Test
	public void testGetBuildsInfo2() {
		ProjectView v=new ProjectView("maskio");
		try {
			String s=v.getBuildsInfo("maskio",0, 0,"FAILURE","");
			System.out.println(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("testGetBuildsInfo");
		}
		
		
	}
	
	@Test
	public void testGetBuildsInfo3() {
		ProjectView v=new ProjectView("maskio");
		try {
			String s=v.getBuildsInfo("maskio",0, 0,"FAILURE","Sonar");
			System.out.println(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("testGetBuildsInfo");
		}
		
		
	}
	
	@Test
	public void testGetBuildsInfo4() {
		ProjectView v=new ProjectView("maskio");
		try {
			String s=v.getBuildsInfo("maskio",0, 0,"","sonar");
			System.out.println(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("testGetBuildsInfo");
		}
		
		
	}
	
	@Test
	public void testgetRecentFailRuns() {
		ProjectView v=new ProjectView("maskio");
		try {
			String s=v.getRecentFailRuns();
			System.out.println(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
		
	}
	
	@Test
	public void testGetAllRecentRuns() {
		ProjectView v=new ProjectView("maskio");
		try {
			String s=v.getRecentAllRuns();
			System.out.println(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
		
	}
	
	@Test
	public void getRecentSucessRuns() {
		ProjectView v=new ProjectView("maskio");
		try {
			String s=v.getRecentSucessRuns();
			System.out.println(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
		
	}
	
	
	@Test
	public void testListAllProject() {
		try {
			System.out.println(ProjectView.getProjectList(0, 0));
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	public void testsearchAllProject() {
		try {
			System.out.println(ProjectView.getProjectList("maskio",0, 0));
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	public void testgetSingleRun() {
		try {
			System.out.println(ProjectView.getSingleRun("maskio",214));
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
	}
	
	
}

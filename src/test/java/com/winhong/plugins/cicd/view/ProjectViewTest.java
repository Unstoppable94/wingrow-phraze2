package com.winhong.plugins.cicd.view;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.winhong.plugins.cicd.system.ProjectType;

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
		ProjectView v=new ProjectView("pro1503915201251");
		try {
			String s=v.getSingleBuildsInfo("pro1503915201251",1);
			System.out.println(s);
		} catch (Exception e) {
			 
			e.printStackTrace();
			fail("testGetBuildsInfo");
		}
		
		
	}

	
	@Test
	public void testGetBuildsInfo() {
 		try {
			String s=ProjectView.getBuildsInfo("pro1503915201251",0, 0);
			System.out.println(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("testGetBuildsInfo");
		}
		
		
	}
	
	@Test
	public void testGetBuildsInfo2() {
		ProjectView v=new ProjectView("pro1503915201251");
		try {
			String s=v.getBuildsInfo("pro1503915201251",0, 0,"FAILURE","");
			System.out.println(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("testGetBuildsInfo");
		}
		
		
	}
	
	@Test
	public void testGetBuildsInfo3() {
		ProjectView v=new ProjectView("pro1503915201251");
		try {
			String s=v.getBuildsInfo("pro1503915201251",0, 0,"FAILURE","Sonar");
			System.out.println(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("testGetBuildsInfo");
		}
		
		
	}
	
	@Test
	public void testGetBuildsInfo4() {
		ProjectView v=new ProjectView("pro1503915201251");
		try {
			String s=v.getBuildsInfo("pro1503915201251",0, 0,"","sonar");
			System.out.println(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("testGetBuildsInfo");
		}
		
		
	}
	
	@Test
	public void testgetRecentFailRuns() {
		ProjectView v=new ProjectView("pro1503915201251");
		try {
			String s=v.getRecentFailRuns();
			System.out.println(s);
		} catch (IOException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
		
	}
	
	@Test
	public void testGetAllRecentRuns() {
		ProjectView v=new ProjectView("pro1503915201251");
		try {
			String s=v.getRecentAllRuns();
			System.out.println(s);
		} catch (IOException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
		
	}
	
	@Test
	public void getRecentSucessRuns() {
		ProjectView v=new ProjectView("pro1503915201251");
		try {
			String s=v.getRecentSucessRuns();
			System.out.println(s);
		} catch (IOException | InstantiationException | IllegalAccessException e) {
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
			System.out.println(ProjectView.getProjectList("","",0, 0));
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
	}
	
	
	@Test
	public void testsearchAllProjectByName() {
		try {
			System.out.println(ProjectView.getProjectList("docker","",0, 0));
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	public void testsearchSpecTypeProject() {
		try {
			System.out.println(ProjectView.getProjectList("",ProjectType.MavenProject,0, 0));
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	public void testgetSingleRun() {
		try {
			System.out.println(ProjectView.getSingleRun("pro1503915201251",1));
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
	}
	
	
}

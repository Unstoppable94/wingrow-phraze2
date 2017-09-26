package com.winhong.cicdweb;

import static org.junit.Assert.*;

import javax.ws.rs.PathParam;

import org.junit.BeforeClass;
import org.junit.Test;

public class ProjectRestTest extends ProjectRest {
	static ProjectRest rest;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		rest=new ProjectRest();
	}

	@Test
	public void testProjectRest() {
		fail("Not yet implemented");
	}

	@Test
	public void testListAllProject() {
		System.out.println(rest.listAllProject("docker", "", 0, 0, null));
 	}

	
	@Test
	public void testListAllProjectByType() {
		System.out.println(rest.listAllProject("", "mavenProject", 0, 0, null));
 	}
	@Test
	public void testTriggerProjectBuild() {
		fail("Not yet implemented");
	}

	@Test
	public void testListSingleProject() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddProject() {
		fail("Not yet implemented");
	}

	@Test
	public void testModifyProject() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteProject() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetProjectbuidinfo() {
		System.out.println(rest.getProjectbuidinfo("pro1505966862278", 4, "", "", 0, 10));
	}

	@Test
	public void testGetProjectSpecialBuild() {
		System.out.println(rest.getProjectSpecialBuild("pro1505966862278", 4));
	}

}

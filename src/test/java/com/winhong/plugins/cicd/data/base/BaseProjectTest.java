package com.winhong.plugins.cicd.data.base;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.winhong.plugins.cicd.system.ProjectType;

public class BaseProjectTest  {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testNewProject() {
		try {
			System.out.println(BaseProject.NewProject());
		} catch (InstantiationException | IllegalAccessException e) {
 			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testNewProjectString() {
		try {
			BaseProject.NewProject(ProjectType.TraditionalDocker);
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testGetInitWorkflow() {
		try {
			BaseProject.getInitWorkflow(ProjectType.TraditionalDocker);
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testGetWorkflow() {
		fail("Not yet implemented");
	}

}

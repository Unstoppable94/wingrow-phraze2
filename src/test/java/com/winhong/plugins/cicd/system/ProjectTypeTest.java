package com.winhong.plugins.cicd.system;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import com.winhong.plugins.cicd.data.base.EnumList;
import com.winhong.plugins.cicd.tool.Tools;

public class ProjectTypeTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testGetClassString() {
		
		System.out.println(ProjectType.getClass(ProjectType.MultistageDocker));

	}

	@Test
	public void testGetGroovy() {
		System.out.println(ProjectType.getConfigXml(ProjectType.MavenProject));

		//fail("Not yet implemented");
	}

	@Test
	public void testGetProjectTypeList() {
		ArrayList<EnumList> w = ProjectType.getProjectTypeList();
		System.out.println(Tools.getJson(w));
		//fail("Not yet implemented");
	}

}

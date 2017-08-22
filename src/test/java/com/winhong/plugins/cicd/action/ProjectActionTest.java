package com.winhong.plugins.cicd.action;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.winhong.plugins.cicd.Maven.MavenProject;
import com.winhong.plugins.cicd.Maven.MavenProjectBaseInfo;
import com.winhong.plugins.cicd.tool.Tools;
import com.winhong.plugins.cicd.view.displayData.DisplayBuild;

public class ProjectActionTest {
	static MavenProject p=new MavenProject();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
 		MavenProjectBaseInfo b=new MavenProjectBaseInfo();
		b.setCreateTime(System.currentTimeMillis());
		b.setDescription("project111");
		b.setExraProperties("");
		b.setGroupId("default");
		b.setId("");
		b.setName("project222");
		b.setJdk("Jdk1.7");
		b.setMavenId("Maven-3.3.9");
		b.setLastModifyTime(b.getCreateTime());
		b.setMailOnfail("mailoffail@w.com");
		b.setMailOnReovery(null);
		b.setMailOnSuccess(null);
		b.setMaxExcutiontime(60);
 		b.setSCMBranch("master");
		b.setSCMPassword("acd12345");
		b.setSCMTYPE("git");
		b.setSCMUrl("http://10.0.2.50:180/winhong/ciexample.git");
		b.setSCMUser("xiehq");
		b.setTrigger("");
		b.setTriggerProperty("5 * * * *");
		p.setBaseInfo(b);
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
	public void testAddOrModifyMavenProject() {
		
		try {
			ProjectAction.AddMavenProject(p);
			p.getBaseInfo().setGroupId("default");
		//	ProjectAction.ModifyMavenProject(p);
		} catch (Exception e) {
 			e.printStackTrace();
			fail();
		}
		
 	}

	@Test
	public void testDeleteMavenProject() {
		try {
			p.getBaseInfo().setName("delete中文Poject2");
			ProjectAction.AddMavenProject(p);
			 
			ProjectAction.DeleteMavenProject(p.getBaseInfo().getId());
		} catch (Exception e) {
 			e.printStackTrace();
			fail();
		}
	}

	
	@Test
	public void charSet(){
		String n="默认";
		try {
			System.out.println(URLEncoder.encode(n));

			System.out.println(URLEncoder.encode(n, "UTF-8"));
			System.out.println(URLEncoder.encode("TEST", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	 
	@Test
	public void testTriiger() {
		try {
			p.getBaseInfo().setName("delete中文Poject2");
			ProjectAction.AddMavenProject(p);
		  ProjectAction.triggerBuild(p.getBaseInfo().getId()); 
 			ProjectAction.DeleteMavenProject(p.getBaseInfo().getId());
		} catch (Exception e) {
 			e.printStackTrace();
			fail();
		}
	}
	
	//public static DisplayBuild    triggerBuild(String projectName) 
	
}

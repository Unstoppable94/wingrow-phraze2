package com.winhong.plugins.cicd.tools;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.winhong.plugins.cicd.action.ProjectAction;
import com.winhong.plugins.cicd.data.base.ProjectBaseInfo;
import com.winhong.plugins.cicd.data.base.Trigger;
import com.winhong.plugins.cicd.maven.MavenProject;
 import com.winhong.plugins.cicd.tool.JenkinsClient;
import com.winhong.plugins.cicd.tool.Tools;

public class JenkinsClientTest {
	
	private static JenkinsClient client;
	private static Trigger period=new Trigger("period","1");
	private static Trigger crontab=new Trigger("crontab","* * * * * ");
	private static Trigger manual=new Trigger("manual","* * * * * ");
	private static MavenProject p=new MavenProject();
	
	
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		ProjectBaseInfo b=new ProjectBaseInfo();
		b.setCreateTime(System.currentTimeMillis());
		b.setDescription("中文project111");
		b.setExraProperties("");
		b.setGroupId("default");
		b.setId("TestPROJ"+System.currentTimeMillis());
		b.setName(b.getId()); 
		b.setLastModifyTime(b.getCreateTime());
		b.setMailOnfail("mailoffail@w.com");
		b.setMailOnReovery(null);
		b.setMailOnSuccess(null);
		b.setMaxExcutiontime(60);
		b.setSCMBranch("master");
		b.setSCMPassword("sCMPassword");
		b.setSCMTYPE("git");
		b.setSCMUrl("sCMUrl");
		b.setSCMUser("sCMUser");
		b.setTrigger("crontab");
		b.setTriggerProperty("* * * * *");
	
		p.setBaseInfo(b);
		client =JenkinsClient.defaultClient();
				//new JenkinsClient("http://10.211.55.6:28080","xiehq","acd12345");
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
	public void testAddOrModifyJob() {
		MavenProject p=new MavenProject();
		p.getBaseInfo().setTrigger(period.getType());
		p.getBaseInfo().setTriggerProperty(period.getContent());
		
		String jobId="aMtempProject"+(new Date().getTime());
		String s=Tools.getJson(p);
		 try {
		
			 if (!client.addOrModifyJob(jobId,s)){
				 fail("addOrModifyJob fail");
			 }
			 
			 if (!client.addOrModifyJob(jobId,s)){
				 fail("addOrModifyJob 2");
			 }
			
			 if (!client.deleteJob(jobId)){
				 fail("delete addOrModifyJob fail");

			 }
		} catch (IOException e) {
			e.printStackTrace();
			 fail("add job fail");
		}	}

	
	@Test
	public void testModifyJob() {
		MavenProject p=new MavenProject();
		p.getBaseInfo().setTrigger(period.getType());
		p.getBaseInfo().setTriggerProperty(period.getContent());
		
		 
		String s=Tools.getJson(p);
		
		String jobId="MtempProject"+(new Date().getTime());
		 try {
			if (!client.addJob(jobId,s)){
				 fail("add job fail");
			 }
			
			if (!client.modifyJob(jobId,s)){
				 fail("add job fail");
			 }
			
			if (!client.jobExist(jobId)){
				fail("testJobExist 1");
				
			}else{
				System.err.println(jobId+"exist");
			}
			
			 if (!client.deleteJob(jobId)){
				 fail("delete job fail");

			 }
		} catch (IOException e) {
			e.printStackTrace();
			 fail("add job fail");
		}
	}

	@Test
	public void testAddJob()   {
		 String jobId="tempProject"+(new Date().getTime());
		 MavenProject p=new MavenProject();
			p.getBaseInfo().setTrigger(period.getType());
			p.getBaseInfo().setTriggerProperty(period.getContent());
			
			 
			String s=Tools.getJson(p);
			
		 try {
			if (!client.addJob(jobId,s)){
				 fail("add job fail");
			 }
			 if (!client.deleteJob(jobId)){
				 fail("delete job fail");

			 }
		} catch (IOException e) {
 			e.printStackTrace();
 			 fail("add job fail");
		}
		
		 
	}

	@Test
	public void testAddJob2()   {
		 String jobId="tempProject"+(new Date().getTime());
		 MavenProject p=new MavenProject();
			p.getBaseInfo().setTrigger(period.getType());
			p.getBaseInfo().setTriggerProperty(period.getContent());
			
			 
			String s=Tools.getJson(p);
			
		 try {
			if (!client.addJob(jobId,s)){
				 fail("add job fail");
			 }
			
		} catch (IOException e) {
 			e.printStackTrace();
 			 fail("add job fail");
		}
		
		 
	}
	@Test
	public void testJobExist() {
		try {
			if (client.jobExist("test2")){
				fail("testJobExist 1");
				
			}
			if (!client.jobExist("maskio")){
				fail("testJobExist 2");
				
			}
		} catch (IOException e) {
			 
			e.printStackTrace();
			fail("testJobExist"+e.getLocalizedMessage());
		}
	}

	@Test 
	public void testgetCrumb() {
		try {
			if (client.getCrumb()==false)
				fail("get crumb");
		} catch (IOException e) {
			 
			e.printStackTrace();
			fail("crumb"+e.getLocalizedMessage());
		}
	}
	
	@Test 
	public void testaddOrModifyView() {
		try {
			if (client.addOrModifyView("test22", "des"))
				fail("get crumb");
		} catch (IOException e) {
			 
			e.printStackTrace();
			fail("crumb"+e.getLocalizedMessage());
		}
	}
	
	@Test 
	public void testaddJobToView() {
		try {
			if (!client.addJobToView("test22", "maskio"))
				fail("get testaddJobToView");
		} catch (IOException e) {
			 
			e.printStackTrace();
			fail("testaddJobToView"+e.getLocalizedMessage());
		}
	}
	
	
	@Test 
	public void testremoveJobFromView() {
		try {
			if (!client.removeJobFromView("test22", "maskio"))
				fail("get testremoveJobFromView");
		} catch (IOException e) {
			 
			e.printStackTrace();
			fail("testremoveJobFromView"+e.getLocalizedMessage());
		}
	}
	
	
	@Test 
	public void testcreateCredential() {
		try {
			if (!client.createCredential("test22", "maskio","pass","test"))
				fail("get testcreateCredential");
		} catch (IOException e) {
			 
			e.printStackTrace();
			fail("testremoveJobFromView"+e.getLocalizedMessage());
		}
	}
 
	@Test 
	public void testTrigger() {
		 
		String s=Tools.getJson(p);
		
		 try {
		
			 if (!ProjectAction.AddProject(p)){
				 fail("addOrModifyJob fail");
			 }
			 
			 System.out.println("ret"+client.triggerBuild(p.getBaseInfo().getId()));
			
//			 if (!client.deleteJob(p.getBaseInfo().getId())){
//				 fail("delete addOrModifyJob fail");
//
//			 }
		} catch (IOException e) {
			e.printStackTrace();
			 fail("add job fail");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		 
	}

	 
	@Test 
	public void testhttpSimpleGet() { 
		try {
			System.out.println(client.httpSimpleGet("/api/json?tree=jobs[name]"));
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}
}

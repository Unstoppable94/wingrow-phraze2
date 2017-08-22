package com.winhong.plugins.cicd.system;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.winhong.plugins.cicd.tool.Tools;

public class ConfigTest {

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
	public void testDefaultConfig() {
		try {
			 RancherConfig ran=new RancherConfig();
			 SonarConfig sonar=new SonarConfig();
			 RegistryMirrorConfig mirror=new RegistryMirrorConfig();
			 SMTPConfig smtp=new SMTPConfig();
			 
			 mirror.setScure(false);
			 mirror.setUrl("http://10.0.2.50");
			 smtp.setHost("smtp.263.net");
			 smtp.setUser("xiehq");
			 smtp.setPassword("Acd12345");
			 
			 ran.setAccessKey("BEFFE0ACF8339FFB5861");
			 ran.setSecureKey("UDbYKhPMY8eHDASoEnLF9kQCX96K6QhuENWgZxam");
			 ran.setServerUrl("http://192.168.217.225:8080");
			 ran.setEnvID("1a5");
			 
			 sonar.setSonarUrl("http://10.0.2.50:9000");
			 sonar.setUser("xiehq");
			 sonar.setPassword("acd12345");
			 
			 Config.saveConfig(ran);
			 Config.saveConfig(mirror);
			 Config.saveConfig(sonar);
			 Config.saveConfig(smtp);
			 
			 System.out.println(Tools.getJson(Config.getRancherConfig()));
			 
			 System.out.println(Tools.getJson(Config.getRegistryMirrorConfig()));

			 System.out.println(Tools.getJson(Config.getSonarConfig()));

			 System.out.println(Tools.getJson(Config.getSMTPConfig()));
			
		} catch (Exception e) {
 			e.printStackTrace();
 			fail("get default");
		}
				
	}

	 
	@Test
	public void testGenEnumList() {
		System.out.println(Tools.getJson(Config.genRegistryEnumList()));
 	}

	@Test
	public void testGetRegistryConfig() {
		
		RegistryConfig f;
		try {
			f = Config.getRegistryConfig("10.0.2.50");
			//System.out.println(RegistryConfig.genEnumList());
			if (f==null)
			fail("Not yet implemented");
			else 
				System.out.println(f.getAuth());	
		} catch (FileNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
	}

	 
	@Test
	public void testdeleteRegistry() {
		RegistryConfig f=new RegistryConfig("10.0.2.53","testauth","test@testemail.com",true);
		RegistryConfig g=new RegistryConfig("10.0.2.52","testauth","test@testemail.com",false);
		try {
			Config.deleteRegistry(f.getServer());
			Config.deleteRegistry(g.getServer());
		} catch (IOException | InstantiationException | IllegalAccessException e) {
 			e.printStackTrace();
 			fail();
		}
	}

	@Test
	public void testsaveRegistry() {
		RegistryConfig f=new RegistryConfig("10.0.2.52","testauth","test@testemail.com",true);
		RegistryConfig g=new RegistryConfig("10.0.2.52","testauth","g@testemail.com",false);
		try {
			Config.saveRegistry(f);
			f.setReboot(true);
			Config.saveRegistry(g);
		} catch (IOException | InstantiationException | IllegalAccessException e) {
 			e.printStackTrace();
 			fail();
		}
	}
}

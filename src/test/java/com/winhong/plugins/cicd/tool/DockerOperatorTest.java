package com.winhong.plugins.cicd.tool;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.spotify.docker.client.exceptions.DockerException;
import com.winhong.plugins.cicd.tool.DockerOperator;

public class DockerOperatorTest {
	private static DockerOperator p;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		p= new DockerOperator();
		p.init();
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
	public void testBuildImages() {
		
		String myCurrentDir = System.getProperty("user.dir")+"/src/test/resource/";
		System.out.println(myCurrentDir);
		try {
			String b = p.buildImages(myCurrentDir, "10.0.2.50","test/test:test");
			System.err.println("imagename:"+b);
			if (b==null){
				fail("building fail");
			}
		} catch (Exception e) {
			 
			e.printStackTrace();
			fail("building fail");
		}
		
		
		
 	}

	@Test
	public void testPushImages() {
		
		try {
			p.pushImage("10.0.2.50", "eGllaHE6QWNkMTIzNDU=","w@1.com","test/test");
		} catch (Exception e)  {
			e.printStackTrace();
			fail("push fail");

		}
	 
	}
}

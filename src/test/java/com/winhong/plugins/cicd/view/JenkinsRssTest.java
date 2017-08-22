package com.winhong.plugins.cicd.view;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rometools.rome.io.FeedException;
import com.winhong.plugins.cicd.tool.Tools;
import com.winhong.plugins.cicd.view.innerData.RssBuild;

public class JenkinsRssTest {

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
	public void testGetRss() {
	//	fail("Not yet implemented");
	}

	@Test
	public void testGetInfo() {
		 try {
			ArrayList<RssBuild> s = JenkinsRss.getInfo(JenkinsRss.failRss);
			System.out.println(Tools.getJson(s));
		} catch (Exception e) {
 			e.printStackTrace();
 			fail();
		}
		 
	}

}

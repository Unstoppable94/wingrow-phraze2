package com.winhong.plugins.cicd.system;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;

public class JenkinsConfigTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testGetUrl() {
		
		try {
			String rssurl=Config.getJenkinsConfig().getUrl();
			System.out.println(rssurl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
		 
	}

}

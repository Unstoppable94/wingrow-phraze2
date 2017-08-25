package com.winhong.plugins.cicd.property;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.winhong.plugins.cicd.exception.ConfigCheckException;
import com.winhong.plugins.cicd.tool.RancherClient;

public class RancherEnvIdPropertyTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//RancherClient.init("http://192.168.101.81:8080","E16BBCEB675189233FCF","dubRG8jwaWEgcVUkZowaJ1qmy34MzxMhESKFggvo");
		
	}

	@Test
	public void testCheck() {
		RancherEnvIdProperty e= new RancherEnvIdProperty("testid","1a1","WinGarden env","test description");
		try {
			e.check();
			fail();
		} catch (ConfigCheckException e1) {
			e1.printStackTrace();
			
		}
	}

	@Test
	public void testRancherEnvIdPropertyStringStringStringString() {
		RancherEnvIdProperty e= new RancherEnvIdProperty("testid","1a5","WinGarden env","test description");
		try {
			e.check();
		} catch (ConfigCheckException e1) {
			e1.printStackTrace();
			fail();
		}
		
 	}

}

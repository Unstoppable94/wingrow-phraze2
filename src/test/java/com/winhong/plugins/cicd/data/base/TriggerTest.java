package com.winhong.plugins.cicd.data.base;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TriggerTest {

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
	public void test() {
		 Trigger t=new Trigger("commit","");
		 if (!t.getName().equals("有提交就触发"))
			 fail("get fail");
		 
		 
		 t.setType("period");
		 if (!t.getName().equals("间隔固定时间（分钟）"))
			 fail("get fail");
	}

}

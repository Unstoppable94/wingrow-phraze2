package com.winhong.plugins.cicd.tools;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.winhong.plugins.cicd.tool.GitOperator;

public class GitOperatorTest {

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
	public void testGitInit() {
		GitOperator git=new GitOperator();
		try {
			git.gitInit("/Users/xiehuiqiang/git/cicdcore/.git","","xiehq","acd12345");
		} catch (Exception e) {
		 
			e.printStackTrace();
			fail("init fail");
		}
		
		
	}

	@Test
	public void testGitAddAndPush() {
		
		GitOperator git=new GitOperator();
		try {
			git.gitInit("/Users/xiehuiqiang/git/cicdcore/.git","","xiehq","acd12345");
			git.gitAddAndPush();
		} catch (Exception e) {
		 
			e.printStackTrace();
			fail("init fail");
		}
 	}

}

package com.winhong.plugins.cicd.Maven;

import static org.junit.Assert.*;

import org.junit.Test;

import com.winhong.plugins.cicd.maven.MavenWorkflow;

public class MavenWorkflowTest extends MavenWorkflow {

	@Test
	public void testMavenWorkflow() {
		
		MavenWorkflow m=new MavenWorkflow();
		String s=m.getJson();
		System.out.println(s);
		
		MavenWorkflow f=MavenWorkflow.genByJson(s);
		
		System.out.println(f.getJson());
//		fail("Not yet implemented");
	}

	/*
	@Test
	public void testGetJson() {
		fail("Not yet implemented");
	}

	@Test
	public void testGenByJson() {
		fail("Not yet implemented");
	}
	*/

}

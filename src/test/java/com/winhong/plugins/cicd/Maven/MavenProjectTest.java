package com.winhong.plugins.cicd.Maven;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.winhong.plugins.cicd.tool.RancherClient;
import com.winhong.plugins.cicd.tool.Tools;

public class MavenProjectTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	//	RancherClient.init("http://192.168.101.81:8080","E16BBCEB675189233FCF","dubRG8jwaWEgcVUkZowaJ1qmy34MzxMhESKFggvo");

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
	public void testGetJson() {
		MavenProject m=new MavenProject();
 		System.out.println(Tools.getJson(m));
		
 	}

	@Test
	public void testGenByJson() {
		
		//URL url = Thread.currentThread().getContextClassLoader().
		//		getResource("MavenProject.json");
		
		ClassLoader classLoader = getClass().getClassLoader();
 		File file = new File(classLoader.getResource("MavenProject.json").getFile());
		System.out.println(file.getAbsolutePath());
		
		//File file = new File(url.getPath());

		InputStream fis;
		try {
			fis = new FileInputStream(file);
		
		
		//create JsonReader object
		JsonReader jsonReader = new JsonReader(new InputStreamReader(fis));
		
		ExclusionStrategy strategy;
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create();
		MavenProject g=gson.fromJson(jsonReader, MavenProject.class);
		//JsonReader	reader=new JsonReader(null);
		System.out.print(Tools.getJson(g));
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
		
 	}

}

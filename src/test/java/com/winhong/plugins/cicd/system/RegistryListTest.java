package com.winhong.plugins.cicd.system;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.winhong.plugins.cicd.tool.Tools;

public class RegistryListTest {

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
	public void testListSave() {
		
		ArrayList<RegistryConfig> registryList=new ArrayList<RegistryConfig> ();
		RegistryConfig f=new RegistryConfig("10.0.2.50","testauth","test@testemail.com",true);
		RegistryConfig g=new RegistryConfig("10.0.2.51","testauth","test@testemail.com",false);
		registryList.add(f);
		registryList.add(g);
		RegistryList t=new RegistryList();
		t.setRegistries(registryList);
		try {
			Config.saveConfig(t);
		} catch (IOException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			fail();
		}
		
		 
		
		
		
	}
	
	@Test
	public void testDefaultConfig() throws IOException {
		
		try {
			ArrayList<RegistryConfig> list = Config.getRegistryList().getRegistries();
			System.out.println("json--"+Tools.getJson(list));

		} catch (FileNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			fail("testDefaultConfig");
		}
		
	}

	
}

package com.winhong.plugins.cicd.view.innerData;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.winhong.plugins.cicd.tool.Tools;

public class StatusOfStatTest {

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
	public void testGetStatus() {
		ArrayList<StatusOfStat> status=new ArrayList<StatusOfStat>();
		status.add(new StatusOfStat("SUCC",122) );
		status.add(new StatusOfStat("fail",122) );
		System.out.println(Tools.getJson(status));
		
 		System.out.println(Tools.getJson(status));
		
	}

	@Test
	public void testSetStatus() {
		ArrayList<StatusOfStat> status=new ArrayList<StatusOfStat>();

		String json="[{\"status\":\"SUCC\",\"number\":122},{\"status\":\"fail\",\"number\":122}]";
Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create();
		
		  status=gson.fromJson(json, new TypeToken<ArrayList<StatusOfStat>>(){}.getType());
			System.out.println(Tools.getJson(status));

		//fail("Not yet implemented");
	}

}

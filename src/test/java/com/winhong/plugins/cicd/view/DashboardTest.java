package com.winhong.plugins.cicd.view;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DashboardTest {

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
	public void testGetAllProjectStatus() {
		try {
			System.out.print(Dashboard.getAllProjectStatus());
		} catch ( Exception e) {
 			e.printStackTrace();
 			fail("get exception");
		}
 	}
	
	@Test
	public void testgetFailedBuids() {
		try {
			System.out.print(Dashboard.getFailedBuids(10));
		} catch ( Exception e) {
 			e.printStackTrace();
 			fail("get exception");
		}
 	}
 
	@Test
	public void testgetRecentBuids() {
		try {
			System.out.print(Dashboard.getLatestExecutedBuid(10));
		} catch ( Exception e) {
 			e.printStackTrace();
 			fail("get exception");
		}
 	}
	
	@Test
	public void testgetTimeRangeStatfromShell(){
		try {
			
			 
			DateFormat formatter = new SimpleDateFormat("mm/dd/yyyy");
			//Date startDate = (Date)formatter.parse(datestr); 
			
			Date begin=(Date)formatter.parse("03/10/2017");  
			Date end=(Date)formatter.parse("04/30/2017");  
			Timestamp b=new Timestamp(begin.getTime());
			Timestamp e=new Timestamp(end.getTime());
			
			
			System.out.print(Dashboard.getTimeRangeStatfromShell(b,e));
		} catch (IOException | ParseException e) {
 			e.printStackTrace();
 			fail("testgetTimeRangeStatfromShell");
		}

		
	}
	
	@Test
	public void testgetTimeRangeStatfromShell2(){
		try {
			
			 
			DateFormat formatter = new SimpleDateFormat("mm/dd/yyyy HH:MM");
			//Date startDate = (Date)formatter.parse(datestr); 
			
			Date begin=(Date)formatter.parse("03/10/2017 22:00");  
			Date end=(Date)formatter.parse("04/30/2017 22:00");  
			 
			
			System.out.print("begin="+begin.getTime());
			System.out.print("end="+end.getTime());

 			
			System.out.print(Dashboard.getTimeRangeStatfromShell(begin.getTime(),end.getTime()));
		} catch (IOException | ParseException e) {
 			e.printStackTrace();
 			fail("testgetTimeRangeStatfromShell");
		}

		
	}
	
	
	@Test
	public void testgetTimeRangeDetailfromShell(){
		try {
			
 
 
			Timestamp begin = Timestamp.valueOf("2017-03-12 18:48:05");
			Timestamp end = Timestamp.valueOf("2017-04-12 18:48:05");

			
			System.out.print(Dashboard.getTimeRangeDetailfromShell(begin,end));
		} catch (IOException  e) {
 			e.printStackTrace();
 			fail("testgetTimeRangeStatfromShell");
		}

		
	}
	
}

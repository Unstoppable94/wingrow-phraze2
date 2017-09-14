package com.winhong.jetty;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class EncrytMainTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testMain() {
		String[] args={"test"};
		EncrytMain.main(args);
		 
	}
	
	
	@Test
	public void testMain2() {
		String[] args={"test1","test3"};
		EncrytMain.main(args);
		 
	}

}

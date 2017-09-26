package com.winhong.plugins.cicd.tool;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

import com.winhong.plugins.cicd.tool.RandomString;

public class RandomStringTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testNextString() {
		RandomString.init(16);
		HashMap<String,Integer> temp=new HashMap<String,Integer>();
		for (int i=0;i<100;i++) {
			String key=RandomString.nextString();
			if (temp.containsKey(key))
				fail("");
			else
				temp.put(key, i);
			System.out.println(key);
		}
		 
		 
	}

}

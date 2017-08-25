package com.winhong.plugins.cicd.mavenStep;

import static org.junit.Assert.*;

import org.junit.Test;

import com.winhong.plugins.cicd.exception.ConfigCheckException;
import com.winhong.plugins.cicd.property.Property;

public class FindbugsTest {

	@Test
	public void testFindbugs() {
		Findbugs b=new Findbugs();
		b.setValue("infilerUrl", "http:");
		
		String s=b.getJson();
		
		System.out.println(s);
		Property p=new Property();
		
		 
	 
		//fail();
		
 	}

}

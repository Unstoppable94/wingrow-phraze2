package com.winhong.plugins.cicd.tools;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import com.winhong.plugins.cicd.tool.RancherClient;
import com.winhong.plugins.cicd.view.displayData.RancherEnvironment;

public class RancherClientTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		//RancherClient.init("http://192.168.101.81:8080","E16BBCEB675189233FCF","dubRG8jwaWEgcVUkZowaJ1qmy34MzxMhESKFggvo");
		
		
	}

	@Test
	public void testGetEnvironments() {
		 
			ArrayList<RancherEnvironment> w;
			try {
				w = RancherClient.getEnvironments();
				System.out.println("size===="+w.size());
				for (int i=0;i<w.size();i++ ) {
					System.out.println(i+"=="+w.get(i).getName());
				}
			} catch (InstantiationException | IllegalAccessException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail();
			}
			
	 
		
 	}

}

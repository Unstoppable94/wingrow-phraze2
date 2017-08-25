package com.winhong.jetty;

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

public class AppTest extends App {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		/*
		 * JENKINS_MASTER: http://jenkins-master:8080 REGISTRY_AUTH: ${registry_auth}
		 * REGISTRY_IP: ${registry_ip} REGISTRY_PORT: ${registry_port} REGISTRY_INSCURE:
		 * ${registry_inscure} WINGARDEN_URL: ${wingarden_server_url} SECRETKEY:
		 * ${secretKey} ACCESSKEY: ${accessKey} ENVIRONMENT: ${env_id} SLAVE_EXECUTORS:
		 * ${slave_executors} JENKINS_PASSWORD="w12sedwiokd" JENKINS_USERNAME="jenkins"
		 * EMAIL_SSL=FALSE; EMAIL_PORT=25; EMAIL_HOST= EMAIL_USER= EMAIL_PASSWORD=
		 */
		HashMap<String, String> map=new  HashMap();
		
		
		map.put("JENKINS_MASTER", "http://192.168.101.6:8080/");

		map.put("REGISTRY_AUTH", "eGllaHE6QWNkMTIzNDU=");
		map.put("REGISTRY_IP", "10.0.2.50");
		map.put("REGISTRY_PORT", "80");
		map.put("REGISTRY_INSCURE", "false");
		map.put("WINGARDEN_URL", "http://192.168.101.2:8080/");
		map.put("SECRETKEY", "i21bRJAQE4PcAUFzHnQGHxHncbvL5TuzaU35UiGZ");
		map.put("ACCESSKEY", "47551EC490F934813DE0");
		map.put("ENVIRONMENT", "1a5");
		map.put("SLAVE_EXECUTORS", "1");
		map.put("JENKINS_PASSWORD", "w12sedwiokd");
		map.put("JENKINS_USERNAME", "jenkins");
		
		map.put("EMAIL_SSL", "http://jenkins-master:8080");
		map.put("EMAIL_PORT", "http://jenkins-master:8080");
		map.put("EMAIL_HOST", "http://jenkins-master:8080");
		map.put("EMAIL_PASSWORD", "http://jenkins-master:8080");
		map.put("EMAIL_USER", "http://jenkins-master:8080");
		
		setEnv(map);
		System.out.println(System.getenv("JENKINS_USERNAME"));
	
	}

	@Test
	public void testMain() {
		fail("Not yet implemented");
	}

	
	
	protected static void setEnv(Map<String, String> newenv)
	{
	  try
	    {
	        Class<?> processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
	        Field theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
	        theEnvironmentField.setAccessible(true);
	        Map<String, String> env = (Map<String, String>) theEnvironmentField.get(null);
	        env.putAll(newenv);
	        Field theCaseInsensitiveEnvironmentField = processEnvironmentClass.getDeclaredField("theCaseInsensitiveEnvironment");
	        theCaseInsensitiveEnvironmentField.setAccessible(true);
	        Map<String, String> cienv = (Map<String, String>)     theCaseInsensitiveEnvironmentField.get(null);
	        cienv.putAll(newenv);
	    }
	    catch (NoSuchFieldException e)
	    {
	      try {
	        Class[] classes = Collections.class.getDeclaredClasses();
	        Map<String, String> env = System.getenv();
	        for(Class cl : classes) {
	            if("java.util.Collections$UnmodifiableMap".equals(cl.getName())) {
	                Field field = cl.getDeclaredField("m");
	                field.setAccessible(true);
	                Object obj = field.get(env);
	                Map<String, String> map = (Map<String, String>) obj;
	                map.clear();
	                map.putAll(newenv);
	            }
	        }
	      } catch (Exception e2) {
	        e2.printStackTrace();
	      }
	    } catch (Exception e1) {
	        e1.printStackTrace();
	    } 
	}
	
	@Test
	public void testInitConfig() {
		App app=new App();
		try {
			app.initConfig();
		} catch ( Exception e) { 
			e.printStackTrace();
			fail();
		}
		//fail("Not yet implemented");
	}

	@Test
	public void testInitJenkinsConfig() {
		//fail("Not yet implemented");
	}

	@Test
	public void testInitRancherConfig() {
		//fail("Not yet implemented");
	}

}

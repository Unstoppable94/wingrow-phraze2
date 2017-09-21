package com.winhong.jetty;

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.winhong.plugins.cicd.openldap.OpenLDAP;
import com.winhong.plugins.cicd.openldap.OpenLDAPConfig;
import com.winhong.plugins.cicd.system.Config;
import com.winhong.plugins.cicd.system.JenkinsConfig;
import com.winhong.plugins.cicd.system.SonarConfig;
import com.winhong.plugins.cicd.tool.Encryptor;
import com.winhong.plugins.cicd.tool.Tools;

public class AppTest extends App {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		Config.setDockerDaemonfile("/Users/xiehq/git/wingrow-test/Data/config/daemon.json");
		Config.setDockerConfigJson("/git/wingrow-test/Data/config/dockerconfig.json");
		
		/*
		 * JENKINS_MASTER: http://jenkins-master:8080 REGISTRY_AUTH: ${registry_auth}
		 * REGISTRY_IP: ${registry_ip} REGISTRY_PORT: ${registry_port} REGISTRY_INSCURE:
		 * ${registry_inscure} WINGARDEN_URL: ${wingarden_server_url} SECRETKEY:
		 * ${secretKey} ACCESSKEY: ${accessKey} ENVIRONMENT: ${env_id} SLAVE_EXECUTORS:
		 * ${slave_executors} JENKINS_PASSWORD="w12sedwiokd" JENKINS_USERNAME="jenkins"
		 * EMAIL_SSL=FALSE; EMAIL_PORT=25; EMAIL_HOST= EMAIL_USER= EMAIL_PASSWORD=
		 */
		

	}

	@Test
	public void testMain() {
		System.out.println(System.currentTimeMillis()+1000*3600*24);
	}

	@Test
	public void testInitConfig() {
		
		HashMap<String, String> map = new HashMap();

		map.put("JENKINS_MASTER", "http://192.168.101.6:8080");
		map.put("FORCE_INIT", "true");
		
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

		map.put("SMTP_SSL", "false");
		map.put("SMTP_PORT", "365");
		map.put("SMTP_HOST", "MAIL.QQ.COM");
		map.put("SMTP_USER", "XIEHQ");
		map.put("SMTP_PASSWORD", "PASSWORD");

		map.put("SONAR_URL", "HTTP://sonar:8080");
		map.put("SONAR_USER", "XIEHQ");
		map.put("SONAR_PASSWORD", "PASSWORD");

		/*
		 * String ldapAdServer = "ldap://192.168.101.114:389";
		String ldapSearchBase = "ou=winhong,DC=wingarden,DC=com";

		String ldapUsername = "cn=administrator,cn=users,dc=wingarden,dc=com"; // SECURITY_PRINCIPAL
		String ldapPassword = "P@ssw0rd"; // ldapPassword

		OpenLDAPConfig ldapConfig = new OpenLDAPConfig(ldapUsername, ldapPassword, ldapAdServer, "simple", "",
				ldapSearchBase, "100");
		try {
			OpenLDAP.Login(ldapConfig, "caiwl", "root@123");
		 */
		map.put("LDAP_URL","ldap://192.168.101.93:389");
		map.put("LDAP_SEARACHBASE","ou=winhong,DC=wingarden,DC=com"); 
		map.put("LDAP_USER","cn=administrator,cn=users,dc=wingarden,dc=com");
		map.put("LDAP_PASSWORD","P@ssw0rd");
		map.put("LDAP_TIMEOUT","200");
		map.put("TOKEN_EXPIRY", "30");
		
		map.put("DOCKER_MIRROR", "http://10.0.2.50");
		
		map.put("DOCKER_REGISTRER_SERVER", "10.0.2.50");
		map.put("DOCKER_REGISTRER_AUTH", "eGllaHE6QWNkMTIzNDU=");
		map.put("DOCKER_REGISTRER_INSECURE", "www"); 

		
		Tools.setEnv(map);
		System.out.println(System.getenv("JENKINS_USERNAME"));
		
		App app = new App();
		try {
			app.initConfig();
			JenkinsConfig jenkinsConfig = new JenkinsConfig();

			jenkinsConfig = Config.getJenkinsConfig();
			SonarConfig so = Config.getSonarConfig();
			System.out.println(jenkinsConfig.getUrl());
			System.out.println(so.getSonarUrl());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		// fail("Not yet implemented");
	}

	@Test
	public void testInitConfigWithEnryption() {
		
		
		HashMap<String, String> map = new HashMap();

		map.put("JENKINS_MASTER", "http://192.168.101.6:8080/");
		map.put("FORCE_INIT", "TRUE");

		map.put("IS_ENCRYPTED", "true");
		map.put("REGISTRY_AUTH", "eGllaHE6QWNkMTIzNDU=");
		map.put("REGISTRY_IP", "10.0.2.50");
		map.put("REGISTRY_PORT", "80");
		map.put("REGISTRY_INSCURE", "false");
		map.put("WINGARDEN_URL", "http://192.168.101.2:8080/");
		
		map.put("SECRETKEY", Encryptor.encrypt("i21bRJAQE4PcAUFzHnQGHxHncbvL5TuzaU35UiGZ"));
		
		map.put("ACCESSKEY", "47551EC490F934813DE0");
		map.put("ENVIRONMENT", "1a5");
		map.put("SLAVE_EXECUTORS", "1");
		map.put("JENKINS_PASSWORD", Encryptor.encrypt("w12sedwiokd"));
		map.put("JENKINS_USERNAME", "jenkins");

		map.put("SMTP_SSL", "false");
		map.put("SMTP_PORT", "365");
		map.put("SMTP_HOST", "MAIL.QQ.COM");
		map.put("SMTP_USER", "XIEHQ");
		map.put("SMTP_PASSWORD", Encryptor.encrypt("PASSWORD"));

		map.put("SONAR_URL", "HTTP://sonar:8080");
		map.put("SONAR_USER", "XIEHQ");
		map.put("SONAR_PASSWORD", Encryptor.encrypt("PASSWORD"));

		
		map.put("LDAP_URL","ldap://192.168.101.114:389");
		map.put("LDAP_SEARACHBASE","ou=winhong,DC=wingarden,DC=com"); 
		map.put("LDAP_USER","cn=administrator,cn=users,dc=wingarden,dc=com");
		map.put("LDAP_PASSWORD",Encryptor.encrypt("P@ssw0rd"));
		map.put("LDAP_TIMEOUT","200");
		map.put("TOKEN_EXPIRY", "30");
		
		map.put("DOCKER_REGISTRER_SERVER", "10.0.2.50");
		map.put("DOCKER_REGISTRER_AUTH", "eGllaHE6QWNkMTIzNDU=");
		map.put("DOCKER_REGISTRER_INSECURE", "TRUE"); 

		Tools.setEnv(map);
		System.out.println(System.getenv("JENKINS_USERNAME"));
		
		App app = new App();
		try {
			
			app.initConfig();
			JenkinsConfig jenkinsConfig = new JenkinsConfig();

			jenkinsConfig = Config.getJenkinsConfig();
			SonarConfig so = Config.getSonarConfig();
			System.out.println(jenkinsConfig.getPassword());
			System.out.println(so.getPassword());
			
			if (Config.getRancherConfig().getSecureKey().equals("i21bRJAQE4PcAUFzHnQGHxHncbvL5TuzaU35UiGZ")==false)
				fail("rancher's secrets");
			
			if (Config.getJenkinsConfig().getPassword().equals("w12sedwiokd")==false)
				fail("rancher's secrets");
			
			System.out.println(Config.getSMTPConfig().getPassword());
			System.out.println(Config.getOpenLDAPConfig().getSECURITY_CREDENTIALS());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		// fail("Not yet implemented");
	}

}

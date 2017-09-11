package com.winhong.jetty;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import com.winhong.plugins.cicd.system.Config;
import com.winhong.plugins.cicd.system.JenkinsConfig;
import com.winhong.plugins.cicd.system.RancherConfig;
import com.winhong.plugins.cicd.tool.RandomString;

 

public class App {

	public static void main(String[] args) {
		InetSocketAddress bindAdress = new InetSocketAddress("0.0.0.0", 8100);
		Server server = new Server(bindAdress);

		ResourceConfig config = new ResourceConfig();
		config.packages("com.winhong.cicdweb");
		ServletHolder jerseyServlet = new ServletHolder(new ServletContainer(config));

		ServletContextHandler context = new ServletContextHandler(server, "/");
		//close for dev
		//config.register(JWTSecurityFilter.class);
		//config.register(UsePrivilegeFilter.class);
		
		context.addServlet(jerseyServlet, "/webapi/*");
		
		try {
			initConfig();
		} catch (InstantiationException | IllegalAccessException | IOException e) {

			e.printStackTrace();
			return;
		}
		try {
			server.start();
			server.join();
		} catch (Exception ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		} finally {

			server.destroy();
		}
	}

	/*
	 * JENKINS_MASTER: http://jenkins-master:8080 REGISTRY_AUTH: ${registry_auth}
	 * REGISTRY_IP: ${registry_ip} REGISTRY_PORT: ${registry_port} REGISTRY_INSCURE:
	 * ${registry_inscure} WINGARDEN_URL: ${wingarden_server_url} SECRETKEY:
	 * ${secretKey} ACCESSKEY: ${accessKey} ENVIRONMENT: ${env_id} SLAVE_EXECUTORS:
	 * ${slave_executors} JENKINS_PASSWORD="w12sedwiokd" JENKINS_USERNAME="jenkins"
	 * EMAIL_SSL=FALSE; EMAIL_PORT=25; EMAIL_HOST= EMAIL_USER= EMAIL_PASSWORD=
	 */
	public static void initConfig() throws IOException, InstantiationException, IllegalAccessException {
		initRancherConfig();
		initJenkinsConfig();
		//init password reset random
		RandomString.init(16);
	}

	public static void initJenkinsConfig() throws IOException, InstantiationException, IllegalAccessException {

		JenkinsConfig jenkinsConfig = new JenkinsConfig();
		try {
			jenkinsConfig = Config.getJenkinsConfig();
		} catch (FileNotFoundException | InstantiationException | IllegalAccessException e) {
			jenkinsConfig = new JenkinsConfig();
		}
		String JENKINS_MASTER = System.getenv("JENKINS_MASTER");
		if (JENKINS_MASTER != null && JENKINS_MASTER.isEmpty() == false)
			jenkinsConfig.setUrl(JENKINS_MASTER);
		String JENKINS_USERNAME = System.getenv("JENKINS_USERNAME");
		if (JENKINS_USERNAME != null && JENKINS_USERNAME.isEmpty() == false)
			jenkinsConfig.setUser(JENKINS_USERNAME);

		String JENKINS_PASSWORD = System.getenv("JENKINS_PASSWORD");
		if (JENKINS_PASSWORD != null && JENKINS_PASSWORD.isEmpty() == false)
			jenkinsConfig.setPassword(JENKINS_PASSWORD);

		Config.saveConfig(jenkinsConfig);

	}

	public static void initRancherConfig() throws IOException, InstantiationException, IllegalAccessException {

		RancherConfig rancherConfig = new RancherConfig();
		try {
			rancherConfig = Config.getRancherConfig();
		} catch (FileNotFoundException | InstantiationException | IllegalAccessException e) {
			rancherConfig = new RancherConfig();
		}
		String WINGARDEN_URL = System.getenv("WINGARDEN_URL");
		if (WINGARDEN_URL != null && WINGARDEN_URL.isEmpty() == false)
			rancherConfig.setServerUrl(WINGARDEN_URL);
		String SECRETKEY = System.getenv("SECRETKEY");
		if (SECRETKEY != null && SECRETKEY.isEmpty() == false)
			rancherConfig.setSecureKey(SECRETKEY);
		String ENVIRONMENT = System.getenv("ENVIRONMENT");
		if (ENVIRONMENT != null && ENVIRONMENT.isEmpty() == false)
			rancherConfig.setEnvID(ENVIRONMENT);
		String ACCESSKEY = System.getenv("ACCESSKEY");
		if (ACCESSKEY != null && ACCESSKEY.isEmpty() == false)
			rancherConfig.setAccessKey(ACCESSKEY);

		Config.saveConfig(rancherConfig);

		// =new RancherConfig();
	}
}
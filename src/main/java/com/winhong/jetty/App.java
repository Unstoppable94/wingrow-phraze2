package com.winhong.jetty;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import com.winhong.plugins.cicd.system.Config;
import com.winhong.plugins.cicd.system.JenkinsConfig;
import com.winhong.plugins.cicd.system.RancherConfig;

public class App {

	public static void main(String[] args) {

		Server server = new Server(8100);

		ServletContextHandler ctx = new ServletContextHandler(ServletContextHandler.SESSIONS);

		ctx.setContextPath("/");
		server.setHandler(ctx);

		ServletHolder serHol = ctx.addServlet(ServletContainer.class, "/webapi/*");
		serHol.setInitOrder(1);
		serHol.setInitParameter("jersey.config.server.provider.packages", "com.winhong.cicdweb");

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
	public void initConfig() throws IOException, InstantiationException, IllegalAccessException {
		initRancherConfig();
		initJenkinsConfig();
	}
	
	public void initJenkinsConfig() throws IOException, InstantiationException, IllegalAccessException {
		
		JenkinsConfig jenkinsConfig = new JenkinsConfig();
		try {
			jenkinsConfig = Config.getJenkinsConfig();
		} catch (FileNotFoundException | InstantiationException | IllegalAccessException e) {
			jenkinsConfig = new JenkinsConfig();
		}
		String JENKINS_MASTER = System.getenv("JENKINS_MASTER");
		if (JENKINS_MASTER!=null && JENKINS_MASTER.isEmpty() == false)
			jenkinsConfig.setUrl(JENKINS_MASTER);
		String JENKINS_USERNAME = System.getenv("JENKINS_USERNAME");
		if (JENKINS_USERNAME!=null && JENKINS_USERNAME.isEmpty() == false)
			jenkinsConfig.setUser(JENKINS_USERNAME);
		 
		String JENKINS_PASSWORD = System.getenv("JENKINS_PASSWORD");
		if (JENKINS_PASSWORD!=null && JENKINS_PASSWORD.isEmpty() == false)
			jenkinsConfig.setPassword(JENKINS_PASSWORD);

		Config.saveConfig(jenkinsConfig);
		
	}
	public void initRancherConfig() throws IOException, InstantiationException, IllegalAccessException {

		RancherConfig rancherConfig = new RancherConfig();
		try {
			rancherConfig = Config.getRancherConfig();
		} catch (FileNotFoundException | InstantiationException | IllegalAccessException e) {
			rancherConfig = new RancherConfig();
		}
		String WINGARDEN_URL = System.getenv("WINGARDEN_URL");
		if (WINGARDEN_URL!=null && WINGARDEN_URL.isEmpty() == false)
			rancherConfig.setServerUrl(WINGARDEN_URL);
		String SECRETKEY = System.getenv("SECRETKEY");
		if (SECRETKEY!=null &&  SECRETKEY.isEmpty() == false)
			rancherConfig.setSecureKey(SECRETKEY);
		String ENVIRONMENT = System.getenv("ENVIRONMENT");
		if (ENVIRONMENT!=null &&  ENVIRONMENT.isEmpty() == false)
			rancherConfig.setEnvID(ENVIRONMENT);
		String ACCESSKEY = System.getenv("ACCESSKEY");
		if (ACCESSKEY!=null && ACCESSKEY.isEmpty() == false)
			rancherConfig.setAccessKey(ACCESSKEY);

		Config.saveConfig(rancherConfig);

		// =new RancherConfig();
	}
}
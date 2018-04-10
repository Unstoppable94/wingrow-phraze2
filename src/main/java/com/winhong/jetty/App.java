package com.winhong.jetty;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.NoSuchFileException;
<<<<<<< HEAD
import java.util.EnumSet;

import javax.servlet.DispatcherType;

=======

>>>>>>> 998ab14ed04442923fa4c554fa7032c20541071e
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
<<<<<<< HEAD
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.FilterHolder;
=======
>>>>>>> 998ab14ed04442923fa4c554fa7032c20541071e
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

<<<<<<< HEAD
import com.winhong.plugins.cicd.action.CheckHeartBeatTimer;
import com.winhong.plugins.cicd.action.GroupAction;
import com.winhong.plugins.cicd.action.SendEmailTimer;
import com.winhong.plugins.cicd.action.UserAction;
import com.winhong.plugins.cicd.cas.CORSResponseFilter;
import com.winhong.plugins.cicd.cas.RegisterUAPFilter;
import com.winhong.plugins.cicd.data.base.ProjectGroupJsonConfig;
import com.winhong.plugins.cicd.filter.UAPLoginFilter;
=======
import com.winhong.plugins.cicd.action.GroupAction;
import com.winhong.plugins.cicd.action.UserAction;
import com.winhong.plugins.cicd.data.base.ProjectGroupJsonConfig;
import com.winhong.plugins.cicd.filter.CORSResponseFilter;
import com.winhong.plugins.cicd.filter.JWTSecurityFilter;
>>>>>>> 998ab14ed04442923fa4c554fa7032c20541071e
import com.winhong.plugins.cicd.filter.UsePrivilegeFilter;
import com.winhong.plugins.cicd.jwt.TokenUtil;
import com.winhong.plugins.cicd.openldap.OpenLDAPConfig;
import com.winhong.plugins.cicd.system.Config;
import com.winhong.plugins.cicd.system.InnerConfig;
import com.winhong.plugins.cicd.system.JenkinsConfig;
import com.winhong.plugins.cicd.system.RancherConfig;
import com.winhong.plugins.cicd.system.RegistryConfig;
import com.winhong.plugins.cicd.system.RegistryList;
import com.winhong.plugins.cicd.system.RegistryMirrorConfig;
import com.winhong.plugins.cicd.system.SMTPConfig;
import com.winhong.plugins.cicd.system.SonarConfig;
import com.winhong.plugins.cicd.tool.Encryptor;
import com.winhong.plugins.cicd.tool.JenkinsClient;
import com.winhong.plugins.cicd.tool.RandomString;
import com.winhong.plugins.cicd.user.User;
<<<<<<< HEAD
import com.winhong.uap.cas.client.authentication.AuthenticationFilter;
import com.winhong.uap.cas.client.session.SingleSignOutFilter;
import com.winhong.uap.cas.client.util.AssertionThreadLocalFilter;
import com.winhong.uap.cas.client.util.HttpServletRequestWrapperFilter;
import com.winhong.uap.cas.client.validation.Cas30ProxyReceivingTicketValidationFilter;
=======
>>>>>>> 998ab14ed04442923fa4c554fa7032c20541071e

public class App {
	
	private static final Logger log = LoggerFactory.getLogger(App.class);
	
	public static void main(String[] args) {
		InetSocketAddress bindAdress = new InetSocketAddress("0.0.0.0", 8100);
		Server server = new Server(bindAdress);
		ResourceConfig config = new ResourceConfig();
		config.packages("com.winhong.cicdweb");
		ServletHolder jerseyServlet = new ServletHolder(new ServletContainer(config));

		ServletContextHandler context = new ServletContextHandler(server, "/");

        

		// close for dev
<<<<<<< HEAD
		//config.register(JWTSecurityFilter.class);
		//config.register(CORSResponseFilter.class);
=======
		config.register(JWTSecurityFilter.class);
		config.register(UsePrivilegeFilter.class);
		config.register(CORSResponseFilter.class);
>>>>>>> 998ab14ed04442923fa4c554fa7032c20541071e
		context.addServlet(jerseyServlet, "/webapi/*");
		//config.register(UsePrivilegeFilter.class);
		context.setSessionHandler(new SessionHandler());
		
		//register uap filter
		new RegisterUAPFilter(context).registerFilterBus();
		        
		
		try {
<<<<<<< HEAD
=======
			//initSonarConfig();

>>>>>>> 998ab14ed04442923fa4c554fa7032c20541071e
			initDirs();
			initConfig();
			// SET login token expire time
			int defaultExpiryMins = 1500;
			String TOKEN_EXPIRY = System.getenv("TOKEN_EXPIRY");
			if (TOKEN_EXPIRY != null && TOKEN_EXPIRY.isEmpty() == false)
				defaultExpiryMins = Integer.parseInt(TOKEN_EXPIRY);
			TokenUtil.setDefaultExpiryMins(defaultExpiryMins);
		} catch (InstantiationException | IllegalAccessException | IOException e) {
			log.error(e.getMessage());

			e.printStackTrace();
			return;
		}
		try {
<<<<<<< HEAD
			//SendEmailThread emailThread = new SendEmailThread();
=======
			SendEmailThread emailThread = new SendEmailThread();
>>>>>>> 998ab14ed04442923fa4c554fa7032c20541071e
			//emailThread.start();
			
			server.start();
			//邮箱(设置检查时间，单位为秒)
			//new SendEmailTimer(1);
			//心跳检测
			new CheckHeartBeatTimer(60 * 3);
			server.join();

		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
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
		boolean force = false;
		String forceStr = System.getenv("FORCE_INIT");

		if (forceStr != null && forceStr.equalsIgnoreCase("true"))
			force = true;
		boolean inited = isInited();
		if (!inited || force) {

			initJenkinsConfig();
			// avoid reinit after restart
			boolean jenkinsStart;

			initRancherConfig();
			initRegisterConfig();
			initSmtpConfig();
			
			initOpenLDAPConfig();
			do{
				try{
				jenkinsStart = JenkinsClient.defaultClient().getCrumb();
				}catch(Exception e){
					jenkinsStart = false;
				}
				log.info("jenkins stat :" + jenkinsStart);
			}while(!jenkinsStart);
			initSonarConfig();
			

			
			
//			try {
//				Thread.sleep(1000*60*3);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				log.error("start error, please try again");
//			}
			//initRegisterConfig();
			// init password reset random
			//

		}
		RandomString.init(16);
		// flowing env should only use in testing
	    //initTestConfig();
		createDefaultGroupAndUser();

		
		//本地测试需要打开
		//initJenkinsConfig();
		
	}

	public static boolean isInited() {
		try {
			Config.getJenkinsConfig();
		} catch (FileNotFoundException | NoSuchFileException e) {
			return false;
		} catch (IOException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return true;

	}

	public static void initDirs() throws IOException, InstantiationException, IllegalAccessException {
		String[] subdirs = { "user", "deletedProjects", "deleteduser", "config", "projects", "projectGroup", "logs","ldapuser","localuser"};
		String parent = InnerConfig.defaultConfig().getDataDir();
		for (int i = 0; i < subdirs.length; i++) {
			File dir = new File(parent + "/" + subdirs[i]);

			if (dir.exists() == false)
				dir.mkdirs();
		}

	}

	public static void initJenkinsConfig() throws IOException, InstantiationException, IllegalAccessException {

		JenkinsConfig jenkinsConfig = null;
		try {
			jenkinsConfig = Config.getJenkinsConfig();
		} catch (FileNotFoundException | NoSuchFileException | InstantiationException | IllegalAccessException e) {
			jenkinsConfig = new JenkinsConfig();
		}
		String Encrypted = System.getenv("IS_ENCRYPTED");

		String JENKINS_MASTER = System.getenv("JENKINS_MASTER");
		if (JENKINS_MASTER != null && JENKINS_MASTER.isEmpty() == false)
			jenkinsConfig.setUrl(JENKINS_MASTER);
		String JENKINS_USERNAME = System.getenv("JENKINS_USERNAME");
		if (JENKINS_USERNAME != null && JENKINS_USERNAME.isEmpty() == false)
			jenkinsConfig.setUser(JENKINS_USERNAME);

		String JENKINS_PASSWORD = System.getenv("JENKINS_PASSWORD");
		if (JENKINS_PASSWORD != null && JENKINS_PASSWORD.isEmpty() == false) {
			if (Encrypted != null && Encrypted.equalsIgnoreCase("true"))
				jenkinsConfig.setPassword(Encryptor.decrypt(JENKINS_PASSWORD));
			else
				jenkinsConfig.setPassword(JENKINS_PASSWORD);
		}

		Config.saveConfig(jenkinsConfig);

	}

	public static void initRancherConfig() throws IOException, InstantiationException, IllegalAccessException {

		RancherConfig rancherConfig = null;
		try {
			rancherConfig = Config.getRancherConfig();
		} catch (FileNotFoundException | NoSuchFileException | InstantiationException | IllegalAccessException e) {
			rancherConfig = new RancherConfig();
		}
		String Encrypted = System.getenv("IS_ENCRYPTED");
		String WINGARDEN_URL = System.getenv("WINGARDEN_URL");
		if (WINGARDEN_URL != null && WINGARDEN_URL.isEmpty() == false)
			rancherConfig.setServerUrl(WINGARDEN_URL);
		String SECRETKEY = System.getenv("SECRETKEY");
		if (SECRETKEY != null && SECRETKEY.isEmpty() == false) {
			if (Encrypted != null && Encrypted.equalsIgnoreCase("true"))
				rancherConfig.setSecureKey(Encryptor.decrypt(SECRETKEY));
			else
				rancherConfig.setSecureKey(SECRETKEY);

		}

		String ENVIRONMENT = System.getenv("ENVIRONMENT");
		if (ENVIRONMENT != null && ENVIRONMENT.isEmpty() == false)
			rancherConfig.setEnvID(ENVIRONMENT);
		String ACCESSKEY = System.getenv("ACCESSKEY");
		if (ACCESSKEY != null && ACCESSKEY.isEmpty() == false)
			rancherConfig.setAccessKey(ACCESSKEY);

		Config.saveConfig(rancherConfig);

		// =new RancherConfig();
	}

	public static void initSmtpConfig() throws IOException, InstantiationException, IllegalAccessException {

		SMTPConfig SMTPConfig = null;
		try {
			SMTPConfig = Config.getSMTPConfig();
		} catch (FileNotFoundException | NoSuchFileException | InstantiationException | IllegalAccessException e) {
			SMTPConfig = new SMTPConfig();
		}
		String SMTP_HOST = System.getenv("SMTP_HOST");
		if (SMTP_HOST != null && SMTP_HOST.isEmpty() == false)
			SMTPConfig.setHost(SMTP_HOST);
		String SMTP_USER = System.getenv("SMTP_USER");
		if (SMTP_USER != null && SMTP_USER.isEmpty() == false)
			SMTPConfig.setUser(SMTP_HOST);

		String Encrypted = System.getenv("IS_ENCRYPTED");
		String SMTP_PASSWORD = System.getenv("SMTP_PASSWORD");
		if (SMTP_PASSWORD != null && SMTP_PASSWORD.isEmpty() == false) {
			if (Encrypted != null && Encrypted.equalsIgnoreCase("true"))
				SMTPConfig.setPassword(Encryptor.decrypt(SMTP_PASSWORD));
			else
				SMTPConfig.setPassword(SMTP_PASSWORD);

		}
		String SMTP_PORT = System.getenv("SMTP_PORT");
		if (SMTP_PORT != null && SMTP_PORT.isEmpty() == false)
			SMTPConfig.setPort(Integer.parseInt(SMTP_PORT));

		String SMTP_SSL = System.getenv("SMTP_SSL");
		if (SMTP_SSL != null && SMTP_SSL.equalsIgnoreCase("TRUE"))
			SMTPConfig.setSsl(true);
		else
			SMTPConfig.setSsl(false);
		Config.saveConfig(SMTPConfig);

		// =new RancherConfig();
	}

	public static void initSonarConfig() throws IOException, InstantiationException, IllegalAccessException {

		SonarConfig sonarConfig = null;
		try {
			sonarConfig = Config.getSonarConfig();
		} catch (FileNotFoundException | NoSuchFileException | InstantiationException | IllegalAccessException e) {
			sonarConfig = new SonarConfig();
		}
		String Encrypted = System.getenv("IS_ENCRYPTED");
		String SONAR_URL = System.getenv("SONAR_URL");
		if (SONAR_URL != null && SONAR_URL.isEmpty() == false)
			sonarConfig.setSonarUrl(SONAR_URL);

		String SONAR_PASSWORD = System.getenv("SONAR_PASSWORD");

		if (SONAR_PASSWORD != null && SONAR_PASSWORD.isEmpty() == false) {
			if (Encrypted != null && Encrypted.equalsIgnoreCase("true"))
				sonarConfig.setPassword(Encryptor.decrypt(SONAR_PASSWORD));
			else
				sonarConfig.setPassword(SONAR_PASSWORD);

		}

		String SONAR_USER = System.getenv("SONAR_USER");
		if (SONAR_USER != null && SONAR_USER.isEmpty() == false)
			sonarConfig.setUser(SONAR_USER);

		Config.saveConfig(sonarConfig);

		// =new SonarConfig();
	}

	public static void initOpenLDAPConfig() throws IOException, InstantiationException, IllegalAccessException {

		OpenLDAPConfig openLDAPConfig = null;
		try {
			openLDAPConfig = Config.getOpenLDAPConfig();
		} catch (FileNotFoundException | NoSuchFileException | InstantiationException | IllegalAccessException e) {
			openLDAPConfig = new OpenLDAPConfig();
		}

		String LDAP_URL = System.getenv("LDAP_URL");
		if (LDAP_URL != null && LDAP_URL.isEmpty() == false)
			openLDAPConfig.setPROVIDER_URL(LDAP_URL);

		String LDAP_SEARACHBASE = System.getenv("LDAP_SEARACHBASE");
		if (LDAP_SEARACHBASE != null && LDAP_SEARACHBASE.isEmpty() == false)
			openLDAPConfig.setSearchBase(LDAP_SEARACHBASE);

		String LDAP_USER = System.getenv("LDAP_USER");
		// SECURITY_PRINCIPAL
		if (LDAP_USER != null && LDAP_USER.isEmpty() == false)
			openLDAPConfig.setSECURITY_PRINCIPAL(LDAP_USER);

		String Encrypted = System.getenv("IS_ENCRYPTED");
		String LDAP_PASSWORD = System.getenv("LDAP_PASSWORD");

		if (LDAP_PASSWORD != null && LDAP_PASSWORD.isEmpty() == false) {
			if (Encrypted != null && Encrypted.equalsIgnoreCase("true"))
				openLDAPConfig.setSECURITY_CREDENTIALS(Encryptor.decrypt(LDAP_PASSWORD));
			else
				openLDAPConfig.setSECURITY_CREDENTIALS(LDAP_PASSWORD);

		}

		String LDAP_TIMEOUT = System.getenv("LDAP_TIMEOUT");
		if (LDAP_TIMEOUT != null && LDAP_TIMEOUT.isEmpty() == false)
			openLDAPConfig.setConnect_Timeout(LDAP_TIMEOUT);

		Config.saveConfig(openLDAPConfig);

		// =new openLDAPConfig();
	}

	public static void initRegisterConfig() throws IOException, InstantiationException, IllegalAccessException {

//		RegistryList registries = new RegistryList();
//		String DOCKER_REGISTRER_SERVER = System.getenv("REGISTRY_IP");
//		String DOCKER_REGISTRER_AUTH = System.getenv("REGISTRY_AUTH");
//		String DOCKER_REGISTRER_INSECURE = System.getenv("REGISTRY_INSCURE");
//		RegistryConfig register = null;
//		if (DOCKER_REGISTRER_SERVER != null && DOCKER_REGISTRER_SERVER.isEmpty() == false) {
//			// String server, String auth, String email, boolean b
//			String REGISTRY_PORT = System.getenv("REGISTRY_PORT");
//			if (REGISTRY_PORT!=null && REGISTRY_PORT.equalsIgnoreCase("80")==false && REGISTRY_PORT.equalsIgnoreCase("443")==false )
//				DOCKER_REGISTRER_SERVER=DOCKER_REGISTRER_SERVER+":"+REGISTRY_PORT;
//			register = new RegistryConfig(DOCKER_REGISTRER_SERVER, DOCKER_REGISTRER_AUTH, "",
//					Boolean.parseBoolean(DOCKER_REGISTRER_INSECURE));
//			registries.getRegistries().add(register);
//			Config.saveConfig(registries);
//
//		}
//
//		 //Docker Mirror setting in jenkins slave ,wingrow don't need to set it
//		 RegistryMirrorConfig dockerMirror = new RegistryMirrorConfig();
//		 String DOCKER_MIRROR = System.getenv("DOCKER_MIRROR");
//		
//		 if (DOCKER_MIRROR != null && DOCKER_MIRROR.isEmpty() == false) {
//		 if (DOCKER_MIRROR.startsWith("http")) {
//		 dockerMirror.setUrl(DOCKER_MIRROR);
//		 Config.saveConfig(dockerMirror);
//		
//		 }
//		 } else if (register != null) {
//		 if (register.isSecure())
//		 dockerMirror.setUrl("https://" + register.getServer());
//		 else
//		 dockerMirror.setUrl("http://" + register.getServer());
//		 Config.saveConfig(dockerMirror);
//		 }
		
		RegistryList registries = new RegistryList();
		Config.saveConfig(registries);
		RegistryMirrorConfig dockerMirror = new RegistryMirrorConfig();
		Config.saveConfig(dockerMirror);
		RegistryConfig registrie = new RegistryConfig();
		Config.saveConfig(registrie);
	}

//	public static void initTestConfig() throws IOException, InstantiationException, IllegalAccessException {
//
//		String TEST_DOCKER_DAEMON = System.getenv("TEST_DOCKER_DAEMON");
//		if (TEST_DOCKER_DAEMON != null && TEST_DOCKER_DAEMON.isEmpty() == false)
//			Config.setDockerDaemonfile(TEST_DOCKER_DAEMON);
//		String TEST_DOCKER_CONFIG = System.getenv("TEST_DOCKER_CONFIG");
//		if (TEST_DOCKER_CONFIG != null && TEST_DOCKER_CONFIG.isEmpty() == false)
//			Config.setDockerConfigJson(TEST_DOCKER_CONFIG);
//	}

	public static void recordRequest(Server server, ServletContextHandler handle) throws IOException {
		HandlerCollection handlers = new HandlerCollection();
		ContextHandlerCollection contexts = new ContextHandlerCollection();
		RequestLogHandler requestLogHandler = new RequestLogHandler();
		handlers.setHandlers(new Handler[] { handle, contexts, new DefaultHandler(), requestLogHandler });
		server.setHandler(handlers);

		String parent = InnerConfig.defaultConfig().getDataDir();
		log.debug("recordRequest ogfile=" + parent + "/logs/jetty-yyyy_mm_dd.request.log");
		NCSARequestLog requestLog = new NCSARequestLog(parent + "/logs/jetty-yyyy_mm_dd.request.log");

		requestLog.setRetainDays(30);
		requestLog.setAppend(true);
		requestLog.setExtended(false);
		requestLog.setLogCookies(true);
		// requestLog.setLogTimeZone("GMT");
		requestLogHandler.setRequestLog(requestLog);
		server.setRequestLog(requestLog); // here will set global request log
	}

	public static void createDefaultGroupAndUser() throws InstantiationException, IllegalAccessException, IOException {

		 
			File file = new File(GroupAction.getProjectGroupJsonfilename(GroupAction.defaultGroup));

			if (file.exists() == false) {
				ProjectGroupJsonConfig def = new ProjectGroupJsonConfig();
				def.setId(GroupAction.defaultGroup);
				def.setName("default");
				def.setDescription("default group,don't modify the group");
				GroupAction.createGroup(def);
			}
		 
			//initSonarConfig();

			if (!UserAction.userExist(UserAction.defaultAdmin)) {
				User defaultUser = new User();
				defaultUser.setUsername(UserAction.defaultAdmin);
				defaultUser.setRole(User.adminRole);
				defaultUser.setUserType(User.LOCAL);
				defaultUser.setPassword(UserAction.defaultPassword);
				UserAction.addUser(defaultUser);

			} 
	}
	
}
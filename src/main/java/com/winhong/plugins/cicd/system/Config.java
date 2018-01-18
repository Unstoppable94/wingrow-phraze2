package com.winhong.plugins.cicd.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winhong.plugins.cicd.data.base.EnumList;
import com.winhong.plugins.cicd.openldap.OpenLDAPConfig;
import com.winhong.plugins.cicd.tool.JenkinsClient;
import com.winhong.plugins.cicd.tool.Tools;

public class Config {

	private final static String configDir = "/config";
	private static final Logger log = LoggerFactory.getLogger(Config.class);
	private static String DockerDaemonfile = "/etc/docker/daemon.json";
	private static String DockerConfigJson = "/.docker/config.json";

	public Config() {
		super();
	}

	private static HashMap<String, Object> configs = new HashMap<String, Object>();

	private static Object getConfig(Class cla) throws InstantiationException, IllegalAccessException, IOException {
		if (configs.containsKey(getClassName(cla))) {
			return configs.get(getClassName(cla));
		}
		String dataDir = InnerConfig.defaultConfig().getDataDir();

		File dir = new File(dataDir + configDir);
		if (dir.exists() == false)
			dir.mkdirs();

		String filename = dir + "/" + getClassName(cla) + ".json";
		File file = new File(filename);
		if (file.exists() == false)
			throw new NoSuchFileException(filename);
		log.debug(filename);
		Object object = Tools.objectFromJsonFile(filename, cla);
		configs.put(getClassName(cla), object);
		return object;
	}

	public static SonarConfig getSonarConfig() throws InstantiationException, IllegalAccessException, IOException {
		return (SonarConfig) getConfig(SonarConfig.class);
	}

	public static SMTPConfig getSMTPConfig() throws InstantiationException, IllegalAccessException, IOException {
		return (SMTPConfig) getConfig(SMTPConfig.class);
	}

	public static RegistryMirrorConfig getRegistryMirrorConfig()
			throws InstantiationException, IllegalAccessException, IOException {
		return (RegistryMirrorConfig) getConfig(RegistryMirrorConfig.class);
	}

	public static RancherConfig getRancherConfig() throws InstantiationException, IllegalAccessException, IOException {
		return (RancherConfig) getConfig(RancherConfig.class);
	}

	public static RegistryList getRegistryList() throws InstantiationException, IllegalAccessException, IOException {
		return (RegistryList) getConfig(RegistryList.class);
	}

	/**
	 * 保存信息
	 * 
	 * @param config
	 * @return
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static <E> boolean saveConfig(E config) throws IOException, InstantiationException, IllegalAccessException {

		configs.remove(getClassName(config.getClass()));

		if (saveConfig(getClassName(config.getClass()), Tools.getJson(config)) == false)
			return false;
		// //sonar-credential
		// log.debug("config.class=" + config.getClass());
		// log.debug("sonar.class=" + SonarConfig.class);

		if (config.getClass().equals(SonarConfig.class)) {
			log.debug("sonar.class=config");

			JenkinsClient client = JenkinsClient.defaultClient();
			SonarConfig c = (SonarConfig) config;
			return client.createCredential("sonar", c.getUser(), c.getPassword(), "credential");

		}
//		//racnher/docker setting in jenkins-slave wingrow don't need to set they

		if (config.getClass().equals(RancherConfig.class)) {
		   //生成Rancher json 文件
		 log.debug(" class=RancherConfig");
		 String home = System.getProperty("user.home");
		 File dir = new File(home + "/.rancher");
		 if (dir.exists() == false)
		 dir.mkdirs();
		 String clijson = home + "/.rancher/cli.json";
		 log.debug("clijson=" + clijson);
		 RancherConfig c = (RancherConfig) config;
		 File file = new File(clijson);
		 if (file.exists()) {
		 file.renameTo(new File(clijson + "@" + System.currentTimeMillis()));
		 }
		 Tools.saveStringToFile(c.genCLiJson(), clijson, false);
		 return true;
		 }

		 if (config.getClass().equals(RegistryList.class)) {
		
		 log.debug(" class=RegistryList");
		
		 // c.getConfigJson();
		 String home = System.getProperty("user.home");
		 File dir = new File(home + "/.docker");
		 if (dir.exists() == false)
		 dir.mkdirs();
		 String dockerjson = home + DockerConfigJson;
		 log.debug("home" + dockerjson);
		 RegistryList c = (RegistryList) config;
		 File file = new File(dockerjson);
		 if (file.exists()) {
		 file.renameTo(new File(dockerjson + "@" + System.currentTimeMillis()));
		}
		 Tools.saveStringToFile(c.getConfigJson(), dockerjson, false);
		 return true;
		 }

		 if (config.getClass().equals(RegistryMirrorConfig.class)) {
		 String mirrorUrl = Config.getRegistryMirrorConfig().getUrl();
		 RegistryList regList = Config.getRegistryList();
		 String daemon=Tools.readResource("WinGrow/config/daemon.json", false);
		 String cmd = "dockerupdate.sh ";
		 daemon=Tools.strRep(daemon,"$mirror", mirrorUrl);
		 daemon=Tools.strRep(daemon,"$insecure-registries",
		 regList.getInscureString());
		
		
		 //runShell(cmd);
		 //Tools.saveStringToFile(daemon, DockerDaemonfile , false);
		 return true;
		 }
		return true;
	}

	
	
	//	 public static boolean saveConfig(RegistryMirrorConfig config) throws
	//	 IOException {
	//	 return saveConfig(getClassName(config.getClass()),
	//	 tools.getJson(config));
	//	 }
	//	
	//	 public static boolean saveConfig(RancherConfig config) throws IOException
	//	 {
	//	 return saveConfig(getClassName(config.getClass()),
	//	 tools.getJson(config));
	//	 }

	/**
	 * 保存配置文件
	 * 
	 * @param name
	 *            配置名称， 要与class一致
	 * @param content
	 *            配置内容
	 * @return 保存是否成功
	 * @throws IOException
	 *             异常
	 */
	public static boolean saveConfig(String name, String content) throws IOException {
		// Save Change
		String dataDir = InnerConfig.defaultConfig().getDataDir();

		File dir = new File(dataDir + configDir);
		if (dir.exists() == false)
			dir.mkdirs();

		String filename = dir + "/" + name + ".json";
		File file = new File(filename);

		if (file.exists()) {
			String temp = dir + "/" + name + ".json" + System.currentTimeMillis();
			file.renameTo(new File(temp));
		}

		log.debug(filename);
		Tools.saveStringToFile(content, filename);

		// File latest = new File(dir + "/" + name + ".json");
		// Path newLink = latest.toPath();
		//
		// if (latest.exists())
		// latest.delete();

		// Files.createLink(newLink, new File(filename).toPath());

		return true;
	}

	private static String getClassName(Class c) {
		String FQClassName = c.getName();
		int firstChar;
		firstChar = FQClassName.lastIndexOf('.') + 1;
		if (firstChar > 0) {
			FQClassName = FQClassName.substring(firstChar);
		}
		return FQClassName;
	}
	/**
	 * 从配置文件中返回指定server的信息
	 * 
	 * @param server
	 *            server名称
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws IOException
	 */
	public static RegistryConfig getRegistryConfig(String server)
			throws InstantiationException, IllegalAccessException, IOException {
		ArrayList<RegistryConfig> list = Config.getRegistryList().getRegistries();

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getServer().equals(server))
				return list.get(i);
		}
		return null;

	}

	/**
	 * 返回一个enum field 列表，主要供package cicd.MavenProperty使用
	 * 
	 * @return
	 */
	public static ArrayList<EnumList> genRegistryEnumList() {
		ArrayList<EnumList> relist = new ArrayList<EnumList>();
		// if (registryList.size()==0){
		try {

			ArrayList<RegistryConfig> list = Config.getRegistryList().getRegistries();
			for (int i = 0; i < list.size(); i++) {

				relist.add(new EnumList(list.get(i).getServer(), list.get(i).getServer()));
			}
			return relist;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			return null;
		}

	}

	public static boolean saveRegistry(RegistryConfig registry)
			throws IOException, InstantiationException, IllegalAccessException {
		RegistryList regList = null;
		try {
			regList = Config.getRegistryList();
		} catch (FileNotFoundException e) {
			regList = new RegistryList();
		}

		ArrayList<RegistryConfig> list = regList.getRegistries();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getServer().equals(registry.getServer())) {
				list.remove(i);
				break;
			}
		}
		list.add(registry);
		log.debug("add/modify" + registry.getServer());
		Config.saveConfig(regList);

		if (registry.isReboot() && registry.isSecure() == false) {
			String mirrorUrl = Config.getRegistryMirrorConfig().getUrl();

			String cmd = "dockerupdate.sh ";
			cmd += regList.getInscureString() + "  --registry-mirror " + mirrorUrl;
			runShell(cmd);
		}

		return true;

	}

	public static boolean deleteRegistry(String server)
			throws IOException, InstantiationException, IllegalAccessException {
		RegistryList regList = Config.getRegistryList();
		ArrayList<RegistryConfig> list = regList.getRegistries();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getServer().equals(server)) {
				list.remove(i);
				break;
			}
		}

		return Config.saveConfig(regList);

	}

	 public static void saveMirrorConfig(RegistryMirrorConfig config)
	 throws IOException, InstantiationException, IllegalAccessException {
	 //Config.saveConfig(config);
	
	 Config.saveConfig(config);
	 RegistryList regList = Config.getRegistryList();
	
	 String mirrorUrl = Config.getRegistryMirrorConfig().getUrl();
	
	 String cmd = "dockerupdate.sh ";
	 cmd += regList.getInscureString() + " --registry-mirror " + mirrorUrl;
	 runShell(cmd);
	
	 }

	private static String runShell(String cmd) throws IOException {
		Runtime rt = Runtime.getRuntime();

		Process proc = rt.exec(cmd);
		BufferedReader stdInput = null;
		BufferedReader stdError = null;
		try {
			stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

			stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

			// read the output from the command
			// System.out.println("Here is the standard output of the command:\n");
			String s = "";
			StringBuffer sb = new StringBuffer();
			while ((s = stdInput.readLine()) != null) {
				sb.append(s);
			}

			// read any errors from the attempted command
			// System.out
			// .println("Here is the standard error of the command (if any):\n");
			while ((s = stdError.readLine()) != null) {
				sb.append(s);
			}
			log.debug(cmd + " return:" + sb.toString());

			return sb.toString();
		} finally {

			try {
 				stdInput.close();
			} catch (Exception e) {

			}
			try {
				stdError.close();
			} catch (Exception e) {

			}
		}

	}

	public static JenkinsConfig getJenkinsConfig() throws InstantiationException, IllegalAccessException, IOException {
		return (JenkinsConfig) getConfig(JenkinsConfig.class);
	}

	public static OpenLDAPConfig getOpenLDAPConfig()
			throws InstantiationException, IllegalAccessException, IOException {
		return (OpenLDAPConfig) getConfig(OpenLDAPConfig.class);
	}

	public static String getDockerDaemonfile() {
		return DockerDaemonfile;
	}

	public static void setDockerDaemonfile(String dockerDaemonfile) {
		DockerDaemonfile = dockerDaemonfile;
	}

	public static String getDockerConfigJson() {
		return DockerConfigJson;
	}

	public static void setDockerConfigJson(String dockerConfigJson) {
		DockerConfigJson = dockerConfigJson;
	}

}

package com.winhong.plugins.cicd.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winhong.plugins.cicd.filter.JWTSecurityFilter;
import com.winhong.plugins.cicd.openldap.OpenLDAP;
import com.winhong.plugins.cicd.openldap.OpenLDAPConfig;
import com.winhong.plugins.cicd.system.Config;
import com.winhong.plugins.cicd.system.InnerConfig;
import com.winhong.plugins.cicd.tool.RandomString;
import com.winhong.plugins.cicd.tool.Tools;
import com.winhong.plugins.cicd.user.User;

public class UserAction {

	private static String userDir = "/user/";

	private static String deletedUserDir = "/deleteduser/";

	private static final Logger log = LoggerFactory.getLogger(UserAction.class);

 	public static final  String PasswordMask="******";

 	public static final  String defaultAdmin="admin";
 	public static final  String defaultPassword="admin";

 	static {
 	 
		try {
			if (!userExist(defaultAdmin)){
				User defaultUser = new User();
				defaultUser.setUsername(defaultAdmin);
				defaultUser.setRole(User.adminRole);
				defaultUser.setUserType(User.LOCAL);
				defaultUser.setPassword(defaultPassword);
				addUser(defaultUser);
				
			}
		} catch (IOException e) {
 			e.printStackTrace();
 			log.error("create default user failed!");
		}
	 
 	 
 		 

}
 	
 	
 	
 	public static ArrayList<User> searchUser(String username,String role,String type ) throws IOException {

		File folder = new File(InnerConfig.defaultConfig().getDataDir()+userDir);
		
		File[] listOfFiles = folder.listFiles();
		ArrayList<User> users = new ArrayList<User>();
		log.debug("usename:"+username);	

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String fileName = listOfFiles[i].getName();
				log.debug("filename:" + fileName);
				if (fileName.endsWith(".json")) {
					if (username != null && username != "")
						if (URLDecoder.decode(fileName, "UTF-8").indexOf(
								username) < 0) {
							continue;
						}
					User u = (User) Tools.objectFromJsonFile(
							listOfFiles[i].getAbsolutePath(), User.class);
					
					if (role != null && role.isEmpty()==false)
						if ( !u.getRole().equals(role)) {
							continue;
						}
					if (type != null && type != "")
						if ( !u.getUserType().equals(type)) {
							continue;
						}
					
					log.debug("adduser:"+u.getUsername());

					users.add(u);
				}

			}
		}

		return users;
	}
 	
 	 
// 	
//	public static ArrayList<User> getAllUser(String username) throws IOException {
//
//		File folder = new File(InnerConfig.defaultConfig().getDataDir()+userDir);
//		
//		File[] listOfFiles = folder.listFiles();
//		ArrayList<User> users = new ArrayList<User>();
//		log.debug("usename:"+username);	
//
//		for (int i = 0; i < listOfFiles.length; i++) {
//			if (listOfFiles[i].isFile()) {
//				String fileName = listOfFiles[i].getName();
//				log.debug("filename:" + fileName);
//				if (fileName.endsWith(".json")) {
//					if (username != null && username != "")
//						if (URLDecoder.decode(fileName, "UTF-8").indexOf(
//								username) < 0) {
//							continue;
//						}
//					User u = (User) Tools.objectFromJsonFile(
//							listOfFiles[i].getAbsolutePath(), User.class);
//					log.debug("adduser:"+u.getUsername());
//
//					users.add(u);
//				}
//
//			}
//		}
//
//		return users;
//	}

	public static User getUserinfo(String name) throws IOException {
		return (User) Tools.objectFromJsonFile(getUserfilename(name),
				User.class);

	}

	private static String getUserfilename(String name)
			throws IOException {

		String dir =  InnerConfig.defaultConfig().getDataDir() + userDir;

		String temp = dir
				+ URLEncoder.encode(name, "UTF-8") + ".json";
		return temp;
	}

	public static User addUser(User user) throws IOException {

		if (userExist(user.getUsername()))
			throw new IOException("用户已经存在");
		long time = System.currentTimeMillis();
		
		user.setId(String.valueOf(time));
		user.setCreateTime(time);
		user.setLatestModifyTime(time);

		Tools.saveStringToFile(Tools.getJson(user),
				getUserfilename(user.getUsername()));
		return user;

	}

	public static User modifyUser(User user,boolean modifyPassword) throws IOException {
		User oldUser = getUserinfo(user.getUsername());
		
		if (oldUser==null )
			throw new IOException("用户不存在");
		//密码处理，判断是否用户本身

		if (user.getPassword()==null || user.getPassword().equals(PasswordMask) || modifyPassword==false){
		
			
			user.setPassword(oldUser.getPassword());
		}
		user.setCreateTime(oldUser.getCreateTime());
		if (!user.getPassword().equals(oldUser.getPassword()))
			user.setPasswordExpired(0);
		long time = System.currentTimeMillis();
		user.setLatestModifyTime(time);
		File file = new File(getUserfilename(user.getUsername()));
		
		file.renameTo(new File(file.getAbsoluteFile() + "@" + time));

		Tools.saveStringToFile(Tools.getJson(user),
				getUserfilename(user.getUsername()));
		user.setPassword(PasswordMask);
		return user;

	}

	public static boolean deleteUser(String username) throws IOException {

		File dir = new File(InnerConfig.defaultConfig().getDataDir()
				+ deletedUserDir);

		if (dir.exists() == false)
			dir.mkdirs();

		long time = System.currentTimeMillis();
		 
		File file = new File(getUserfilename(username));

		file.renameTo(new File(file.getAbsoluteFile() + "@" + time+"_deleted"));

		return true;

	}

	public static boolean userExist(String username)
			throws IOException {
		File file = new File(getUserfilename(username));

		return file.exists();
	}

	
 
 	/**
 	 * reset user password 
 	 * @param username
 	 * @return
 	 * @throws IOException
 	 */
 	public static String resetPassword(String username)
			throws IOException {
		 User user=getUserinfo(username);
		 String password=RandomString.nextString();
		 user.setPassword(password);
		 
		 long expired=System.currentTimeMillis()+InnerConfig.defaultConfig().getPasswordExprired()*60*1000;
		 user.setPasswordExpired(expired);
		 log.debug("RandomString:"+password);
		 modifyUser(user,true);
		 return password;
		 
	}
	
 	
 	public static User Login(String username,String password) throws IOException {
 		User user=null;
 		try {
 			user=getUserinfo(username);
 		} catch (  NoSuchFileException e) {
 			return null;
 		}
 		if (user.getUserType().endsWith(User.LOCAL)) {
 			long expired=user.getPasswordExpired();
 			long now=System.currentTimeMillis();
 			if (expired>0 && expired<now) {
 				log.info("Password Expired:"+username);
 				return null;
 			}
 			if (!user.getPassword().equals(password)) 
 				return null;
 			
 		}else {
 			OpenLDAPConfig ldapConfig;
			try {
				ldapConfig = Config.getOpenLDAPConfig();
				OpenLDAP.Login(ldapConfig, username, password);
				 
			} catch (  Exception e) {
				log.info("OpenLDAPConfig login fail"+e.getMessage());
				 return null;
			}
 			
 		}
 		return user;
 	}
}

package com.winhong.plugins.cicd.tool;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.bind.DatatypeConverter;

import org.apache.http.client.utils.URLEncodedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winhong.plugins.cicd.data.base.Trigger;
import com.winhong.plugins.cicd.system.Config;
import com.winhong.plugins.cicd.system.InnerConfig;
import com.winhong.plugins.cicd.system.JenkinsConfig;

public class JenkinsClient {

	private String url;
	private String name;
	private String password;

	private String viewConfigFile = "WinGrow/config/viewConfig.xml";

	private static JenkinsClient client;
	private String crumb;
	private String crumbField;
	private static final Logger log = LoggerFactory.getLogger(JenkinsClient.class);
	private static final int BUFFER_SIZE = 4096;

	// private JenkinsServer server;

	public JenkinsClient(String url, String name, String password) {
		super();

		this.url = url;

		this.name = name;
		this.password = password;

	}

	public static JenkinsClient defaultClient() throws InstantiationException, IllegalAccessException, IOException {
		if (client == null) {
			JenkinsConfig con = Config.getJenkinsConfig();
			client = new JenkinsClient(con.getUrl(), con.getUser(), con.getPassword());
		}
		return client;

	}

	public boolean triggerBuild(String jobName) throws MalformedURLException, IOException {
		// job/test2/build
		String buidlurl = url + "/job/" + URLEncoder.encode(jobName) + "/build";

		return httpSimpleModify(new URL(buidlurl));
		// throws IOException {

	}

	/**
	 * 为项目创建一个Jenkins，Credential，供调用
	 * 
	 * @param projectName
	 *            项目名称
	 * @param username
	 *            用户
	 * @param password
	 *            密码
	 * @param usage
	 *            用途
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public boolean createCredential(String projectName, String username, String password, String usage)
			throws MalformedURLException, IOException {

		// curl -H "$CRUMB" -H "Content-Type:application/json" -X POST
		// 'http://xiehq:acd12345@10.211.55.6:8080/credentials/store/system/domain/_/credential/test22-test/doDelete'
		try {
			String deleteUrl = url + "/credentials/store/system/domain/_/credential/" + projectName + "-" + usage
					+ "/doDelete";
			httpJobModify(new URL(deleteUrl), "", "");
		} catch (Exception e) {

		}
		// 没有设置用户
		if (username == null) {
			return true;
		}
		String content = "{ \"\": \"0\",  " + " \"credentials\": {  " + "   \"scope\": \"GLOBAL\"," + "   \"id\": \""
				+ projectName + "-" + usage + "\"," + "   \"username\": \"" + username + "\"," + "   \"password\": \""
				+ password + "\"," + "   \"description\": \"" + usage + "\","
				+ "   \"stapler-class\": \"com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl\" "
				+ "  }}";

		// URLEncodedUtils.parse(content, "UTF-8");
		String S = URLEncoder.encode(content, "UTF-8");
		String serverUrl = url + "/credentials/store/system/domain/_/createCredentials?json=" + S;
		log.debug("serverUrl:" + serverUrl);
		// return httpSimpleModifyWithoutContentType(new URL(serverUrl));

		int code = httpModifyWithReturnCode(new URL(serverUrl), "", "application/json");
		if (code == 403 || (code >= 200 && code < 300))
			return true;
		return false;

	}

	public boolean addOrModifyJob(String jobId, String content) throws IOException {
		if (jobExist(jobId)) {
			return modifyJob(jobId, content);
		} else {
			return addJob(jobId, content);
		}
	}

	public boolean deleteJob(String jobId) throws IOException {
		URL serverUrl = new URL(url + "/job/" + URLEncoder.encode(jobId) + "/doDelete"); // Jenkins
		// URL
		// localhost:8080,
		// job
		// named
		// 'test'
		int code = httpModifyWithReturnCode(serverUrl, null, null);
		if (code == 403 || (code >= 200 && code < 300))
			return true;
		return false;
	}

	public boolean modifyJob(String jobId, String content) throws IOException {
		URL serverUrl = new URL(url + "/job/" + URLEncoder.encode(jobId) + "/config.xml"); // Jenkins
		// URL
		// localhost:8080,
		// job
		// named
		// 'test'
		return httpJobModify(serverUrl, content);
	}

	public boolean addJob(String jobId, String content) throws IOException {
		URL serverUrl = new URL(url + "/createItem?name=" + URLEncoder.encode(jobId));
		if (jobExist(jobId))
			throw new IOException("项目：" + jobId + " 已经存在！");
		// URL serverUrl = new URL(
		// "http://xiehq:acd12345@10.211.55.6:8080/createItem?name="
		// + jobId);

		// Jenkins URL localhost:8080, job named 'test'
		return httpJobModify(serverUrl, content);

	}

	public boolean jobExist(String jobId) throws IOException {
		URL serverUrl = new URL(url + "/job/" + URLEncoder.encode(jobId)); // Jenkins
																			// URL
		// localhost:8080, job
		// named 'test'
		log.debug("serverUrl" + serverUrl.toString());
		HttpURLConnection connection = getConnection(serverUrl, "GET");

		connection.connect();

		int code = connection.getResponseCode();
		log.debug(serverUrl.toString() + " return code:" + code);
		if (code >= 200 && code < 300) {
			/*
			 * InputStream inputStream = connection.getInputStream();
			 * 
			 * byte[] buffer = new byte[BUFFER_SIZE]; int bytesRead; while ((bytesRead =
			 * inputStream.read(buffer)) != -1) { System.out.println(new String(buffer)); }
			 */
			return true;
		}

		return false;

	}

	private HttpURLConnection getConnection(URL serverUrl, String method) throws IOException {
		String authStr = name + ":" + password;
		String encoding = DatatypeConverter.printBase64Binary(authStr.getBytes("utf-8"));

		HttpURLConnection connection = (HttpURLConnection) serverUrl.openConnection();

		connection.setRequestProperty("Authorization", "Basic " + encoding);

		connection.setReadTimeout(10000);
		connection.setConnectTimeout(15000);
		connection.setRequestMethod(method);
		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection.setDoOutput(true);

		return connection;

	}

	// http://xiehq:acd12345@10.211.55.6:8080/crumbIssuer/api/xml?xpath=concat(//crumbRequestField,":",//crumb)

	public boolean getCrumb() throws IOException {
		// String authStr = name + ":" + password;
		URL serverUrl = new URL(url + "/crumbIssuer/api/xml?xpath=concat(//crumbRequestField,\":\",//crumb)"); // Jenkins
		HttpURLConnection connection = getConnection(serverUrl, "GET");

		connection.connect();

		int code = connection.getResponseCode();
		log.debug(serverUrl.toString() + " return code:" + code);
		if (code >= 200 && code < 300) {

			InputStream inputStream = connection.getInputStream();

			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead;
			bytesRead = inputStream.read(buffer);
			if (bytesRead < 40) {
				return false;
			}
			String crumbRequestField = new String(buffer, 0, bytesRead);
			String[] array = crumbRequestField.split(":");
			// log.debug("array:"+array);

			crumbField = array[0];
			crumb = array[1];

			log.debug(crumbField + ":" + crumb);

			return true;
		} else {
			throw new IOException("Get crumb failed! code=" + code + "url=" + url);
		}
		// return false;
	}

	private boolean httpJobModify(URL serverUrl, String content) throws IOException {
		return httpJobModify(serverUrl, content, null);
	}

	private boolean httpJobModify(URL serverUrl, String content, String contentType) throws IOException {

		if (crumbField == null)
			if (!getCrumb())
				return false;

		// log.debug(crumbField + ":" + crumb);
		log.debug("serverUrl:" + serverUrl.toString());
		log.debug("content:" + content);
		log.debug("contentType:" + contentType);

		HttpURLConnection connection = getConnection(serverUrl, "POST");
		connection.setRequestProperty(crumbField, crumb);
		if (contentType == null)
			connection.setRequestProperty("Content-Type", "text/xml");
		else {

			connection.setRequestProperty("Content-Type", contentType);

		}
		connection.setRequestProperty("charset", "UTF-8");

		// InputStream inputStream = connection.getInputStream();

		// @SuppressWarnings("resource")
		// FileInputStream inputStream = new FileInputStream(file);

		// content = new String(content.getBytes("GBK"), "UTF-8");
		if (content != null && content.isEmpty() == false) {
			try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {

				// byte b[] = (content).getBytes();
				byte b[] = Tools.ToUTF8(content).getBytes();

				wr.write(b, 0, b.length);

			}
			log.debug("SEND content:" + content);

		}

		// connection.connect();
		// log.debug("resp:"+connection.getResponseMessage());

		// 测试代码
		/*
		 * InputStream content = connection.getInputStream(); BufferedReader in = new
		 * BufferedReader(new InputStreamReader(content)); String line; while ((line =
		 * in.readLine()) != null) { System.out.println(line); }
		 */
		// 以上为测试代码
		int code = connection.getResponseCode();

		log.debug(serverUrl.toString() + " return code:" + code);
		log.debug(connection.getResponseMessage());

		if (code >= 200 && code < 300) {
			return true;
		} else {
			String out = Tools.getHttpResult(connection);
			throw new IOException("Server out:" + out);
		}

	}

	public InputStream getFile(String path) throws IOException {

		if (crumbField == null)
			if (!getCrumb()) {
				throw new IOException("get file fail,can't get Crumb ");
			}

		// log.debug(crumbField + ":" + crumb);
		// log.debug("serverUrl:" + serverUrl.toString());
		// log.debug("content:" + content);

		URL serverUrl = new URL(url + path);

		HttpURLConnection connection = getConnection(serverUrl, "POST");
		connection.setRequestProperty(crumbField, crumb);

		int code = connection.getResponseCode();

		log.debug(serverUrl.toString() + " return code:" + code);

		if (code >= 200 && code < 300) {

			InputStream serverOut = connection.getInputStream();

		 

			return serverOut;
		} else {
			log.debug(connection.getResponseMessage());
			throw new IOException("get file fail");
		}

	}

	public boolean addOrModifyView(String viewId, String description) throws IOException {
		if (viewExist(viewId)) {
			return modifyView(viewId, description);
		} else {
			return addView(viewId, description);
		}
	}

	public boolean deleteview(String viewId) throws IOException {
		URL serverUrl = new URL(url + "/view/" + URLEncoder.encode(viewId) + "/doDelete"); // Jenkins
		// URL
		// localhost:8080,
		// view
		// named
		// 'test'
		return httpViewModify(serverUrl, null, null);
	}

	public boolean modifyView(String viewId, String description) throws IOException {
		URL serverUrl = new URL(url + "/view/" + URLEncoder.encode(viewId) + "/config.xml"); // Jenkins
		// URL
		// localhost:8080,
		// view
		// named
		// 'test'
		return httpViewModify(serverUrl, viewId, description);
	}

	public boolean addView(String viewId, String description) throws IOException {
		URL serverUrl = new URL(url + "/createView?name=" + URLEncoder.encode(viewId));

		// URL serverUrl = new URL(
		// "http://xiehq:acd12345@10.211.55.6:8080/createItem?name="
		// + viewId);

		// Jenkins URL localhost:8080, view named 'test'
		return httpViewModify(serverUrl, viewId, description);

	}

	public boolean viewExist(String viewId) throws IOException {
		URL serverUrl = new URL(url + "/view/" + URLEncoder.encode(viewId)); // Jenkins
																				// URL
		// localhost:8080,
		// view
		// named 'test'

		HttpURLConnection connection = getConnection(serverUrl, "GET");

		connection.connect();

		int code = connection.getResponseCode();
		log.debug(serverUrl.toString() + " return code:" + code);
		if (code >= 200 && code < 300) {
			/*
			 * InputStream inputStream = connection.getInputStream();
			 * 
			 * byte[] buffer = new byte[BUFFER_SIZE]; int bytesRead; while ((bytesRead =
			 * inputStream.read(buffer)) != -1) { System.out.println(new String(buffer)); }
			 */
			return true;
		}

		return false;

	}

	public String getLatestBuild(String jobid) throws IOException {
		URL serverUrl = new URL(url + "/job/" + jobid + "/lastBuild/api/json");

		HttpURLConnection connection = getConnection(serverUrl, "GET");

		connection.connect();

		int code = connection.getResponseCode();
		log.debug(serverUrl.toString() + " return code:" + code);
		String line = "";
		String out = "";
		InputStream serverOut = null;
		BufferedReader in =null;
		try {
			serverOut = connection.getInputStream();
			 in=new BufferedReader(new InputStreamReader(serverOut));

			while ((line = in.readLine()) != null) {
				out += line;
			}

			if (code >= 200 && code < 300) {
				return out;
			} else {

				log.debug(connection.getResponseMessage());
				throw new IOException("Server out:" + out);
			}
		} finally {
			try {
				in.close();
			} catch (Exception e) {

			}
			try {
				serverOut.close();
			} catch (Exception e) {

			}
		}

	}

	/**
	 * 创建或者修改VIEW ,注意只修改name description
	 * 
	 * @param serverUrl
	 * @param name
	 * @param description
	 * @return
	 * @throws IOException
	 */
	public boolean httpViewModify(URL serverUrl, String name, String description) throws IOException {

		if (crumbField == null)
			if (!getCrumb())
				return false;

		log.debug(crumbField + ":" + crumb);

		// URL serverUrl = new URL(url+"/createView?name=" + name);

		// URL serverUrl=new URL(this.url+/createItem?name=);

		HttpURLConnection connection = getConnection(serverUrl, "POST");

		connection.setRequestProperty(crumbField, crumb);
		connection.setRequestProperty("Content-Type", "text/xml");
		connection.setRequestProperty("charset", "UTF-8");

		// ClassLoader classLoader = getClass().getClassLoader();
		// URL re = classLoader.getResource(viewConfigFile);
		// if (re == null) {
		// throw new IOException("view config file not exist!");
		// }

		//@SuppressWarnings("resource")
		// File file = new File(re.getFile());
		//
		String content = Tools.readResource(viewConfigFile, false);

		// description = new String(description.getBytes("GBK"), "ASCII");

		if (name != null)
			content = content.replace("#name", (name));
		if (description != null)
			content = content.replace("#description", (description));

		// InputStream inputStream = connection.getInputStream();

		// @SuppressWarnings("resource")
		// FileInputStream inputStream = new FileInputStream(file);

		content = Tools.ToUTF8(content);
		// connection.setRequestProperty("charset", "GBK");

		// tools.readFile(file).toString();
		log.debug("content=" + content);
		log.debug("serverUrl=" + serverUrl);
		//write content to server
		try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
			byte b[] = content.getBytes();
			wr.write(b, 0, b.length);

		}

		// connection.connect();
		// log.debug("resp:"+connection.getResponseMessage());

		// 测试代码

		// 以上为测试代码
		int code = connection.getResponseCode();

		log.debug(serverUrl.toString() + " return code:" + code);

		if (code >= 200 && code < 300) {
			return true;
		} else if (code != 403) {
			String out = Tools.getHttpResult(connection);
			throw new IOException("Server out:" + out);
		} else
			return true;

	}

	// curl -X POST -H "$CRUMB"
	// http://xiehq:acd12345@10.211.55.6:8080/view/test22/addJobToView?name=maskio

	public boolean addJobToView(String viewId, String jobId) throws IOException {
		URL serverUrl = new URL(url + "/view/" + viewId + "/addJobToView?name=" + jobId); // Jenkins URL
		return httpSimpleModify(serverUrl);

	}

	public boolean removeJobFromView(String viewId, String jobId) throws IOException {
		URL serverUrl = new URL(url + "/view/" + viewId + "/removeJobFromView?name=" + jobId); // Jenkins URL
		return httpSimpleModify(serverUrl);

	}

	/**
	 * 执行HTTP post
	 * 
	 * @param serverUrl
	 * @param name
	 * @param description
	 * @return
	 * @throws IOException
	 */
	public boolean httpSimpleModify(URL serverUrl) throws IOException {

		if (crumbField == null)
			if (!getCrumb())
				return false;

		log.debug(crumbField + ":" + crumb);

		// URL serverUrl = new URL(url+"/createView?name=" + name);

		// URL serverUrl=new URL(this.url+/createItem?name=);

		HttpURLConnection connection = getConnection(serverUrl, "POST");

		connection.setRequestProperty(crumbField, crumb);
		connection.setRequestProperty("Content-Type", "text/xml");
		log.debug(serverUrl.toString() );
		Tools.getHttpResult( connection);
		return true;
	}

	
	
	
	public String httpSimpleGet(String a_url) throws IOException {
		URL serverUrl = new URL(url + a_url);
		return httpSimpleGet(serverUrl);
	}

	public String httpSimpleGet(URL serverUrl) throws IOException {

		if (crumbField == null)
			if (!getCrumb())
				throw new IOException("get crumb fail");

		log.debug(crumbField + ":" + crumb);

		// URL serverUrl = new URL(url+"/createView?name=" + name);

		// URL serverUrl=new URL(this.url+/createItem?name=);

		HttpURLConnection connection = getConnection(serverUrl, "GET");

		connection.setRequestProperty(crumbField, crumb);
		connection.setRequestProperty("Content-Type", "text/xml");
		connection.setRequestProperty("charset", "UTF-8");

		return Tools.getHttpResult(connection); 
	}

	public String httpSimpleModifyWithResult(URL serverUrl) throws IOException {

		if (crumbField == null)
			if (!getCrumb())
				throw new IOException("get Crumb fail!");

		log.debug(crumbField + ":" + crumb);

		// URL serverUrl = new URL(url+"/createView?name=" + name);

		// URL serverUrl=new URL(this.url+/createItem?name=);

		HttpURLConnection connection = getConnection(serverUrl, "POST");

		connection.setRequestProperty(crumbField, crumb);
		connection.setRequestProperty("Content-Type", "text/xml");

		return Tools.getHttpResult( connection);
 
	}

	private int httpModifyWithReturnCode(URL serverUrl, String content, String contentType) throws IOException {

		if (crumbField == null)
			if (!getCrumb())
				return -1;

		// log.debug(crumbField + ":" + crumb);
		log.debug("serverUrl:" + serverUrl.toString());
		log.debug("content:" + content);
		log.debug("contentType:" + contentType);

		HttpURLConnection connection = getConnection(serverUrl, "POST");
		connection.setRequestProperty(crumbField, crumb);
		if (contentType == null)
			connection.setRequestProperty("Content-Type", "text/xml");
		else {

			connection.setRequestProperty("Content-Type", contentType);

		}
		connection.setRequestProperty("charset", "UTF-8");

		// InputStream inputStream = connection.getInputStream();

		// @SuppressWarnings("resource")
		// FileInputStream inputStream = new FileInputStream(file);

		// content = new String(content.getBytes("GBK"), "UTF-8");
		if (content != null && content.isEmpty() == false) {
			try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {

				// byte b[] = (content).getBytes();
				byte b[] = Tools.ToUTF8(content).getBytes();

				wr.write(b, 0, b.length);

			}
			log.debug("SEND content:" + content);

		}

		int code = connection.getResponseCode();

		return code;
	}
	public String httpShowStatistics(URL serverUrl, String content, String contentType) throws IOException{
		if(crumbField == null){
			if(!getCrumb()){
				throw new IOException("crumb is faild!");
			}
		}
		log.debug("serverUrl :"+ serverUrl.toString());
		log.debug("content : " + content);
		log.debug("ContenType:" + contentType);
		HttpURLConnection connection = getConnection(serverUrl, "POST");
		connection.setRequestProperty(crumbField, crumb);
		if(contentType != null && contentType.trim() != ""){
			connection.setRequestProperty("Content-Type", contentType);
		}else{
			connection.setRequestProperty("Content-Type", "application/json");
		}
		connection.setRequestProperty("charset", "UTF-8");
		DataOutputStream wr = null;
		if(content != null && content.isEmpty() == false){
			wr = new DataOutputStream(connection.getOutputStream());
			byte b[] = Tools.ToUTF8(content).getBytes();
			wr.write(b, 0, b.length);
		}
		log.debug("SEND CONTENT" + content);
		int code = connection.getResponseCode();
		log.debug(serverUrl.toString() + " return code:" + code);
		String line = "";
		String out = "";
		InputStream serverOut = null;
		BufferedReader in =null;
		try {
			serverOut = connection.getInputStream();
			 in=new BufferedReader(new InputStreamReader(serverOut));

			while ((line = in.readLine()) != null) {
				out += line;
			}
			log.debug("out--------:" + out);
			if (code >= 200 && code < 300) {
				return out;
			} else {

				log.debug(connection.getResponseMessage());
				throw new IOException("Server out:" + out);
			}
		} finally {
			try {
				in.close();
			} catch (Exception e) {

			}
			try {
				serverOut.close();
			} catch (Exception e) {

			}
		}
		
	}
	//url:baseurl+/statistics/projectStat
	public String projectStatShow(String content) throws IOException{
		URL serverUrl = new URL(url + "/statistics/projectstat");
		return httpShowStatistics(serverUrl, content, null);
	}
	//url:baseurl+/statistics/buildStat
	public String projectBuildStat(String content) throws IOException{
		URL serverUrl = new URL(url + "/statistics/buildstat");
		return httpShowStatistics(serverUrl, content, null);
	}
	//url:${JENKINS_PATH}/buildDetail/all?groupId=wwe&start=1510734599733&end=1520734599733
	public String buildDetialAll(String groupId, String start, String end) throws IOException{
		String url = this.url + "/buildDetail/all?groupId="+groupId+"&start="+start+"&end="+end;
		URL serverurl = new URL(url);
		log.debug("SEND URL:  "+url);
		String result = httpSimpleGet(serverurl);
		
		return result;
	}
	//url:${JENKINS_PATH}/buildDetail/all?groupId=wwe&start=1510734599733&end=1520734599733
	public String groupByStatus(String groupId, String start, String end) throws IOException{

		String url = this.url + "/buildDetail/groupByStatus?groupId="+groupId+"&start="+start+"&end="+end;
		URL serverurl = new URL(url);
		log.debug("SEND URL:  "+url);
		String result = httpSimpleGet(serverurl);
		
		return result;
	}
}

package com.winhong.plugins.cicd.action;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.winhong.plugins.cicd.data.base.RegistryOuth;
import com.winhong.plugins.cicd.system.RegistryConfig;

public class ConfigServerAction {
	private static final Logger log = LoggerFactory
			.getLogger(ConfigServerAction.class);
	//配置请求的jenkins-slave服务url
	private static String configServerUrl = null;
	//获取jenkins-slave服务下的容器列表的url,获取容器列表后再获取ip
	private static String cotainerMetadata = "http://rancher-metadata/latest/services/jenkins-slave/containers/";
	static{
		String envValue = System.getenv("CONFIG_SERVER_URL");
		if(envValue == null || envValue.trim().length() == 0){
			configServerUrl = "http://jenkins-slave:8090";
		}else {
			configServerUrl = System.getenv("CONFIG_SERVER_URL");
		}
	}
	//httpPost
	public static String sendPost(String uri, String data) throws Exception{
		//URL url = new URL(configServerUrl+"/docker/"+uri);
		URL url = new URL(uri);
		log.debug("rest server url: " + url.getPath());
		URLConnection con = url.openConnection();
		HttpURLConnection http = (HttpURLConnection)con;
		http.setRequestMethod("POST"); // PUT is another valid option
		http.setDoOutput(true);
		//"{\"url\":\"http://10.0.2.50 \",\"secure\":\"false\"}" 
		byte[] out = data.getBytes(StandardCharsets.UTF_8);
		int length = out.length;

		http.setFixedLengthStreamingMode(length);
		http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		http.connect();
		try(OutputStream os = http.getOutputStream()) {
		    os.write(out);
		}
		BufferedReader br = null;
		if (200 <= http.getResponseCode() && http.getResponseCode() <= 299) {
		    br = new BufferedReader(new InputStreamReader(http.getInputStream()));
		} else {
		    br = new BufferedReader(new InputStreamReader(http.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String output;
		String result = null;
		while ((output = br.readLine()) != null) {
		sb.append(output);
	    result = sb.toString();
		log.debug("response info: " + result);
	   }
		return result;
	}
	/**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            //connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            //connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    
    //向所有的jenkins slave发送数据
    //jenkins slave auth config
	public static void configAuthServer(RegistryConfig config) throws Exception{
		//jenkins slave config
		ArrayList<RegistryOuth> roList = new ArrayList<RegistryOuth>();
		RegistryOuth ro = new RegistryOuth(config.getServer(), config.getAuth());
		roList.add(ro);
		String data = new Gson().toJson(roList);
		
		//String response = ConfigServerAction.sendPost("auths", data);
		List<String> ips = getJenkins_slave_ip();
		int ipSize = ips.size();
		
		for(int i=0 ; i<ipSize ; i++){
			String singleIp = ips.get(i);
			String uri = "http://"+singleIp+":8090/docker/auth";
			String response = sendPost(uri, data);
			log.debug("jenkins slave data state " + uri +"-----" +response);
		}
	}
	//jenkins slave daemon.json config
	public static void configDaemonServer(String data) throws Exception{
		List<String> ips = getJenkins_slave_ip();
		int ipSize = ips.size();
		for(int i=0 ; i<ipSize ; i++){
			String singleIp = ips.get(i);
			String uri = "http://"+singleIp+":8090/docker/daemon";
			String response = sendPost(uri, data);
			log.debug("jenkins slave data state " + uri +"-----" +response);
		}
	}
	
	//获取rancher中的所有jenkins-slave服务下的容器ip
	//curl http://rancher-metadata/latest/services/jenkins-slave/containers/0/primary_ip   10.42.254.61
	//curl http://rancher-metadata/latest/services/jenkins-slave/containers/1/primary_ip   10.42.75.119
	public static List<String> getJenkins_slave_ip() {
		String result = sendGet(cotainerMetadata, null);
		result = result.substring(result.length()-1, result.length());
		//System.out.println(result);
		int slaveNumber = Integer.parseInt(result);
		log.debug("jenkins slave number: " + slaveNumber);
		if( slaveNumber != 0 ) {
			slaveNumber = slaveNumber -1;
		}else {
			return null;
		}
		List<String> ipList = new ArrayList<String>();
		for(int i=0; i<=slaveNumber; i++) {
			String ip = sendGet(cotainerMetadata+i+"/primary_ip", null);
			log.debug("single jenkins slave ip: " + ip);
			ipList.add(ip);
		}
		return ipList;
	}
	
	//向jenkins_slave服务中的所用ip发送数据
	
}

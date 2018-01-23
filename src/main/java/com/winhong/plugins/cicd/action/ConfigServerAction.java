package com.winhong.plugins.cicd.action;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.winhong.plugins.cicd.data.base.RegistryOuth;
import com.winhong.plugins.cicd.system.RegistryConfig;

public class ConfigServerAction {
	private static final Logger log = LoggerFactory
			.getLogger(ConfigServerAction.class);
	//配置请求的url
	private static String configServerUrl = null;
	static{
		String envValue = System.getenv("CONFIG_SERVER_URL");
		if(envValue == null || envValue.trim().length() == 0){
			configServerUrl = "http://jenkins-slave:8090";
		}else {
			configServerUrl = System.getenv("CONFIG_SERVER_URL");
		}
	}
	public static String httpTest(String uri, String data) throws Exception{
		URL url = new URL(configServerUrl+"/docker/"+uri);
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
	public static void configRegistryServer(RegistryConfig config) throws Exception{
		//jenkins slave config
		ArrayList<RegistryOuth> roList = new ArrayList<RegistryOuth>();
		RegistryOuth ro = new RegistryOuth(config.getServer(), config.getAuth());
		roList.add(ro);
		String data = new Gson().toJson(roList);
		String response = ConfigServerAction.httpTest("auths", data);
		log.debug("jenkins slave registry post : " + response);
	}
}

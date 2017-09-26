package com.winhong.plugins.cicd.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Base64;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.winhong.plugins.cicd.data.base.EnumList;
import com.winhong.plugins.cicd.system.Config;
import com.winhong.plugins.cicd.system.RancherConfig;
import com.winhong.plugins.cicd.system.RegistryConfig;
import com.winhong.plugins.cicd.view.displayData.RancherEnvironment;

public class RancherClient {

	private static final Logger log = LoggerFactory.getLogger(RancherClient.class);

	/**
	 * 返回一个enum field 列表，主要供package cicd.MavenProperty使用
	 * 
	 * @return
	 */
	public static ArrayList<EnumList> genRancherEnvEnumList() {
		ArrayList<EnumList> relist = new ArrayList<EnumList>();
		// if (registryList.size()==0){
		try {

			ArrayList<RancherEnvironment> list = getEnvironments();

			for (int i = 0; i < list.size(); i++) {

				relist.add(new EnumList(list.get(i).getId(), list.get(i).getName()));
			}
			return relist;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			return null;
		}

	}

	public static ArrayList<RancherEnvironment> getEnvironments()
			throws MalformedURLException, IOException, InstantiationException, IllegalAccessException {
		JsonParser parser = new JsonParser();
		RancherConfig rancherConfig = Config.getRancherConfig();
		String envUrl = rancherConfig.getServerUrl() + "/v2-beta/projects?all=true&limit=-1";

		String toExtract = httpSimpleGet(new URL(envUrl));
		JsonObject obj = parser.parse(toExtract).getAsJsonObject();
		JsonArray data = obj.get("data").getAsJsonArray();

		ArrayList<RancherEnvironment> envs = new ArrayList<RancherEnvironment>();

		// return gson.fromJson(json, cla);
		for (int i = 0; i < data.size(); i++) {
			JsonObject ele = data.get(i).getAsJsonObject();

			RancherEnvironment env = new RancherEnvironment(ele.get("id").getAsString(), ele.get("name").getAsString());
			envs.add(env);

		}

		return envs;

	}

	private static HttpURLConnection getConnection(URL serverUrl, String method)
			throws IOException, InstantiationException, IllegalAccessException {
		RancherConfig rancherConfig = Config.getRancherConfig();

		String authStr = rancherConfig.getAccessKey() + ":" + rancherConfig.getSecureKey();
		String encoding = DatatypeConverter.printBase64Binary(authStr.getBytes("utf-8"));

		HttpURLConnection connection = (HttpURLConnection) serverUrl.openConnection();
		// String userPassword = "username" + ":" + "password";

		connection.setRequestProperty("Authorization", "Basic " + encoding);
		connection.setRequestProperty("Accept", "application/json");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setReadTimeout(10000);
		connection.setConnectTimeout(15000);
		connection.setRequestMethod(method);
		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection.setDoOutput(true);

		return connection;

	}

	public static String httpSimpleGet(URL serverUrl)
			throws IOException, InstantiationException, IllegalAccessException {

		HttpURLConnection connection = getConnection(serverUrl, "GET");

		return Tools.getHttpResult( connection);

	}

}

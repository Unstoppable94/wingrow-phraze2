package com.winhong.plugins.cicd.data.base;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.winhong.plugins.cicd.tool.Tools;

public class RegistryOuthTest {
	public static void main(String[] args) {
		
		ArrayList<RegistryOuth> res = new ArrayList<RegistryOuth>();
		RegistryOuth ro = new RegistryOuth();
		ro.setAuth("afdsfdsf==");
		ro.setServer("http://1.0.0.0");
		res.add(ro);
		Gson gson = new Gson();
		String result = gson.toJson(res);
		System.out.println(result);
	}
}

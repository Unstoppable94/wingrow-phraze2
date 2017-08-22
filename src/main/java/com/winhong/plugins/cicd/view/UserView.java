package com.winhong.plugins.cicd.view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import com.winhong.plugins.cicd.action.UserAction;
import com.winhong.plugins.cicd.system.InnerConfig;
import com.winhong.plugins.cicd.tool.Tools;
import com.winhong.plugins.cicd.user.User;

public class UserView {

	private static final Logger log = LoggerFactory.getLogger(UserView.class);

	private String userid;

	private static final String buildsUrl = "/api/json?tree=builds[name,building,result,number,duration,timestamp,building,artifacts[fileName,relativePath]]";

	private static final String simpleJobsUrl = "/api/json?tree=jobs[name]";
	private static int maxrows = 300;

	private static final int defaultMaxLine = 10;

	public static String getUserList(int start, int maxLine) throws Exception {
		return getUserList(null, start, maxLine);
	}

	public static String getUserList(String userName, int start, int maxLine)
			throws Exception {
		if (start < 0)
			start = 0;

		if (maxLine == 0)
			maxLine = defaultMaxLine;

		ArrayList<User> v = UserAction.getAllUser(userName);

		userList ret = new UserView().new userList();
		ret.total = v.size();

		for (int i = start; i < start + maxLine; i++) {
			// 避免数组错误
			if (i >= v.size() || i < 0)
				break;
			User user = v.get(i);
			// 不显示密码
			user.setPassword(UserAction.PasswordMask);
			ret.users.add(user);

		}

		// replace to results to reduce web modify job
		return Tools.getJson(ret).replaceFirst("users", "results");

	}

	private class userList {
		@Expose
		private int total;

		@Expose
		private ArrayList<User> users = new ArrayList<User>();

		public int getTotal() {
			return total;
		}

		public void setTotal(int total) {
			this.total = total;
		}

		public ArrayList<User> getUsers() {
			return users;
		}

		public void setUsers(ArrayList<User> users) {
			this.users = users;
		}

		public userList() {
			super();
			// TODO Auto-generated constructor stub
		}

	}

}

package com.winhong.plugins.cicd.mavenStep;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.winhong.plugins.cicd.data.base.Stage;
import com.winhong.plugins.cicd.exception.ConfigCheckException;
import com.winhong.plugins.cicd.mavenProperty.Skip;
import com.winhong.plugins.cicd.mavenProperty.commonString;

public class DeployToRancher extends Stage {

	private final static String id = "deployToRancher";
	private final static String name = "部署到Rancher";
	private final static String description = "部署到Rancher";

	private Skip skip = new Skip();

	final private String serviceId = "service";

	private commonString service = new commonString(serviceId, "", 10, 50,
			"Rancher service", "部署的Rancher Service  NAME,采用remove 后run 模式部署️ ");

	final private String argId = "arg";
	private commonString arg = new commonString(argId, "", 0, 250,
			"Rancher 启动参数", "Rancher run的启动参数 ️");

	final private String cmdId = "cmd";
	private commonString cmd = new commonString(cmdId, "", 0, 250, "容器启动命令",
			"容器启动命令,可以不输入️");

	/**
	 * 生产一个新的对象
	 */
	public DeployToRancher() {
		super(id, name, description);
		this.setProperty(skip);
		this.setProperty(service);
		this.setProperty(arg);
		this.setProperty(cmd);

	}

	/*
	 * 使用json 恢复后都是property 类，必须进行实例转换并在调用检查类
	 * 
	 * @see com.winhong.plugins.cicd.data.base.Stage#check()
	 */
	@Override
	public boolean check() throws ConfigCheckException {

		skip = new Skip(this.getProperty(skip.getId()));
		service = new commonString(this.getProperty(serviceId));
		return skip.check() && service.check();

	}

 
}

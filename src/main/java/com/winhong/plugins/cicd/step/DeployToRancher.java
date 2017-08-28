package com.winhong.plugins.cicd.Step;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.winhong.plugins.cicd.data.base.Stage;
import com.winhong.plugins.cicd.exception.ConfigCheckException;
import com.winhong.plugins.cicd.property.RancherEnvIdProperty;
import com.winhong.plugins.cicd.property.Skip;
import com.winhong.plugins.cicd.property.commonString;

public class DeployToRancher extends Stage {

	private final static String id = "deployToRancher";
	private final static String name = "部署应用";
	private final static String description = "部署应用到指定环境";

	private Skip skip = new Skip();

	final private String environmentId = "environment";

	private RancherEnvIdProperty environment = new RancherEnvIdProperty(environmentId, "",
			"部署环境", "部署的 Service  NAME,采用remove 后run 模式部署️ ");
	
	final private String serviceId = "service";

	private commonString service = new commonString(serviceId, "", 10, 50,
			"部署服务service", "部署的 Service  NAME,采用remove 后run 模式部署️，格式为stack/service ");

	final private String argId = "arg";
	private commonString arg = new commonString(argId, "", 0, 250,
			"启动参数", "服务启动参数如 -p8080:8080 ️");

	final private String cmdId = "cmd";
	private commonString cmd = new commonString(cmdId, "", 0, 250, "容器启动命令",
			"Docker容器启动命令,可以不输入️");

	/**
	 * 生产一个新的对象
	 */
	public DeployToRancher() {
		super(id, name, description);
		this.setProperty(environment);
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
		environment =new RancherEnvIdProperty(this.getProperty(environmentId));
		if (skip.getValue().equals("false"))
			return   service.check() && environment.check();
		return skip.check();
	}

 
}

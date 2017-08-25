package com.winhong.plugins.cicd.mavenStep;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.winhong.plugins.cicd.data.base.Stage;
import com.winhong.plugins.cicd.exception.ConfigCheckException;
import com.winhong.plugins.cicd.mavenProperty.ContinueOnFail;
import com.winhong.plugins.cicd.mavenProperty.Url;
import com.winhong.plugins.cicd.property.Property;
import com.winhong.plugins.cicd.property.Skip;
import com.winhong.plugins.cicd.property.commonPassword;
import com.winhong.plugins.cicd.property.commonString;
import com.winhong.plugins.cicd.property.commonTextArea;

import java.lang.reflect.Modifier;

public class Sonar extends Stage {

	private final static String id = "sonar";
	private final static String name = "执行Sonar";
	private final static String description = "调用Sonar server进行代码检查";

	private ContinueOnFail continueOnfail = new ContinueOnFail();

	private Skip skip = new Skip();

	 
	/**
	 * 生产一个新的sonar对象
	 */
	public Sonar() {
		super(id, name, description);
		this.setProperty(continueOnfail);
		this.setProperty(skip);
		
	}

	/*
	 * 使用json 恢复后都是property 类，必须进行实例转换并在调用检查类
	 * 
	 * @see com.winhong.plugins.cicd.data.base.Stage#check()
	 */
	@Override
	public boolean check() throws ConfigCheckException {

		skip = new Skip(this.getProperty(skip.getId()));
		// TODO

		return true;
	}

	 
}

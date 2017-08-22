package com.winhong.plugins.cicd.mavenStep;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.winhong.plugins.cicd.data.base.Property;
import com.winhong.plugins.cicd.data.base.Stage;
import com.winhong.plugins.cicd.exception.ConfigCheckException;
import com.winhong.plugins.cicd.mavenProperty.ContinueOnFail;
import com.winhong.plugins.cicd.mavenProperty.RegistryProperty;
import com.winhong.plugins.cicd.mavenProperty.Skip;
import com.winhong.plugins.cicd.mavenProperty.Url;
import com.winhong.plugins.cicd.mavenProperty.commonPassword;
import com.winhong.plugins.cicd.mavenProperty.commonString;
import com.winhong.plugins.cicd.mavenProperty.commonTextArea;

import java.lang.reflect.Modifier;

public class CreateImage extends Stage {

	private final static String id = "createImage";
	private final static String name = "创建Image";
	private final static String description = "生成docker images";

	//private ContinueOnFail continueOnfail = new ContinueOnFail();

	private Skip skip = new Skip();

	final private  String  registryId = "registry";

	private RegistryProperty registry=new  RegistryProperty(registryId, "", "Registry", "Image 存放的Registry");

	final private String tagId = "tag";

	private commonString tag = new commonString(tagId, "", 1, 30,
			"Docker tag", "docker tag,系统生成的时候会默认在TAG后面加上编译执行序号️");

  

	/**
	 * 生产一个新的sonar对象
	 */
	public CreateImage() {
		super(id, name, description);
		//this.setProperty(continueOnfail);
		this.setProperty(skip);
 		this.setProperty(registry);
		this.setProperty(tag);
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

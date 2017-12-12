package com.winhong.plugins.cicd.step;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.winhong.plugins.cicd.data.base.Stage;
import com.winhong.plugins.cicd.exception.ConfigCheckException;
import com.winhong.plugins.cicd.mavenProperty.ContinueOnFail;
import com.winhong.plugins.cicd.mavenProperty.RegistryProperty;
import com.winhong.plugins.cicd.mavenProperty.Url;
import com.winhong.plugins.cicd.property.Property;
import com.winhong.plugins.cicd.property.Skip;
import com.winhong.plugins.cicd.property.commonPassword;
import com.winhong.plugins.cicd.property.commonString;
import com.winhong.plugins.cicd.property.commonTextArea;

import java.lang.reflect.Modifier;

public class CreateImage extends Stage {

	private final static String id = "createImage";
	private final static String name = "镜像打包信息配置";
	private final static String description = "生成docker images";

	//private ContinueOnFail continueOnfail = new ContinueOnFail();

	private Skip skip = new Skip();

	final static private  String  registryId = "registry";

	private RegistryProperty registry=new  RegistryProperty(registryId, "", "Registry", "Image 存放的Registry");

	final static private String tagId = "tag";

	private commonString tag = new commonString(tagId, "", 1, 30,
			"Docker tag", "docker tag, 格式为dir/image,系统生成的时候会默认在TAG后面加上编译执行序号");

	private commonString dockerfile=new commonString("dockerfile","./Dockerfile",2,100, "Dockerfile","默认为代码根目录下的Dockerfile文件 " );


	 
	/**
	 * 生产一个新的Image
	 */
	public CreateImage(boolean canSkip) {
		super(id, name, description);
		if (canSkip)
			this.setProperty(skip);
 		this.setProperty(registry);
		this.setProperty(tag);
 		this.setProperty(dockerfile);
		
 	}

	/*
	 * 使用json 恢复后都是property 类，必须进行实例转换并在调用检查类
	 * 
	 * @see com.winhong.plugins.cicd.data.base.Stage#check()
	 */
	@Override
	public boolean check() throws ConfigCheckException {

		skip = new Skip(this.getProperty(skip.getId()));
 
		return true;
	}

	 

}

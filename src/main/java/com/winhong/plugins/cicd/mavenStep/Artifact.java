package com.winhong.plugins.cicd.mavenStep;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.winhong.plugins.cicd.data.base.Property;
import com.winhong.plugins.cicd.data.base.Stage;
import com.winhong.plugins.cicd.exception.ConfigCheckException;
import com.winhong.plugins.cicd.mavenProperty.CompileGoal;
import com.winhong.plugins.cicd.mavenProperty.ContinueOnFail;
import com.winhong.plugins.cicd.mavenProperty.Skip;
import com.winhong.plugins.cicd.mavenProperty.Url;
import com.winhong.plugins.cicd.mavenProperty.commonTextArea;

import java.lang.reflect.Modifier;
 
public class Artifact extends Stage {

	private final static String id="artifact";
	private final static String name="执行打包";
	private final static String description="打包输出物";
	
	
	
	
	
	private ContinueOnFail continueOnfail=new ContinueOnFail();
	

	private Skip skip=new Skip();
	
 	
	
	/**
	 * 生产一个新的对象
	 */
	public Artifact() {
		super(id, name, description);
 		this.setProperty(continueOnfail);
		this.setProperty(skip);
  	}
	
	
	 

	/* 使用json 恢复后都是property 类，必须进行实例转换并在调用检查类
	 * 
	 * @see com.winhong.plugins.cicd.data.base.Stage#check()
	 */
	@Override
	public boolean check() throws ConfigCheckException {
		continueOnfail=new ContinueOnFail(this.getProperty(continueOnfail.getId()));
		if (!continueOnfail.check()){
			return false;
		}
		 
		skip=new Skip(this.getProperty(skip.getId()));
		if (!skip.check()){
			return false;
		}
		  
		
		return true;
	}
	
	 

}

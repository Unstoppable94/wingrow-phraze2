package com.winhong.plugins.cicd.mavenProperty;

import com.winhong.plugins.cicd.exception.ConfigCheckException;
import com.winhong.plugins.cicd.property.Property;

public class Url extends Property{

	public Url(Property p){
		super(p);
	}
	 
	
	public Url(String id,String name,String description){
		super(
				id,
				"string",
				true,
				true,
				false,
				"http://" ,// 默认必须通过才继续
				null,
				"",
				10,
				400,
				name,
				description
				);
	}
	
 
	public Url(String id,String name,String description,boolean required){
		super(
				id,
				"string",
				true,
				true,
				required,
				"http://" ,// 默认必须通过才继续
				null,
				"",
				10,
				400,
				name,
				description
				);
	}
	
	public Url(String id,String name,String description,String url){
		super(
				id,
				"string",
				true,
				true,
				false,
				url,// 默认必须通过才继续
				null,
				"",
				10,
				400,
				name,
				description
				);
	}
	
	
	 

	public Url(String id,String name,String description,boolean required,String url){
		super(
				id,
				"string",
				true,
				true,
				required,
				url,// 默认必须通过才继续
				null,
				"",
				10,
				400,
				name,
				description
				);
	}
	
	@Override
	public boolean check() throws ConfigCheckException {
		String goal=this.getValue();
		if (goal.length()< this.getMinLength() || goal.length() >this.getMaxLength()){
			throw new ConfigCheckException(this.getName()+".URL 长度不对！");
		}
		if (goal.toLowerCase().startsWith("http")==false){
			throw new ConfigCheckException(this.getName()+".URL 没有以http开头！");
		}
		return true;
	}


 	

}

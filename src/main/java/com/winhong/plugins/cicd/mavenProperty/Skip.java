package com.winhong.plugins.cicd.mavenProperty;

import com.winhong.plugins.cicd.data.base.Property;
import com.winhong.plugins.cicd.exception.ConfigCheckException;

public class Skip extends Property{
 
	public Skip(){
		super(
				"skip",
				"boolean",
				true,
				true,
				true,
				"false" ,// 默认必须通过才继续
				null,
				"",
				0,
				0,
				"是否跳过",
				"是否跳过当前步骤"
				);
	}
	
 
	public Skip(Boolean con){
		super(
				"skip",
				"boolean",
				true,
				true,
				true,
				con.toString().toLowerCase(),
				null,
				"",
				0,
				0,
				"失败后是否继续",
				"是否跳过当前步骤"
				);
	}
	 
	 


	public Skip(Property property) {
		super(property);
	}


	@Override
	public boolean check() throws ConfigCheckException {
		String goal=this.getValue();
		 if (!goal.equals("false") &&!goal.equals("true"))
			 	throw new ConfigCheckException("是否跳过，必须为true 或者false");
		return true;
	}


 	

}

package com.winhong.plugins.cicd.mavenProperty;

import com.winhong.plugins.cicd.exception.ConfigCheckException;
import com.winhong.plugins.cicd.property.Property;

public class ContinueOnFail extends Property{

	
	
	public ContinueOnFail(Property p) {
		super(p);
 	}


 
	public ContinueOnFail(){
		super(
				"continueOnfail",
				"boolean",
				true,
				true,
				true,
				"false" ,// 默认必须通过才继续
				null,
				"",
				0,
				0,
				"失败后是否继续",
				"失败后默认不继续"
				);
	}
	
 
	public ContinueOnFail(Boolean con){
		super(
				"continueOnfail",
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
				"失败后默认不继续"
				);
	}
	 
	 


	@Override
	public boolean check() throws ConfigCheckException {
		String goal=this.getValue();
		 if (!goal.equals("false") &&!goal.equals("true"))
			 	throw new ConfigCheckException("是否继续的值，必须为true 或者false");
		return true;
	}


 	

}

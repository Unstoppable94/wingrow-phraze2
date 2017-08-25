package com.winhong.plugins.cicd.property;

import com.winhong.plugins.cicd.exception.ConfigCheckException;

public class commonTextArea extends Property{

	
	
	
	public commonTextArea(Property p) {
		super(p);
 	}



	 
	
	 
	public commonTextArea(String id,String value,int min,int max,String name,String description){
		super(
				id,
				"string",
				true,
				true,
				true,
				value,
				null,
				"",
				min,
				max,
				name,
				description
				);
	}
	 

	@Override
	public boolean check() throws ConfigCheckException {
		String goal=this.getValue();
		if (goal.length()< this.getMinLength() || goal.length() >this.getMaxLength()){
			throw new ConfigCheckException(this.getName()+",文本内容长度不对！");
		}
		return true;
	}


 	

}

package com.winhong.plugins.cicd.mavenProperty;

import java.util.ArrayList;

import com.winhong.plugins.cicd.data.base.EnumList;
import com.winhong.plugins.cicd.data.base.Property;
import com.winhong.plugins.cicd.exception.ConfigCheckException;

public class commonPassword extends Property{

	
	
	
	public commonPassword(Property p) {
		super(p);
 	}



	public commonPassword(String id, 
			String type,
			boolean readable, 
			boolean writable, 
			boolean required, 
			String value,
			ArrayList<EnumList> enumValues, 
			String datePattern, 
			int minLength, 
			int maxLength, 
			String name, 
			String description) {
		super(id, type, readable, writable, required, value,enumValues , datePattern, minLength, maxLength, name, description);
	}
	
	
	 
	public commonPassword(String id,String value,int min,int max,String name,String description){
		super(
				id,
				"password",
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

package com.winhong.plugins.cicd.mavenProperty;

import com.winhong.plugins.cicd.exception.ConfigCheckException;
import com.winhong.plugins.cicd.property.Property;
import com.winhong.plugins.cicd.system.Config;
import com.winhong.plugins.cicd.system.RegistryList;
import com.winhong.plugins.cicd.tool.Tools;

public class RegistryProperty extends Property{

	
	
	
	public RegistryProperty(Property p) {
		super(p);
 	}


 
	 
	public RegistryProperty(String id,String value,String name,String description){
		super(
				id,
				"enum",
				true,
				true,
				true,
				value,
				Config.genRegistryEnumList(),
				"",
				1,
				1,
				name,
				description
				);
	}


	@Override
	public boolean check() throws ConfigCheckException {
		// TODO
		return true;
	}


 	

}

package com.winhong.plugins.cicd.property;

import java.util.ArrayList;

import com.winhong.plugins.cicd.data.base.EnumList;
import com.winhong.plugins.cicd.exception.ConfigCheckException;
 import com.winhong.plugins.cicd.tool.RancherClient;
 
public class RancherEnvIdProperty extends Property{

	
	
	
	public RancherEnvIdProperty(Property p) {
		super(p);
 	}


 
	 
	public RancherEnvIdProperty(String id,String value,String name,String description){
		super(
				id,
				"enum",
				true,
				true,
				true,
				value,
				RancherClient.genRancherEnvEnumList(),
				"",
				1,
				1,
				name,
				description
				);
	}


	@Override
	public boolean check() throws ConfigCheckException {
		ArrayList<EnumList> envs = RancherClient.genRancherEnvEnumList();
		for (int i=0;i<envs.size();i++) {
			if (envs.get(i).getId().equals(getValue()) ){
				return true;
			}
		}
		throw new ConfigCheckException(this.getName()+"的环境信息不存在");
	}


 	

}

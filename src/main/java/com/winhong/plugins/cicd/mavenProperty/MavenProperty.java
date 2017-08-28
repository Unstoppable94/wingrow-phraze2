package com.winhong.plugins.cicd.mavenProperty;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.winhong.plugins.cicd.data.base.EnumList;
import com.winhong.plugins.cicd.exception.ConfigCheckException;
import com.winhong.plugins.cicd.property.Property;
import com.winhong.plugins.cicd.system.InnerConfig;
import com.winhong.plugins.cicd.system.JdkConfig;
import com.winhong.plugins.cicd.system.MavenConfig;
import com.winhong.plugins.cicd.tool.RancherClient;
 
public class MavenProperty extends Property{

	
	
	
	public MavenProperty(Property p) {
		super(p);
 	}


 
	 
	public MavenProperty(String id,String value,String name,String description){
		super(
				id,
				"enum",
				true,
				true,
				true,
				value,
				genMavenList(),
				"",
				1,
				1,
				name,
				description
				);
	}


	@Override
	public boolean check() throws ConfigCheckException {
		ArrayList<MavenConfig> list;
		try {
			list = InnerConfig.defaultConfig().getMaven();
			for (int i=0;i<list.size();i++) {
				if (list.get(i).getId().equals(getValue()) ){
					return true;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		throw new ConfigCheckException(this.getName()+"的JDK信息不存在");
	}


	public static ArrayList<EnumList> genMavenList() {
		ArrayList<EnumList> relist = new ArrayList<EnumList>();
		// if (registryList.size()==0){
		try {

			ArrayList<MavenConfig> list=InnerConfig.defaultConfig().getMaven();
			
			for (int i = 0; i < list.size(); i++) {

				relist.add(new EnumList(list.get(i).getId(), list.get(i)
						.getVersion()));
			}
			return relist;
		} catch (Exception e) {
 			e.printStackTrace();
			return null;
		}

	}
 	

}

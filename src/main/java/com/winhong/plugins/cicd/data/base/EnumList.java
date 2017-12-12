package com.winhong.plugins.cicd.data.base;

import com.google.gson.annotations.Expose;

/**
 * 这个类用于保存web 下拉列表显示需要的字段
 * @author xiehuiqiang
 *
 */
public class EnumList {

	/**
	 *内部值 
	 */
	@Expose
	String id;
	
	/**
	 *显示值 
	 */
	@Expose
	String name;
	
	@Expose
	String description;
	
	public EnumList() {
 	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public EnumList(String id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	public EnumList(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

}

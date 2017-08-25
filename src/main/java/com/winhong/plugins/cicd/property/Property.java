package com.winhong.plugins.cicd.property;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.winhong.plugins.cicd.data.base.EnumList;
import com.winhong.plugins.cicd.exception.ConfigCheckException;

public  class Property {
	
 
	/**
	 * 数据ID，用于内部
	 */
	@Expose
	private String id;

	/**
	 * 数据类型，enum（列表），string，date，int，long 具体待定
	 */
	@Expose
	private String type;
	
	/**
	 * 是否显示
	 */
	@Expose
	private boolean readable;
	
	/**
	 * 是否可以修改
	 */
	@Expose
	private boolean writable;
	
	/**
	 * 是否必须显示
	 */
	@Expose
	private boolean required;
	
	
	/**
	 * 值
	 */
	@Expose
	private String value;
	
	
	/**
	 * 枚举值，只用于type=enum 时候格式为
	 * [{id:ID,name:name},{id:ID,name:name},]
	 */
	@Expose
	private ArrayList<EnumList> enumValues=new ArrayList<EnumList>();
	
	
	/**
	 * 日期格式，只用于type=date
	 */
	@Expose
	private String datePattern="yyyy-MM-dd hh:mm";
	
	
	/**
	 * 最小长度，只用于type=string
	 */
	@Expose
	private int minLength;
	
	/**
	 * 最大长度，只用于type=string
	 */
	@Expose
	private int maxLength;
	
	
	
	/**
	 * 显示名称
	 */
	@Expose
	private String name;
	

	/**
	 * 描述用途
	 */
	@Expose
	private String description;
	
	/**
	 * 运行检查规则，判断输入的值是否符合内部要求
	 * @return 成功返回true，失败抛异常
	 */
	public    boolean check() throws ConfigCheckException{
		return false;
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




	public String getDescription() {
		return description;
	}




	public void setDescription(String description) {
		this.description = description;
	}




	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isReadable() {
		return readable;
	}

	public void setReadable(boolean readable) {
		this.readable = readable;
	}

	public boolean isWritable() {
		return writable;
	}

	public void setWritable(boolean writable) {
		this.writable = writable;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	 

	public String getDatePattern() {
		return datePattern;
	}

	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}

	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	 


	public Property() {
		super();
	}




	public Property(String id, String type, boolean readable, boolean writable, boolean required, String value,
			ArrayList<EnumList> enumValues, String datePattern, int minLength, int maxLength, String name, String description) {
		super();
		this.id = id;
		this.type = type;
		this.readable = readable;
		this.writable = writable;
		this.required = required;
		this.value = value;
		this.enumValues = enumValues;
		this.datePattern = datePattern;
		this.minLength = minLength;
		this.maxLength = maxLength;
		this.name = name;
		this.description = description;
		if (this.enumValues==null){
			this.enumValues=new ArrayList<EnumList> ();
		}
	}




	public Property(Property p) {
		super();
		this.id = p.id;
		this.type = p.type;
		this.readable = p.readable;
		this.writable = p.writable;
		this.required = p.required;
		this.value = p.value;
		this.enumValues = p.enumValues;
		this.datePattern = p.datePattern;
		this.minLength = p.minLength;
		this.maxLength = p.maxLength;
		this.name = p.name;
		this.description = p.description;
		if (this.enumValues==null){
			this.enumValues=new ArrayList<EnumList> ();
		}
 	}




	public ArrayList<EnumList> getEnumValues() {
		return enumValues;
	}




	public void setEnumValues(ArrayList<EnumList> enumValues) {
		if (enumValues==null){
			this.enumValues=new ArrayList<EnumList> ();
		}else{
			this.enumValues = enumValues;

		}
	}

	
	
 
}


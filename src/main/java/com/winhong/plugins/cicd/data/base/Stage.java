package com.winhong.plugins.cicd.data.base;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.winhong.plugins.cicd.exception.ConfigCheckException;
import com.winhong.plugins.cicd.property.Property;

public    class  Stage {

	/**
	 * 内部ID，
	 */
	@Expose
	private String id;
	
	
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

	/**
	 * 显示名称，Jenkins stage name  
	 * 
	 */
	@Expose
	private String name;
	
	/**
	 * 描述
	 */
	@Expose
	private String description;


	public Stage(String id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	/**
	 * 属性列表
	 */
	@Expose
	private ArrayList<Property> elements= new ArrayList<Property>();
	
	public ArrayList<Property> getElements() {
		return elements;
	}

	public void setElements(ArrayList<Property> elements) {
		this.elements = elements;
	}

	public   boolean check() throws ConfigCheckException{
		return false;
	}
	
	/**
	 * 设置属性，如果有相同ID的属性则替换，否则增加
	 * @param p 属性
	 */
	public void setProperty(Property p){
		for (int i=0;i<elements.size();i++){
			Property temp = elements.get(i);
			if (temp.getId().equals(p.getId())){
				elements.set(i,p);
				return;
			}
			
		}
		elements.add(p);

		
	}
	
	
	/**
	 * 根据ID 返回property， 没有找到返回null
	 * @param id property ID
	 * @return property
	 */
	public Property getProperty(String id){
		for (int i=0;i<elements.size();i++){
			Property temp = elements.get(i);
			if (temp.getId().equals(id)){
 				return temp;
			}			
		}
		return null;
	}
	
	/**
	 *  设置某个属性的值，注意没有进行检查
	 * @param id 属性ID
	 * @param value 属性值
	 * @return 成功返回true，没有找对于属性，返回false
	 */
	public boolean setValue(String id,String value){
		
		for (int i=0;i<elements.size();i++){
			Property temp = elements.get(i);
			if (temp.getId().equals(id)){
				elements.get(i).setValue(value);
 				return true;
			}			
		}
		return false;
	}
	
	
	/**
	 * 返回属性值，没有找到属性返回null
	 * @param id 属性ID
	 * @return 属性值
	 */
	public String getValue(String id){
		
		for (int i=0;i<elements.size();i++){
			Property temp = elements.get(i);
			if (temp.getId().equals(id)){
  				return temp.getValue();
			}			
		}
		return null;
	}
	
	public String getJson(){
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

		return gson.toJson(this);
	}
	
}

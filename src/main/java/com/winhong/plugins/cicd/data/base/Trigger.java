package com.winhong.plugins.cicd.data.base;

import com.google.gson.annotations.Expose;

public class Trigger {
	
 	
	
	public final String typeId[]={"manual","commit","crontab","period"};
	
	private final String typeName[]={"手工触发","有提交就触发","crontab定时","间隔固定时间（分钟）"};
	
	/**
	 * 触发类型，默认手工触发
	 */
	@Expose		
	private String type=typeId[0];
	
	@Expose
	private String name;
	
	// <triggers><hudson.triggers.TimerTrigger><spec>H/1 * * * * </spec></hudson.triggers.TimerTrigger></triggers> 
	/**
	 * 附加值，可能是Jenkins 定期，或者一个间隔数字
	 */
	@Expose
	private String content;
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	 

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		for (int i=0;i<typeId.length;i++){
			if (typeId[i].equals(type)){
				return typeName[i];
			}
		}
		return  null;
	}

	/**
	 * 用于Json 反序列化,实际不用
	 * @param name 名称
	 */
	public void setName(String name) {
		
	}

	public Trigger() {
		super();
		type=typeId[0];
 	}

	public Trigger(String type,String content) {
		super();
		this.type = type;
		this.content=content;
	}

	
	private final static String triggerStr="<triggers><hudson.triggers.TimerTrigger><spec>##content</spec></hudson.triggers.TimerTrigger></triggers>";
	
    private final static String scmTriggerStr="<triggers>"
    		+ "<hudson.triggers.SCMTrigger>"
    		+ "<spec>* * * * * </spec>"
    		+ "<ignorePostCommitHooks>false</ignorePostCommitHooks>"
    		+ "</hudson.triggers.SCMTrigger></triggers>";
  
    
	/**
	 * 返回Trigger 类型，用于替换Jenkins 默认模板中的trigger信息
	 * @return
	 */
	public String getTrigger(){
		if (type.equals("period")){
			if (content.equals("1"))
				return triggerStr.replace("##content", "* * * * *");
			else
				return triggerStr.replace("##content", "H/"+this.content+" * * * *");
		}
		
		if (type.equals("crontab")){
			return triggerStr.replace("##content", this.content);
		}
		//每分钟检查一次变化,目前看在PIPELINE模式下，Jenkins并不支持
		//TODO 
		if (type.equals("commit")){
			return scmTriggerStr;
		}
		
		return "<triggers/>";
		//H/1 * * * *
		
		
	}
	
	
	

}

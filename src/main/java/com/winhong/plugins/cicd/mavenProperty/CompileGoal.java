package com.winhong.plugins.cicd.mavenProperty;

import com.winhong.plugins.cicd.data.base.Property;
import com.winhong.plugins.cicd.exception.ConfigCheckException;

public class CompileGoal extends Property{

	
	
	
	public CompileGoal(Property p) {
		super(p);
 	}


 
	
	public CompileGoal(){
		super(
				"compileGoal",
				"string",
				true,
				true,
				true,
				"-DskipTests=true clean  package" ,// default value
				null,
				"",
				5,
				100,
				"Maven Goal",
				"Maven 执行目标，默认为-DskipTests=true clean  package"
				);
	}
	
	public CompileGoal(String goal){
		super(
				"compileGoal",
				"string",
				true,
				true,
				true,
				goal,
				null,
				"",
				5,
				100,
				"Maven Goal",
				"Maven 执行目标，默认为-DskipTests=true clean  package"
				);
	}
	public void setGoal(String goal){
		this.setValue(goal);
	}
	
	public String getGoal(){
		return this.getValue();
	}


	@Override
	public boolean check() throws ConfigCheckException {
		String goal=this.getGoal();
		if (goal.length()< this.getMinLength() || goal.length() >this.getMaxLength()){
			throw new ConfigCheckException("Goal 长度不对！");
		}
		return true;
	}


 	

}

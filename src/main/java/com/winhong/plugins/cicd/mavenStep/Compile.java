package com.winhong.plugins.cicd.mavenStep;

import com.winhong.plugins.cicd.data.base.Stage;
import com.winhong.plugins.cicd.exception.ConfigCheckException;
import com.winhong.plugins.cicd.mavenProperty.CompileGoal;
 
public class Compile extends Stage {

	private final static String id="compile";
	private final static String name="编译";
	private final static String description="调用Maven进行编译";
	
	
	/**
	 * 编译目标
	 * @param goal
	 */
	public Compile(String goal) {
		super(id, name, description);
		CompileGoal g = new CompileGoal(goal);
		this.setGoal(g);
		
 	}
	
	

	/**
	 * 生产一个新的默认goal的complie对象
	 */
	public Compile() {
		super(id, name, description);
		CompileGoal g = new CompileGoal();
		this.setGoal(g);
		
 	}
	
	
	/**
	 * 设置complie 目标，默认为 -DskipTests=true clean  package
	 */
	//private CompileGoal goal;
	
	
	public CompileGoal getGoal() {
		return (CompileGoal) this.getElements().get(0);
	}

	
	public void setGoal(CompileGoal goal)  {
		this.getElements().add( goal);
	}


	@Override
	public boolean check() throws ConfigCheckException {
		this.getGoal().check();
		
 		return true;
	}

	
	
	
	

}

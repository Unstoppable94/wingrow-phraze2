package com.winhong.plugins.cicd.mavenStep;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
 import com.winhong.plugins.cicd.data.base.Stage;
import com.winhong.plugins.cicd.exception.ConfigCheckException;
import com.winhong.plugins.cicd.mavenProperty.CompileGoal;
import com.winhong.plugins.cicd.mavenProperty.JdkProperty;
import com.winhong.plugins.cicd.mavenProperty.MavenProperty;
  
public class Compile extends Stage {

	private final static String id="compile";
	private final static String name="Maven编译信息配置";
	private final static String description="调用Maven进行编译";
	
	
	
	
	CompileGoal goal = new CompileGoal();
	
	//InnerConfig.defaultConfig().getJdk()
	
	private JdkProperty jdk = new JdkProperty("jdk", "",
			"JDK版本", "选择编译时候使用的JDK版本️ ");
	private MavenProperty maven = new MavenProperty("maven", "",
			"Maven版本", "选择编译时候使用的Maven版本️ ");
	/**
	 * 生产一个新的默认goal的complie对象
	 */
	public Compile() {
		super(id, name, description);
		//CompileGoal g = new CompileGoal();
		//this.setGoal(g);
		
		this.setProperty(maven);
		this.setProperty(jdk);
		this.setProperty(goal);
	
		
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

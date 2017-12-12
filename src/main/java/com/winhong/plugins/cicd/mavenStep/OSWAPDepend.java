package com.winhong.plugins.cicd.mavenStep;

 
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
 import com.winhong.plugins.cicd.data.base.Stage;
import com.winhong.plugins.cicd.exception.ConfigCheckException;
import com.winhong.plugins.cicd.property.Skip;
 
 
public class OSWAPDepend extends Stage {

	private final static String id="OSWAPDepend";
	private final static String name="依赖库安全检查信息配置";
	private final static String description="调用oswap dependancy 对依赖库执行安全检查";
	
	
	
	
	private Skip skip=new Skip();
	
	 
	
	/**
	 * 生产一个新的对象
	 */
	public OSWAPDepend() {
		super(id, name, description);
		skip.setValue("true");
 		this.setProperty(skip);
	 
 	}
	
	
	 

	/* 使用json 恢复后都是property 类，必须进行实例转换并在调用检查类
	 * 
	 * @see com.winhong.plugins.cicd.data.base.Stage#check()
	 */
	@Override
	public boolean check() throws ConfigCheckException {
		skip=new Skip(this.getProperty(skip.getId()));
		return skip.check();
	}
	
 

}

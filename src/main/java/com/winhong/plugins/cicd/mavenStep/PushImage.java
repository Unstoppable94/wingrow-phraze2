package com.winhong.plugins.cicd.mavenStep;

 
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
 import com.winhong.plugins.cicd.data.base.Stage;
import com.winhong.plugins.cicd.exception.ConfigCheckException;
 import com.winhong.plugins.cicd.mavenProperty.Skip;
 
 
public class PushImage extends Stage {

	private final static String id="pushImage";
	private final static String name="保存Image";
	private final static String description="保存Image到仓库";
	
	
	
	
	private Skip skip=new Skip();
	
	 
	
	/**
	 * 生产一个新的对象
	 */
	public PushImage() {
		super(id, name, description);
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

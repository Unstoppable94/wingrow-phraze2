package com.winhong.plugins.cicd.dockerStep;

import com.winhong.plugins.cicd.data.base.Stage;
import com.winhong.plugins.cicd.exception.ConfigCheckException;
import com.winhong.plugins.cicd.mavenProperty.ContinueOnFail;
import com.winhong.plugins.cicd.mavenProperty.Url;
import com.winhong.plugins.cicd.property.Skip;
import com.winhong.plugins.cicd.property.commonString;
import com.winhong.plugins.cicd.property.commonTextArea;

public class Build extends Stage{

	private final static String id="dockerbuild";
	private final static String name="Build Image";
	private final static String description="调用指定Dockerfile进行打包";
	
	
	
	
	
 	
 	
	private commonString tagart=new commonString("tagart","",5, 200, "目标Image tag","生产的Docker image tag前缀,注意增加registery iP");
	
	private commonString dockerfile=new commonString("dockerfile","./dockerfile",2,100, "Dockerfile","默认为代码根目录下的Dockerfile" );

	/**
	 * 生产一个新的docker build对象
	 */
	public Build() {
		super(id, name, description);
//		sourceDirMap.setRequired(true);
		tagart.setRequired(true);
		this.setProperty(tagart);

		this.setProperty(dockerfile); 

 	}
	
	
	 

	/* 使用json 恢复后都是property 类，必须进行实例转换并在调用检查类
	 * 
	 * @see com.winhong.plugins.cicd.data.base.Stage#check()
	 */
	@Override
	public boolean check() throws ConfigCheckException {
			
		tagart=new commonString(this.getProperty("tagart"));
		if (!tagart.check()){
			return false;
		}
		return true;
	}
}

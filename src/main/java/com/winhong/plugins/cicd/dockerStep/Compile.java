package com.winhong.plugins.cicd.dockerStep;

import com.winhong.plugins.cicd.data.base.Stage;
import com.winhong.plugins.cicd.exception.ConfigCheckException;
import com.winhong.plugins.cicd.property.commonString;

public class Compile extends Stage{

	private final static String id="dockercompile";
	private final static String name="代码编译所用镜像信息配置";
	private final static String description="调用指定Docker image进行代码编译";
	//private final static String description=null;
	
	
	
	
	
 	
	final static private String srcMap="srcMap";
	
	private commonString sourceDirMap=new commonString(srcMap,"/src",5, 200, "代码在容器内Mapping目录","代码目录在编译容器内的mapping设置");
	
 
	private commonString dockeruser=new commonString("dockeruser","",2,50, "docker运行用户","编译docker运行时用户，不输入时候使用image默认用户" );
	
	private commonString dockerworkdir=new commonString("dockerworkdir","/src",2,50, "docker运行目录","编译docker运行时目录，不输入时候使用image默认目录" );
 
	
	private commonString dockercmd=new commonString("dockercmd","",2,50, "编译命令","编译命令，不输入使用image默认命令" );
	
	private commonString distDirMap=new commonString("distDirMap","",2,50, "输出目录mapping配置","输出结果目录配置，如果输出目录不在src目录下，请注意配置将，否则可能没有输出" );

	private commonString image=new commonString("image","",3,200, "编译使用的image","编译使用的image，建议带上版本信息" );

	/**
	 * 生产一个新的findbugs对象
	 */
	public Compile() {
		super(id, name, description);
//		sourceDirMap.setRequired(true);
		image.setRequired(true);
		this.setProperty(image);

		this.setProperty(sourceDirMap);
		this.setProperty(distDirMap);

		this.setProperty(dockeruser);
		this.setProperty(dockerworkdir);
		this.setProperty(dockercmd);

 	}
	
	
	 

	/* 使用json 恢复后都是property 类，必须进行实例转换并在调用检查类
	 * 
	 * @see com.winhong.plugins.cicd.data.base.Stage#check()
	 */
	@Override
	public boolean check() throws ConfigCheckException {
			
		image=new commonString(this.getProperty(image.getId()));
		if (!image.check()){
			return false;
		}
		return true;
	}
}

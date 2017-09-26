package com.winhong.plugins.cicd.mavenStep;

 
 
import com.winhong.plugins.cicd.data.base.Stage;
import com.winhong.plugins.cicd.exception.ConfigCheckException;
import com.winhong.plugins.cicd.mavenProperty.ContinueOnFail;
 
import com.winhong.plugins.cicd.property.Skip;
import com.winhong.plugins.cicd.property.commonString;
 
 
public class Findbugs extends Stage {

	private final static String id="findbugs";
	private final static String name="执行findbugs";
	private final static String description="调用findbugs进行代码检查";
	
	
	
	
	
	private ContinueOnFail continueOnfail=new ContinueOnFail();
	
	final static private String inFilterUrlId="inFilterUrl";
	
	private  commonString inFilterUrl=new commonString(inFilterUrlId,"", 0,255 ,"Include Filter","findbugs 规则文件,includefilter");
			//Url(inFilterUrlId,"Findbugs infilterUrl","findbugs 规则文件,includefilter");
	
	final static private String excludefilerUrlId="excludeFilterUrl";

	private  commonString excludeFilterUrl=new commonString(excludefilerUrlId,"", 0,255 ,"Exclude Filter","findbugs 错误忽略文件,excludefilter");
	private Skip skip=new Skip();
	
	//	public commonTextArea(String id,String value,int min,int max,String name,String description){
	//final private String  inFilterContentId="inFilterContent";
	
	//private commonTextArea inFilterContent=new commonTextArea(inFilterContentId, "", 0, 10240, "findbugs 规则", "配置infilter URL ，本项内容不起作用️",false);
	
//	final private String  excludeFilterContentId="excludeFilterContent";
	
	//private commonTextArea excludeFilterContent=new commonTextArea(excludeFilterContentId, "", 
	//		0, 10240, "findbugs 忽略错误内容", "配置infilter URL ，本项内容不起作用️",false);

	
	/**
	 * 生产一个新的findbugs对象
	 */
	public Findbugs() {
		super(id, name, description);
 		this.setProperty(continueOnfail);
		this.setProperty(skip);
		this.setProperty(inFilterUrl);
		this.setProperty(excludeFilterUrl);
 	}
	
	
	 

	/* 使用json 恢复后都是property 类，必须进行实例转换并在调用检查类
	 * 
	 * @see com.winhong.plugins.cicd.data.base.Stage#check()
	 */
	@Override
	public boolean check() throws ConfigCheckException {

				
		
		return true;
	}
	
	 

}

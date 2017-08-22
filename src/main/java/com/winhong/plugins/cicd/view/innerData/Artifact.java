package com.winhong.plugins.cicd.view.innerData;

import com.google.gson.annotations.Expose;

/**
 * Jenkins Artifact 即Jenkins 的结果，通过zip 打包target生成
 * @author xiehuiqiang
 *
 */
public class Artifact {

	
	@Expose
	private String displayPath;
	
	@Expose
	private String fileName;
	
	
	//
	/**
	 * 使用job 模式的
	 * 下载路径，只有http://10.211.55.6:8080/job/maskio/144/artifact/jenkins-maskio-144.zip
	 * 中的jenkins-maskio-144.zip 部分，需要根据project及build number 结合生成
	 * 
	 * 使用wf 可以看到全部路径
	 * /job/maskio/144/artifact/jenkins-maskio-144.zip
	 */
	@Expose
	private String relativePath;
	
	public Artifact() {
 	}

	public String getDisplayPath() {
		return displayPath;
	}

	public void setDisplayPath(String displayPath) {
		this.displayPath = displayPath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	public Artifact(String displayPath, String fileName, String relativePath) {
		super();
		this.displayPath = displayPath;
		this.fileName = fileName;
		this.relativePath = relativePath;
	}
	

}

package com.winhong.plugins.cicd.exception;

/**
 * @author xiehuiqiang
 * 检查失败异常
 */
public class ConfigCheckException extends Exception{

	 

	/**
	 * 唯一ID
	 */
	private static final long serialVersionUID = 5492204420088812150L;

	public ConfigCheckException() {
		super();
 	}

	public ConfigCheckException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
 	}

	public ConfigCheckException(String message, Throwable cause) {
		super(message, cause);
 	}

	public ConfigCheckException(String message) {
		super(message);
 	}

	public ConfigCheckException(Throwable cause) {
		super(cause);
 	}

}

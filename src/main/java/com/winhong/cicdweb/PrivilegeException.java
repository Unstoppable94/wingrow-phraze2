package com.winhong.cicdweb;

import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class PrivilegeException extends ServletException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PrivilegeException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PrivilegeException(String message, Throwable rootCause) {
		super(message, rootCause);
		// TODO Auto-generated constructor stub
	}

	public PrivilegeException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public PrivilegeException(Throwable rootCause) {
		super(rootCause);
		// TODO Auto-generated constructor stub
	}

 
	
//	public PrivilegeException(HttpServletRequest req,String message) {
//		
//		 
//		// TODO Auto-generated constructor stub
//	}

}

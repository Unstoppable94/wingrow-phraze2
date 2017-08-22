package com.winhong.plugins.cicd.openldap;

public class ClientVisibleException extends RuntimeException {

	 
	public ClientVisibleException(String string, String string2) {
		 super(string+":"+string2);
		 }
}

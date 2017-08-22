package com.winhong.plugins.cicd.openldap;

import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;

import com.google.gson.annotations.Expose;

//@Type(name = OpenLDAPConstants.CONFIG)
public class OpenLDAPConfig {

 
	@Expose
	String SECURITY_PRINCIPAL;
	
	@Expose
	String SECURITY_CREDENTIALS;
	
	final static String INITIAL_CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";

	@Expose
	String PROVIDER_URL;
	
	@Expose
	String SECURITY_AUTHENTICATION="simple";
	
	@Expose
	String SECURITY_PROTOCOL;

	@Expose
	String SearchBase;
	
	@Expose
	String Connect_Timeout="200";

	public Hashtable<String, String> getProperties() {
		Hashtable<String, String> props = new Hashtable<>();
		props.put(Context.SECURITY_AUTHENTICATION, SECURITY_AUTHENTICATION);

		props.put(Context.SECURITY_PRINCIPAL, SECURITY_PRINCIPAL);
		props.put(Context.SECURITY_CREDENTIALS, SECURITY_CREDENTIALS);
		props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		props.put("com.sun.jndi.ldap.connect.timeout", Connect_Timeout);
		// String url = "ldap://" + ldapConfig.getServer() + ':' +
		// String.valueOf(ldapConfig.getPort()) + '/';
		props.put(Context.PROVIDER_URL, PROVIDER_URL);
		if (SECURITY_PROTOCOL.equalsIgnoreCase("ssl"))
			props.put(Context.SECURITY_PROTOCOL, SECURITY_PROTOCOL);
		return props;
	}

	
	
	
	public OpenLDAPConfig(String sECURITY_PRINCIPAL, String sECURITY_CREDENTIALS, String pROVIDER_URL,
			String sECURITY_AUTHENTICATION, String sECURITY_PROTOCOL, String searchBase, String connect_Timeout) {
		super();
		SECURITY_PRINCIPAL = sECURITY_PRINCIPAL;
		SECURITY_CREDENTIALS = sECURITY_CREDENTIALS;
		PROVIDER_URL = pROVIDER_URL;
		SECURITY_AUTHENTICATION = sECURITY_AUTHENTICATION;
		SECURITY_PROTOCOL = sECURITY_PROTOCOL;
		SearchBase = searchBase;
		Connect_Timeout = connect_Timeout;
	}




	public String getSECURITY_AUTHENTICATION() {
		return SECURITY_AUTHENTICATION;
	}

	public void setSECURITY_AUTHENTICATION(String sECURITY_AUTHENTICATION) {
		SECURITY_AUTHENTICATION = sECURITY_AUTHENTICATION;
	}

	public String getSECURITY_PRINCIPAL() {
		return SECURITY_PRINCIPAL;
	}

	public void setSECURITY_PRINCIPAL(String sECURITY_PRINCIPAL) {
		SECURITY_PRINCIPAL = sECURITY_PRINCIPAL;
	}

	public String getSECURITY_CREDENTIALS() {
		return SECURITY_CREDENTIALS;
	}

	public void setSECURITY_CREDENTIALS(String sECURITY_CREDENTIALS) {
		SECURITY_CREDENTIALS = sECURITY_CREDENTIALS;
	}

	public static String getINITIAL_CONTEXT_FACTORY() {
		return INITIAL_CONTEXT_FACTORY;
	}

	 

	public String getPROVIDER_URL() {
		return PROVIDER_URL;
	}

	public void setPROVIDER_URL(String pROVIDER_URL) {
		PROVIDER_URL = pROVIDER_URL;
	}

	public String getSECURITY_PROTOCOL() {
		return SECURITY_PROTOCOL;
	}

	public void setSECURITY_PROTOCOL(String sECURITY_PROTOCOL) {
		SECURITY_PROTOCOL = sECURITY_PROTOCOL;
	}

	public String getSearchBase() {
		return SearchBase;
	}

	public void setSearchBase(String searchBase) {
		SearchBase = searchBase;
	}

	public String getDomain() {
		 
		return "api.auth.ldap.openldap.domain";
	}
}

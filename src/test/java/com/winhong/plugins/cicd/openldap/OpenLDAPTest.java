package com.winhong.plugins.cicd.openldap;

import static org.junit.Assert.*;

import java.util.Hashtable;

import javax.naming.Context;

import org.junit.Test;

public class OpenLDAPTest {

	@Test
	public void testValidateConfigNormal() {
		
		String ldapAdServer = "ldap://192.168.101.114:389";
		String ldapSearchBase = "ou=winhong,DC=wingarden,DC=com";

		String ldapUsername = "cn=administrator,cn=users,dc=wingarden,dc=com"; // SECURITY_PRINCIPAL
		String ldapPassword = "P@ssw0rd"; // ldapPassword

		OpenLDAPConfig ldapConfig = new OpenLDAPConfig(ldapUsername, ldapPassword, ldapAdServer, "simple", "",
				ldapSearchBase, "100");
		try {
			OpenLDAP.Login(ldapConfig, "caiwl", "root@123");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}

	}

	
	@Test
	public void testValidateConfigNormal2() {
		
		String ldapAdServer = "ldap://192.168.101.114:389";
		String ldapSearchBase = "cn=winhong,DC=wingarden,DC=com";

		String ldapUsername = "cn=xiehq,cn=winhong,dc=wingarden,dc=com"; // SECURITY_PRINCIPAL
		String ldapPassword = "root@123"; // ldapPassword

		OpenLDAPConfig ldapConfig = new OpenLDAPConfig(ldapUsername, ldapPassword, ldapAdServer, "simple", "",
				ldapSearchBase, "100");
		try {
			OpenLDAP.Login(ldapConfig, "xiehq", "root@123");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}

	}

	
	@Test
	public void testValidateConfigErrorPort() {

		String ldapAdServer = "ldap://10.10.111.136:382";
		String ldapSearchBase = "cn=users,DC=wingarden,DC=com";

		String ldapUsername = "cn=Administrator,cn=users,dc=wingarden,dc=com"; // SECURITY_PRINCIPAL
		String ldapPassword = "P@ssw0rd"; // ldapPassword

		OpenLDAPConfig ldapConfig = new OpenLDAPConfig(ldapUsername, ldapPassword, ldapAdServer, "simple", "",
				ldapSearchBase, "100");
		try {
			OpenLDAP.Login(ldapConfig, "testuser", "root@123");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
 		}
		fail();
	}
	
	
	@Test
	public void testValidateConfigErropass1() {

		String ldapAdServer = "ldap://10.10.111.136:389";
		String ldapSearchBase = "cn=users,DC=wingarden,DC=com";

		String ldapUsername = "cn=Administrator,cn=users,dc=wingarden,dc=com"; // SECURITY_PRINCIPAL
		String ldapPassword = "P@2"; // ldapPassword

		OpenLDAPConfig ldapConfig = new OpenLDAPConfig(ldapUsername, ldapPassword, ldapAdServer, "simple", "",
				ldapSearchBase, "100");
		try {
			OpenLDAP.Login(ldapConfig, "testuser", "root@123");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
 		}
		fail();

	}
	
	

	@Test
	public void testValidateConfigErropass2() {

		String ldapAdServer = "ldap://10.10.111.136:389";
		String ldapSearchBase = "cn=users,DC=wingarden,DC=com";

		String ldapUsername = "cn=Administrator,cn=users,dc=wingarden,dc=com"; // SECURITY_PRINCIPAL
		String ldapPassword = "P@ssw0rd"; // ldapPassword

		OpenLDAPConfig ldapConfig = new OpenLDAPConfig(ldapUsername, ldapPassword, ldapAdServer, "simple", "",
				ldapSearchBase, "100");
		try {
			OpenLDAP.Login(ldapConfig, "testuser", "root@1223");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
 		}
		fail();

	}
}

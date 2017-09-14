package com.winhong.plugins.cicd.openldap;

import java.net.ConnectException;
import java.util.Hashtable;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.LdapName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenLDAP {

	private static final Logger log = LoggerFactory.getLogger(OpenLDAP.class);

	public static void Login(OpenLDAPConfig ldapConfig, String username, String password) throws Exception {

		LdapContext userContext = null;
		Hashtable<String, String> props = ldapConfig.getProperties();
		try {

			userContext = new InitialLdapContext(props, null);
			try {
				authentication(userContext, ldapConfig.getSearchBase(), ldapConfig.getPROVIDER_URL(), username,
						password);
				// userContext.getAttributes(new LdapName(ldapConfig.getDomain()));
			} catch (NamingException e) {
				throw new UserLoginFailureException("Failed to login ldap User: " + errorCodeToDescription(e), e,
						username);
			}
		} catch (NamingException e) {
			if (e.getRootCause() instanceof ConnectException) {
				throw new ClientVisibleException("Unable to talk to ldap.",
						"Provided server and port refused connection.");
			}
			throw new ClientVisibleException("Unable to authenticate service account.",
					"Unable to create context with service account credentials." + "Username(DN):"
							+ ldapConfig.SECURITY_PRINCIPAL);
		} finally {
			if (userContext != null) {
				try {
					userContext.close();
				} catch (NamingException e) {
					log.info("Failed to close Test service context.", e);
				}
			}
		}
	}

	public static LdapContext authentication(DirContext ctx, String ldapSearchBase, String ldapAdServer,
			String username, String password) throws NamingException, Exception {
		SearchResult srLdapUser = findAccountByAccountName(ctx, ldapSearchBase, username);
		if (srLdapUser == null)
			throw new ClientVisibleException("Unable to authenticate service account.", "no such user");
		String user = srLdapUser.getNameInNamespace();
		// user="test";
		// getFullName(srLdapUser);
		LdapContext context;

		Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		props.put(Context.PROVIDER_URL, ldapAdServer);
		props.put(Context.SECURITY_PRINCIPAL, user);
		props.put(Context.SECURITY_CREDENTIALS, password);

		context = new InitialLdapContext(props, null);

		return context;

	}

	public static SearchResult findAccountByAccountName(DirContext ctx, String ldapSearchBase, String accountName)
			throws NamingException {

		String searchFilter = "(&(objectClass=user)(sAMAccountName=" + accountName + "))";

		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

		NamingEnumeration<SearchResult> results = ctx.search(ldapSearchBase, searchFilter, searchControls);

		SearchResult searchResult = null;
		if (results.hasMoreElements()) {
			searchResult = (SearchResult) results.nextElement();

			// make sure there is not another item available, there should be
			// only 1 match
			if (results.hasMoreElements()) {
				System.err.println("Matched multiple users for the accountName: " + accountName);
				return null;
			}
		}

		return searchResult;
	}

	private static final Pattern LDAP_ERROR_CODE = Pattern.compile("data +[57][2307][01235e]");

	public static String errorCodeToDescription(NamingException code) {
		String errorCode = code.getExplanation();
		Matcher m = LDAP_ERROR_CODE.matcher(errorCode);
		if (m.find()) {
			errorCode = m.group(0).substring(m.group(0).length() - 3);
		}
		switch (errorCode) {
		case "525":
			return "User not found";
		case "52e":
			return "Invalid credentials";
		case "530":
			return "Not permitted to logon at this time";
		case "531":
			return "Not permitted to logon at this workstation";
		case "532":
			return "Password expired (remember to check the user set in osuser.xml also)";
		case "533":
			return "Account disabled";
		case "701":
			return "Account expired";
		case "773":
			return "User must reset password";
		case "775":
			return "User account locked";
		default:
			return errorCode;
		}
	}

}

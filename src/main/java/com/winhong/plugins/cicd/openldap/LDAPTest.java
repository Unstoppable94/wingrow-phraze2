package com.winhong.plugins.cicd.openldap;

import java.util.Hashtable;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

/**
 * Example code for retrieving a Users Primary Group from Microsoft Active
 * Directory via. its LDAP API
 * 
 * @author Adam Retter <adam.retter@googlemail.com>
 */
public class LDAPTest {

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) throws NamingException {

		final String ldapAdServer = "ldap://10.10.111.136:389";
		final String ldapSearchBase = "cn=users,DC=wingarden,DC=com";

		final String ldapUsername = "cn=Administrator,cn=users,dc=wingarden,dc=com";
		final String ldapPassword = "P@ssw0rd";

		final String ldapAccountToLookup = "testuser";

		Hashtable<String, Object> env = new Hashtable<String, Object>();
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		if (ldapUsername != null) {
			env.put(Context.SECURITY_PRINCIPAL, ldapUsername);
		}
		if (ldapPassword != null) {
			env.put(Context.SECURITY_CREDENTIALS, ldapPassword);
		}
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, ldapAdServer);
		// env.put(Context.INITIAL_CONTEXT_FACTORY,
		// "com.sun.jndi.ldap.LdapCtxFactory");
		// ensures that objectSID attribute values
		// will be returned as a byte[] instead of a String
		env.put("java.naming.ldap.attributes.binary", "objectSID");

		// the following is helpful in debugging errors
		//env.put("com.sun.jndi.ldap.trace.ber", System.err);

		// DirContext ctx = new InitialDirContext(env);
		//InitialDirContext ctx = new InitialDirContext(env);
		LdapContext ctx = new InitialLdapContext(env,null);
		// LdapContext ctx = new InitialLdapContext();
		authentication(ctx, ldapSearchBase, ldapAdServer, "testuser", "root@123");

		LDAPTest ldap = new LDAPTest();

		// 1) lookup the ldap account
		SearchResult srLdapUser = ldap.findAccountByAccountName(ctx, ldapSearchBase, ldapAccountToLookup);

		// 2) get the SID of the users primary group
		String primaryGroupSID = ldap.getPrimaryGroupSID(srLdapUser);

		// 3) get the users Primary Group
		String primaryGroupName = ldap.findGroupBySID(ctx, ldapSearchBase, primaryGroupSID);
		System.out.println("primaryGroupSID:" + primaryGroupName);
		System.out.println("primaryGroupName:" + primaryGroupName);

	}

	public static boolean authentication(DirContext ctx, String ldapSearchBase, String ldapAdServer, String username,
			String password) throws NamingException {
		SearchResult srLdapUser = findAccountByAccountName(ctx, ldapSearchBase, username);

		String user = srLdapUser.getNameInNamespace();
		//user="test";
		// getFullName(srLdapUser);

		try {
			Properties props = new Properties();
			props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			props.put(Context.PROVIDER_URL, ldapAdServer);
			props.put(Context.SECURITY_PRINCIPAL, user);
			props.put(Context.SECURITY_CREDENTIALS, password);

			LdapContext context = new InitialLdapContext(props,null);
		} catch (NamingException e) {
            throw new UserLoginFailureException("Failed to login ldap User: " 
            		+  errorCodeToDescription(e), e, username);
        }catch (Exception e) {
			System.out.println("===log fail:" + e.getMessage());

			return false;
		}
		System.out.println("====log sucessed:" );

		return true;

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

	
	public static String errorCodeToDescription(NamingException code){
        String errorCode = code.getExplanation();
        Matcher m = LDAP_ERROR_CODE.matcher(errorCode);
        if (m.find()) {
            errorCode = m.group(0).substring(m.group(0).length()-3);
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
	

	public String findGroupBySID(DirContext ctx, String ldapSearchBase, String sid) throws NamingException {

		String searchFilter = "(&(objectClass=group)(objectSid=" + sid + "))";

		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

		NamingEnumeration<SearchResult> results = ctx.search(ldapSearchBase, searchFilter, searchControls);

		if (results.hasMoreElements()) {
			SearchResult searchResult = (SearchResult) results.nextElement();

			// make sure there is not another item available, there should be
			// only 1 match
			if (results.hasMoreElements()) {
				System.err.println("Matched multiple groups for the group with SID: " + sid);
				return null;
			} else {
				return (String) searchResult.getAttributes().get("sAMAccountName").get();
			}
		}
		return null;
	}

	public static String getFullName(SearchResult srLdapUser) throws NamingException {
		System.err.println(srLdapUser.getNameInNamespace());

		NamingEnumeration<String> ids = srLdapUser.getAttributes().getIDs();
		while (ids.hasMore()) {
			System.err.println("id=" + ids.next());

		}

		byte[] objectSID = (byte[]) srLdapUser.getAttributes().get("fullName").get();
		String strPrimaryGroupID = (String) srLdapUser.getAttributes().get("fullName").get();

		String strObjectSid = decodeSID(objectSID);

		return strObjectSid.substring(0, strObjectSid.lastIndexOf('-') + 1) + strPrimaryGroupID;
	}

	public String getPrimaryGroupSID(SearchResult srLdapUser) throws NamingException {

		byte[] objectSID = (byte[]) srLdapUser.getAttributes().get("objectSid").get();
		String strPrimaryGroupID = (String) srLdapUser.getAttributes().get("primaryGroupID").get();

		String strObjectSid = decodeSID(objectSID);

		return strObjectSid.substring(0, strObjectSid.lastIndexOf('-') + 1) + strPrimaryGroupID;
	}

	/**
	 * The binary data is in the form: byte[0] - revision level byte[1] - count
	 * of sub-authorities byte[2-7] - 48 bit authority (big-endian) and then
	 * count x 32 bit sub authorities (little-endian)
	 * 
	 * The String value is: S-Revision-Authority-SubAuthority[n]...
	 * 
	 * Based on code from here -
	 * http://forums.oracle.com/forums/thread.jspa?threadID=1155740&tstart=0
	 */
	public static String decodeSID(byte[] sid) {

		final StringBuilder strSid = new StringBuilder("S-");

		// get version
		final int revision = sid[0];
		strSid.append(Integer.toString(revision));

		// next byte is the count of sub-authorities
		final int countSubAuths = sid[1] & 0xFF;

		// get the authority
		long authority = 0;
		// String rid = "";
		for (int i = 2; i <= 7; i++) {
			authority |= ((long) sid[i]) << (8 * (5 - (i - 2)));
		}
		strSid.append("-");
		strSid.append(Long.toHexString(authority));

		// iterate all the sub-auths
		int offset = 8;
		int size = 4; // 4 bytes for each sub auth
		for (int j = 0; j < countSubAuths; j++) {
			long subAuthority = 0;
			for (int k = 0; k < size; k++) {
				subAuthority |= (long) (sid[offset + k] & 0xFF) << (8 * k);
			}

			strSid.append("-");
			strSid.append(subAuthority);

			offset += size;
		}

		return strSid.toString();
	}
}
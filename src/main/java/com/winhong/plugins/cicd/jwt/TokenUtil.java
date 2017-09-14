package com.winhong.plugins.cicd.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import org.apache.commons.lang.StringUtils;

import java.security.Key;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.ws.rs.core.Context;

public class TokenUtil {

	/**
	 * the key only can used in one machine ,not support cluster
	 */
	@Context
    private static  Key defaultkey=MacProvider.generateKey();
	
	//default expriy time in minutes
	
	private static int defaultExpiryMins=15;
	

	private static int defaultVersion=1;
	
	
	private static HashMap<String,Date> LoginedToken=new  HashMap<String,Date>();

	
	
	public static String getJWTString(String username, String[] roles ) {
		return getJWTString(  username,  roles,   defaultVersion,   getExpiryDate(defaultExpiryMins),   defaultkey);
		
	}
	
	public static String getJWTString(String username, String[] roles, int version) {
		return getJWTString(  username,  roles,   version,   getExpiryDate(defaultExpiryMins),   defaultkey);
		
	}
	
	public static String getJWTString(String username, String[] roles, int version, int expiresMinutes) {
		return getJWTString(  username,  roles,   version,    getExpiryDate(expiresMinutes),   defaultkey);
		
	}
	
	public static String getJWTString(String username, String[] roles, int version, Date expires) {
		return getJWTString(  username,  roles,   version,   expires,   defaultkey);
		
	}
    public static String getJWTString(String username, String[] roles, int version, Date expires, Key key) {
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token
        if (username == null) {
            throw new NullPointerException("null username is illegal");
        }
        if (roles == null) {
            throw new NullPointerException("null roles are illegal");
        }
        if (expires == null) {
            throw new NullPointerException("null expires is illegal");
        }
        if (key == null) {
            throw new NullPointerException("null key is illegal");
        }

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        String jwtString = Jwts
                .builder()
                .setIssuer("Jersey-Security-Basic")
                .setSubject(username)
                .setAudience(StringUtils.join(Arrays.asList(roles), ","))
                .setExpiration(expires)
                .setIssuedAt(new Date())
                .setId(String.valueOf(version))
                .signWith(signatureAlgorithm, key)
                .compact();
        LoginedToken.put(jwtString, new Date());
        return jwtString;
    }
    
    public static boolean removeToken(String token) {
    		if (LoginedToken.remove(token)!=null)
    			return true;
    		return false;
    }

    
    public static boolean isValid(String token) {
    		return isValid(  token,defaultkey);
    	
    }
    
    public static boolean isValid(String token, Key key) {
        try {
        		if (!LoginedToken.containsKey(token))
        			return false;
            Jwts.parser().setSigningKey(key).parseClaimsJws(token.trim());
            return true;
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    public static String getName(String jwsToken ) {
    		return getName( jwsToken,defaultkey );
    }
    
    public static String getName(String jwsToken, Key key) {
        if (isValid(jwsToken, key)) {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(jwsToken);
            return claimsJws.getBody().getSubject();
        }
        return null;
    }

    public static String[] getRoles(String jwsToken) {
    		return getRoles(jwsToken,defaultkey);
    }
    
    public static String[] getRoles(String jwsToken, Key key) {
        if (isValid(jwsToken, key)) {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(jwsToken);
            return claimsJws.getBody().getAudience().split(",");
        }
        return new String[]{};
    }
    
    public static int getVersion(String jwsToken) {
    		return getVersion( jwsToken,defaultkey);
    }
    
    public static int getVersion(String jwsToken, Key key) {
        if (isValid(jwsToken, key)) {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(jwsToken);
            return Integer.parseInt(claimsJws.getBody().getId());
        }
        return -1;
    }

    public static Date getExpiryDate(int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

	public static Date getExpiryDate() {
		
		return getExpiryDate(defaultExpiryMins);
	}
	
	

	public static int getDefaultExpiryMins() {
		return defaultExpiryMins;
	}

	public static void setDefaultExpiryMins(int defaultExpiryMins) {
		TokenUtil.defaultExpiryMins = defaultExpiryMins;
	}
}
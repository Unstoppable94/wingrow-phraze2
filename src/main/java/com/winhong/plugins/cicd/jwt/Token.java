package com.winhong.plugins.cicd.jwt;

 import com.google.gson.annotations.Expose;

import java.io.Serializable;
 
public class Token implements Serializable {
    private static final long serialVersionUID = -186954891348069462L;
    @Expose
    private String authorization;
    @Expose
    private long expires;
    
    @Expose
    boolean mustChangePassword;

    public Token() { // for some reason the jackson engine needs a zero arg constructor.
    }

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

	public long getExpires() {
		return expires;
	}

	public void setExpires(long expires) {
		this.expires = expires;
	}

	public boolean isMustChangePassword() {
		return mustChangePassword;
	}

	public void setMustChangePassword(boolean mustChangePassword) {
		this.mustChangePassword = mustChangePassword;
	}

    
}

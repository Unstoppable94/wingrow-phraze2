package com.winhong.plugins.cicd.jwt;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Date;

public class Token implements Serializable {
    private static final long serialVersionUID = -186954891348069462L;
    @Expose
    private String authorization;
    @Expose
    private long expires;

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

    
}

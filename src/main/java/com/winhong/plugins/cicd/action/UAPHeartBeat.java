package com.winhong.plugins.cicd.action;

import com.winhong.plugins.cicd.uapconfig.UapUtils;

public class UAPHeartBeat {
	public static void checkHeartBeat(){
		UapUtils.getRestClientV1().systems().heartbeat("102");
	}
}

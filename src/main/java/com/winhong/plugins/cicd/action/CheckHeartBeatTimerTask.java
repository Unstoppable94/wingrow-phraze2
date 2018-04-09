package com.winhong.plugins.cicd.action;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winhong.plugins.cicd.uapconfig.UapUtils;

public class CheckHeartBeatTimerTask extends TimerTask{
	private static final Logger log = LoggerFactory.getLogger(CheckHeartBeatTimerTask.class);
	@Override
	public void run() {
		UapUtils.getRestClientV1().systems().heartbeat("102");
	}
}

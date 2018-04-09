package com.winhong.plugins.cicd.action;

import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckHeartBeatTimer {
	
	private static final Logger log = LoggerFactory.getLogger(CheckHeartBeatTimer.class);
	Timer timer ;
	
	public CheckHeartBeatTimer(int time) {
		// TODO Auto-generated constructor stub
		timer = new Timer();
		timer.schedule(new CheckHeartBeatTimerTask(), time * 1000, time * 1000);
		log.debug("Heart check beat" + time);
	}
}

package com.winhong.plugins.cicd.action;

import java.util.Timer;

public class CheckHeartBeatTimer {
	Timer timer ;
	
	public CheckHeartBeatTimer(int time) {
		// TODO Auto-generated constructor stub
		timer = new Timer();
		timer.schedule(new CheckHeartBeatTimerTask(), time * 1000, time * 1000);
	}
}

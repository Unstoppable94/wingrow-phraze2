package com.winhong.plugins.cicd.action;

import java.util.Timer;

public class SendEmailTimer {
	Timer timer ;
	
	public SendEmailTimer(int time) {
		// TODO Auto-generated constructor stub
		timer = new Timer();
		timer.schedule(new SendEmailTimerTask(), time * 1000, time * 1000);
	}
}

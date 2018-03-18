package com.winhong.jetty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winhong.plugins.cicd.action.NotifyAction;
import com.winhong.plugins.cicd.action.StatisticsAction;

public class SendEmailThread extends Thread {
	public static Logger log = LoggerFactory.getLogger(SendEmailThread.class);
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {
			while(true){
				NotifyAction.checkProjectStatus();
				log.debug("send email---------");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

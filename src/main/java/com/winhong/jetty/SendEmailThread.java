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
		try {
			while(true){
				try {
					NotifyAction.checkProjectStatus();
				} catch (Exception e) {
					// TODO: handle exception
					log.debug("please config Email:" +e);
				}
				
				//
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

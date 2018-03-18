package com.winhong.plugins.cicd.action;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winhong.plugins.cicd.tool.Tools;

public class SendEmailTimerTask extends TimerTask{
	
	private static final Logger log = LoggerFactory.getLogger(SendEmailTimerTask.class);
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(true){
				NotifyAction.checkProjectStatus();
				log.debug("send email -----");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

package com.winhong.plugins.cicd.action;

import static org.junit.Assert.*;

import org.junit.Test;

import com.winhong.plugins.cicd.system.SMTPConfig;

public class NotifyActionTest {

	@Test
	public void testSendSmtpEmail() {
		try {
			//NotifyAction.checkProjectStatus();
		} catch (Exception e) {
			 
			e.printStackTrace();
			fail();
		}
		}
	//邮箱TEST
	@Test
	public void testSendSmtpEmail_() throws Exception {
		SMTPConfig smtpConfig = new SMTPConfig();
		smtpConfig.setHost("smtp.winhong.com");
		smtpConfig.setPort(25);
		smtpConfig.setUser("lizheng@winhong.com");
		smtpConfig.setPassword("");
		String to = "linlb@winhong.com";
		String subject = "test mail";
		String content = "test mail";
		NotifyAction.SendSmtpEmail(smtpConfig, to, subject, content);
	}

}

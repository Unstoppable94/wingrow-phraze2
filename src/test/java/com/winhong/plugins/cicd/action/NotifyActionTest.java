package com.winhong.plugins.cicd.action;

import static org.junit.Assert.*;

import org.junit.Test;

public class NotifyActionTest {

	@Test
	public void testSendSmtpEmail() {
		try {
			NotifyAction.checkProjectStatus();
		} catch (Exception e) {
			 
			e.printStackTrace();
			fail();
		}
		}

}

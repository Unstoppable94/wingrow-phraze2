package com.winhong.plugins.cicd.action;

import static org.junit.Assert.fail;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.winhong.plugins.cicd.Maven.MavenProject;
import com.winhong.plugins.cicd.Maven.MavenProjectBaseInfo;
import com.winhong.plugins.cicd.data.base.ProjectGroupJsonConfig;

public class genTestData {
	static MavenProject p=new MavenProject();

static ProjectGroupJsonConfig group=new ProjectGroupJsonConfig();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		group.setId("default");
		group.setName("default");
		group.setDescription("defaultGroup");
	 
 		MavenProjectBaseInfo b=new MavenProjectBaseInfo();
		b.setCreateTime(System.currentTimeMillis());
		b.setDescription("中文project111");
		b.setExraProperties("");
		b.setGroupId("test");
		b.setId("中文");
		b.setName("中文project111");
		b.setJdk("1.7");
		b.setMavenId("3.3.9");
		b.setLastModifyTime(b.getCreateTime());
		b.setMailOnfail("mailoffail@w.com");
		b.setMailOnReovery(null);
		b.setMailOnSuccess(null);
		b.setMaxExcutiontime(60);
 		b.setSCMBranch("master");
		b.setSCMPassword("sCMPassword");
		b.setSCMTYPE("git");
		b.setSCMUrl("sCMUrl");
		b.setSCMUser("sCMUser");
		b.setTrigger("crontab");
		b.setTriggerProperty("* * * * *");
		p.setBaseInfo(b);
		}

	 
	@Test
	public void gentestData() {
		
		try {
			GroupAction.createGroup(group);
			p.getBaseInfo().setGroupId("default");
			ProjectAction.AddMavenProject(p);
			p.getBaseInfo().setId("test2");
			p.getBaseInfo().setName("test2");
			ProjectAction.AddMavenProject(p);
			ProjectAction.triggerBuild("test2");
			 
		} catch (Exception e) {
 			e.printStackTrace();
			fail();
		}
		
 	}
}

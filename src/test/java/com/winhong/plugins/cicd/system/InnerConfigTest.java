package com.winhong.plugins.cicd.system;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.winhong.plugins.cicd.tool.Tools;

public class InnerConfigTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDefaultConfig() throws IOException {
		try {
			InnerConfig t = InnerConfig.defaultConfig();
			EmailTemplate e = new EmailTemplate();
			e.setTitle("$PROJECTNAME 第$NUMBER 构建失败 ");
			e.setContent("$PROJECTNAME 第$NUMBER 构建失败,请尽快处理");
			t.setFailedTemplate(e);

			EmailTemplate e2 = new EmailTemplate();
			e2.setTitle("$PROJECTNAME 第$NUMBER 构建成功 ");
			e2.setContent("$PROJECTNAME 第$NUMBER 构建成功");
			t.setSuccessTemplate(e2);

			EmailTemplate e3 = new EmailTemplate();
			e3.setTitle("$PROJECTNAME 第$NUMBER 构建恢复成功 ");
			e3.setContent("$PROJECTNAME 第$NUMBER 构建成功");
			t.setRecoveryTemplate(e3);

			System.out.println(Tools.getJson(t));

		} catch (FileNotFoundException e) {

			e.printStackTrace();
			fail("get default");
		}

	}

	@Test
	public void testGetJson() {
		InnerConfig t = new InnerConfig();
		JenkinsConfig j = new JenkinsConfig();
		j.setUrl("http://10.211.55.6:8080");
		j.setUser("xiehq");
		j.setPassword("acd12345");
		GitLabConfig f = new GitLabConfig();
		f.setUrl("10.0.2.50:180");
		f.setUser("xiehq");
		f.setPassword("acd12345");
		t.setGitlab(f);
		// t.setJenkins(j);

		t.setJenkinsDir("/Users/xiehuiqiang/git/jenkins-plugins/work");

		System.out.println(Tools.getJson(t));

	}

	@Test
	public void testEmailTemplate() {
		EmailTemplate e = new EmailTemplate();
		e.setTitle("$PROJECTNAME 第$NUMBER 构建失败 ");
		e.setContent("$PROJECTNAME 第$NUMBER 构建失败,请尽快处理");
		System.out.println(Tools.getJson(e));

	}

	@Test
	public void testEnvrionment() throws IOException {
		HashMap<String, String> map = new HashMap();

		map.put("WINGROW_DATA", "/wingrow_data");

		Tools.setEnv(map);
		System.out.println(System.getenv("WINGROW_DATA"));
		try {
			System.out.println(InnerConfig.defaultConfig().getDataDir());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}

	}
}

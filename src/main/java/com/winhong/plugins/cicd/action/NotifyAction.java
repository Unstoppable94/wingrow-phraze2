package com.winhong.plugins.cicd.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.sun.mail.util.MailSSLSocketFactory;
import com.winhong.plugins.cicd.data.base.BaseProject;
import com.winhong.plugins.cicd.data.base.ProjectBaseInfo;
import com.winhong.plugins.cicd.maven.MavenProject;
import com.winhong.plugins.cicd.system.Config;
import com.winhong.plugins.cicd.system.EmailTemplate;
import com.winhong.plugins.cicd.system.InnerConfig;
import com.winhong.plugins.cicd.system.SMTPConfig;
import com.winhong.plugins.cicd.tool.JenkinsClient;
import com.winhong.plugins.cicd.tool.Tools;
 import com.winhong.plugins.cicd.view.innerData.Build;
import com.winhong.plugins.cicd.view.innerData.Job;
import com.winhong.plugins.cicd.view.innerData.JobListOfView;

import sun.misc.Signal;
import sun.misc.SignalHandler;

public class NotifyAction {

	private static final Logger log = LoggerFactory.getLogger(NotifyAction.class);

	private static boolean running = true;

//	public static void init() {
//		// Runtime.getRuntime().addShutdownHook(new Thread() {
//		// public void run() {
//		// // System.out.println("reached point of no return ...");
//		// }
//		// });
//
//		SignalHandler handler = new SignalHandler() {
//			public void handle(Signal sig) {
//				if (running) {
//					running = false;
//					System.out.println("Signal " + sig);
//					System.out.println("Shutting down ...");
//				} else {
//					// only on the second attempt do we exit
//					System.out.println("shutdown interrupted!");
//					System.exit(0);
//				}
//			}
//		};
//		Signal.handle(new Signal("INT"), handler);
//		Signal.handle(new Signal("TERM"), handler);
//	}
//
//	public static void main(String[] args) {
//
//		init();
//
//		int sleepminutes = 5;
//		if (args.length > 0) {
//			sleepminutes = Integer.parseInt(args[0]);
//
//		}
//
//		while (true) {
//			try {
//				checkProjectStatus();
//				for (int i = -0; i < sleepminutes; i++) {
//					if (running == false)
//						System.exit(0);
//					Thread.sleep(60 * 1000);
//				}
//
//			} catch (Exception e) {
//				e.printStackTrace();
//				try {
//					Thread.sleep(60 * 1000);
//				} catch (InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			}
//		}
//
//	}

	public static void SendSmtpEmail(SMTPConfig smtpConfig, String to, String subject, String content)
			throws MessagingException, GeneralSecurityException {

		// Get the session object
		Properties props = new Properties();
		final String user = smtpConfig.getUser();
		final String password = smtpConfig.getPassword();

		props.put("mail.smtp.host", smtpConfig.getHost());

		int port = smtpConfig.getPort();

		props.put("mail.smtp.auth", "true");
		// log.debug("conf"+host+":"+user+":"+password);

		// String host, final String user, final String password,
		if (smtpConfig.isSsl()) {
			MailSSLSocketFactory sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
			props.put("mail.smtp.ssl.enable", "true");
			props.put("mail.smtp.ssl.socketFactory", sf);
			if (port == 0)
				port = 465;

		} else {
			if (port == 0)
				port = 25;
		}

		props.put("mail.smtp.port", port);
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});

		// Compose the message
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setText(content);

			// send the message
			Transport.send(message);

			log.debug("message sent successfully...");

		} catch (MessagingException e) {
			throw e;
		}
	}

	// private static final String getBuildUrl =
	// "http://10.211.55.6:28080/api/json?tree=jobs[name,displayName,color,inQueue,inQueue,lastCompletedBuild[name,building,result,number,duration,timestamp,building],lastSuccessfulBuild[name,building,result,number,duration,timestamp,building]]";

	private static final String getBuildUrl = "/api/json?tree=jobs[name,displayName,color,inQueue,inQueue,lastCompletedBuild[name,building,result,number,duration,timestamp,building]]";

	// private static boolean runningflag=false;
	private static String notifyLogDir = "/notifyLog";

	private static String notifyLogFile = "/notifyLog.json";

	static {

		try {
			InnerConfig config = InnerConfig.defaultConfig();

			String datadir = config.getDataDir();
			File file = new File(datadir + notifyLogDir);

			if (file.exists() == false) {
				file.mkdirs();

			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("创建目录失败" + e.getLocalizedMessage());
		}

	}

	public static void checkProjectStatus() throws Exception {
		log.debug("Start to check project status....");
		InnerConfig config = InnerConfig.defaultConfig();

		SMTPConfig smtpConfig = Config.getSMTPConfig();
		if (smtpConfig.getHost() == null) {
			throw new Exception("请先配置SMTP server信息！");
		}
		EmailTemplate successTemplate = config.getSuccessTemplate();
		EmailTemplate failedTemplate = config.getFailedTemplate();
		EmailTemplate recoveryTemplate = config.getRecoveryTemplate();

		String datadir = config.getDataDir();

		// status=gson.fromJson(json, new
		// TypeToken<ArrayList<StatusOfStat>>(){}.getType());
		String url = getBuildUrl;
		String logFileName = datadir + notifyLogDir + notifyLogFile;
		File sendLogFile = new File(logFileName);

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create();

		HashMap<String, sendlogItem> sendlog = new HashMap<String, sendlogItem>();

		Type type = new TypeToken<HashMap<String, sendlogItem>>() {
		}.getType();

		if (sendLogFile.exists()) {
			InputStream fis = new FileInputStream(sendLogFile);
			// create JsonReader object
			JsonReader jsonReader = new JsonReader(new InputStreamReader(fis));

			sendlog = gson.fromJson(jsonReader, type);
		}
		if (sendlog == null)
			sendlog = new HashMap<String, sendlogItem>();

		String output = JenkinsClient.defaultClient().httpSimpleGet(url);

		@SuppressWarnings("unchecked")
		JobListOfView v = (JobListOfView) Tools.objectFromJsonString(output, JobListOfView.class);

		// Job job=(Job) tools.objectFromJsonUrl(url, Job.class);
		// project.getBaseInfo().
		ArrayList<Job> jobs = v.getJobs();

		for (int i = 0; i < jobs.size(); i++) {
			Job job = jobs.get(i);
			Build build = job.getLastCompletedBuild();

			if (build == null)
				continue;
			BaseProject project = ProjectAction.getProject(job.getName());
			String status = Tools.colorToStatus(job.getColor());

			sendlogItem item = sendlog.get(job.getName());
			if (item == null)
				item = new NotifyAction().new sendlogItem(job.getName(), -1, "", false);

			boolean sendResult = false;
			// 没有发送过邮件
			if (item.buildId < build.getNumber()) {

				if (status.equals(Tools.FAILED) && project.getBaseInfo().getMailOnfail() != null
						&& !project.getBaseInfo().getMailOnfail().equals("")) {
					sendResult = SendNodifyEmail(smtpConfig, failedTemplate, project.getBaseInfo().getMailOnfail(),
							project.getBaseInfo(), job);
				} else if (status.equals(Tools.SUCCESS) && project.getBaseInfo().getMailOnReovery() != null
						&& !project.getBaseInfo().getMailOnReovery().equals("")) {

					if (item.getSendFlag().equals(Tools.FAILED)) {
						sendResult = SendNodifyEmail(smtpConfig, recoveryTemplate,
								project.getBaseInfo().getMailOnfail(), project.getBaseInfo(), job);
					}
				}

				if (status.equals(Tools.SUCCESS) && project.getBaseInfo().getMailOnSuccess() != null
						&& !project.getBaseInfo().getMailOnSuccess().equals("")) {
					sendResult = SendNodifyEmail(smtpConfig, successTemplate, project.getBaseInfo().getMailOnSuccess(),
							project.getBaseInfo(), job);

				}
			}

			sendlogItem newitem = new NotifyAction().new sendlogItem(job.getName(), build.getNumber(), status,
					sendResult);
			sendlog.put(job.getName(), newitem);

		}

		// save log
		if (sendLogFile.exists())
			sendLogFile.renameTo(new File(logFileName + "@" + System.currentTimeMillis()));

		String res = gson.toJson(sendlog, type);

		Tools.saveStringToFile(res, logFileName);
		log.debug("save check  result to " + logFileName);
	}

	private static boolean SendNodifyEmail(SMTPConfig smtpConfig, EmailTemplate template, String mailto,
			ProjectBaseInfo baseInfo, Job job) {
		String title = Tools.strRep(template.getTitle(), "$PROJECTNAME", baseInfo.getName());
		title = Tools.strRep(title, "$NUMBER", String.valueOf(job.getLastCompletedBuild().getNumber()));

		String content = Tools.strRep(template.getContent(), "$PROJECTNAME", baseInfo.getName());
		content = Tools.strRep(content, "$NUMBER", String.valueOf(job.getLastCompletedBuild().getNumber()));
		try {
			SendSmtpEmail(smtpConfig, mailto, title, content);
			return true;
		} catch (MessagingException | GeneralSecurityException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return false;

	}

	private class sendlogItem {

		@Expose
		String projectId;

		@Expose
		int buildId;

		public boolean isSendResult() {
			return sendResult;
		}

		public void setSendResult(boolean sendResult) {
			this.sendResult = sendResult;
		}

		@Expose
		String sendFlag;

		@Expose
		boolean sendResult;

		public sendlogItem(String projectId, int buildId, String sendFlag, boolean sendResult) {
			super();
			this.projectId = projectId;
			this.buildId = buildId;
			this.sendFlag = sendFlag;
			this.sendResult = sendResult;
		}

		public int getBuildId() {
			return buildId;
		}

		public void setBuildId(int buildId) {
			this.buildId = buildId;
		}

		public String getProjectId() {
			return projectId;
		}

		public void setProjectId(String projectId) {
			this.projectId = projectId;
		}

		public String getSendFlag() {
			return sendFlag;
		}

		public void setSendFlag(String sendFlag) {
			this.sendFlag = sendFlag;
		}

	}

}
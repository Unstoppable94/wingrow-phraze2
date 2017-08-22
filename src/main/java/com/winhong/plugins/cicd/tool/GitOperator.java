package com.winhong.plugins.cicd.tool;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.InitCommand;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.api.errors.UnmergedPathsException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.util.FS;

public class GitOperator {

	private Repository repo;
	private Config config;
	private Git git;
	private UsernamePasswordCredentialsProvider cp;

	// cp

	/**
	 * init repo，repo 必须已经存在,注意目录需要带上.git
	 * 
	 * @param path
	 *            目录
	 * @throws IOException
	 *             IO一次
	 * @throws IllegalArgumentException
	 *             IllegalArgumentException
	 * @throws GitAPIException 
	 */
	public void gitInit(String path, String url, String name, String password)
			throws IOException, IllegalArgumentException, GitAPIException {

		try {
		 
			repo = new FileRepositoryBuilder().setGitDir(new File(path))
					.readEnvironment() // scan environment GIT_* variables
					.findGitDir() // scan up the file system tree
					.build();

			
			 
			//repo.readDirCache();
			System.out.println("repo exist"+repo.getConfig().toString());
			
			
		} catch (IOException e) {
			
			System.out.println("IOException,repo noexist");

			repo = FileRepositoryBuilder.create(new File(path));
			Git.init().setDirectory( new File(path) ).setBare(false).call();
			setUrl(url);

		} catch (IllegalArgumentException e) {
			throw e;
		}
		if (repo == null) {
			repo = FileRepositoryBuilder.create(new File(path));
			Git.init().setDirectory( new File(path) ).setBare(false).call();
			setUrl(url);

		}

		git = new Git(repo);
		System.out.println(repo.getDirectory());
		//InitCommand init=git.init();
		//init.setDirectory( new File(path) ).setBare(false).call();
	//	 .setDirectory( new File(path) ).call();
		System.out.println("IS BARE?"+repo.isBare());

		cp = new UsernamePasswordCredentialsProvider(name, password);

	}

	public void setUrl(String url) {
		config = repo.getConfig();
		config.setString("remote", "origin", "url", url);

	}

	/*
	 * 
	 * CloneCommand cloneCommand = Git.cloneRepository(); cloneCommand.setURI(
	 * "https://example.com/repo.git" ); cloneCommand.setCredentialsProvider(
	 * new UsernamePasswordCredentialsProvider( "user", "password" ) );
	 */

	public void gitAddAndPush() throws NoHeadException, NoMessageException,
			UnmergedPathsException, ConcurrentRefUpdateException,
			WrongRepositoryStateException, GitAPIException {
		AddCommand add = git.add();
		add.addFilepattern(".").call();

		CommitCommand commit = git.commit();
		String now = new Date().toString();

		commit.setMessage("commit by" + this.getClass().toString() + "@" + now)
				.call();
		PushCommand pc = git.push();
		pc.setCredentialsProvider(cp).setForce(true).setPushAll();

		Iterator<PushResult> it = pc.call().iterator();
		if (it.hasNext()) {
			System.out.println(it.next().toString());
		}

	}

}

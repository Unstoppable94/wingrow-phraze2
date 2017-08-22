package com.winhong.plugins.cicd.tool;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicReference;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.ProgressHandler;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ProgressMessage;
import com.spotify.docker.client.messages.RegistryAuth;
import com.spotify.docker.client.messages.RegistryAuth.Builder;
import org.glassfish.jersey.internal.util.Base64;

public class DockerOperator {

	private String dockerServer = "http://10.211.55.6:4243";
	DockerClient dockerClient;

	public void init() {

		// final DockerClient client = DefaultDockerClient.builder()
		// .registryAuth(registryAuth)
		// .build()

		dockerClient = DefaultDockerClient.builder()
				.uri(URI.create(dockerServer)).build();

	}

	public String buildImages(String dir, String repository, String tag)
			throws DockerException, InterruptedException, IOException {
		// File baseDir = new File(dir);

		final AtomicReference<String> imageIdFromMessage = new AtomicReference<>();

		final String returnedImageId = dockerClient.build(Paths.get(dir),
				repository + "/" + tag, new ProgressHandler() {
					@Override
					public void progress(ProgressMessage message)
							throws DockerException {
						final String imageId = message.buildImageId();
						if (imageId != null) {
							imageIdFromMessage.set(imageId);
						}
					}
				});

		return returnedImageId;
	}

	public DockerOperator() {
		super();
	}

	// static Builder fromDockerConfig(final Path configPath) throws IOException
	// {
	// return parseDockerConfig(configPath, null);
	// }

	public void pushImage(String server, String authString, String email,
			String tag) throws DockerException, InterruptedException,
			IOException {
		// dockerClient.
		// final RegistryAuth registryAuth = RegistryAuth.fromDockerConfig()
		// .build();
		String username = null;
		String password = null;
		final String[] authParams = Base64.decodeAsString(authString)
				.split(":");

		if (authParams.length == 2) {
			username = authParams[0].trim();
			password = authParams[1].trim();
		}

		final RegistryAuth registryAuth = RegistryAuth.builder()
				.password(password).username(username).email(email)
				.serverAddress(server).build();

		/*
		 * final String[] authParams =
		 * Base64.decodeAsString(authString).split(":");
		 * 
		 * if (authParams.length == 2) {
		 * authBuilder.username(authParams[0].trim());
		 * authBuilder.password(authParams[1].trim()); }
		 */
		// dockerClient.push("test/test:test",registryAuth);
		dockerClient.push(tag, registryAuth);
	}

}

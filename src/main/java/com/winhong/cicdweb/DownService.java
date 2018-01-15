package com.winhong.cicdweb;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winhong.plugins.cicd.tool.JenkinsClient;

@Path("/download")
@Consumes("application/json;charset=UTF-8")
public class DownService {
	private static final Logger log = LoggerFactory.getLogger(DownService.class);

	private static final int BUFFER_SIZE = 4096;

	@GET
	@Produces(MediaType.APPLICATION_OCTET_STREAM) // 返回方式为流
	public byte[] Download(@QueryParam("name") String path, @Context HttpServletResponse response)
			throws FileNotFoundException {
		path = path.replaceAll("%2F", "/");
		log.debug("filename:" + path);
		try {
			JenkinsClient client = JenkinsClient.defaultClient();

			InputStream fis = client.getFile(path);
			String filename = path.substring(path.lastIndexOf("/") + 1);
			// byte[] b = new byte[fis.available()];
			// log.debug("length="+b.length);
			// fis.read(b);
			// String text = IOUtils.toString(fis, StandardCharsets.UTF_8.name());
			if (client.getCrumbField() == null)
				if (!client.getCrumb())
					throw new IOException("get crumb fail");
			log.debug(client.getCrumbField() + ":" + client.getCrumbValue());
			response.setHeader(client.getCrumbField(), client.getCrumbValue());
			response.setHeader("Content-Disposition", "attachment;filename=" + filename);// 为文件命名
			response.addHeader("Content-type", "application/octet-stream");
			response.addHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Pragma", "public");
			response.setHeader("Expires", "0");
			response.addHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.addHeader("Content-Description", "File Transfer");

			// opens an output stream to save into file
			// FileOutputStream outputStream = new FileOutputStream(tempFile);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			int bytesRead = -1;
			byte[] buffer = new byte[BUFFER_SIZE];

			while ((bytesRead = fis.read(buffer)) != -1) {
				output.write(buffer, 0, bytesRead);
			}

			byte[] targetArray = output.toByteArray();
			output.close();
			fis.close();
			response.addHeader("Content-Length: ", String.valueOf(targetArray.length));

			return targetArray;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			return new byte[0];
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			return new byte[0];
		}
	}
}

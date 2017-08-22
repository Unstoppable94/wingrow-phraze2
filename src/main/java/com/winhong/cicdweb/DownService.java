package com.winhong.cicdweb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class DownService {
	private static final Logger log = LoggerFactory.getLogger(DownService.class);

	@GET
	@Produces(MediaType.APPLICATION_OCTET_STREAM) // 返回方式为流
	public byte[] getStudentJl(@QueryParam("name") String path, @Context HttpServletRequest request,
			@Context HttpServletResponse response) throws FileNotFoundException {

		log.debug("filename:"+path);
		JenkinsClient client = JenkinsClient.defaultClient();
		try {
			
			InputStream fis = client.getFile(path);
			String filename = path.substring(path.lastIndexOf("/") + 1);
			//byte[] b = new byte[fis.available()];
			//log.debug("length="+b.length);
			//fis.read(b);
			String text = IOUtils.toString(fis, StandardCharsets.UTF_8.name());
			
			response.setHeader("Content-Disposition", "attachment;filename=" + filename);// 为文件命名
			response.addHeader("content-type", "application/pdf");
			return text.getBytes();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}

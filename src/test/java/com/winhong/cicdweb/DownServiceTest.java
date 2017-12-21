package com.winhong.cicdweb;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;

public class DownServiceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testGetStudentJl() {
		
 		  HttpServletResponse response=Mockito.mock(HttpServletResponse.class);
		
		DownService d=new DownService();
		try {
			ServletOutputStream outputStream=Mockito.mock(ServletOutputStream.class);
			//ServletOutputStream outputStream = response.getOutputStream()
			//		when(response.getWriter())
		     //       .thenReturn(new PrintWriter(new StringWriter()));
			
			OngoingStubbing<ServletOutputStream> out = Mockito.when( response.getOutputStream() ).thenReturn(outputStream );
			//d.Download("/job/pro1505966862278/4/artifact/jenkins-pro1505966862278-4.zip", response);

			d.Download("/job/pro1509613746515/2/consoleText", response);

			
 		} catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

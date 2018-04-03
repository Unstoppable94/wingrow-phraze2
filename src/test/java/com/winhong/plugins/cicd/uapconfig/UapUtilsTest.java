package com.winhong.plugins.cicd.uapconfig;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import com.winhong.uap.sdk.RestFactory;
import com.winhong.uap.sdk.api.RestClient.RestClientV1;
import com.winhong.uap.sdk.api.local.UserService;
import com.winhong.uap.sdk.common.Paginate;
import com.winhong.uap.sdk.model.User;

public class UapUtilsTest {

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	public static void main(String[] args) throws Exception { 
	       String url = "https://cas.iwinhong.com/uap/v1"; 
	       System.out.println("url=="+url); 
	       try { 
	           RestClientV1 rest = RestFactory.builderV1() 
	                   .endpoint(url).useNonStrictSSLClient(true) 
	                   //.credentials(account, decrypt) 
	                   .authenticate(); 
	           UserService service = rest.locals().users(); 
	           Paginate<User> users = service.getAllByIdLike("ad"); 
	           List list = users.getData(); 
	           System.out.println("rest==="+list.size()); 
	       } catch (Exception e) { 
	           e.printStackTrace(); 
	       } 
	   }
}

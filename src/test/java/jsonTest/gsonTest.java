package jsonTest;

import static org.junit.Assert.*;

import java.sql.Timestamp;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.winhong.plugins.cicd.mavenStep.Compile;

 
public class gsonTest {

	@Test
	public void testPrintJson() {
		Compile c=new Compile();
		
		
		Gson gson = new GsonBuilder().serializeNulls().create();
		 
		String json = gson.toJson(c);
		System.out.println(json);

		json = gson.toJson(null);
		System.out.println(json);
		
		
 	}

	@Test
	public void testTimeTest(){
		long t=1489894156000L;
		Timestamp time=new Timestamp(t);
		System.out.print(time.toGMTString());
				
		
	}
}

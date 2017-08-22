package jsonTest;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.winhong.plugins.cicd.action.GroupAction;
import com.winhong.plugins.cicd.mavenStep.Compile;

public class logTest {

	private static final Logger log = LoggerFactory
			.getLogger(GroupAction.class);
	@Test
	public void testPrintJson() {
		Compile c=new Compile("testGoal");
		
		
		log.debug("onlytest");
		log.debug("test", new Exception("test"));
		
		
 	}
}

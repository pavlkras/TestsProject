import java.io.File;
import java.io.IOException;

import tel_ran.tests.services.subtype_handlers.programming.CodeTester;
import tel_ran.tests.services.subtype_handlers.programming.JavaTester;




public class TestAppl {

	private static final String ProjectPath = System.getProperty("user.dir");
	private static final String testSamplePath = ProjectPath.concat("\\TEST\\testZip.zip");
	private static final String taskPath = ProjectPath.concat("\\TEST\\TestFromPerson"); 

	public static void main(String[] args) throws IOException {
		
		CodeTester test = new JavaTester();
		System.out.println(test.testIt(taskPath, testSamplePath));
		
		
	}

}

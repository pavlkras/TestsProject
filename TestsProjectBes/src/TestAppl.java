import java.io.File;
import java.io.IOException;

import tel_ran.tests.gradle_tester.CodeTester;



public class TestAppl {

	private static final String ProjectPath = System.getProperty("user.dir");
	private static final String testPath = ProjectPath.concat("\\TEST\\RESULT");
	private static final String testSamplePath = ProjectPath.concat("\\TEST\\testZip.zip");
	private static final String taskPath = ProjectPath.concat("\\TEST\\TestFromPerson"); 

	public static void main(String[] args) throws IOException {
		
		CodeTester test = new CodeTester(testPath);
		System.out.println(test.testIt(taskPath, testSamplePath));
		
		
	}

}

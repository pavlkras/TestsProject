package tel_ran.tests.services.subtype_handlers.gradle;

import java.io.File;
import java.io.IOException;

public class CodeTester implements ICodeTester {

	TestTemplateHandler testTemplateHandler = null;
	public static String WORK_PATH = ""; 
	
	
	public CodeTester(String workPath) throws IOException{
		File fl = new File(workPath);
		if(!fl.exists()) {
			fl.mkdirs();
			if(!WORK_PATH.equals(workPath))
				WORK_PATH = workPath;
		}
		
		testTemplateHandler = new TestTemplateHandler(workPath);
	}
	
	
	public CodeTester() throws IOException{
		testTemplateHandler = new TestTemplateHandler(ICodeTester.workFolderPath);
	}
	
	
	public synchronized boolean testIt(String codeFromPersonPath, String pathToAnswersZip) {
		boolean results = false;
				
		try{
			testTemplateHandler.fillTemplate(codeFromPersonPath, pathToAnswersZip);
			results = GradleHandler.buildAndTest(testTemplateHandler.getWorkFolder());	
			
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			saveResults(results);
		}
		
		testTemplateHandler.cleanWorkFolder();
	
		return results;
	}

	private void saveResults(boolean results) {
		// TODO Auto-generated method stub
		
	}
}
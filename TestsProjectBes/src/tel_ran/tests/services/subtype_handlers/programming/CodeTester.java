package tel_ran.tests.services.subtype_handlers.programming;


import java.io.IOException;


public class CodeTester implements ICodeTester {

	IProgramTestHandler testTemplateHandler;
		
	public CodeTester() throws IOException{
		testTemplateHandler = new GradleJava();
	}	
	
	public synchronized boolean testIt(String codeFromPersonPath, String pathToAnswersZip) {
		boolean results = false;
				
		try{
			testTemplateHandler.fillTemplate(codeFromPersonPath, pathToAnswersZip);
			results = testTemplateHandler.test();		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
//			saveResults(results);
		}
		
//		testTemplateHandler.cleanWorkFolder();
	
		return results;
	}
}
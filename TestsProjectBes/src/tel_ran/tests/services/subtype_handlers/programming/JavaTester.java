package tel_ran.tests.services.subtype_handlers.programming;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import tel_ran.tests.entitys.GeneratedProgrammingQuestion;
import tel_ran.tests.entitys.InTestQuestion;
import tel_ran.tests.entitys.Texts;
import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.utils.FileManagerService;

public class JavaTester extends CodeTester {

	
		
	@Override
	public String saveCode(InTestQuestion tQuestion, String[] lines) throws IOException {
		String result = null;
		int companyId = tQuestion.getTest().getCompany().getId();
		long testId = tQuestion.getTest().getId();
		long questionId = tQuestion.getQuestion().getId();
		
		result = FileManagerService.saveCode(companyId, testId, questionId, lines);
		
		return result;
	}

	@Override
	protected IProgramTestHandler getTestHandler() {
		this.testTemplateHandler = new GradleJava();
		return this.testTemplateHandler;
	}

	@Override
	public void addDataToJson(JSONObject jsn, GeneratedProgrammingQuestion question) throws JSONException {
		List<Texts> list = question.getTextsList();
		if(list!=null && list.size() > 0) {
			jsn.put(ICommonData.JSN_INTEST_CODE, list.get(0).getText());			
		}
		
	}
	
	@Override
	public boolean testIt(String codeFromPersonPath, String pathToAnswersZip) {
		
		getTestHandler();
		
		boolean results = false;	
		
		try{
			testTemplateHandler.fillTemplate(codeFromPersonPath, pathToAnswersZip);
			results = testTemplateHandler.test();					
		}catch(Exception e){
			e.printStackTrace();
		}
				
		testTemplateHandler.cleanWorkFolder();
	
		return results;
	}

	
}

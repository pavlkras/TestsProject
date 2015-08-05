package tel_ran.tests.services.subtype_handlers;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import tel_ran.tests.services.inner_result.dataobjects.InnerResultDataObject;
import tel_ran.tests.services.subtype_handlers.gradle.CodeTester;

import java.io.IOException;

@Component
public class CodeTestQuestionHandler extends AbstractTestQuestionHandler implements ITestQuestionHandler{
	
	public static final int QUESTION_TYPE = 2;
	
	public CodeTestQuestionHandler() {
		super();
	}

	@Override
	public void analyze() {
//		Source code is here:
//		https://github.com/IgorTymoshchuk/GradleTestsSubProj.git
		String pathToAnswersZip;
		String codeFromPersonPath;
		CodeTester gradleModule;
		boolean gradleAnswer;

		pathToAnswersZip=getQuestionAttribubes().getFileLocationLink(); //Path to zip
		codeFromPersonPath=fileManager.getPathToCode(companyId, testId, getQuestionID()); //Path to person code
		try {
			//TODO Check case of time limit of execution of analyze process
			gradleModule = new CodeTester();
			gradleAnswer=gradleModule.testIt(codeFromPersonPath,pathToAnswersZip);
			if(gradleAnswer){
				dataObj.setStatus(InnerResultDataObject.STATUS_TRUE);
			} else {
				dataObj.setStatus(InnerResultDataObject.STATUS_FALSE);
			}
		} catch (IOException e) {
			//TODO Check case of exception inside of gradle module
			e.printStackTrace();
		}
	}


	@Override
	public boolean setPersonAnswer(JSONObject answerJsonObj) {
		// TODO Auto-generated method stub
		// Saving fields to files

		return false;
	}

	@Override
	public String getQuestionJson(int index) {
		String stub = getQuestionAttribubes().getAnswers().get(0);
		getQuestionAttribubes().getQuestionId().getQuestionText(); //question text
		getQuestionAttribubes().getDescription(); //description
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQuestionViewResultJson() {
		// TODO Auto-generated method stub
		return null;
	}
}
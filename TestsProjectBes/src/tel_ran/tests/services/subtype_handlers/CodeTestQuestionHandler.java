package tel_ran.tests.services.subtype_handlers;

import org.json.JSONObject;
import tel_ran.tests.gradle_tester.CodeTester;

import java.io.IOException;

public class CodeTestQuestionHandler extends AbstractTestQuestionHandler implements ITestQuestionHandler{

	public CodeTestQuestionHandler() {
		super();
	}

	@Override
	public void analyze() {
		// TODO Gradle call
//		Source code is here:
//		https://github.com/IgorTymoshchuk/GradleTestsSubProj.git
		String pathToAnswersZip;
		String codeFromPersonPath;
		CodeTester gradleModule;
		boolean gradleAnswer;

		pathToAnswersZip=getQuestionAttribubes().getFileLocationLink(); //Path to zip
		codeFromPersonPath=fileManager.getPathToCode(companyId, testId, getQuestionID()); //Path to person code
		try {

			gradleModule = new CodeTester();
//----------NEED TO PROCEED SOMEWHERE WITH GRADLE ANSWER
			gradleAnswer=gradleModule.testIt(codeFromPersonPath,pathToAnswersZip);
//--------------------------------

		} catch (IOException e) {
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


		getQuestionAttribubes().getLineCod(); //stub
		getQuestionAttribubes().getQuestionId().getQuestionText(); //question text
		getQuestionAttribubes().getQuestionId().getDescription(); //description
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQuestionViewResultJson() {
		// TODO Auto-generated method stub
		return null;
	}
}
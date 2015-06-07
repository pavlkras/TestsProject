package tel_ran.tests.services.subtype_handlers;

import org.json.JSONObject;

public class CodeTestQuestionHandler extends AbstractTestQuestionHandler implements ITestQuestionHandler{

	public CodeTestQuestionHandler() {
		super();
	}

	@Override
	public void analyze() {
		// TODO Gradle call
		getQuestionAttribubes().getFileLocationLink(); //Path to zip
		fileManager.getPathToCode(companyId, testId, getQuestionID()); //Path to person code
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
package tel_ran.tests.services.subtype_handlers;

import org.json.JSONObject;

import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.services.inner_result.dataobjects.InnerResultDataObject;

public class CodeTestQuestionHandler extends AbstractTestQuestionHandler implements ITestQuestionHandler{

	public CodeTestQuestionHandler() {
		super();
	}

	@Override
	public void createFromQuestion(EntityQuestionAttributes question) {
		dataObj = new InnerResultDataObject();
		dataObj.setQuestionID(question.getId());
		dataObj.setRightAnswer(null);
		dataObj.setMetacategory(question.getMetaCategory());
		dataObj.setStatus(InnerResultDataObject.STATUS_NOT_ASKED);		
	}

	@Override
	public void analyze() {
		// TODO 
		// Gradle call
		EntityQuestionAttributes question = getQuestionAttribubes();
	}

	@Override
	public boolean setPersonAnswer(JSONObject answerJsonObj) {
		// TODO Auto-generated method stub
		// Saving fields to files
		
		return false;
	}

	@Override
	public String getQuestionJson(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQuestionViewResultJson() {
		// TODO Auto-generated method stub
		return null;
	}

}

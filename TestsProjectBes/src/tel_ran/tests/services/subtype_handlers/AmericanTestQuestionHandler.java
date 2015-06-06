package tel_ran.tests.services.subtype_handlers;

import org.json.JSONException;
import org.json.JSONObject;

import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.services.inner_result.dataobjects.InnerResultDataObject;

public class AmericanTestQuestionHandler extends AbstractTestQuestionHandler implements ITestQuestionHandler{

	public AmericanTestQuestionHandler(){
		super();
	}

	@Override
	public void createFromQuestion(EntityQuestionAttributes question) {
		dataObj = new InnerResultDataObject();
		dataObj.setQuestionID(question.getId());
		dataObj.setRightAnswer(question.getCorrectAnswer());
		dataObj.setMetacategory(question.getMetaCategory());
		dataObj.setStatus(InnerResultDataObject.STATUS_NOT_ASKED);
	}

	@Override
	public void analyze() {
		if(dataObj.getRightAnswer().equals(dataObj.getPersonAnswer())){
			dataObj.setStatus(InnerResultDataObject.STATUS_TRUE);
		} else {
			dataObj.setStatus(InnerResultDataObject.STATUS_FALSE);
		}
	}

	@Override
	public boolean setPersonAnswer(JSONObject answerJsonObj) {
		//TODO Export string to constant
		String personAnswer = "";
		try {
			personAnswer = answerJsonObj.getString("answer");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		dataObj.setPersonAnswer(personAnswer);
		analyze();
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
package tel_ran.tests.services.subtype_handlers;

import org.json.JSONException;
import org.json.JSONObject;

import tel_ran.tests.services.inner_result.dataobjects.InnerResultDataObject;

public class AmericanTestQuestionHandler extends AbstractTestQuestionHandler implements ITestQuestionHandler{

	public AmericanTestQuestionHandler(){
		super();
	}

	@Override
	public void analyze() {
		if(getQuestionAttribubes().getCorrectAnswer().equals(dataObj.getPersonAnswer())){
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
		JSONObject json = new JSONObject();
		try {
			json.put(KEY_INDEX, index);
			json.put(KEY_QUESTION_TEXT, getQuestionAttribubes().getQuestionId().getQuestionText());
			json.put(KEY_QUESTION_IMAGE, fileManager.getFileFromPath(getQuestionAttribubes().getFileLocationLink()));
			json.put(KEY_NUMBER_OF_ANSWERS, getQuestionAttribubes().getNumberOfResponsesInThePicture());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	@Override
	public String getQuestionViewResultJson() {
		
		// TODO Auto-generated method stub
		return null;
	}

}
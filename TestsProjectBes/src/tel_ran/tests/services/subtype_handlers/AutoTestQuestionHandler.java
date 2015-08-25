package tel_ran.tests.services.subtype_handlers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.entitys.EntityTestQuestions;
import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.inner_result.dataobjects.InnerResultDataObject;
import tel_ran.tests.services.utils.FileManagerService;
@Component
public class AutoTestQuestionHandler extends AbstractTestQuestionHandler {

	
	
	public AutoTestQuestionHandler(){		
		super();
		type = ICommonData.QUESTION_TYPE_ALL_IN_IMAGE;
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
			System.out.println("answer was not found inside of request");
			return false;
		}
		dataObj.setPersonAnswer(personAnswer);
		analyze();
		return true;
	}

	@Override
	public String getQuestionJson(int index) {
		JSONObject json = new JSONObject();
		try {
			json.put(ICommonData.JSN_INTEST_QUESTION_TEXT, getQuestionAttribubes().getQuestionId().getQuestionText());
			String fileLink = getQuestionAttribubes().getFileLocationLink();
			json.put(ICommonData.JSN_IMAGE, getImageBase64(fileLink));
			JSONArray answers = new JSONArray();
			for(int max = getQuestionAttribubes().getNumberOfResponsesInThePicture(), i = 0; i < max; i++){
				answers.put(Character.toString ((char) (i+65)));
			}
			json.put(ICommonData.JSN_INTEST_OPTIONS_CHARS, answers);
			json.put(ICommonData.JSN_INTEST_INDEX, index);
			json.put(ICommonData.JSN_INTEST_TYPE, type);
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
	
	@Override
	public JSONObject getJsonForTest(long eqtId, int index) throws JSONException {
		// from SUPER - text of question, id of EntityTestQuestion, index, type
		JSONObject result = super.getJsonForTest(eqtId, index);
		
		// get image
		String fileLink = getQuestionAttribubes().getFileLocationLink();
		if(fileLink!=null && fileLink.length() > 2) {
			result.put(ICommonData.JSN_INTEST_IMAGE, getImageBase64(fileLink));
		}
		
		// get options chars (A, B, C, D, ...)
		JSONArray answers = new JSONArray();
		for(int max = getQuestionAttribubes().getNumberOfResponsesInThePicture(), i = 0; i < max; i++){
			answers.put(Character.toString ((char) (i+65)));
		}
		result.put(ICommonData.JSN_INTEST_OPTIONS_CHARS, answers);	
				
		return result;		
	}
	
	
	@Override
	protected int getStatus(String answer) {
		String correctAnswer = entityQuestionAttributes.getCorrectAnswer();
		int status;
		if(answer.equalsIgnoreCase(correctAnswer)) {
			status = ICommonData.STATUS_CORRECT;
		} else {
			status = ICommonData.STATUS_INCORRECT;
		}			
		return status;
	}
	
	
	
		
}
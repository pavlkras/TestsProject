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
import tel_ran.tests.services.common.IPublicStrings;
import tel_ran.tests.services.inner_result.dataobjects.InnerResultDataObject;
import tel_ran.tests.services.utils.FileManagerService;
@Component
public class AutoTestQuestionHandler extends AbstractTestQuestionHandler {
		
	
	public AutoTestQuestionHandler(){		
		super();
		type = ICommonData.QUESTION_TYPE_ALL_IN_IMAGE;
		gradeType = 0;
	}

//	@Override
//	public void analyze() {
//		if(getQuestionAttribubes().getCorrectAnswer().equals(dataObj.getPersonAnswer())){
//			dataObj.setStatus(InnerResultDataObject.STATUS_TRUE);
//		} else {
//			dataObj.setStatus(InnerResultDataObject.STATUS_FALSE);
//		}
//	}

//	@Override
//	public boolean setPersonAnswer(JSONObject answerJsonObj) {
//		//TODO Export string to constant
//		String personAnswer = "";
//		try {
//			personAnswer = answerJsonObj.getString("answer");
//		} catch (JSONException e) {
//			System.out.println("answer was not found inside of request");
//			return false;
//		}
//		dataObj.setPersonAnswer(personAnswer);
//		analyze();
//		return true;
//	}

	@Override
	public String getQuestionJson(int index) {
		JSONObject json = new JSONObject();
		try {
			json.put(ICommonData.JSN_INTEST_QUESTION_TEXT, getQuestionAttribubes().getEntityTitleQuestion().getQuestionText());
			String fileLink = getQuestionAttribubes().getFileLocationLink();
			json.put(ICommonData.JSN_IMAGE, getImageBase64(fileLink));
			JSONArray answers = new JSONArray();
			int numOfQuestions = getQuestionAttribubes().getNumberOfResponsesInThePicture();
			for (int i = 0; i < numOfQuestions; i++ )
				answers.put(IPublicStrings.LETTERS[i]);
						
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
		putImageForJSON(ICommonData.JSN_INTEST_IMAGE, result);
				
		// get options chars (A, B, C, D, ...)
		putLettersForAnswerOptions(getQuestionAttribubes().getNumberOfResponsesInThePicture(), 
				ICommonData.JSN_INTEST_OPTIONS_CHARS,
				result);
						
		return result;		
	}
	
	protected void putLettersForAnswerOptions(int numOfQuestion, String key, JSONObject jsn) 
			throws JSONException {
		JSONArray result = new JSONArray();	
		System.out.println(ICommonData.LETTERS.length);
		for(int i = 0; i < numOfQuestion; i++){
			System.out.println(i);
			System.out.println(ICommonData.LETTERS[0]);
			result.put(ICommonData.LETTERS[i]);
		}
		jsn.put(key, result);
			
	}
	

	@Override
	protected String preparingAnswer(String answer) {		
		return answer;
	}
	
	
	@Override
	protected int checkAnswers() {
		String correctAnswer = entityQuestionAttributes.getCorrectAnswer();
		String answer = entityTestQuestion.getAnswer();
		int status;
		if(answer.equalsIgnoreCase(correctAnswer)) {
			status = ICommonData.STATUS_CORRECT;
		} else {
			status = ICommonData.STATUS_INCORRECT;
		}	
		return status;
	}

	// IMAGE, ANSWER_LETTERS, CORRECT ANSWER + DATA FROM SUPER
	@Override
	public JSONObject getJsonWithCorrectAnswer(EntityTestQuestions entityTestQuestion) throws JSONException {
		JSONObject result = super.getJsonWithCorrectAnswer(entityTestQuestion);
		
		int size = getQuestionAttribubes().getNumberOfResponsesInThePicture();
		putLettersForAnswerOptions(size, ICommonData.JSN_QUESTDET_ANSWERS_LETTERS, result);
		putImageForJSON(ICommonData.JSN_QUESTDET_IMAGE, result);
		
		result.put(ICommonData.JSN_QUESTDET_CORRECT_ANSWER, getQuestionAttribubes().getCorrectAnswer());		
		
		return result;
	}
	
	@Override
	protected JSONArray getAnswerInJSON(EntityTestQuestions etq) {
		String answer = etq.getAnswer();
		JSONArray result = new JSONArray();
		result.put(answer);
		return result;
	}

	//impossible to change status for this type of question
	@Override
	protected int getStatusFromMark(String mark) {		
		return -1;
	}



		
}
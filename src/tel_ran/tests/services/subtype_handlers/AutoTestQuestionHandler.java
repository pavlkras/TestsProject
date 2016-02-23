package tel_ran.tests.services.subtype_handlers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import tel_ran.tests.entitys.GeneratedCommonQuestion;
import tel_ran.tests.entitys.InTestQuestion;
import tel_ran.tests.entitys.Question;
import tel_ran.tests.services.common.ICommonData;

@Component
public class AutoTestQuestionHandler extends AbstractTestQuestionHandler {
		
	GeneratedCommonQuestion question;
	
	@Override
	public void setQuestion(Question question) {
		System.out.println("I'm HERE !!!!!!!!!!!!!!!");
		this.question = (GeneratedCommonQuestion)question;
		System.out.println(this.question.getId());
		
	}	
	
	public AutoTestQuestionHandler(){		
		super();
		type = ICommonData.QUESTION_TYPE_ALL_IN_IMAGE;
		gradeType = 0;
		categoryType = "auto";
	}

	@Override
	protected void addDataToJson(JSONObject jsn) throws JSONException {
				
		jsn.put(ICommonData.JSN_INTEST_QUESTION_TEXT, question.getTitle().getQuestionText());
		jsn.put(ICommonData.JSN_IMAGE, getImageBase64(question.getFileLocationLink()));
		
					
		int numOfQuestions = question.getNumberOfAnswerOptions();	
		putLettersForAnswerOptions(numOfQuestions, ICommonData.JSN_INTEST_OPTIONS_CHARS, jsn);		
	}

	
	@Override
	protected void addFullDataToJson(JSONObject jsn) throws JSONException {
		jsn.put(ICommonData.JSN_QUESTDET_METACATEGORY, question.getCategory().getMetaCategory());
		jsn.put(ICommonData.JSN_QUESTDET_CATEGORY1, question.getCategory().getCategory1());
		jsn.put(ICommonData.JSN_QUESTDET_TEXT, question.getCategory().getCategory2());
		
		int size = question.getNumberOfAnswerOptions();
		
		putLettersForAnswerOptions(size, ICommonData.JSN_QUESTDET_ANSWERS_LETTERS, jsn);
		jsn.put(ICommonData.JSN_IMAGE, getImageBase64(question.getFileLocationLink()));
		
		jsn.put(ICommonData.JSN_QUESTDET_CORRECT_ANSWER, question.getCorrectAnswerChar());	
		
	}
	
	@Override
	public String getQuestionViewResultJson() {
		
		// TODO Auto-generated method stub
		return null;
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
		
		String correctAnswer = question.getCorrectAnswerChar();
		String answer = tQuestion.getAnswer();
		int status;
		if(answer.equalsIgnoreCase(correctAnswer)) {
			status = ICommonData.STATUS_CORRECT;
		} else {
			status = ICommonData.STATUS_INCORRECT;
		}	
		return status;
	}

		
	@Override
	protected JSONArray getAnswerInJSON(InTestQuestion etq) {
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

	@Override
	public String getQuestionJson(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void printQuestion() {
		System.out.println(this.question.getId());
		
	}

	



		
}
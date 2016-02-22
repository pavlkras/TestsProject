package tel_ran.tests.services.subtype_handlers;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tel_ran.tests.entitys.Question;
import tel_ran.tests.entitys.QuestionCustom;
import tel_ran.tests.entitys.QuestionCustomTest;
import tel_ran.tests.entitys.Texts;
import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.common.IPublicStrings;

public class AmericanTestQuestionHandler extends AutoTestQuestionHandler {
	
	QuestionCustomTest question;
		
	
	@Override
	public void setQuestion(Question question) {
		this.question = (QuestionCustomTest)question;
	}
	
	public AmericanTestQuestionHandler() {
		super();
		type = ICommonData.QUESTION_TYPE_AMERICAN_TEST;
		gradeType = 0;
		categoryType = "custom";
	}

	@Override
	protected void addDataToJson(JSONObject jsn) throws JSONException {
		
		String description = ((QuestionCustom)question).getDescription();
		jsn.put(ICommonData.JSN_INTEST_DESCRIPTION, getManyLinesField(description));
		
		jsn.put(ICommonData.JSN_INTEST_QUESTION_TEXT, question.getTitle().getQuestionText());
						
		putLettersForAnswerOptions(question.getNumberOfAnswerOptions(), ICommonData.JSN_INTEST_OPTIONS_CHARS, jsn);
		JSONArray array = getAnswerOptions(ICommonData.JSN_INTEST_OPTIONS_CHARS, ICommonData.JSN_INTEST_ONE_ANSWER_OPTION);	
		if(array!=null)
			jsn.put(ICommonData.JSN_INTEST_ALL_ANSWER_OPTIONS, array);					
		
	}
	
	@Override
	protected void addFullDataToJson(JSONObject jsn) throws JSONException {
		jsn.put(ICommonData.JSN_QUESTDET_METACATEGORY, question.getCategory().getMetaCategory());
		jsn.put(ICommonData.JSN_QUESTDET_CATEGORY1, question.getCategory().getCategory1());
		jsn.put(ICommonData.JSN_QUESTDET_TEXT, question.getCategory().getCategory2());
		
		jsn.put(ICommonData.JSN_QUESTDET_DESCRIPTION, getManyLinesField(question.getDescription()));
		
		putLettersForAnswerOptions(question.getNumberOfAnswerOptions(), ICommonData.JSN_INTEST_OPTIONS_CHARS, jsn);
		JSONArray array = getAnswerOptions(ICommonData.JSN_QUESTDET_ANSWER_OPTION_LETTER, ICommonData.JSN_QUESTDET_ANSWER_OPTION);	
		if(array!=null)
			jsn.put(ICommonData.JSN_QUESTDET_ANSWER_OPTIONS_LIST, array);	
		
	}
	
	
	private JSONArray getAnswerOptions(String keyLetters, String keyOption) throws JSONException {
		List<Texts> list = question.getTextsList();	
		JSONArray array = null;
		if(list!=null) {
			array = new JSONArray();	
			int numOfQuestions = list.size();
			
			for (int i = 0; i < numOfQuestions; i++) {
				JSONObject jsn = new JSONObject();				
				jsn.put(keyOption, list.get(i).getText());
				jsn.put(keyLetters, IPublicStrings.LETTERS[i]);
				array.put(jsn);
			}						
		}
		return array;
			
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

}

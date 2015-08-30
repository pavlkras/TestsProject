package tel_ran.tests.services.subtype_handlers;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tel_ran.tests.entitys.EntityTestQuestions;
import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.common.IPublicStrings;

public class AmericanTestQuestionHandler extends AutoTestQuestionHandler {
	
	
	
	public AmericanTestQuestionHandler() {
		super();
		type = ICommonData.QUESTION_TYPE_AMERICAN_TEST;
		gradeType = 0;
	}



	@Override
	public JSONObject getJsonForTest(long eqtId, int index) throws JSONException {
		// from SUPER - text of question, id of EntityTestQuestion, index, type
		// + image, chars for answer
		JSONObject result = super.getJsonForTest(eqtId, index);
		
		// get description
		String description = getQuestionAttribubes().getDescription();
		result.put(ICommonData.JSN_INTEST_DESCRIPTION, getManyLinesField(description));
		
		// get answer options
		JSONArray array = getAnswerOptions(ICommonData.JSN_INTEST_OPTIONS_CHARS, ICommonData.JSN_INTEST_ONE_ANSWER_OPTION);	
		if(array!=null)
			result.put(ICommonData.JSN_INTEST_ALL_ANSWER_OPTIONS, array);			
						
		return result;		
	}
	
	// DATA FROM SUPER + DESCRIPTION, ANSWER_OPTIONS
	@Override
	public JSONObject getJsonWithCorrectAnswer(EntityTestQuestions entityTestQuestion) throws JSONException  {
		JSONObject result = super.getJsonWithCorrectAnswer(entityTestQuestion);
		result.put(ICommonData.JSN_QUESTDET_DESCRIPTION, getManyLinesField(getQuestionAttribubes().getDescription()));
		
		JSONArray array = getAnswerOptions(ICommonData.JSN_QUESTDET_ANSWER_OPTION_LETTER, ICommonData.JSN_QUESTDET_ANSWER_OPTION);	
		if(array!=null)
			result.put(ICommonData.JSN_QUESTDET_ANSWER_OPTIONS_LIST, array);	
		
		return result;
	}
	
	private JSONArray getAnswerOptions(String keyLetters, String keyOption) throws JSONException {
		List<String> list = getQuestionAttribubes().getAnswers();	
		JSONArray array = null;
		if(list!=null) {
			array = new JSONArray();	
			int numOfQuestions = list.size();
			
			for (int i = 0; i < numOfQuestions; i++) {
				JSONObject jsn = new JSONObject();				
				jsn.put(keyOption, list.get(i));
				jsn.put(keyLetters, IPublicStrings.LETTERS[i]);
				array.put(jsn);
			}						
		}
		return array;
			
	}

}

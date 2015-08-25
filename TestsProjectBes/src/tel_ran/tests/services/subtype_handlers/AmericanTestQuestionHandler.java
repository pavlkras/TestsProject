package tel_ran.tests.services.subtype_handlers;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tel_ran.tests.entitys.EntityAnswersText;
import tel_ran.tests.services.common.ICommonData;

public class AmericanTestQuestionHandler extends AutoTestQuestionHandler {
	
	
	
	public AmericanTestQuestionHandler() {
		super();
		type = ICommonData.QUESTION_TYPE_AMERICAN_TEST;
	}



	@Override
	public JSONObject getJsonForTest(long eqtId, int index) throws JSONException {
		// from SUPER - text of question, id of EntityTestQuestion, index, type
		// + image, chars for answer
		JSONObject result = super.getJsonForTest(eqtId, index);
		
		// get description
		String description = getQuestionAttribubes().getDescription();
		result.put(ICommonData.JSN_INTEST_DESCRIPTION, description);
		
		// get answer options
		List<String> list = getQuestionAttribubes().getAnswers();		
		if(list!=null) {
			JSONArray array = new JSONArray();			
			for(String str : list) {
				JSONObject jsn = new JSONObject();
				jsn.put(ICommonData.JSN_INTEST_ONE_ANSWER_OPTION, str);
				array.put(jsn);
			}
			result.put(ICommonData.JSN_INTEST_ALL_ANSWER_OPTIONS, array);			
		}
						
		return result;		
	}
	

}

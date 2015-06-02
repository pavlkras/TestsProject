package tel_ran.tests.services.testhandler;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.services.PersonalActionsService;
import tel_ran.tests.services.inner_result.dataobjects.InnerResultDataObject;
import tel_ran.tests.services.subtype_handlers.ITestQuestionHandler;
import tel_ran.tests.services.subtype_handlers.SingleTestQuestionHandlerFactory;

public class PersonTestHandler implements IPersonTestHandler {
	private JSONArray jsonTestResults;
	public static final String KEY_INDEX = "index";
	
	
	@Autowired
	PersonalActionsService personalActionsService;
	// TODO Change to interface
	// IPersonalActionsService personalActionsService;
	
	public PersonTestHandler(String json){
		if( json==null || json=="" ){
			jsonTestResults = new JSONArray();
		}else{
			try {
				jsonTestResults = new JSONArray(json);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public boolean addQuestions(List<Long> questionsID) {
		boolean res = true;
		try {
			for(Long questionID : questionsID){
				EntityQuestionAttributes questionAttr = personalActionsService.getQuestionAttribubesById(questionID);
				ITestQuestionHandler questionResult = SingleTestQuestionHandlerFactory.getInstance(questionAttr);
				jsonTestResults.put(new JSONObject(questionResult.toJsonString()));
			}
		} catch (Exception e) {
				e.printStackTrace();
				res = false;
		}
		return res;
	}

	private ITestQuestionHandler getInstanceFromJson(JSONObject json){
		ITestQuestionHandler questionResult = SingleTestQuestionHandlerFactory.getInstance(json);
		return questionResult;
	}

	@Override
	public int length() {
		return jsonTestResults.length();
	}

	@Override
	public boolean setAnswer(String answer) {

		//TODO Check
		boolean res = true;
		try {
			JSONObject answerJsonObj = new JSONObject(answer); 
			int index = answerJsonObj.getInt(KEY_INDEX);
			JSONObject jsonObj = jsonTestResults.getJSONObject(index);
			ITestQuestionHandler innerResult = getInstanceFromJson(jsonObj);
			innerResult.setPersonAnswer(answerJsonObj);
			jsonTestResults.put(index, new JSONObject(innerResult.toJsonString()));
		} catch (JSONException e) {
			e.printStackTrace();
			res = false;
		}
		return res;
	}

	@Override
	public boolean analyzeAll() {
		
		boolean res = true;
		for(int i=0, length = jsonTestResults.length(); i < length; i++){
			JSONObject jsonObj = null;
			try {
				jsonObj = jsonTestResults.getJSONObject(i);
				if(jsonObj.get(InnerResultDataObject.KEY_STATUS).equals(InnerResultDataObject.STATUS_NOT_ALALYZED)){
					ITestQuestionHandler testQuestionResult = getInstanceFromJson(jsonObj);
					testQuestionResult.analyze();
					jsonTestResults.put(i, new JSONObject(testQuestionResult.toJsonString()));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	@Override
	public int getRightAnswersQuantity() {
		int res = 0;
		for(int i=0, length = jsonTestResults.length(); i < length; i++){
			JSONObject jsonObj = null;
			try {
				jsonObj = jsonTestResults.getJSONObject(i);
				if(jsonObj.get(InnerResultDataObject.KEY_STATUS).equals(InnerResultDataObject.STATUS_TRUE)){
					res++;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	@Override
	public String getJsonTestResults() {
		return jsonTestResults.toString();
	}

	@Override
	public String next() {
		// TODO Auto-generated method stub
		
		return null;
	}
}
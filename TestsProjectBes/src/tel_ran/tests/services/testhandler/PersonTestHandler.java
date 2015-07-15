package tel_ran.tests.services.testhandler;

import java.util.List;

import javax.persistence.EntityManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.services.inner_result.dataobjects.InnerResultDataObject;
import tel_ran.tests.services.interfaces.IFileManagerService;
import tel_ran.tests.services.subtype_handlers.ITestQuestionHandler;
import tel_ran.tests.services.subtype_handlers.SingleTestQuestionHandlerFactory;
@Component
public class PersonTestHandler implements IPersonTestHandler {
	private JSONArray jsonTestResults;
	public static final String KEY_INDEX = "index";
	
	IFileManagerService fileManager;
	private EntityManager em;
	private long companyId;
	private long testId;
	
	public PersonTestHandler(long companyId, long testId, IFileManagerService fileManager, EntityManager em){
		this.em = em;
		this.fileManager = fileManager;
		this.companyId = companyId;
		this.testId = testId;
		String json = fileManager.getJson(companyId, testId);
		
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
				EntityQuestionAttributes questionAttr = em.find(EntityQuestionAttributes.class, questionID);
				ITestQuestionHandler questionResult = SingleTestQuestionHandlerFactory.getInstance(questionAttr);
				//questionResult.
				
				jsonTestResults.put(new JSONObject(questionResult.toJsonString()));
			}
		} catch (Exception e) {
				e.printStackTrace();
				res = false;
		} finally {
			if(res){
				save();	
			}
		}
		return res;
	}

	private ITestQuestionHandler getInstanceFromJson(JSONObject json){
		ITestQuestionHandler questionResult = SingleTestQuestionHandlerFactory.getInstance(json, companyId, testId);
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
			ITestQuestionHandler testQuestionHandler = getInstanceFromJson(jsonObj);
			testQuestionHandler.setPersonAnswer(answerJsonObj);
			jsonTestResults.put(index, new JSONObject(testQuestionHandler.toJsonString()));
		} catch (JSONException e) {
			e.printStackTrace();
			res = false;
		}
		save();
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
		save();
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
	public String next() {
		String res = null;
		for(int i=0, length = jsonTestResults.length(); i < length; i++){
			try {
				JSONObject jsonObj = jsonTestResults.getJSONObject(i);
				if(jsonObj.get(InnerResultDataObject.KEY_STATUS).equals(InnerResultDataObject.STATUS_NOT_ASKED)){
					ITestQuestionHandler testQuestionResult = getInstanceFromJson(jsonObj);
					res = testQuestionResult.getQuestionJson(i);
					break;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	private void save() {
		fileManager.saveJson(companyId, testId, jsonTestResults.toString());
	}
}
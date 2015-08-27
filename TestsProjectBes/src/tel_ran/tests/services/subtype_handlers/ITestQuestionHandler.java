package tel_ran.tests.services.subtype_handlers;

import org.json.JSONException;
import org.json.JSONObject;

import tel_ran.tests.entitys.EntityQuestionAttributes;

public interface ITestQuestionHandler {
	public void createFromQuestion(long questionId, String metacategory);
	public void fromJsonString(String json, long companyId, long testId);
	public String toJsonString();
	public long getQuestionID();
	public String getStatus();
	public void analyze();
	public boolean setPersonAnswer(JSONObject answerJsonObj);
	public boolean setPersonAnswer(JSONObject answerJsonObj, long etqId);
	public String getQuestionJson(int index);
	public String getQuestionViewResultJson();
	void setEntityQuestionAttributes(EntityQuestionAttributes entityQuestionAttributes);
	void setCompanyId(long companyId); 
	void setTestId(long testId); 
	void setEtqId(long etqId);
	int checkResult();
		
	/**
	 * Returns short version of Questions, that can be used by User or Person in tests
	 * @param eqtId = id on EntityTestQuestion
	 * @return JSONObject
	 * @throws JSONException 
	 */
	public JSONObject getJsonForTest(long eqtId, int index) throws JSONException;
}
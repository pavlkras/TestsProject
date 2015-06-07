package tel_ran.tests.services.subtype_handlers;

import org.json.JSONObject;

public interface ITestQuestionHandler {
	public void createFromQuestion(long questionId, String metacategory);
	public void fromJsonString(String json, long companyId, long testId);
	public String toJsonString();
	public long getQuestionID();
	public String getStatus();
	public void analyze();
	public boolean setPersonAnswer(JSONObject answerJsonObj);
	public String getQuestionJson(int index);
	public String getQuestionViewResultJson();
}
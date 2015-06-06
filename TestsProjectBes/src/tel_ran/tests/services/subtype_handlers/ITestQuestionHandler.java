package tel_ran.tests.services.subtype_handlers;

import org.json.JSONObject;

import tel_ran.tests.entitys.EntityQuestionAttributes;

public interface ITestQuestionHandler {
	public void createFromQuestion(EntityQuestionAttributes question);
	public void fromJsonString(String json);
	public String toJsonString();
	public long getQuestionID();
	public String getStatus();
	public void analyze();
	public boolean setPersonAnswer(JSONObject answerJsonObj);
	public String getQuestionJson(int index);
	public String getQuestionViewResultJson();
}
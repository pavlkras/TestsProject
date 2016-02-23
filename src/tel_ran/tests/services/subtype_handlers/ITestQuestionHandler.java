package tel_ran.tests.services.subtype_handlers;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import tel_ran.tests.entitys.InTestQuestion;
import tel_ran.tests.entitys.Question;


public interface ITestQuestionHandler {

	public void printQuestion();
	public InTestQuestion setPersonAnswer(JSONObject answerJsonObj, InTestQuestion tQuestion) throws JSONException, IOException;
	public String getQuestionJson(int index);
	public String getQuestionViewResultJson();
	
	void setCompanyId(long companyId); 
	void setTestId(long testId); 
	public String getCategoryType();
	int checkResult();
			
	// NEW
	public String getJsonForTest(int index) throws JSONException;
	
	/**
	 * Returns full version of Question with Person's answer
	 * @param entityTestQuestion
	 * @return
	 * @throws JSONException 
	 */
	JSONObject getJsonWithCorrectAnswer(InTestQuestion entityTestQuestion) throws JSONException; 
	
	/**
	 * MANUAL CHECK ANSWERS OF PERSON
	 * save the mark into DB and returns number of a new status of the question
	 * @param mark - String from IPublcStrings.GRADE_OPTIONS[][]
	 * @return
	 */
	int setMark(String mark);
	public void setTestQuestion(InTestQuestion tQuestion);
	public void setQuestion(Question baseQuestion);
}
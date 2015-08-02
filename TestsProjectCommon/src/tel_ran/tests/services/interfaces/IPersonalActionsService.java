package tel_ran.tests.services.interfaces;

import tel_ran.tests.services.fields.ApplicationFinalFields;


public interface IPersonalActionsService extends ICommonService{
	public boolean GetTestForPerson(String pssswordForCreatedTest);	
	public String getNextQuestion(long testId);
	public void setAnswer(long testId, String jsonAnswer);
	public String getToken(String password);
	public boolean testIsPassed(long testId);
	void saveImage(long testId, String image);
}
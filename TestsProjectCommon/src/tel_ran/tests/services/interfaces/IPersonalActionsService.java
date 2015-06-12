package tel_ran.tests.services.interfaces;

import tel_ran.tests.services.fields.ApplicationFinalFields;


public interface IPersonalActionsService extends ApplicationFinalFields{
	public boolean GetTestForPerson(String pssswordForCreatedTest);	
	public String getNextQuestion(long testId);
	public void setAnswer(long testId, String jsonAnswer);
	}

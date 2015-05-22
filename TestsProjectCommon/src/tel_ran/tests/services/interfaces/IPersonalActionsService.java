package tel_ran.tests.services.interfaces;

import tel_ran.tests.services.fields.ApplicationFinalFields;


public interface IPersonalActionsService extends ApplicationFinalFields{
	public String[] GetTestForPerson(String testId);
	// case save result of test for Person
	boolean SaveStartPersonTestParam(String id, String correctAnswers, long timeStartTest);
	boolean SaveEndPersonTestResult(String id, String personAnswers, String personAnswersCode, String imagesOfPerson, String ScreenShoots, long timeEndTest);
	}

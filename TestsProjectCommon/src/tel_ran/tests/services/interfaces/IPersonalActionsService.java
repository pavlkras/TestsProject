package tel_ran.tests.services.interfaces;


public interface IPersonalActionsService {
	public String[] GetTestForPerson(String testId);
	// case save result of test for Person
	boolean SaveStartPersonTestParam(String id, String correctAnswers, long timeStartTest);
	boolean SaveEndPersonTestResult(String id, String personAnswers, String imageLinkText, long timeEndTest);
}

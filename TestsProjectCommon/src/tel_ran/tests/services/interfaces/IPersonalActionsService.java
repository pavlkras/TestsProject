package tel_ran.tests.services.interfaces;

public interface IPersonalActionsService extends ICommonService{
	
	boolean GetTestForPerson(String pssswordForCreatedTest);	
	String getToken(String password);
	void saveImage(long testId, String image);		

	
	/**
	 * method for TEST
	 * save an answer of person and return the next test question if it exists
	 * If questions have been finished it start the process of results checking
	 * @param testId = long
	 * @param answer = JSON
	 * @return JSON string with question
	 */
	String saveAndGetNextQuestion(long testId, String answer);
}
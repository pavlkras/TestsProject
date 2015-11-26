package tel_ran.tests.services.interfaces;

import java.util.List;
import java.util.Map;



/**
 * Interface for Company's services.
 * Includes methods for persons management
 *
 */
public interface ICompanyActionsService extends ICommonAdminService {

	/**

	 */
		
	/**
	 * Return list of all tests created by the company
	 * @return list of JSON-files
	 */
	public List<String> listOfCreatedTest();
	
	/**
	 * Return JSONArray of question details with answers of the person
	 * @param companyId
	 * @param testQuestionId
	 * @return
	 */
	public String getQuestionDetails(long companyId, long testQuestionId);

	/**
	 * Return JSON Array = list of unchecked questions in the given test 
	 * @param companyId
	 * @param testId
	 * @return String (JSON) or "{}", if there aren't unchecked questions in this test
	 */
	public String getListOfUncheckedQuestions(long companyId, long testId);

	//Company actions for 3.1.4. Viewing test results
	public String getTestsResultsAll(long companyId, String timeZone);
	public String getTestsResultsForPersonID(long companyId, int personID, String timeZone);
	public String getTestsResultsForTimeInterval(long companyId, long date_from, long date_until, String timeZone);
	public String getTestResultDetails(long companyId, long testId);
	public String encodeIntoToken(long companyId);
	
	/**
	 * Checks the answer of the person
	 * It receives String = JSON with fields:
	 * -- "mark" = correct or incorrect or other (from gradeOptions)
	 * -- "id" - id of test-q
	 * String = status of the question in the DB or ""
	 * @param companyId
	 * @param mark
	 * @return 
	 */
	public String checkAnswer(long companyId, String mark);
	

	/**
	 * The method returns a list of all custom categories (category1 and category2) that were created by the company
	 * Key of the map is names of 1st level categories (category1) and values - list of sub-categories (category2) that were created
	 * within this category. If no category2 were created, the list will be empty 	 
	 * @param token
	 * @return 
	 */
	Map<String, List<String>> getCompanyCustomCategories(String token);
	
	
}
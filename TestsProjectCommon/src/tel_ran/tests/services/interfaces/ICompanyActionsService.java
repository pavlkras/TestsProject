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
	public String getQuestionDetails(int companyId, long testQuestionId);

	/**
	 * Return JSON Array = list of unchecked questions in the given test 
	 * @param companyId
	 * @param testId
	 * @return String (JSON) or "{}", if there aren't unchecked questions in this test
	 */
	public String getListOfUncheckedQuestions(int companyId, long testId);

	//Company actions for 3.1.4. Viewing test results
	public String getTestsResultsAll(int companyId, String timeZone);
	public String getTestsResultsForPersonID(int companyId, int personID, String timeZone);
	public String getTestsResultsForTimeInterval(int companyId, long date_from, long date_until, String timeZone);
	public String getTestResultDetails(int companyId, long testId);
	public String encodeIntoToken(int companyId);
	

	/**
	 * The method returns a list of all custom categories (category1 and category2) that were created by the company
	 * Key of the map is names of 1st level categories (category1) and values - list of sub-categories (category2) that were created
	 * within this category. If no category2 were created, the list will be empty 	 
	 * @param token
	 * @return 
	 */
	Map<String, List<String>> getCompanyCustomCategories(String token);
	
	
}
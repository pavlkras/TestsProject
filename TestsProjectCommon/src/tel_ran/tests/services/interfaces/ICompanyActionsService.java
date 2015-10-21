package tel_ran.tests.services.interfaces;

import java.util.List;



/**
 * Interface for Company's services.
 * Includes methods for persons management
 *
 */
public interface ICompanyActionsService extends ICommonAdminService {

	/**

	 */
	/**
	 * CREATE TEST FOR PERSON = CREATE TEST
	 * This method can be used if the user (company) have chosen some questions from the list
	 * @param questionIdList - list of question IDs. If the size of the list is less than nQuestions, could be added questions be metaCategory 
	 * @param personId - person ID (link to EntityPerson in DB)
	 * @param pass - password for the new test
	 * @param metaCategories - list of meta categories separated by commas. Can be null if we don't need to generate else questions
	 * @param categories1 - list of categories-1 (prog.lang OR user's category) separated by commas.
	 * @param complexityLevel - list of difficulty level, separated by commas. Can be null if we don't need to generate else questions 
	 * @param nQuestions - total number of questions
	 * @return 0 if the Test was created successfully or the number of possible problems
	 * 1 - not enough questions in the database to generate the test
	 * 3 - not enough data to generate the text. Categories1 should be specified, but the field is null	 * 
	 */
	int createTestFromQuestionList(List<Long> questionIdList, int personId, String pass, String metaCategories, String categories1, 
			String complexityLevel, String nQuestions); 	
	
	
	/**
	 * CREATE TEST FOR PERSON = CREATE PERSON
	 * return personId
	 */
	int createPerson(int personId,String personName,String personSurname,String personEmail); 
	
			
	/**
	 * CREATE TEST FOR PERSON = CREATE LIST OF QUESTIONS (Prog.Task)
	 * @param metaCategory - name of the MetaCategory 
	 * @param category1 - name of Category (Prog.Lang or User's categories)
	 */
	public List<Long> createSetQuestions(String metaCategory, String category1, String level_num, int nQuestion);
	
	/**
	 * CREATE TEST FOR PERSON = CREATE LIST OF QUESTIONS AND TEST
	 * @return 0 if the Test was created successfully or the number of possible problems
	 * 1 - not enough questions in the database to generate the test
	 * 2 - invalid value in the field personEmail
	 * 3 - not enough data to generate the text. Categories1 should be specified, but the field is null	 * 
	 */
	public int createSetQuestiosnAndTest(String metaCategory, String category1, String level_num, String nQuestion, int personId, String pass);
	

	/**
	 * FULL CREATE TEST FOR PERSON with LIST
	 * This function create a new person and a new test for this person. It also forms and sends e-mail to the person
	 * This method can be used if the user (company) have chosen some questions from the list
	 * @param questionIdList - list of question IDs. If the size of the list is less than nQuestions, could be added questions be metaCategory 
	 * @param metaCategories - list of Meta Categories in one String, separated by a comma ("Abstract Reasoning, Attention, ...")
	 * @param categories1 - list of Categories1 in one String, separated by a comma (for Program.Language)
	 * @param difLevel - list of levels of difficulty, separated by a comma 
	 * @param nQuestion - total number of question
	 * @param personPassport - person id, passport
	 * @param personName 
	 * @param personSurname
	 * @param personEmail - required field
	 * @param pass - password for the generated test
	 * @return 0 if the Test was created successfully or the number of possible problems
	 * 1 - not enough questions in the database to generate the test
	 * 2 - invalid value in the field personEmail
	 * 3 - not enough data to generate the text. Categories1 should be specified, but the field is null	 * 
	 */
	public int createTestForPersonFullWithQuestions(List<Long> questionIdList, String metaCategories, String categories1, String difLevel, String nQuestion, int personPassport,
			String personName, String personSurname, String personEmail, String pass);
	
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
	
}
package tel_ran.tests.services.interfaces;

import java.util.List;
import java.util.Map;

import tel_ran.tests.services.fields.ApplicationFinalFields;

public interface ICommonService extends ApplicationFinalFields {
	
	/**
	 * Return list of all possible Meta Categories for auto generated question. 
	 * Warning: these are only potentially possible Meta Categories. The DB can not contain questions of these categories	  
	 */
	List<String> getPossibleMetaCaterories();
	
	/**
	 * Return list of all possible Categories1 (like Prog.Language etc) by Meta Category
	 * Warning: these are only potentially possible Categories. The DB can not contain questions of these categories
	 * @param metaCategory = name of Meta Category 
	 */
	List<String> getPossibleCategories1(String metaCategory);
		
	/**
	 * List of MetaCategories that exist in Data Base
	 * The company will get the list of metaCategory from its part of questions-items (by CompanyId)
	 * The user will get the list of metaCategory from maintenance part (where CompanyId = null)	
	 * The maintenance - the list of metaCategory from whole table without any restrictions.	 
	 */
	List<String> getAllMetaCategoriesFromDataBase(String token);
	
	/**
	 * List of Categories-1 that exist in Data Base	 
	 * The company will get the list of Category-1 from its part of questions-items (by CompanyId)
	 * The user - from maintenance part (where CompanyId = null)	
	 * The maintenance - from whole table without any restrictions.	 
	 */
	List<String> getAllCategories1FromDataBase();
	
	/**
	 * List of Categories-2 that exist in Data Base	 
	 * The company will get the list of Category-2 from its part of questions-items (by CompanyId)
	 * The user - from maintenance part (where CompanyId = null)	
	 * The maintenance - from whole table without any restrictions.	 
	 */
	List<String> getAllCategories2FromDataBase();
	
	/**
	 * List of Categories 1 that exist in DB and have the given MetaCategory
	 * It can be used to get a list of programming languages
	 * The company will get the list of Categories from its part of questions-items (by CompanyId)
	 * The user - from maintenance part (where CompanyId = null)	
	 * The maintenance - from whole table without any restrictions.	
	 * @param metaCategory
	 * @return
	 */
	List<String> getCategories1ByMetaCategory(String metaCategory);
	
	
	/**
	 * List of Categories-1 in metaCategories "American Test" and "Open Question", that exist in Data Base	 
	 * The company will get the list of Category-2 from its part of questions-items (by CompanyId)
	 * The user - from maintenance part (where CompanyId = null)	
	 * The maintenance - from whole table without any restrictions.	 
	 */
	List<String> getUsersCategories1FromDataBase(String token);
	
	
	/**
	 * Returns the question if it's exist and available 
	 * @param questionID
	 * @param actionKey
	 * @return
	 */
	public String[] getQuestionById(String questionID, int actionKey);
	
	/**
	 * Return the question in JSON-format
	 * @param questionId
	 * @return
	 */
	public String getJsonQuestionById(long questionId);
	
	/**
	 * Return the user's information in JSON-format  
	 * Type of user, name, number of questions in the base, number of created tests 
	 * @return
	 */
	String getUserInformation(String token);
	
	
	
	/**
	 * The method returns a list of all MetaCategories and Categories1 for auto-generated questions that exist in DB	 * 
	 * Key of the map is names of MetaCategories and values - list of sub-categories (category1) that were created
	 * within this meta-category. If no category1 were created, the list will be empty 	 
	 * @param token
	 * @return 
	 */
	Map<String, List<String>> getAdminAutoCategories();
	
	/**
	 * The method returns a list of all custom categories that were created by the Administrator 
	 * Key of the map is names of Categories (Category1) and values - list of sub-categories (category2) that were created
	 * within this category. If no category2 were created, the list will be empty 	 
	 * @param token
	 * @return 
	 */
	Map<String, List<String>> getAdminCustomAutoCategories();
	
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
	public int createTestForPersonFullWithQuestions(String token, List<Long> questionIdList, String metaCategories, String categories1, String difLevel, String nQuestion, String personId,
			String personName, String personSurname, String personEmail, String pass);
}

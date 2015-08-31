package tel_ran.tests.services.interfaces;

import java.util.List;

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
	List<String> getAllMetaCategoriesFromDataBase();
	
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
	List<String> getUsersCategories1FromDataBase();
	
	
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
	String getUserInformation();
}

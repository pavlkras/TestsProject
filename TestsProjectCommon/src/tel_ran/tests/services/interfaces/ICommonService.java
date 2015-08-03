package tel_ran.tests.services.interfaces;

import java.util.List;

import tel_ran.tests.services.fields.ApplicationFinalFields;

public interface ICommonService extends ApplicationFinalFields {

	/**
	 * List of MetaCategories that exist in Data Base	 
	 */
	List<String> getAllMetaCategoriesFromDataBase();
	
	/**
	 * List of Categories-1 that exist in Data Base	 
	 */
	List<String> getAllCategories1FromDataBase();
	
	/**
	 * List of Categories-2 that exist in Data Base	 
	 */
	List<String> getAllCategories2FromDataBase();

	
	/**
	 * List of Categories 1 that exist in DB and have the given MetaCategory
	 * It can be used to get a list of programming languages
	 * @param metaCategory
	 * @return
	 */
	List<String> getCategories1ByMetaCategory(String metaCategory);
	
	/**
	 * Returns the question if it's exist and available 
	 * @param questionID
	 * @param actionKey
	 * @return
	 */
	public String[] getQuestionById(String questionID, int actionKey);
}

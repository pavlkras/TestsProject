package tel_ran.tests.services.interfaces;

import java.util.List;

import tel_ran.tests.services.fields.ApplicationFinalFields;

public interface ICommonService extends ApplicationFinalFields {

	List<String> getAllCategories1FromDataBase();
	List<String> getAllCategories2FromDataBase();
	List<String> getAllMetaCategoriesFromDataBase();
	
	/**
	 * Returns the question if it's exist and available 
	 * @param questionID
	 * @param actionKey
	 * @return
	 */
	public String[] getQuestionById(String questionID, int actionKey);
}

package tel_ran.tests.data_loader;

import java.util.List;
import java.util.Map;

import json_models.IJsonModels;
import json_models.QuestionModel;
import tel_ran.tests.entitys.EntityPerson;
import tel_ran.tests.services.fields.Role;

public interface IDataTestsQuestions extends IData {
	int getNumberQuestions(int id, Role role);
	int getNumberTests(int id, Role role);	
	boolean saveNewQuestion(String fileLocationLink, String metaCategory, String category1, String category2, int levelOfDifficulty,
			List<String> answers, String correctAnswerChar, int answerOptionsNumber, String description, String questionText, int id, Role role);
	List<String> getUserCategories(int id, Role role);
	List<IJsonModels> getQuesionsList(Boolean typeOfQuestion, String metaCategory,
			String category1, int id, Role role);
	List<String> getUserMetaCategories(int id, Role role);
	long createPerson(String personPassport, String personName,
			String personSurname, String personEmail);
	List<Long> getQuestionIdByParams(int id, Role role, String string,
			String string2, int parseInt);
	long createTest(String pass, long personId, long startTime, long stopTime,
			List<Long> questionIdList, int companyId, Role role);
	
	/**
	 * returns true if the table with EntityQuestionAttributes is empty.
	 * This method is used for auto-generation of questions whild starting the application	 * 
	 */
	boolean isNoQuestions();
	
	/**
	 * Returns list of category that exist in DB. The method can be used for getting categories of any level 
	 * and sub-categories for the category
	 * @param companyId = id of company that created these categories. If companyId = -1 the list of admin-categories 
	 * (with companyId =null) will be returned
	 * @param catgeryLevel = 0 for metaCategory, 1 for Category1, 2 for Category2.	 * 
	 * @param parent = name of parent. If it is specified the list of subcategories for this category. If it is null and levelOfParent
	 * is "-1" the full list of sub-categories for this level will be got. If levelOfParent is 0 or 1, you will get
	 * the list of sub-categories for parent whose name is null. 
	 * @param levelOfParent - 0-1. Or "-1" if you don't need to search by parent. This allows to search by parent and also by parent of parent. 
	 * @return
	 */
	List<String> getCategories(int companyId, int catgeryLevel, String parent, int levelOfParent);
	
	/**
	 * Returns list of CUSTOM category2 that exist in DB for given Category1 and Company ID (or for Admin)
	 * 
	 * @param category1 - name of category1
	 * @param companyId - id of company. This parameter is not important, if the role = Role.Administrator and can be -1 
	 * @param role - Company or Administrator (value of ENUM Role)
	 * @return List of String array where [0] = name of category2, [1] = name of metaCategory. [0] can be null if
	 * this category1 has some questions without any sub-categories 
	 */
	Map<String, List<String>> getCustomCategories2WithMetaCategory(String category1, int companyId,
			Role role);
	
}

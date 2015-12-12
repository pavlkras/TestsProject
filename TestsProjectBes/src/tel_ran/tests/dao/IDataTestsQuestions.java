package tel_ran.tests.dao;

import java.util.List;
import java.util.Map;

import json_models.CategoriesList;
import json_models.IJsonModels;
import json_models.QuestionModel;
import tel_ran.tests.entitys.EntityPerson;
import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.entitys.EntityTest;
import tel_ran.tests.entitys.EntityTestQuestions;
import tel_ran.tests.entitys.EntityTestTemplate;
import tel_ran.tests.services.fields.Role;
import tel_ran.tests.token_cipher.User;

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
	 * Return list of ALL CUSTOM categories of the given Company or Admin
	 * @param role. Can be equals COMPANY or ADMIN
	 * @param companyId - id of company. For ADMNI can be = -1 or any other number
	 * @return
	 */
	CategoriesList getCategoriesList(Role role, int companyId);
	
	/**
	 * Save new Template
	 * @param entity
	 * @param user 
	 */
	void createTemplate(EntityTestTemplate entity, Role role, long companyId);
	
	/**
	 * Return full list of questions by params
	 * @param metaCategory
	 * @param category1
	 * @param category2
	 * @param difficulty
	 * @param role
	 * @param id
	 * @param isAdmin 
	 * @return
	 */
	List<EntityQuestionAttributes> getQuestionListByParams(String metaCategory,
			String category1, String category2, int difficulty, Role role,
			int id, boolean isAdmin);
	
	/**
	 * Return EntityQuestionAttributes with given ID
	 * @param id
	 * @return
	 */
	EntityQuestionAttributes findQuestionById(Long id);
	
	
	/**
	 * Create new Person 
	 * @param entity
	 * @return id of new element
	 */
	long createPerson(EntityPerson entity);
	long createTest(EntityTest test, List<EntityTestQuestions> questions,
			long personId, Role role, long id);
	EntityTest findTestById(long testId);
	List<EntityQuestionAttributes> getQuestionListByParams(Role role, long id);
	List<EntityTestTemplate> getTemplates(int id);
	
	
	
}

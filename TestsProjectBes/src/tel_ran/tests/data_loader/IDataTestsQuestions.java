package tel_ran.tests.data_loader;

import java.util.List;

import json_models.IJsonModels;
import json_models.QuestionModel;
import tel_ran.tests.entitys.EntityPerson;
import tel_ran.tests.services.fields.Role;

public interface IDataTestsQuestions {
	int getNumberQuestions(long id, Role role);
	int getNumberTests(long id, Role role);	
	boolean saveNewQuestion(String fileLocationLink, String metaCategory, String category1, String category2, int levelOfDifficulty,
			List<String> answers, String correctAnswerChar, int answerOptionsNumber, String description, String questionText, long id, Role role);
	List<String> getUserCategories(long id, Role role);
	List<IJsonModels> getQuesionsList(Boolean typeOfQuestion, String metaCategory,
			String category1, long id, Role role);
	List<String> getUserMetaCategories(long id, Role role);
	long createPerson(String personPassport, String personName,
			String personSurname, String personEmail);
	List<Long> getQuestionIdByParams(long id, Role role, String string,
			String string2, int parseInt);
	long createTest(String pass, long personId, long startTime, long stopTime,
			List<Long> questionIdList, long id, Role role);
	
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
	List<String> getCategories(long companyId, int catgeryLevel, String parent, int levelOfParent);
	
}

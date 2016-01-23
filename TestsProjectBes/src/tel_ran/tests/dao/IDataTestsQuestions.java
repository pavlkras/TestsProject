package tel_ran.tests.dao;

import java.util.List;
import java.util.Set;

import json_models.IJsonModels;
import json_models.TemplateModel;

import tel_ran.tests.entitys.Category;

import tel_ran.tests.entitys.Company;
import tel_ran.tests.entitys.Person;

import tel_ran.tests.entitys.Test;
import tel_ran.tests.entitys.InTestQuestion;
import tel_ran.tests.entitys.TestTemplate;
import tel_ran.tests.entitys.Texts;
import tel_ran.tests.entitys.Question;
import tel_ran.tests.entitys.QuestionCustom;
import tel_ran.tests.entitys.TemplateCategory;
import tel_ran.tests.services.fields.Role;

public interface IDataTestsQuestions extends IData {
	int getNumberQuestions(int id, Role role);
	int getNumberTests(int id, Role role);	
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
	List<Category> getCategoriesList(Role role, int companyId);
	
	/**
	 * Save new Template
	 * @param entity
	 * @param user 
	 */
	void createTemplate(TemplateModel template, Role role, long companyId);
	
	
	Question findQuestionById(Long id);
	
	
	/**
	 * Create new Person 
	 * @param entity
	 * @return id of new element
	 */
	long createPerson(Person entity);
	long createTest(Test test, Set<Question> questions, Person person, Role role, int id);
	Test findTestById(long testId);
//	List<Question> getQuestionListByParams(Role role, long id);
	List<TestTemplate> getTemplates(int id);
	TestTemplate getTemplate(long templateId);
	
	/**
	 * returns true if the table with Categoryes is empty.
	 * This method is used for auto-generation of questions while starting the application	 * 
	 */
	boolean isNoCategory();
	
	/**
	 * returns true if the table with Questions is empty.
	 * This method is used for auto-generation of questions while starting the application	 * 
	 */
	boolean isNoQuestions();
	
	void createCategory(Category cat, int id, Role role);
	List<Category> getAutoCategoriesList();	
	boolean saveNewQuestion(Question qstn, Category category, Company adminCompany, List<Texts> texts);
	boolean saveNewCustomQuestion(QuestionCustom question, List<Texts> texts);
	Category createCategory(Category category, Company company);
	Category getCategory(int categoryId);
	List<Question> getAmericanTestsByParams(Category category, int difficulty, Role role, int id, boolean isAdmin);
	List<Question> getOpenQuestionsByParams(Category category, int difficulty, Role role, int id, boolean isAdmin);
	List<Question> getQuestionsByParams(TemplateCategory tCategory);
	List<Question> getAllQuestionsByParams(Category category, int difficulty, Role role, int id, boolean isAdmin);
	InTestQuestion findTestQuestionById(long tQuestionId);
	void saveAnswer(InTestQuestion tQuestion);
	void saveTest(Test test);
	Question initiateQuestionInTest(InTestQuestion tQuestion);
	TestTemplate getTemplateForResults(long template_id, long id, Role role);
	List<Test> getFinishedTestsForTemplate(TestTemplate template, long id, Role role);
	
	
	
}

package tel_ran.tests.services;



import java.util.List;

import json_models.CategoriesList;
import json_models.JSONKeys;
import json_models.ResultAndErrorModel;
import json_models.PersonModel;
import json_models.QuestionModel;
import json_models.TemplateModel;
import json_models.TestModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;

import tel_ran.tests.dao.CategoryMaps;
import tel_ran.tests.dao.IDataTestsQuestions;
import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.services.common.IPublicStrings;
import tel_ran.tests.services.fields.Role;
import tel_ran.tests.token_cipher.User;

public class TestService implements IService {
	
	@Autowired
	IDataTestsQuestions testQuestsionsData;	
	
	User user;
	
	public TestService() {}
	
	@Override
	public void setUser(User user) {
		this.user = user;
	}
	
	
			
	public String getCategories() {
		CategoriesList catList = testQuestsionsData.getCategoriesList(user.getRole(), user.getRoleNumber());
		String result = "";
		
		try {
			result = catList.getString();
		} catch (JSONException e) {			
			e.printStackTrace();
		}
		
		return result;
	}

	/**
	 * Return list of possible auto categories.
	 * This list is saved in the Memory by CategoryMaps
	 * @return
	 */
	public static String getAutoCategories() {		
		return CategoryMaps.getJsonAutoCategories();
	}
	
	/**
	 * Return list of existing admin categories 
	 * @return
	 */
	public String getAdminCategories() {
		CategoriesList categoriesList = testQuestsionsData.getCategoriesList(Role.ADMINISTRATOR, -1);		
		String result = "";
		
		try {
			result = categoriesList.getString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	
	
	public String createTestAndPerson(String testInfo) {
				
		//0 - read info about save template
		//1 - read info for tests
		TemplateModel template = null;
		try {
			template = new TemplateModel(testInfo);			
		} catch (JSONException e) {
			e.printStackTrace();
			try {
				return ResultAndErrorModel.getJson(IPublicStrings.TEST_SOME_TROUBLE);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		if(template!=null && template.isSaved()) 
			saveTemplate(template);
		
		//2 - read Info for new Person
		PersonModel person = null;
		try {		
			person = new PersonModel(testInfo);
		} catch (JSONException e) {
			e.printStackTrace();			
			try {
				return ResultAndErrorModel.getJson(IPublicStrings.TEST_NOT_CORRECT_PERSON_INFO);
			} catch (JSONException e1) {				
				e1.printStackTrace();
			}			
		}
		
		//3 - create new Test
		template.createNewTest(this);
		template.fillTest(this);
		
		//4 - save Test
		long testId = saveTest(template.getTest(), person);
		
		String result = "";
		try {
			result = ResultAndErrorModel.getResponse(JSONKeys.TEST_ID, testId, IPublicStrings.TEST_SUCCESS);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	private long saveTest(TestModel test, PersonModel person) {
		long personId = testQuestsionsData.createPerson(person.getEntity());			
				
		return testQuestsionsData.createTest(test.getTest(), test.getQuestions(), personId, this.user.getRole(), this.user.getId());		
	}

	private void saveTemplate(TemplateModel template) {
		testQuestsionsData.createTemplate(template.getEntity(), this.user.getRole(), this.user.getId());		
	}

	public List<EntityQuestionAttributes> getAllQuestionsByParams(
			String metaCategory, String category1, String category2, int difficulty) {		
		return this.testQuestsionsData.getQuestionListByParams(metaCategory, category1, category2, difficulty, this.user.getRole(),
				this.user.getId());
	}

	public EntityQuestionAttributes findQuestionById(Long id) {		
		return testQuestsionsData.findQuestionById(id);
	}

	public String sendTestByMail(String link, long testId) {
		//1 - find test by id
		
		TestModel test = new TestModel(testQuestsionsData.findTestById(testId));		
				
		//2 - check test
		if(!this.user.getRole().equals(Role.ADMINISTRATOR) && !(this.user.getRole().equals(Role.COMPANY) && 
				test.getCompanyId() != this.user.getId())) {
				try {
					return ResultAndErrorModel.getJson(IPublicStrings.TEST_NO_RIGHT_TO_SEND);
				} catch (JSONException e) {
					
					e.printStackTrace();
					return "";
				}
		}			
					
		//3 - create Sender
		boolean result;
		
		try {
			result = test.sendMail(link);
		} catch (JSONException e) {
			e.printStackTrace();
			try {
				return ResultAndErrorModel.getJson(IPublicStrings.TEST_NOT_SENDED);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return "";
			}
			
		}
		
		if(!result) {
			try {
				return ResultAndErrorModel.getJson(IPublicStrings.TEST_NOT_SENDED);
			} catch (JSONException e) {			
				e.printStackTrace();
				return "";
			}			
		} else {
			try {
				return ResultAndErrorModel.getAnswer("The mail was sended", 0);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "";
		}				
		
	}

	public String getQuestionsByCompany() {
		
		List<EntityQuestionAttributes> questions = this.testQuestsionsData.getQuestionListByParams(this.user.getRole(),
				this.user.getId());
		
		return getStringFromQuestionList(questions);
	}

	private String getStringFromQuestionList(List<EntityQuestionAttributes> questions) {
		JSONArray jsonArray = new JSONArray();
		for(EntityQuestionAttributes question : questions) {
			try {
				jsonArray.put(new QuestionModel(question).getJSON());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return jsonArray.toString();
	}
	

	
	
	
	
	
	
	


}

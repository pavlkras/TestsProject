package tel_ran.tests.services;



import org.json.JSONException;

import json_models.JSONKeys;
import json_models.PersonModel;
import json_models.ResultAndErrorModel;
import json_models.TemplateModel;
import json_models.TestModel;
import tel_ran.tests.services.common.IPublicStrings;
import tel_ran.tests.services.fields.Role;


public class TestService extends TemplatesService {
	
	
	public TestService() {}
		
	
	@Override
	public String createNewElement(String dataJson) {
				
		//0 - read info about save template
		//1 - read info for tests
		TemplateModel template = null;
		try {
			template = new TemplateModel(dataJson);			
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
			person = new PersonModel(dataJson);
		} catch (JSONException e) {
			e.printStackTrace();			
			try {
				return ResultAndErrorModel.getJson(IPublicStrings.TEST_NOT_CORRECT_PERSON_INFO);
			} catch (JSONException e1) {				
				e1.printStackTrace();
			}			
		}
		
		//3 - create new Test
		QuestionsService service = (QuestionsService) AbstractServiceGetter.getService(user, 
				AbstractServiceGetter.BEAN_QUESTIONS_SERVICE);
		template.createNewTest(service);
		template.fillTest(this.testQuestsionsData);
		
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
	
	
	protected long saveTest(TestModel test, PersonModel person) {
		long personId = testQuestsionsData.createPerson(person.getEntity());			
		long testId = testQuestsionsData.createTest(test.getTest(), test.getQuestions(), personId, this.user.getRole(), this.user.getId());	
		
		person.setTest(test);
		
		return testId;
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

	

	@Override
	public String getAllElements() {
		// TODO Auto-generated method stub
		return null;
	}
	

}

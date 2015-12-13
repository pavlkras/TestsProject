package tel_ran.tests.services;

import java.util.Iterator;

import org.json.JSONException;

import json_models.PersonModel;
import json_models.PersonsList;
import json_models.ResultAndErrorModel;
import json_models.TemplateModel;
import tel_ran.tests.entitys.EntityTestTemplate;
import tel_ran.tests.services.common.IPublicStrings;

public class TestForTemplateService extends TestService {
	
	@Override
	public String createNewElement(String dataJson) {
			
		System.out.println("In SERVICE");		
		
		TemplateModel template = null;
		try {
			template = getTemplateModelById(dataJson);
			
		} catch (JSONException e) {			
			e.printStackTrace();
			try {
				return ResultAndErrorModel.getJson(IPublicStrings.TEST_SOME_TROUBLE);
			} catch (JSONException e1) {
				return "{}";
			}
		}
		System.out.println("Template done");	
		
		PersonsList persons = null;
		try {
			persons = new PersonsList(dataJson, (int)this.user.getId());
		} catch (JSONException e) {			
			e.printStackTrace();
			try {
				return ResultAndErrorModel.getJson(IPublicStrings.TEST_SOME_TROUBLE);
			} catch (JSONException e1) {
				return "{}";
			}
		}
		
		System.out.println("Persons read: " + persons.persons.size());
		
		createTests(persons, template);
				
		sendTestsByMail(persons);
		
		//4 - return response
		String response;
		try {
			response = persons.getString();
		} catch (JSONException e) {			
			e.printStackTrace();
			try {
				response = ResultAndErrorModel.getJson(IPublicStrings.TEST_SOME_TROUBLE);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				response = "{}";
			}
		}
		System.out.println(response);
		return response;
	}
	
	
	private void sendTestsByMail(PersonsList persons) {
		Iterator<PersonModel> it = persons.iterator();
		String path = persons.getPath();
		while(it.hasNext()) {
			System.out.println("SENDING MAIL");
			PersonModel person = it.next();
			person.sendEmail(path);
		}
		
	}





	private void createTests(PersonsList persons, TemplateModel template) {
		System.out.println("Creating tests");
		
		Iterator<PersonModel> it = persons.iterator();
		QuestionsService service = (QuestionsService) AbstractServiceGetter.getService(user, 
				AbstractServiceGetter.BEAN_QUESTIONS_SERVICE);
		
		while(it.hasNext()) {
			System.out.println("In iterator");
			PersonModel person = it.next();			
			template.createNewTest(service);
			template.fillTest(this.testQuestsionsData);
			saveTest(template.getTest(), person);			
		}
	}

	private TemplateModel getTemplateModelById(String dataJson) throws JSONException {
		TemplateModel templateModel = new TemplateModel();
		templateModel.setIdFromJson(dataJson);
				
		EntityTestTemplate template = this.testQuestsionsData.getTemplate(templateModel.getTemplateId());
		
		templateModel.setTemplate(template);
		
		return templateModel;
	}
	
}

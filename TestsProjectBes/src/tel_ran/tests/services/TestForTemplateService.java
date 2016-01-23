package tel_ran.tests.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import json_models.JSONKeys;

import json_models.ResultAndErrorModel;
import tel_ran.tests.entitys.Person;
import tel_ran.tests.entitys.Question;
import tel_ran.tests.entitys.Test;
import tel_ran.tests.entitys.TestTemplate;
import tel_ran.tests.services.common.IPublicStrings;
import tel_ran.tests.services.utils.MailSender;

@Component("testTemplateService")
@Scope("prototype")
public class TestForTemplateService extends TemplatesService {
	
	@Override
	public String createNewElement(String dataJson) {	
		
		List<Test> tests = null;
		try {
			JSONObject jsnInfo = new JSONObject(dataJson);
			
			TestTemplate template = getTemplateFromJson(jsnInfo);
			
			List<Person> persons = getPersonListFromJson(jsnInfo);			
			System.out.println("Persons read: " + persons.size());
			
			String path = getPathFromJson(jsnInfo);
			
			tests = createTests(persons, template, path);
			
			sendTestsByMail(tests);
			
		} catch (JSONException e2) {
			e2.printStackTrace();			
		}
		
		
		String response;
		try {
			response = getResponse(tests);
		} catch (JSONException e) {			
			e.printStackTrace();
			response = "{}";
			
		}
		System.out.println(response);
		return response;
	}
	
	
	private String getResponse(List<Test> tests) throws JSONException {
		if(tests==null)
			return ResultAndErrorModel.getJson(IPublicStrings.TEST_SOME_TROUBLE);
		
		JSONArray array = new JSONArray();
		for(Test t : tests) {
			JSONObject jsn = new JSONObject();
			jsn.put(JSONKeys.PERSON_MAIL, t.getPerson().getPersonEmail());
			jsn.put(JSONKeys.TEST_LINK_TO_SEND, t.getLinkToTest());
			jsn.put(JSONKeys.TEST_WAS_SENT, t.isSendedByMail());
			array.put(jsn);			
		}
		
		return array.toString();
	
	}


	private String getPathFromJson(JSONObject jsnInfo) throws JSONException {
		if(jsnInfo.has(JSONKeys.TEST_LINK_TO_SEND))
			return jsnInfo.getString(JSONKeys.TEST_LINK_TO_SEND);
		return "";
	}


	private List<Person> getPersonListFromJson(JSONObject jsnInfo) throws JSONException {
		List<Person> result = null;
		if(jsnInfo.has(JSONKeys.TEST_FOR_PERSONS)) {
			result = new ArrayList<Person>();
			JSONArray personsJsn = jsnInfo.getJSONArray(JSONKeys.TEST_FOR_PERSONS);
			int personNum = personsJsn.length();
			
			for(int i = 0; i < personNum; i++) {			
				Person person = new Person((JSONObject) personsJsn.get(i));	
				this.testQuestsionsData.createPerson(person);
				result.add(person);
			}			
		}		
		
		return result;
	}


	private TestTemplate getTemplateFromJson(JSONObject jsnInfo) throws JSONException {
		long templateId = jsnInfo.getLong(JSONKeys.TEMPLATE_ID);
		System.out.println("Template ID = " + templateId);
		TestTemplate template = this.testQuestsionsData.getTemplate(templateId);
		return template;
	}


	private boolean sendTestsByMail(List<Test> tests) {
		boolean result = true;
		for(Test t : tests) {
			if(sendMail(t.getPerson().getPersonEmail(), t.getLinkToTest())) {
				t.setSendedByMail(true);
			} else {
				t.setSendedByMail(false);
				result = false;
			}
		}
		return result;
		
	}
	
	private boolean sendMail(String mail, String link) {
		MailSender sender = new MailSender(link);
		boolean result = sender.sendEmail(mail);		
		return result;
	}
	

	private List<Test> createTests(List<Person> persons, TestTemplate template, String path) {
		List<Test> tests = new ArrayList<Test>();
		for(Person p : persons) {
			Test test = createNewTest(p, template, path);	
			tests.add(test);
		}
		return tests;
	}

	private Test createNewTest(Person p, TestTemplate template, String path) {
		Test test = new Test();
		test.setBaseTemplate(template);		
					
		Set<Question> questions = template.getQuestions(this.testQuestsionsData);		
		test.generateLink(path);
		test.setAmountOfQuestions(questions.size());
		
		this.testQuestsionsData.createTest(test, questions, p, user.getRole(), (int)user.getId());
		
		return test;
	}

	
}

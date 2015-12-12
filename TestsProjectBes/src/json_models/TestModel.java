package json_models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tel_ran.tests.dao.IDataTestsQuestions;
import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.entitys.EntityTest;
import tel_ran.tests.entitys.EntityTestQuestions;
import tel_ran.tests.services.TestService;
import tel_ran.tests.services.utils.MailSender;

public class TestModel implements IJsonModels {
	
	Set<Long> questionsId;	
	EntityTest test;
	List<EntityTestQuestions> questions;

	public TestModel() {		
		this.questionsId = new HashSet<Long>();
		this.test = new EntityTest();
		this.test.setPassword(getRandomPassword());
	}
	
	public TestModel(EntityTest findTestById) {
		this.test = new EntityTest();
	}
	
	public int getCompanyId() {
		return this.test.getEntityCompany().getId();
	}

	public String getPassword() {
		return test.getPassword();
	}
	
	public EntityTest getTest() {
		return test;
	}



	private String getRandomPassword() {
		String uuid = UUID.randomUUID().toString();		
		return uuid	;
	}	
	
	
	public Set<Long> getQuestionsId() {
		return questionsId;
	}


	public int addQuestiionsList(List<Long> questionsId) {
		int size = questionsId.size();
		this.questionsId.addAll(questionsId);
		int size2 = this.questionsId.size();
		return size - size2;
	}

	@Override
	public String getString() throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	@Override
	public JSONObject getJSON() throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray getJSONArray() throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean addQuestion(long questionId) {		
		return this.questionsId.add(questionId);
	}


	public void fill(IDataTestsQuestions testQuestsionsData) {
		this.questions = new ArrayList();
		for(Long id : this.questionsId) {
			EntityTestQuestions testQuestions = new EntityTestQuestions();
			testQuestions.setEntityTest(test);
			testQuestions.setEntityQuestionAttributes(testQuestsionsData.findQuestionById(id));
			this.questions.add(testQuestions);
		}		
	}		
	
	public List<EntityTestQuestions> getQuestions() {
		return questions;
	}


	public void setQuestions(List<EntityTestQuestions> list) {
		
	}
		
	public boolean sendMail(String linkJson) throws JSONException {
		StringBuilder txt = new StringBuilder(getPath(linkJson)); 
		txt.append(getPassword());
		
		MailSender sender = new MailSender(txt.toString());
		boolean result = sender.sendEmail(this.test.getEntityPerson().getPersonEmail());
		
		return result;
	}
	
	private String getPath(String linkJson) throws JSONException {
		JSONObject jsn = new JSONObject(linkJson);
		return jsn.getString(JSONKeys.TEST_LINK_TO_SEND);
	}
		

}

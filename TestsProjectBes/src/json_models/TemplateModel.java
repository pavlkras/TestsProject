package json_models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.entitys.EntityTestQuestions;
import tel_ran.tests.entitys.EntityTestTemplate;
import tel_ran.tests.services.TestService;

public class TemplateModel implements IJsonModels {

	EntityTestTemplate template;
	boolean isSaved = false;
	TestModel test;
	int companyId;
	
	List<Long> questionsId;
	List<Map<String, Object>> templates;
	Random ran = new Random();
	
	public TemplateModel(String initiateJson) throws JSONException {
		readJson(initiateJson);
		readTemplateFromJson(initiateJson);		
	}
		
	public void setCompanyId(int id) {
		this.companyId = id;
	}
		
	public int getCompanyId() {
		return companyId;
	}

	private void readJson(String jsnString) throws JSONException {
		JSONObject jsn = new JSONObject(jsnString);
		Boolean isSaved = jsn.getBoolean(JSONKeys.TEMPLATE_SAVE);
		if(isSaved!=null && isSaved) {
			this.isSaved = true;
			template = new EntityTestTemplate();
			String templateName = jsn.getString(JSONKeys.TEMPLATE_NAME);
			if(templateName!=null) template.setTemplateName(templateName);
		}			
	}
	
	public void readTemplateFromJson(String jsnString) throws JSONException {
		JSONObject jsn = new JSONObject(jsnString);
		
		//read questions
		JSONArray jsnQuestions = jsn.getJSONArray(JSONKeys.TEMPLATE_QUESTIONS);
		if(jsnQuestions!=null && jsnQuestions.length()>0) {
			readListOfQuestions(jsnQuestions);			
		}		
				
		//read templates
		JSONArray jsnTemplates = jsn.getJSONArray(JSONKeys.TEMPLATE_CATEGORIES);
		if(jsnTemplates!=null) {
			readListOfTemplates(jsnTemplates);
		}
		
		//save template in Entity
		this.template.setTemplate(questionsId, templates);
		
	}
		
	private void readListOfTemplates(JSONArray jsnTemplates) throws JSONException {
		this.templates = new ArrayList();
		
		int size = jsnTemplates.length();
		
		for(int i = 0; i < size; i++) {
			JSONObject jsn = jsnTemplates.getJSONObject(i);
			if(jsn!=null) {
				Map<String,Object> map = new HashMap<String, Object>();
				String mCategory = jsn.getString(JSONKeys.TEMPLATE_META_CATEGORY);				
				String category1 = jsn.getString(JSONKeys.TEMPLATE_CATEGORY1);
				String category2 = jsn.getString(JSONKeys.TEMPLATE_CATEGORY2);
				int difficulty;
				try {
					difficulty = jsn.getInt(JSONKeys.TEMPLATE_DIFFICULTY);
				} catch (Exception e) {
					difficulty = 0;
					e.printStackTrace();
				}
				int quantaty;
				try {
					quantaty = jsn.getInt(JSONKeys.TEMPLATE_QUANTITY);
				} catch (Exception e) {
					quantaty = 0;
					e.printStackTrace();
				}
				if(mCategory!=null && quantaty > 0) {
					map.put(JSONKeys.TEMPLATE_META_CATEGORY, mCategory);
					map.put(JSONKeys.TEMPLATE_CATEGORY1, category1);
					map.put(JSONKeys.TEMPLATE_CATEGORY2, category2);
					map.put(JSONKeys.TEMPLATE_DIFFICULTY, difficulty);
					map.put(JSONKeys.TEMPLATE_QUANTITY, quantaty);
					this.templates.add(map);
				}
			}			
		}
		
	}

	private void readListOfQuestions(JSONArray questionsJsn) throws JSONException {
		this.questionsId = new ArrayList<Long>();
		
		int size = questionsJsn.length();
		
		for(int i = 0; i < size; i++) {
			JSONObject jsn = questionsJsn.getJSONObject(i);
			if(jsn!=null) {
				long num = jsn.getLong(JSONKeys.TEMPLATE_QUESTION_ID);
				this.questionsId.add(num);
			}			
		}
		
	}
	
	public boolean isSaved() {
		return isSaved;
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
	
	public EntityTestTemplate getEntity() {
		return this.template;
	}
	
	public boolean hasQuestionsList() {
		if(this.questionsId!=null && this.questionsId.size()>0) return true;
		return false;
	}

	public List<Long> getQuestionsId() {
		return questionsId;
	}

	public List<Map<String, Object>> getTemplates() {
		return templates;
	}

	public boolean hasCategoriesMap() {
		if (this.templates!=null && this.templates.size()>0) return true;
		return false;
	}
	
	
	
	public TestModel getTest() {
		return test;
	}

	public EntityTestTemplate getTemplate() {
		return template;
	}

	public void createNewTest(TestService service) {
		this.test = new TestModel();
		
		if(this.hasQuestionsList()) {
			this.test.addQuestiionsList(questionsId);
		}		
		
		if(this.hasCategoriesMap()) {			
			for(Map<String,Object> map : this.templates) {
				createQuestionsSet(map, service);
			}		
		}
				
	}
	
	private void createQuestionsSet(Map<String,Object> map, TestService service) {
		List<EntityQuestionAttributes> questions = service.getAllQuestionsByParams((String)map.get(JSONKeys.TEMPLATE_META_CATEGORY),
				(String)map.get(JSONKeys.TEMPLATE_CATEGORY1), (String)map.get(JSONKeys.TEMPLATE_CATEGORY2),
				(Integer)map.get(JSONKeys.TEMPLATE_DIFFICULTY));				
		int number = (Integer)map.get(JSONKeys.TEMPLATE_QUANTITY);
		
		for(int i = 0; i < number; i ++) {
			if(!this.test.addQuestion(randomQuestion(questions))) {
				i--;
			}			
		}
		
	}

	private long randomQuestion(List<EntityQuestionAttributes> questions) {
		int index = this.ran.nextInt(questions.size());
		return questions.get(index).getId();
	}

	public void fillTest(TestService testService) {
		this.test.fill(testService);		
	}
	
	
	

}

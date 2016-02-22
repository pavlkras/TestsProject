package json_models;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tel_ran.tests.dao.IDataTestsQuestions;
import tel_ran.tests.entitys.Category;
import tel_ran.tests.entitys.TemplateCategory;
import tel_ran.tests.entitys.Test;
import tel_ran.tests.entitys.TestTemplate;

public class TemplateModel implements IJsonModels {

	TestTemplate template;
	boolean isSaved = false;
	Test test;
	int companyId = -1;
	long templateId = -1;
	
	Set<Long> questionsId;
	Set<TemplateCategory> categories;	

		
	IDataTestsQuestions dao;
	
	public TemplateModel(){
		
	}
	
	public TemplateModel(String initiateJson) throws JSONException {
		readJson(initiateJson);			
	}
	
	public Set<Long> getQuestionsId() {
		return this.questionsId;
	}
	
	public Set<TemplateCategory> getCategories(){
		return this.categories;
	}
	
		
	public void setCompanyId(int id) {
		this.companyId = id;
	}
		
	public int getCompanyId() {
		return companyId;
	}

	private void readJson(String jsnString) throws JSONException {
		JSONObject jsn = new JSONObject(jsnString);
				
		//for cases when we want to save template in a process of new test creation
		//we check if we need to save new template in DB or only use it one time for new test generation
		//if we get json from a pfrocess of new template creation, it doesn't contain this data. So we return 
		//true here.
		if(checkIfSaved(jsn)) {
			this.template = new TestTemplate();
			this.template.setTemplate(jsnString);
			readName(jsn);
			readQuestionsList(jsn);
			readCategoriesList(jsn);
			
		}
			
	}

	private void readCategoriesList(JSONObject jsn) throws JSONException {
		if(jsn.has(JSONKeys.TEMPLATE_CATEGORIES)) {
			JSONArray array = jsn.getJSONArray(JSONKeys.TEMPLATE_CATEGORIES);
			readListOfCategories(array);
			
		}
		
	}

	private void readQuestionsList(JSONObject jsn) throws JSONException {
		if(jsn.has(JSONKeys.TEMPLATE_QUESTIONS)) {
			JSONArray array = jsn.getJSONArray(JSONKeys.TEMPLATE_QUESTIONS);
			readListOfQuestions(array);
		}
		
	}



	private void readName(JSONObject jsn) throws JSONException {
		String templateName = jsn.getString(JSONKeys.TEMPLATE_NAME);
		if(templateName!=null) template.setTemplateName(templateName);	
	}


	
	private boolean checkIfSaved(JSONObject jsn) throws JSONException {
		boolean isSaved;
		if(jsn.has(JSONKeys.TEMPLATE_SAVE)) {
			isSaved = jsn.getBoolean(JSONKeys.TEMPLATE_SAVE);
		} else {
			isSaved = true;
		}
		this.isSaved = isSaved;
		return isSaved;
	}
	
		
	private void readListOfCategories(JSONArray jsnTemplates) throws JSONException {
		this.categories = new HashSet<TemplateCategory>();
		
		int size = jsnTemplates.length();
		
		for(int i = 0; i < size; i++) {
			JSONObject jsn = jsnTemplates.getJSONObject(i);
			if(jsn!=null) {
				TemplateCategory templateCategory = new TemplateCategory();
				
				int categoryId = jsn.getInt(JSONKeys.CATEGORY_ID);
				Category category = dao.getCategory(categoryId);
				templateCategory.setCategory(category);
				
				if(jsn.has(JSONKeys.TEMPLATE_SOURCE)) {
					templateCategory.setSource(jsn.getString(JSONKeys.TEMPLATE_SOURCE));
				}
				
				if(jsn.has(JSONKeys.TEMPLATE_TYPE_OF_QUESTION)){
					templateCategory.setTypeOfQuestion(jsn.getString(JSONKeys.TEMPLATE_TYPE_OF_QUESTION));
				}
				

				int difficulty;
				try {
					difficulty = jsn.getInt(JSONKeys.TEMPLATE_DIFFICULTY);
				} catch (Exception e) {
					difficulty = 0;
					e.printStackTrace();
				}				
				templateCategory.setDifficulty(difficulty);
												
				int quantaty;
				try {
					quantaty = jsn.getInt(JSONKeys.TEMPLATE_QUANTITY);
				} catch (Exception e) {
					quantaty = 0;
					e.printStackTrace();
				}
				templateCategory.setQuantity(quantaty);	
				
				this.categories.add(templateCategory);
			}	
			
		}
		
	}

	private void readListOfQuestions(JSONArray questionsJsn) throws JSONException {
		this.questionsId = new HashSet<Long>();
		
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
		
	public boolean hasQuestionsList() {
		if(this.questionsId!=null && this.questionsId.size()>0) return true;		
		return false;
	}

		
	public Test getTest() {
		return test;
	}

	public TestTemplate getTemplate() {
		return template;
	}




	public boolean hasCategories() {
		if(this.categories!=null && this.categories.size()>0) return true;
		
		return false;
	}

	

	public void setIdFromJson(String dataJson) throws JSONException {
		JSONObject jsnOb = new JSONObject(dataJson);
		this.templateId = jsnOb.getLong(JSONKeys.TEMPLATE_ID);
	}

	public long getTemplateId() {
		return templateId;
	}

	public void setTemplate(TestTemplate template2) throws JSONException {
		this.template = template2;		
	}

	public void setDao(IDataTestsQuestions testQuestsionsData) {
		this.dao = testQuestsionsData;		
	}

	public void setData(String dataJson) throws JSONException {
		readJson(dataJson);			
	}
	
	

}

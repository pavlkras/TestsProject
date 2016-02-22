package json_models;


import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tel_ran.tests.entitys.TemplateCategory;
import tel_ran.tests.entitys.TestTemplate;
import tel_ran.tests.entitys.Test;

public class ResultsByTemplateModel implements IJsonModels {
	
	public static final String CELL_NAME_PERSON_NAME = "Name";
	public static final String CELL_NAME_PERSON_LASTNAME = "Surname";
	public static final String CELL_NAME_COMMON_RATE = "Common rate";

	private TestTemplate template;
	private List<Test> tests;
	
	
	public void setTemplate(TestTemplate template) {
		this.template = template;
	}
	
	

	public void setTests(List<Test> tests) {
		this.tests = tests;
	}



	@Override
	public String getString() throws JSONException {
		
		return getJSON().toString();
	}

	@Override
	public JSONObject getJSON() throws JSONException {
		JSONObject result = new JSONObject();
		
		putTemplateInfo(result);
				
		result.put(JSONKeys.RESULTS_HEADER, getHeaderJson());
		
		result.put(JSONKeys.RESULTS_RESULTS, getResultsJson());
		
		return result;
	}

	private JSONArray getResultsJson() {
		JSONArray results = new JSONArray();
		
		for(Test t : this.tests) {			
			results.put(t.getResults());			
		}
		
		return results;
	}

	private void putTemplateInfo(JSONObject result) throws JSONException {
		result.put(JSONKeys.TEMPLATE_NAME, template.getTemplateName());
		result.put(JSONKeys.TEMPLATE_ID, template.getId());
		
	}

	private JSONObject getHeaderJson() throws JSONException {
		JSONObject header = new JSONObject();
		
		header.put(JSONKeys.RESULTS_PERSON_DATA, getPersonDataHeader());
		header.put(JSONKeys.RESULTS_HEADER_COMMON_DATA, getCommonDataHeader());
		header.put(JSONKeys.RESULTS_HEADER_CATEGORIES, getCategoriesHeader());
		
		return header;
	}
	
	private JSONArray getCategoriesHeader() throws JSONException {
		JSONArray categoriesHeader = new JSONArray();
		Set<TemplateCategory> categories = this.template.getCategories();
		
		for(TemplateCategory cat : categories) {
			JSONObject jsn = new JSONObject();
			jsn.put(JSONKeys.RESULTS_HEADER_CELL_NAME, cat.getCategory().getCategoryName());
			categoriesHeader.put(jsn);
		}
		return categoriesHeader;
	}

	private JSONObject getCommonDataHeader() throws JSONException {
		JSONObject dataHeader = new JSONObject();
		dataHeader.put(JSONKeys.RESULTS_HEADER_CELL_NAME, CELL_NAME_COMMON_RATE);
		return dataHeader;
	}

	private JSONObject getPersonDataHeader() throws JSONException {
		JSONObject personHeader = new JSONObject();
		personHeader.put(JSONKeys.RESULTS_HEADER_CELL_NAME, CELL_NAME_PERSON_NAME);
		
		
		return personHeader;
	}

	@Override
	public JSONArray getJSONArray() throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}

}

package json_models;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tel_ran.tests.entitys.TestTemplate;

public class TemplateListModel implements IJsonModels {

	List<TestTemplate> templates;
	
	public TemplateListModel(List<TestTemplate> templatesList) {
		this.templates = templatesList;
	}

	@Override
	public String getString() throws JSONException {
		
		return getJSONArray().toString();
	}

	@Override
	public JSONObject getJSON() throws JSONException {
		
		return null;
	}

	@Override
	public JSONArray getJSONArray() throws JSONException {
		JSONArray result = new JSONArray();
		for(TestTemplate template : templates) {
			JSONObject jsnObj = new JSONObject();
			jsnObj.put(JSONKeys.TEMPLATE_NAME, template.getTemplateName());
			jsnObj.put(JSONKeys.TEMPLATE_ID, template.getId());
			result.put(jsnObj);
		}
		
		return result;
	}

}

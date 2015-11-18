package json_models;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CategoriesList implements IJsonModels {
	
	private Map<String, List<String>> categories;
	private Map<String, Map<String, String>> categoriesWithData;
	
		
	public void setCategoriesWithData(
			Map<String, Map<String, String>> categoriesWithData) {
		this.categoriesWithData = categoriesWithData;
	}

	public void setCategories(Map<String, List<String>> categories) {
		this.categories = categories;
	}

	@Override
	public String getString() throws JSONException {		
		return getJSONArray().toString();
	}

	@Override
	public JSONObject getJSON() throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray getJSONArray() throws JSONException {
		if(categories!=null) return getFromCategories();
		
		return null;
	}
	
	private JSONArray getFromCategories() throws JSONException {
		JSONArray result = new JSONArray();
		
		Set<Map.Entry<String, List<String>>> set = categories.entrySet();
		for(Map.Entry<String, List<String>> e : set) {
			JSONObject jsn = new JSONObject();
			jsn.put(JSONKeys.CATEGORY_PARENT, e.getKey());
			List<String> subCategories = e.getValue();
			
			JSONArray array = new JSONArray();
			if(subCategories!=null) {				
			
			for(String s : subCategories) {
				JSONObject jsn2 = new JSONObject();
				jsn2.put(JSONKeys.CATEGORY_CHILD, s);
				array.put(jsn2);
			}
			}
			jsn.put(JSONKeys.CATEGORY_CHILDREN, array);
			result.put(jsn);		
		}
		return result;
	}
	
	

}

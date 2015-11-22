package json_models;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CategoriesList implements IJsonModels {
	
	private Map<String, List<String>> categories;
	private Map<String, Map<String, List<String>>> categoriesWithData;
	
		
	public void setCategoriesWithData(
			Map<String, Map<String, List<String>>> map) {
		this.categoriesWithData = map;
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
		if(categoriesWithData!=null) return getFromCategoriesWithData();
		return null;
	}
	
	private JSONArray getFromCategoriesWithData() throws JSONException {
		JSONArray result = new JSONArray();
		
		Set<Map.Entry<String, Map<String, List<String>>>> set = categoriesWithData.entrySet();
		for(Map.Entry<String, Map<String, List<String>>> entry : set) {
			JSONObject jsnCategory = new JSONObject();
			jsnCategory.put(JSONKeys.CATEGORY_PARENT, entry.getKey());
			Map<String, List<String>> subCategories = entry.getValue();
			JSONArray childrenArray = new JSONArray();
			if(subCategories!=null) {
				Set<Map.Entry<String, List<String>>> innerSet = subCategories.entrySet(); 
				
				for(Map.Entry<String, List<String>> innerEntry : innerSet) {
					JSONObject jsnSubCategory = new JSONObject();
					jsnSubCategory.put(JSONKeys.CATEGORY_CHILD, innerEntry.getKey());
					JSONArray mc = new JSONArray();
					List<String> mcList = innerEntry.getValue();
					if(mcList!=null) {
						for(String m : mcList) {
							JSONObject jsn = new JSONObject();
							jsn.put(JSONKeys.CATEGORY_MC, m);
							mc.put(jsn);
						}						
					}
					jsnSubCategory.put(JSONKeys.CATEGORY_MC_LIST, mc);
					childrenArray.put(jsnSubCategory);
				}				
			}
			jsnCategory.put(JSONKeys.CATEGORY_CHILDREN, childrenArray);
			result.put(jsnCategory);
		}
		
		return result;
	}

	private JSONArray getFromCategories() throws JSONException {
		JSONArray result = new JSONArray();
		
		Set<Map.Entry<String, List<String>>> set = categories.entrySet();
		for(Map.Entry<String, List<String>> e : set) {
			JSONObject jsn = new JSONObject();
			jsn.put(JSONKeys.CATEGORY_PARENT, e.getKey());
			List<String> subCategories = e.getValue();
			JSONArray array = new JSONArray();
			if(subCategories!=null){
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

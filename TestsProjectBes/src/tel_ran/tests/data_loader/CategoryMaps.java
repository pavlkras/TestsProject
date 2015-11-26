package tel_ran.tests.data_loader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import json_models.CategoriesList;
import json_models.IJsonModels;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tel_ran.tests.processor.TestProcessor;
import tel_ran.tests.services.fields.Role;
import tel_ran.tests.services.utils.SpringApplicationContext;

public final class CategoryMaps {
		
	private static Map<String, List<String>> AUTO_CATEGORIES; 
	private static String AUTO_CATEGORIES_JSON;	
	
	
	static {
		renewAutoCategories();		
	}
	
	public CategoryMaps() {		
	
	}	

	public static String getJsonAutoCategories() {
		return AUTO_CATEGORIES_JSON;
	}
	
	private static void renewAutoCategories() {
		List<String> metaCategories = TestProcessor.getMetaCategory();
		AUTO_CATEGORIES = new HashMap<String, List<String>>();
		for(String s : metaCategories) {
			List<String> categories = TestProcessor.getCategoriesList(s);
			AUTO_CATEGORIES.put(s, categories);			
		}
		try {
			AUTO_CATEGORIES_JSON = getCategoriesJson(AUTO_CATEGORIES);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String getCategoriesJson(Map<String, List<String>> map) throws JSONException{
		CategoriesList jsonModel = new CategoriesList();
		jsonModel.setCategories(map);
		return jsonModel.getString();
	}
	

}

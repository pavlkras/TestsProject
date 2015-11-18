package tel_ran.tests.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import json_models.CategoriesList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tel_ran.tests.processor.TestProcessor;
import tel_ran.tests.token_cipher.User;

public class TestTemplateService {
	
	private static Map<String, List<String>> AUTO_CATEGORIES; 
	private static String AUTO_CATEGORIES_JSON;
	
	static {
		renewAutoCategories();		
		try {
			AUTO_CATEGORIES_JSON = getCategoriesJson(AUTO_CATEGORIES);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public String getCategories(User user) {
		
		return null;
	}

	public static String getAutoCategories() {		
		return AUTO_CATEGORIES_JSON;
	}
	
	
	private static void renewAutoCategories() {
		List<String> metaCategories = TestProcessor.getMetaCategory();
		AUTO_CATEGORIES = new HashMap<String, List<String>>();
		for(String s : metaCategories) {
			List<String> categories = TestProcessor.getCategoriesList(s);
			AUTO_CATEGORIES.put(s, categories);			
		}
	}
	
	private static String getCategoriesJson(Map<String, List<String>> map) throws JSONException{
		if(map==null) System.out.println("NULL");
		CategoriesList jsonModel = new CategoriesList();
		jsonModel.setCategories(map);
		return jsonModel.getString();
	}

}

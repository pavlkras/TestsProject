package tel_ran.tests.data_loader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import json_models.CategoriesList;

import org.json.JSONException;

import tel_ran.tests.processor.TestProcessor;
import tel_ran.tests.services.utils.SpringApplicationContext;

public final class CategoryMaps {
	
	private static CategoryMaps instance;
	private ITestData persistence;
	
	private Map<String, List<String>> AUTO_CATEGORIES; 
	private String AUTO_CATEGORIES_JSON;
	private Map<String, List<Map<String, String>>> ADMIN_CATEGORIES;
	
	private CategoryMaps() {
		renewAutoCategories();
		persistence = (ITestData) SpringApplicationContext.getBean("testData");
	}	
	
	public static CategoryMaps getInstance() {
		if(instance==null) {
			synchronized (CategoryMaps.class) {
				if(instance==null)
					instance = new CategoryMaps();
			}			
		}		
		return instance;		
	}
	
	
	public String getJsonAutoCategories() {
		return this.AUTO_CATEGORIES_JSON;
	}
	
	private void renewAdminCategories() {
		List<String> categories1 = persistence.getCategories1(-1);
	}
	
	private void renewAutoCategories() {
		List<String> metaCategories = TestProcessor.getMetaCategory();
		this.AUTO_CATEGORIES = new HashMap<String, List<String>>();
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
	
	private String getCategoriesJson(Map<String, List<String>> map) throws JSONException{
		CategoriesList jsonModel = new CategoriesList();
		jsonModel.setCategories(map);
		return jsonModel.getString();
	}

}

package tel_ran.tests.categoryLists;

import java.util.Iterator;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import json_models.JSONKeys;
import tel_ran.tests.dao.IDataTestsQuestions;
import tel_ran.tests.entitys.Category;
import tel_ran.tests.entitys.CategoryGenerated;
import tel_ran.tests.testGeneration.TelRanTestGenerator;
import tel_ran.tests.testGeneration.TestGeneration;
import tel_ran.tests.token_cipher.User;

public class GeneratedCategoryList extends CategoriesList {

	public static List<Category> generatedCategory;
	private static String AUTO_CATEGORIES_JSON;	
	
	
	
	
	public GeneratedCategoryList() {		
		this.categories = generatedCategory;
	}
	
	
	@Override
	public void setData(IDataTestsQuestions data, User user) {
		if(generatedCategory==null || generatedCategory.isEmpty()) {		
			renewDataFromDb(data);		
		}
		System.out.println(AUTO_CATEGORIES_JSON);
	}
	
	
	public GeneratedCategoryList(IDataTestsQuestions persistence, boolean forced) {
		if(forced || generatedCategory==null)
			renewDataFromDb(persistence);
		else
			this.categories = generatedCategory;
	}

	private void renewDataFromDb(IDataTestsQuestions persistence) {
		List<Category> list = persistence.getAutoCategoriesList();
		System.out.println("Categories num = " + list.size());
		generatedCategory = list;
		System.out.println("Generated category = " + generatedCategory.size());
		this.categories = list;
		createBaseList();
		try {
			AUTO_CATEGORIES_JSON = this.getString();
			System.out.println(AUTO_CATEGORIES_JSON);
		} catch (JSONException e) {		
			e.printStackTrace();
		}
	}
	

	public String getJsonAutoCategories() throws JSONException {
		if(AUTO_CATEGORIES_JSON==null) {
			CategoriesList list = initiateGeneratedCategories();
			AUTO_CATEGORIES_JSON = list.getString();			
		}
						
		return AUTO_CATEGORIES_JSON;
	}

	public static CategoriesList initiateGeneratedCategories() {
		
		TestGeneration generator = new TelRanTestGenerator();		
		
		CategoriesList result = new GeneratedCategoryList();		
		
		List<String> mCategories = generator.getMetaCategories();		
		
		for(String mc : mCategories) {
			
			CategoryGenerated cat = new CategoryGenerated(mc);
			result.addCategory(cat);
						
			List<String> categories = generator.getCategoriesByMetacategory(mc);
			if(categories != null && categories.size() > 0) {
				cat.setFinal(false);
				
				for(String cat1 : categories) {
					
					CategoryGenerated catChild = new CategoryGenerated(cat, cat1);
					catChild.setFinal(true);
					result.addCategory(catChild);
				}				
			} else {
				cat.setFinal(true);
			}
		}		
		return result;
	}
	
	
	@Override
	public String getString() throws JSONException {	
		if(AUTO_CATEGORIES_JSON!=null && AUTO_CATEGORIES_JSON.length()>5) {
			System.out.println("AUTO_JSON is not null!!!");
			return AUTO_CATEGORIES_JSON;
		}
		return createJsonFromList().toString();
	}

	@Override
	protected JSONArray createJsonFromList() throws JSONException {		
			
		JSONArray result = new JSONArray();
	
		for(Category category : this.parentCategories) {
			JSONObject jsn = new JSONObject();
			jsn.put(JSONKeys.CATEGORY_PARENT, category.getCategoryName());
			jsn.put(JSONKeys.CATEGORY_ID, category.getId());
			List<Category> subcats = null;
			if((subcats = category.getChildrenCategory())!=null) {
				JSONArray array = new JSONArray();
				for(Category c : subcats) {
					JSONObject jsn2 = new JSONObject();
					jsn2.put(JSONKeys.CATEGORY_CHILD, c.getCategoryName());
					jsn2.put(JSONKeys.CATEGORY_ID, c.getId());
					array.put(jsn2);
				}	
				jsn.put(JSONKeys.CATEGORY_CHILDREN, array);
			}
			result.put(jsn);
			
		}
		
		return result;
	}

	@Override
	public Iterator<Category> iterator() {		
		return new CategoryIterator(this.categories);
	}

	@Override
	public int fullSize() {			
		return new CategoryIterator(categories).getSize();
	}

	
	
	
	
	
}

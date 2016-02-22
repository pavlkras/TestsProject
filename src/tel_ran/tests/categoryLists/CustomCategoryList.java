package tel_ran.tests.categoryLists;

import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import json_models.JSONKeys;
import tel_ran.tests.dao.IDataTestsQuestions;
import tel_ran.tests.entitys.Category;
import tel_ran.tests.services.common.IPublicStrings;
import tel_ran.tests.token_cipher.User;

public class CustomCategoryList extends CategoriesList {

	@Override
	public void setData(IDataTestsQuestions data, User user) {		
		this.categories = data.getCategoriesList(user.getRole(), (int)user.getId());
	}

	@Override
	public String getString() throws JSONException {		
		return getJSONArray().toString();
	}
	
	@Override
	protected JSONArray createJsonFromList() throws JSONException {
		JSONArray result = new JSONArray();
		
		for(Category category : this.parentCategories) {
			JSONObject jsnCategory = new JSONObject();
			jsnCategory.put(JSONKeys.CATEGORY_PARENT, category.getCategoryName());
			jsnCategory.put(JSONKeys.CATEGORY_ID, category.getId());
			
											
			JSONArray types = new JSONArray();
			if(category.isContainAmericanTests()) {
				JSONObject jsn = new JSONObject();
				jsn.put(JSONKeys.CATEGORY_MC, IPublicStrings.COMPANY_AMERICAN_TEST);
				types.put(jsn);							
			}
				
			if(category.isContainOpenQuestions()) {
					JSONObject jsn = new JSONObject();
					jsn.put(JSONKeys.CATEGORY_MC, IPublicStrings.COMPANY_QUESTION);
					types.put(jsn);							
			}
			jsnCategory.put(JSONKeys.CATEGORY_MC_LIST, types);
						
			
			List<Category> subcats = null;
			if((subcats = category.getChildrenCategory())!=null) {
				JSONArray array = new JSONArray();
				for(Category c : subcats) {
					JSONObject jsn2 = new JSONObject();
					jsn2.put(JSONKeys.CATEGORY_CHILD, c.getCategoryName());
					jsn2.put(JSONKeys.CATEGORY_ID, c.getId());
					array.put(jsn2);
					
					JSONArray types2 = new JSONArray();
					if(c.isContainAmericanTests()) {
						JSONObject jsn = new JSONObject();
						jsn.put(JSONKeys.CATEGORY_MC, IPublicStrings.COMPANY_AMERICAN_TEST);
						types2.put(jsn);							
					}
						
					if(c.isContainOpenQuestions()) {
						JSONObject jsn = new JSONObject();
						jsn.put(JSONKeys.CATEGORY_MC, IPublicStrings.COMPANY_QUESTION);
						types2.put(jsn);							
					}
					
					jsn2.put(JSONKeys.CATEGORY_MC_LIST, types2);
										
					array.put(jsn2);
				}	
				jsnCategory.put(JSONKeys.CATEGORY_CHILDREN, array);
			}
			result.put(jsnCategory);
		}
				
		return result;
	}

	@Override
	public Iterator<Category> iterator() {
		
		return this.categories.iterator();
	}

	@Override
	public int fullSize() {		
		return this.categories.size();
	}
	


}

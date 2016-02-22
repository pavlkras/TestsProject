package tel_ran.tests.categoryLists;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import json_models.IJsonModels;
import tel_ran.tests.dao.IDataTestsQuestions;
import tel_ran.tests.entitys.Category;
import tel_ran.tests.services.fields.Role;
import tel_ran.tests.token_cipher.User;

public abstract class CategoriesList implements IJsonModels, Iterable<Category> {
		
	List<Category> categories;
	List<Category> parentCategories;	

	
	
	public abstract void setData(IDataTestsQuestions data, User user); 
	
	public void addCategory (Category category) {
		if(this.categories==null) this.categories = new ArrayList<Category>();
		this.categories.add(category);
	}	
	
	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}


	

	@Override
	public JSONObject getJSON() throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray getJSONArray() throws JSONException {
		
		if(this.parentCategories==null) createBaseList();		

		return createJsonFromList();
	}
	
	abstract protected JSONArray createJsonFromList() throws JSONException;

	protected void createBaseList() {
		this.parentCategories = new ArrayList<>();
		for(Category cat : this.categories) {
			if(cat.getParentCategory()==null)
				this.parentCategories.add(cat);			
		}	
		System.out.println("Parent list = " + this.parentCategories.size());
	}

	public void createAllCategories(int id, Role role, IDataTestsQuestions persistence) {
		for(Category cat : this.categories) {
			persistence.createCategory(cat, id, role);
		}	
		
	}
	
	public abstract int fullSize();

	public List<String> getSimpleTopList() {
		if(parentCategories==null) createBaseList();
		List<String> result = new ArrayList<String>();
		for(Category cat : this.parentCategories) {
			result.add(cat.getCategoryName());
		}
		return result;
	}

}

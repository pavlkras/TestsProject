package tel_ran.tests.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;





import json_models.CategoriesList;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;

import tel_ran.tests.data_loader.CategoryMaps;
import tel_ran.tests.data_loader.IDataTestsQuestions;
import tel_ran.tests.data_loader.ITestData;
import tel_ran.tests.data_loader.TestsPersistence;
import tel_ran.tests.processor.TestProcessor;
import tel_ran.tests.token_cipher.User;

public class TestTemplateService  {
	
	@Autowired
	IDataTestsQuestions testQuestsionsData;	
	@Autowired
	ITestData testData;
			
	public String getCategories(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String getAutoCategories() {	
		CategoryMaps cm = CategoryMaps.getInstance();
		
		return CategoryMaps.getInstance().getJsonAutoCategories();
	}
	
	
	
	
	

	public String getAdminCategories() {
		
		
		
		return null;
	}


}

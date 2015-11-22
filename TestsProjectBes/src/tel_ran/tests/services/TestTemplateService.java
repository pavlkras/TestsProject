package tel_ran.tests.services;



import org.springframework.beans.factory.annotation.Autowired;

import tel_ran.tests.data_loader.CategoryMaps;
import tel_ran.tests.data_loader.IDataTestsQuestions;
import tel_ran.tests.token_cipher.User;

public class TestTemplateService  {
	
	@Autowired
	IDataTestsQuestions testQuestsionsData;	
	
			
	public String getCategories(User user) {
		
		return null;
	}

	public static String getAutoCategories() {
		
		return CategoryMaps.getInstance().getJsonAutoCategories();
	}
	
	public String getAdminCategories() {
				
		return CategoryMaps.getInstance().getJsonAdminCategories();
	}


}

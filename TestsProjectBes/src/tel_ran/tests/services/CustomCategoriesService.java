package tel_ran.tests.services;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;

import json_models.CategoriesList;
import tel_ran.tests.dao.IDataTestsQuestions;

public class CustomCategoriesService extends AbstractService {

	@Autowired
	IDataTestsQuestions testQuestsionsData;	
	
	@Override
	public String getAllElements() {
		CategoriesList catList = testQuestsionsData.getCategoriesList(user.getRole(), user.getRoleNumber());
		String result = "";
		
		try {
			result = catList.getString();
		} catch (JSONException e) {			
			e.printStackTrace();
		}		
		return result;		
	}

	@Override
	public String createNewElement(String dataJson) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}

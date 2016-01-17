package tel_ran.tests.services;

import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import tel_ran.tests.categoryLists.CategoriesList;
import tel_ran.tests.dao.IDataTestsQuestions;


public class CategoriesService extends AbstractService {

	CategoriesList categoriesList;
	
	@Autowired
	IDataTestsQuestions testQuestsionsData;	
			
	public void setCategoriesList(CategoriesList categoriesList) {
		this.categoriesList = categoriesList;
	}

	/**
	 * Return list of possible categories.
	 * @return
	 */
	@Override
	public String getAllElements() {		
		this.categoriesList.setData(this.testQuestsionsData, this.user);
		try {
			return categoriesList.getString();
		} catch (JSONException e) {		
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String createNewElement(String dataJson) {
		throw new RuntimeException();
	}

	@Override
	public List<String> getSimpleList() {
		this.categoriesList.setData(this.testQuestsionsData, this.user);
			return this.categoriesList.getSimpleTopList();
	}

	@Override
	public String getInformation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getElement(String params) {
		// TODO Auto-generated method stub
		return null;
	}
	

}

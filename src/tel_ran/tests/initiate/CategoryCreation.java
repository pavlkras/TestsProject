package tel_ran.tests.initiate;


import java.util.Properties;

import tel_ran.tests.categoryLists.CategoriesList;
import tel_ran.tests.categoryLists.GeneratedCategoryList;
import tel_ran.tests.dao.IDataTestsQuestions;
import tel_ran.tests.services.fields.Role;



public class CategoryCreation extends DataCreation {

	public static final String NAME = "CATEGORIES";
	
	public CategoryCreation(IDataTestsQuestions tp) {
		this.persistence = tp;	
	}
	
	@Override
	void fill() {
		CategoriesList categories = GeneratedCategoryList.initiateGeneratedCategories();
		categories.createAllCategories(-1, Role.ADMINISTRATOR, (IDataTestsQuestions)this.persistence);			
	}

	@Override
	void setProperties(Properties properties) {		
	}

	@Override
	public boolean isNeedToFill() {
		return ((IDataTestsQuestions)this.persistence).isNoCategory();	
		
	}

	@Override
	public String getName() {		
		return NAME;
	}

}

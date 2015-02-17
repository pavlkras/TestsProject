package tel_ran.tests.services.interfaces;

import java.util.List;

public interface IPersonalActionsService {

	 List<String> getCategoriesList();
	 List<String> getComplexityLevelList();
	 String getMaxCategoryLevelQuestions(String catName, String complexityLevel);
}

package tel_ran.tests.services.interfaces;

import java.util.List;

public interface IPersonalActionsService {

	 List<String> getCategoriesList();
	 String getMaxCategoryQuestions(String catName);
	 int testCreationService(String userId);
	 boolean saveUserService(String[] args);
	 String[] loadUserservice(String userId);
	 int testCreationByCategory(String userId, String category, int level, int qAmount);
	 String[] loadTestService(int id);
	 List<Integer> getLevelsList();
	 String loadXMLTest(int id);
}

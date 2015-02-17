package tel_ran.tests.services.interfaces;

import java.util.List;

public interface IPersonalActionsService {
	
	boolean saveUserService(String[] args);
	String[] loadUserservice(String userId);
	List<String> getCategoriesList();
	String getMaxCategoryQuestions(String catName, String level);
	String loadXMLTest(int id);
	String getTraineeQuestions(String category, int level, int qAmount);
	List<String> getComplexityLevelList();
	String getMaxCategoryLevelQuestions(String catName, String complexityLevel);
}

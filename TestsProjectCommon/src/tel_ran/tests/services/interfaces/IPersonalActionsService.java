package tel_ran.tests.services.interfaces;

import java.util.List;

public interface IPersonalActionsService {
	
	static final int ID = 0;
	static final int NAME = 1;
	static final int PASSWORD = 2;
	static final int EMAIL = 3;
	static final int USER_FIELDS = 5; 
	static final int TEST_FIELDS = 5;
	static final int TEST_ID = 0;
	
	boolean saveUserService(String[] args);
	String[] loadUserservice(String userId);
	List<String> getCategoriesList();
	String getMaxCategoryQuestions(String catName, String level);
	String loadXMLTest(int id);
	String getTraineeQuestions(String category, int level, int qAmount);
	List<String> getComplexityLevelList();
	String getMaxCategoryLevelQuestions(String catName, String complexityLevel);
}

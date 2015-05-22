package tel_ran.tests.services.interfaces;

import java.util.List;

import tel_ran.tests.services.fields.ApplicationFinalFields;

public interface IUserActionService extends ApplicationFinalFields{	
		boolean AddingNewUser(String[] args);
		public String[] GetUserByMail(String userMail);
		boolean IsUserExist(String userMail,String userPassword);
		////
		public List<String> getCategoriesList();
		public String getMaxCategoryQuestions(String catName, String level);		
		public List<String> getTraineeQuestions(String category, int level, int qAmount);
		public List<String> getComplexityLevelList();
		public String getMaxCategoryLevelQuestions(String catName, String complexityLevel);
		public String TestCodeQuestionUserCase(String codeText);
}

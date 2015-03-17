package tel_ran.tests.services.interfaces;

import java.util.List;

public interface IMaintenanceService {
	public static final String DELIMITER = "----";// delimiter for entity's and filling from file
	//
	boolean setAutorization(String username, String password);
	//
	boolean ModuleForBuildingQuestions(String category,int nQuestion);// Auto Generation many questions by any parameters	
	//
	boolean CreateNewQuestion(String imageLink, String questionText, String category, int levelOfDifficulti, List<String> answers, char correctAnswer,int id);
	//
	public List<String> SearchAllQuestionInDataBase(String category, int levelOfDifficulti);
	//
	public String getQuestionById(String questionID);
	//
	public List<String> getAllCategoriesFromDataBase();
	//
	boolean UpdateTextQuestionInDataBase(String questionID, String imageLink, String questionText, String category, int levelOfDifficulti, List<String> answers, char correctAnswer);
	//
	public String deleteQuetionById(String questionID);
	//
	boolean FillDataBaseFromTextResource(List<String> inputParsedText);	

	// method for group generate test. AlexFoox returned Long ID of question 
	public List<Long> getUniqueSetQuestionsForTest(String category,String level,Long nQuestion);
}

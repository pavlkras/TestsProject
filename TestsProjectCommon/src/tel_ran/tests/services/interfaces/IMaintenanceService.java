package tel_ran.tests.services.interfaces;
import java.util.List;

import tel_ran.tests.services.fields.ApplicationFinalFields;

public interface  IMaintenanceService extends ApplicationFinalFields{
	//
	boolean setAutorization(String username, String password);
	//
	boolean ModuleForBuildingQuestions(String category,int nQuestion);// Auto Generation many questions by any parameters	
	//
	boolean CreateNewQuestion(String imageLink, String questionText, String category, int levelOfDifficulty, List<String> answers, char correctAnswer,int questionNumber, int numberOfResponsesInThePicture);
	//
	public List<String> SearchAllQuestionInDataBase(String category, int levelOfDifficulty);
	//
	public String[] getQuestionById(String questionID, int actionKey);
	//
	public List<String> getAllCategoriesFromDataBase();
	//
	boolean UpdateTextQuestionInDataBase(String questionID, String imageLink, String questionText, String category, int levelOfDifficulty, List<String> answers, char correctAnswer);
	//
	public String deleteQuetionById(String questionID);
	//
	boolean FillDataBaseFromTextResource(List<String> inputParsedText);	
	// method for group generate test. AlexFoox returned Long ID of question 
	public List<Long> getUniqueSetQuestionsForTest(String category,String levelMin, String levelmax, Long nQuestion);
}

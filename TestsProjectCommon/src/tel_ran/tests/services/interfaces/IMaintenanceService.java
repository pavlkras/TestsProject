package tel_ran.tests.services.interfaces;
import java.util.List;

import tel_ran.tests.services.fields.ApplicationFinalFields;

public interface  IMaintenanceService extends ApplicationFinalFields{
	//
	boolean setAutorization(String username, String password);
	//
	boolean ModuleForBuildingQuestions(String byCategory, int diffLevel, int nQuestions);// Auto Generation many questions by any parameters	
	//
	boolean  CreateNewQuestion(String questionText,String fileLocationLink, String metaCategory, String category, int levelOfDifficulty, List<String> answers, 
			String correctAnswer,int questionNumber, int numberOfResponsesInThePicture,	String description, String codeText, String languageName);
	//
	public List<String> SearchAllQuestionInDataBase(String category, int levelOfDifficulty);
	//
	public String[] getQuestionById(String questionID, int actionKey);
	//
	public List<String> getAllCategoriesFromDataBase();
	//
	boolean UpdateTextQuestionInDataBase(String questionID, 
			String questionText, String descriptionText, String codeText, String languageName, 
			String metaCategory, String category, int levelOfDifficulty, List<String> answers, String correctAnswer, 
			String fileLocationPath, String numAnswersOnPictures);
	//
	public String deleteQuetionById(String questionID);
	//
	boolean FillDataBaseFromTextResource(List<String> inputParsedText);	
	//
	public List<String> GetGeneratedExistCategory();
	// method for group generate test. AlexFoox returned Long ID of question 
	public List<Long> getUniqueSetQuestionsForTest(String category,String level_num, Long nQuestion);
}

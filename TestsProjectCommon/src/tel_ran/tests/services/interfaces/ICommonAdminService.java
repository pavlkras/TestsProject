package tel_ran.tests.services.interfaces;

import java.util.List;

/**
 * Interface for Administrator's services. It's the parent interface for ICompanyActionsService
 * Includes authorization and questions management
 * 
 */
public interface ICommonAdminService extends ICommonService {	
	
	public boolean setAutorization(String username, String password);
	
	/**
	 * Method searches Company by Name in the DB. Returns ID of the Company if the DB contains its name.
	 * @param companyName - Company name should be exactly the same that was specified before but 
	 * register does not matter.  
	 * @return ID of the Company or -1 if the company hasn't been found 
	 */
	long getCompanyByName(String companyName);	
	
	/**
	 * Creation of the new Company. 
	 * All the field must be less then 255 characters. 
	 * @param C_Name = Name of the Company. Must be unique. The Company can be found by this name. Required field.
	 * @param C_Site = web-site of the Company
	 * @param C_Specialization = info about the Company. Need only for future statistics. 
	 * @param C_AmountEmployes = info about the Company. Need only for future statistics. 
	 * @param C_Password = Password. 
	 * @return
	 */
	boolean CreateCompany(String C_Name,String C_Site, String C_Specialization,String C_AmountEmployes,String C_Password);
	
	
	/**
	 * Auto-generation new test questions by Meta-Category (Quantative, Abstract Reasoning, Programming Task etc.)
	 * This method fills the DB by new test-questions generated by Test Generation application. 	 *  
	 * @param byMetaCategory - one of the MetaCategory. Should contain one of the done Test Generation constants. 
	 * @param diffLevel - maximum level of difficulty. There will be generated questions of this level and lower. 
	 * For Example: for level "3" - questions of the level 1, 2 and 3.   
	 * @param nQuestions - number of the questions to generate
	 * @return true if the generation has been succeed and false if there were some problems
	 */
	boolean ModuleForBuildingQuestions(String byMetaCategory, int diffLevel, int nQuestions);// Auto Generation many questions by any parameters	
	
	
	/**
	 * Auto-generation new test questions by Meta-Category (Quantative, Abstract Reasoning, Programming Task etc.)
	 * This method fills the DB by new test-questions generated by Test Generation application. 	 *  
	 * @param byMetaCategory - one of the MetaCategory. Should contain one of the done Test Generation constants. 
	 * @param category1 - programming language for MetaCategory Programming_Task
	 * @param diffLevel - maximum level of difficulty. There will be generated questions of this level and lower. 
	 * For Example: for level "3" - questions of the level 1, 2 and 3.   
	 * @param nQuestions - number of the questions to generate
	 * @return true if the generation has been succeed and false if there were some problems
	 */
	boolean ModuleForBuildingQuestions(String byMetaCategory, String category1, int diffLevel, int nQuestions);// Auto Generation many questions by any parameters	
	
	
	/**
	 * Manual creation of the new question - old version
	 * @param questionText
	 * @param fileLocationLink
	 * @param metaCategory
	 * @param category
	 * @param levelOfDifficulty
	 * @param answers
	 * @param correctAnswer
	 * @param questionNumber
	 * @param numberOfResponsesInThePicture
	 * @param description
	 * @param codeText
	 * @param languageName
	 * @return
	 */
	boolean  CreateNewQuestion(String questionText,String fileLocationLink, String metaCategory, String category, int levelOfDifficulty, List<String> answers, 
			String correctAnswer,int questionNumber, int numberOfResponsesInThePicture,	String description, String codeText, String languageName);

	/**
	 * Method for the manual creation of a new Question for Open Question Form (UserTest)  
	 * MetaCategory will be auto specified = "C Open Question"	 
	 * QuestionTest will be specified automatically  
	 * @param category1 - Category created by Company or Administrator
	 * @param levelOfDifficulty - Level of Difficulty from 1 to 5	
	 * @param description - text of the question or task (should be less than 2000 characters)
	 * @return true if the question has been created successfully
	 */
	boolean  CreateNewQuestion(String category1, int levelOfDifficulty, String description);
	
	/**
	 * Method for the manual creation of a new Question for American Test Form (UserTest)  	 * 
	 * MetaCategory will be auto specified = "C American Test"	
	 * QuestionTest will be specified automatically 	  
	 * 
	 * @param category1 - Category created by Company or Administrator
	 * @param levelOfDifficulty - Level of Difficulty from 1 to 5	
	 * @param answerOptions - List of answer options
	 * @param correctAnswer - Letter of the correct answer 	  
	 * @param numberOfResponsesInThePicture
	 * @param description - text of the question or task (should be less than 2000 characters)
	 * @param fileLocationLink - link to the picture that can be added to the Question	
	 * @return true if the question has been created successfully
	 */
	boolean  CreateNewQuestion(String category, int levelOfDifficulty, List<String> answerOptions, 
			String correctAnswer, String description, String fileLocationLink);

	
	/**
	 * Returns all available questions in the DB by Categories.	 *  
	 * If all the parameters are null  the method returns full list of available question
	 * @param metaCategory - name of the MetaCategory
	 * @param category1 - name of language for Programming task or of user category     
	 * @param category2 - name of sub-category (like Calculator)
	 * @param levelOfDifficulty
	 * @return 
	 */
	public List<String> SearchAllQuestionInDataBase(String metaCategory, String category1, String category2, int levelOfDifficulty);
	
	public String deleteQuetionById(String questionID);
	
	
	
	// ???? 
	boolean UpdateTextQuestionInDataBase(String questionID, 
			String questionText, String descriptionText, String codeText, String languageName, 
			String metaCategory, String category, int levelOfDifficulty, List<String> answers, String correctAnswer, 
			String fileLocationPath, String numAnswersOnPictures);	
	
	/**
	 * Returns list of all possible Meta Categories for auto generated question. 
	 * Warning: these are only potentially possible Meta Categories. The DB can not contain questions of these categories 
	 * @return list of the Meta Category name
	 */
	public List<String> GetGeneratedExistCategory();
	
	// method for group generate test. AlexFoox returned Long ID of question 
	public List<Long> getUniqueSetQuestionsForTest(String category,String level_num, Long nQuestion);
	

}

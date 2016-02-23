package tel_ran.tests.services.interfaces;

import java.util.List;

/**
 * Interface for Administrator's services. It's the parent interface for ICompanyActionsService
 * Includes authorization and questions management
 * 
 */
public interface ICommonAdminService extends ICommonService {	
			
	/**
	 * Manual creation of the new question - USED in FES   !!!!!!!!!!!!!!!!!!!!!!!!!!
	 * @param token = token
	 * @param jsonQuestion = question in JSON format
	 * @return
	 */
	boolean createNewQuestion(String token, String jsonQuestion);

	
	/**
	 * Returns all available questions in the DB by Categories.	 *  
	 * If all the parameters are null  the method returns full list of available question
	 * @param metaCategory - name of the MetaCategory
	 * @param category1 - name of language for Programming task or of user category     
	 * @param category2 - name of sub-category (like Calculator)
	 * @param levelOfDifficulty
	 * @return 
	 */
	public List<String> searchAllQuestionInDataBase(String metaCategory, String category1, String category2, int levelOfDifficulty);
	
	public String deleteQuetionById(String questionID);
	
	
	/**
	 * Returns full questions list in JSON format.
	 * 
	 * @param typeOfQuestion - required field. Use constants ICommonData.TYPE_OF_QUESTION_USERS for user metaCategory 
	 * (American test open question) and ICommonData.TYPE_OF_QUESTION_AUTO for auto cat.
	 * @param metaCategory - can be specified to get list of question only from this metaCategory. Should match
	 * with typeOfQuestion.
	 * @param category1 - can be specified to get list of question only from given Category1. Should match
	 * with typeOfQuestion and metaCategory if they are specified.
	 * @return
	 */
	String getAllQuestionsList(String token, Boolean typeOfQuestion, String metaCategory, String category1);	
		

}

package tel_ran.tests.services.testhandler;

import java.util.List;

public interface IPersonTestHandler {
	//fills json with prepared structure for retrieving questions
//	public boolean addQuestions(List<Long> questionsID); 

	//Puts answer for some index
	public boolean setAnswer(String jsonAnswer);
	
	//Runs the check of questions whether the given answers are true or false
//	public boolean analyzeAll(); 

	//Returns number of question records
//	public int length();
	
	//Returns next unanswered question and index in json format or null if all questions are already answered
//	public String next();
	
	//Returns number of right answers
//	public int getRightAnswersQuantity();

	//Returns status of the question with some id
//	public String getStatus(int index);  
}
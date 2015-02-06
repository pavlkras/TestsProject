package tel_ran.tests.services.interfaces;

import java.util.List;

public interface IMaintenanceService {//общий интерфейс 
	//Сюда добавляем свои методы для работы с ними через веб. 
	boolean createQuestion(String questionText,String descriptionText,String category,int level,List<String> answers,int trueAnswerNumber);
	public String getInformation(String questionKey);
	boolean FillDataBaseFromTextResource(List<String> inputParsedText);
	String SearchQuestionInDataBase(String question, String category);
	String UpdateQuestionInDataBase(String questionID, String questionText,
			String descriptionText, String category, int question_level,
			List<String> answer, int trueAnswerNumber);
	// method for group generate test
	List<String> GeneratedTestQuestion(String category,String level);
}

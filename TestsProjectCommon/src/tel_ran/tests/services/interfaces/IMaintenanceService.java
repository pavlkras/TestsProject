package tel_ran.tests.services.interfaces;

import java.util.List;

public interface IMaintenanceService {//общий интерфейс 
	//Сюда добавляем свои методы для работы с ними через веб. 
	//ВАЖНО ЭТО ИНТЕРФЕЙС !!!-ТЕСТ-!!! при изменениях ихменить инрерфейс !!!-СЕРВИС-!!!.

	boolean createQuestion(String questionText,String descriptionText,String category,int level,List<String> answers,int trueAnswerNumber);
	public String getInformation(String questionKey);
	boolean AddQuestionsFromFile(String FileName);
	String SearchQuestionInDataBase(String question, String category);
	String UpdateQuestionInDataBase(String questionID, String questionText,
			String descriptionText, String category, int question_level,
			List<String> answer, int trueAnswerNumber);
}

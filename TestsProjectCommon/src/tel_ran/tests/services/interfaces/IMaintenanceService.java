package tel_ran.tests.services.interfaces;

import java.util.List;

public interface IMaintenanceService {//общий интерфейс 
	//Сюда добавляем свои методы для работы с ними через веб. 
	boolean createQuestion(String questionText,String descriptionText,String category,int level,List<String> answers,int trueAnswerNumber);
	//Метод обновляет вопрос в БД
	boolean UpdateQuestionInDataBase(String questionID, String questionText,
			String descriptionText, String category, int question_level,
			List<String> answer, int trueAnswerNumber);
	//Поиск по БД возвращает Лист Стрингов : принимает 2 параметра но поиск идет по question
	public List<String> SearchQuestionInDataBase(String question, String category);
	//Метод добавления данных в БД из файла на удаленном компьютере
	boolean FillDataBaseFromTextResource(List<String> inputParsedText);		
	//Возвращает 1 вопрос с ответами в стринге: поиск по ID Question
	public String getInformation(String questionKey);
	// method for group generate test. AlexFoox
	public List<String> generatedTestQuestion(String category,String level);
}

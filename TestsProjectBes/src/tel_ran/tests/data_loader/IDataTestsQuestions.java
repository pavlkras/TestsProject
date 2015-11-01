package tel_ran.tests.data_loader;

import java.util.List;

import json_models.IJsonModels;
import json_models.QuestionModel;
import tel_ran.tests.entitys.EntityPerson;
import tel_ran.tests.services.fields.Role;

public interface IDataTestsQuestions {
	int getNumberQuestions(long id, Role role);
	int getNumberTests(long id, Role role);	
	boolean saveNewQuestion(String fileLocationLink, String metaCategory, String category1, String category2, int levelOfDifficulty,
			List<String> answers, String correctAnswerChar, int answerOptionsNumber, String description, String questionText, long id, Role role);
	List<String> getUserCategories(long id, Role role);
	List<IJsonModels> getQuesionsList(Boolean typeOfQuestion, String metaCategory,
			String category1, long id, Role role);
	List<String> getUserMetaCategories(long id, Role role);
	long createPerson(long personPassport, String personName,
			String personSurname, String personEmail);
	List<Long> getQuestionIdByParams(long id, Role role, String string,
			String string2, int parseInt);
	long createTest(String pass, long personId, long startTime, long stopTime,
			List<Long> questionIdList, long id, Role role);
	
}

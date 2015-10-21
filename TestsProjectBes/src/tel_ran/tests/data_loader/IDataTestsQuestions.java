package tel_ran.tests.data_loader;

import java.util.List;

import tel_ran.tests.services.fields.Role;

public interface IDataTestsQuestions {
	int getNumberQuestions(long id, Role role);
	int getNumberTests(long id, Role role);	
	boolean saveNewQuestion(String fileLocationLink, String metaCategory, String category1, String category2, int levelOfDifficulty,
			List<String> answers, String correctAnswerChar, int answerOptionsNumber, String description, String questionText, long id, Role role);
	
}

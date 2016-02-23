package tel_ran.tests.testGeneration;

import java.util.List;

import tel_ran.tests.entitys.Texts;
import tel_ran.tests.entitys.GeneratedQuestion;
import tel_ran.tests.entitys.Question;

public interface TestGeneration {

	List<String> getMetaCategories();

	List<String> getCategoriesByMetacategory(String mc);

	void setWorkingDir(String string);

	List<Question> createQuestions(GeneratedQuestion question, String metaCategory, String category1,
			int numQuestionsForEachCategory, int level) throws Exception;
	
	List<Texts> getTexts(); 

}

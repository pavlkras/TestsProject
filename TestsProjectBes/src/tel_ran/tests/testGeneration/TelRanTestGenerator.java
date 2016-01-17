package tel_ran.tests.testGeneration;

import java.util.ArrayList;
import java.util.List;
import tel_ran.tests.entitys.Texts;
import tel_ran.tests.entitys.GeneratedCommonQuestion;
import tel_ran.tests.entitys.GeneratedProgrammingQuestion;
import tel_ran.tests.entitys.GeneratedQuestion;
import tel_ran.tests.entitys.Question;
import tel_ran.tests.processor.TestProcessor;

public class TelRanTestGenerator implements TestGeneration {

	String workingDir;
	List<Texts> texts;
	
	@Override
	public List<String> getMetaCategories() {
		return TestProcessor.getMetaCategory();
	}

	@Override
	public List<String> getCategoriesByMetacategory(String mc) {		
		return TestProcessor.getCategoriesList(mc);
	}

	@Override
	public void setWorkingDir(String string) {
		this.workingDir = string;		
	}

	@Override
	public List<Question> createQuestions(GeneratedQuestion question, String metaCategory, String category1,
			int numQuestionsForEachCategory, int level) throws Exception {
		List<Question> questions = new ArrayList<>();
		
		TestProcessor proc = new TestProcessor();
		
		List<String[]> listQuestions = null;
		
		if(category1==null) {
			listQuestions = proc.processStart(metaCategory, numQuestionsForEachCategory, workingDir, level);
		} else {
			listQuestions = proc.processStart(metaCategory, category1, numQuestionsForEachCategory, workingDir, level);
		}
		
		if(listQuestions!=null) {
						
			for(String[] fres : listQuestions) {				
				GeneratedQuestion qstn = fillNewQuestion(question, fres);	
				questions.add(qstn);
			}				
		}
		
		
		
		return questions;
	}
	
		
	private GeneratedQuestion fillNewQuestion(GeneratedQuestion question, String[] fres) {
		if(question.getClass().equals(GeneratedProgrammingQuestion.class))
			return fillNewProgramTask(question, fres);
		else
			return fillNewCommonQuestion(question, fres);
	}
	

	private GeneratedQuestion fillNewCommonQuestion(GeneratedQuestion question, String[] fres) {
		
		this.texts = null;
		
		GeneratedCommonQuestion result = new GeneratedCommonQuestion();
		
		////
		/* 0 - question text  ("Реализуйте интерфейс")
		 * 1 - description (текст интерфейса и траляля) - тут был прежде линк на картинку( this field may bee null!!!)
		 * 2 - category (маленькой. тут - Калькулятор)
		 * 3 - level of difficulty = 1-5
		 * 4 - char right answer = ( this field may bee null!!!)
		 * 5 - number responses on pictures = 1-...
		 * 6 - file location link (for all saving files)  ( this field may bee null!!!)
		 * 7 - languageName	(bee as meta category for question witch code example)  ( this field may bee null!!!)
		 * 8 - code pattern	 (pattern code for Person)	( this field may bee null!!!)
		 */		
		
		result.setTitle(fres[0]);
		result.setSubtype(fres[2]);	
		result.setLevelOfDifficulty(Integer.parseInt(fres[3]));	
		result.setCorrectAnswerChar(fres[4]);
		result.setNumberOfAnswerOptions(Integer.parseInt(fres[5]));
		result.setLinkToFile(fres[6]);	
		
		return result;
	}

	private GeneratedQuestion fillNewProgramTask(GeneratedQuestion question, String[] fres) {
		GeneratedProgrammingQuestion result = new GeneratedProgrammingQuestion();
		
		////
		/* 0 - question text  ("Реализуйте интерфейс")
		 * 1 - description (текст интерфейса и траляля) - тут был прежде линк на картинку( this field may bee null!!!)
		 * 2 - category (маленькой. тут - Калькулятор)
		 * 3 - level of difficulty = 1-5
		 * 4 - char right answer = ( this field may bee null!!!)
		 * 5 - number responses on pictures = 1-...
		 * 6 - file location link (for all saving files)  ( this field may bee null!!!)
		 * 7 - languageName	(bee as meta category for question witch code example)  ( this field may bee null!!!)
		 * 8 - code pattern	 (pattern code for Person)	( this field may bee null!!!)
		 */			
		
		result.setTitle(fres[0]);
		result.setDescription(fres[1]);
		result.setSubtype(fres[2]);	
		result.setLevelOfDifficulty(Integer.parseInt(fres[3]));	
		result.setLinkToFile(fres[6]);	
		
		if(fres[8]!=null && fres[8].length() > 0) {		
			this.texts = new ArrayList<>();
			Texts text = new Texts();
			text.setText(fres[8]);
			texts.add(text);			
		} else {
			this.texts = null;
		}
		
		return result;
	}

	public List<Texts> getTexts() {
		return texts;
	}
	
	
	
	
	

}

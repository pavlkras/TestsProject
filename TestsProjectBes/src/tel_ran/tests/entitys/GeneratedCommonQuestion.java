package tel_ran.tests.entitys;



import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import tel_ran.tests.services.subtype_handlers.AutoTestQuestionHandler;
import tel_ran.tests.services.subtype_handlers.ITestQuestionHandler;


@Entity
@Table(name="Questions")
@DiscriminatorValue(value="CommonGeneration")
public class GeneratedCommonQuestion extends GeneratedQuestion {

	private String correctAnswer;
	private int numberOfAnswerOptions;
	
	
	public String getCorrectAnswerChar() {
		return correctAnswer;
	}
	public void setCorrectAnswerChar(String correctAnswerChar) {
		this.correctAnswer = correctAnswerChar;
	}
	public int getNumberOfAnswerOptions() {
		return numberOfAnswerOptions;
	}
	public void setNumberOfAnswerOptions(int numberOfAnswerOptions) {
		this.numberOfAnswerOptions = numberOfAnswerOptions;
	}
	@Override
	public GeneratedQuestion getNewInstance() {
		
		return new GeneratedCommonQuestion();
	}
	@Override
	public ITestQuestionHandler getHandler() {
		ITestQuestionHandler result = new AutoTestQuestionHandler();
		result.setQuestion(this);
		result.printQuestion();		
		return result;
		
	}
	
	
	
	
	
}

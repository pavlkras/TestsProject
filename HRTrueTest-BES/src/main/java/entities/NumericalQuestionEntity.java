package main.java.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import main.java.model.config.CategorySet;
import main.java.model.dao.NumericalQuestionData;

@Entity
@Table(name="numerical_question")
public class NumericalQuestionEntity extends BaseQuestionEntity {
	public static final String DELIMETER = "#";
	
	@Column(name="description")
	String description;
	@Column(name="sequence")
	String sequence;
	@Column(name="answers", length=54)
	String answers;
	@Column(name="correct_answer", length=10)
	String correctAnswer;
	
	public NumericalQuestionEntity(String candidateAnswer, Boolean passed, CatDiffEntity catDiff,
			TestEntity test, String description, String sequence, String answers, String correctAnswer) {
		super((byte)CategorySet.getCategoryIdByName(CategorySet.NUMERICAL_TASK), candidateAnswer, passed, catDiff, test);
		this.description = description;
		this.sequence = sequence;
		this.answers = answers;
		this.correctAnswer = correctAnswer;
	}

	public NumericalQuestionEntity() {
		// TODO Auto-generated constructor stub
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getAnswers() {
		return answers;
	}

	public void setAnswers(String answers) {
		this.answers = answers;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public static NumericalQuestionData convertToNumericalQuestionData(NumericalQuestionEntity entity){
		List<String> answers = new ArrayList<>(Arrays.asList(entity.answers.split(DELIMETER)));
		List<String> sequence = new ArrayList<>(Arrays.asList(entity.sequence.split(DELIMETER)));
		
		return new NumericalQuestionData(entity.id, entity.candidateAnswer, entity.passed,
				entity.description, sequence, answers, entity.correctAnswer);
	}
	
	public static String buildNumbersSequence(Collection<Integer> numbers) {
		StringBuilder builder = new StringBuilder();
		
		String prefix = "";
		for(Integer number : numbers){
			builder.append(prefix);
			prefix = DELIMETER;
			builder.append(number == null ? "?" : number.toString());
		}
		
		return builder.toString();
	}

}

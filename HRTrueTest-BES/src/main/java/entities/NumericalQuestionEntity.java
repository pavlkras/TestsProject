package main.java.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import main.java.model.config.CategorySet;
import main.java.model.dao.BaseQuestionData;
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
	
	@Override
	public BaseQuestionData convertToDataInstance() {
		List<String> answers = new ArrayList<>(Arrays.asList(this.answers.split(DELIMETER)));
		List<String> sequence = new ArrayList<>(Arrays.asList(this.sequence.split(DELIMETER)));
		
		return new NumericalQuestionData(this.id, this.candidateAnswer, this.passed,
				this.description, sequence, answers, this.correctAnswer);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((answers == null) ? 0 : answers.hashCode());
		result = prime * result + ((correctAnswer == null) ? 0 : correctAnswer.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((sequence == null) ? 0 : sequence.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		NumericalQuestionEntity other = (NumericalQuestionEntity) obj;
		if (answers == null) {
			if (other.answers != null)
				return false;
		} else if (!answers.equals(other.answers))
			return false;
		if (correctAnswer == null) {
			if (other.correctAnswer != null)
				return false;
		} else if (!correctAnswer.equals(other.correctAnswer))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (sequence == null) {
			if (other.sequence != null)
				return false;
		} else if (!sequence.equals(other.sequence))
			return false;
		return true;
	}
	
}

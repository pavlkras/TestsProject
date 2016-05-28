package main.java.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import main.java.model.dao.AttentionQuestionData;
import main.java.model.dao.BaseQuestionData;

@Entity
@Table(name="attention_question")
public class AttentionQuestionEntity extends BaseQuestionEntity {
	public static final String DELIMETER = "#";
	
	@Column(name="description")
	String description;
	@Column(name="answers", length=190)
	String answers;
	@Column(name="correct_answer", length=36)
	String correctAnswer;
	
	public AttentionQuestionEntity(String candidateAnswer, Boolean passed, CatDiffEntity catDiff, TestEntity test, String description,
			String answers, String correctAnswer) {
		super(candidateAnswer, passed, catDiff, test);
		this.description = description;
		this.answers = answers;
		this.correctAnswer = correctAnswer;
	}
	public AttentionQuestionEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public BaseQuestionData convertToDataInstance(){
		List<String> answers = new ArrayList<>(Arrays.asList(this.answers.split(DELIMETER)));
		
		return new AttentionQuestionData(this.id, this.candidateAnswer, this.passed,
				this.description, answers, this.correctAnswer, this.catDiff.category, this.catDiff.difficulty);
	}
	
	public static String buildAnswersSequence(Collection<String> answers){
		StringBuilder builder = new StringBuilder();
		
		String prefix = "";
		for(String answer : answers){
			builder.append(prefix);
			prefix = DELIMETER;
			builder.append(answer);
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
		AttentionQuestionEntity other = (AttentionQuestionEntity) obj;
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
		return true;
	}
	
}

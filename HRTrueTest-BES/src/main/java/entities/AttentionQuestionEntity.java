package main.java.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import main.java.model.config.CategorySet;
import main.java.model.dao.AttentionQuestionData;

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
		super((byte)CategorySet.getCategoryIdByName(CategorySet.ATTENTION_TASK), candidateAnswer, passed, catDiff, test);
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
	
	public static AttentionQuestionData convertToAttentionQuestionData(AttentionQuestionEntity entity){
		List<String> answers = new ArrayList<>(Arrays.asList(entity.answers.split(DELIMETER)));
		
		return new AttentionQuestionData(entity.id, entity.candidateAnswer, entity.passed,
				entity.description, answers, entity.correctAnswer);
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
}

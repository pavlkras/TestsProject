package main.java.model.dao;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class NumericalQuestionData extends BaseQuestionData {
	String description;
	List<String> sequence;
	List<String> answers;
	@JsonIgnore
	String correctAnswer;
	
	public NumericalQuestionData(long id, String candidateAnswer, Boolean passed, String description,
			List<String> sequence, List<String> answers, String correctAnswer, int category, int difficulty) {
		super(id, candidateAnswer, passed, category, difficulty);
		this.description = description;
		this.sequence = sequence;
		this.answers = answers;
		this.correctAnswer = correctAnswer;
	}
	public NumericalQuestionData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<String> getSequence() {
		return sequence;
	}
	public void setSequence(List<String> sequence) {
		this.sequence = sequence;
	}
	public List<String> getAnswers() {
		return answers;
	}
	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
}

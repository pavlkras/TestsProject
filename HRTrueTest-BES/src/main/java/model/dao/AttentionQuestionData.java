package main.java.model.dao;

import java.util.List;

public class AttentionQuestionData extends BaseQuestionData {
	String description;
	List<String> answers;
	String correctAnswer;
	
	public AttentionQuestionData(long id, String candidateAnswer, Boolean passed, String description,
			List<String> answers, String correctAnswer, int category, int difficulty) {
		super(id, candidateAnswer, passed, category, difficulty);
		this.description = description;
		this.answers = answers;
		this.correctAnswer = correctAnswer;
	}
	public AttentionQuestionData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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

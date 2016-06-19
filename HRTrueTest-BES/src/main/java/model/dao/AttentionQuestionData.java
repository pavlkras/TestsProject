package main.java.model.dao;

import java.util.List;

import main.java.utils.annotations.RolesJsonIgnore;

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
	@RolesJsonIgnore({"ROLE_USER", "ROLE_CANDIDATE"})
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	@RolesJsonIgnore({"ROLE_USER", "ROLE_CANDIDATE"})
	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
}

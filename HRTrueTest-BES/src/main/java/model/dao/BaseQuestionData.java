package main.java.model.dao;

import main.java.utils.annotations.RolesJsonIgnore;

public abstract class BaseQuestionData {
	long id;
	String candidateAnswer;
	Boolean passed;
	int category;
	int difficulty;
	public BaseQuestionData(long id, String candidateAnswer, Boolean passed, int category, int difficulty) {
		super();
		this.id = id;
		this.candidateAnswer = candidateAnswer;
		this.passed = passed;
		this.category = category;
		this.difficulty = difficulty;
	}
	public BaseQuestionData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@RolesJsonIgnore("ROLE_CANDIDATE")
	public String getCandidateAnswer() {
		return candidateAnswer;
	}
	public void setCandidateAnswer(String candidateAnswer) {
		this.candidateAnswer = candidateAnswer;
	}
	public Boolean isPassed() {
		return passed;
	}
	public void setPassed(Boolean passed) {
		this.passed = passed;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public int getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
}

package main.java.model.dao;

public abstract class BaseQuestionData {
	long id;
	String candidateAnswer;
	Boolean passed;
	public BaseQuestionData(long id, String candidateAnswer, Boolean passed) {
		super();
		this.id = id;
		this.candidateAnswer = candidateAnswer;
		this.passed = passed;
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
}

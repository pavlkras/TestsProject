package tel_ran.tests.model;


public class Answer {

	private Question questionAnswer = null;

	private String id;
	private long keyQuestion;

	private boolean isAnswerRight;
	private String answerText;
	
	
	public Answer() {
		
	}
	
	public Question getQuestionAnswer() {
		return questionAnswer;
	}
	
	
	
	public void setQuestionAnswer(Question questionAnswer) {
		this.questionAnswer = questionAnswer;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
	public long getKeyQuestion() {
		return keyQuestion;
	}
	public void setKeyQuestion(long keyQuestion) {
		this.keyQuestion = keyQuestion;
	}
	public boolean getIsAnswerRight() {
		return isAnswerRight;
	}
	public void setIsAnswerRight(boolean isAnswer) {
		this.isAnswerRight = isAnswer;
	}
	public String getAnswerText() {
		return answerText;
	}
	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}
	
}

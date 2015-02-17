package tel_ran.tests.services;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
@Entity
public class EntityAnswer {
	public EntityAnswer() {}
	@Id
	@Column(name="ID")
	@GeneratedValue
	private long id;

	private boolean isAnswer;
	private String answerText;
	@ManyToOne	
	private EntityQuestion questionid;
	
	public long getId() {
		return id;
	}
	
	public boolean isAnswer() {
		return isAnswer;
	}
	public void setAnswer(boolean isAnswer) {
		this.isAnswer = isAnswer;
	}
	public String getAnswerText() {
		return answerText;
	}
	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}
	public EntityQuestion getQuestionId() {
		return questionid;
	}
	public void setQuestionId(EntityQuestion quest) {
		this.questionid = quest;
	}
	private String DELIMITER = "----";
	@Override
	public String toString() {
		return answerText +DELIMITER+isAnswer+DELIMITER;
	}


}

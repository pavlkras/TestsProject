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

	long id;	


	private boolean isAnswer;
	private String answerText;

	@ManyToOne	
	EntityQuestion quest;

	
	public boolean isAnswer() {
		return isAnswer;
	}
	public void setAnswer(boolean isAnswer) {
		this.isAnswer = isAnswer;
	}
	protected String getAnswerText() {
		return answerText;
	}
	protected void setAnswerText(String answerText) {
		this.answerText = answerText;
	}
	protected EntityQuestion getQuest() {
		return quest;
	}
	protected void setQuest(EntityQuestion quest) {
		this.quest = quest;
	}
	@Override
	public String toString() {
		return answerText +":"+isAnswer+ ":";
	}


}

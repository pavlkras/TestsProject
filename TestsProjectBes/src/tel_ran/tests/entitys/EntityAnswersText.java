package tel_ran.tests.entitys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class EntityAnswersText implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	////
	public EntityAnswersText() {}
	////
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	////
	private String answerText;
	////
	@ManyToOne
	private EntityQuestionAttributes questionAttributeId;
	////
	public long getId() {
		return id;
	}
	////
	public String getAnswerText() {
		return answerText;
	}
	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}
	////
	public EntityQuestionAttributes getQuestionAttributeId() {
		return questionAttributeId;
	}
	public void setQuestionAttributeId(EntityQuestionAttributes questionAttributeId) {
		this.questionAttributeId = questionAttributeId;
	}
	////
	@Override
	public String toString() {
		return answerText;
	}
}
package tel_ran.tests.entitys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class EntityTexts implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	////
	public EntityTexts() {}
	////
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	////
	@Column(name="answerText", length = 3000)
	private String answerText;
	////
	@ManyToOne
	private EntityQuestionAttributes entityQuestionAttributes;
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
	public EntityQuestionAttributes getEntityQuestionAttributes() {
		return entityQuestionAttributes;
	}
	public void setEntityQuestionAttributes(EntityQuestionAttributes entityQuestionAttributesbutes) {
		this.entityQuestionAttributes = entityQuestionAttributes;
	}
	////
	@Override
	public String toString() {
		return answerText;
	}
}
package tel_ran.tests.entitys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Texts implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	////
	public Texts() {}
	////
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	////
	@Column(name="answerText", length = 3000)
	private String text;
	////
	@ManyToOne
	private EntityQuestionAttributes entityQuestionAttributes;
	////
	
	@ManyToOne
	private Question question;
		
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public long getId() {
		return id;
	}
	
	////
	public EntityQuestionAttributes getEntityQuestionAttributes() {
		return entityQuestionAttributes;
	}
	public void setEntityQuestionAttributes(EntityQuestionAttributes entityQuestionAttributesbutes) {
		this.entityQuestionAttributes = entityQuestionAttributesbutes;
	}
	////
	@Override
	public String toString() {
		return text;
	}
}
package tel_ran.tests.entitys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class EntityTestQuestions {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private long id;
	
	@ManyToOne
	private EntityQuestionAttributes question;
	
	@ManyToOne
	@JoinColumn(name="TEST_ID", nullable=false)
	private EntityTest test;
	
	@Column(name="answer", length = 10000)
	private String answer;	
			
	private int status;	
	

	
	public EntityTest getEntityTest() {
		return test;
	}
	public void setEntityTest(EntityTest entityTest) {
		this.test = entityTest;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public EntityQuestionAttributes getEntityQuestionAttributes() {
		return question;
	}
	public void setEntityQuestionAttributes(
			EntityQuestionAttributes entityQuestionAttributes) {
		this.question = entityQuestionAttributes;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	

}

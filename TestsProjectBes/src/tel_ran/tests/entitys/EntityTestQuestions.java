package tel_ran.tests.entitys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class EntityTestQuestions {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private long tQuestionId;
	
	@ManyToOne
	private EntityQuestionAttributes eqaId;
	
	@ManyToOne
	@JoinColumn(name="TEST_ID", nullable=false)
	private EntityTest entityTest;
	
	@Column(name="answer", length = 10000)
	private String answer;	
	
	private String linkToAnswer;
		
	private int status;	
	
	public EntityTest getEntityTest() {
		return entityTest;
	}
	public void setEntityTest(EntityTest entityTest) {
		this.entityTest = entityTest;
	}
	public long getId() {
		return tQuestionId;
	}
	public void setId(long id) {
		this.tQuestionId = id;
	}
	public EntityQuestionAttributes getEntityQuestionAttributes() {
		return eqaId;
	}
	public void setEntityQuestionAttributes(
			EntityQuestionAttributes entityQuestionAttributes) {
		this.eqaId = entityQuestionAttributes;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getLinkToAnswer() {
		return linkToAnswer;
	}
	public void setLinkToAnswer(String linkToAnswer) {
		this.linkToAnswer = linkToAnswer;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	

}

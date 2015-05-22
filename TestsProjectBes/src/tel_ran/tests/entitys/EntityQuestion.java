package tel_ran.tests.entitys;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import tel_ran.tests.services.interfaces.IMaintenanceService;

@Entity
public class EntityQuestion implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	////
	public EntityQuestion(){}
	////
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	////
	@Column(name = "questionText", unique = true, nullable = false, length = 500)
	private String questionText;
	////
	@Column(name = "description", length = 1500)
	private String description;
	////
	@OneToMany(mappedBy = "questionId")
	List<EntityQuestionAttributes> questionAttributes;
	////
	public String getQuestionText() {
		return questionText;
	}
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	public List<EntityQuestionAttributes> getQuestionAttributes() {
		return questionAttributes;
	}
	public void setQuestionAttributes(
			List<EntityQuestionAttributes> questionAttributes) {
		this.questionAttributes = questionAttributes;
	}	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getId() {
		return id;
	}
	////
	@Override
	public String toString() {
		return id + IMaintenanceService.DELIMITER + questionText + IMaintenanceService.DELIMITER + description;
	}
}
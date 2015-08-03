package tel_ran.tests.entitys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
	@Id @GeneratedValue(strategy = GenerationType.AUTO)	
	@Column(name = "id")
	private long id;
	////
	@Column(name = "questionText", unique = true, nullable = false, length = 500)
	private String questionText; //Text of the question
	
	//// --------- !!!!!!!!!!!!! - EXPIRED  !!!! - TO DELETE!!!!
	//// --------- FIELD MOVED TO EntityQuestionAttributes ------------- ////
//	@Column(name = "description", length = 10)
//	private String description; 
	
	////
	@OneToMany(mappedBy = "questionId")
	List<EntityQuestionAttributes> questionAttributes;
	////
	
	public EntityQuestion(){}
		
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
	public void addQuestionAttributes(EntityQuestionAttributes eqa) {
		if(this.questionAttributes==null)
			this.questionAttributes = new ArrayList<EntityQuestionAttributes>();

		this.questionAttributes.add(eqa);
	}
	
	public void deleteQuestionAttributes(EntityQuestionAttributes eqa) {
		this.questionAttributes.remove(eqa);
	}
	
	//// --------- !!!!!!!!!!!!! - EXPIRED  !!!! - TO DELETE!!!!
	//// --------- FIELD MOVED TO EntityQuestionAttributes ------------- ////
//	public String getDescription() {
//		return description;
//	}
	//// --------- !!!!!!!!!!!!! - EXPIRED  !!!! - TO DELETE!!!!
	//// --------- FIELD MOVED TO EntityQuestionAttributes ------------- ////
//	public void setDescription(String description) {
//		this.description = description;
//	}
	public long getId() {
		return id;
	}
	
	//// --------- !!!!!!!!!!!!! - EXPIRED  !!!! - TO CHANGE!!!!
	//// --------- FIELD description MOVED TO EntityQuestionAttributes ------------- ////
	@Override
	public String toString() {
		return id + IMaintenanceService.DELIMITER + questionText /*+ IMaintenanceService.DELIMITER + description*/;
	}
}
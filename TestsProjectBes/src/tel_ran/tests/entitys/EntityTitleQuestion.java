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
public class EntityTitleQuestion implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	
	////
	@Id @GeneratedValue(strategy = GenerationType.AUTO)	
	@Column(name = "id")
	private long id;
	////
	@Column(name = "questionText", unique = true, nullable = false, length = 500)
	private String questionText; //Text of the question
	
	public EntityTitleQuestion(){}
		
	public String getQuestionText() {
		return questionText;
	}
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public long getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return id + IMaintenanceService.DELIMITER + questionText /*+ IMaintenanceService.DELIMITER + description*/;
	}
}
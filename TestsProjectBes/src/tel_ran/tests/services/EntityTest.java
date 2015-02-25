package tel_ran.tests.services;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity

public class EntityTest {
 
	@Id
	@GeneratedValue
	private long testId;
    private String question;
    
    @ManyToOne	
	private EntityPersonFOOX personId;
 


public EntityPersonFOOX getPersonId() {
	return personId;
}

public void setPersonId(EntityPersonFOOX personId) {
	this.personId = personId;
}

public long getTestId() {
  return testId;
 }

public String getQuestion() {
	return question;
}


public void setQuestion(String idQuestion) {
	// TODO Auto-generated method stub
	this.question = idQuestion;
}
 
}
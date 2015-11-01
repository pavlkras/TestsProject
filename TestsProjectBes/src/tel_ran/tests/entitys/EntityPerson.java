package tel_ran.tests.entitys;
import java.util.List;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class EntityPerson {
    @Id
	@Column(name="personId",unique = true, nullable = false, length = 25)
    private long personId;
  
    @OneToMany(mappedBy = "entityPerson")
    private List<EntityTest> enTest;
 
	public List<EntityTest> getEnTest() {
		return enTest;
	}
	public void setEnTest(List<EntityTest> enTest) {
		this.enTest = enTest;
	}
	private String personName;
    private String personSurname;
    private String personEmail;
    
	public String getPersonEmail() {
		return personEmail;
	}
	public void setPersonEmail(String personEmail) {
		this.personEmail = personEmail;
	}
	public long getPersonId() {
		return personId;
	}
	public void setPersonId(long personId) {
		this.personId = personId;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getPersonSurname() {
		return personSurname;
	}
	public void setPersonSurname(String personSurname) {
		this.personSurname = personSurname;
	}
	@Override
	public String toString() {
		return "EntityPerson [personId=" + personId + ", personName=" + personName + ", personSurname="
				+ personSurname + ", personEmail=" + personEmail + "]";
	}
	
}

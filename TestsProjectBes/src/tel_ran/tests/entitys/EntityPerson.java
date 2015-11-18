package tel_ran.tests.entitys;
import java.util.List;





import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table (name = "EntityPerson", uniqueConstraints = {
			@UniqueConstraint(columnNames = "identify")
	})
public class EntityPerson {
    
	@Id
	@GeneratedValue
	@Column(name="personId",unique = true, nullable = false, length = 25)	
    private long personId;
	private String personName;
    private String personSurname;
    private String personEmail;
    
    @ManyToOne
    private EntityCompany byCompany;
    
    @Column(name="identify")
    private String identify;
   
    @OneToMany(mappedBy = "person")
    private List<EntityTest> test;
 
	public List<EntityTest> getEntityTest() {
		return test;
	}
	public void setEntityTest(List<EntityTest> enTest) {
		this.test = enTest;
	}
	public String getIdentify() {
		return identify;
	}
	public void setIdentify(String identify) {
		this.identify = identify;
	}	
  
    
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

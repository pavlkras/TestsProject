package tel_ran.tests.services;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
@Entity
public class EntityCompanyTwo {

	private int c_ID;
	private String c_Name;
	private String c_Site;
	private String c_Specialization;
	private String c_AmountEmployes;
	private String c_Password;
	
	@OneToMany (targetEntity=EntityPerson.class, mappedBy ="")
	List<EntityPerson>persons;

	@Id
	@GeneratedValue
	public int getC_ID() {
		return c_ID;
	}

	public void setC_ID(int c_ID) {
		this.c_ID = c_ID;
	}

	public String getC_Name() {
		return c_Name;
	}

	public void setC_Name(String c_Name) {
		this.c_Name = c_Name;
	}

	public String getC_Site() {
		return c_Site;
	}

	public void setC_Site(String c_Site) {
		this.c_Site = c_Site;
	}

	public String getC_Specialization() {
		return c_Specialization;
	}

	public void setC_Specialization(String c_Specialization) {
		this.c_Specialization = c_Specialization;
	}

	public String getC_AmountEmployes() {
		return c_AmountEmployes;
	}

	public void setC_AmountEmployes(String c_AmountEmployes) {
		this.c_AmountEmployes = c_AmountEmployes;
	}

	public String getC_Password() {
		return c_Password;
	}

	public void setC_Password(String c_Password) {
		this.c_Password = c_Password;
	}

	public List<EntityPerson> getPersons() {
		return persons;
	}

	public void setPersons(List<EntityPerson> persons) {
		this.persons = persons;
	}
	
	

}



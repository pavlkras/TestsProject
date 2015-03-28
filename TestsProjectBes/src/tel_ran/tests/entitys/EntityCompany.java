package tel_ran.tests.entitys;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
@Entity
public class EntityCompany {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	@OneToMany (mappedBy = "entityCompany")
	private List<EntityTest> personsTests; 
	private String C_Name;
	private String C_Site;

	private String C_Specialization;

	private String C_AmountEmployes;

	private String C_Password;

	public void setPassword(String password) {
		C_Password = password;
	}
	public long getId() {
		return id;
	}

	public String getPassword() {
		return C_Password;
	}
	public String getC_Name() {
		return C_Name;
	}
	public void setC_Name(String c_Name) {
		C_Name = c_Name;
	}
	public String getC_Site() {
		return C_Site;
	}
	public void setC_Site(String c_Site) {
		C_Site = c_Site;
	}
	public String getC_Specialization() {
		return C_Specialization;
	}
	public void setC_Specialization(String c_Specialization) {
		C_Specialization = c_Specialization;
	}
	public String getC_AmountEmployes() {
		return C_AmountEmployes;
	}
	public void setC_AmountEmployes(String c_AmountEmployes) {
		C_AmountEmployes = c_AmountEmployes;
	}
	public List<EntityTest> getPersonsTests() {
		return personsTests;
	}
	public void setPersonsTests(List<EntityTest> personsTests) {
		this.personsTests = personsTests;
	}
	@Override
	public String toString() {
		return "Company " + C_Name + ", Site : " + C_Site
				+ ", Specialization : " + C_Specialization
				+ ", Amount Employes : " + C_AmountEmployes 
				;
	}
}



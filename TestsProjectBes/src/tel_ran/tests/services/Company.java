package tel_ran.tests.services;
import javax.persistence.*;

import java.util.*;
@Entity
public class Company {
	@Id
	private String C_Name;
	private String C_Site;
		
	private String C_Specialization;
	
	private String C_AmountEmployes;
	
	private String C_Password;
	
	//// Added by Igor ////
	@OneToMany(mappedBy = "company")
	private Set<EntityPerson> personsSet = new HashSet<EntityPerson>();
	
	public Set<EntityPerson> getEntityPerson() {
	    return personsSet;
	}
	public void setEntityPerson(Set<EntityPerson> param) {
	    this.personsSet = param;
	}
	//// End of Adding ////

	public void setPassword(String password) {
		C_Password = password;
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

	@Override
	public String toString() {
		return "Company " + C_Name + ", Site : " + C_Site
				+ ", Specialization : " + C_Specialization
				+ ", Amount Employes : " + C_AmountEmployes 
				;
	}


}



package tel_ran.tests.services;
import javax.persistence.*;

import java.util.*;
@Entity
public class CompanyTwo {
	@Id
	private int id;
	private String C_Name;
	private String C_Site;
		
	private String C_Specialization;
	
	private String C_AmountEmployes;
	
	private String C_Password;
	
	@OneToOne
	int company_id;
	@OneToMany (mappedBy ="")
	List<Personal_Details>personals;
	
	public int getId() {
		return id;
	}
	public List<Personal_Details> getPersonals() {
		return personals;
	}
	public void setPersonals(List<Personal_Details> personals) {
		this.personals = personals;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getC_Password() {
		return C_Password;
	}
	public void setC_Password(String c_Password) {
		C_Password = c_Password;
	}
	public int getCompany_id() {
		return company_id;
	}
	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}
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
		return "CompanyTwo [id=" + id + ", C_Name=" + C_Name + ", C_Site="
				+ C_Site + ", C_Specialization=" + C_Specialization
				+ ", C_AmountEmployes=" + C_AmountEmployes + ", C_Password="
				+ C_Password + "]";
	}


}



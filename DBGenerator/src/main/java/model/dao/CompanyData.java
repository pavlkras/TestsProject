package main.java.model.dao;

public class CompanyData extends UserData {
	String name;
	String site;
	byte specialization;
	byte employees_amnt;
	
	public CompanyData() {
		// TODO Auto-generated constructor stub
	}
	
	public CompanyData(String email, String password, byte role, 
			String name, String site, byte specialization, byte employees_amnt) {
		super(email, password, role);
		this.name = name;
		this.site = site;
		this.specialization = specialization;
		this.employees_amnt = employees_amnt;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public byte getSpecialization() {
		return specialization;
	}
	public void setSpecialization(byte specialization) {
		this.specialization = specialization;
	}
	public byte getEmployees_amnt() {
		return employees_amnt;
	}
	public void setEmployees_amnt(byte employees_amnt) {
		this.employees_amnt = employees_amnt;
	}
}

package main.java.model.dao;

import java.util.Set;

public class CompanyData extends CredentialsData {
	String name;
	String site;
	byte activityType;
	byte employeesAmnt;
	
	
	public CompanyData(){
	}
	
	public CompanyData(Long id, String email, String password, String name, String site, byte activityType,
			byte employeesAmnt, Set<String> authorities) {
		//TODO add constants for roles
		super(id, email, password, authorities);
		this.name = name;
		this.site = site;
		this.activityType = activityType;
		this.employeesAmnt = employeesAmnt;
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
	public byte getAcitivityType() {
		return activityType;
	}
	public void setActivityType(byte activityType) {
		this.activityType = activityType;
	}
	public byte getEmployeesAmnt() {
		return employeesAmnt;
	}
	public void setEmployeesAmnt(byte employeesAmnt) {
		this.employeesAmnt = employeesAmnt;
	}
}

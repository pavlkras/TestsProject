package main.java.model.dao;

public class CompanyData extends UserData {
	String name;
	String site;
	byte activityType;
	byte employeesAmnt;
	private long id;
	
	
	public CompanyData(){
		
	}
	
	public CompanyData(String email, String password, String name, String site, byte activityType,
			byte employeesAmnt) {
		//TODO add constants for roles
		super(email, password, (byte)1);
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

	public long getId(){
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
		
	}
}

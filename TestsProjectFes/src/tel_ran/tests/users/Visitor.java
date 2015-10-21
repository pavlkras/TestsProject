package tel_ran.tests.users;

import java.util.Date;

import tel_ran.tests.services.fields.Role;
import tel_ran.tests.utils.SpringApplicationContext;
import handlers.IHandler;

public class Visitor {
	
	String token;
	int roleNumber;
	Role role;
	private String email;
	public IHandler handler;
	
	//FIELDS FOR USERS
	String address;
	Date birthDate;
	String firstName;
	String lastName;
	String nickName;
	String passportNumber;
	
	//FIELDS FOR COMPANIES
	String companyName;
	String employesNumber;
	String site;
	String specialization;
	
	public Visitor(int role) {
		changeRole(role);
	}
	
	public void changeRole(int role) {
		this.roleNumber = role;
		this.role = Role.values()[role];
		setHandler();
	}
	
	private void setHandler() {			
		switch(role) {
			case GUEST : handler = (IHandler) SpringApplicationContext.getBean("handler"); break;
			case PERSON : handler = (IHandler) SpringApplicationContext.getBean("handler"); break;
			case USER : handler = (IHandler) SpringApplicationContext.getBean("user"); break;
			case COMPANY : handler = (IHandler) SpringApplicationContext.getBean("company"); break;
			case ADMINISTRATOR : handler = (IHandler) SpringApplicationContext.getBean("admin"); break;
		}
		if(this.token!=null)
			handler.setToken(token);		
	}	
	
	public void setToken(String token) {
		this.token = token;
		if(this.handler!=null)
			this.handler.setToken(token);
	}
		
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	
		
	public String getToken() {
		return token;
	}

	public int getRoleNumber() {
		return roleNumber;
	}

	public void setRoleNumber(int roleNumber) {
		this.roleNumber = roleNumber;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public String getEmployesNumber() {
		return employesNumber;
	}

	public void setEmployesNumber(String employesNumber) {
		this.employesNumber = employesNumber;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	


}

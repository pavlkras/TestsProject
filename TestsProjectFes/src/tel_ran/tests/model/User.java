package tel_ran.tests.model;

import tel_ran.tests.services.interfaces.IPersonalActionsService;

public class User {	
	private String firstName;
	private String lastName;
	private String password;	
	private String email;	
	//
	public User() {	}

	public User(String [] args) {		
		this.firstName = args[IPersonalActionsService.FIRSTNAME];
		this.lastName = args[IPersonalActionsService.LASTTNAME];
		this.password = args[IPersonalActionsService.PASSWORD];
		this.email = args[IPersonalActionsService.EMAIL];
	} 
	//
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String id) {
		this.firstName = id;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String name) {
		this.lastName = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName
				+ ", password=" + password + ", email=" + email + "]";
	}

}


package tel_ran.tests.model;

import tel_ran.tests.services.interfaces.IPersonalActionsService;



public class User {
	
	
	private String id;//teudat zeut. to work with field convert it on int in functions
	private String name;
	private String password;	
	
	public User() {	
		
	}
	
	public User(String [] args) {		
		this.id = args[IPersonalActionsService.ID];
		this.name = args[IPersonalActionsService.NAME];
		this.password = args[IPersonalActionsService.PASSWORD];
		this.email = args[IPersonalActionsService.EMAIL];
	}
	
	private String email;	
		

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
		return "User [id=" + id + ", name=" + name + ", password=" + password
				+ ", email=" + email +  "]";
	}
	

}


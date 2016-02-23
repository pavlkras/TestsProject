package handlers;

import tel_ran.tests.services.fields.Role;

public class User extends Registred {
	
	public User() {
		this.role = Role.USER;
		this.roleNumber = Role.USER.ordinal();
	}
	
	@Override
	public String logInPage() {			
		return "user/UserAccountPage";	
	}
	
	@Override
	public String companyLogInPage() {
		return "user/UserAccountPage";
	}
	
}

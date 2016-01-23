package handlers;


import tel_ran.tests.services.fields.Role;


public class Admin extends Registred {
	
	public Admin() {
		this.role = Role.ADMINISTRATOR;
		this.roleNumber = Role.ADMINISTRATOR.ordinal();
	}
		
	@Override
	public String logInPage() {		
		return "maintenance/MaintenanceMainPage";		
	}
	
	@Override
	public String companyLogInPage() {
		return "maintenance/MaintenanceMainPage";	
	}
	

}

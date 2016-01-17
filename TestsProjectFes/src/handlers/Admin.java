package handlers;

import java.util.List;

import org.springframework.ui.Model;

import tel_ran.tests.controller.MainController;
import tel_ran.tests.services.fields.Role;
import tel_ran.tests.services.interfaces.ICommonAdminService;

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

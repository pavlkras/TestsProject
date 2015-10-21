package handlers;

import org.springframework.ui.Model;

import tel_ran.tests.controller.MainController;
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
	
	@Override
	public boolean generateAutoQuestions(String metaCategory, String category2, int levelOfDifficulty,
			String nQuestions) {
		return false;		
	}

}

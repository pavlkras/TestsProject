package handlers;


import java.util.List;

import tel_ran.tests.services.fields.Role;

public class Guest extends AbstractHandler {
	
	
	
	
	public Guest() {
		this.role = Role.GUEST;		
		this.roleNumber = Role.GUEST.ordinal();
	}
	
	@Override
	public boolean generateAutoQuestions(String metaCategory, String category2, int levelOfDifficulty,
			String nQuestions) {
		return false;		
	}

	@Override
	public List<String> getPossibleMetaCaterories() {		
		return null;
	}

	


}

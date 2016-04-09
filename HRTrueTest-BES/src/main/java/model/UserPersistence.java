package main.java.model;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import main.java.model.dao.UserData;
import main.java.model.interfaces.IUserModel;

public class UserPersistence implements IUserModel {

	@PersistenceContext(unitName="HRTrueTestBES")
	EntityManager em;
	
	@Override
	public boolean registerUser(UserData data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserData getUserData(String email) {
		// TODO Auto-generated method stub
		return null;
	}

}

package main.java.model.interfaces;

import main.java.model.dao.UserData;

public interface IUserModel {
	boolean registerUser(UserData user);
	UserData getUserData(String email);
}

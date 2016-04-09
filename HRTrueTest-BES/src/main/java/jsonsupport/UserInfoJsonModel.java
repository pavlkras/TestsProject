package main.java.jsonsupport;

import main.java.security.dao.User;

public class UserInfoJsonModel implements IJsonModel {

	String userId = "";
	String userName = "";
	String role = "";

	public UserInfoJsonModel(User user) {
		userId = user.getId() + "";
		userName = user.getUsername();
		role = user.getRole();
	}

	public String getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public String getRole() {
		return role;
	}

}

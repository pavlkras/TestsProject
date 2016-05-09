package main.java.jsonsupport;

import main.java.security.dao.JwtUser;

public class UserInfoJsonModel implements IJsonModel {

	String userId = "";
	String userName = "";
	String role = "";

	public UserInfoJsonModel(JwtUser user) {
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

package tel_ran.tests.token_cipher;

import tel_ran.tests.services.fields.Role;

public class User {
	
	long id;
	int roleNumber;
	private Role role;
	boolean authorized;
	String uniqueName;
	
	public User(long id, int role) {
		super();
		this.id = id;
		this.roleNumber = role;
		if(id<0 || role < 0)
			authorized = false;
		else {
			authorized = true;
			this.role = Role.values()[roleNumber];
			this.uniqueName = this.role.toString() +id;
		}
	}
	
	public static User getAdminUser() {
		User user = new User(1, Role.ADMINISTRATOR.ordinal());
		return user;
	}
		
	public String getUniqueName() {
		return uniqueName;
	}

	public Role getRole() {
		return role;
	}


	public void setId(long id) {
		this.id = id;
	}
	public void setRoleNumber(int role) {
		this.roleNumber = role;
	}
	public boolean isAutorized() {
		return authorized;
	}
	public long getId() {
		return id;
	}
	public int getRoleNumber() {
		return roleNumber;
	}
	
	
	
	
	

}

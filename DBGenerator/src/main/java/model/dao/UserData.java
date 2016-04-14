package main.java.model.dao;

public abstract class UserData {
	String email;
	String password;
	private byte role;
	
	public UserData(String email, String password, byte role) {
		super();
		this.email = email;
		this.password = password;
		this.role = role;
	}
	
	public UserData(){
		
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public byte getRole() {
		return role;
	}
	public void setRole(byte role) {
		this.role = role;
	}
}

package main.java.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="credentials")
public class CredentialsEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="aa_id")
	long id;
	@Column(name="login", length=30, nullable=false, unique=true)
	String login;
	@Column(name="password", length=60)
	String password;
	@Column(name="role")
	byte role;
	
	
	public CredentialsEntity(){
		
	}
	public CredentialsEntity(String login, String password, byte role){
		setLogin(login);
		setPassword(password);
		setRole(role);
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
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
	public long getId() {
		return id;
	}
}

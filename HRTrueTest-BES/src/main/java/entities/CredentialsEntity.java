package main.java.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import main.java.model.config.AuthorityName;
import main.java.model.dao.CredentialsData;

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
	@Column(name="roles")
	byte roles;
	
	
	public CredentialsEntity(){
		
	}
	public CredentialsEntity(String login, String password, byte roles){
		setLogin(login);
		setPassword(password);
		setRoles(roles);
	}
	
	public CredentialsEntity(String username, String password, Set<String> authorities) {
		this(username, password, convertAuthoritiesToDbMask(authorities));
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
	public byte getRoles() {
		return roles;
	}
	public void setRoles(byte roles) {
		this.roles = roles;
	}
	public long getId() {
		return id;
	}
	
	public static CredentialsData convertToCredentialsData(CredentialsEntity entity){
		long id = entity.id;
		String username = entity.login;
		String password = entity.password;
		
		Set<String> authorities = convertDbMaskToAuthorities(entity.roles);
		
		return new CredentialsData(id, username, password, authorities);
	}
	
	public static byte convertAuthoritiesToDbMask(Set<String> authorities){
		byte ret = 0;
		
		for (AuthorityName value : AuthorityName.values()){
			if (authorities.contains(value.name())){
				ret += value.code();
			}
		}
		
		return ret;
	}
	
	public static Set<String> convertDbMaskToAuthorities(byte mask){
		Set<String> authorities = new HashSet<>();
		
		for (AuthorityName value : AuthorityName.values()){
			if ((mask & value.code()) != 0){
				authorities.add(value.name());
			}
		}
		
		return authorities;
	}
}

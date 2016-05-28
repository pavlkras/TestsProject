package main.java.model.dao;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CandidateData extends CredentialsData {
	long id;
	String firstName;
	String lastName;
	@JsonIgnore
	String tmpPasswd;
	
	public CandidateData(long id, String email, String password,
			String firstName, String lastName, Set<String> authorities) {
		super(id, email, password, authorities);
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public CandidateData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTmpPasswd() {
		return tmpPasswd;
	}
	public void setTmpPasswd(String tmpPasswd) {
		this.tmpPasswd = tmpPasswd;
	}
}

package main.java.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="credentials")
public class CredentialsEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="aa_id")
	long id;
	@Column(name="login", length=30, nullable=false, unique=true)
	String login;
	@Column(name="password", length=60)
	String password;
	@Column(name="role")
	byte role;
}

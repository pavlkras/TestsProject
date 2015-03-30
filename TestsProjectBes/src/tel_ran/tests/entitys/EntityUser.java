package tel_ran.tests.entitys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@SuppressWarnings("serial")
public class EntityUser implements Serializable{
	@Id
	@Column(name = "email", unique = true, nullable = false, length = 70)
	private String email;
	////
	private String firstName;
	private String lastName;
	private String password;
	////
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	////
	@Override
	public String toString() {
		return "User firstName=" + firstName + ", lastName=" + lastName + ", password=" + password
				+ ", email=" + email;
	}
}
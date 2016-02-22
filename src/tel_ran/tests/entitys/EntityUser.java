package tel_ran.tests.entitys;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class EntityUser implements Serializable{
	
	private static final long serialVersionUID = 2L;
	
	@Id
	@Column(unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "email", unique = true, nullable = false, length = 70)
	private String email;
	////
	private String nickname;
	private String firstName;
	private String lastName;
	private String password;
	private String passportNumber;
	private String address;
	@Temporal(value=TemporalType.DATE)
	private Date birthdate;
	private boolean adminAccess;
	
	@OneToMany(mappedBy="user")
	private List<Test> passedTests;
			
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public boolean isAdminAccess() {
		return adminAccess;
	}
	public void setAdminAccess(boolean adminAccess) {
		this.adminAccess = adminAccess;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassportNumber() {
		return passportNumber;
	}
	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
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
		return "User nickname=" + nickname + " firstName=" + firstName + ", lastName=" + lastName + ", password=" + password
				+ ", email=" + email + " adress=" + address + " adminAccess=" + adminAccess;
	}
}
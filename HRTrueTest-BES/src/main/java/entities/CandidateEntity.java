package main.java.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import main.java.model.dao.CandidateData;

@Entity
@Table(name="candidate")
public class CandidateEntity {
	@Id
	@Column(name="aa_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	long id;
	@Column(name="email", length=30, unique=true)
	String email;
	@Column(name="first_name", length=25)
	String firstName;
	@Column(name="last_name", length=30)
	String lastName;
	public CandidateEntity(String email, String firstName, String lastName) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	public CandidateEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public long getId() {
		return id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CandidateEntity other = (CandidateEntity) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	public static CandidateData convertToCandidateData(CandidateEntity entity){
		return new CandidateData(entity.id, entity.email, entity.firstName, entity.lastName);
	}
	public static List<CandidateData> convertToCandidateDataList(Iterable<CandidateEntity> entities){
		List<CandidateData> candidates = new ArrayList<CandidateData>();
		for (CandidateEntity entity : entities){
			candidates.add(convertToCandidateData(entity));
		}
		return candidates;
	}
}

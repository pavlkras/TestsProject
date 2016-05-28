package main.java.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import main.java.model.config.AuthorityName;
import main.java.model.dao.CandidateData;
import main.java.utils.Crypto;

@Entity
@Table(name="candidate")
@GenericGenerator(name="credentials-primarykey", strategy="foreign",
parameters={@Parameter(name="property", value="credentials")
})
public class CandidateEntity {
	@Id
	@Column(name="aa_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	long id;
	@MapsId
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "aa_id")
	CredentialsEntity credentials;
	@Column(name="first_name", length=25)
	String firstName;
	@Column(name="last_name", length=30)
	String lastName;
	@Column(name="temp_passwd", length=8)
	String tmpPasswd;
	public CandidateEntity(String email, String firstName, String lastName) {
		super();
		this.tmpPasswd = generateTemporaryPassword();
		this.firstName = firstName;
		this.lastName = lastName;
		
		String hash = Crypto.generateHash(tmpPasswd);
		credentials = new CredentialsEntity(email, hash, (byte)(AuthorityName.ROLE_USER.code()|AuthorityName.ROLE_CANDIDATE.code()));
	}
	public CandidateEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CredentialsEntity getCredentials() {
		return credentials;
	}
	public void setCredentials(CredentialsEntity credentials) {
		this.credentials = credentials;
	}
	public String getTmpPasswd() {
		return tmpPasswd;
	}
	public void setTmpPasswd(String tmpPasswd) {
		this.tmpPasswd = tmpPasswd;
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
		int result = credentials.hashCode();
		result = prime * result + ((credentials.login == null) ? 0 : credentials.login.hashCode());
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
		if (credentials.login == null) {
			if (other.credentials.login != null)
				return false;
		} else if (!credentials.login.equals(other.credentials.login))
			return false;
		if (id != other.id)
			return false;
		return credentials.equals(obj);
	}
	public static CandidateData convertToCandidateData(CandidateEntity entity){
		CandidateData candidateData = new CandidateData(entity.id,
				entity.credentials.login, entity.credentials.password,
				entity.firstName, entity.lastName,
				CredentialsEntity.convertDbMaskToAuthorities(entity.credentials.roles));
		candidateData.setTmpPasswd(entity.tmpPasswd);
		return candidateData;
	}
	public static List<CandidateData> convertToCandidateDataList(Iterable<CandidateEntity> entities){
		List<CandidateData> candidates = new ArrayList<CandidateData>();
		for (CandidateEntity entity : entities){
			candidates.add(convertToCandidateData(entity));
		}
		return candidates;
	}
	
	private String generateTemporaryPassword() {
		Random random = new Random();
		final String alphabet = "0123456789abcdefghijklnmopqrstuvwxyzABCDEFGHIJKLNMOPQRSTUVWXYZ";
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < 8; ++i){
			ret.append(alphabet.charAt(random.nextInt(alphabet.length())));
		}
		return ret.toString();
	}
}

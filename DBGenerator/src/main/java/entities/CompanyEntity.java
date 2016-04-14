package main.java.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="company")
@GenericGenerator(name="credentials-primarykey", strategy="foreign",
parameters={@Parameter(name="property", value="credentials")
})
public class CompanyEntity {
	@Id
	@GeneratedValue(generator = "credentials-primarykey")
	@Column(name="aa_id")
	long id;
	@MapsId
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "aa_id")
	CredentialsEntity credentials;
	@Column(name="name", length=50, nullable=false)
	String name;
	@Column(name="site", length=50)
	String site;
	@Column(name="specialization")
	byte specialization;
	@Column(name="employees_amnt")
	byte employees_amnt;
	@OneToMany(mappedBy="company",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	Set<TemplateEntity> templates;
	
	public CompanyEntity(){
		
	}
	
	public CompanyEntity(CredentialsEntity credentials, String name, String site,
			byte specialization, byte employees_amnt){
		setCredentials(credentials);
		setName(name);
		setSite(site);
		setSpecialization(specialization);
		setEmployees_amnt(employees_amnt);
	}
	
	public CredentialsEntity getCredentials() {
		return credentials;
	}
	public void setCredentials(CredentialsEntity credentials) {
		this.credentials = credentials;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public byte getSpecialization() {
		return specialization;
	}
	public void setSpecialization(byte specialization) {
		this.specialization = specialization;
	}
	public byte getEmployees_amnt() {
		return employees_amnt;
	}
	public void setEmployees_amnt(byte employees_amnt) {
		this.employees_amnt = employees_amnt;
	}
	public Set<TemplateEntity> getTemplates() {
		return templates;
	}
	public void setTemplates(Set<TemplateEntity> templates) {
		this.templates = templates;
	}
	public long getId() {
		return id;
	}
}

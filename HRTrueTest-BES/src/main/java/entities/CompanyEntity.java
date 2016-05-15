package main.java.entities;

import java.util.ArrayList;
import java.util.List;
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

import main.java.model.dao.CompanyData;

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
	@Column(name="activity_type")
	byte activityType;
	@Column(name="employees_amnt")
	byte employeesAmnt;
	@OneToMany(mappedBy="company",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	Set<TemplateEntity> templates;
	
	public CompanyEntity(){
		
	}
	
	public CompanyEntity(CredentialsEntity credentials, String name, String site,
			byte activityType, byte employeesAmnt){
		setCredentials(credentials);
		setName(name);
		setSite(site);
		setActivityType(activityType);
		setEmployeesAmnt(employeesAmnt);
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
	public byte getActivityType() {
		return activityType;
	}
	public void setActivityType(byte activityType) {
		this.activityType = activityType;
	}
	public byte getEmployeesAmnt() {
		return employeesAmnt;
	}
	public void setEmployeesAmnt(byte employeesAmnt) {
		this.employeesAmnt = employeesAmnt;
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
	
	public static CompanyData convertToCompanyData(CompanyEntity entity){
		return new CompanyData(entity.credentials.login, entity.credentials.password, entity.name,
				entity.site, entity.activityType, entity.employeesAmnt, 
				CredentialsEntity.convertDbMaskToAuthorities(entity.credentials.roles));
	}
	
	public static Iterable<CompanyData> convertToCompanyDataList(Iterable<CompanyEntity> entities){
		List<CompanyData> companyDatas = new ArrayList<CompanyData>();
		for (CompanyEntity entity : entities){
			companyDatas.add(convertToCompanyData(entity));
		}
		return companyDatas;
	}
}

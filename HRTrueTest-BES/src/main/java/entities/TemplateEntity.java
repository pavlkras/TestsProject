package main.java.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="template")
public class TemplateEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="aa_id")
	long id;
	@Column(name="name", length=30, nullable=false)
	String name;
	@ManyToOne
	@JoinColumn(name="company_id")
	CompanyEntity company = null;
	@OneToMany(mappedBy="template",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	Set<TemplateItemEntity> items;
	
	public TemplateEntity(String name, CompanyEntity company) {
		super();
		this.name = name;
		this.company = company;
	}
	public TemplateEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CompanyEntity getCompany() {
		return company;
	}
	public void setCompany(CompanyEntity company) {
		this.company = company;
	}
	public Set<TemplateItemEntity> getItems() {
		return items;
	}
	public void setItems(Set<TemplateItemEntity> items) {
		this.items = items;
	}
	public long getId() {
		return id;
	}	
}

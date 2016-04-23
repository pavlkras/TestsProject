package main.java.entities;

import java.util.HashSet;
import java.util.List;
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
import javax.persistence.UniqueConstraint;

import main.java.model.dao.TemplateData;
import main.java.model.dao.TemplateItemData;

@Entity
@Table(name="template", uniqueConstraints={@UniqueConstraint(columnNames={"name","company_id"})})
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
	@OneToMany(mappedBy="template",cascade=CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval=true)
	Set<TemplateItemEntity> items;
	@OneToMany(mappedBy="template",fetch=FetchType.EAGER,orphanRemoval=true)
	Set<TestEntity> tests;
	
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
	public Set<TestEntity> getTests() {
		return tests;
	}
	public void setTests(Set<TestEntity> tests) {
		this.tests = tests;
	}
	public long getId() {
		return id;
	}	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		TemplateEntity other = (TemplateEntity) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public static TemplateData convertToTemplateData(TemplateEntity entity){
		long id = entity.id;
		String name = entity.name;
		List<TemplateItemData> items = (List<TemplateItemData>) TemplateItemEntity.convertToTemplateItemDataList(entity.items);
		return new TemplateData(id, name, items);
	}
	
	public static Iterable<TemplateData> convertToTemplateDataSet(Iterable<TemplateEntity> entities) {
		Set<TemplateData> templates = new HashSet<TemplateData>();
		for(TemplateEntity entity : entities){
			templates.add(convertToTemplateData(entity));
		}
		return templates;
	}
}

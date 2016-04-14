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
@Table(name="category")
public class CategoryEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="aa_id")
	int id;
	@Column(name="name", length=30, nullable=false)
	String name;
	@ManyToOne
	@JoinColumn(name="parent_category_id")
	CategoryEntity parent = null;
	@OneToMany(mappedBy="category",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	Set<TemplateItemEntity> templateItems;
	
	public CategoryEntity() {
	}
	public CategoryEntity(String name) {
		this(name, null);
	}
	public CategoryEntity(String name, CategoryEntity parent) {
		this.name = name;
		this.parent = parent;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CategoryEntity getParent() {
		return parent;
	}
	public void setParent(CategoryEntity parent) {
		this.parent = parent;
	}
	public Set<TemplateItemEntity> getTemplateItems() {
		return templateItems;
	}
	public void setTemplateItems(Set<TemplateItemEntity> templateItems) {
		this.templateItems = templateItems;
	}
}

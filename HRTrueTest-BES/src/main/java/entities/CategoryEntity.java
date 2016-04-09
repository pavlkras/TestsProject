package main.java.entities;

import java.util.List;

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

@Entity(name="category")
public class CategoryEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="aa_id")
	byte id;
	@Column(name="name", length=30, nullable=false)
	String name;
	@ManyToOne
	@JoinColumn(name="parent_category_id")
	CategoryEntity parent = null;
	@OneToMany(mappedBy="category",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	List<TemplateItemEntity> templateItems;
}

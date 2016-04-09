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

@Entity(name="template")
public class TemplateEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="aa_id")
	long id;
	@Column(name="name", length=30, nullable=false)
	String name;
	@ManyToOne
	@JoinColumn(name="company_id")
	CompanyEntity company = null;
	@OneToMany(mappedBy="template",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	List<TemplateItemEntity> items;
}

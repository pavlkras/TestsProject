package main.java.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="template_item")
public class TemplateItemEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="aa_id")
	long id;
	@Column(name="difficulty", nullable=false)
	byte difficulty;
	@Column(name="amount", nullable=false)
	byte amount;
	@ManyToOne
	@JoinColumn(name="template_id", referencedColumnName="aa_id", nullable=false)
	TemplateEntity template;
	
	@ManyToOne
	@JoinColumn(name="category_id", referencedColumnName="aa_id", nullable=false)
	CategoryEntity category;
	
	
}

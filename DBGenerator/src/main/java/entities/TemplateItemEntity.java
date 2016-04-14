package main.java.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="template_item")
public class TemplateItemEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
	
	
	public TemplateItemEntity(byte difficulty, byte amount, TemplateEntity template, CategoryEntity category) {
		super();
		this.difficulty = difficulty;
		this.amount = amount;
		this.template = template;
		this.category = category;
	}
	public TemplateItemEntity() {
		super();
		// TODO Auto-generated constructor stub
	}


	public byte getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(byte difficulty) {
		this.difficulty = difficulty;
	}
	public byte getAmount() {
		return amount;
	}
	public void setAmount(byte amount) {
		this.amount = amount;
	}
	public TemplateEntity getTemplate() {
		return template;
	}
	public void setTemplate(TemplateEntity template) {
		this.template = template;
	}
	public CategoryEntity getCategory() {
		return category;
	}
	public void setCategory(CategoryEntity category) {
		this.category = category;
	}
	public long getId() {
		return id;
	}
}

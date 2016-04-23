package main.java.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import main.java.model.dao.TemplateItemData;

@Entity
@Table(name="template_item", uniqueConstraints={@UniqueConstraint(columnNames={"cat_diff_id","template_id"})})
public class TemplateItemEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="aa_id")
	long id;
	@Column(name="amount", nullable=false)
	byte amount;
	@ManyToOne
	@JoinColumn(name="cat_diff_id", referencedColumnName="aa_id", nullable=false)
	CatDiffEntity catDiff;
	@ManyToOne
	@JoinColumn(name="template_id", referencedColumnName="aa_id", nullable=false)
	TemplateEntity template;
	
	public TemplateItemEntity(byte amount, CatDiffEntity catDiff, TemplateEntity template) {
		super();
		this.catDiff = catDiff;
		this.amount = amount;
		this.template = template;
	}
	public TemplateItemEntity() {
		super();
		// TODO Auto-generated constructor stub
	}


	public CatDiffEntity getCatDiff() {
		return catDiff;
	}
	public void setCatDiff(CatDiffEntity catDiff) {
		this.catDiff = catDiff;
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
		TemplateItemEntity other = (TemplateItemEntity) obj;
		if (id != other.id)
			return false;
		return true;
	}
	public static TemplateItemData convertToTemplateItemData(TemplateItemEntity entity){
		return new TemplateItemData(entity.id, entity.catDiff.difficulty, entity.amount, entity.catDiff.category);
	}
	
	public static List<TemplateItemData> convertToTemplateItemDataList(Iterable<TemplateItemEntity> entities){
		List<TemplateItemData> ret = new ArrayList<TemplateItemData>();
		for (TemplateItemEntity entity : entities){
			ret.add(convertToTemplateItemData(entity));
		}
		return ret;
	}
}

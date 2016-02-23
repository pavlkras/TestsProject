package tel_ran.tests.entitys;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import json_models.IJsonModels;
import json_models.JSONKeys;

@Entity
@Table(name="Categories")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
		name="discriminator",
		discriminatorType=DiscriminatorType.STRING
		)
public abstract class Category implements IJsonModels {
	
	@Id
	@GeneratedValue
	protected int id; 
	
	@Column(nullable=false)
	protected String categoryName;
	
	@ManyToOne
	protected Category parentCategory;
	
	@OneToMany(mappedBy="parentCategory", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	protected List<Category> childrenCategory;
	
	@ManyToOne
	protected Company company;
	
	//these three fields are old and need only temporary
	protected String metaCategory;
	protected String category1;
	protected String category2;	
	
	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
	protected List<Question> questions;
	
	@Column(unique=true)
	protected String controlName;
			
	public String getControlName() {
		return controlName;
	}

	public void setControlName(String controlName) {
		this.controlName = controlName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Category getParentCategory() {
		return parentCategory;
	}

	public abstract void setParentCategory(Category parentCategory);


	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;		
		
		if(this.parentCategory!=null) {
			this.parentCategory.setCompany(company);
		}
	}

	public String getMetaCategory() {
		return metaCategory;
	}

	public void setMetaCategory(String metaCategory) {
		this.metaCategory = metaCategory;
	}

	public String getCategory1() {
		return category1;
	}

	public void setCategory1(String category1) {
		this.category1 = category1;
	}

	public String getCategory2() {
		return category2;
	}

	public void setCategory2(String category2) {
		this.category2 = category2;
	}

	public int getId() {
		return id;
	}
	
	public void setControlName(int companyId) {
		String delimetr = "__";
		StringBuilder str = new StringBuilder();
		str.append(this.categoryName).append(delimetr);
		if(this.parentCategory!=null) {
			str.append(this.parentCategory.getId());
		} else {
			str.append("0");
		}
		str.append(delimetr).append(companyId);
		this.controlName = str.toString();
	}

	public List<Category> getChildrenCategory() {
		return childrenCategory;
	}

	public void setChildrenCategory(List<Category> childrenCategory) {
		this.childrenCategory = childrenCategory;
	}
	
	public abstract boolean isContainOpenQuestions();
	public abstract boolean isContainAmericanTests();

	public abstract void copyCategory(Category category);
	
	@Override
	public String getString() throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getJSON() throws JSONException {
		JSONObject jsn = new JSONObject();
		if(this.parentCategory!=null) {
			jsn.put(JSONKeys.QUESTION_CATEGORY1, this.parentCategory.getCategoryName());
			jsn.put(JSONKeys.QUESTION_CATEGORY2, this.categoryName);
		} else {
			jsn.put(JSONKeys.QUESTION_CATEGORY1, this.categoryName);
		}
		jsn.put(JSONKeys.CATEGORY_ID, this.id);	
		return jsn;
	}

	@Override
	public JSONArray getJSONArray() throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTopCategory() {
		if(this.parentCategory==null) 
			return this.categoryName;
		else
			return this.parentCategory.categoryName;
		
	}

	public String getSecondCategory() {
		if(this.parentCategory==null)
			return null;
		else
			return this.categoryName;
		
	}
	

}

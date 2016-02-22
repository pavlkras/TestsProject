package tel_ran.tests.entitys;

import java.util.ArrayList;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.json.JSONException;
import org.json.JSONObject;

import json_models.JSONKeys;

@Entity
@Table(name="Categories")
@DiscriminatorValue(value="Custom")
public class CategoryCustom extends Category {

	public static final String NO_CATEGORY = "no category";
	public static final String MC_CUSTOM = "custom";
	
	private Boolean containOpenQuestions;
	private Boolean containAmericanTests;
	
	
	public CategoryCustom(){
		
	}
	
	public CategoryCustom(CategoryCustom child, String catName) {
		this.categoryName = catName;
		this.category1 = this.categoryName;		
		this.childrenCategory = new ArrayList<>();
		this.childrenCategory.add(child);
		this.metaCategory = MC_CUSTOM;			
	}

	public CategoryCustom(String dataJson) throws JSONException {
		
		JSONObject jsn = new JSONObject(dataJson);
		
		this.metaCategory = MC_CUSTOM;
		
		if(jsn.has(JSONKeys.QUESTION_CATEGORY2)) {
			
			this.categoryName = jsn.getString(JSONKeys.QUESTION_CATEGORY2);
			this.category2 = this.categoryName;
			
			if(jsn.has(JSONKeys.QUESTION_CATEGORY1)) {
				this.category1 = jsn.getString(JSONKeys.QUESTION_CATEGORY1);
				this.parentCategory = new CategoryCustom(this, this.category1);				
			}
		} else {
			
			
			if(jsn.has(JSONKeys.QUESTION_CATEGORY1)) {
				this.categoryName = jsn.getString(JSONKeys.QUESTION_CATEGORY1);	
				
			} else {
				this.categoryName = NO_CATEGORY;
			}
			this.category1 = this.categoryName;			
			
		}
		
			
	}

	public boolean isContainOpenQuestions() {
		if(this.containOpenQuestions==null) return false;
		return containOpenQuestions;
	}

	public void setContainOpenQuestions(Boolean containOpenQuestions) {
		this.containOpenQuestions = containOpenQuestions;
		if(this.parentCategory!=null) {
			((CategoryCustom)this.parentCategory).setContainOpenQuestions(containOpenQuestions);				
						
		}
	}

	public boolean isContainAmericanTests() {
		if(this.containAmericanTests==null) return false;
		return containAmericanTests;
	}

	public void setContainAmericanTests(Boolean containAmericanTests) {
		this.containAmericanTests = containAmericanTests;
		if(this.parentCategory!=null) {			
			((CategoryCustom)this.parentCategory).setContainAmericanTests(containOpenQuestions);				
		}
	}	

	@Override
	public void setParentCategory(Category parentCategory) {
		if(parentCategory instanceof CategoryCustom ) {
			this.parentCategory = parentCategory;
		}		
	}

	@Override
	public void copyCategory(Category category) {
		this.category1 = category.category1;
		this.category2 = category.category2;
		this.categoryName = category.categoryName;
		
		if(this.containAmericanTests==null) {
			this.containAmericanTests = category.isContainAmericanTests();
		} else {
			this.containAmericanTests = category.isContainAmericanTests() ? category.isContainAmericanTests() : this.containAmericanTests; 
		}
		
		if(this.containOpenQuestions==null) {
			this.containOpenQuestions = category.isContainOpenQuestions();
		} else {
			this.containOpenQuestions = category.isContainOpenQuestions() ? category.isContainOpenQuestions() : this.containOpenQuestions; 
		}
		this.parentCategory = category.parentCategory;
		this.metaCategory = category.metaCategory;		
		this.controlName = category.controlName;
		this.company = category.company;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((containAmericanTests == null) ? 0 : containAmericanTests.hashCode());
		result = prime * result + ((containOpenQuestions == null) ? 0 : containOpenQuestions.hashCode());
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
		
		CategoryCustom other = (CategoryCustom) obj;
		
		if (this.controlName == null) {
			if(other.controlName !=null)
				return false;
		} else if(!this.controlName.equals(other.controlName))
			return false;
		
		
		if (this.category1 == null) {
			if(other.category1 !=null)
				return false;
		} else if(!this.category1.equals(other.category1))
			return false;		
		
		
		
		if (this.category2 == null) {
			if(other.category2 !=null)
				return false;
		} else if(!this.category2.equals(other.category2))
			return false;
		
		System.out.println("E6");
		
				
		if (this.metaCategory == null) {
			if(other.metaCategory !=null)
				return false;
		} else if(!this.metaCategory.equals(other.metaCategory))
			return false;
		
		System.out.println("E8");
				
		if (this.categoryName == null) {
			if(other.categoryName !=null)
				return false;
		} else if(!this.categoryName.equals(other.categoryName))
			return false;
		
		System.out.println("E10");
		
		if (containAmericanTests == null) {
			if (other.containAmericanTests != null && other.containAmericanTests)
				return false;
		} else if (containAmericanTests && (other.containAmericanTests==null || !other.containAmericanTests)) {
			return false;
		} else if (!containAmericanTests &&(other.containAmericanTests!=null && other.containAmericanTests))
			return false;
		
		
		System.out.println("E11");
		
		if (containOpenQuestions == null) {
			if (other.containOpenQuestions != null && other.containOpenQuestions)
				return false;
		} else if (containOpenQuestions && (other.containOpenQuestions==null || !other.containOpenQuestions)) {
			return false;
		} else if (!containOpenQuestions &&(other.containOpenQuestions!=null && other.containOpenQuestions))
			return false;
		
		
		return true;
	}

	public void setQuestionType(QuestionCustom question) {
		if(question.getClass().equals(QuestionCustomOpen.class)) {
			this.containOpenQuestions = true;
		} else if(question.getClass().equals(QuestionCustomTest.class)) {
			this.containAmericanTests = true;
		}
		
	}

	

	
	
	
}

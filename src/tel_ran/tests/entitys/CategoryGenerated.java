package tel_ran.tests.entitys;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="Categories")
@DiscriminatorValue(value="Generated")
public class CategoryGenerated extends Category {

	//true if it is possible to create questions for this category
	private boolean isFinal;
	
	
	
		
	public boolean isFinal() {
		return isFinal;
	}	
	
	public CategoryGenerated() {
		super();
	}

	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}

	public CategoryGenerated(String metaCategory) {
		
		this.categoryName = metaCategory;
		this.metaCategory = metaCategory;		
	}

	public CategoryGenerated(Category cat, String cat1) {
		this.categoryName = cat1;
		this.metaCategory = cat.getCategoryName();
		this.category1 = cat1;
		this.parentCategory = cat;		
	}

	@Override
	public void setParentCategory(Category parentCategory) {
		if(parentCategory instanceof CategoryGenerated) {
			this.parentCategory = parentCategory;
		}		
	}

	@Override
	public boolean isContainOpenQuestions() {
		
		return false;
	}
	
	@Override
	public boolean isContainAmericanTests() {
		return true;
	}

	@Override
	public void copyCategory(Category category) {
		this.category1 = category.category1;
		this.category2 = category.category2;
		this.categoryName = category.categoryName;
		this.parentCategory = category.parentCategory;
		this.metaCategory = category.metaCategory;		
		this.controlName = category.controlName;		
	}
	
}

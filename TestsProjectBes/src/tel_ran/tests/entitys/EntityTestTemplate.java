package tel_ran.tests.entitys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * EntityTestTemplate - entity for templates of tests.
 * Each template has information about its contain in JSON-format. There are fields: metaCategory, category1,
 * category2, level of difficulty, number of questions. All this info is kept in the column "template".
 * Each template can be created by Company (its ID is in the column "entityCompany") or by Administrator 
 * (null in entityCompany) 
 * Templates should have names and can have some custom category. The last field can be empty. These categories are created
 * by company or by admin. They are used only to group templates in the view. 
 */
@Entity
public class EntityTestTemplate {
	
	@Id
	@GeneratedValue
	private long id;
	
	/**
	 * The name of template. It's unique for each user.
	 */
	@Column(nullable=false)
	private String templateName;
	
	/**
	 * Content of template in JSON-format. It's a JSON with fields:
	 * questionsId : [] - array with id of questions (EntityQuestionAttributes) - long
	 * template : [] - array width data:
	 * ---- metaCategory : String
	 * ---- category1 : String
	 * ---- category2 : String
	 * ---- difficulty : int
	 * ---- amount : int
	 */
	@Column(length=2000)
	private String template;
	
	/**
	 * Link to the company that owns this template.
	 * If it was created by Admin this field is null 
	 */
	@ManyToOne
	private EntityCompany entityCompany;
		
	/**
	 * Custom category. Each company can create some categories. They are used only in view and searching
	 */
	private String customCategory;	
	
	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public EntityCompany getEntityCompany() {
		return entityCompany;
	}

	public void setEntityCompany(EntityCompany entityCompany) {
		this.entityCompany = entityCompany;
	}

	public String getCustomCategory() {
		return customCategory;
	}

	public void setCustomCategory(String customCategory) {
		this.customCategory = customCategory;
	}

	public long getId() {
		return id;
	}
	
	

}

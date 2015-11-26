package tel_ran.tests.entitys;

import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import json_models.JSONKeys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
	
	public void setTemplate(List<Long> questionsId, List<Map<String,Object>> templates) {
		JSONObject jsn = new JSONObject();	
		try{
		if(questionsId!=null) {
			JSONArray array = new JSONArray();
			for(long id : questionsId) {
				if(id<0) continue;
				JSONObject jsnOb = new JSONObject();				
				jsnOb.put(JSONKeys.TEMPLATE_QUESTION_ID, id);				
			}
			jsn.put(JSONKeys.TEMPLATE_QUESTIONS, array);			
		}
		
		if(templates!=null) {
			JSONArray array2 = new JSONArray();
			for(Map<String, Object> template : templates) {
				if(template==null) continue;
				JSONObject jsnTemp = new JSONObject();
				jsnTemp.put(JSONKeys.TEMPLATE_META_CATEGORY, template.get(JSONKeys.TEMPLATE_META_CATEGORY));
				jsnTemp.put(JSONKeys.TEMPLATE_QUANTITY, template.get(JSONKeys.TEMPLATE_QUANTITY));
				jsnTemp.put(JSONKeys.TEMPLATE_CATEGORY1, template.get(JSONKeys.TEMPLATE_CATEGORY1));
				jsnTemp.put(JSONKeys.TEMPLATE_CATEGORY2, template.get(JSONKeys.TEMPLATE_CATEGORY2));
				jsnTemp.put(JSONKeys.TEMPLATE_DIFFICULTY, template.get(JSONKeys.TEMPLATE_DIFFICULTY));
			}
			jsn.put(JSONKeys.TEMPLATE_CATEGORIES, array2);			
		}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		this.template = jsn.toString();
		
	}
	
	

}

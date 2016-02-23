package tel_ran.tests.entitys;


import javax.persistence.Entity;
import javax.persistence.Table;

import org.json.JSONException;
import org.json.JSONObject;

import json_models.JSONKeys;
import tel_ran.tests.processor.TestProcessor;


@Entity
@Table(name="Questions")
public abstract class GeneratedQuestion extends Question {

	private String subtype;
	
	

	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}
		
	public static GeneratedQuestion getQuestionByCategory(Category category) {		
		if(category.getCategoryName().equals(TestProcessor.MC_PROGRAMMING))
			return new GeneratedProgrammingQuestion();
		if(category.getParentCategory()!=null && category.getParentCategory().getCategoryName().equals(TestProcessor.MC_PROGRAMMING))
			return new GeneratedProgrammingQuestion();
		return new GeneratedCommonQuestion();
	}

	abstract public GeneratedQuestion getNewInstance();
	
	@Override
	public JSONObject getJSON() throws JSONException {
		
		JSONObject jsn = this.getShortJSON();
		//TO DO
		
		
		return jsn;		
	}
	
	@Override
	public JSONObject getShortJSON() throws JSONException {

		JSONObject jsn = this.category.getJSON();		
		jsn.put(JSONKeys.QUESTION_ID, this.id);
		jsn.put(JSONKeys.QUESTION_DIFFICULTY_LVL, this.levelOfDifficulty);		
		jsn.put(JSONKeys.QUESTION_HAS_IMAGE, true);				
		return jsn;	
	}

	
}

package tel_ran.tests.entitys;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;


import org.json.JSONException;
import org.json.JSONObject;

import json_models.JSONKeys;
import tel_ran.tests.services.common.IPublicStrings;
import tel_ran.tests.services.subtype_handlers.ITestQuestionHandler;
import tel_ran.tests.services.subtype_handlers.OpenQuestionHandler;


@Entity
@Table(name="Questions")
@DiscriminatorValue(value="OpenQuestion")
public class QuestionCustomOpen extends QuestionCustom {
	

	@Override
	public List<Texts> setJsonData(String dataJson) throws JSONException {
		
		JSONObject jsn = new JSONObject(dataJson);
		
		if(jsn.has(JSONKeys.QUESTION_TITLE)) {
			this.setTitle(JSONKeys.QUESTION_TITLE);			
		} else {
			this.setTitle(IPublicStrings.COMPANY_QUESTION_QUESTION);			
		}
		
		if(jsn.has(JSONKeys.QUESTION_IMAGE)) {
			this.fileLocationLink = jsn.getString(JSONKeys.QUESTION_IMAGE);
		}
		
		this.levelOfDifficulty = jsn.getInt(JSONKeys.QUESTION_DIFFICULTY_LVL);
		this.description = jsn.getString(JSONKeys.QUESTION_DESCRIPTION);
		return null;
	}

	@Override
	public JSONObject getShortJSON() throws JSONException {

		JSONObject jsn = this.category.getJSON();		
		jsn.put(JSONKeys.QUESTION_ID, this.id);
		jsn.put(JSONKeys.QUESTION_DIFFICULTY_LVL, this.levelOfDifficulty);
		if(this.fileLocationLink!=null)
			jsn.put(JSONKeys.QUESTION_HAS_IMAGE, true);
		jsn.put(JSONKeys.QUESTION_SHORT_DESCRIPTION, this.getShortDescription());
		jsn.put(JSONKeys.QUESTION_TYPE, IPublicStrings.COMPANY_QUESTION);
		return jsn;
	}

	@Override
	public JSONObject getJSON() throws JSONException {
		
		JSONObject jsn = this.getShortJSON();
		//TO DO
		
		
		return jsn;		
	}

	@Override
	public ITestQuestionHandler getHandler() {	
		ITestQuestionHandler result = new OpenQuestionHandler();
		result.setQuestion(this);
		
		return result;
	}	
}

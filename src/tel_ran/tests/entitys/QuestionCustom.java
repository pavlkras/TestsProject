package tel_ran.tests.entitys;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.json.JSONException;
import org.json.JSONObject;

import json_models.JSONKeys;
import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.common.IPublicStrings;

@Entity
@Table(name="Questions")
public abstract class QuestionCustom extends Question {

	@Column(length = 2500)
	protected String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static QuestionCustom getInstanceByJson(String dataJson) throws JSONException {
		JSONObject jsn = new JSONObject(dataJson);
		String metaCategory = null;
		if(jsn.has(JSONKeys.QUESTION_META_CATEGORY)) {
			metaCategory = jsn.getString(JSONKeys.QUESTION_META_CATEGORY);
		} else {
			if(jsn.has(JSONKeys.QUESTION_ANSWER_OPTIONS)) {
				metaCategory = IPublicStrings.COMPANY_AMERICAN_TEST;
			} else {
				metaCategory = IPublicStrings.COMPANY_QUESTION;
			}
		}
		
		switch(metaCategory) {		
			case IPublicStrings.COMPANY_AMERICAN_TEST : return new QuestionCustomTest();
			case IPublicStrings.COMPANY_QUESTION : return new QuestionCustomOpen();		
		}		
		
		return null;
	}

	public abstract List<Texts> setJsonData(String dataJson) throws JSONException;
	
	public String getShortDescription() {
		String result = null;
		if(this.description==null && this.fileLocationLink!=null) {
			result = "See the image";
		} else {
			result = this.description.replaceAll("\\n", "  ");
			int len = result.length();
			if(len>ICommonData.SHORT_DESCR_LEN) 
				result = result.substring(0, ICommonData.SHORT_DESCR_LEN);	
		}
		return result;
	}
	
}

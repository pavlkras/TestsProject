package json_models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.services.common.ICommonData;

public class QuestionModel implements IJsonModels {

	private long questionId;
	private String metaCategory;
	private String category1;
	private String category2;
	private int difLevel;
	private boolean containsImage;
	private String shortDescription;
	
	
	public QuestionModel (long questionId, String metaCategory, String category1, String category2, int difLevel, boolean isImage) {
		this.questionId = questionId;
		this.metaCategory = metaCategory;
		this.category1 = category1;
		this.category2 = category2;
		this.difLevel = difLevel;
		this.containsImage = isImage;
	}
	
	public void addShortDescription(String shrt) {
		if(shrt==null && this.containsImage) {
			this.shortDescription = "See the image";
		} else {
			shrt = shrt.replaceAll("\\n", "  ");
			int len = shrt.length();
			if(len>ICommonData.SHORT_DESCR_LEN) 
				shrt = shrt.substring(0, ICommonData.SHORT_DESCR_LEN);	
			this.shortDescription = shrt;
		}
	}
			
	@Override
	public JSONObject getJSON() throws JSONException {
		JSONObject jsn = new JSONObject();
		jsn.put(JSONKeys.QUESTION_ID, this.questionId);
		jsn.put(JSONKeys.QUESTION_META_CATEGORY, this.metaCategory);
		if(this.category1!=null) 
			jsn.put(JSONKeys.QUESTION_CATEGORY1, this.category1);
		if(this.category2!=null)
			jsn.put(JSONKeys.QUESTION_CATEGORY2, this.category2);
		jsn.put(JSONKeys.QUESTION_DIFFICULTY_LVL, this.difLevel);
		jsn.put(JSONKeys.QUESTION_HAS_IMAGE, this.containsImage);
		if(this.shortDescription!=null)
			jsn.put(JSONKeys.QUESTION_SHORT_DESCRIPTION, this.shortDescription);
		return jsn;
	}
	
	@Override
	public String getString() throws JSONException {		
		return getJSON().toString();
	}

	@Override
	public JSONArray getJSONArray() throws JSONException {		
		return null;
	}

}

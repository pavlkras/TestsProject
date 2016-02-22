package tel_ran.tests.services.processes;

import org.json.JSONException;
import org.json.JSONObject;

import json_models.JSONKeys;
import tel_ran.tests.services.common.ICommonData;

public class CategoryResults {


	String type;
	int categoryId;
	int numQuestions;
	int numAnswers;
	int numCorrectAnswers;
	int numUncheckedUnswers;
					
	public CategoryResults(int categoryId2, String categoryType) {
		this.categoryId = categoryId2;
		this.type = categoryType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;		
		result = prime * result + categoryId;
		return result;
	}
	
	public void setAnswerResult(int res) {
		numQuestions++;
		if(res!=ICommonData.STATUS_NO_ANSWER) {
			numAnswers++;
			if(res==ICommonData.STATUS_CORRECT)
				numCorrectAnswers++;
			else if (res==ICommonData.STATUS_UNCHECKED)
				numUncheckedUnswers++;				
		}			
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoryResults other = (CategoryResults) obj;	
		if (categoryId != other.categoryId)
			return false;
		return true;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public int getNumQuestions() {
		return numQuestions;
	}
	public void setNumQuestions(int numQuestions) {
		this.numQuestions = numQuestions;
	}
	public int getNumAnswers() {
		return numAnswers;
	}
	public void setNumAnswers(int numAnswers) {
		this.numAnswers = numAnswers;
	}
	public int getNumCorrectAnswers() {
		return numCorrectAnswers;
	}
	public void setNumCorrectAnswers(int numCorrectAnswers) {
		this.numCorrectAnswers = numCorrectAnswers;
	}
	public int getNumUncheckedUnswers() {
		return numUncheckedUnswers;
	}
	public void setNumUncheckedUnswers(int numUncheckedUnswers) {
		this.numUncheckedUnswers = numUncheckedUnswers;
	}
	
	public JSONObject getJson() throws JSONException {
		JSONObject jsn = new JSONObject();
		jsn.put(JSONKeys.RESULTS_CATEGORY_TYPE, type);
		jsn.put(JSONKeys.RESULTS_CATEGORY_ID, categoryId);
		jsn.put(JSONKeys.RESULTS_CATEGORY_NUM_QUESTIONS, numQuestions);
		jsn.put(JSONKeys.RESULTS_CATEGORY_ID, numAnswers);
		jsn.put(JSONKeys.RESULTS_CATEGORY_NUM_CORRECT_ANSWERS, numCorrectAnswers);
		if(numUncheckedUnswers>0)
			jsn.put(JSONKeys.RESULTS_CATEGORY_NUM_UNCHECKED_ANSWERS, numUncheckedUnswers);
		return jsn;
	}		
	
}

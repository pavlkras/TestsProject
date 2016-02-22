package tel_ran.tests.entitys;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import json_models.JSONKeys;
import tel_ran.tests.services.common.IPublicStrings;
import tel_ran.tests.services.subtype_handlers.AmericanTestQuestionHandler;
import tel_ran.tests.services.subtype_handlers.ITestQuestionHandler;

@Entity
@Table(name="Questions")
@DiscriminatorValue(value="AmericanTest")
public class QuestionCustomTest extends QuestionCustom {
	
	private String correctAnswer;
	private int numberOfAnswerOptions;
		
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "question")
	@Fetch(value = FetchMode.SUBSELECT)
	List<Texts> texts;
	
	
	
	
	public String getCorrectAnswerChar() {
		return correctAnswer;
	}
	public void setCorrectAnswerChar(String correctAnswerChar) {
		this.correctAnswer = correctAnswerChar;
	}
	public int getNumberOfAnswerOptions() {
		return numberOfAnswerOptions;
	}
	public void setNumberOfAnswerOptions(int numberOfAnswerOptions) {
		this.numberOfAnswerOptions = numberOfAnswerOptions;
	}
	public List<Texts> getTextsList() {
		return texts;
	}
	public void setTextsList(List<Texts> textsList) {
		this.texts = textsList;
	}
	
	@Override
	public List<Texts> setJsonData(String dataJson) throws JSONException {
		
		JSONObject jsn = new JSONObject(dataJson);
		
		if(jsn.has(JSONKeys.QUESTION_TITLE)) {
			this.setTitle(JSONKeys.QUESTION_TITLE);			
		} else {
			this.setTitle(IPublicStrings.COMPANY_AMERICAN_TEST_QUESTION);			
		}
		
		if(jsn.has(JSONKeys.QUESTION_IMAGE)) {
			this.fileLocationLink = jsn.getString(JSONKeys.QUESTION_IMAGE);
		}
		
		this.levelOfDifficulty = jsn.getInt(JSONKeys.QUESTION_DIFFICULTY_LVL);
		this.description = jsn.getString(JSONKeys.QUESTION_DESCRIPTION);
		this.correctAnswer = jsn.getString(JSONKeys.QUESTION_CORRECT_ANSWER_CHAR);
		
		if(jsn.has(JSONKeys.QUESTION_ANSWERS_NUMBER)) {
			this.numberOfAnswerOptions = jsn.getInt(JSONKeys.QUESTION_ANSWERS_NUMBER);
		}
		
		JSONArray arrayAnswers = null;
		if(this.numberOfAnswerOptions==0 && jsn.has(JSONKeys.QUESTION_ANSWER_OPTIONS)) {
			arrayAnswers = jsn.getJSONArray(JSONKeys.QUESTION_ANSWER_OPTIONS);			
			this.numberOfAnswerOptions = arrayAnswers.length();
		}
		
		if(jsn.has(JSONKeys.QUESTION_ANSWER_OPTIONS)) {
			if(arrayAnswers==null) arrayAnswers = jsn.getJSONArray(JSONKeys.QUESTION_ANSWER_OPTIONS);
			this.texts = new ArrayList<Texts>();
			int size = arrayAnswers.length();
			for(int i = 0; i < size; i++) {
				Texts text = new Texts();
				text.setText(arrayAnswers.getJSONObject(i).getString(JSONKeys.QUESTION_ONE_OPTION));
				this.texts.add(text);				
			}			
		}
		
		return this.texts;
	}
	
	
	@Override
	public JSONObject getShortJSON() throws JSONException {
		JSONObject jsn = this.category.getJSON();		
		jsn.put(JSONKeys.QUESTION_ID, this.id);
		jsn.put(JSONKeys.QUESTION_DIFFICULTY_LVL, this.levelOfDifficulty);
		if(this.fileLocationLink!=null)
			jsn.put(JSONKeys.QUESTION_HAS_IMAGE, true);
		jsn.put(JSONKeys.QUESTION_SHORT_DESCRIPTION, this.getShortDescription());
		jsn.put(JSONKeys.QUESTION_TYPE, IPublicStrings.COMPANY_AMERICAN_TEST);
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
		ITestQuestionHandler result = new AmericanTestQuestionHandler();
		result.setQuestion(this);
		return result;
	}
	

}

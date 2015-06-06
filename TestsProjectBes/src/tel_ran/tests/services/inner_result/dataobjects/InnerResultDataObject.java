package tel_ran.tests.services.inner_result.dataobjects;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class InnerResultDataObject {

	public static final String STATUS_NOT_ASKED = "notAsked";
	public static final String STATUS_NOT_ALALYZED = "notAnalyzed";
	public static final String STATUS_TRUE = "true";
	public static final String STATUS_FALSE = "false";
	public static final String KEY_QUESTION_ID = "questionID";
	public static final String KEY_STATUS = "status";
	public static final String KEY_METACATEGORY = "metacategory";
	
	@SerializedName(KEY_QUESTION_ID)
	long questionID;		// KEY_QUESTION_ID have to have the same name
	@SerializedName(KEY_STATUS)
	String status;			// KEY_STATUS have to have the same name
	@SerializedName(KEY_METACATEGORY)
	String metacategory; 	// KEY_METACATEGORY have to have the same name
	
	String rightAnswer;
	String personAnswer;

	public String toJson(){
		return new Gson().toJson(this);
	}
	
	public long getQuestionID() {
		return questionID;
	}

	public void setQuestionID(long questionID) {
		this.questionID = questionID;
	}
	
	public String getRightAnswer() {
		return rightAnswer;
	}

	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
	}

	public String getPersonAnswer() {
		return personAnswer;
	}

	public void setPersonAnswer(String personAnswer) {
		this.personAnswer = personAnswer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMetacategory() {
		return metacategory;
	}

	public void setMetacategory(String metacategory) {
		this.metacategory = metacategory;
	}
}

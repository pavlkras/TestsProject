package tel_ran.tests.services.subtype_handlers;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tel_ran.tests.entitys.Question;

import tel_ran.tests.entitys.QuestionCustomOpen;

import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.common.IPublicStrings;


public class OpenQuestionHandler extends AbstractTestQuestionHandler {

	QuestionCustomOpen question;
	
	@Override
	public void setQuestion(Question question) {
		this.question = (QuestionCustomOpen)question;
	}
	
		
	public OpenQuestionHandler() {
		super();
		type = ICommonData.QUESTION_TYPE_OPEN;
		gradeType = 0;
		categoryType = "custom";
	}

	@Override
	protected void addDataToJson(JSONObject jsn) throws JSONException {
		
		jsn.put(ICommonData.JSN_INTEST_QUESTION_TEXT, question.getTitle().getQuestionText());
		jsn.put(ICommonData.JSN_INTEST_DESCRIPTION, getManyLinesField(question.getDescription()));
		
		String fileLink = question.getFileLocationLink();
		if(fileLink!=null && fileLink.length() > 2) {
			jsn.put(ICommonData.JSN_INTEST_IMAGE, getImageBase64(fileLink));
		}	
		
	}
	
	@Override
	protected void addFullDataToJson(JSONObject jsn) throws JSONException {
		jsn.put(ICommonData.JSN_QUESTDET_METACATEGORY, question.getCategory().getMetaCategory());
		jsn.put(ICommonData.JSN_QUESTDET_CATEGORY1, question.getCategory().getCategory1());
		jsn.put(ICommonData.JSN_QUESTDET_TEXT, question.getCategory().getCategory2());
		
		jsn.put(ICommonData.JSN_QUESTDET_DESCRIPTION, getManyLinesField(question.getDescription()));
		
		JSONArray array = getManyLinesField(tQuestion.getAnswer());
		jsn.put(ICommonData.JSN_QUESTDET_ANSWER, array);
		
	}

	@Override
	public String getQuestionJson(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQuestionViewResultJson() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected int checkAnswers() {		
		return ICommonData.STATUS_UNCHECKED;
	}

	@Override
	protected String preparingAnswer(String answer) {
		return answer;
	}


	@Override
	protected int getStatusFromMark(String mark) {
		int res =-1;		
		if(mark.equals(IPublicStrings.GRADE_OPTIONS[0][1]))
			res = ICommonData.STATUS_CORRECT;
		if(mark.equals(IPublicStrings.GRADE_OPTIONS[0][0]))
			res = ICommonData.STATUS_INCORRECT;
		return res;
	}


	@Override
	public void printQuestion() {
		System.out.println(this.question.getId());
		
	}
	
}

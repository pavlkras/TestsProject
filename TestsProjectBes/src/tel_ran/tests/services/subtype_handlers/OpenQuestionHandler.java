package tel_ran.tests.services.subtype_handlers;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.entitys.EntityTestQuestions;
import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.common.IPublicStrings;
import tel_ran.tests.services.utils.FileManagerService;

public class OpenQuestionHandler extends AbstractTestQuestionHandler {

	
		
	public OpenQuestionHandler() {
		super();
		type = ICommonData.QUESTION_TYPE_OPEN;
		gradeType = 0;
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
	public JSONObject getJsonForTest(long eqtId, int index) throws JSONException {
		// from SUPER - text of question, id of EntityTestQuestion, index, type
		JSONObject result = super.getJsonForTest(eqtId, index);
				
		// description		
		result.put(ICommonData.JSN_INTEST_DESCRIPTION, getManyLinesField(getQuestionAttribubes().getDescription()));
		
		// get image
		String fileLink = getQuestionAttribubes().getFileLocationLink();
		if(fileLink!=null && fileLink.length() > 2) {
			result.put(ICommonData.JSN_INTEST_IMAGE, getImageBase64(fileLink));
		}
		
		return result;		
	}

	
	@Override
	protected int checkAnswers() {		
		return ICommonData.STATUS_UNCHECKED;
	}

	@Override
	protected String preparingAnswer(String answer) {
		return answer;
	}


	// DESCRIPTION, ANSWER + DATA FROM SUPER
	@Override
	public JSONObject getJsonWithCorrectAnswer(EntityTestQuestions entityTestQuestion) throws JSONException {
		JSONObject result = super.getJsonWithCorrectAnswer(entityTestQuestion);
		result.put(ICommonData.JSN_QUESTDET_DESCRIPTION, getManyLinesField(getQuestionAttribubes().getDescription()));
				
		JSONArray array = getManyLinesField(entityTestQuestion.getAnswer());
		result.put(ICommonData.JSN_QUESTDET_ANSWER, array);
				
		return result;
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

	
}

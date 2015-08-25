package tel_ran.tests.services.subtype_handlers;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.entitys.EntityTestQuestions;
import tel_ran.tests.services.common.ICommonData;

public class OpenQuestionHandler extends AbstractTestQuestionHandler {

	
		
	public OpenQuestionHandler() {
		super();
		type = ICommonData.QUESTION_TYPE_OPEN;
	}

	@Override
	public void analyze() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean setPersonAnswer(JSONObject answerJsonObj) {
		// TODO Auto-generated method stub
		return false;
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
		result.put(ICommonData.JSN_INTEST_DESCRIPTION, getQuestionAttribubes().getDescription());
		
		// get image
		String fileLink = getQuestionAttribubes().getFileLocationLink();
		if(fileLink!=null && fileLink.length() > 2) {
			result.put(ICommonData.JSN_INTEST_IMAGE, getImageBase64(fileLink));
		}
		
		return result;		
	}

	@Override
	protected int getStatus(String answer) {		
		return ICommonData.STATUS_UNCHECKED;
	}
	
	
		


}

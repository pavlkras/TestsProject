package tel_ran.tests.services;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import tel_ran.tests.dao.IDataTestsQuestions;
import tel_ran.tests.entitys.Company;
import tel_ran.tests.entitys.InTestQuestion;
import tel_ran.tests.entitys.Test;
import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.common.IPublicStrings;
import tel_ran.tests.services.fields.Role;
import tel_ran.tests.services.subtype_handlers.ITestQuestionHandler;
import tel_ran.tests.utils.errors.DataException;

public class TestResultService extends AbstractService {
	
	@Autowired
	IDataTestsQuestions testQuestsionsData;
	private Company company;

	public void setCompanyId(int companyId) throws DataException {		
		this.company = testQuestsionsData.getCompanyById(companyId, Role.COMPANY);
		if(this.company==null) throw new DataException(DataException.NO_COMPANY);
	}	
	
	@Override
	public String getAllElements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getElement(String params) {
		// TODO Auto-generated method stub
		return null;
	}

	// CHECK ANSWER OF PERSON
	@Override
	public String createNewElement(String dataJson) {
		
		String response = "";
		
		try {
			JSONObject jsn = new JSONObject(dataJson);
			String res = jsn.getString(ICommonData.JSN_CHECK_MARK);			
			long testQuestionId = jsn.getLong(ICommonData.JSN_CHECK_ID);
			InTestQuestion tQuestion = testQuestsionsData.findTestQuestionById(testQuestionId);
			
			
			if(tQuestion!=null && tQuestion.getStatus()==ICommonData.STATUS_UNCHECKED) {
				ITestQuestionHandler handler = tQuestion.getQuestion().getHandler();
				int newStatus = handler.setMark(res);
				testQuestsionsData.saveAnswer(tQuestion);
				
				response = IPublicStrings.QUESTION_STATUS[newStatus];
				
				long testId = tQuestion.getTest().getId();
				
				renewStatusOfTest(testId);
				}
								
			} catch (JSONException e) {
					e.printStackTrace();			
			}
						
			return response;
		
	}

	@Override
	public List<String> getSimpleList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInformation() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int[] renewStatusOfTest(long testId) {
		
		Test test = testQuestsionsData.findTestById(testId);
		
		int[] result = new int[5];
		List<InTestQuestion> tQuestions = test.getInTestQuestions();
				
		for(InTestQuestion tQuestion : tQuestions) {
			int status = tQuestion.getStatus();			
			result[4]++;
			result[status]++;			
		}
		
		boolean testIsPassed;				
		
		if(result[ICommonData.STATUS_NO_ANSWER]==0) {			
			testIsPassed = true;
			if(!test.isPassed()) {			
				test.setPassed(true);				
			}
		} else {
			testIsPassed = false;
		}
		
		if(result[ICommonData.STATUS_UNCHECKED]==0 && testIsPassed) {			
			if(!test.isChecked()) {
				test.setChecked(true);					
			}
		}

		test.setAmountOfCorrectAnswers(result[ICommonData.STATUS_CORRECT]);	
		
		testQuestsionsData.saveTest(test);
		
		return result;
	}
	

}

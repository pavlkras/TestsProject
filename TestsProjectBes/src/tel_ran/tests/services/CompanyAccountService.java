package tel_ran.tests.services;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import tel_ran.tests.dao.IDataTestsQuestions;
import tel_ran.tests.services.common.ICommonData;

public class CompanyAccountService extends AccountService {

	@Autowired
	IDataTestsQuestions testQuestsionsData;	
		
	@Override
	public String getInformation() {
		String result = "";			
		
		int numberQuestions = this.testQuestsionsData.getNumberQuestions((int)user.getId(), user.getRole());
		int numberTests = this.testQuestsionsData.getNumberTests((int)user.getId(), user.getRole());
			
			JSONObject jsn = new JSONObject();
			try {
				jsn.put(ICommonData.MAP_ACCOUNT_QUESTION_NUMBER, numberQuestions);
				jsn.put(ICommonData.MAP_ACCOUNT_TESTS_NUM, numberTests);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			result = jsn.toString();	
			
		return result;
	}

	@Override
	public String getElement(String params) {
		// TODO Auto-generated method stub
		return null;
	}
}

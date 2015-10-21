package handlers;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.fields.Role;
import tel_ran.tests.services.interfaces.ICommonAdminService;
import tel_ran.tests.strings.JSONKeys;
import tel_ran.tests.users.Visitor;

public class Company extends  Registred  {

			
	public Company() {
		this.role = Role.COMPANY;
		this.roleNumber = Role.COMPANY.ordinal();
	}
		
	@Override
	public String companyLogInPage() {
		return "company/Company_main";
	}
	
	@Override
	public String logInPage() {		
		return "/company_main";	
	}

	@Override
	public String getAccountInformation(Visitor visitor) {
		String result;
		String dinamicInfo = commonService.getUserInformation(token);
		JSONObject jsn = null;
		if(dinamicInfo!=null) {
			try {
				jsn = new JSONObject(dinamicInfo);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			jsn = new JSONObject();
		}
		
		try {
			jsn.put(JSONKeys.AUTO_COMPANY_NAME, visitor.getCompanyName());
			jsn.put(JSONKeys.AUTO_WEBSITE, visitor.getSite());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		result = jsn.toString();
			
//			JSONObject jsn = new JSONObject();
//			try {
//				jsn.put(ICommonData.MAP_ACCOUNT_NAME, entityCompany.getC_Name());
//				jsn.put(ICommonData.MAP_ACCOUNT_WEB, entityCompany.getC_Site());
//				jsn.put(ICommonData.MAP_ACCOUNT_QUESTION_NUMBER, getNumberQuestion());
//				jsn.put(ICommonData.MAP_ACCOUNT_TESTS_NUM, getNumberTests());
//			} 
		return result;

	}
	
	



}

package tel_ran.tests.utils;

import org.json.JSONException;
import org.json.JSONObject;

import tel_ran.tests.strings.JSONKeys;

public final class JsonFabric {
	
	public static String getAutorizationJson(String email, String password) throws JSONException {
		JSONObject json = new JSONObject();
		json.put(JSONKeys.AUTO_EMAIL, email);
		json.put(JSONKeys.AUTO_PASSWORD, password);
		return json.toString();				
	}
	
	public static String userSignUp(String firstname, String lastname, String email, String password, String nickname) throws JSONException {
		JSONObject json = new JSONObject();
		json.put(JSONKeys.AUTO_EMAIL, email);
		json.put(JSONKeys.AUTO_PASSWORD, password);
		json.put(JSONKeys.AUTO_FIRSTNAME, firstname);
		json.put(JSONKeys.AUTO_LASTNAME, lastname);
		json.put(JSONKeys.AUTO_NICKNAME, nickname);		
		return json.toString();
	}
	
	public static String getCompanyAutorizationJson(String companyName, String password) throws JSONException {
		JSONObject json = new JSONObject();
		json.put(JSONKeys.AUTO_COMPANY_NAME, companyName);
		json.put(JSONKeys.AUTO_PASSWORD, password);
		return json.toString();
	}

	public static String companySignUp(String c_Name, String c_Site,
			String c_Specialization, String c_AmountEmployes, String c_Password) throws JSONException {
		JSONObject json = new JSONObject();
		json.put(JSONKeys.AUTO_COMPANY_NAME, c_Name);
		json.put(JSONKeys.AUTO_WEBSITE, c_Site);
		json.put(JSONKeys.AUTO_SPECIALIZATION, c_Specialization);
		json.put(JSONKeys.AUTO_EMPL_NUMBER, c_AmountEmployes);
		json.put(JSONKeys.AUTO_PASSWORD, c_Password);
		return json.toString();
	}

}

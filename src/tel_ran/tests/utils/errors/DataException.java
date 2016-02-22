package tel_ran.tests.utils.errors;

import org.json.JSONException;

import json_models.ResultAndErrorModel;
import tel_ran.tests.services.common.IPublicStrings;

public class DataException extends Exception {

	public static final int NO_TEST = IPublicStrings.ERROR_NO_TEST;
	public static final int NO_COMPANY = IPublicStrings.ERROR_NO_COMPANY;
	public static final int NO_QUESTION = IPublicStrings.ERROR_NO_QUESTION;
	
	int error;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

	public DataException(int error) {
		this.error = error;
	}
	
	public String getString() {
		String result = "";
		try {
			result = ResultAndErrorModel.getJson(this.error);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
}

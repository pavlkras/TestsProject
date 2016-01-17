package tel_ran.tests.utils.errors;

import org.json.JSONException;

import json_models.ResultAndErrorModel;
import tel_ran.tests.services.common.IPublicStrings;

public class AccessException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getString() {
		String result = "";
		try {
			result = ResultAndErrorModel.getJson(IPublicStrings.ERROR_NO_ACCESS);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}

package json_models;

import org.json.JSONException;
import org.json.JSONObject;
import tel_ran.tests.services.common.IPublicStrings;

public class ResultAndErrorModel {
	
	
	public static String getJson(String str) throws JSONException {
		
		JSONObject obj = new JSONObject();
		obj.put(JSONKeys.ERROR, str);
		return obj.toString();
	}
	
	public static String getJson(String str, int errorCode) throws JSONException {
		
		JSONObject obj = new JSONObject();
		obj.put(JSONKeys.ERROR, str);
		obj.put(JSONKeys.ERROR_CODE, errorCode);
		return obj.toString();
		
	}
	
	public static String getJson(int errorcode) throws JSONException {
		JSONObject obj = new JSONObject();
		obj.put(JSONKeys.ERROR, IPublicStrings.ERRORS_TEXT[errorcode]);
		obj.put(JSONKeys.ERROR_CODE, errorcode);
		return obj.toString();
	}
	
	public static String getAnswer(String answer, int code) throws JSONException {
		JSONObject jsn = new JSONObject();
		jsn.put(JSONKeys.RESPONSE_TEXT, answer);
		jsn.put(JSONKeys.RESPONSE_CODE, code);
		return jsn.toString();
	}
	
	public static String getResponse(String key, Object value, int code) throws JSONException {
		JSONObject jsn = new JSONObject();
		jsn.put(key, value);
		jsn.put(JSONKeys.RESPONSE_CODE, code);
		return jsn.toString();
	}
	

}

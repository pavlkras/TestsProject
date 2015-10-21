package json_models;

import org.json.JSONException;
import org.json.JSONObject;

public class ErrorModel {
	
	
	public static String getJson(String str) throws JSONException {
		
		JSONObject obj = new JSONObject();
		obj.put(JSONKeys.ERROR, str);
		return obj.toString();
	}

}

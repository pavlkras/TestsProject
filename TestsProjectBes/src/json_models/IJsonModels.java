package json_models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public interface IJsonModels {
	
	public String getString() throws JSONException;
	public JSONObject getJSON() throws JSONException;
	JSONArray getJSONArray() throws JSONException;

}

package json_models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SimpleArray implements IJsonModels {
	
	List<IJsonModels> list = new ArrayList<IJsonModels>();
	private String key;
	
	public SimpleArray() {
		super();
		this.key = JSONKeys.ARRAY_OF_RESULT;
	}
	
	public SimpleArray(String key) {
		super();
		this.key = key;
	}
	
	public void addObject(IJsonModels model) {
		list.add(model);
	}
	
	public void addAll(List<IJsonModels> list) {
		for(IJsonModels model : list) {
			this.list.add(model);
		}
	}
	
	
	public JSONObject getJSON() throws JSONException {
		JSONObject result = new JSONObject();		
		result.put(this.key, getJSONArray());		
		return result;		
	}
	
	public JSONArray getJSONArray() throws JSONException {
		JSONArray array = new JSONArray();
		
		for(IJsonModels model : list) {
			array.put(model.getJSON());
		}
		return array;
	}

	@Override
	public String getString() throws JSONException {		
		return getJSON().toString();
	}	
	
	

}

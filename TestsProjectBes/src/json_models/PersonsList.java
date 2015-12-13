package json_models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tel_ran.tests.entitys.EntityPerson;

public class PersonsList implements IJsonModels, Iterable {

	public List<PersonModel> persons = new ArrayList();
	String path;
	int companyId;
	
	public PersonsList(String dataJson, int companyId) throws JSONException {
		this.companyId = companyId;
		readDataFromJson(dataJson);
	}
		
	private void readDataFromJson(String dataJson) throws JSONException  {
		JSONObject object = new JSONObject(dataJson);
		JSONArray array = (JSONArray) object.get(JSONKeys.TEST_FOR_PERSONS);
		
		int size = array.length();
		for(int i = 0; i < size; i++) {
			JSONObject innerJson = array.getJSONObject(i);
			PersonModel person;
			try {
				person = new PersonModel(innerJson);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
			this.persons.add(person);
		}
		
		this.path = object.getString(JSONKeys.TEST_LINK_TO_SEND);
	}

	@Override
	public String getString() throws JSONException {
		
		return getJSONArray().toString();
	}

	@Override
	public JSONObject getJSON() throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray getJSONArray() throws JSONException {
		JSONArray array = new JSONArray();
		for(PersonModel person : persons) {
			JSONObject jsn = new JSONObject();
			jsn.put(JSONKeys.PERSON_MAIL, person.getMail());
			jsn.put(JSONKeys.TEST_LINK_TO_SEND, person.getLink());
			jsn.put(JSONKeys.TEST_WAS_SENT, person.mailSent);
			array.put(jsn);
		}
		return array;
	}

	@Override
	public Iterator<PersonModel> iterator() {		
		return new PersonIterator(this.persons);
	}
	
	public String getPath() {
		return path;
	}
	
	
	
	public static class PersonIterator implements Iterator<PersonModel> {
		
		List<PersonModel> persons;
		int counter = 0;
		int size;
		
		public PersonIterator(List<PersonModel> list) {
			persons = list;
			size = list.size();
		}
			

		@Override
		public boolean hasNext() {			
			return counter < persons.size();
		}

		@Override
		public PersonModel next() {			
			return persons.get(counter++);
		}
		
	}


	
	

}

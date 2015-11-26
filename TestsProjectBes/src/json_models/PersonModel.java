package json_models;

import java.util.UUID;

import netscape.javascript.JSException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tel_ran.tests.entitys.EntityPerson;

public class PersonModel implements IJsonModels {
	
	EntityPerson person;
	
	public PersonModel(EntityPerson p) {
		this.person = p;
	}
	
	public PersonModel(String json) throws JSONException {
		this.person = new EntityPerson();
		readJson(json);		
	}
			
	public EntityPerson getEntity() {
		return person;
	}

	private void readJson(String jsn) throws JSONException {
		
		JSONObject jsnObject = new JSONObject(jsn);
		
		String email = jsnObject.getString(JSONKeys.PERSON_MAIL);
		if(email==null) throw new JSONException("no email");
		person.setPersonEmail(email);
				
		String passport = jsnObject.getString(JSONKeys.PERSON_PASSPORT);
		if(passport == null) throw new JSONException("no passport");
		person.setIdentify(passport);
		
		String fname = jsnObject.getString(JSONKeys.PERSON_FNAME);
		if(fname!=null) person.setPersonName(fname);
		
		String lname = jsnObject.getString(JSONKeys.PERSON_LNAME);
		if(lname!=null) person.setPersonSurname(lname);		
				
	}
	

	@Override
	public String getString() throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getJSON() throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray getJSONArray() throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}

}

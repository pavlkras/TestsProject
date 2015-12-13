package json_models;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tel_ran.tests.entitys.EntityPerson;

public class PersonModel implements IJsonModels {
	
	EntityPerson person;
	long testId;
	TestModel test;
	boolean mailSent;
	
	
	public PersonModel(EntityPerson p) {
		this.person = p;
	}
	
	public PersonModel(JSONObject json) throws JSONException {
		this.person = new EntityPerson();
		readJson(json);	
	}
	
	public PersonModel(String json) throws JSONException {
		this.person = new EntityPerson();
		readJson(json);		
	}
			
	public EntityPerson getEntity() {
		return person;
	}

	private void readJson(JSONObject jsnObject) throws JSONException {
		
		if(jsnObject.has(JSONKeys.PERSON_MAIL)){
			String email = jsnObject.getString(JSONKeys.PERSON_MAIL);
			person.setPersonEmail(email);
		} else {
			throw new JSONException("no email");
		}
		
		if(jsnObject.has(JSONKeys.PERSON_PASSPORT)){
			String passport = jsnObject.getString(JSONKeys.PERSON_PASSPORT);
			person.setIdentify(passport);			
		}
		
		if(jsnObject.has(JSONKeys.PERSON_FNAME)) {
			String fname = jsnObject.getString(JSONKeys.PERSON_FNAME);
			person.setPersonName(fname);
		}
				
		if(jsnObject.has(JSONKeys.PERSON_LNAME)) {
			String lname = jsnObject.getString(JSONKeys.PERSON_LNAME);
			person.setPersonSurname(lname);		
		}
		
	}
	
	private void readJson(String jsn) throws JSONException {
		
		JSONObject jsnObject = new JSONObject(jsn);		
		readJson(jsnObject);	
				
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

	public void setTestId(long testId) {
		this.testId = testId;
		
	}

	public void setTest(TestModel testModel) {
		this.test = testModel;
		
	}

	public void sendEmail(String path) {
		if(this.test.sendEmailByPath(path))
			this.mailSent = true;
		System.out.println(Boolean.toString(mailSent));
	}
	
	public String getMail(){
		return this.person.getPersonEmail();
	}
	
	public String getLink(){
		return this.test.link;
	}

}

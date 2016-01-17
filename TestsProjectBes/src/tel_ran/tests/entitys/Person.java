package tel_ran.tests.entitys;
import java.util.List;





import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import json_models.IJsonModels;
import json_models.JSONKeys;

@Entity
public class Person implements IJsonModels {
    
	@Id
	@GeneratedValue
	@Column(name="personId",unique = true, nullable = false, length = 25)	
    private long personId;
	private String personName;
    private String personSurname;
    private String personEmail;
    
    @ManyToOne
    private Company company;
    
    @Column(name="identify")
    private String identify;
   
    @OneToMany(mappedBy = "person")
    private List<Test> test;
    
    
    public Person() {    	
    }
    
 
	public Person(JSONObject jsn) throws JSONException {
		readJson(jsn);	
	}
		
	
	private void readJson(JSONObject jsn) throws JSONException {
		if(jsn.has(JSONKeys.PERSON_MAIL)){
			this.personEmail = jsn.getString(JSONKeys.PERSON_MAIL);			
		} else {
			throw new JSONException("no email");
		}
		
		if(jsn.has(JSONKeys.PERSON_PASSPORT)){
			this.identify = jsn.getString(JSONKeys.PERSON_PASSPORT);						
		}
		
		if(jsn.has(JSONKeys.PERSON_FNAME)) {
			this.personName = jsn.getString(JSONKeys.PERSON_FNAME);			
		}
				
		if(jsn.has(JSONKeys.PERSON_LNAME)) {
			this.personSurname = jsn.getString(JSONKeys.PERSON_LNAME);					
		}
		
	}


	public List<Test> getEnTest() {
		return test;
	}
	public void setEnTest(List<Test> enTest) {
		this.test = enTest;
	}
	public String getIdentify() {
		return identify;
	}
	public void setIdentify(String identify) {
		this.identify = identify;
	}	  
    
	public String getPersonEmail() {
		return personEmail;
	}
	public void setPersonEmail(String personEmail) {
		this.personEmail = personEmail;
	}
	public long getPersonId() {
		return personId;
	}
	public void setPersonId(long personId) {
		this.personId = personId;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getPersonSurname() {
		return personSurname;
	}
	public void setPersonSurname(String personSurname) {
		this.personSurname = personSurname;
	}
	@Override
	public String toString() {
		return "EntityPerson [personId=" + personId + ", personName=" + personName + ", personSurname="
				+ personSurname + ", personEmail=" + personEmail + "]";
	}


	@Override
	public String getString() throws JSONException {
		
		return this.getJSON().toString();
	}


	@Override
	public JSONObject getJSON() throws JSONException {
		JSONObject jsn = new JSONObject();
		jsn.put(JSONKeys.PERSON_id, this.personId);
		jsn.put(JSONKeys.PERSON_NAME, this.personName);
		jsn.put(JSONKeys.PERSON_SURNAME, this.personSurname);
		
		
		return jsn;
	}


	@Override
	public JSONArray getJSONArray() throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}

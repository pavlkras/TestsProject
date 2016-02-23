package json_models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationModel extends AutorizationModel implements IJsonModels {
		
	private boolean userExist = false;
	private boolean success = false;
	
	
	public void setUserExist(boolean userExist) {
		this.userExist = userExist;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public Date getBirthDate() {
		SimpleDateFormat format = new SimpleDateFormat(JSONKeys.DATE_TEMPLATE);
		try {
			return format.parse(this.birthdate);
		} catch (ParseException e) {			
			return null;
		}
	}

	public String getAddress() {
		return this.address;
	}
	
	public String getLastname() {
		return lastName;
	}

	public String getNickname() {
		return nickName;
	}

	public String getEmail() {
		return this.email;
	}
	
	public String getWebSite() {
		return this.webSite;
	}
	
	public String getSpec() {
		return this.spec;
	}
	
	public String getEmployesNumber() {
		return this.employesNumber;
	}

	public RegistrationModel(String jsonString) throws JSONException {
		super(jsonString);
		JSONObject json = new JSONObject(jsonString);	
		
		if(json.has(JSONKeys.AUTO_ADDRESS))
				this.address = json.getString(JSONKeys.AUTO_ADDRESS);		
		if(json.has(JSONKeys.AUTO_FIRSTNAME))
				this.firstName = json.getString(JSONKeys.AUTO_FIRSTNAME);		
		if(json.has(JSONKeys.AUTO_LASTNAME))
				this.lastName = json.getString(JSONKeys.AUTO_LASTNAME);		
		if(json.has(JSONKeys.AUTO_NICKNAME))
			this.nickName = json.getString(JSONKeys.AUTO_NICKNAME);	
		if(json.has(JSONKeys.AUTO_BIRTHDATE))
			this.birthdate = json.getString(JSONKeys.AUTO_BIRTHDATE);
		if(json.has(JSONKeys.AUTO_EMAIL))
			this.email = json.getString(JSONKeys.AUTO_EMAIL);
		if(json.has(JSONKeys.AUTO_WEBSITE))
			this.webSite = json.getString(JSONKeys.AUTO_WEBSITE);
		if(json.has(JSONKeys.AUTO_SPECIALIZATION))
			this.spec = json.getString(JSONKeys.AUTO_SPECIALIZATION);
		if(json.has(JSONKeys.AUTO_EMPL_NUMBER))
			this.employesNumber = json.getString(JSONKeys.AUTO_EMPL_NUMBER);	
		
	}
	
	public JSONObject getJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(JSONKeys.SIGNUP_USER_EXIST, this.userExist);
		json.put(JSONKeys.SIGNUP_SUCCESS, this.success);
		return json;
	}

	@Override
	public String getString() throws JSONException {		
		return getJSON().toString();
	}

	
}

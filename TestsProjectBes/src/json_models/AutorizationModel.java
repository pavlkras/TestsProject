package json_models;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;


public class AutorizationModel implements IJsonModels {
	
	protected String login;
	
	protected String password; //JSONKeys.AUTO_PASSWORD
	private int roleNumber = 0;	//JSONKeys.AUTO_ROLENUMBER
	private boolean admin = false; //JSONKeys.AUTO_ADMIN
	private String token = ""; //JSONKeys.AUTO_TOKEN
	
	protected String address;//JSONKeys.AUTO_ADDRESS
	protected String birthdate;//JSONKeys.AUTO_BIRTHDATE
	protected String firstName;//JSONKeys.AUTO_FIRSTNAME
	protected String lastName;//JSONKeys.AUTO_LASTNAME
	protected String nickName;//JSONKeys.AUTO_NICKNAME
	protected String passport;//SONKeys.AUTO_PASSPORTID

	protected String email; //JSONKeys.AUTO_EMAIL
	protected String webSite;
	protected String spec;
	protected String employesNumber;
			
	public AutorizationModel(String jsonString) throws JSONException {
		JSONObject json = new JSONObject(jsonString);
		if(json.has(JSONKeys.AUTO_COMPANY_NAME)) 
			this.login = json.getString(JSONKeys.AUTO_COMPANY_NAME);
		else if(json.has(JSONKeys.AUTO_EMAIL)) 
			this.login = json.getString(JSONKeys.AUTO_EMAIL);		
		this.password = json.getString(JSONKeys.AUTO_PASSWORD);	
	}
	
	public void setEmployesNumber(String employesNumber) {
		this.employesNumber = employesNumber;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getLogin() {
		return login;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setBirthDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(JSONKeys.DATE_TEMPLATE);
		this.birthdate = format.format(date);
	}
	
	public void setAddress(String address) {
		this.address = address;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public int getRoleNumber() {
		return roleNumber;
	}

	public void setRoleNumber(int roleNumber) {
		this.roleNumber = roleNumber;	
		
	}

	
	
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	@Override
	public String getString() throws JSONException {
		JSONObject object = new JSONObject();
		object.put(JSONKeys.AUTO_TOKEN, this.token);
		object.put(JSONKeys.AUTO_ROLENUMBER, this.roleNumber);
		object.put(JSONKeys.AUTO_ADMIN, this.admin);
		if(this.address!=null)
			object.put(JSONKeys.AUTO_ADDRESS, this.address);
		if(this.birthdate!=null)
			object.put(JSONKeys.AUTO_BIRTHDATE, this.birthdate);
		if(this.firstName!=null)
			object.put(JSONKeys.AUTO_FIRSTNAME, this.firstName);
		if(this.lastName!=null)
			object.put(JSONKeys.AUTO_LASTNAME, this.lastName);
		if(this.nickName!=null)
			object.put(JSONKeys.AUTO_NICKNAME,  this.nickName);
		if(this.passport!=null)
			object.put(JSONKeys.AUTO_PASSPORTID, this.passport);
		if(this.email!=null)
			object.put(JSONKeys.AUTO_EMAIL, this.email);
		if(this.webSite!=null)
			object.put(JSONKeys.AUTO_WEBSITE, this.webSite);
		if(this.spec!=null)
			object.put(JSONKeys.AUTO_SPECIALIZATION, this.spec);
		if(this.employesNumber!=null)
			object.put(JSONKeys.AUTO_EMPL_NUMBER, this.employesNumber);
		return object.toString();
	}
	
}

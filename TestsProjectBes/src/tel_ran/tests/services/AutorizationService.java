package tel_ran.tests.services;

import json_models.AutorizationModel;
import json_models.ResultAndErrorModel;
import json_models.RegistrationModel;

import org.json.JSONException;


import org.springframework.beans.factory.annotation.Autowired;

import tel_ran.tests.data_loader.IDataLoader;
import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.fields.Role;
import tel_ran.tests.services.utils.SpringApplicationContext;
import tel_ran.tests.token_cipher.TokenProcessor;

public class AutorizationService {

	private IDataLoader autoData;	
	private static AutorizationService service;		
	private TokenProcessor tokenProcessor;
	private static final int TOKEN_VALID_IN_SECOND = 86400; // 24h
	private static String monitor = "Monitor";
	
	private AutorizationService() {	
		autoData = (IDataLoader) SpringApplicationContext.getBean("autoData");
		tokenProcessor = (TokenProcessor) SpringApplicationContext.getBean("tokenProc");
	}
	
	public static AutorizationService getInstance() {
		if(service==null) {
			synchronized (monitor) {
				if(service==null)
					service = new AutorizationService();
			}
			
		}
		return service;
	}
	
	public String testLogIn(String loginJson) {		
		
		//1 - get Data from JSON
		AutorizationModel model = null;
		try {
			model = new AutorizationModel(loginJson);
		} catch (JSONException e) {			
			e.printStackTrace();
			try {
				return ResultAndErrorModel.getJson("Incorrect JSON");
			} catch (JSONException e1) {				
				e1.printStackTrace();			
			}
		}
		
		//2 - check if it's user data
		long id = autoData.checkUserLogIn(model.getLogin(), model.getPassword());
		
		
		//3 - get token and info 
		if(id>=0) {
			boolean admin = autoData.isAdmin(id);
			if(admin) {				
				model.setAdmin(true);
				model.setRoleNumber(Role.ADMINISTRATOR.ordinal());
			} else {
				model.setRoleNumber(Role.USER.ordinal());
			}
			model.setToken(this.encodeToken(id, model.getRoleNumber()));	
			autoData.fillInfoAboutUser(model, id);
		}
		
		
		//4 - preparing result
		String result = null;		
		try {
			result =  model.getString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	
	public String testSignIn(String request) {
		
		
		//1 - get Data from JSON
		RegistrationModel model = null;
		try {
			model = new RegistrationModel(request);
		} catch (JSONException e) {			
			e.printStackTrace();
			try {
				return ResultAndErrorModel.getJson("Incorrect JSON");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		//2 - check if this email is in DB
		boolean emailIsInDb = autoData.checkUserEmail(model.getLogin());
		model.setUserExist(emailIsInDb);
		
		//3 - registration
		if(!emailIsInDb) {				
			
			//3b - registration
			boolean registrated = autoData.userRegistration(model.getLogin(), model.getPassword(),
					model.getFirstName(), model.getLastname(), model.getNickname());
			
			model.setSuccess(registrated);
		}		
		//4 preparing answer
		
		String result = null;
		try {
			result = model.getString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	private String encodeToken(long id, int role) {
		String token = tokenProcessor.encodeRoleToken(id, role, TOKEN_VALID_IN_SECOND);
		return token;
	}
	
	public String companyLogIn(String request) {
		
		//1 - get Data from JSON
		AutorizationModel model = null;
		try {
			model = new AutorizationModel(request);
		} catch (JSONException e) {			
			e.printStackTrace();
			try {
				return ResultAndErrorModel.getJson("Incorrect JSON");
			} catch (JSONException e1) {				
				e1.printStackTrace();			
			}
		}
			
		//2 - check if it's existing data	
		long id = autoData.checkCompanyLogIn(model.getLogin(), model.getPassword());
		
		//3 - get token and info 
		if(id>=0) {
			model.setRoleNumber(Role.COMPANY.ordinal());			
			model.setToken(this.encodeToken(id, model.getRoleNumber()));	
			autoData.fillInfoAboutCompany(model, (int)id);
		}		
		
		//4 - preparing result
		String result = null;		
		try {
			result =  model.getString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String companySignUp(String request) {
		
		//1 - get Data from JSON
		RegistrationModel model = null;
		try {
			model = new RegistrationModel(request);
		} catch (JSONException e) {			
			e.printStackTrace();
			try {
				return ResultAndErrorModel.getJson("Incorrect JSON");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		//2 - check if this login is in DB
		boolean companyExists = autoData.checkCompanyName(model.getLogin());
		model.setUserExist(companyExists);
		
		//3 - registration
		if(!companyExists) {
			boolean registrated = autoData.companyRegistration(model.getLogin(), model.getPassword(),
					model.getEmployesNumber(), model.getWebSite(), model.getSpec());
			
			model.setSuccess(registrated);
		}		
		//4 preparing answer
		
		String result = null;
		try {
			result = model.getString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;

	}

	public boolean findCompany(String name_company) {
		boolean result = autoData.checkCompanyName(name_company);
		
		return result;
	}
}

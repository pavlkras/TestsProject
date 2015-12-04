package tel_ran.tests.initiate;

import java.util.Properties;

import tel_ran.tests.dao.IData;
import tel_ran.tests.dao.IDataLoader;
import tel_ran.tests.dao.TestsPersistence;

public class UserCreation extends DataCreation {

	String email;
	String password;
	public static final String NAME = "USER";
	
	
	public UserCreation(IDataLoader bean) {
		super();
		this.persistence = bean;		
	}
	

	@Override
	void fill() {
		((IDataLoader)persistence).userRegistration(email, password, true);		
	}



	@Override
	void setProperties(Properties properties) {
		this.email = properties.getProperty("admin.email");
		this.password = properties.getProperty("admin.password");	
	}


	@Override
	public boolean isNeedToFill() {
		return !((IDataLoader)persistence).checkRootUser();		
	}


	@Override
	public String getName() {
		
		return NAME;
	}
	
	
	
}

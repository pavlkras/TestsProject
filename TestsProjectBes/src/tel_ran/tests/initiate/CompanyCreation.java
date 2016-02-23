package tel_ran.tests.initiate;

import java.util.Properties;

import tel_ran.tests.dao.IDataLoader;


public class CompanyCreation extends DataCreation {

	String name;
	public static final String NAME = "COMPANY";
	
	public CompanyCreation(IDataLoader  bean) {
		this.persistence = bean;
		
		
	}

	@Override
	void fill() {
		((IDataLoader)persistence).companyRegistration(name, null, null, null, null);		
	}

	@Override
	void setProperties(Properties properties) {
		this.name = properties.getProperty("admin.company");				
	}

	@Override
	public boolean isNeedToFill() {	
		return !((IDataLoader) persistence).checkFirstCompany();
	}

	@Override
	public String getName() {
	
		return NAME;
	}
}

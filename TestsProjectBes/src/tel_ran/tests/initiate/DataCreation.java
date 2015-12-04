package tel_ran.tests.initiate;

import java.util.Properties;

import tel_ran.tests.dao.IData;
import tel_ran.tests.dao.TestsPersistence;

public abstract class DataCreation {

	IData persistence;
	
	public void setData(IData bean) {
		this.persistence = bean;		
	}
	
	abstract void fill();
	abstract void setProperties(Properties properties);

	abstract public boolean isNeedToFill();
	
	abstract public String getName();

}

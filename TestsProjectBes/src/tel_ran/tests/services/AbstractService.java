package tel_ran.tests.services;

import java.util.List;

import tel_ran.tests.token_cipher.User;

public abstract class AbstractService {
	
	User user;

	public void setUser(User user) {
		this.user = user;
	}

	public abstract String getAllElements();
	public abstract String getElement(String params);
	public abstract String createNewElement(String dataJson);
	public abstract List<String> getSimpleList();
	public abstract String getInformation();

}

package tel_ran.tests.dao;

import json_models.AutorizationModel;


public interface IDataLoader extends IData {
	
	long checkUserLogIn(String email, String password);
	long checkCompanyLogIn(String name, String password);
	boolean isAdmin(long id);
	boolean checkUserEmail(String email);
	boolean checkRootUser();
	boolean checkFirstCompany();
	boolean userRegistration(String email, String password);
	boolean userRegistration(String email, String password, boolean isAdmin);
	boolean userRegistration(String email, String password, String firstName, String lastName, String nickName);
	void fillInfoAboutUser(AutorizationModel model, long id);
	void fillInfoAboutCompany(AutorizationModel model, int id);
	boolean checkCompanyName(String login);
	boolean companyRegistration(String login, String password,
			String employesNumber, String webSite, String spec);
	long findTestIdByPassword(String key);
	
}

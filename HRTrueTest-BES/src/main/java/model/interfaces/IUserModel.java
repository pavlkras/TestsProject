package main.java.model.interfaces;

import main.java.model.dao.CategoryData;
import main.java.model.dao.CompanyData;

public interface IUserModel {
	boolean registerCompany(CompanyData user);
	CompanyData getCompanyData(String email);
	Iterable<CategoryData> getChildCategories(Integer parent_id);
}

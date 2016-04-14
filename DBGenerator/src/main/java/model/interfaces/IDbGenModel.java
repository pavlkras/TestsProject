package main.java.model.interfaces;

import main.java.model.dao.CompanyData;

public interface IDbGenModel {
	boolean addCompany(CompanyData data);

	void addRootCategory();

	void addChildCategory();

	void addTemplate(String templateName, CompanyData data);

	void addTemplateItem(byte amount, byte difficulty, String templateName, CompanyData data);
}

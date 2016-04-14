package main.java.model;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import main.java.entities.CompanyEntity;
import main.java.entities.CredentialsEntity;
import main.java.model.dao.CompanyData;
import main.java.model.interfaces.IUserModel;
import main.java.utils.Crypto;

public class UserPersistence implements IUserModel {

	@PersistenceContext(unitName="HRTrueTestBES")
	EntityManager em;
	
	@Override
	@Transactional
	public boolean registerCompany(CompanyData data) {
		data.setPassword(Crypto.generateHash(data.getPassword()));
		CredentialsEntity credentials = new CredentialsEntity(data.getEmail(), data.getPassword(), data.getRole());
		CompanyEntity company = new CompanyEntity(credentials, data.getName(), data.getSite(), data.getSpecialization(), data.getEmployees_amnt());
		em.persist(company);
		return false;
	}
}

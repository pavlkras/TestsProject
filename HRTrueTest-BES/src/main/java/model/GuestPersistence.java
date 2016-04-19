package main.java.model;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import main.java.entities.CompanyEntity;
import main.java.entities.CredentialsEntity;
import main.java.model.dao.CompanyData;
import main.java.utils.Crypto;

public class GuestPersistence{

	@PersistenceContext(unitName="HRTrueTestBES")
	EntityManager em;
	
	@Transactional
	public boolean registerCompany(CompanyData data) {
		data.setPassword(Crypto.generateHash(data.getPassword()));
		CredentialsEntity credentials = new CredentialsEntity(data.getEmail(), data.getPassword(), data.getRole());
		CompanyEntity company = new CompanyEntity(credentials, data.getName(), data.getSite(),
				data.getAcitivityType(),data.getEmployeesAmnt());
		em.persist(company);
		return true;
	}

	public CompanyData getCompanyData(String email) {
		Query query = em.createQuery("SELECT c FROM CompanyEntity c WHERE c.credentials.login = :email")
				.setParameter("email", email);
		CompanyEntity company = (CompanyEntity) query.getSingleResult();
		CompanyData companyData = CompanyEntity.convertToCompanyData(company);
		companyData.setId(company.getId());
		return companyData;
	}
}

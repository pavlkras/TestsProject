package main.java.model;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import main.java.entities.CatDiffEntity;
import main.java.entities.CompanyEntity;
import main.java.entities.CredentialsEntity;
import main.java.entities.TemplateEntity;
import main.java.entities.TemplateItemEntity;
import main.java.model.config.ActivityTypeMap;
import main.java.model.config.CategorySet;
import main.java.model.config.EmployeesAmountMap;
import main.java.model.dao.CompanyData;
import main.java.model.dao.TemplateItemData;
import main.java.utils.Crypto;

public class DbGenPersistence {

	@PersistenceContext(unitName = "testsHibernate", type = PersistenceContextType.EXTENDED)
	EntityManager em;

	@Autowired
	CategorySet categories;
	@Autowired
	EmployeesAmountMap employeesAmounts;
	@Autowired
	ActivityTypeMap activityTypes;

	public CategorySet getCategories() {
		return categories;
	}

	public EmployeesAmountMap getEmployeesAmounts() {
		return employeesAmounts;
	}

	public ActivityTypeMap getActivityTypes() {
		return activityTypes;
	}

	@Transactional
	public boolean addCompany(CompanyData data) {
		data.setPassword(Crypto.generateHash(data.getPassword()));
		CredentialsEntity credentials = new CredentialsEntity(data.getEmail(), data.getPassword(), data.getAuthorities());
		CompanyEntity company = new CompanyEntity(credentials, data.getName(), data.getSite(), data.getAcitivityType(),
				data.getEmployeesAmnt());
		em.persist(company);
		return false;
	}

	@Transactional
	public void addTemplate(String templateName, CompanyData data) {
		Query query = em.createQuery("SELECT c FROM CompanyEntity c WHERE c.credentials.login = ?1").setParameter(1,
				data.getEmail());
		CompanyEntity companyEntity = (CompanyEntity) query.getSingleResult();
		TemplateEntity templateEntity = new TemplateEntity(templateName, companyEntity);
		em.persist(templateEntity);
	}

	@Transactional
	public void addTemplateItem(TemplateItemData item, String templateName, CompanyData data) {
		Query query = em
				.createQuery("SELECT t FROM TemplateEntity t WHERE t.name = ?1 AND t.company.credentials.login = ?2")
				.setParameter(1, templateName).setParameter(2, data.getEmail());
		TemplateEntity templateEntity = (TemplateEntity) query.getSingleResult();

		query = em.createQuery("SELECT cd FROM CatDiffEntity cd WHERE cd.difficulty = ?1 AND cd.category = ?2")
				.setParameter(1, item.getDifficulty()).setParameter(2, item.getCategory());
		CatDiffEntity catDiffEntity = null;
		try {
			catDiffEntity = (CatDiffEntity) query.getSingleResult();
		} catch (NoResultException e) {
			catDiffEntity = new CatDiffEntity(item.getDifficulty(), item.getCategory());
			em.persist(catDiffEntity);
		}

		TemplateItemEntity templateItemEntity = new TemplateItemEntity(item.getAmount(), catDiffEntity, templateEntity);
		em.persist(templateItemEntity);
	}
}

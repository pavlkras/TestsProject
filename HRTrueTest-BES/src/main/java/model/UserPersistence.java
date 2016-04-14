package main.java.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import main.java.entities.CategoryEntity;
import main.java.entities.CompanyEntity;
import main.java.entities.CredentialsEntity;
import main.java.model.dao.CategoryData;
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
		CompanyEntity company = new CompanyEntity(credentials, data.getName(), data.getSite(),
				data.getSpecialization(),data.getEmployees_amnt());
		em.persist(company);
		return true;
	}

	@Override
	public CompanyData getCompanyData(String email) {
		Query query = em.createQuery("SELECT c FROM CompanyEntity c WHERE c.credentials.login = :email")
				.setParameter("email", email);
		CompanyEntity company = (CompanyEntity) query.getSingleResult();
		CompanyData companyData = new CompanyData(company.getCredentials().getLogin(), company.getCredentials().getPassword(),
				company.getCredentials().getRole(), company.getName(), company.getSite(),
				company.getSpecialization(), company.getEmployees_amnt());
		companyData.setId(company.getId());
		return companyData;
	}

	@Override
	public Iterable<CategoryData> getChildCategories(Integer parent_id) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<CategoryEntity> cq = cb.createQuery(CategoryEntity.class);
		Root<CategoryEntity> category = cq.from(CategoryEntity.class);
		cq.select(category);
		if (parent_id == null){
			cq.where(cb.isNull(category.get("parent")));
		}
		else {
			Join<CategoryEntity, CategoryEntity> parentEntity = category.join("parent");
			cq.where(cb.equal(parentEntity.get("id"), parent_id));
		}
		TypedQuery<CategoryEntity> query = em.createQuery(cq);
		List<CategoryEntity> results = query.getResultList();
		return CategoryEntity.convertToCategoryDataList(results);
	}
}

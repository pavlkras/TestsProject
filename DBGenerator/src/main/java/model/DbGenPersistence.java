package main.java.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import main.java.entities.CategoryEntity;
import main.java.entities.CompanyEntity;
import main.java.entities.CredentialsEntity;
import main.java.entities.TemplateEntity;
import main.java.entities.TemplateItemEntity;
import main.java.model.dao.CompanyData;
import main.java.model.interfaces.IDbGenModel;
import main.java.utils.Crypto;

public class DbGenPersistence implements IDbGenModel {

	private static Random random = new Random();
	
	@PersistenceContext(unitName="testsHibernate", type=PersistenceContextType.EXTENDED)
	EntityManager em;

	@Override
	@Transactional
	public boolean addCompany(CompanyData data) {
		data.setPassword(Crypto.generateHash(data.getPassword()));
		CredentialsEntity credentials = new CredentialsEntity(data.getEmail(), data.getPassword(), data.getRole());
		CompanyEntity company = new CompanyEntity(credentials, data.getName(), data.getSite(),
				data.getSpecialization(), data.getEmployees_amnt());
		em.persist(company);
		return false;
	}

	@Override
	@Transactional
	public void addRootCategory() {
		Query query = em.createQuery("SELECT c FROM CategoryEntity c");
		int count = query.getResultList().size();
		String name = "Category_" + count;
		CategoryEntity category = new CategoryEntity(name);
		em.persist(category);
	}

	@Override
	@Transactional
	public void addChildCategory() {
		Query query = em.createQuery("SELECT c FROM CategoryEntity c");
		List<?> entities = query.getResultList();
		List<CategoryEntity> categories = convertToCategoryList(entities);
		
		int cat_ind = random.nextInt(categories.size());
		CategoryEntity parent = categories.get(cat_ind);
		
		query = em.createQuery("SELECT c FROM CategoryEntity c WHERE c.parent=?1")
				.setParameter(1, parent);
		int child_count = query.getResultList().size();
		
		String name = parent.getName() + "_" + child_count;
		CategoryEntity childCategoryEntity = new CategoryEntity(name, parent);
		em.persist(childCategoryEntity);
	}

	private List<CategoryEntity> convertToCategoryList(List<?> entities) {
		List<CategoryEntity> categories = new ArrayList<>();
		for (Object entity : entities){
			CategoryEntity category = (CategoryEntity) entity;
			categories.add(category);
		}
		return categories;
	}

	@Override
	@Transactional
	public void addTemplate(String templateName, CompanyData data) {
		Query query = em.createQuery("SELECT c FROM CompanyEntity c WHERE c.credentials.login = ?1")
				.setParameter(1, data.getEmail());
		CompanyEntity companyEntity = (CompanyEntity) query.getSingleResult();
		TemplateEntity templateEntity = new TemplateEntity(templateName, companyEntity);
		em.persist(templateEntity);
	}

	@Override
	@Transactional
	public void addTemplateItem(byte amount, byte difficulty, String templateName, CompanyData data) {
		Query query = em.createQuery("SELECT t FROM TemplateEntity t WHERE t.name = ?1 AND t.company.credentials.login = ?2")
				.setParameter(1, templateName)
				.setParameter(2, data.getEmail());
		TemplateEntity templateEntity = (TemplateEntity) query.getSingleResult();
		
		query = em.createQuery("SELECT c FROM CategoryEntity c");
		List<?> entities = query.getResultList();
		List<CategoryEntity> categories = convertToCategoryList(entities);
		CategoryEntity randomCategory = categories.get(random.nextInt(categories.size()));
		
		TemplateItemEntity templateItemEntity = new TemplateItemEntity(difficulty, amount, templateEntity, randomCategory);
		em.persist(templateItemEntity);
	}
}

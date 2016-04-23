package main.java.model;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import main.java.entities.CatDiffEntity;
import main.java.entities.CompanyEntity;
import main.java.entities.TemplateEntity;
import main.java.entities.TemplateItemEntity;
import main.java.model.dao.TemplateData;
import main.java.model.dao.TemplateItemData;

public class CompanyPersistence {
	@PersistenceContext(unitName="HRTrueTestBES")
	EntityManager em;

	public Iterable<TemplateData> getTemplatesForId(long id) {
		Query query = em.createQuery("SELECT t from TemplateEntity t WHERE t.company.id = ?1")
				.setParameter(1, id);
		List<TemplateEntity> res = query.getResultList();
		
		return TemplateEntity.convertToTemplateDataSet(res);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void addTemplateForId(long id, TemplateData template) {
		Query query = em.createQuery("SELECT c FROM CompanyEntity c WHERE c.id = ?1")
				.setParameter(1, id);
		CompanyEntity company = (CompanyEntity) query.getSingleResult();
		TemplateEntity entity = new TemplateEntity(template.getName(), company);
		em.persist(entity);
		addTemplateItemsForTemplate(entity, template.getItems());
		
	}
	
	@Transactional(propagation=Propagation.NESTED)
	public void addTemplateItemsForTemplate(TemplateEntity template, List<TemplateItemData> items){
		Set<TemplateItemData> itemsSet = new LinkedHashSet<>();
		for (TemplateItemData item : items){
			boolean existsInSet = false;
			for (TemplateItemData itemFromSet : itemsSet){
				if (item.equals(itemFromSet)){
					itemFromSet.setAmount((byte)(itemFromSet.getAmount() + item.getAmount()));
					existsInSet = true;
					break;
				}
			}
			if (!existsInSet){
				itemsSet.add(item);
			}
		}

		for (TemplateItemData item : itemsSet){
			Query query = em.createQuery("SELECT cd FROM CatDiffEntity cd WHERE cd.difficulty = ?1 AND cd.category = ?2")
					.setParameter(1, item.getDifficulty())
					.setParameter(2, item.getCategory());
			CatDiffEntity catDiffEntity = null;
			try{
				catDiffEntity = (CatDiffEntity) query.getSingleResult();
			} catch (NoResultException e){
				catDiffEntity = new CatDiffEntity(item.getDifficulty(), item.getCategory());
				em.persist(catDiffEntity);
			}
			TemplateItemEntity entity = new TemplateItemEntity(item.getAmount(), catDiffEntity, template);
			em.persist(entity);
		}
	}

}

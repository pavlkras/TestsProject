package main.java.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javassist.expr.NewArray;
import main.java.entities.BaseQuestionEntity;
import main.java.entities.TestEntity;
import main.java.model.dao.BaseQuestionData;
import main.java.model.dao.TestData;

public class TestingPersistence {
	@PersistenceContext(unitName="HRTrueTestBES")
	EntityManager em;

	public Iterable<BaseQuestionData> getTest(long id, String testDesc) {
		String[] description = testDesc.split(TestData.DELIMETER);
		if (!description[0].equals(new Long(id).toString())){
			throw new SecurityException("user wasn't authorized for test");
		}
		
		Query query = em.createQuery("SELECT q FROM BaseQuestionEntity q WHERE q.test.link = ?1")
				.setParameter(1, testDesc);
		List<BaseQuestionEntity> entities = query.getResultList();
		
		List<BaseQuestionData> questions = new ArrayList<BaseQuestionData>();
		for (BaseQuestionEntity entity : entities){
			questions.add(entity.convertToDataInstance());
		}
		
		return questions;
	}

	public long getCandidateFromTest(String testDesc) {
		Query query = em.createQuery("SELECT t FROM TestEntity t WHERE t.link =?1")
				.setParameter(1, testDesc);
		TestEntity testEntity = (TestEntity) query.getSingleResult();
				
		return testEntity.getCandidate().getId();
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public int startTesting(String testDesc) {
		Query query = em.createQuery("UPDATE TestEntity t SET start_date = ?1 WHERE t.link = ?2")
				.setParameter(1, new Date(System.currentTimeMillis()))
				.setParameter(2, testDesc);
		return query.executeUpdate();
	}
}

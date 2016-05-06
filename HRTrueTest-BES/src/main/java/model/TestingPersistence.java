package main.java.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import main.java.entities.BaseQuestionEntity;
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
}

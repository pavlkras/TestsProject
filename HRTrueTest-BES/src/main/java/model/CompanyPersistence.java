package main.java.model;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dao.test.AttentionTest;
import dao.test.SequenceTest;
import generator.Generator;
import main.java.entities.AttentionQuestionEntity;
import main.java.entities.BaseQuestionEntity;
import main.java.entities.CandidateEntity;
import main.java.entities.CatDiffEntity;
import main.java.entities.CompanyEntity;
import main.java.entities.NumericalQuestionEntity;
import main.java.entities.TemplateEntity;
import main.java.entities.TemplateItemEntity;
import main.java.entities.TestEntity;
import main.java.model.config.CategorySet;
import main.java.model.dao.CandidateData;
import main.java.model.dao.TemplateData;
import main.java.model.dao.TemplateItemData;
import main.java.model.dao.TestData;

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
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void createMultipleTests(long id, List<TestData> tests, CategorySet categories) {
		for (TestData test : tests){
			createSingleTest(id, test, categories);
		}
	}
	
	@Transactional(propagation=Propagation.NESTED)
	public void createSingleTest(long id, TestData test, CategorySet categories) {
		CandidateEntity candidateEntity = findOrCreateCandidate(test.getCandidate());
		
		Query query = em.createQuery("SELECT t FROM TemplateEntity t WHERE t.id = ?1 AND t.company.id = ?2")
				.setParameter(1, test.getTemplateId())
				.setParameter(2, id);
		TemplateEntity templateEntity = (TemplateEntity) query.getSingleResult();
		
		long currTimeInMillis = System.currentTimeMillis();
		String link = "" + candidateEntity.getId() + "-" + templateEntity.getId() + "-" + currTimeInMillis;
		TestEntity testEntity = new TestEntity(templateEntity, candidateEntity, link, 
				new Date(currTimeInMillis), null, null);
		em.persist(testEntity);
		
		generateMultipleCategoriesQuestions(templateEntity.getItems(), categories, testEntity);
	}

	private void generateMultipleCategoriesQuestions(Set<TemplateItemEntity> items, CategorySet categories, TestEntity testEntity) {
		for (TemplateItemEntity item : items){
			generateSingleCategoryQuestions(item, categories, testEntity);
		}
	}

	private void generateSingleCategoryQuestions(TemplateItemEntity itemEntity, CategorySet categories, TestEntity testEntity) {
		for (int i = 0; i < itemEntity.getAmount(); ++i){
			generateConcreteQuestion(itemEntity.getCatDiff(), categories, testEntity);
		}	
	}

	private void generateConcreteQuestion(CatDiffEntity catDiff, CategorySet categories, TestEntity testEntity) {
		BaseQuestionEntity question = null;
		String category = CategorySet.getCategoryNameById(catDiff.getCategory());
		
		Generator questionGenerator = new Generator();
		switch (category){
			case CategorySet.ABSTRACT_TASK:
			case CategorySet.AMERICAN_TEST:
			case CategorySet.JAVA_PROGRAMMING_TASK:
			case CategorySet.NUMERICAL_TASK: {
				SequenceTest test = questionGenerator.generateSequenceTest(catDiff.getDifficulty());
				Map<Integer, Boolean> keyMap = test.getAnswers();
				String sequence = NumericalQuestionEntity.buildNumbersSequence(Arrays.asList(test.getSequence()));
				String answers = NumericalQuestionEntity.buildNumbersSequence(keyMap.keySet());
				String correctAnswer = null;
				for (Integer key : keyMap.keySet()){
					if (keyMap.get(key)){
						correctAnswer = key.toString();
						break;
					}
				}
				question = new NumericalQuestionEntity(null, null, catDiff, testEntity, test.getDescription(), sequence, answers, correctAnswer);
				break;
			}
			case CategorySet.OPEN_QUESTION:
				break;
			case CategorySet.ATTENTION_TASK: {
				AttentionTest test = questionGenerator.generateAttentionTest(catDiff.getDifficulty());
				Map<String, Boolean> keyMap = test.getAnswers();
				String answers = AttentionQuestionEntity.buildAnswersSequence(keyMap.keySet());
				String correctAnswer = null;
				for (String key : keyMap.keySet()){
					if (keyMap.get(key)){
						correctAnswer = key;
						break;
					}
				}
				question = new AttentionQuestionEntity(null, null, catDiff, testEntity, test.getDescription(), answers, correctAnswer);
				break;
			}
			case CategorySet.PROGRAMMING_TASK:
			default:
				break;
		}
		em.persist(question);
	}

	@Transactional(propagation=Propagation.NESTED)
	public CandidateEntity findOrCreateCandidate(CandidateData candidate) {
		Query query = em.createQuery("SELECT c FROM CandidateEntity c WHERE c.email = ?1")
				.setParameter(1, candidate.getEmail());
		CandidateEntity candidateEntity = null;
		try {
			candidateEntity = (CandidateEntity) query.getSingleResult();
		} catch (NoResultException e){
			candidateEntity = new CandidateEntity(candidate.getEmail(),
					candidate.getFirstName(), candidate.getLastName());
			em.persist(candidateEntity);
		}
		return candidateEntity;
	}

}

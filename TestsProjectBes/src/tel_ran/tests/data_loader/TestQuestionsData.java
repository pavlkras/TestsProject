package tel_ran.tests.data_loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import json_models.CategoriesList;
import json_models.IJsonModels;
import json_models.QuestionModel;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tel_ran.tests.entitys.EntityCompany;
import tel_ran.tests.entitys.EntityPerson;
import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.entitys.EntityTest;
import tel_ran.tests.entitys.EntityTestQuestions;
import tel_ran.tests.entitys.EntityTestTemplate;
import tel_ran.tests.entitys.EntityTexts;
import tel_ran.tests.entitys.EntityTitleQuestion;
import tel_ran.tests.processor.TestProcessor;
import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.common.IPublicStrings;
import tel_ran.tests.services.fields.Role;

public class TestQuestionsData extends TestsPersistence implements
		IDataTestsQuestions {

	// ------------------------ STATUS OF DB ---------------------------------------------------------------------- //
	@Override
	public boolean isNoQuestions() {
		int count = getNumberQuestions(-1, Role.ADMINISTRATOR);
		if(count>0) return false;
		return true;
	}
	
	
	// ----------------------------------------------------------------------------------------------------------- //
	
	@Override
	public int getNumberQuestions(int id, Role role) {
		
		StringBuilder query = new StringBuilder("SELECT COUNT(eqa) from EntityQuestionAttributes eqa");
		
		switch(role) {
			case COMPANY :
				query.append(" WHERE eqa.").append(getLimitsForCompanyQuery(id)); break;
			case ADMINISTRATOR :
				query.append(" WHERE eqa.").append(getLimitsForCompanyQuery(ADMIN_C_ID)); break;
			default:
				break;				
		}		
		long result = (Long)em.createQuery(query.toString()).getSingleResult();	
		return (int) result;	
	}
		

	@Override
	public int getNumberTests(int id, Role role) {
		StringBuilder query = new StringBuilder("SELECT COUNT(et) from EntityTest et");
		
		switch(role) {
		case COMPANY :
			query.append(" WHERE et.").append(getLimitsForCompanyQuery(id)); break;
		case USER :		
		case GUEST :
		case PERSON: return 0; 
		default:
			break;				
		}
		long result = (Long)em.createQuery(query.toString()).getSingleResult();	
		return (int) result;	
	}
	
	//-------------------------------------QUESTIONS ---------------------------------------------------//
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED) 
	public EntityTitleQuestion createQuestion(String questionText) {
		EntityTitleQuestion objectQuestion;		
		
		if(questionText == null) {
			questionText = ICommonData.NO_QUESTION;
		}
		//// query if question exist as text in Data Base
		Query tempRes = em.createQuery("SELECT q FROM EntityTitleQuestion q WHERE q.questionText='"+questionText+"'");
		try{			
			objectQuestion = (EntityTitleQuestion) tempRes.getSingleResult();			
			
		}catch(javax.persistence.NoResultException e){				
			objectQuestion = new EntityTitleQuestion();	
			objectQuestion.setQuestionText(questionText);			
			em.persist(objectQuestion);			
		}								
		return objectQuestion;		
		
	}
	
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW) 
	public boolean saveNewQuestion(String fileLocationLink, String metaCategory,
			String category1, String category2, int levelOfDifficulty,
			List<String> answers, String correctAnswerChar, int answerOptionsNumber, String description,
			String questionText, int id, Role role) {
		boolean result = false;
		
		EntityTitleQuestion etq = createQuestion(questionText);
	
		EntityQuestionAttributes questionAttributesList = new EntityQuestionAttributes();// new question attributes creation		
		
		questionAttributesList.setDescription(description);		
		if(role.equals(Role.COMPANY)) {	
			EntityCompany ec = em.find(EntityCompany.class, id);
			questionAttributesList.setCompanyId(ec);
		} else if(role.equals(Role.ADMINISTRATOR)) {
			EntityCompany ec = em.find(EntityCompany.class, ADMIN_C_ID);
			questionAttributesList.setCompanyId(ec);
		}
		questionAttributesList.setEntityTitleQuestion(etq);	
		questionAttributesList.setFileLocationLink(fileLocationLink);	
		questionAttributesList.setMetaCategory(metaCategory);
		questionAttributesList.setCategory1(category1);		
		questionAttributesList.setCategory2(category2);
		questionAttributesList.setLevelOfDifficulty(levelOfDifficulty);	
		questionAttributesList.setCorrectAnswer(correctAnswerChar);		
		questionAttributesList.setNumberOfResponsesInThePicture(answerOptionsNumber);
		em.persist(questionAttributesList);	
		
		if(answers != null)	{			
			List<EntityTexts> answersList = new ArrayList<EntityTexts>();
			for (String answerText : answers) {	
				
				if(answerText!=null && answerText.length()>0) {
					EntityTexts ans = writeNewAnswer(answerText, questionAttributesList); 
					answersList.add(ans);
				}
			}			
			questionAttributesList.setQuestionAnswersList(answersList);// mapping to answers
			em.merge(questionAttributesList);	
		}				
		em.merge(etq);		
		
		result = true;
		return result;
	}
	
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED) 
	private EntityTexts writeNewAnswer(String answer, EntityQuestionAttributes qAttrId){		
		EntityTexts temp = new EntityTexts();
		temp.setAnswerText(answer);		
		temp.setEntityQuestionAttributes(qAttrId);	
		em.persist(temp);	
		em.clear();
		return temp;
	}
	
	@Override
	@Transactional
	public List<String> getUserCategories(int id, Role role) {		

		StringBuilder query = new StringBuilder("Select DISTINCT q.category1 FROM EntityQuestionAttributes q WHERE (q.metaCategory='");
		query.append(IPublicStrings.COMPANY_AMERICAN_TEST).append("' OR q.metaCategory='").append(IPublicStrings.COMPANY_QUESTION).
			append("') AND q.category1 is not null");
		
		switch(role) {
			case COMPANY :
				query.append(" AND q.").append(getLimitsForCompanyQuery(id)); break;
			case ADMINISTRATOR :
				query.append(" AND q.").append(getLimitsForNotCompanyQuery()); break;
			case USER :		
			case GUEST :
			case PERSON: 
			default:		
		}	
		query.append(" ORDER BY q.category1");	
		List<String> result = em.createQuery(query.toString()).getResultList();		
		
		return result;
	}

	
	@Override
	@Transactional
	public List<IJsonModels> getQuesionsList(Boolean typeOfQuestion, String metaCategory,
			String category1, int id, Role role) {
		
		//1 - start query 
		StringBuilder query = new StringBuilder("SELECT c FROM EntityQuestionAttributes c WHERE ");
		boolean needAND = false;
		
		//2 - adding id or other limits
		switch(role) {
		case COMPANY :
			query.append(" c.").append(getLimitsForCompanyQuery(id)); needAND = true; break;
		case ADMINISTRATOR :
			query.append(" c.").append(getLimitsForNotCompanyQuery()); needAND = true; break;
		case USER :		
		case GUEST :
		case PERSON: 
		default:		
		}	
		
		//3 - adding metaCategories		
		if(needAND) {
			query.append(" AND");			
		}	
		
		if(metaCategory==null) {
			
			if(typeOfQuestion) {								
				query.append(" (c.metaCategory='").append(IPublicStrings.COMPANY_AMERICAN_TEST)
						.append("' OR c.metaCategory='").append(IPublicStrings.COMPANY_QUESTION)
						.append("')");
				} else {
					List<String> cat = TestProcessor.getMetaCategory();
					int count = cat.size();
					query.append(" (");
					for(String c : cat) {
						query.append("c.metaCategory='").append(c).append("' OR ");					
					}
					int len = query.length();						
					query.delete(len-4, len);
					query.append(")");				
				}
		} else {					
			query.append(" c.metaCategory=").append(metaCategory);			
		}
		
			
		
		//4 - adding categories		
		if(category1!=null) {		
			query.append(" AND");
			query.append(" c.category1=").append(category1);			
		}
		
		//5 - adding sort		
		query.append(" ORDER BY c.id DESC");		
		System.out.println(query.toString()); // ---------------------------------------- SYSO ---- !!!!!!!!!!!!!!!!!!!!!
						
		//6 - get result 
		List<EntityQuestionAttributes> listOfEqa = em.createQuery(query.toString()).getResultList();
		List<IJsonModels> resultList = null;
		
		if(!listOfEqa.isEmpty()) {			
			resultList = new ArrayList<IJsonModels>();
			for(EntityQuestionAttributes eqa : listOfEqa) {
				boolean isImage;
				if(eqa.getFileLocationLink()!=null) {
					isImage = true;				
				} else {
					isImage = false;			
				}
				QuestionModel question = new QuestionModel(eqa.getId(), eqa.getMetaCategory(), 
						eqa.getCategory1(), eqa.getCategory2(), eqa.getLevelOfDifficulty(), isImage);				
				question.addShortDescription(eqa.getDescription());
				resultList.add(question);					
			}			
		}
		return resultList;
	}
			
	

	@Override
	public List<String> getUserMetaCategories(int id, Role role) {	
		
		StringBuilder query = new StringBuilder("Select DISTINCT cat.metaCategory FROM EntityQuestionAttributes cat WHERE cat.metaCategory is not null");
		switch(role) {
			case COMPANY :
				query.append(" AND cat.").append(getLimitsForCompanyQuery(id)); break;
			case ADMINISTRATOR :
			case USER :
				query.append(" AND cat.").append(getLimitsForNotCompanyQuery()); break;
			default:				
		}
		query.append(" ORDER BY cat.metaCategory");		
		
		return em.createQuery(query.toString()).getResultList();
	}
	
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW) 
	public long createPerson(String personPassport, String personName,
			String personSurname, String personEmail) {		
		
		EntityPerson person;
		String query = "Select p FROM EntityPerson p WHERE p.identify='" + personPassport + "'";
		
		System.out.println("I'm here + " + query);
		
		List<EntityPerson> result = em.createQuery(query).getResultList();
		if(result!=null && !result.isEmpty()) {
			person = result.get(0);
			
		} else {
			person = new EntityPerson();
			person.setIdentify(personPassport);
		}
		if(personEmail!=null && personEmail.length()>1)
			person.setPersonEmail(personEmail);
		if(personName!=null && personEmail.length()>1)
			person.setPersonName(personName);
		if(personSurname!=null && personEmail.length()>1)
			person.setPersonSurname(personSurname);
			
		em.persist(person);	
		
		return person.getPersonId();		
	}
	

	@Override
	public List<Long> getQuestionIdByParams(int id, Role role, String metaCategory,
			String category1, int level) {
		
			StringBuilder condition = new StringBuilder("SELECT c.id FROM EntityQuestionAttributes c WHERE ");
			condition.append("c.metaCategory=?1 AND c.levelOfDifficulty=?2");
			
			if(role.equals(Role.COMPANY)) {
				condition.append(" AND c.").append(getLimitsForCompanyQuery(id));
			} else {
				condition.append(" AND c.").append(getLimitsForNotCompanyQuery());
			}
			
			boolean c1 = false;
			if(category1!=null && !category1.equals(ICommonData.NO_CATEGORY1)) {
				condition.append(" AND c.category1=?3");
				c1 = true;
			}
			
			Query query = em.createQuery(condition.toString());
			query.setParameter(1, metaCategory);
			query.setParameter(2, level);
			if(c1) {			
				query.setParameter(3, category1);
			}
			
		return query.getResultList();	
	}

	
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW) 
	public long createTest(String pass, long personId, long startTime,
			long stopTime, List<Long> questionIdList, int id, Role role) {
		long result = -1;
				
		EntityTest test = new EntityTest();		
		test.setPassword(pass); 
		test.setStartTestDate(startTime);// setting parameter for wotchig method in FES
		test.setEndTestDate(stopTime);// setting parameter for wotchig method in FES		
			
		EntityCompany ec = null;
		if(role.equals(Role.COMPANY)) {
			ec = em.find(EntityCompany.class, id);
		}
		test.setEntityCompany(ec);
		
		EntityPerson ePerson = em.find(EntityPerson.class, personId);
		test.setEntityPerson(ePerson);	
		test.setAmountOfQuestions(questionIdList.size());
		test.setPassed(false);
		test.setChecked(false);
		em.persist(test);
		
		result = test.getId();
		
		
		for(Long qId : questionIdList) {
			EntityQuestionAttributes eqa = em.find(EntityQuestionAttributes.class, qId);
			EntityTestQuestions etq = new EntityTestQuestions();
			etq.setEntityQuestionAttributes(eqa);
			etq.setEntityTest(test);
			etq.setStatus(ICommonData.STATUS_NO_ANSWER);
			em.persist(etq);
			test.addEntityTestQuestions(etq);
		}
		
		em.merge(test);		

		return result;
		
	}

	// ------------------------- LISTS OF CATEGORIES ------------------------------------- //
	
	// LIST OF CUSTOM CATEGORIES
	@Override
	public CategoriesList getCategoriesList(Role role, int companyId) {
		
		//1 - check role. If it's an ADMIN companyId = 1			
		if (role.equals(Role.ADMINISTRATOR)) companyId = ADMIN_C_ID;
		if(companyId<0) return null;
				
		//2 - get List of categories1
		List<String> categories1 = getCategories(companyId, 1, null, -1);
		
		//3 - get Map for each value in the list
		Map<String, Map<String,List<String>>> map = new LinkedHashMap<>();
		
		for(String s : categories1) {
			Map<String, List<String>> innerMap = getCustomCategories2WithMetaCategory(s, companyId);
			map.put(s, innerMap);			
		}
						
		//4 - create object of CategoryMaps
		CategoriesList result = new CategoriesList();
		result.setCategoriesWithData(map);
		
		return result;
	}

	@Override
	public List<String> getCategories(int companyId, int categoryLevel,
			String parent, int levelOfParent) {
		List<String> result = null;
		String categoryType;
		String parentType = null;
		StringBuilder textQuery = new StringBuilder("SELECT DISTINCT c.");
		switch(categoryLevel) {
			case 0 : categoryType = "metaCategory"; break;
			case 1 : categoryType = "category1"; break;
			case 2 : categoryType = "category2"; break;
			default: return result;
		}		
		textQuery.append(categoryType);
		
		textQuery.append(" FROM EntityQuestionAttributes c WHERE");
		
		if(levelOfParent>=0) {
			switch(levelOfParent) {
				case 0 : parentType = "metaCategory"; break;
				case 1 : parentType = "category1"; break;			
			}
			textQuery.append(" c.").append(parentType);
			if(parent==null) {
				textQuery.append(" is null");
			} else {
				textQuery.append("='").append(parent).append("'");
			}
		}
		
		
		textQuery.append(" AND c.");		
		if(companyId<0) {
			textQuery.append(getLimitsForNotCompanyQuery());			
		} else {
			textQuery.append(getLimitsForCompanyQuery(companyId));
		}
		
		textQuery.append(" ORDER BY c.").append(categoryType);
			
		result = em.createQuery(textQuery.toString()).getResultList();		
		
		return result;
	}
	
	private Map<String,List<String>> getCustomCategories2WithMetaCategory(
			String category1, int companyId) {
		
		
		StringBuilder queryBuilder = new StringBuilder("SELECT c.metaCategory FROM EntityQuestionAttributes c WHERE c.");
		queryBuilder.append(getLimitsForCompanyQuery(companyId));
		queryBuilder.append(" AND c.category1=?1 AND c.category2 is null");
		Query query = em.createQuery(queryBuilder.toString());
		query.setParameter(1, category1);
		List<String> resultsQuery = query.getResultList();
		Map<String,List<String>> map = new HashMap<>();
		List<String> mc;
		if(resultsQuery!=null) {			 
			mc = new ArrayList<String>();
			if(resultsQuery.contains(IPublicStrings.COMPANY_AMERICAN_TEST)) {
				mc.add(IPublicStrings.COMPANY_AMERICAN_TEST);
			}
			if(resultsQuery.contains(IPublicStrings.COMPANY_QUESTION)) {
				mc.add(IPublicStrings.COMPANY_QUESTION);
			}			
			map.put(null, mc);				
		}
		
		List<String> listCategories2 = getCategories(companyId, 2, category1, 1);	
		
		if(listCategories2!=null) {
			queryBuilder = new StringBuilder("SELECT c.metaCategory FROM EntityQuestionAttributes c WHERE c.");			
			queryBuilder.append(getLimitsForCompanyQuery(companyId));
			queryBuilder.append(" AND c.category2=?1 AND c.category1=?2");			
			
						
			for(String category2 : listCategories2) {				
				mc = new ArrayList<>();
				query = em.createQuery(queryBuilder.toString());
				query.setParameter(2, category1);
				query.setParameter(1, category2);				
				List<String> mcQuery = query.getResultList();
				if(mcQuery!=null) {
					if (mcQuery.contains(IPublicStrings.COMPANY_AMERICAN_TEST)) {
						mc.add(IPublicStrings.COMPANY_AMERICAN_TEST);						
					} 
					if(mcQuery.contains(IPublicStrings.COMPANY_QUESTION)) {
						mc.add(IPublicStrings.COMPANY_QUESTION);						
					}	
					map.put(category2, mc);					
				}					
			}			
		}
					
		return map;
	}
	

	
	// -------------------------------------- TEST TEMPLATES --------------------------------------------- //
	
	@Override
	@Transactional(readOnly=false)
	public void createTemplate(EntityTestTemplate entity, Role role, long companyId) {
		EntityCompany company = em.find(EntityCompany.class, getCompanyId(role, companyId));
		entity.setEntityCompany(company);
		em.persist(entity);
				
		String templateName = entity.getTemplateName();
		if(templateName==null) {
			templateName = "Template_" + entity.getId();
			entity.setTemplateName(templateName);
			em.merge(entity);
		}		
	}


	@Override
	public List<EntityQuestionAttributes> getQuestionListByParams(
			String metaCategory, String category1, String category2,
			int difficulty, Role role, long id) {	
		
		StringBuilder queryString = new StringBuilder("SELECT c FROM EntityQuestionAttributes c WHERE c.");
		queryString.append(getLimitsForCompanyQuery(getCompanyId(role, id)));
		
		if(metaCategory!=null && !metaCategory.isEmpty()) {
			queryString.append(" AND c.metaCategory='").append(metaCategory).append("'");
		}
		
		if(category1!=null && !category1.isEmpty()) {
			queryString.append(" AND c.category1='").append(category1).append("'");
		}
		
		if(category2!=null && !category2.isEmpty()) {
			queryString.append(" AND c.category2='").append(category2).append("'");
		}
		
		if(difficulty>0) {
			queryString.append(" AND c.levelOfDifficulty='").append(difficulty).append("'");
		}
		
		List<EntityQuestionAttributes> result = (List<EntityQuestionAttributes>) em.createQuery(queryString.toString());
				
		return result;
	}
	
	
	@Override
	public EntityQuestionAttributes findQuestionById(Long id) {		
		return (EntityQuestionAttributes) em.find(EntityQuestionAttributes.class, id);
	}

	@Override
	@Transactional(readOnly=false)
	public long createPerson(EntityPerson entity) {
		em.persist(entity);		 
		return entity.getPersonId();
		
	}

	@Override
	@Transactional(readOnly=false)
	public long createTest(EntityTest test,
			List<EntityTestQuestions> questions, long personId, Role role, long id) {
		
		EntityPerson person = em.find(EntityPerson.class, personId);
		test.setEntityCompany(em.find(EntityCompany.class, getCompanyId(role, id)));
		test.setEntityPerson(person);
		em.persist(test);				
		for(EntityTestQuestions etq : questions) {
			etq.setEntityTest(test);
			em.persist(etq);
		}
		return test.getId();
		
	}
	
	@Override
	public EntityTest findTestById(long testId) {		
		return em.find(EntityTest.class, testId);
	}


	
	//------------------------------------ INNER METHODS ------------------------------------------------//
	private String getLimitsForCompanyQuery(int id) {	
//		EntityCompany ec = em.find(EntityCompany.class, id);
		return "entityCompany='" + id + "'";
	}
	
	private String getLimitsForNotCompanyQuery() {	
		return getLimitsForCompanyQuery(ADMIN_C_ID);
	}
	
	private int getCompanyId(Role role, long companyId) {
		if(role.equals(Role.ADMINISTRATOR)) return ADMIN_C_ID;
		return (int)companyId;
	}


	

	


	


	
}

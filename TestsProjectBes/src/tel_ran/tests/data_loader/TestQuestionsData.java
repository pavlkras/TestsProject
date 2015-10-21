package tel_ran.tests.data_loader;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tel_ran.tests.entitys.EntityCompany;
import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.entitys.EntityTexts;
import tel_ran.tests.entitys.EntityTitleQuestion;
import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.fields.Role;

public class TestQuestionsData extends TestsPersistence implements
		IDataTestsQuestions {

	@Override
	public int getNumberQuestions(long id, Role role) {
		
		StringBuilder query = new StringBuilder("SELECT COUNT(eqa) from EntityQuestionAttributes eqa");
		
		switch(role) {
			case COMPANY :
				query.append(" WHERE eqa.").append(getLimitsForCompanyQuery(id)); break;
			default:
				break;				
		}		
		long result = (Long)em.createQuery(query.toString()).getSingleResult();	
		return (int) result;	
	}
		

	@Override
	public int getNumberTests(long id, Role role) {
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
			String questionText, long id, Role role) {
		boolean result = false;
		
		EntityTitleQuestion etq = createQuestion(questionText);
	
		EntityQuestionAttributes questionAttributesList = new EntityQuestionAttributes();// new question attributes creation		
		
		questionAttributesList.setDescription(description);		
		if(role.equals(Role.COMPANY)) {	
			EntityCompany ec = em.find(EntityCompany.class, id);
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
	
	//------------------------------------ INNER METHODS ------------------------------------------------//
	private String getLimitsForCompanyQuery(long id) {	
//		EntityCompany ec = em.find(EntityCompany.class, id);
		return "entityCompany='" + id + "'";
	}



	
}

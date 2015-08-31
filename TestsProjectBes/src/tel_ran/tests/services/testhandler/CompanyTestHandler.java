package tel_ran.tests.services.testhandler;

import java.util.List;

import javax.persistence.EntityManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tel_ran.tests.entitys.EntityTest;
import tel_ran.tests.entitys.EntityTestQuestions;
import tel_ran.tests.services.common.ICommonData;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

public class CompanyTestHandler implements ICompanyTestHandler {
	
	
	
	@PersistenceContext(unitName="springHibernate", type=PersistenceContextType.TRANSACTION)	
	public EntityManager em;
		
	
	private static final String LOG = CompanyTestHandler.class.getSimpleName();
		
	private EntityTest test;
	private List<EntityTestQuestions> listQuestions;
	
	public CompanyTestHandler() {
		
	}
	
	public void setTestId(long testId) {
		if(em==null)
			System.out.println("!!!!!!!!!!!!!! NULLLL");
		this.test = em.find(EntityTest.class, testId);
		setListQuestions();
	}
	
	public CompanyTestHandler(EntityManager em, EntityTest test) {
		super();			
		this.test = test;	
		setListQuestions();
	}
	

	public CompanyTestHandler(EntityManager em, long testId) {
		super();				
		this.test = em.find(EntityTest.class, testId);
		setListQuestions();
	}
	
	@SuppressWarnings("unchecked")
	protected void setListQuestions() {
		String query = "SELECT c from EntityTestQuestions c WHERE c.entityTest=?1";
		
		listQuestions = em.createQuery(query).setParameter(1, test).getResultList();	
	}
	
	// returns array with amount of answers in the test:
	// 4 - all
	// 3 - correct
	// 2 - inCorrect
	// 1 - unChecked
	// 0 - unAnswered	
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public int[] renewStatusOfTest() {
		int[] result = new int[5];
				
		for(EntityTestQuestions etq : listQuestions) {
			int status = etq.getStatus();			
			result[4]++;
			result[status]++;
			System.out.println(status);
			
		}
		
		boolean testIsPassed;		
				
		System.out.println(LOG + " -58-M: renewStatusOfTest - CORRECT = " + result[ICommonData.STATUS_CORRECT]);
		System.out.println(LOG + " -59-M: renewStatusOfTest - INCORRECT = " + result[ICommonData.STATUS_INCORRECT]);
		System.out.println(LOG + " -60-M: renewStatusOfTest - UNANSWERED = " + result[ICommonData.STATUS_NO_ANSWER]);
		System.out.println(LOG + " -61-M: renewStatusOfTest - UNCHECKED = " + result[ICommonData.STATUS_UNCHECKED]);
		
		if(result[ICommonData.STATUS_NO_ANSWER]==0) {			
			testIsPassed = true;
			if(!test.isPassed()) {				
				System.out.println(LOG + " -328-M: renewStatusOfTest - i'm writing that test is passed");
				test.setPassed(true);	
				em.merge(test);
			}
		} else {
			testIsPassed = false;
		}
		
		if(result[ICommonData.STATUS_UNCHECKED]==0 && testIsPassed) {			
			if(!test.isChecked()) {
				test.setChecked(true);	
				em.merge(test);
			}
		}
						
//		if(test.getAmountOfCorrectAnswers()!=result[ICommonData.STATUS_CORRECT]) {
			test.setAmountOfCorrectAnswers(result[ICommonData.STATUS_CORRECT]);	
			em.merge(test);
//		}
		
		return result;
	}
	

}

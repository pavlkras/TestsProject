package tel_ran.tests.services.processes;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tel_ran.tests.entitys.EntityTest;
import tel_ran.tests.entitys.EntityTestQuestions;
import tel_ran.tests.services.TestsPersistence;
import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.subtype_handlers.ITestQuestionHandler;
import tel_ran.tests.services.subtype_handlers.SingleTestQuestionHandlerFactory;

@Service
public class TestFinisher extends TestsPersistence implements ITestProcess {
	
	private long testId;
	private static final String LOG = TestFinisher.class.getSimpleName();
	
	public TestFinisher() {
		
	}
	
	public void setTestId(long testId) {
		this.testId = testId;
	}


	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void run() {
		System.out.println(LOG + " -398 - in finishTest");
		EntityTest test = em.find(EntityTest.class, testId);
		long time = System.currentTimeMillis();
		test.setEndTestDate(time);	
		test.setDuration((int)(time - test.getStartTestDate()));
		em.merge(test);
		System.out.println(LOG + " - 404: fininshTest");
		int numQuestions = test.getAmountOfQuestions();
				
		List<EntityTestQuestions> list = getTestQuestions(test);
		int[] result = new int[numQuestions];
		
		for (int i = 0; i < numQuestions; i++) {
			ITestQuestionHandler testQuestionHandler = SingleTestQuestionHandlerFactory.getInstance(list.get(i));
			testQuestionHandler.setEntityQuestionAttributes(list.get(i).getEntityQuestionAttributes());
			testQuestionHandler.setCompanyId(test.getEntityCompany().getId());
			testQuestionHandler.setTestId(testId);
			testQuestionHandler.setEtqId(list.get(i).getId());
			result[i] = testQuestionHandler.checkResult();
			System.out.println(LOG + " status " + result[i]);
		}		
		
		saveStatus(result, test);	
		
	}
	
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	private boolean saveStatus(int[] answers, EntityTest test) {
		int status[] = new int[4];
		
		for (int i = 0; i < answers.length; i++) {
			status[answers[i]]++;
		}
		
		if(status[ICommonData.STATUS_NO_ANSWER] == 0) {
			test.setPassed(true);
			
		}
		
		if(status[ICommonData.STATUS_UNCHECKED]==0 && status[ICommonData.STATUS_NO_ANSWER] ==0) {
			test.setChecked(true);
			
		}
		
		test.setAmountOfCorrectAnswers(status[ICommonData.STATUS_CORRECT]);
		em.merge(test);
		
		return true;
		
	}
	
	protected List<EntityTestQuestions> getTestQuestions(EntityTest test) {
		String query = "SELECT c from EntityTestQuestions c WHERE c.entityTest=?1";
		return em.createQuery(query).setParameter(1, test).getResultList();
	}

}

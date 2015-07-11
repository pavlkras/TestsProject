package tel_ran.tests.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tel_ran.tests.entitys.EntityTest;
import tel_ran.tests.services.interfaces.IFileManagerService;
import tel_ran.tests.services.interfaces.IPersonalActionsService;
import tel_ran.tests.services.testhandler.IPersonTestHandler;
import tel_ran.tests.services.testhandler.PersonTestHandler;

public class PersonalActionsService extends TestsPersistence implements	IPersonalActionsService {	
	@Autowired
	IFileManagerService fileManager;
	private long testID;
	private long companyID;
	////
	@Override
	public boolean GetTestForPerson(String testPassword) {	// creation test for person
		boolean actionResult = false;
		EntityTest tempRes = (EntityTest) em.createQuery("SELECT test FROM EntityTest test WHERE test.password='"+testPassword+"'").getSingleResult();
		if(tempRes != null){
			testID = tempRes.getTestId();
			companyID = 0;//tempRes.getCompanyId();      //------ to do ?????
			getTestResultsHandler(companyID, testID, fileManager);
			actionResult = true;
		}

		return actionResult;
	}
	////-------  Processing  ----------------// START //
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String getNextQuestion(long testId){
		String question = null; 
		EntityTest test = em.find(EntityTest.class, testId);

		if(!test.isPassed()){
			IPersonTestHandler testResultsJsonHandler = getTestResultsHandler(test.getEntityCompany().getId(), testId, fileManager);

			question = testResultsJsonHandler.next();
			if ( question == null ){
				//TODO new Thread needed
				testResultsJsonHandler.analyzeAll();

				test.setAmountOfCorrectAnswers(testResultsJsonHandler.getRightAnswersQuantity());
				test.setPassed(true);
				em.persist(test);
			}
		}
		return question;
	}

	@Override
	public void setAnswer(long testId, String jsonAnswer){
		EntityTest test = em.find(EntityTest.class, testId);

		if(!test.isPassed()){
			getTestResultsHandler(test.getEntityCompany().getId(), testId, fileManager).setAnswer(jsonAnswer);
		}
	}

	IPersonTestHandler getTestResultsHandler(long companyId, long testId, IFileManagerService fileManager){
		return new PersonTestHandler(companyId, testId, fileManager, em);
	}
	////-------  Processing  ----------------// END //
}
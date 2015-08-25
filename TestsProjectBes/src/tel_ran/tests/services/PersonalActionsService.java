package tel_ran.tests.services;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.entitys.EntityTest;
import tel_ran.tests.entitys.EntityTestQuestions;
import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.interfaces.IPersonalActionsService;
import tel_ran.tests.services.subtype_handlers.ITestQuestionHandler;
import tel_ran.tests.services.subtype_handlers.SingleTestQuestionHandlerFactory;
import tel_ran.tests.services.testhandler.IPersonTestHandler;
import tel_ran.tests.services.testhandler.PersonTestHandler;
import tel_ran.tests.services.utils.FileManagerService;
import tel_ran.tests.token_cipher.TokenProcessor;

public class PersonalActionsService extends CommonServices implements IPersonalActionsService {	
	
	@Autowired
	TokenProcessor tokenProcessor;
	
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
			getTestResultsHandler(companyID, testID);
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
			if(test.getStartTestDate()==0){
				test.setStartTestDate(new Date().getTime());
				em.persist(test);
			}
			IPersonTestHandler testResultsJsonHandler = getTestResultsHandler(test.getEntityCompany().getId(), testId);

			question = testResultsJsonHandler.next();
			if ( question == null ){
				//TODO new Thread needed
				testResultsJsonHandler.analyzeAll();

				test.setAmountOfCorrectAnswers(testResultsJsonHandler.getRightAnswersQuantity());
				test.setEndTestDate(new Date().getTime());
				test.setPassed(true);
				em.persist(test);
			}
		}
		return question;
	}

	
	/**
	 * Returns JSON of all questions in short version (for person) for the given test
	 * @param testId = id of the test
	 * @return String = JSON
	 */	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String getAllTestQuestions(long testId) {		
		String result = null;	
		EntityTest test = em.find(EntityTest.class, testId);
		if(!test.isPassed()) {
			test.setStartTestDate(System.currentTimeMillis());
						
			List<EntityTestQuestions> questions = test.getEntityTestQuestions();
			
			JSONArray arrayResult = new JSONArray();
			
			int index = 0;
				
			for (EntityTestQuestions etq : questions) {		
				
					if(etq.getStatus() == ICommonData.STATUS_NO_ANSWER) {						
						EntityQuestionAttributes eqa = etq.getEntityQuestionAttributes();						
						ITestQuestionHandler handler = SingleTestQuestionHandlerFactory.getInstance(eqa);
						JSONObject jsn;
						try {
							jsn = handler.getJsonForTest(etq.getId(), index);
							arrayResult.put(jsn);	
							index++;
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}											
				}				
			}			
			
			result = arrayResult.toString();
		}
		return result;
	}
	
	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void setAnswer(long testId, String jsonAnswer){
		
		EntityTest test = em.find(EntityTest.class, testId);

		if(!test.isPassed()){
			getTestResultsHandler(test.getEntityCompany().getId(), testId).setAnswer(jsonAnswer);
			try {
				JSONObject jsn = new JSONObject(jsonAnswer);
				if(jsn.getBoolean("finished")) {
					finishTest(testId);
					getStatusOfTest(testId);
					
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	private void finishTest(long testId) {
		EntityTest test = em.find(EntityTest.class, testId);
		long time = System.currentTimeMillis();
		test.setEndTestDate(time);	
		em.merge(test);
		
	}
	
	
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	private JSONObject getStatusOfTest(long testId) throws JSONException {
		int correct = 0;
		int inCorrect = 0;
		int unAnswered = 0;
		int unChecked = 0;
		JSONArray arrayUnchecked = new JSONArray();
		
		EntityTest test = em.find(EntityTest.class, testId);
		
		String query = "SELECT c from EntityTestQuestions c WHERE c.entityTest=?1";
		List<EntityTestQuestions> list = em.createQuery(query).setParameter(1, test).getResultList();		
		
				
		for(EntityTestQuestions etq : list) {
			int status = etq.getStatus();
			
			switch(status) {
			case ICommonData.STATUS_CORRECT :
				correct++;
				break;
			case ICommonData.STATUS_INCORRECT :
				inCorrect++;
				break;
			case ICommonData.STATUS_NO_ANSWER :
				unAnswered++;
				break;
			case ICommonData.STATUS_UNCHECKED :
				unChecked++;
				JSONObject jsn = new JSONObject();
				jsn.put(ICommonData.JSN_TEST_QUESTION_ID, etq.getId());				
				break;
			}
		}
		
		boolean testIsPassed;
		boolean testIsChecked;
		
				
		if(unAnswered==0) {			
			testIsPassed = true;
			if(!test.isPassed()) {				
				test.setPassed(true);
				em.merge(test);				
			}
		} else {
			testIsPassed = false;
		}
		
		if(unChecked==0) {
			testIsChecked = true;
			if(!test.isChecked()) {
				test.setChecked(true);
				em.merge(test);
			}
		} else {
			testIsChecked = false;
		}
		
		if(test.getAmountOfCorrectAnswers()!=correct) {
			test.setAmountOfCorrectAnswers(correct);
			em.merge(test);
		}		
		
			
		JSONObject result = new JSONObject();
		result.put(ICommonData.JSN_TEST_CORRECT_ANSWERS, correct);
		result.put(ICommonData.JSN_TEST_INCORRECT_ANSWERS, inCorrect);
		result.put(ICommonData.JSN_TEST_IS_CHECKED, testIsChecked);
		result.put(ICommonData.JSN_TEST_IS_PASSED, testIsPassed);
		result.put(ICommonData.JSN_TEST_LIST_ANSWERS_TO_CHECK, arrayUnchecked);
		result.put(ICommonData.JSN_TEST_QUESTION_NUMBER, test.getAmountOfQuestions());
		result.put(ICommonData.JSN_TEST_UNANSWERED_ANSWERS, unAnswered);
		result.put(ICommonData.JSN_TEST_UNCHECKED_ANSWERS, unChecked);
		return result;
	}

	IPersonTestHandler getTestResultsHandler(long companyId, long testId){
		return new PersonTestHandler(companyId, testId, em);
	}
	////-------  Processing  ----------------// END //
	@Override
	public String getToken(String password) {
		// TODO password is potentially dangerous because it is directly the same inside of the request.
		// Check how to write this code without SQL-injection problem !!!
		String token = null;
		EntityTest test = (EntityTest) em.createQuery("Select t from EntityTest t where t.password='" + password +"'" ).getSingleResult();
		if(test != null){
			token = tokenProcessor.encodeIntoToken(test.getTestId(), ICommonData.TOKEN_VALID_IN_SECONDS);
		}
		return token;
	}
	
	public boolean testIsPassed(long testId){
		boolean res = false;
		EntityTest test = null;		
		try{
			test = em.find(EntityTest.class, testId);
			if(test.isPassed()){
				res = true;
			}
		}catch (Exception e){
			e.printStackTrace();
		}

		return res;
	}
	@Override
	public void saveImage(long testId, String image) {
		EntityTest test = em.find(EntityTest.class, testId);

		if(!test.isPassed()){
			FileManagerService.saveImage(test.getEntityCompany().getId(), testId, image);
		}
	}
	@Override
	protected boolean ifAllowed(EntityQuestionAttributes question) {
		
		return false;
	}

	@Override
	protected String getLimitsForQuery() {
		return null;
	}
	
	@Override
	public String getUserInformation() {		
		return null;
	}
	
	
	
}
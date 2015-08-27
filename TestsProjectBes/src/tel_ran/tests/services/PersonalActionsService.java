package tel_ran.tests.services;
import java.io.IOException;
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
	private static final String LOG = PersonalActionsService.class.getSimpleName();
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
	
	// ---- NO USED ----------- //
	// ----- OLD VERSION ------- //
	////-------  Processing  ----------------// START //
//	@Override
//	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String getNextQuestion(long testId){
		String question = null; 
//		EntityTest test = em.find(EntityTest.class, testId);
//
//		if(!test.isPassed()){
//			if(test.getStartTestDate()==0){
//				test.setStartTestDate(new Date().getTime());
//				em.persist(test);
//			}
//			IPersonTestHandler testResultsJsonHandler = getTestResultsHandler(test.getEntityCompany().getId(), testId);
//
//			question = testResultsJsonHandler.next();
//			if ( question == null ){
//				//TODO new Thread needed
//				testResultsJsonHandler.analyzeAll();
//
//				test.setAmountOfCorrectAnswers(testResultsJsonHandler.getRightAnswersQuantity());
//				test.setEndTestDate(new Date().getTime());
//				test.setPassed(true);
//				em.persist(test);
//			}
//		}
		return question;
	}

	
	/**
	 * NEW FLOW that gets all questions for the test begins here
	 * It returns the list of the questions that have status = "unAnswered"
	 * If there aren't such questions or the test has been passed it returns null
	 * Returns JSON of all questions in short version (for person) for the given test
	 * @param testId = id of the test
	 * @return String = JSON
	 */	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String getAllTestQuestions(long testId) {		
		String result = null;	
		
		// find test in DB by testId
		EntityTest test = em.find(EntityTest.class, testId);
		
		// check if the test is passed
		if(!test.isPassed()) {
			
			// save start time of test if
			if (test.getStartTestDate()==0)
				test.setStartTestDate(System.currentTimeMillis());
			
			// get list of test-questions (from EntityTestQuestions)
			List<EntityTestQuestions> questions = test.getEntityTestQuestions();
			
			JSONArray arrayResult = new JSONArray();			
			int index = 0;		
			
			for (EntityTestQuestions etq : questions) {
				
					// check if this questions is unaswered
					if(etq.getStatus() == ICommonData.STATUS_NO_ANSWER) {	
						// get question (EntityQuestionAttributes) from test-question
						EntityQuestionAttributes eqa = etq.getEntityQuestionAttributes();
						
						// create handler for the question and get JSON
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
			
			if(questions.size()>0)
				result = arrayResult.toString();
		}
		return result;
	}
	
	/** 
	 * The flow of saving the answer of Person
	 * It's called by PersonTestRESTController
	 * @param testId - id of test
	 * @param jsonAnswer - JSON with answer and test-question ID (EntityTestQuestions) 
	 */
	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public void setAnswer(long testId, String jsonAnswer){
		
		EntityTest test = em.find(EntityTest.class, testId);
		long compId = test.getEntityCompany().getId();

		if(!test.isPassed()){
			//save answer
			getTestResultsHandler(compId, testId).setAnswer(jsonAnswer);
			try {
				//check if this answer is last in the test
				JSONObject jsn = new JSONObject(jsonAnswer);
				if(jsn.getBoolean("finished")) {
					finishTest(testId, compId);
					getStatusOfTest(testId);
					
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}		
	}
	

	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	private void finishTest(long testId, long compId) {
		EntityTest test = em.find(EntityTest.class, testId);
		long time = System.currentTimeMillis();
		test.setEndTestDate(time);	
		test.setDuration((int)(test.getStartTestDate() - time));
		em.merge(test);
		System.out.println(LOG + " - 181-M: fininshTest");
		int numQuestions = test.getAmountOfQuestions();
				
		List<EntityTestQuestions> list = getTestQuestions(test);
		
		for (int i = 0; i < numQuestions; i++) {
			ITestQuestionHandler testQuestionHandler = SingleTestQuestionHandlerFactory.getInstance(list.get(i));
			testQuestionHandler.setEntityQuestionAttributes(list.get(i).getEntityQuestionAttributes());
			testQuestionHandler.setCompanyId(compId);
			testQuestionHandler.setTestId(testId);
			testQuestionHandler.setEtqId(list.get(i).getId());
			testQuestionHandler.checkResult();
			
		}		
		
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
		System.out.println(LOG + " - 205: M:  saveImage");
		EntityTest test = em.find(EntityTest.class, testId);

		if(!test.isPassed()){
			System.out.println(LOG + " - 209: M:  saveImage - test is not passed");
			try {
				FileManagerService.saveImage(test.getEntityCompany().getId(), testId, image);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
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
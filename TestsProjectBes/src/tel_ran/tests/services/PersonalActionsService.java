package tel_ran.tests.services;
import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.entitys.EntityTest;
import tel_ran.tests.entitys.EntityTestQuestions;
import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.interfaces.IPersonalActionsService;
import tel_ran.tests.services.processes.ITestProcess;
import tel_ran.tests.services.processes.TestFinisher;
import tel_ran.tests.services.subtype_handlers.ITestQuestionHandler;
import tel_ran.tests.services.subtype_handlers.SingleTestQuestionHandlerFactory;
import tel_ran.tests.services.testhandler.IPersonTestHandler;
import tel_ran.tests.services.testhandler.PersonTestHandler;
import tel_ran.tests.services.utils.FileManagerService;
import tel_ran.tests.token_cipher.TokenProcessor;


public class PersonalActionsService extends CommonServices implements IPersonalActionsService {	
			
	@Autowired
	TokenProcessor tokenProcessor;
	
	private EntityTest entityTest;
	
	private long testID;
	private long companyID;
	
	/**
	 * TaskExecutor is used for the process of test finishing (analyzing in a thread)
	 */
	@Autowired
	TaskExecutor taskExecutor;
	
	@Autowired
	ITestProcess testFinisher;
	
	private static final String LOG = PersonalActionsService.class.getSimpleName();
	////
	@Override
	public boolean GetTestForPerson(String testPassword) {	// creation test for person
		boolean actionResult = false;
		EntityTest tempRes = (EntityTest) em.createQuery("SELECT test FROM EntityTest test WHERE test.password='"+testPassword+"'").getSingleResult();
		if(tempRes != null){
			testID = tempRes.getId();
			companyID = 0;//tempRes.getCompanyId();      //------ to do ?????
			getTestResultsHandler(companyID, testID);
			actionResult = true;
		}

		return actionResult;
	}
	
	
	IPersonTestHandler getTestResultsHandler(long companyId, long testId){
		return new PersonTestHandler(companyId, testId, em);
	}
	

	@Override
	public String getToken(String password) {
		// TODO password is potentially dangerous because it is directly the same inside of the request.
		// Check how to write this code without SQL-injection problem !!!
		String token = null;
		EntityTest test = (EntityTest) em.createQuery("Select t from EntityTest t where t.password='" + password +"'" ).getSingleResult();
		if(test != null){
			token = tokenProcessor.encodeIntoToken(test.getId(), ICommonData.TOKEN_VALID_IN_SECONDS);
		}
		return token;
	}
	

	@Override
	public void saveImage(long testId, String image) {		
		EntityTest test = em.find(EntityTest.class, testId);

		if(!test.isPassed()){
			
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
	@Transactional(readOnly=false)
	public String saveAndGetNextQuestion(long testId, String answer) {
		
		if(this.entityTest==null || this.testID != testId) {
			entityTest = em.find(EntityTest.class, testId);
			this.testID = testId;
		}
		String result = null;
		
		
		
		// check if the test is passed
		if(!entityTest.isPassed()) {
			long gotAnswer = -1;
			//save answer
			
			if(answer!=null && answer.length()>3)
				try {
					gotAnswer = this.saveAnswer(answer);					
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
						
			// save start time of test is new
			if (entityTest.getStartTestDate()==0) {
				entityTest.setStartTestDate(System.currentTimeMillis());
				em.merge(entityTest);
			}
			
			
			// get list of test-questions (from EntityTestQuestions)				
			String query = "SELECT c FROM EntityTestQuestions c WHERE c.entityTest=?1 AND c.id!=?2 AND c.status=?3 ORDER by c.id";
			List<EntityTestQuestions> questions = em.createQuery(query).setParameter(1, entityTest)
					.setParameter(2, gotAnswer).setParameter(3, ICommonData.STATUS_NO_ANSWER).getResultList();
			
				
			if(questions!=null && questions.size()>0) {							
				int index = entityTest.getAmountOfQuestions() - questions.size();
				EntityTestQuestions etq = questions.get(0);				
				ITestQuestionHandler handler = SingleTestQuestionHandlerFactory.getInstance(etq);
				JSONObject jsn = null;
				try {
					jsn = handler.getJsonForTest(etq.getId(), index);
											
				} catch (JSONException e) {					
					e.printStackTrace();
				}	
				result = jsn.toString();
			} else {	
				//if the test is finished (no questions in the response from DB), 
				//TestFinisher will be start in a new Thread
				testFinisher.setTestId(testId);
				taskExecutor.execute(testFinisher);
			}
						
		}		
		
		return result;
		
		
	}
	
	
	
	
	
	private long saveAnswer(String answer) throws JSONException {	

		System.out.println(answer);

		
			JSONObject jsn = new JSONObject(answer);
			long etqId = 0;
			try {
				etqId = jsn.getLong(ICommonData.JSN_INTEST_QUESTION_ID);
				EntityTestQuestions etq = em.find(EntityTestQuestions.class, etqId);
				if(etq.getStatus()==ICommonData.STATUS_NO_ANSWER) {
					ITestQuestionHandler testQuestionHandler = SingleTestQuestionHandlerFactory.getInstance(etq);	
					testQuestionHandler.setPersonAnswer(jsn, etqId);				
				}	
			} catch (JSONException e) {
				
			}
							
			
			return etqId;
		
	}

	@Override
	public String getUserInformation(String arg0) {
		
		return null;
	}
		
	
	
	
	
	
}
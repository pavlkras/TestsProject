package tel_ran.tests.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import javax.persistence.Query;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tel_ran.tests.entitys.EntityCompany;
import tel_ran.tests.entitys.EntityPerson;
import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.entitys.EntityTest;
import tel_ran.tests.entitys.EntityTestQuestions;
import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.common.IPublicStrings;
import tel_ran.tests.services.fields.Role;
import tel_ran.tests.services.interfaces.ICompanyActionsService;
import tel_ran.tests.services.subtype_handlers.ITestQuestionHandler;
import tel_ran.tests.services.subtype_handlers.SingleTestQuestionHandlerFactory;
import tel_ran.tests.services.testhandler.CompanyTestHandler;
import tel_ran.tests.services.testhandler.ICompanyTestHandler;
import tel_ran.tests.services.testhandler.IPersonTestHandler;
import tel_ran.tests.services.testhandler.PersonTestHandler;
import tel_ran.tests.services.utils.FileManagerService;
import tel_ran.tests.services.utils.UtilsStatic;
import tel_ran.tests.token_cipher.TokenProcessor;
import tel_ran.tests.token_cipher.User;

public class CompanyActionsService extends CommonAdminServices implements ICompanyActionsService {
	
	private EntityCompany entityCompany;
	@Autowired
	private TokenProcessor tokenProcessor;
	
	public static final String LOG = CompanyActionsService.class.getSimpleName();
	
	long id=-1;
	
	
	//-------------Override super class methods ----------- // BEGIN ////
	
	@Override
	protected boolean ifAllowed(EntityQuestionAttributes eqa) {
		
		if(eqa.getEntityCompany().equals(entityCompany))
			return true;
		
		return false;
	}
	
	@Override
	protected EntityCompany getCompany() {	
		if((this.entityCompany==null) || (this.entityCompany.getId()!=this.id)) {
		
//				System.out.println(LOG + "81 - BUT I HAVE ID = " + this.entityCompany.getId());
			if(this.id>=0)
				this.entityCompany = em.find(EntityCompany.class, id);
			else
				this.entityCompany = null;
		}
		System.out.println(LOG + " 82 - my companyId = " + id + " =? " + entityCompany.getId());
		return this.entityCompany;
	}
	
	@Override
	protected EntityCompany renewCompany() {
		EntityCompany result = em.find(EntityCompany.class, id);
		return result;
	}
	
	
	@Override
	protected String getLimitsForQuery() {	
		EntityCompany ec = getCompany();
		return "companyId='" + ec.getId() + "'";
	}
	



	//------------- 	Use case Ordering Test 3.1.3 -------------/// END  ////	
	
	

	//------------- Viewing test results  3.1.4.----------- //   BEGIN    ///
	
	
	// LISTS OF TESTS ------------------ 
	
	@Override
	public String getTestsResultsAll(long companyId, String timeZone) {		
		this.id = companyId;
		String res = "";
		EntityCompany company = getCompany();
		if(company!=null){
			
			@SuppressWarnings("unchecked")
			List<EntityTest> tests = (List<EntityTest>) em.createQuery
				("SELECT t FROM EntityTest t WHERE t.endTestDate!=0 AND t.entityCompany = :company ORDER BY t.entityPerson")
				.setParameter("company", company)
				.getResultList();
			
			res = generateJsonResponseCommon(tests, timeZone);
		}
		return res;
	}

	@Override
	public String getTestsResultsForPersonID(long companyId, int personID, String timeZone) {
		String res = "";
		EntityCompany company = em.find(EntityCompany.class, companyId);
		EntityPerson person = em.find(EntityPerson.class, personID);
		if(company!=null && person != null){
			@SuppressWarnings("unchecked")
			List<EntityTest> tests = (List<EntityTest>) em.createQuery
				("SELECT t FROM EntityTest t WHERE t.isPassed=true AND t.entityPerson = :person AND t.entityCompany = :company")
				.setParameter("person", person)
				.setParameter("company", company)
				.getResultList();
			res = generateJsonResponseCommon(tests, timeZone);
		}
		return res;
	}

	@Override
	public String getTestsResultsForTimeInterval(long companyId, long date_from, long date_until, String timeZone) {
		String res = "[]";
		EntityCompany company = em.find(EntityCompany.class, companyId);
		if(company!=null){
			@SuppressWarnings("unchecked")
			List<EntityTest> tests = (List<EntityTest>) em.createQuery
				("SELECT t FROM EntityTest t WHERE t.isPassed=true AND t.startTestDate >= :date_from AND t.startTestDate <= :date_until AND t.entityCompany = :company ORDER BY t.entityPerson")
				.setParameter("date_from", date_from)
				.setParameter("date_until", date_until)
				.setParameter("company", company)
				.getResultList();
			res = generateJsonResponseCommon(tests, timeZone);
		}
		return res;
	}
	
	
	
	// LISTS OF QUESTIONS  ------------------ //
	

	/**
	 * Method for ViewResults - get details of test. Should return:
	 * - duration (JSN = "duration")
	 * - amount of question (JSN = "amountOfQuestions")
	 * - amount of correct answers (JSN = "amountOfCorrectAnswers")
	 * - amount of incorrect answers (JSN = "incorrect")
	 * - amount of unchecked answers (JSN = "unchecked")
	 * - percent of correct answers - String! (JSN = "persentOfRightAnswers")
	 * - photo-pictures (JSN = "pictures")
	 * - list of questions (JSN = "questions") with:
	 * ----- index in list (JSN = "index") 
	 * ----- ID (EntityTestQuestions) (JSN = "questionId")
	 * ----- metaCategory (JSN = "metaCategory")
	 * ----- category1 (JSN = "category")
	 * ----- status (correct,incorrect,unchecked) in two options:
	 * ----------- as number (= adress for IPublicStrings.QUESTION_STATUS[])
	 * ----------- as String (from IPublicStrings.QUESTION_STATUS[])
	 * 
	 * @param companyId - id of company (by EntityCompany, it needs to get files of pictures) 
	 * @param testId - id of test (by EntityTest)
	 * @return JSON. Keys of JSON - see in ICommonData, JSN_TESTDETAILS_
	 */
	@Override
	public String getTestResultDetails(long companyId, long testId) {
		String res = "{}";
		EntityCompany company = em.find(EntityCompany.class, companyId);
		if(company!=null){
			EntityTest test = (EntityTest) em.createQuery
				("SELECT t FROM EntityTest t WHERE t.testId = :testId AND t.entityCompany = :company")
				.setParameter("testId", testId)
				.setParameter("company", company)
				.getSingleResult();
			
			if(test!=null) {
				
				JSONObject jsonObj = null;
				
				// get duration in String
				long start = test.getStartTestDate();
				long end = test.getEndTestDate();												
				String durationStr = UtilsStatic.getDuration(start, end);		
								
				try {
					
					// numbers of CORRECT ANSWERS, INCORRECT, UNCHECKED answers 
					// PERCENT of Correct
					// list of questions (with id, categories, status)
					// index
					jsonObj = this.checkStatusAndGetJson(test.getId());
									
					// pictures
					
					List <String> images_ = FileManagerService.getImage(companyId, testId);
					if(images_!=null) {						
						JSONArray imagArray = new JSONArray(images_);
						jsonObj.put(ICommonData.JSN_TESTDETAILS_PHOTO, imagArray);
					} else {
//						System.out.println(LOG + " - 229-M: getTestResultDetails - NO IMAGES");
					}					
					// duration
					jsonObj.put("duration", durationStr);					
					// amount of questions
					int numberOfQuestions = test.getAmountOfQuestions();
					jsonObj.put(ICommonData.JSN_TESTDETAILS_QUESTIONS_NUMBER, numberOfQuestions);
					
//					JSONArray questions = new JSONArray();
//					for(int i=0; i<numberOfQuestions; i++){
//						JSONObject singleQuestion = new JSONObject();
//						singleQuestion.put("index", i);
//						singleQuestion.put("status", getTestResultsHandler(companyId, testId).getStatus(i));
//						questions.put(singleQuestion);
//					}
//					jsonObj.put("questions", questions);
						
//					jsonObj = this.getStatusOfTest(test.getTestId());

				} catch (JSONException e) {
					e.printStackTrace();					
				}
				
				res = jsonObj.toString();
//				System.out.println(LOG + " - 247-M: getTestResultdetails, result " + res);
			}
		}
		return res;
	}
	
	/**
	 * Method for ViewResults - get list of UNCHECKED questions. Should return:	 	 
	 * - amount of unchecked answers (JSN = "unchecked")	 	 
	 * - list of questions (JSN = "questions") with:
	 * ----- index in list (JSN = "index") 
	 * ----- ID (EntityTestQuestions) (JSN = "questionId")
	 * ----- metaCategory (JSN = "metaCategory")
	 * ----- category1 (JSN = "category")
	 * ----- status (unchecked) in two options:
	 * ----------- as number (= adress for IPublicStrings.QUESTION_STATUS[])
	 * ----------- as String (from IPublicStrings.QUESTION_STATUS[])
	 * 
	 * @param companyId - id of company (by EntityCompany, it needs to get files of pictures) 
	 * @param testId - id of test (by EntityTest)
	 * @return JSON. Keys of JSON - see in ICommonData, JSN_TESTDETAILS_
	 */ 
	@Override
	public String getListOfUncheckedQuestions(long companyId, long testId) {		
		String res = "{}";
		entityCompany = em.find(EntityCompany.class, companyId);
		if(entityCompany!=null){
			EntityTest test = (EntityTest) em.createQuery
				("SELECT t FROM EntityTest t WHERE t.testId = :testId AND t.entityCompany = :company")
				.setParameter("testId", testId)
				.setParameter("company", entityCompany)
				.getSingleResult();
			
			if(test!=null) {
				
				JSONObject jsonObj = null;
				
				try {
					jsonObj = this.getListOfUncheckedQuestions(test.getId());
					res = jsonObj.toString();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		return res;
	}
	
	
	// CHECK ANSWER OF PERSON
	@Override	
	public String checkAnswer(long companyId, String mark) {
		String response = "";
		entityCompany = em.find(EntityCompany.class, companyId);
		if(entityCompany!=null) {
			try {
				JSONObject jsn = new JSONObject(mark);
				String res = jsn.getString(ICommonData.JSN_CHECK_MARK);
				long testQuestionId = jsn.getLong(ICommonData.JSN_CHECK_ID);
				EntityTestQuestions etq = em.find(EntityTestQuestions.class, testQuestionId);
				if(etq!=null && etq.getStatus()==ICommonData.STATUS_UNCHECKED) {
					ITestQuestionHandler handler = SingleTestQuestionHandlerFactory.getInstance(etq);
					int newStatus = handler.setMark(res);
					response = IPublicStrings.QUESTION_STATUS[newStatus];
					ICompanyTestHandler testHandler = SingleTestQuestionHandlerFactory.getTestCompanyHandler();
					long testId = etq.getEntityTest().getId();
//					System.out.println(testId);
					testHandler.setTestId(testId);
					testHandler.renewStatusOfTest();
//					this.renewStatusOfTest(etq.getEntityTest().getTestId());
				}
							
			} catch (JSONException e) {
				e.printStackTrace();			
			}
		}
		
		return response;
	}
	
			
		//------------- Viewing test results  3.1.4.----------- // END ////	
		
		

	@Override
	public String encodeIntoToken(long companyId) {
		//encodes current timestamp and companyId into token
		String token = tokenProcessor.encodeIntoToken(companyId, ICommonData.TOKEN_VALID_IN_SECONDS);
		return token;
	}	
	
	//PRIVATE
	// JSON for Lists of test
	private String generateJsonResponseCommon(List<EntityTest> testresults, String timeZone) {		
		JSONArray result = new JSONArray();
		for (EntityTest test: testresults){
			
			
			JSONObject jsonObj = new JSONObject();
			try {
				jsonObj.put(ICommonData.JSN_TEST_IS_CHECKED, test.isChecked());
				jsonObj.put("personName",test.getEntityPerson().getPersonName());
				jsonObj.put("personSurname",test.getEntityPerson().getPersonSurname());
				jsonObj.put("testid",test.getId());
				SimpleDateFormat sdf = new SimpleDateFormat(ICommonData.DATE_FORMAT);
				sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
				jsonObj.put("testDate", sdf.format(new Date(test.getStartTestDate())));
				String prc = null;
				if(test.isChecked()) {
					prc = Float.toString(Math.round((float)test.getAmountOfCorrectAnswers()/(float)test.getAmountOfQuestions()*100));
				} else {
					prc = "unchecked";
				}
				jsonObj.put("persentOfRightAnswers", prc);				
				result.put(jsonObj);
			} catch (JSONException e) {}
		}
		return result.toString();
	}
	
	// PRIVATE METHODS FOR TEST CREATION ---------------------- // BEGIN -----------

	

		

	
	// ------------------------- creating TEST ---------------- // END

	
	// ------------------------- info about TESTS and COMPANY -------------- // BEGIN
	
	// info about COMPANY


	@Override
	public List<String> listOfCreatedTest() {
		Query qry = em.createQuery("SELECT et FROM EntityTest et WHERE et.entityCompany=?1");
		qry.setParameter(1, entityCompany);
		List<EntityTest> list = qry.getResultList();
		List<String> resultList = null;
		if(list!=null) {
		
		resultList = new ArrayList<String>();
		
		for(EntityTest et : list) {
			try {
				resultList.add(getTestJson(et));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
		return resultList;
	}
	

	
	private String getTestJson(EntityTest et) throws JSONException {
		String result = null;
		JSONObject jsn = new JSONObject();
		if(et!=null) {
			jsn.put("id", et.getId());
			jsn.put("question_num", et.getAmountOfQuestions());
			jsn.put("passed", et.isPassed());
			jsn.put("pers_surname", et.getEntityPerson().getPersonSurname());
			jsn.put("pers_name", et.getEntityPerson().getPersonName());
			jsn.put("pers_id", et.getEntityPerson().getPersonId());		
			result = jsn.toString();
		}
		
		return result;
	}

	// RETURN JSON for QUESTION DETAIL (with Person's answer) by test-question Id and company Id
	// Fields in the JSON:
	// 
	// QUESTION IN TEST
	// -- test-question Id ("questionId") - long (id from EntityTestQuestion)
	// -- index ("index") - int = index of the question in this test
	// -- status (correct, incorrect, unchecked) may be in two forms:
	// ---- in numbers ("statusNumber") - int - corresponds with IPublicStrings.QUESTION_STATUS[]
	// ---- in Strings ("statusString") - String = values from this array
	//
	// QUESTION INFO
	// -- type to display question ("type") - 1=auto, 2=code, 3=american, 4=open. See ICommonData TYPES FOR DISPLAY QUESTIONS
	// -- metaCategory ("metaCategory") = String
	// -- category1 ("category") = String
	// -- short text of the question ("text") = String (main question = title of the question) 
	// -- description, long text ("description") = Array; "line" - String for one line 	// for american and open questions
	// -- image ("image") = String in base64												// for all questions except Programming task
	// -- code stub ("code") = Array of Strings, each String = one line  					// ONLY for programming task
	// -- answer's options for American Test ("options") = Array, that includes fields:  	// ONLY for american tests
	// ----- text of the option ("answerOption") = String 
	// ----- letter of the option ("letter") = String (A, B, C or D...) 
	// -- letters for answer options ("letters") = array of String (A, B, C, D, E..) 		// for auto and american tests
	//
	// ANSWER
	// -- answer of the Person ("answer") = Array of String; "line" - String for one line 
	//
	// INFO FOR GRADE (CHECK) - only for unchecked questions
	// -- type of grade ("gradeType") = int: 0 - correct/incorrect, 1 - 1/2/3/4/5, 2 - percent (from 0% to 100%)
	// -- grade options ("gradeOptions") = array of String 								      // for 0 and 1 types	
	@Override
	@Transactional
	public String getQuestionDetails(long companyId, long testQuestionId) {
		String res = "{}";
		entityCompany = em.find(EntityCompany.class, companyId);
		if(entityCompany!=null) {
			EntityTestQuestions entityTestQuestion = em.find(EntityTestQuestions.class, testQuestionId);
			if(entityTestQuestion!=null && entityTestQuestion.getEntityTest().getEntityCompany().equals(entityCompany)) {
				
				JSONObject jsonObject = null;
				
				try {
					EntityQuestionAttributes entityQuestionAttributes = entityTestQuestion.getEntityQuestionAttributes();
					ITestQuestionHandler handler = SingleTestQuestionHandlerFactory.getInstance(entityTestQuestion);
					jsonObject = handler.getJsonWithCorrectAnswer(entityTestQuestion);
														
					jsonObject.put(ICommonData.JSN_QUESTDET_INDEX, this.getIndexForTestQuestion(testQuestionId, false));
									
					res = jsonObject.toString();
					
				} catch (JSONException e) {
					
					e.printStackTrace();
				}						
				
				
			}		
		}
		return res;
	}

	
	@Override
	public Map<String, List<String>> getCompanyCustomCategories(String token) {
		Map<String, List<String>> result = null;
		User user = tokenProcessor.decodeRoleToken(token);
		int id = (int)user.getId();
		if(user.isAutorized() && user.getRole().equals(Role.COMPANY)) {
			List<String> categories1 = testQuestsionsData.getCategories(id, 1, null, -1);
			
			if(categories1.size()>0){
				result = new LinkedHashMap<String, List<String>>();
				
				for(String str : categories1) {
					List<String> subs = testQuestsionsData.getCategories(id, 2, str, 1);
					if(subs == null) {
						subs = new ArrayList<String>();
					}
					result.put(str, subs);
				}							
			}			
		}			
		return result;
	}




				


	// ------------------- PRIVATE METHODS FOR TEST CREATION ---------------------- // END -----------

}

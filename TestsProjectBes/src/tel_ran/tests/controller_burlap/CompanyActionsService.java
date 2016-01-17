package tel_ran.tests.controller_burlap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.persistence.Query;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tel_ran.tests.entitys.Company;
import tel_ran.tests.entitys.Person;
import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.entitys.Test;
import tel_ran.tests.entitys.InTestQuestion;
import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.common.IPublicStrings;
import tel_ran.tests.services.fields.Role;
import tel_ran.tests.services.interfaces.ICompanyActionsService;
import tel_ran.tests.services.subtype_handlers.ITestQuestionHandler;
import tel_ran.tests.services.subtype_handlers.SingleTestQuestionHandlerFactory;
import tel_ran.tests.services.utils.FileManagerService;
import tel_ran.tests.services.utils.UtilsStatic;
import tel_ran.tests.token_cipher.TokenProcessor;
import tel_ran.tests.token_cipher.User;

public class CompanyActionsService extends CommonAdminServices implements ICompanyActionsService {
	
	private Company entityCompany;
	@Autowired
	private TokenProcessor tokenProcessor;
	
	public static final String LOG = CompanyActionsService.class.getSimpleName();
	
	int id=-1;
	
	
	//-------------Override super class methods ----------- // BEGIN ////
	
	@Override
	protected boolean ifAllowed(EntityQuestionAttributes eqa) {
		
		if(eqa.getEntityCompany().equals(entityCompany))
			return true;
		
		return false;
	}
	
	@Override
	protected Company getCompany() {	
		if((this.entityCompany==null) || (this.entityCompany.getId()!=this.id)) {
		
//				System.out.println(LOG + "81 - BUT I HAVE ID = " + this.entityCompany.getId());
			if(this.id>=0)
				this.entityCompany = em.find(Company.class, id);
			else
				this.entityCompany = null;
		}
		System.out.println(LOG + " 82 - my companyId = " + id + " =? " + entityCompany.getId());
		return this.entityCompany;
	}
	
	@Override
	protected Company renewCompany() {
		Company result = em.find(Company.class, id);
		return result;
	}
	
	
	@Override
	protected String getLimitsForQuery() {	
		Company ec = getCompany();
		return "companyId='" + ec.getId() + "'";
	}
	



	//------------- 	Use case Ordering Test 3.1.3 -------------/// END  ////	
	
	

	//------------- Viewing test results  3.1.4.----------- //   BEGIN    ///
	
	
	// LISTS OF TESTS ------------------ 
	
	@Override
	public String getTestsResultsAll(int companyId, String timeZone) {		
		this.id = companyId;
		String res = "";
		Company company = getCompany();
		if(company!=null){
			
			@SuppressWarnings("unchecked")
			String queryText = "SELECT t FROM Test t WHERE t.endTestDate!=0 AND t.company = :company ORDER BY t.person";
			Query q = em.createQuery(queryText);
			q.setParameter("company", company);
			List<Test> tests = (List<Test>) q.getResultList();
			
			res = generateJsonResponseCommon(tests, timeZone);
		}
		return res;
	}

	@Override
	public String getTestsResultsForPersonID(int companyId, int personID, String timeZone) {
		String res = "";
		Company company = em.find(Company.class, companyId);
		Person person = em.find(Person.class, personID);
		if(company!=null && person != null){
			@SuppressWarnings("unchecked")
			List<Test> tests = (List<Test>) em.createQuery
				("SELECT t FROM Test t WHERE t.isPassed=true AND t.person = :person AND t.company = :company")
				.setParameter("person", person)
				.setParameter("company", company)
				.getResultList();
			res = generateJsonResponseCommon(tests, timeZone);
		}
		return res;
	}

	@Override
	public String getTestsResultsForTimeInterval(int companyId, long date_from, long date_until, String timeZone) {
		String res = "[]";
		Company company = em.find(Company.class, companyId);
		if(company!=null){
			@SuppressWarnings("unchecked")
			List<Test> tests = (List<Test>) em.createQuery
				("SELECT t FROM Test t WHERE t.isPassed=true AND t.startTestDate >= :date_from AND t.startTestDate <= :date_until AND t.company = :company ORDER BY t.person")
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
	 * ----- ID (InTestQuestio) (JSN = "questionId")
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
	public String getTestResultDetails(int companyId, long testId) {
		String res = "{}";
		Company company = em.find(Company.class, companyId);
		if(company!=null){
			Test test = em.find(Test.class, testId);
			
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
	 * Return JSONObject with list of questions and some info about all test.	 
	 * @param testId
	 * @param fullList
	 * @return
	 * @throws JSONException
	 */
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public JSONObject checkStatusAndGetJson(long testId) throws JSONException {
		int correct = 0;
		int inCorrect = 0;
		int unAnswered = 0;
		int unChecked = 0;
				
		Test test = em.find(Test.class, testId);
		
		String query = "SELECT c from InTestQuestion c WHERE c.test=?1";
		
		JSONArray jsnArray = new JSONArray();
		
		@SuppressWarnings("unchecked")
		List<InTestQuestion> list = em.createQuery(query).setParameter(1, test).getResultList();			
				
		int index = 0;
		for(InTestQuestion etq : list) {			
			
				JSONObject jsnQuest = new JSONObject();
				int status = this.getObjectForShortQuestionInfo(etq, jsnQuest);
				jsnQuest.put(ICommonData.JSN_TESTDETAILS_QUESTION_INDEX, index++);			
				jsnArray.put(jsnQuest);
			
			
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
				break;
			default:
//				System.out.println(LOG + " -315-M: getStatusOfTest - incorrect status = " + status);
			}
		}
		
		boolean testIsPassed;		
						
		if(unAnswered==0) {			
			testIsPassed = true;
			if(!test.isPassed()) {				
				test.setPassed(true);						
			}
		} else {
			testIsPassed = false;
		}
		
		if(unChecked==0 && testIsPassed) {			
			if(!test.isChecked()) {
				test.setChecked(true);				
			}
		} 
						
		if(test.getAmountOfCorrectAnswers()!=correct) {
			test.setAmountOfCorrectAnswers(correct);			
		}		
		
		em.merge(test);
		
		int allQuestions = test.getAmountOfQuestions();
			
		JSONObject result = new JSONObject();
		
		result.put(ICommonData.JSN_TESTDETAILS_LIST_OF_QUESTIONS, jsnArray);
		result.put(ICommonData.JSN_TESTDETAILS_CORRECT_QUESTIONS_NUMBER, correct);
		result.put(ICommonData.JSN_TESTDETAILS_INCORRECT_ANSWERS_NUMBER, inCorrect);		
		result.put(ICommonData.JSN_TESTDETAILS_UNCHECKED_QUESTIONS_NUMBER, unChecked);
		
		float percent;
		int checked = allQuestions - unAnswered - unChecked;
		if(checked==0) 
			percent = 0;
		else
			percent = Math.round((float)correct/(float)checked)*100;
		String resPercent = Float.toHexString(percent) + "%";
		
		if(unChecked==0 && unAnswered==0) {
			result.put(ICommonData.JSN_TEST_PERCENT_OF_CORRECT, resPercent); // Add calculations from the resultTestCodeFromPerson field
		} else if (unChecked==0){
			result.put(ICommonData.JSN_TEST_PERCENT_OF_CORRECT, "not answered (" + resPercent + " from " + checked +")");
		} else {
			result.put(ICommonData.JSN_TEST_PERCENT_OF_CORRECT, "not checked (" + resPercent + " from " + checked + ")");
		}
		
		return result;
		
	}
	
	/**
	 * Fill JSONObject with short information about question in the test. It includes:
	 * -- test-question ID
	 * -- status (in num and String)
	 * -- metaCategory
	 * -- category
	 * @param etq
	 * @param jsnQuest
	 * @param index
	 * @return
	 * @throws JSONException
	 */
	private int getObjectForShortQuestionInfo (InTestQuestion etq, JSONObject jsnQuest) throws JSONException {
		int status = etq.getStatus();
		
		//set ID and index
		jsnQuest.put(ICommonData.JSN_TESTDETAILS_QUESTION_ID, etq.getId());		
	
		//set STATUS	
		jsnQuest.put(ICommonData.JSN_TESTDETAILS_QUESTION_STATUS_NUM, status);
		jsnQuest.put(ICommonData.JSN_TESTDETAILS_QUESTION_STATUS_STR, IPublicStrings.QUESTION_STATUS[status]);
	
		//set category-metacategory
		jsnQuest.put(ICommonData.JSN_TESTDETAILS_QUESTION_METACATEGORY, etq.getQuestion().getCategory().getTopCategory());
		jsnQuest.put(ICommonData.JSN_TESTDETAILS_QUESTION_CATEGORY1, etq.getQuestion().getCategory().getSecondCategory());
		
		return status;
	}
	
	
	/**
	 * Return JSONObject with list of questions and some info about all test.
	 * JSON includes fields:
	 * -- "questions" = list of questions (array) with fields:
	 * ------ test-question ID
	 * ------ status (in num and String)
	 * ------ metaCategory
	 * ------ category
	 * ------ index in list
	 * -- number of unchecked questions
	 * @param testId
	 * @param fullList
	 * @return
	 * @throws JSONException
	 */
	@Transactional
	public JSONObject getListOfUncheckedQuestions(long testId) throws JSONException {
		Test test = em.find(Test.class, testId);
		String query = "SELECT c from InTestQuestion c WHERE c.test=?1 AND c.status=?2";
		@SuppressWarnings("unchecked")
		List<InTestQuestion> list = em.createQuery(query).setParameter(1, test).setParameter(2, ICommonData.STATUS_UNCHECKED)
				.getResultList();
		JSONObject result = new JSONObject();
		int num = 0;
		JSONArray array = new JSONArray();
		if(list!=null) {			
			for (InTestQuestion etq : list) {
				JSONObject jsn = new JSONObject();
				getObjectForShortQuestionInfo(etq, jsn);
				num++;
				jsn.put(ICommonData.JSN_TESTDETAILS_QUESTION_INDEX, num);
				array.put(jsn);
			}
			
		}
		result.put(ICommonData.JSN_TESTDETAILS_LIST_OF_QUESTIONS, array);
		result.put(ICommonData.JSN_TESTDETAILS_UNCHECKED_QUESTIONS_NUMBER, num);
		return result;
	}
	
	/**
	 * Method for ViewResults - get list of UNCHECKED questions. Should return:	 	 
	 * - amount of unchecked answers (JSN = "unchecked")	 	 
	 * - list of questions (JSN = "questions") with:
	 * ----- index in list (JSN = "index") 
	 * ----- ID (InTestQuestion) (JSN = "questionId")
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
	public String getListOfUncheckedQuestions(int companyId, long testId) {		
		String res = "{}";
		entityCompany = em.find(Company.class, companyId);
		if(entityCompany!=null){
			Test test = (Test) em.createQuery
				("SELECT t FROM Test t WHERE t.testId = :testId AND t.company = :company")
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
	
			
		//------------- Viewing test results  3.1.4.----------- // END ////	
		
		

	@Override
	public String encodeIntoToken(int companyId) {
		//encodes current timestamp and companyId into token
		String token = tokenProcessor.encodeIntoToken(companyId, ICommonData.TOKEN_VALID_IN_SECONDS);
		return token;
	}	
	
	//PRIVATE
	// JSON for Lists of test
	private String generateJsonResponseCommon(List<Test> testresults, String timeZone) {		
		JSONArray result = new JSONArray();
		for (Test test: testresults){
			
			
			JSONObject jsonObj = new JSONObject();
			try {
				jsonObj.put(ICommonData.JSN_TEST_IS_CHECKED, test.isChecked());
				jsonObj.put("personName",test.getPerson().getPersonName());
				jsonObj.put("personSurname",test.getPerson().getPersonSurname());
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
		Query qry = em.createQuery("SELECT et FROM Test et WHERE et.company=?1");
		qry.setParameter(1, entityCompany);
		List<Test> list = qry.getResultList();
		List<String> resultList = null;
		if(list!=null) {
		
		resultList = new ArrayList<String>();
		
		for(Test et : list) {
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
	

	
	private String getTestJson(Test et) throws JSONException {
		String result = null;
		JSONObject jsn = new JSONObject();
		if(et!=null) {
			jsn.put("id", et.getId());
			jsn.put("question_num", et.getAmountOfQuestions());
			jsn.put("passed", et.isPassed());
			jsn.put("pers_surname", et.getPerson().getPersonSurname());
			jsn.put("pers_name", et.getPerson().getPersonName());
			jsn.put("pers_id", et.getPerson().getPersonId());		
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
	public String getQuestionDetails(int companyId, long testQuestionId) {
		String res = "{}";
		entityCompany = em.find(Company.class, companyId);
		if(entityCompany!=null) {
			InTestQuestion entityTestQuestion = em.find(InTestQuestion.class, testQuestionId);
			if(entityTestQuestion!=null && entityTestQuestion.getTest().getCompany().equals(entityCompany)) {
				
				JSONObject jsonObject = null;
				
				try {
//					EntityQuestionAttributes entityQuestionAttributes = entityTestQuestion.getEntityQuestionAttributes();
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

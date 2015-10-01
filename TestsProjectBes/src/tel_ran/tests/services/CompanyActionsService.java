package tel_ran.tests.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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

public class CompanyActionsService extends CommonAdminServices implements ICompanyActionsService {
	
	private EntityCompany entityCompany;
	@Autowired
	private TokenProcessor tokenProcessor;
	
	public static final String LOG = CompanyActionsService.class.getSimpleName();
	
	long id=-1;
	
	//-------------Use Case Company Login 3.1.1----------- //   BEGIN    ///
	@Override
	public boolean setAutorization(String username, String password) { 
		boolean result = false;
		entityCompany = (EntityCompany) em.createQuery("Select c from EntityCompany c where c.C_Name='" + username + "'").getSingleResult();
		
		if(entityCompany != null){
			if( entityCompany.getPassword().equals(password)){
				result = true;
			}else{
				result = false;
			}			
		}
		this.id = entityCompany.getId();
		return result;
	}
	//-------------Use Case Company Login 3.1.1----------- //   END    ///
	
	//-------------Override super class methods ----------- // BEGIN ////
	
	@Override
	protected boolean ifAllowed(EntityQuestionAttributes eqa) {
		
		if(eqa.getCompanyId().equals(entityCompany))
			return true;
		
		return false;
	}
	
	@Override
	protected EntityCompany getCompany() {	
		if((this.entityCompany==null) || (this.entityCompany.getId()!=this.id)) {
			System.out.println(LOG + "81 - BUT I HAVE ID = " + this.entityCompany.getId());
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
	

	
	//------------- 	Use case Ordering Test 3.1.3 -------------/// BEGIN ////
	
		
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public int createPerson(int personId,String personName, String personSurname, String personEmail) {

		EntityPerson person = this.createEntityPerson(personId, personName, personSurname, personEmail);
		
		if(person==null)
			return 0;

		return person.getPersonId();
	}
	

	//------------- 	Use case Ordering Test 3.1.3 -------------/// END  ////	
	
	

	//------------- Viewing test results  3.1.4.----------- //   BEGIN    ///
	
	
	// LISTS OF TESTS ------------------ 
	
	@Override
	public String getTestsResultsAll(long companyId, String timeZone) {		
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
					jsonObj = this.checkStatusAndGetJson(test.getTestId());
									
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
					jsonObj = this.getListOfUncheckedQuestions(test.getTestId());
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
					long testId = etq.getEntityTest().getTestId();
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
				jsonObj.put("testid",test.getTestId());
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
	
	// Person handler	
//		private IPersonTestHandler getTestResultsHandler(long companyId, long testId){
//			return new PersonTestHandler(companyId, testId, em);
//		}
//		
	
		
		
	//------------- Viewing test results  3.1.4.----------- // END ////	
	
	
	// ------------ Creating tests -------------------------// BEGIN ////
		
		
	// old version
//	@Override
//	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
//	public int createTestForPersonFull(String metaCategories, String categories1, String difLevel, String nQuestion, int personPassport,
//			String personName, String personSurname, String personEmail, String pass) {		
//		return this.createTestForPersonFullWithQuestions(null, metaCategories, categories1, difLevel, nQuestion, 
//				personPassport, personName, personSurname, personEmail, pass);
//	}

		
		
	// gate for create test	with creation person
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public int createTestForPersonFullWithQuestions(List<Long> questionIdList, String metaCategories, String categories1, String difLevel, String nQuestion, int personPassport,
			String personName, String personSurname, String personEmail, String pass) {
		
		EntityPerson ePerson = this.createEntityPerson(personPassport, personName, personSurname, personEmail);
		if(ePerson==null)
			return 2;
		
		return this.testFromQuestionList(questionIdList, ePerson, pass, metaCategories, categories1, difLevel, nQuestion);

	}
		

	@Override
	public int createSetQuestiosnAndTest(String metaCategory, String category1, String level_num, String nQuestion, 
			int personId, String pass) {
		
		EntityPerson temp = em.find(EntityPerson.class, personId);	
		
		return testFromQuestionList(null, temp, pass, metaCategory, category1, level_num, nQuestion);
	}

	@Override  
	public List<Long> createSetQuestions(String metaCategory, String categories1, String levelsOfDifficulty, int nQuestion) 
		{				
			List<Long> result = null; 			
			return createSetQuestions(metaCategory, categories1, levelsOfDifficulty, nQuestion, result);
		}


	// gate for create test	for given Person 
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public int createTestFromQuestionList(List<Long> questionIdList, int personId, String pass, String metaCategories, String categories1, 
			String complexityLevel, String nQuestions) {		
						
		EntityPerson temp = em.find(EntityPerson.class, personId);		
		return this.testFromQuestionList(questionIdList, temp, pass, metaCategories, categories1, complexityLevel, nQuestions);
	}

	
	// PRIVATE METHODS FOR TEST CREATION ---------------------- // BEGIN -----------

	@SuppressWarnings("unchecked")
	private List<Long> createSetQuestions(String metaCategory, String categories1, String levelsOfDifficulty, int nQuestion, 
			List<Long> preparedList) {		
		
		if(nQuestion > 0 && metaCategory != null && levelsOfDifficulty!=null){
			int size = 0;
			int newSize = 0;
			HashSet<Long> allQuestId = new HashSet<Long>();
			if(preparedList!=null) {				 
				size = preparedList.size(); 				
				allQuestId.addAll(preparedList);
				newSize = allQuestId.size();
				if(size > newSize) 
					nQuestion += newSize = size;
			}
			
			String[] categoryArray = metaCategory.split(",");	
//			System.out.println("Category: " + Arrays.toString(categoryArray));
			
			String[] levelsArray = levelsOfDifficulty.split(",");
//			System.out.println("Levels: " + Arrays.toString(levelsArray));
			String[] categories1Array;
			if(categories1!=null) {
				categories1Array = categories1.split(",");
			} else {
				categories1Array = new String[categoryArray.length];
				Arrays.fill(categories1Array, ICommonData.NO_CATEGORY1);
			}
//			System.out.println("Categories1: " + Arrays.toString(categories1Array));
			
			StringBuilder condition;
			Query query;
			EntityCompany ec = getCompany();
			List<Long> allAttributeQuestionsId;
						
			int typeNumbers = categoryArray.length;			
//			System.out.println("Number of categories " + typeNumbers);
			long step = nQuestion/typeNumbers;
//			System.out.println("Step " + step);
			long r = nQuestion % typeNumbers;
//			System.out.println("Rest " + r);
			long nGeneratedQuestion = 0L;
			int count = typeNumbers;
			
			
			for (int i = 0; i < typeNumbers; i++ ) {
				
				condition = new StringBuilder("SELECT c.id FROM EntityQuestionAttributes c WHERE ");
				condition.append("c.metaCategory=?1 AND c.levelOfDifficulty=?2");
												
				if(ec==null) {
					condition.append(" AND c.companyId IS NULL");
				} else {
					condition.append(" AND c.companyId=?3");						
				}
				
				// if category is specified
				if(!categories1Array[i].equals(ICommonData.NO_CATEGORY1)) {
					condition.append(" AND c.category1=?4");
				}
				
				query = em.createQuery(condition.toString());
				
				query.setParameter(1, categoryArray[i]);
									
				query.setParameter(2, Integer.parseInt(levelsArray[i]));	
				
				if(ec!=null) {					
					query.setParameter(3, ec);
				}

				// if category is specified
				if(!categories1Array[i].endsWith(ICommonData.NO_CATEGORY1)) {
					query.setParameter(4, categories1Array[i]);
				}
				
//				System.out.println("Query : " + query.toString());
				
				if(i == typeNumbers-1) 
					step = step +r;
				
				allAttributeQuestionsId = query.getResultList();
				count = count--;
				
				if(allAttributeQuestionsId == null) {
//					System.out.println("Query is empty");
					if(count-i-1>0) {
						step = (nQuestion - nGeneratedQuestion) / (count-i-1);
						r = (nQuestion - nGeneratedQuestion) % (count-i-1);
//						System.out.println("New step = " + step + "; New rest = " + r);
					} 
					
				} else if (allAttributeQuestionsId.size() < step) {
					
					long resultSize = (long) allAttributeQuestionsId.size();
//					System.out.println("Query is too small. There're only " + resultSize + "questions");
					int resPlus = randomAttributeQuestionsId(allAttributeQuestionsId, resultSize, allQuestId);
					nGeneratedQuestion += resultSize;
					
					if(count-i-1>0) {
						step = (nQuestion - nGeneratedQuestion) / (count-i-1);
						r = (nQuestion - nGeneratedQuestion) % (count-i-1);
//						System.out.println("New step = " + step + "; New rest = " + r);
					}
					
				} else {					
					
					int resPlus = randomAttributeQuestionsId(allAttributeQuestionsId, step, allQuestId);
					nGeneratedQuestion += step;
				}			
				
//				System.out.println("Generated questions = " + nGeneratedQuestion);
//				System.out.println("Count = " + count);
//				System.out.println("Index i = " + i);
			}	
			List<Long> result = new ArrayList<Long>();
			result.addAll(allQuestId);
			return result;
		}
		
		return null;
		
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	private int testFromQuestionList(List<Long> questionIdList, EntityPerson ePerson, String pass, String metaCategories, String categories1, 
			String complexityLevel, String nQuestions) {
		int result = -1;
		int numberQuestions = Integer.parseInt(nQuestions);
		int listSize;
		
		if(questionIdList==null) {
			listSize = 0;
			questionIdList = new ArrayList<Long>();
		} else
			listSize = questionIdList.size();		
		
		int numberQuestionsToAutoGenerate = numberQuestions - listSize;
		
		if (numberQuestionsToAutoGenerate > 0) {
			List<Long> autoQuestions = this.createSetQuestions(metaCategories, categories1, complexityLevel, numberQuestionsToAutoGenerate, questionIdList);
			questionIdList = autoQuestions;							
		} 
		
		if(questionIdList.size() < numberQuestions)
			result = 1;
		else {
		
			if(!this.createTest(questionIdList, ePerson, pass) && result!=1)
				result = 1;
			else
				result = 0; 
		}
		
		return result;
		
	}
	
//	private boolean checkCategory1 (String metaCategory, String category1) {
//		boolean result = false;
//		
//		List<String> categories1InDb = this.getCategories1ByMetaCategory(metaCategory);
//		if(categories1InDb.contains(category1))
//			result = true;
//		
//		return result;
//	}
		
	private static int randomAttributeQuestionsId(List<Long> allAttributeQuestionsId, Long nQuestion, 
			HashSet<Long> listOfId){
		
		int startSize = listOfId.size();
		int listSize = allAttributeQuestionsId.size();
				
		if(allAttributeQuestionsId.size() > 0){			
			for(int i=0; i<nQuestion;){	
				Random rnd = new Random();
				int rand =  rnd.nextInt(listSize);							
				long questionAttributeId = allAttributeQuestionsId.get(rand);
				if(listOfId.add(questionAttributeId)) {
					i++;
				} else {
					listSize--;
					if(listSize < nQuestion-i){
						listOfId.addAll(allAttributeQuestionsId);
						break;
					}
				}					
								
			}
		}
		
		return listOfId.size() - startSize;
		
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	private boolean createTest(List<Long> questionIdList, EntityPerson ePerson, String pass) {		
		boolean result = false;
		
		EntityTest test = new EntityTest();		
		test.setPassword(pass); 
		test.setStartTestDate(0L);// setting parameter for wotchig method in FES
		test.setEndTestDate(0L);// setting parameter for wotchig method in FES		
		
		EntityCompany ec = this.getCompany();
		if(ec!=null)
				test.setEntityCompany(entityCompany);
		test.setEntityPerson(ePerson);
		em.persist(test);
		long testId = test.getTestId();
		
		if( questionIdList.size() > 0 ){
			long companyId = entityCompany.getId();
			////  creating folder tree for test
			FileManagerService.initializeTestFileStructure(companyId, testId);
			////
			
			for(Long id : questionIdList) {
				EntityQuestionAttributes eqa = em.find(EntityQuestionAttributes.class, id);
				EntityTestQuestions etq = new EntityTestQuestions();
				etq.setEntityQuestionAttributes(eqa);
				etq.setEntityTest(test);
				etq.setStatus(ICommonData.STATUS_NO_ANSWER);
				em.persist(etq);
				test.addEntityTestQuestions(etq);
			}
			
//			IPersonTestHandler testResultsJsonHandler = new PersonTestHandler(companyId, testId, em);			
//			testResultsJsonHandler.addQuestions(questionIdList);			
			test.setAmountOfQuestions(questionIdList.size());
			test.setPassed(false);
			test.setChecked(false);
			em.merge(test);
			result = true;;
		}
		
		return result;
		
	}
		
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	private EntityPerson createEntityPerson(int personId, String personName, String personSurname, String personEmail) {
		
		if(personEmail==null || personEmail.length()<5)
			return null;
		
			
		EntityPerson person = em.find(EntityPerson.class, personId);
		if(person==null){			
			person = new EntityPerson();
			person.setPersonId(personId);
			person.setPersonName(personName);
			person.setPersonSurname(personSurname);	
			person.setPersonEmail(personEmail);
			em.persist(person);      
			
		} 
		
		return person;
	}
	
	// ------------------------- creating TEST ---------------- // END

	
	// ------------------------- info about TESTS and COMPANY -------------- // BEGIN
	
	// info about COMPANY
	@Override
	public String getUserInformation() {
		String result = null;	
		
		if(entityCompany!=null) {
			JSONObject jsn = new JSONObject();
			try {
				jsn.put(ICommonData.MAP_ACCOUNT_NAME, entityCompany.getC_Name());
				jsn.put(ICommonData.MAP_ACCOUNT_WEB, entityCompany.getC_Site());
				jsn.put(ICommonData.MAP_ACCOUNT_QUESTION_NUMBER, getNumberQuestion());
				jsn.put(ICommonData.MAP_ACCOUNT_TESTS_NUM, getNumberTests());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			result = jsn.toString();
			}
		return result;
	}

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
	
	// number of created and sended tests
	private long getNumberTests() {
		String query = "SELECT COUNT(et) from EntityTest et ";
		query = query.concat(" WHERE et.entityCompany=?1");
		Query newQ = em.createQuery(query);
		newQ.setParameter(1, entityCompany);
		Long result = (Long) newQ.getSingleResult();
		
		return result;
		
	}
	
	
	private String getTestJson(EntityTest et) throws JSONException {
		String result = null;
		JSONObject jsn = new JSONObject();
		if(et!=null) {
			jsn.put("id", et.getTestId());
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




				


	// ------------------- PRIVATE METHODS FOR TEST CREATION ---------------------- // END -----------

}

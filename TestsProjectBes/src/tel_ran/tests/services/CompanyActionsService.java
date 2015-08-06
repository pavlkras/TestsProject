package tel_ran.tests.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

import tel_ran.tests.entitys.EntityAdministrators;
import tel_ran.tests.entitys.EntityCompany;
import tel_ran.tests.entitys.EntityPerson;
import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.entitys.EntityTest;
import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.interfaces.ICompanyActionsService;
import tel_ran.tests.services.interfaces.IFileManagerService;
import tel_ran.tests.services.testhandler.IPersonTestHandler;
import tel_ran.tests.services.testhandler.PersonTestHandler;
import tel_ran.tests.token_cipher.TokenProcessor;

public class CompanyActionsService extends MaintenanceService implements ICompanyActionsService {
	
	private EntityCompany entityCompany;
	@Autowired
	private TokenProcessor tokenProcessor;
	@Autowired
	IFileManagerService fileManager;
	
	long id;
	
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
		return "companyId='" + ec + "'";
	}
	

	//-------------Use Case Company Sign up 3.1.2----------- //   BEGIN    ///



	//-------------Use Case Company Sign up 3.1.2-----------  /// END  ///

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
	@Override
	public String getTestsResultsAll(long companyId, String timeZone) {
		String res = "";
		EntityCompany company = em.find(EntityCompany.class, companyId);
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

	@Override
	public String getTestResultDetails(long companyId, long testId) {
		String res = "{}";
		EntityCompany company = em.find(EntityCompany.class, companyId);
		if(company!=null){
			EntityTest test = (EntityTest) em.createQuery
				("SELECT t FROM EntityTest t WHERE t.isPassed=true AND t.testId = :testId AND t.entityCompany = :company")
				.setParameter("testId", testId)
				.setParameter("company", company)
				.getSingleResult();
//			JSONObject jsonObj = new JSONObject();
//			try {
//				jsonObj.put("duration",duration);
//				jsonObj.put("amountOfQuestions",amountOfQuestions);
//				jsonObj.put("complexityLevel",levelOfDifficulty);
//				jsonObj.put("amountOfCorrectAnswers",amountOfCorrectAnswers);
//				jsonObj.put("persentOfRightAnswers",Math.round((float)amountOfCorrectAnswers/(float)amountOfQuestions*100)); // Add calculations from the resultTestCodeFromPerson field  
//				jsonObj.put("pictures", getJsonArrayImage(pictures));
//				jsonObj.put("codesFromPerson", getJsonArrayCode(testCodeFromPerson, resultTestCodeFromPerson, "java,csharp,cpp,css,"));
//			} catch (JSONException e) {}
//			return jsonObj.toString();
			
//			res = test.getJsonDetails();// TO DO Throws actions NullPointerException !!!!!!!!!!!!!
		}
		return res;
	}

	private String generateJsonResponseCommon(List<EntityTest> testresults, String timeZone) {
		JSONArray result = new JSONArray();
		for (EntityTest test: testresults){
			
			JSONObject jsonObj = new JSONObject();
			try {
				jsonObj.put("personName",test.getEntityPerson().getPersonName());
				jsonObj.put("personSurname",test.getEntityPerson().getPersonSurname());
				jsonObj.put("testid",test.getTestId());
				SimpleDateFormat sdf = new SimpleDateFormat(ICommonData.DATE_FORMAT);
				sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
				jsonObj.put("testDate", sdf.format(new Date(test.getStartTestDate())));
				jsonObj.put("persentOfRightAnswers",Math.round((float)test.getAmountOfCorrectAnswers()/(float)test.getAmountOfQuestions()*100));
				result.put(jsonObj);
			} catch (JSONException e) {}
		}
		return result.toString();
	}
	
//	public String getJsonDetails() {

//}

	@Override
	public String encodeIntoToken(long companyId) {
		//encodes current timestamp and companyId into token
		String token = tokenProcessor.encodeIntoToken(companyId, ICommonData.TOKEN_VALID_IN_SECONDS);
		return token;
	}	
	//------------- Viewing test results  3.1.4.----------- // END ////	




	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public int createTestForPersonFull(String metaCategories, String categories1, String difLevel, String nQuestion, int personPassport,
			String personName, String personSurname, String personEmail, String pass) {		
		return this.createTestForPersonFull(null, metaCategories, categories1, difLevel, nQuestion, 
				personPassport, personName, personSurname, personEmail, pass);
	}

	
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public int createTestForPersonFull(List<Long> questionIdList, String metaCategories, String categories1, String difLevel, String nQuestion, int personPassport,
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
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public int createTestFromQuestionList(List<Long> questionIdList, int personId, String pass, String metaCategories, String categories1, 
			String complexityLevel, String nQuestions) {		
						
		EntityPerson temp = em.find(EntityPerson.class, personId);		
		return this.testFromQuestionList(questionIdList, temp, pass, metaCategories, categories1, complexityLevel, nQuestions);
	}

	
	

	
	// ------------------- PRIVATE METHODS FOR TEST CREATION ---------------------- // BEGIN -----------

	////
	////-------------- Method for test case group AlexFoox Company return id of unique set questions ----------// BEGIN  //
	@SuppressWarnings("unchecked")	
	@Override  
	public List<Long> createSetQuestions(String metaCategory, String categories1, String levelsOfDifficulty, int nQuestion) {			
		
			List<Long> result = new ArrayList<Long>();
			if(nQuestion > 0 && metaCategory != null){		
				String[] categoryArray = metaCategory.split(",");	
				String[] levelsArray = levelsOfDifficulty.split(",");
				String[] categories1Array;
				if(categories1!=null) {
					categories1Array = categories1.split(",");
				} else {
					categories1Array = new String[categoryArray.length];
					Arrays.fill(categories1Array, "");
				}
				
				StringBuilder condition;
				Query query;
				EntityCompany ec = getCompany();
				List<Long> allAttributeQuestionsId;
							
				int typeNumbers = categoryArray.length;			
				long step = nQuestion/typeNumbers;
				long r = nQuestion % typeNumbers;
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
					if(!categories1Array[i].endsWith(ICommonData.NO_CATEGORY1)) {
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
					
					if(i == typeNumbers-1) 
						step = step +r;
					
					allAttributeQuestionsId = query.getResultList();
					count = count--;
					
					if(allAttributeQuestionsId == null) {
						
						if(count-i-1>0) {
							step = (nQuestion - nGeneratedQuestion) / (count-i-1);
							r = (nQuestion - nGeneratedQuestion) % (count-i-1);
						} 
						
					} else if (allAttributeQuestionsId.size() < step) {
						long resultSize = (long) allAttributeQuestionsId.size();
						result.addAll(randomAttributeQuestionsId(allAttributeQuestionsId, resultSize));
						nGeneratedQuestion += resultSize;
						
						if(count-i-1>0) {
							step = (nQuestion - nGeneratedQuestion) / (count-i-1);
							r = (nQuestion - nGeneratedQuestion) % (count-i-1);
						}
						
					} else {
					
						nGeneratedQuestion += allAttributeQuestionsId.size();
						result.addAll(randomAttributeQuestionsId(allAttributeQuestionsId, step));
					}			
					
				}			
	
			}
			return result;
		}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	private int testFromQuestionList(List<Long> questionIdList, EntityPerson ePerson, String pass, String metaCategories, String categories1, 
			String complexityLevel, String nQuestions) {
		int result = -1;
		int numberQuestions = Integer.parseInt(nQuestions);
		int listSize;
		
		if(questionIdList==null) {
			listSize =0;
			questionIdList = new ArrayList<Long>();
		} else
			listSize = questionIdList.size();
		
		if (listSize < numberQuestions) {
			List<Long> autoQuestions = this.createSetQuestions(metaCategories, categories1, complexityLevel, (numberQuestions - listSize));
			if(autoQuestions == null) {				
				return 1;
				}
			else
				result = 0;
			questionIdList.addAll(autoQuestions);
		} 
		
		if(!this.createTest(questionIdList, ePerson, pass) && result!=1)
			result = 1;
		
		return result;
		
	}
	
	private boolean checkCategory1 (String metaCategory, String category1) {
		boolean result = false;
		
		List<String> categories1InDb = this.getCategories1ByMetaCategory(metaCategory);
		if(categories1InDb.contains(category1))
			result = true;
		
		return result;
	}
	
	//// TO DO ! CHECK UNIQUE !!!!!!!!!!!!!!!!!	
	private static List<Long> randomAttributeQuestionsId(List<Long> allAttributeQuestionsId, Long nQuestion){
		List<Long> result = new ArrayList<Long>();
		if(allAttributeQuestionsId.size() > 0){
			if(nQuestion >= allAttributeQuestionsId.size()){
				result = allAttributeQuestionsId;
			}else{
				for(int i=0; i<nQuestion;){	
					Random rnd = new Random();
					int rand =  rnd.nextInt(allAttributeQuestionsId.size());							
					long questionAttributeId = allAttributeQuestionsId.get(rand);
					if(result.size() > 1){
						int flag = 0;          
						for(Long num: result){
							if(num == questionAttributeId){
								flag = 1;
							}
						}	
						if(flag == 0){
							result.add(questionAttributeId);
							i++;	
						}						
					}else{
						result.add(questionAttributeId);
						i++;
					}
				}				
			}
		}
		return result;
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
			fileManager.initializeTestFileStructure(companyId, testId);
			////			
			IPersonTestHandler testResultsJsonHandler = new PersonTestHandler(companyId, testId, fileManager, em);			
			testResultsJsonHandler.addQuestions(questionIdList);			
			test.setAmountOfQuestions(testResultsJsonHandler.length());
			test.setPassed(false);
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

	// ------------------- PRIVATE METHODS FOR TEST CREATION ---------------------- // END -----------

}

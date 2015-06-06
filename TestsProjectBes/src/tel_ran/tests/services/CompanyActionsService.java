package tel_ran.tests.services;

import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tel_ran.tests.entitys.EntityAdministrators;
import tel_ran.tests.entitys.EntityCompany;
import tel_ran.tests.entitys.EntityPerson;
import tel_ran.tests.entitys.EntityTest;
import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.interfaces.ICompanyActionsService;
import tel_ran.tests.services.interfaces.IFileManagerService;
import tel_ran.tests.services.testhandler.IPersonTestHandler;
import tel_ran.tests.services.testhandler.PersonTestHandler;
import tel_ran.tests.token_cipher.TokenProcessor;

public class CompanyActionsService extends TestsPersistence implements ICompanyActionsService {
	private EntityCompany entityCompany;
	@Autowired
	private TokenProcessor tokenProcessor;

	IFileManagerService fm = new FileManagerService();
	//-------------Use Case Company Login 3.1.1----------- //   BEGIN    ///
	@Override
	public boolean CompanyAuthorization(String companyName, String password) {
		boolean result = false;
		entityCompany = (EntityCompany) em.createQuery("Select c from EntityCompany c where c.C_Name='" + companyName+"'" ).getSingleResult();
		if(entityCompany != null){
			if( entityCompany.getPassword().equals(password)){
				result = true;
			}else{
				result = false;
			}
		}
		return result;
	}
	////
	@Override
	public long getCompanyByName(String companyName) {   
		long result = -1;			
		EntityCompany tempCompanyEntity = null;

		try {
			tempCompanyEntity = (EntityCompany) em.createQuery("Select c from EntityCompany c where c.C_Name='" + companyName+"'" ).getSingleResult();			
		} catch (Exception e) {
			//e.printStackTrace();
		}		
		if(tempCompanyEntity != null && tempCompanyEntity.getC_Name().equalsIgnoreCase(companyName)){
			result = tempCompanyEntity.getId();
		}
		return result;
	}
	//-------------Use Case Company Login 3.1.1----------- //   END    ///

	//-------------Use Case Company Sign up 3.1.2----------- //   BEGIN    ///
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public String[] getAnySingleQuery(String strQuery) {
		if(em.find(EntityAdministrators.class,"qqq@qqq.qq") == null){
			EntityAdministrators emad = new EntityAdministrators();
			emad.setPassportNumber("12345");
			emad.setUserMail("qqq@qqq.qq");
			emad.setUserPassword("12345");
			emad.setUserName("test");
			emad.setUserAddress("californy");
			em.persist(emad);
		}
		String[] outResult;
		List<EntityCompany> result = em.createQuery(
				"SELECT c FROM EntityCompany c WHERE c.C_Name LIKE :custName").setParameter("custName","%"+strQuery+"%").getResultList();// return to client result of operation
		int len_gth = result.size();
		outResult = new String[len_gth];
		int flCount = 0;
		for(EntityCompany q: result){		
			if(flCount != len_gth){
				outResult[flCount++] = q.toString();
			}
		}
		return outResult;// return to client 
	}

	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public boolean createCompany(String C_Name, String C_Site, String C_Specialization, String C_AmountEmployes, String C_Password) {
		boolean result=false;		
		try {
			EntityCompany comp = new EntityCompany();
			comp.setC_Name(C_Name);
			comp.setC_Site(C_Site);
			comp.setC_Specialization(C_Specialization);
			comp.setC_AmountEmployes(C_AmountEmployes);
			comp.setPassword(C_Password);
			em.persist(comp);
			result=true;
		} catch (Throwable e) {
			result=false;
			System.out.println("catch from CREATE COMPANY BES");
			e.printStackTrace();
		}

		return result;
	}
	//-------------Use Case Company Sign up 3.1.2-----------  /// END  ///

	//------------- 	Use case Ordering Test 3.1.3 -------------/// BEGIN ////
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public long createIdTest(List<Long> questionsID, int personId, String pass, String category, String levelOfDifficulty) {
		EntityPerson temp = em.find(EntityPerson.class, personId);	

		EntityTest test = new EntityTest();
		test.setPassword(pass); 		
		test.setStartTestDate(0L);// setting parameter for wotchig method in FES
		test.setEndTestDate(0L);// setting parameter for wotchig method in FES
		//
		test.setEntityCompany(entityCompany);	
		test.setEntityPerson(temp);
		//
		em.persist(test);
		long testId = test.getTestId();
		if( questionsID.size() > 0 ){
			long companyId = test.getEntityCompany().getId();
			////  creating folder treee for test
			fm.initializeTestFileStructure(companyId, testId);
			////
			
			IPersonTestHandler testResultsJsonHandler = new PersonTestHandler(null);
			testResultsJsonHandler.addQuestions(questionsID);
			fm.saveJson(companyId, testId, testResultsJsonHandler.getJsonTestResults());
			
			test.setAmountOfQuestions(testResultsJsonHandler.length());
			test.setPassed(false);
			em.persist(test);
		}
		return testId;
	}
	
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public int createPerson(int personId,String personName,String personSurname,String personEmail) {
		int result = personId;		
		if(em.find(EntityPerson.class, personId)==null){			
			EntityPerson person = new EntityPerson();
			person.setPersonId(personId);
			person.setPersonName(personName);
			person.setPersonSurname(personSurname);	
			person.setPersonEmail(personEmail);
			em.persist(person);        
			result=person.getPersonId();
		}
		return result;
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
			res = generateJsonResponse(tests, timeZone);
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
			("SELECT t FROM EntityTest t WHERE t.endTestDate!=0 AND t.entityPerson = :person AND t.entityCompany = :company")
			.setParameter("person", person)
			.setParameter("company", company)
			.getResultList();
			res = generateJsonResponse(tests, timeZone);
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
			("SELECT t FROM EntityTest t WHERE t.endTestDate!=0 AND t.startTestDate >= :date_from AND t.startTestDate <= :date_until AND t.entityCompany = :company ORDER BY t.entityPerson")
			.setParameter("date_from", date_from)
			.setParameter("date_until", date_until)
			.setParameter("company", company)
			.getResultList();
			res = generateJsonResponse(tests, timeZone);
		}
		return res;
	}

	@Override
	public String getTestResultDetails(long companyId, long testId) {
		String res = "{}";
		EntityCompany company = em.find(EntityCompany.class, companyId);
		if(company!=null){
			EntityTest test = (EntityTest) em.createQuery
					("SELECT t FROM EntityTest t WHERE t.endTestDate!=0 AND t.testId = :testId AND t.entityCompany = :company")
					.setParameter("testId", testId)
					.setParameter("company", company)
					.getSingleResult();
			res = test.getJsonDetails();// TO DO Throws actions NullPointerException !!!!!!!!!!!!!
		}
		return res;
	}

	private String generateJsonResponse(List<EntityTest> testresults, String timeZone) {
		JSONArray result = new JSONArray();
		for (EntityTest test: testresults){
			result.put(test.getJsonObjectCommonData(timeZone));
		}
		return result.toString();
	}

	@Override
	public String encodeIntoToken(long companyId) {
		//encodes current timestamp and companyId into token
		String token = tokenProcessor.encodeIntoToken(companyId, ICommonData.TOKEN_VALID_IN_SECONDS);
		return token;
	}	
	//------------- Viewing test results  3.1.4.----------- // END ////	
}

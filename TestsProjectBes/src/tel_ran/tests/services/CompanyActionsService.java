package tel_ran.tests.services;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tel_ran.tests.entitys.EntityCompany;
import tel_ran.tests.entitys.EntityPerson;
import tel_ran.tests.entitys.EntityTest;
import tel_ran.tests.services.interfaces.ICompanyActionsService;

public class CompanyActionsService extends TestsPersistence implements ICompanyActionsService {
private EntityCompany entityCompany;
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

	@Override
	public long getCompanyByName(String companyName) {
		long result = 0;		
		EntityCompany tempCompanyEntity = (EntityCompany) em.createQuery("Select c from EntityCompany c where c.C_Name='" + companyName+"'" ).getSingleResult();		
		if(tempCompanyEntity != null && tempCompanyEntity.getC_Name().equalsIgnoreCase(companyName)){
			result = tempCompanyEntity.getId();
		}
		return result;
	}
	//-------------Use Case Company Login 3.1.1----------- //   END    ///
	
	//-------------Use Case Company Sign up 3.1.2----------- //   BEGIN    ///
	@SuppressWarnings("unchecked")
	@Override
	public String[] getAnySingleQuery(String strQuery) {
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
				EntityCompany comp =new EntityCompany();
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
	public long  createIdTest(List<Long> list, int personId, String pass) {
		EntityPerson temp = em.find(EntityPerson.class, personId);	
		EntityTest test = new EntityTest();
		EntityPerson pers = new EntityPerson();
		StringBuffer idQuestion = new StringBuffer();
		for(Long s : list){			
			idQuestion.append(s);
			idQuestion.append(",");
		}
		test.setQuestion(idQuestion.toString() );
		pers.setPersonId(personId);
		test.setEntityCompany(entityCompany);
		test.setPassword(pass); 
		test.setEntityPerson(temp);

		em.persist(test);
		long testId = test.getTestId();
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
	public String getTestsResultsAll(long companyId) {
		String res = "";
		EntityCompany company = em.find(EntityCompany.class, companyId);
		if(company!=null){
			@SuppressWarnings("unchecked")
			List<EntityTest> tests = (List<EntityTest>) em.createQuery
			("SELECT t FROM EntityTest t WHERE t.entityCompany = :company ORDER BY t.entityPerson")
			.setParameter("company", company)
			.getResultList();
			res = generateJsonResponse(tests);
		}
		return res;
	}

	@Override
	public String getTestsResultsForPersonID(long companyId, int personID) {
		String res = "";
		EntityCompany company = em.find(EntityCompany.class, companyId);
		EntityPerson person = em.find(EntityPerson.class, personID);
		if(company!=null && person != null){
			@SuppressWarnings("unchecked")
			List<EntityTest> tests = (List<EntityTest>) em.createQuery
			("SELECT t FROM EntityTest t WHERE t.entityPerson = :person AND t.entityCompany = :company")
			.setParameter("person", person)
			.setParameter("company", company)
			.getResultList();
			res = generateJsonResponse(tests);
		}
		return res;
	}

	@Override
	public String getTestsResultsForTimeInterval(long companyId, Date date_from, Date date_until) {
		 String res = "";
		 EntityCompany company = em.find(EntityCompany.class, companyId);
		 if(company!=null){
			 @SuppressWarnings("unchecked")
			 List<EntityTest> tests = (List<EntityTest>) em.createQuery
			 ("SELECT t FROM EntityTest t WHERE t.testDate >= :date_from AND t.testDate <= :date_until AND t.entityCompany = :company ORDER BY t.entityPerson")
			 .setParameter("date_from", date_from)
			 .setParameter("date_until", date_until)
			 .setParameter("company", company)
			 .getResultList();
			 res = generateJsonResponse(tests);
		 }
		 return res;
	 }
	
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
			 res = test.getJsonDetails();
		 }
		 return res;
	 }
	 
	 private String generateJsonResponse(List<EntityTest> testresults) {
		 JSONArray result = new JSONArray();
		 for (EntityTest test: testresults){
			 result.put(test.getJsonObjectCommonData());
		 }
		 return result.toString();
	 }
	 //------------- Viewing test results  3.1.4.----------- // END ////

}

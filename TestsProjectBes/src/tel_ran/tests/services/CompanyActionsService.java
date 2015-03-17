package tel_ran.tests.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tel_ran.tests.entitys.EntityCompany;
import tel_ran.tests.entitys.EntityPerson;
import tel_ran.tests.entitys.EntityTest;
import tel_ran.tests.entitys.EntityTestResultCommon;
import tel_ran.tests.services.interfaces.ICompanyActionsService;


public class CompanyActionsService extends TestsPersistence implements ICompanyActionsService {

	//-------------Use Case Company Login 3.1.1----------- //   BEGIN    ///
	@Override
	public boolean CompanyAuthorization(String companyName, String password) {
		boolean result = false;
		EntityCompany res = em.find(EntityCompany.class, companyName);
		if(res != null){
			if( res.getPassword().equals(password)){
				result = true;
			}else{
				result = false;
			}
		}
		return result;
	}

	@Override
	public boolean getCompanyByName(String companyName) {
		boolean result = false;
		if(em.find(EntityCompany.class, companyName) != null){
			result = true;
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
	public boolean createCompany(String C_Name, String C_Site,
			String C_Specialization, String C_AmountEmployes, String C_Password) {
		boolean result=false;
		if(em.find(EntityCompany.class, C_Name)==null){
			EntityCompany comp =new EntityCompany();
			comp.setC_Name(C_Name);
			comp.setC_Site(C_Site);
			comp.setC_Specialization(C_Specialization);
			comp.setC_AmountEmployes(C_AmountEmployes);
			comp.setPassword(C_Password);
			em.persist(comp);
			result=true;
		}
		return result;
	}

	//-------------Use Case Company Sign up 3.1.2-----------  /// END  ///
	//------------- 	Use case Ordering Test 3.1.3 -------------/// BEGIN ////
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public long  createIdTest(List<Long> list, int personId) {
		EntityPerson temp = em.find(EntityPerson.class, personId);	
		EntityTest test = new EntityTest();
		EntityPerson pers = new EntityPerson();
		StringBuffer idQuestion = new StringBuffer();
		for(Long s : list){
			System.out.println(s.toString());
			idQuestion.append(s);
			idQuestion.append(",");
		}
		test.setQuestion(idQuestion.toString() );
		pers.setPersonId(personId);
		test.setPersonId(temp);
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
	public List<String> getTestsResultsAll(String companyName) {
		List<String> res = new ArrayList<String>();
		EntityCompany company = em.find(EntityCompany.class, companyName);
		if(company!=null){
			@SuppressWarnings("unchecked")
			List<EntityTestResultCommon> tests = (List<EntityTestResultCommon>) em.createQuery
			("SELECT t FROM EntityTestResultCommon t WHERE t.company = :company ORDER BY t.entityPerson")
			.setParameter("company", company)
			.getResultList();
			generateJsonResponse(res, tests);
		}
		return res;
	}

	@Override
	public List<String> getTestsResultsForPersonID(String companyName, int personID) {
		List<String> res = new ArrayList<String>();
		EntityCompany company = em.find(EntityCompany.class, companyName);
		EntityPerson person = em.find(EntityPerson.class, personID);
		if(company!=null){
			@SuppressWarnings("unchecked")
			List<EntityTestResultCommon> tests = (List<EntityTestResultCommon>) em.createQuery
			("SELECT t FROM EntityTestResultCommon t WHERE t.entityPerson = :person AND t.company = :company")
			.setParameter("person", person)
			.setParameter("company", company)
			.getResultList();
			generateJsonResponse(res, tests);
		}
		return res;
	}

	@Override
	public List<String> getTestsResultsForTimeInterval(String companyName, Date date_from, Date date_until) {
		List<String> res = new ArrayList<String>();
		EntityCompany company = em.find(EntityCompany.class, companyName);
		if(company!=null){
			@SuppressWarnings("unchecked")
			List<EntityTestResultCommon> tests = (List<EntityTestResultCommon>) em.createQuery
			("SELECT t FROM EntityTestResultCommon t WHERE t.testDate >= :date_from AND t.testDate <= :date_until AND t.company = :company ORDER BY t.entityPerson")
			.setParameter("date_from", date_from)
			.setParameter("date_until", date_until)
			.setParameter("company", company)
			.getResultList();
			generateJsonResponse(res, tests);
		}
		return res;
	}

	private void generateJsonResponse(List<String> res,	List<EntityTestResultCommon> tests) {
		for (EntityTestResultCommon test: tests){
			JSONObject jsonObj = new JSONObject();
			test.getEntityPerson().fillJsonObject(jsonObj); //Adding person data to jSon
			test.fillJsonObject(jsonObj);					//Adding TestResultCommon data to jSon
			test.getEntityTestResultDetails().fillJsonObject(jsonObj); //Adding TestResultDetails data to jSon
			//	System.out.println(jsonObj.toString());
			res.add(jsonObj.toString());
		}
	}
	//------------- Viewing test results  3.1.4.----------- // END ////
	//////////////////////////////////////////////////////////////////////////////////////////
	//-------------Use Case Company  3.1.?----------- //   BEGIN    ///
	@Override
	public void printQuestion(int arg0) {
		// TODO Auto-generated method stub
	}
	//-------------Use Case Company  3.1.?----------- //   END    ///
}

package tel_ran.tests.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.json.JSONObject;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tel_ran.tests.services.interfaces.ICompanyActionsService;


public class CompanyActionsService extends TestsPersistence implements ICompanyActionsService {

	//-------------Use Case Company Sign up 3.1.2----------- //   BEGIN    ///
	@Override
	public String[] getAnySingleQuery(String strQuery) {

		String []array = null;
		try {
			Query query=em.createQuery(strQuery);
			List<Object> result=query.getResultList();
			array=new String[result.size()];
			int ind=0;
			for(Object obj: result)
				array[ind++]=obj.toString();
		} catch (Throwable e) {
			array=new String[1];
			array[0]="Sorry, you should learn jpql "+e.getMessage();
		}
		return array;
	}


	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public boolean createCompany(String C_Name, String C_Site,
			String C_Specialization, String C_AmountEmployes, String C_Password) {

		boolean result=false;

		if(em.find(Company.class, C_Name)==null){
			Company comp =new Company();
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

//-------------Use Case Company Sign up 3.1.2-----------  /// end  ///
	@Override
	public void printQuestion(int arg0) {
		// TODO Auto-generated method stub

	}
	

	
	// 	3.1.4. Viewing test results /// BEGIN ////
	@Override
	public List<String> getTestsResultsAll(String companyName) {
		List<String> res = new ArrayList<String>();
		Company company = em.find(Company.class, companyName);
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
		Company company = em.find(Company.class, companyName);
		EntityPerson person = em.find(EntityPerson.class, Integer.toString(personID));
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
		Company company = em.find(Company.class, companyName);
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
	// 	3.1.4. Viewing test results /// END ////




	// 	Use case Ordering Test 3.1.3 /// BEGIN ////
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public long  createIdTest(List<Long> list, int personId) {
		EntityPersonFOOX temp = em.find(EntityPersonFOOX.class, personId);	
		EntityTest test = new EntityTest();
		EntityPersonFOOX pers = new EntityPersonFOOX();
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
	public int createPerson(int personId,String personName,String personSurname) {
		int result = personId;		
		if(em.find(EntityPersonFOOX.class, personId)==null){
			EntityPersonFOOX person = new EntityPersonFOOX();
			person.setPersonId(personId);
			person.setPersonName(personName);
			person.setPersonSurname(personSurname);			
			em.persist(person);        
			result=person.getPersonId();
			
		}
		return result;
	}
	// 	Use case Ordering Test 3.1.3 /// END  ////

}

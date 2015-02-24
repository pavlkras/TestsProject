package tel_ran.tests.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.json.JSONObject;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tel_ran.tests.services.interfaces.ICompanyActionsService;

public class CompanyActionsService extends TestsPersistence implements ICompanyActionsService {

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


	@Override
	public void printQuestion(int arg0) {
		// TODO Auto-generated method stub
		
	}
// 	3.1.4. Viewing test results /// BEGIN ////
	@Override
	public List<String> getTestsResultsAll(String companyName) {
		List<String> res = new ArrayList<String>();
		Company company = getCompanyInstance(companyName);
		
		if(company!=null){
			/*@SuppressWarnings("unchecked")
			Query query = em.createQuery("SELECT p, t FROM EntityPerson p LEFT OUTER JOIN p.entityTestResultCommon t WHERE p.company = :company ORDER BY p.person_id").setParameter("company", company);
			List<Object[]> personsAndTests = (List<Object[]>) query.getResultList();
			System.out.println("Number of objects in list: "+personsAndTests.size());
			System.out.println("Number of objects in array: "+personsAndTests.get(0).length);

			if( personsAndTests.size() > 0 ){
				res = new ArrayList<String>();
				for(Object[] personAndTests:personsAndTests){
					StringBuffer strbuf = new StringBuffer();
					EntityPerson person = (EntityPerson) personAndTests[0];
					//TODO jSon process: add personjSondata
					strbuf.append(person.toString()).append("/--/");
					for( int i = 1; i < personAndTests.length; i++ ){
						EntityTestResultCommon test = (EntityTestResultCommon) personAndTests[i];
						//TODO jSon process: add testjSonData
						strbuf.append(test.toString());
					}
					System.out.println(strbuf.toString());
					res.add(strbuf.toString());
				}
			}
*/
			List<EntityTestResultCommon> tests = company.getEntityTestResultCommon();
			for (EntityTestResultCommon test: tests){
				JSONObject jsonObj = new JSONObject();
				test.fillJsonObject(jsonObj);
				test.getEntityTestResultDetails().fillJsonObject(jsonObj);
				test.getEntityPerson().fillJsonObject(jsonObj);
				
				res.add(jsonObj.toString());
			}
		}
		return res;
	}

	private Company getCompanyInstance(String companyName) {
		Company company = null;
		try{
			company = (Company)em.createQuery("SELECT c FROM Company c WHERE c.C_Name = '"+companyName+"'").getSingleResult();
		}catch(Exception e){}
		return company;
	}

	@Override
	public List<String> getTestsResultsForPersonID(String companyName, int personID) {
		Company company = null;
		company = getCompanyInstance(companyName);
		
		@SuppressWarnings("unchecked")
		List<EntityTestResultCommon> testsData = (List<EntityTestResultCommon>) em.createQuery
				("SELECT t FROM EntityPerson p JOIN p.entityTestResultCommon t WHERE p.C_Name = :company AND p.person_id = :personID")
				.setParameter("personID", personID).setParameter("company", company).getResultList();
		System.out.println("Number of tests gotten from DB: "+testsData.size());
		List<String> res = new ArrayList<String>();
		if( testsData.size() > 0 ){
			for(EntityTestResultCommon test:testsData){
				res.add(test.toString());
			}
		}
		return res;
	}

	@Override
	public List<String> getTestsResultsForTimeInterval(String companyName,
			Date date_from, Date date_until) {
		
		
		return new ArrayList<String>();
	}

	@Override
	public String getTestsResultsForTestID(String companyName, int testID) {
		return "";
	}
// 	3.1.4. Viewing test results /// END ////

}

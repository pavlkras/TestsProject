package tel_ran.tests.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

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
		
		Company company = (Company) em.createQuery("SELECT c FROM Company c WHERE c.C_Name="+companyName).getSingleResult();
		Set<EntityPerson> persons = company.getEntityPerson();
		for(EntityPerson person: persons){
			List<EntityTestCommon> test_data = (List<EntityTestCommon>) em.createQuery("SELECT t FROM EntityTestCommon WHERE t.entityPerson LIKE :pers").setParameter("pers", person).getResultList();
			for(EntityTestCommon test:test_data){
				res.add(test.toString());
			}
		}
		return res;
	}

	@Override
	public List<String> getTestsResultsForPersonID(String companyName, int personID) {
		
		
		return new ArrayList<String>();
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

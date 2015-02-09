package tel_ran.tests.services;

import java.util.Date;
import java.util.List;

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
// 	
	@Override
	public List<String> getTestsResultsAll(int companyID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getTestsResultsForPersonID(int companyID, int personID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getTestsResultsForTimeInterval(int companyID,
			Date date_from, Date date_until) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTestsResultsForTestID(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}


}

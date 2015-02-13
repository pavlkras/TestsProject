package tel_ran.tests.services;

import java.util.ArrayList;
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
// 	3.1.4. Viewing test results /// BEGIN ////
	@Override
	public List<String> getTestsResultsAll(String companyID) {
		List<String> res = new ArrayList<String>();
/*		EntityCompanyTwo company = (EntityCompanyTwo)em.createQuery("SELECT company FROM EntityCompanyTwo WHERE company.c_ID = "+companyID).getSingleResult();
		List<EntityTestCommon> respose = (List<EntityTestCommon>) em.createQuery(
				"Select testCommon FROM EntityTestCommon WHERE testCommon.company=").getResultList();
		
		for(EntityTestCommon etc:respose){
			res.add(etc.toString());
		}*/
		return res;
	}

	@Override
	public List<String> getTestsResultsForPersonID(String companyID, int personID) {
		
		
		return new ArrayList<String>();
	}

	@Override
	public List<String> getTestsResultsForTimeInterval(String companyID,
			Date date_from, Date date_until) {
		
		
		return new ArrayList<String>();
	}

	@Override
	public String getTestsResultsForTestID(String arg0, int arg1) {
		return "";
	}
// 	3.1.4. Viewing test results /// END ////

}

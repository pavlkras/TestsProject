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
// 	
	@Override
	public List<String> getTestsResultsAll(int companyID) {
		
		
		List<String> res = new ArrayList<>();
		
		//for testing purpose
		String divider = "/--/";
		res.add("FirstName"+divider+"SecondName"+divider+"TestCategory"+divider+"TestName"+divider+"TestDate"+divider+"TestID");
		res.add("Name1"+divider+"Name1"+divider+"Category1"+divider+"Name1"+divider+"4/23/2014"+divider+"1");
		res.add("Name2"+divider+"Name2"+divider+"Category3"+divider+"Name2"+divider+"6/30/2014"+divider+"2");
		return res;
	}

	@Override
	public List<String> getTestsResultsForPersonID(int companyID, int personID) {
		
		List<String> res = new ArrayList<>();
		
		//for testing purpose
		String divider = "/--/";
		res.add("FirstName"+divider+"SecondName"+divider+"TestCategory"+divider+"TestName"+divider+"TestDate"+divider+"TestID");
		res.add("Name1"+divider+"Name1"+divider+"Category1"+divider+"Name1"+divider+"4/23/2014"+divider+"1");
		res.add("Name2"+divider+"Name2"+divider+"Category3"+divider+"Name2"+divider+"6/30/2014"+divider+"2");
		return res;
	}

	@Override
	public List<String> getTestsResultsForTimeInterval(int companyID,
			Date date_from, Date date_until) {
		
		List<String> res = new ArrayList<>();
		
		//for testing purpose
		String divider = "/--/";
		res.add("FirstName"+divider+"SecondName"+divider+"TestCategory"+divider+"TestName"+divider+"TestDate"+divider+"TestID");
		res.add("Name1"+divider+"Name1"+divider+"Category1"+divider+"Name1"+divider+"4/23/2014"+divider+"1");
		res.add("Name2"+divider+"Name2"+divider+"Category3"+divider+"Name2"+divider+"6/30/2014"+divider+"2");
		return res;
	}

	@Override
	public String getTestsResultsForTestID(int arg0, int arg1) {
		
		//for testing purpose
		String divider = "/--/";
		// duration Nquestions ComplexityLevel QRightAnsw QWrongAnsw Photo1URL Photo2URL Photo3URL Photo4URL Photo5URL
		return "duration"+divider+"Nquestions"+divider+"ComplexityLevel"+divider+"QRightAnsw"+divider+"QWrongAnsw"+divider+"http://localhost/";
	}


}

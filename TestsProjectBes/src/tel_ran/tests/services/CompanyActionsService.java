package tel_ran.tests.services;

import java.util.Date;
import java.util.List;

import tel_ran.tests.services.interfaces.ICompanyActionsService;

public class CompanyActionsService extends TestsPersistence implements ICompanyActionsService {

	@Override
	public boolean createCompany(String arg0, String arg1, String arg2,
			String arg3, String arg4) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String[] getAnySingleQuery(String arg0) {
		// TODO Auto-generated method stub
		return null;
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


}

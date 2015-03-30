package tel_ran.tests.services.interfaces;

import java.util.Date;
import java.util.List;

public interface ICompanyActionsService {
//Use case 3.1.1
long getCompanyByName(String companyName);
boolean CompanyAuthorization(String companyName , String password);
	


//alexfoox Company 
boolean createCompany(String C_Name,String C_Site, String C_Specialization,String C_AmountEmployes,String C_Password);
String []getAnySingleQuery(String strQuery);

//Use case Ordering Test 3.1.3
long createIdTest(List<Long> list, int personId, String pass, String category, int complexityLevel);
int createPerson(int personId,String personName,String personSurname,String personEmail);

//Company actions for 3.1.4. Viewing test results
public String getTestsResultsAll(long companyId);
public String getTestsResultsForPersonID(long companyId, int personID);
public String getTestsResultsForTimeInterval(long companyId, Date date_from, Date date_until);
public String getTestResultDetails(long companyId, long testId);
}
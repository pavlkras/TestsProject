package tel_ran.tests.services.interfaces;

import java.util.List;

import tel_ran.tests.services.fields.ApplicationFinalFields;

public interface ICompanyActionsService extends ApplicationFinalFields{
//Use case 3.1.1
long getCompanyByName(String companyName);
boolean CompanyAuthorization(String companyName , String password);
boolean CreateCompany(String C_Name,String C_Site, String C_Specialization,String C_AmountEmployes,String C_Password);
String []getAnySingleQuery(String strQuery);

//Use case Ordering Test 3.1.3
boolean CreateTest(List<Long> list, int personId, String pass, String category, String complexityLevel);
int CreatePerson(int personId,String personName,String personSurname,String personEmail);

//Company actions for 3.1.4. Viewing test results
public String getTestsResultsAll(long companyId, String timeZone);
public String getTestsResultsForPersonID(long companyId, int personID, String timeZone);
public String getTestsResultsForTimeInterval(long companyId, long date_from, long date_until, String timeZone);
public String getTestResultDetails(long companyId, long testId);
public String encodeIntoToken(long companyId);
}
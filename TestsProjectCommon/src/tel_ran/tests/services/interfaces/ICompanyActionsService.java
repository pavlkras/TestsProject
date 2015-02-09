package tel_ran.tests.services.interfaces;

import java.util.Date;
import java.util.List;

public interface ICompanyActionsService {
void printQuestion(int id);

//alexfoox Company 
boolean createCompany(String C_Name,String C_Site, String C_Specialization,String C_AmountEmployes,String C_Password);
String []getAnySingleQuery(String strQuery);

//Company actions for 3.1.4. Viewing test results
List<String> getTestsResultsAll(int currCompanyId);
List<String> getTestsResultsForTimeInterval(int currCompanyId, Date timeFrom, Date timeUntil);
List<String> getTestsResultsForPersonID(int currCompanyId, int PersonID);
String getTestsResultsForTestID(int currCompanyId, int TestID);
//getPicturesFromTest(int testID);


}
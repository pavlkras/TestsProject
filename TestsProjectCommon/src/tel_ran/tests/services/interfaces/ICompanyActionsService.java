package tel_ran.tests.services.interfaces;

import java.util.List;



/**
 * Interface for Company's services.
 * Includes methods for persons management
 *
 */
public interface ICompanyActionsService extends ICommonAdminService {
	//Use case 3.1.1
	
	// long getCompanyByName(String companyName); - moved to Maintenance Service
	
	// boolean CompanyAuthorization(String companyName , String password); - moved to Naintenance Service / setAuthorization!
	// boolean CreateCompany(String C_Name,String C_Site, String C_Specialization,String C_AmountEmployes,String C_Password); - moved to MaintenaceService
	// String []getAnySingleQuery(String strQuery); - moved to Maintenance Service

	//Use case Ordering Test 3.1.3
	boolean CreateTest(List<Long> list, int personId, String pass, String category, String complexityLevel);
	int CreatePerson(int personId,String personName,String personSurname,String personEmail);
	
	/**
	 * New version of the function 
	 * @param personId
	 * @param category
	 * @param level_num
	 * @param nQuestion
	 * @return
	 */
	boolean CreateTest(int personId, String category, String level_num, Long nQuestion);

	//Company actions for 3.1.4. Viewing test results
	public String getTestsResultsAll(long companyId, String timeZone);
	public String getTestsResultsForPersonID(long companyId, int personID, String timeZone);
	public String getTestsResultsForTimeInterval(long companyId, long date_from, long date_until, String timeZone);
	public String getTestResultDetails(long companyId, long testId);
	public String encodeIntoToken(long companyId);
	String[] getAnySingleQuery(String strQuery);
}
package tel_ran.tests.services.interfaces;
public interface ICompanyActionsService {
void printQuestion(int id);

//alexfoox Company 
boolean createCompany(String C_Name,String C_Site, String C_Specialization,String C_AmountEmployes,String C_Password);
String []getAnySingleQuery(String strQuery);
}

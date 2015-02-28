package tel_ran.tests.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import tel_ran.tests.services.interfaces.ICompanyActionsService;
import tel_ran.tests.services.interfaces.IMaintenanceService;


@Controller
@Scope("session") /*session timer default = 20min*/
@RequestMapping({"/","/CompanyActions"})
public class CompanyActions {
	String companyName;
	@Autowired
	ICompanyActionsService companyService;
	
	@Autowired
	IMaintenanceService maintenanceService;
	
	
	
	// Action Re-mapping for Send Ajax request to DB if equal  user and priority level, and set: flAuthorization=true;
		@RequestMapping({"/CompanyActions"})
		public String signIn(){			
			return "CompanySignIn";
		}
	// ALEX FOOX Action Re-mapping
	@RequestMapping({"/CompanyRegistration"})
	public String transitionActionPage(){		
		return "CompanyRegistrationPage";
	}
	// IGOR Action Re-mapping
	@RequestMapping({"/create_request"})
	public String createRequest(){
		return "CompanyTestsResultsStartPage";
	}

	
/* ----------Use case Company Login--------------
Login Normal Flow: 
1.	The user enters a company username and password. 
2.	The user presses a submit button
3.	The System moves to a home page for the specific company. Alternative flows: Wrong Company Username, Wrong Password
3.1.1.1.	Wrong Company Username
Pre-Conditions:
1.	The user enters wrong username
Wrong Username Flow:
1.	The alert window appears with message: â€œCompany with <username> is not registered. Type the right company username or  go to registrationâ€�
2.	Returns to the Login
3.1.1.2.	Wrong Password
Pre-Conditions:
1.	The user enters wrong password
Wrong Password Flow:
1.	The alert window appears with message: â€œWrong password. Type the right passwordâ€�
2.	Returns to the Login */
	//

	@RequestMapping("/loginProcessing")
	public String loginProcessing(String companyName, String password,Model model){
	boolean IfExistCompany = companyService.getCompanyByName(companyName);
	String result;
	if(IfExistCompany){
	boolean ress = companyService.CompanyAuthorization(companyName, password);
    if(ress ){       
         result = "CompanyGenerateTest";
    }else{
    result = "CompanySignIn";
    model.addAttribute("result", " This password not variable try again " );
    }
    }else {	       			
	   result = "CompanySignIn";
	   model.addAttribute("result", " This Company not exist - " + companyName);
	}
     return result;
	}

	

	// END  --------------- Use case Company Login--------------
	
/* -------------Use Case Company Sign up 3.1.2-----------
	/*Pre-conditions:
1.	The System is running up
2.	The company is not registered in the System
Registration Normal Flow:
1.	The user enters a company username
2.	The user enters an web site of company
3.	The user selects the company specialization (Education, Software development, Telecommunication, etc.)
4.	The user selects number of the employees (up to 10, 10-50, 50-100, 100-500, 500-1000, more 1000)
5.	The user enters password
6.	The user enters password confirmation. Alternative flow: Wrong Password Confirmation
7.	The user presses a submit button
8.	The System moves to Login page. Alternative flow: Company Registered 
3.1.2.3.	Wrong Password Confirmation
Pre-Conditions:
1.	The user enters wrong password confirmation
Wrong Password Confirmation Flow
1.	The alert window appears with message: â€œWrong password confirmation. Type the same passwordâ€�
2.	Returns to the Registration Flow with #6. That is all filled fields except password confirmation should remain filled
3.1.2.4.	Company Registered
Pre-Conditions:
1.	The user enters company username that already has been registered
User Registered Flow:
1.	The alert window appears with message: â€œThe company <username> already registered. Type another company username or go to Login with this oneâ€�
2.	Returns to  the Registration Flow
        	----------Company  ALEX FOOX -----------*/
	@RequestMapping({"/input_form"})
	public String query() {
		return "Company_input_form";
	}

	@RequestMapping({"/query_processing"})
	public String queryProcessing(String jpaStr, Model model) {
		;
		String[] result = companyService.getAnySingleQuery(jpaStr);
		StringBuffer buf = new StringBuffer();
		for (String str : result)
			buf.append(str).append("<br>");
		model.addAttribute("myResult", buf.toString());
		return "Company_result_view";
	}

	@RequestMapping({"/companyadd"})
	public String addCompany() {
		return "Company_add_form";
	}

	@RequestMapping({"/add_processing"})
	public String addProcessing(String C_Name,String C_Site, String C_Specialization,String C_AmountEmployes,String C_Password) {

		boolean flag = companyService.createCompany(C_Name, C_Site, C_Specialization, C_AmountEmployes, C_Password);
		if(flag==true){
			return "Company_success_adding";
		}
		else return "Company_Error";
	}
	//-------------Use Case Company Sign up 3.1.2-----------

	/*-------------Use case Ordering Test 3.1.3-------------
Pre-Conditions:
1.	The System is running up
2.	The user is signed in for the proper company
Normal Flow:
1.	The user selects a test category
2.	The user selects  the  test details (complexity level)
3.	The user enters an identity number of the tested person 
4.	The user enters the person personal data (First Name, Family Name)
5.	The System generates a test and a link for that test
6.	The System presents the link for performing the test in the control mode  */
//

	


	@RequestMapping({"/add_test"})
	public String createTest(String category,String level,int personId,String personName, String personSurname, Model model) {
		maintenanceService.setAutorization(true);

		List<Long> listIdQuestions = maintenanceService.getUniqueSetQuestionsForTest(category, level, (long) 15);

	    int personId1 = companyService.createPerson(personId, personName, personSurname);
		long idTest = companyService.createIdTest(listIdQuestions,personId1);
		String link = "http://localhost:8080/TestsProjectFes/test_preparing?" + idTest;        

		model.addAttribute("myResult", link);

		return "CompanyTestLink";
	}	 
//------------END  Use case Ordering Test 3.1.3-------------

/*-------------Use case Viewing test results----------------
3.1.4.	Viewing test results
Pre-Conditions:
1.	The System is running up
2.	The user is signed in for the proper company
Normal Flow:
1.	The user selects the details for a test results query. There should be the following queries:
a)	All test results
b)	Test results for the specified time period. There should be start date and end date. The selection should be done using standard calendar gadget 
c)	Test results for the specified person identity number
2.	The user fills the proper data for the selected test
3.	The System runs the query
4.	 The System shows the list of the test results items. Item should contain: 
a)	Personal data (First and Family names)
b)	Test details (category, name, etc.)
c)	Test date
5.	The user selects an item
6.	The system shows the following results:
a)	Test duration 
b)	Number of the questions
c)	Complexity level
d)	Number of the right answers with the percentage 
e)	Number of the wrong answers with the percentage
f)	5 photos made during the test	------ IGOR ------*/

	@RequestMapping({"/process_request"})
	public String processRequestTestsCommon(String request_type, String date_from, String date_until, String person_id, Model model){

		boolean isError = false;
		String res = "";
		List<String> bes_response = new ArrayList<String>();

		if(request_type.equals("all")){
			List<String> temp = companyService.getTestsResultsAll("Comp2");  //Changed for test
			if(temp != null){
				bes_response = temp; 
			}

		}else if(request_type.equals("time_interval")){
			Date date_from_ = null;
			Date date_until_ = null;

			try {
				DateFormat df = new SimpleDateFormat ("MM/dd/yyyy"); 
				date_from_ = df.parse(date_from);
				date_until_ = df.parse(date_until);
				System.out.println(date_from_);
				System.out.println(date_until_);
				if (date_until_.compareTo(date_from_)<0){
					isError = true;
				}
			} catch (ParseException e) {
				isError = true;
			}
			if(!isError)
				bes_response = companyService.getTestsResultsForTimeInterval("Comp2", date_from_, date_until_);

		}else if(request_type.equals("by_person_id")){
			int id = 0;
			try {
				id = Integer.parseInt(person_id);
				System.out.println("Parsed int: "+id);
			}catch(NumberFormatException e){
				isError = true;
			}
			if(!isError)
				bes_response = companyService.getTestsResultsForPersonID("Comp2", id);
		}

		if (isError){
			res = "ErrorMessage";
		}else{
			StringBuffer strbuf = new StringBuffer();
			for(String jSon : bes_response){
				strbuf.append(jSon);
			}
			res = strbuf.toString();
			//res = CompanyTestsResutlsHandler.compileToViewTestCommon(bes_response);
		}
		model.addAttribute("res", res);

		return "CompanyTestsCommon";
	}
	/*In order to use output string from method "compile_to_view" you should surround the code generated by method with <table></table> tags.
	 Exanple:
	 <table style="width: 100%" border=1>${Result}</table>
	 It gives you possibility to assign desirable style for the output table.
	 The method "compile_to_view" is using the first string in the list for building of the header of the table.
	 In case of the list has only one string the header will build using default column name initiliazed in defaultColumnName string + coulumn count.
	 In case of the empty list the method will return null string.
	 */
	@RequestMapping({"/test_details"})
	public String processRequestTestDetails(String test_ID, Model model){
		/*boolean errorlevel = false;
		String res = "";
		String bes_response = null;
		int test_ID_ = 0;
		try {
			test_ID_ = Integer.parseInt(test_ID);
		}catch(NumberFormatException e){
			errorlevel = true;
		}
		if(!errorlevel)
			bes_response = companyService.getTestsResultsForTestID(companyName, test_ID_);

		if (errorlevel){
			res = "ErrorMessage";
		}else{
			res = CompanyTestsResutlsHandler.compileToViewTestDetails(bes_response);
		}
		model.addAttribute("res", res);
*/
		return "CompanyTestDetails";
	}
// -----------------END  Use case Viewing test results-----------------

}

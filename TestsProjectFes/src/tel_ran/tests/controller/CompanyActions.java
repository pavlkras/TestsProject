package tel_ran.tests.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import tel_ran.tests.services.interfaces.ICompanyActionsService;
import tel_ran.tests.controller.CompanyTestsResutlsHandler;

@Controller
@Scope("session") /*session timer default = 20min*/
@RequestMapping({"/","/CompanyActions"})
public class CompanyActions {
	int company_id;
	@Autowired
	ICompanyActionsService companyService;	

	@RequestMapping({"/CompanyActions"})
	public String signIn(){
		return "CompanySignIn";
	}
	// ALEX FOOX	
		@RequestMapping({"/CompanySignInAction"})
		public String home(String username,String password ) {
			return "home_page";
		}
	//Use case Company Login
	/*Pre-conditions:
1.	The system comprising of front-end controller and back-end Database service (aka the System) is running up
2.	The user is registered in the System
Login Normal Flow: 
1.	The user enters a company username and password. 
2.	The user presses a submit button
3.	The System moves to a home page for the specific company. Alternative flows: Wrong Company Username, Wrong Password
3.1.1.1.	Wrong Company Username
Pre-Conditions:
1.	The user enters wrong username
Wrong Username Flow:
1.	The alert window appears with message: “Company with <username> is not registered. Type the right company username or  go to registration”
2.	Returns to the Login
3.1.1.2.	Wrong Password
Pre-Conditions:
1.	The user enters wrong password
Wrong Password Flow:
1.	The alert window appears with message: “Wrong password. Type the right password”
2.	Returns to the Login
	 */
	//
	//
	//Use Case Company Sign up 3.1.2
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
1.	The alert window appears with message: “Wrong password confirmation. Type the same password”
2.	Returns to the Registration Flow with #6. That is all filled fields except password confirmation should remain filled
3.1.2.4.	Company Registered
Pre-Conditions:
1.	The user enters company username that already has been registered
User Registered Flow:
1.	The alert window appears with message: “The company <username> already registered. Type another company username or go to Login with this one”
2.	Returns to  the Registration Flow

	 */
	// ALEX FOOX	
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
	//
	//
	//Use case Ordering Test 3.1.3
	//
	/*Pre-Conditions:
1.	The System is running up
2.	The user is signed in for the proper company
Normal Flow:
1.	The user selects a test category
2.	The user selects  the  test details (complexity level)
3.	The user enters an identity number of the tested person 
4.	The user enters the person personal data (First Name, Family Name)
5.	The System generates a test and a link for that test
6.	The System presents the link for performing the test in the control mode 
..............................
...............................
..............................
...............................
..............................
...............................
..............................
...............................
..............................
...............................
..............................
...............................
..............................
...............................
..............................
...............................
..............................
...............................
..............................
...............................
..............................
...............................
..............................
...............................
	 */
	//Use case Viewing test results
	/*
3.1.4.	Viewing test results
Pre-Conditions:
1.	The System is running up
2.	The user is signed in for the proper company
Normal Flow:
1.	The user selects the details for a test results query. There should be the following queries:
•	All test results
•	Test results for the specified time period. There should be start date and end date. The selection should be done using standard calendar gadget 
•	Test results for the specified person identity number
2.	The user fills the proper data for the selected test
3.	The System runs the query
4.	 The System shows the list of the test results items. Item should contain: 
•	Personal data (First and Family names)
•	Test details (category, name, etc.)
•	Test date
5.	The user selects an item
6.	The system shows the following results:
•	Test duration 
•	Number of the questions
•	Complexity level
•	Number of the right answers with the percentage 
•	Number of the wrong answers with the percentage
•	5 photos made during the test

	 */

	// IGOR
	@RequestMapping({"/create_request"})
	public String createRequest(){
		return "CompanyTestsResultsStartPage";
	}
	 
	// IGOR
	@RequestMapping({"/process_request"})
	public String processRequestTestsCommon(String request_type, String date_from, String date_until, String user_id, Model model){
		
		boolean errorlevel = false;
		String res = "";
		List<String> bes_response = null;
		
		if(request_type.equals("all")){
			bes_response = companyService.getTestsResultsAll(company_id);
		
		}else if(request_type.equals("time_interval")){
			Date date_from_ = null;
			Date date_until_ = null;
			
			try {
				DateFormat df = new SimpleDateFormat ("MM/dd/yyyy"); 
				date_from_ = df.parse(date_from);
				date_until_ = df.parse(date_until);
			} catch (ParseException e) {
				errorlevel = true;
			}
			if(!errorlevel)
				bes_response = companyService.getTestsResultsForTimeInterval(company_id, date_from_, date_until_);
			
		}else if(request_type.equals("user_specific")){
			int user_ID = 0;
			try {
				user_ID = Integer.parseInt(user_id);
			}catch(NumberFormatException e){
				errorlevel = true;
			}
			if(!errorlevel)
				bes_response = companyService.getTestsResultsForPersonID(company_id, user_ID);
		}
		
		if (errorlevel){
			res = "ErrorMessage";
		}else{
			res = CompanyTestsResutlsHandler.compileToViewTestCommon(bes_response);
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
		
		boolean errorlevel = false;
		String res = "";
		String bes_response = null;
		int test_ID_ = 0;
		
		try {
			test_ID_ = Integer.parseInt(test_ID);
		}catch(NumberFormatException e){
			errorlevel = true;
		}
		if(!errorlevel)
			bes_response = companyService.getTestsResultsForTestID(company_id, test_ID_);
	
		if (errorlevel){
			res = "ErrorMessage";
		}else{
			res = CompanyTestsResutlsHandler.compileToViewTestDetails(bes_response);
		}
		model.addAttribute("res", res);
		
	return "CompanyTestDetails";
	}
	

	
	
	/*
	 *
	 *
=======
	/*	*********** Stub method for test list generation ********************
	static List<String> stubList(int rowsCount, int colunmsCount, String delimiter){
	List<String> res = new ArrayList<String>();
	String row ="";
		for(int i = 0; i < rowsCount; i++){
			for (int j = 0; j < colunmsCount; j++){
				row += ("Row " + i + " " + "Column " + j + delimiter);
			}
			res.add(row);
			row="";
		}
		return res;
	}
>>>>>>> origin/master
	 *
	 *   ***************** Sample method for test *********************
	@RequestMapping({"/"})
	String calcProccessing(String line, Model model){
		int rowsCount = 10;
		int colunmsCount = 7;
		String delimiter = "%";
		List <String> tableData = stubList(rowsCount, colunmsCount, delimiter);
		
		String res = compile_to_view(tableData);

		model.addAttribute("Result", res);
		return "viewResult";
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 */



}

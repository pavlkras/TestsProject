package tel_ran.tests.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@Scope("session") /*session timer default = 20min*/
@RequestMapping({"/","/CompanyActions"})
public class CompanyActions {
	int company_id;
	@Autowired
	//ICompanyActionsService companyService;
@RequestMapping({"/CompanyActions"})
public String signIn(){
	return "CompanySignIn";
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

	/*
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

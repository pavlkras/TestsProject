package tel_ran.tests.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import tel_ran.tests.services.interfaces.IPersonalActionsService;

@Controller
@Scope("session")
@RequestMapping({"/","/PersonalActions"})
public class PersonalActions {
	int personId;
	@Autowired
	IPersonalActionsService personalService;
	@RequestMapping({"/PersonalActions"})
	public String signIn(){
		return "PersonalSignIn";
	}
//use case Personal Login 3.2.1
	/*3.2.1.	Login
	Pre-conditions:
	3.	The System is running up
	4.	The user is registered in the System
	Login Normal Flow: 
	4.	The user enters a username and password. 
	5.	The user presses a submit button
	6.	The System moves to a home page for the specific user. Alternative flows: Wrong Username, Wrong Password
	3.2.1.1	Wrong Username
	Pre-Conditions:
	2.	The user enters wrong username
	Wrong Username Flow:
	3.	The alert window appears with message: “User with <username> is not registered. Type the right username or  go to registration”
	4.	Returns to the Login
	3.2.1.2	Wrong Password
	Pre-Conditions:
	2.	The user enters wrong password
	Wrong Password Flow:
	3.	The alert window appears with message: “Wrong password. Type the right password”
	4.	Returns to the Login

*/
	//use case 3.2.2. Personal sign up
	/*Pre-conditions:
		3.	The System is running up
		4.	The user is not registered in the System
		Registration Normal Flow:
		9.	The user enters a username
		10.	The user enters an email
		11.	The user enters password
		12.	The user enters password confirmation. Alternative flow: Wrong Password Confirmation
		13.	The user presses a submit button
		14.	The System moves to Login page. Alternative flow: User Registered 
		3.2.2.1.	Wrong Password Confirmation
		Pre-Conditions:
		2.	The user enters wrong password confirmation
		Wrong Password Confirmation Flow
		3.	The alert window appears with message: “Wrong password confirmation. Type the same password”
		4.	Returns to the Registration Flow with #4. That is all fields except password confirmation should remain filled
		3.2.2.2.	User Registered
		Pre-Conditions:
		2.	The user enters username that already has been registered
		User Registered Flow:
		3.	The alert window appears with message: “The <username> already registered. Type another username or go to Login with this one”
		4.	Returns to  the Registration Flow
*/
	//Use case 3.2.3 Performing Test - Trainee Mode
	/*Pre-conditions:
		1.	The System is running up
		2.	The user is signed in
		3.	There are prepared test questions
		Normal Flow:
		1.	The user selects a test category
		2.	The system shows maximal number of the questions in the selected category
		3.	The user enters number of questions from 1 up to the maximal number. Alternative Flow: Wrong Questions Number
		4.	The System shows a question with several answers. (American method)
		5.	The user selects an answer
		6.	The items 4 and 5 are repeated for the selected number of the questions
		7.	The System shows the test results containing the following:
		•	Test duration 
		•	List of the questions with marking of right or wrong


		3.2.3.1.	Wrong Questions Number
		Pre-conditions:
		1.	During running performing test in the trainee mode the user entered wrong number of the questions
		Flow:
		1.	The alert window appears with the message: “Wrong number of the questions. Type the right number that is not more than maximal questions number”
		2.	Returns to the Normal Flow of the Performing Test in the Trainee Mode #3
............................
.............................
...........................
................................
.............................
*/
	//use case Performing Test - Control Mode
	/*Pre-conditions:
		1.	The user has the link for the test given by a company
		2.	The user uses any Hardware with the following:
		•	Internet connection 
		•	Running Internet Browser 
		•	Enabled Web camera
		Normal Flow:
		1.	The user types the link in the Internet Browser
		2.	The System launches pre-generated test with random capturing photos from the PC’s Web camera. Alternative Flow: Web Camera Disabled
		3.	The System shows a question with several answers. (American method)
		4.	The user selects an answer
		5.	The System captures photo in random time periods.  
		6.	The items 3 - 4 are repeated for all the questions of the generated test. 
		3.2.4.1.	Web Camera Disabled
		Pre-Conditions:
		1.	Performing test in the control mode
		2.	Web camera either doesn’t exist or is disabled
		Flow:
		1.	Stop test with the alert message “Web camera should be enabled. Please try again”
...........................................
...........................................
...........................................
...........................................
...........................................
...........................................
...........................................
...........................................
...........................................
...........................................
...........................................
...........................................
...........................................
...........................................
...........................................
...........................................

*/
}

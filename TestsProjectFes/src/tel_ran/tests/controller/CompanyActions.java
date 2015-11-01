package tel_ran.tests.controller;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import tel_ran.tests.services.common.IPublicStrings;
import tel_ran.tests.services.interfaces.ICommonAdminService;
import tel_ran.tests.services.interfaces.ICompanyActionsService;
import tel_ran.tests.users.Visitor;


@Controller
@Scope("session") /*session timer default = 20min*/
@RequestMapping({"/","/CompanyActions"})
public class CompanyActions extends AbstractAdminActions implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	// --- private fields 
	private	String companyName;
	private long companyId = -1;
	
	RestTemplate restTemplate =  new RestTemplate();


//	@Autowired
//	private	IMaintenanceService maintenanceService;	
	// --- private fields 
	// -- getters and setters
	
	public CompanyActions() {
		super();				
	}
	
	@Autowired
	@Qualifier("companyService")
	public void setObject(ICommonAdminService companyService) {
		adminService = companyService;
	}
	
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	
	

	/*-------------Use case Ordering Test 3.1.3-------------
Pre-Conditions:
1.	The System is running up
2.	The user is signed in for the proper company
Normal Flow:
1.	The user selects a test category
2.	The user selects  the  test details (complexity level)
3.	The user enters an identity number of the tested person 
4.	The user enters the person personal data (First Name, Family Name , E-mail)
5.	The System generates a test and a link for that test
6.	The System presents the link for performing the test in the control mode  */
	//


	 

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

	@RequestMapping({"/view_results"})
	public String viewResults(Model model){
		//Code for testing
		//		companyId = 8;
		String page = "company/ErrorPage";
		if(companyId != -1){
			String token = ((ICompanyActionsService)adminService).encodeIntoToken(companyId);
			model.addAttribute("token", token);
			page = "company/CompanyViewTestsResults";
		}
		return page;
	}	
	// -----------------END  Use case Viewing test results-----------------	
	


//	/**
//	 * CREATE questions by auto generation
//	 * @param category = list of metaCategories
//	 * @param nQuestions = total number of questions to create
//	 * @param levelOfDifficulty = list of dif.Level 
//	 * @param model
//	 */
//	@RequestMapping({"/addQuestionsFromResourses"})
//	public String moduleForBuildingQuestions(String category, String nQuestions, Model model) {		
//		String path = "company/CompanyOtherResourses";
//		return super.moduleForBuildingQuestions(category, nQuestions, model, path);// return too page after action
//	}

	
	/**
	 * List of auto-metaCategory for page with auto generation
	 * 
	 */
	
	
	
//	protected void AutoInformationTextHTML(String string) {
//		autoGeneratedInformationTextHTML.append(string);
//	}
//	
	
	
	
	// ----------------------------- UPDATE PAGE ------------------------- //
	

		
	



	
}

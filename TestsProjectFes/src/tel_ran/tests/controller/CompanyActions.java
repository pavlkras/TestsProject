package tel_ran.tests.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import tel_ran.tests.services.interfaces.ICompanyActionsService;
import tel_ran.tests.services.interfaces.IMaintenanceService;


@Controller
@Scope("session") /*session timer default = 20min*/
@RequestMapping({"/","/CompanyActions"})
public class CompanyActions {
	private static int N_ROWS_CATEGORY = 10;
	String companyName;
	@Autowired
	ICompanyActionsService companyService;

	RestTemplate restTemplate =  new RestTemplate();

	@Autowired
	IMaintenanceService maintenanceService;	
	// Action Re-mapping for Send Ajax request to DB if equal  user and priority level, and set: flAuthorization=true;
	@RequestMapping({"/CompanyActions"})
	public String signIn(){			
		return "CompanySignIn";
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
		int counter = 0;
		if(IfExistCompany){
			boolean ress = companyService.CompanyAuthorization(companyName, password);
			if(ress ){ 				
				StringBuffer categoryHtmlText = new StringBuffer();
				List<String> resultCategory = maintenanceService.getAllCategoriesFromDataBase();
				for(String catBox:resultCategory){					
					if(counter < N_ROWS_CATEGORY){
						categoryHtmlText.append("&nbsp;&nbsp;&nbsp;"+catBox + "-<input type='checkbox' name='category' value='" + catBox + "' />");
						counter++;
					}else{
						counter = 0;
						categoryHtmlText.append("<br>");
					}
				}
				model.addAttribute("categoryFill", categoryHtmlText.toString());
				result = "CompanyGenerateTest";
				this.companyName = companyName; 
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
	@RequestMapping({"/search_form"})
	public String query() {
		return "Company_search_form";
	}

	@RequestMapping({"/query_processing"})
	public String queryProcessing(String jpaStr, Model model) {
		String[] result = companyService.getAnySingleQuery(jpaStr);
		StringBuffer buf = new StringBuffer();
		for (String str : result)
			buf.append(str).append("<br>");
		model.addAttribute("myResult", buf.toString());
		return "Company_search_form";
	}

	@RequestMapping({"/companyadd"})
	public String addCompany() {
		return "Company_add_form";
	}

	@RequestMapping({"/add_processing"})
	public String addProcessing(String C_Name,String C_Site, String C_Specialization,String C_AmountEmployes,String C_Password,Model model) {

		boolean flag = companyService.createCompany(C_Name, C_Site, C_Specialization, C_AmountEmployes, C_Password);
		if(flag==true){
			model.addAttribute("myResult", "<H1>Company Added Success</H1>");
			return "Company_search_form";
		}
		else{
			model.addAttribute("myResult", "<H1>This Company - "+C_Name+".  Already Exist!</H1>");
			return "Company_search_form";
		}
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
4.	The user enters the person personal data (First Name, Family Name , E-mail)
5.	The System generates a test and a link for that test
6.	The System presents the link for performing the test in the control mode  */
	//
	@RequestMapping({"/add_test"})
	public String createTest(String category,String level,int personId,String personName, String personSurname,String personEmail, Model model) {
		maintenanceService.setAutorization("0","0");
		List<Long> listIdQuestions = maintenanceService.getUniqueSetQuestionsForTest(category, level, (long) 15);

		int personId1 = companyService.createPerson(personId, personName, personSurname,personEmail);
		long idTest = companyService.createIdTest(listIdQuestions,personId1);
		String link = "http://localhost:8080/TestsProjectFes/jobSeeker_test_preparing_click_event?" + idTest;        
		boolean flagMail = sendEmail(link,personEmail);
		if(flagMail){
			model.addAttribute("myResult", link +"<br>" + "<H1>message was sent successfully</H1>");    	
		}else{
			model.addAttribute("myResult", "<H1>Error while sending message</H1>");
		}
		return "Company_TestLink";
	}	 
	//------------END  Use case Ordering Test 3.1.3-------------
	private boolean sendEmail(String link, String personEmail) {
		boolean result = false;
		/*
		final String username = "senderurltest@gmail.com";
		final String password = "sender54321.com";
		String subject = "Email from HR";
		String text = "Press for this link :  "+ link;

		try {

			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");

			Session session = Session.getDefaultInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			Message message = new MimeMessage(session);		            
			message.setFrom(new InternetAddress(username));		            
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(personEmail));		            
			message.setSubject(subject);		            
			message.setText(text);
			session.setDebug(true);

			Transport.send(message);
			result = true;

		}  catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();

		}*/
		return result;
	}

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
		//TODO Create method of coding companyName & currentTime into token for BES authorize
		// or find another authorize method for the REST service
		//model.addAttribute("token", "ef");
		List<String> res = restTemplate.getForObject("http://localhost:8080/TestsProjectBes/view_results_rest/all_tests_results/"+ companyName, List.class);
		return "CompanyViewTestsResults";
	}
	// -----------------END  Use case Viewing test results-----------------

}

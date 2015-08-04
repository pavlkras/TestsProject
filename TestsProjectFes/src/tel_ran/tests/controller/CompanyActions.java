package tel_ran.tests.controller;
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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import tel_ran.tests.services.common.IPublicStrings;
import tel_ran.tests.services.interfaces.ICompanyActionsService;


@Controller
@Scope("session") /*session timer default = 20min*/
@RequestMapping({"/","/CompanyActions"})
public class CompanyActions {
	// --- private fields 
	private	String companyName;
	private long companyId = -1;
	private static String PATH_ADDRESS_TO_SERVICE = "";
	RestTemplate restTemplate =  new RestTemplate();
	@Autowired
	private ICompanyActionsService companyService;
//	@Autowired
//	private	IMaintenanceService maintenanceService;	
	// --- private fields 
	// -- getters and setters
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	////
	// Action Re-mapping for Send Ajax request to DB if equal  user and priority level, and set: flAuthorization=true;
	@RequestMapping({"/CompanyActions"})
	public String signIn(){		
		return "company/CompanySignIn";  
	}

	// IGOR Action Re-mapping
	@RequestMapping({"/create_request"})
	public String createRequest(){
		return "company/CompanyTestsResultsStartPage";
	}
	
	// IGOR Action Re-mapping
	@RequestMapping({"/company_main"})
	public String loginSucceessCompany(){
		return "company/Company_main";
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
	public String loginProcessing(String companyName, String password, Model model){
		////// Method getCompanyByName(companyName) - return companyId;
		///	boolean IfExistCompany
		companyId = companyService.getCompanyByName(companyName);
		String result;		
		////if(IfExistCompany){
		if(companyId>0){
			boolean ress = companyService.setAutorization(companyName, password);
			if(ress){ 				
				result = "company/Company_main";
				this.setCompanyName(companyName); 
			}else{
				result = "company/CompanySignIn";
				model.addAttribute("result", " This password not variable try again " );
			}
		}else {	       			
			result = "company/CompanySignIn";
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
		return "company/Company_search_form";
	}

	@RequestMapping({"/query_processing"})
	public String queryProcessing(String jpaStr, Model model) {
		String[] result = companyService.getAnySingleQuery(jpaStr);
		StringBuffer buf = new StringBuffer();
		for (String str : result)
			buf.append(str).append("<br>");
		model.addAttribute("myResult", buf.toString());
		return "company/Company_search_form";
	}
	////
	@RequestMapping({"/companyadd"})
	public String addCompany() {
		return "company/Company_add_form";
	}
	
	
	@RequestMapping({"/testGeneration"})
	public String testGeneration(Model model) {
		StringBuffer categoryHtmlText = new StringBuffer();
		List<String> resultCategory = companyService.getAllMetaCategoriesFromDataBase();
		categoryHtmlText.append("<table class='table_ind'><tr><th>Category of Question:</th><th  colspan='2'>Level of difficulty</th></tr>");
		for(String catBox:resultCategory){					
			categoryHtmlText.append("<tr class='tr_ind'>");
			categoryHtmlText.append("<td>").append(catBox).append(":</td>");
			categoryHtmlText.append("<td><input class='category' type='checkbox' name='category' value='").
				append(catBox).append("' /></td>");
			categoryHtmlText.append("<td><select name='level_num' disabled>").
				append("<option value='1'>1</option>").append("<option value='2'>2</option>").
				append("<option value='3'>3</option>").append("<option value='4'>4</option>").
				append("<option value='5'>5</option>").append("</select></td></tr>");						
		}
		categoryHtmlText.append("</table>");
		model.addAttribute("categoryFill", categoryHtmlText.toString());
//		result = "company/CompanyGenerateTest";
		this.setCompanyName(companyName); 
		return "company/CompanyGenerateTest";
	}
	
	//// method response JSON, Ajax on company add page 
	@RequestMapping(value="/add_processing_ajax",method=RequestMethod.POST)
	public @ResponseBody JsonResponse ajaxRequestStream(HttpServletRequest request) {   
		String name_company = request.getParameter("name");
		JsonResponse res = new JsonResponse(); 
		long tRes = companyService.getCompanyByName(name_company);
		if(tRes == -1){    			
			res.setStatus("SUCCESS");
			res.setResult(name_company); 			
		} else{
			res.setStatus("ERROR");	
			res.setResult(name_company); 
		}
		return res;
	}
	//// static resurse class for JSON, Ajax on company add page 
	class JsonResponse {
		private String status = null;
		private Object result = null;
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public Object getResult() {
			return result;
		}
		public void setResult(Object result) {
			this.result = result;
		}
	}
	////
	@RequestMapping({"/add_processing"})
	public String addProcessing(String C_Name,String C_Site, String C_Specialization,String C_AmountEmployes,String C_Password,Model model) {
		boolean flag = false;		
		try{
			flag = companyService.CreateCompany(C_Name, C_Site, C_Specialization, C_AmountEmployes, C_Password);
		}catch(Throwable th){
			th.printStackTrace();
			System.out.println("catch creation company FES");
		}

		if(flag){
			model.addAttribute("myResult", "<H1>Company Added Success</H1>");
			return "company/Company_search_form";
		}
		else{
			model.addAttribute("myResult", "<H1>This Company - "+C_Name+".  Already Exist!</H1>");
			return "company/Company_search_form";
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
	/**
	 * CREATE NEW TEST for person
	 * 
	 * @param metaCategory 
	 * @param category1 - for programming language
	 * @param level_num
	 * @param personId
	 * @param personName
	 * @param personSurname
	 * @param personEmail 
	 * @param selectCountQuestions
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping({"/add_test"})
	public String createTest(String category, String category1, String level_num, String personId, 
			String personName, String personSurname,String personEmail, String selectCountQuestions, 
			Model model, HttpServletRequest request) {	
		
		String url = request.getRequestURL().toString();
		PATH_ADDRESS_TO_SERVICE = url.replace("add_test", "jobSeeker_test_preparing_click_event");
		////		
		int counterOfQuestions = Integer.parseInt(selectCountQuestions);
		System.out.println("level_num--"+level_num);//-------------------------------sysout
		System.out.println("category--"+category);//-------------------------------sysout
		if(category1!=null)
			System.out.println("language -- " + category1);		
				
		String password = getRandomPassword();
		int result = companyService.createTestForPersonFull(category, category1, level_num, selectCountQuestions, 
				Integer.parseInt(personId), personName, personSurname, personEmail, password);
		
		String link = null;
		
		if(result==0) {
			link = PATH_ADDRESS_TO_SERVICE + "?" + password; // -------------------------------------------------------------------------------------TO DO real address NOT text in string !!!
			if(!sendEmail(link,personEmail))
				result = 5;
		}
		
//		List<Long> listIdQuestions = companyService.createSetQuestions(metaCategory, category1, level_num, counterOfQuestions);
//		int personID = companyService.createPerson(Integer.parseInt(personId), personName, personSurname,personEmail);
//		boolean isCreated = companyService.CreateTest(listIdQuestions, personID, password, metaCategory, level_num);	//------------ TO DO levels change !!	add company name for
		////
				
			String messageText;
			switch(result) {			
				case 0 : 
					messageText = "<a href='" + link + "'><h2><b>Test link</b></h2></a><br>" + "<H1>" + 
							IPublicStrings.CREATE_TEST_ERROR[0] + "</H1>";
					break;
				case 1 :
				case 2 :
				case 3 :
				case 5 :
					messageText = "<H1>" + IPublicStrings.CREATE_TEST_ERROR[result] + "</H1>";
					break;
				default :
					messageText = "<H1>" + IPublicStrings.CREATE_TEST_ERROR[4] + "</H1>";			
			}
			
		model.addAttribute("myResult", messageText);	
			
		return "company/Company_TestLink";
	}

	private String getRandomPassword() {
		String uuid = UUID.randomUUID().toString();		
		return uuid	;
	}	 
	//------------END  Use case Ordering Test 3.1.3-------------
	private boolean sendEmail(String link, String personEmail) {
		boolean result = false;
		final String username = "senderurltest@gmail.com";
		final String password = "sender54321.com";
		String subject = "Email from HR";
		String text = "\nPress for this link :  "+ link + "\n";

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

		}
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
		//Code for testing
		//		companyId = 8;
		String page = "company/ErrorPage";
		if(companyId != -1){
			String token = companyService.encodeIntoToken(companyId);
			model.addAttribute("token", token);
			page = "company/CompanyViewTestsResults";
		}
		return page;
	}	
	// -----------------END  Use case Viewing test results-----------------	
}

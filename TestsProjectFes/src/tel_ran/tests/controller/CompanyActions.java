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
	private static String PATH_ADDRESS_TO_SERVICE = "";
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
	
	@RequestMapping({"/testGeneration"})
	public String testGeneration(Model model) {
		StringBuffer categoryHtmlText = new StringBuffer();
		List<String> resultCategory = adminService.getAllMetaCategoriesFromDataBase();
		categoryHtmlText.append("<table class='table_ind'><tr><th colspan='2'>Category of Question:</th><th>Level of difficulty</th></tr>");
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
		model.addAttribute("userQuestions", adminService.getAllQuestionsList(true, null, null));
		
//		result = "company/CompanyGenerateTest";
//		this.setCompanyName(companyName); 
		return "company/CompanyGenerateTest";
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
			String personName, String personSurname,String personEmail, String selectCountQuestions, String questionsId,
			Model model, HttpServletRequest request) {	
		
		String url = request.getRequestURL().toString();
		PATH_ADDRESS_TO_SERVICE = url.replace("add_test", "jobSeeker_test_preparing_click_event");
		////			
		System.out.println("level_num--"+level_num);//-------------------------------sysout
		System.out.println("category--"+category);//-------------------------------sysout
		if(category1!=null)
			System.out.println("language -- " + category1);		
		List<Long> questionsIdList = null;
		if(questionsId!=null) {
			questionsIdList = new LinkedList<Long>();
			String[] qIds = questionsId.split(",");
			for(String q : qIds) {
				questionsIdList.add(Long.parseLong(q));
			}
		}
		
		String password = getRandomPassword();
		int result = ((ICompanyActionsService)adminService).createTestForPersonFullWithQuestions(questionsIdList, category,
				category1, level_num, selectCountQuestions, Integer.parseInt(personId), personName, personSurname, personEmail, password);				
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
					messageText = "<H1>" + IPublicStrings.CREATE_TEST_ERROR[result] + "</H1>";
					break;
				case 5 :					
					messageText = "<a href='" + link + "'><h2><b>Test link</b></h2></a><br>" + "<H1>" + 
							IPublicStrings.CREATE_TEST_ERROR[result] + "</H1>";
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
	
	
	
	protected void AutoInformationTextHTML(String string) {
		autoGeneratedInformationTextHTML.append(string);
	}
	
	
	
	
	// ------------------------- ADDING QUESTION. MANUAL --------- //
	
	@RequestMapping({ "/company_add" })
	public String addingPage(@ModelAttribute Visitor visitor) {	
		
		return super.addingPage("company/CompanyCreateQuestion"); // - Page = MaintenanceAddingPage
	}	
	//temporary!!!!
	//TODO - problem with double list in creating questions
	@RequestMapping({ "/company_add_" })
	public String addingPage_() {	
		clearStringBuffer();
		AutoInformationTextHTML(checkUsersCategoryBoxTestHTML(autoGeneratedInformationTextHTML));
		return "company/CompanyCreateQuestion"; // - Page = MaintenanceAddingPage
	}
	
	@RequestMapping(value = "/company_add_questions" , method = RequestMethod.POST)
	@ResponseBody
	public String addProcessingPage(String questionText, String descriptionText, String codeText,
			String  category1, String metaCategory, String category2, String  compcategory, String levelOfDifficulty, 
			String fileLocationLink, String correctAnswer, String numberAnswersOnPicture, 
			String at1, String at2, String at3, String at4,  Model model)
	{	
		
		
		return super.AddProcessingPage(questionText, descriptionText, 
				codeText, category1, metaCategory, category2, compcategory, levelOfDifficulty, 
				fileLocationLink, correctAnswer, numberAnswersOnPicture, at1, at2, at3, at4, model, "company/CompanyCreateQuestion"); // - Page = MaintenanceAddingPage
			
	}
	
	// ----------------------------- UPDATE PAGE ------------------------- //
	
	@RequestMapping({ "/company_questions_update" })
	public String updatePage(String view_mode, Model model) {

		clearStringBuffer();				
		AutoInformationTextHTML(buildingCategory1CheckBoxTextHTML());
		String res;	
		if(view_mode==null) {
			res = adminService.getAllQuestionsList(true, null, null);
		} else {
		switch(view_mode) {
		
		case "all": 
			res = adminService.getAllQuestionsList(null, null, null);
			break;
		case "user":
			res = adminService.getAllQuestionsList(true, null, null);
			break;
		case "auto" :
			res = adminService.getAllQuestionsList(false, null, null);
			break;
		default:
			res = adminService.getAllQuestionsList(true, null, null); 
		
		}
		}
		
		
		model.addAttribute(RESULT, res);		
		
		String path = "company/CompanyUpdatePage";
		return path;
//		return super.updatePage(path, model);
	}
		
	@RequestMapping({"/question_see" + "/{questId}"})
	public String seeQuestion(@PathVariable long questId) {
								
		return adminService.getJsonQuestionById(questId);
	}



	
}

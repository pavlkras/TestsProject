package tel_ran.tests.controller;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import tel_ran.tests.model.Answer;
import tel_ran.tests.model.Question;
import tel_ran.tests.model.Test;
import tel_ran.tests.services.interfaces.IMaintenanceService;
import tel_ran.tests.services.interfaces.IPersonalActionsService;

@Controller
@Scope("session")
@RequestMapping({"/","/PersonalActions"})
public class PersonalActions {
	@Autowired
	IPersonalActionsService personalService; 
	@Autowired
	IMaintenanceService maintenanceService;	
	@RequestMapping({"/"})
	public String Index(){     return "UserSignIn";       }// this mapping to index page !!!!!! for all users and all company !!!!
	//--------------------- fields of this class ---------------------------
	String personId = "";
	String personPassword = "";
	String testId  = "";	
	private String categoryName = null;
	private String level = null;
	private Test mTest = null;
	private List<Question> mQstnList = null;
	private List<Answer> mAnswList = null;
	private int counter = 0;
	private List<Answer> receivedAnswers = new ArrayList<Answer>();
	/*
	 3.2. Personal Actions 
	3.2.1. Login
	Pre-conditions:
	3. The System is running up
	4. The user is registered in the System
	Login Normal Flow: 
	4. The user enters a username and password. 
	5. The user presses a submit button
	6. The System moves to a home page for the specific user. Alternative flows: Wrong Username, Wrong Password
	3.1.1.1. Wrong Username
	Pre-Conditions:
	2. The user enters wrong username
	Wrong Username Flow:
	3. The alert window appears with message: “User with <username> is not registered. Type the right username or  go to registration”
	4. Returns to the Login
	3.1.1.1. Wrong Password
	Pre-Conditions:
	2. The user enters wrong password
	Wrong Password Flow:
	3. The alert window appears with message: “Wrong password. Type the right password”
	4. Returns to the Login
	 */
	//// ------  login case --------- // BEGIN //
	@RequestMapping(value = "/login_action", method = {RequestMethod.POST, RequestMethod.GET})
	public String login_action(String userEmail, String password, HttpServletRequest request, Model pageModel) {
		String outPage = "UserSignIn";
		String sign_up = request.getParameter("sign_up");
		if(sign_up != null){
			outPage = "UserRegistration";
		}else{
			////		
			boolean getUser = personalService.IsUserExist(userEmail, password);

			if (getUser) {					
				outPage = "UserAccountPage";
			} else{
				pageModel.addAttribute("logedUser", "wrong password");
			}
		}
		return outPage;		
	}
	/*
	3.2.2. Registration (Sign up)
	Pre-conditions:
	3. The System is running up
	4. The user is not registered in the System
	Registration Normal Flow:
	9. The user enters a username
	10. The user enters an email
	11. The user enters password
	12. The user enters password confirmation. Alternative flow: Wrong Password Confirmation
	13. The user presses a submit button
	14. The System moves to Login page. Alternative flow: User Registered 
	3.1.1.1. Wrong Password Confirmation
	Pre-Conditions:
	2. The user enters wrong password confirmation
	Wrong Password Confirmation Flow
	3. The alert window appears with message: “Wrong password confirmation. Type the same password”
	4. Returns to the Registration Flow with #4. That is all fields except password confirmation should remain filled
	3.1.1.1. User Registered
	Pre-Conditions:
	2. The user enters username that already has been registered
	User Registered Flow:
	3. The alert window appears with message: “The <username> already registered. Type another username or go to Login with this one”
	4. Returns to  the Registration Flow
	 */ 
	////-----------  Registration case -------------- // BEGIN //
	@RequestMapping(value = "/signup_action", method = RequestMethod.POST)
	public String signup_action(String firstname, String lastname,String email, String password, Model model) {
		String outPage = "UserSignIn";
		String[] res = personalService.GetUserByMail(email);
		if (res == null) {				
			String[] userArgs = { firstname, lastname, email,  password};			
			personalService.AddingNewUser(userArgs);
			outPage = "UserAccountPage";
		}
		//
		return outPage;
	}
	////------  Registration case -- // END //
	/*
	3.2.3. Performing Test – Trainee Mode
	Pre-conditions:
	1. The System is running up
	2. The user is signed in
	3. There are prepared test questions
	Normal Flow:
	1. The user selects a test category
	2. The system shows maximal number of the questions in the selected category
	3. The user enters number of questions from 1 up to the maximal number. Alternative Flow: Wrong Questions Number
	4. The System shows a question with several answers. (American method)
	5. The user selects an answer
	6. The items 4 and 5 are repeated for the selected number of the questions
	7. The System shows the test results containing the following:
	Test duration 
	List of the questions with marking of right or wrong
	3.1.1.1. Wrong Questions Number
	Pre-conditions:
	1. During running performing test in the trainee mode the user entered wrong number of the questions
	Flow:
	1. The alert window appears with the message: “Wrong number of the questions. Type the right number that is not more than maximal questions number”
	2. Returns to the Normal Flow of the Performing Test in the Trainee Mode #3

	 */
	////
	@RequestMapping("/result_mod")
	public String result_mode(@PathVariable int id, HttpServletRequest request,	Model pageModel) {
		return "UserTraineeModeCreationTest_1";
	}
	////
	@RequestMapping({"add_questions_count"})
	public String addCategoryLevelMaxQuestionsNumber(String catName, String levelName, Model model){
		mTest = new Test();
		categoryName = catName;
		level = levelName;

		String questionsCountByCategoryLevel = personalService.getMaxCategoryLevelQuestions(categoryName, level);
		model.addAttribute("catName", categoryName);
		model.addAttribute("levelName", level);
		model.addAttribute("questionsCountByCategoryLevel", questionsCountByCategoryLevel);
		mTest.setCategoryName(categoryName);
		mTest.setLevel(Integer.parseInt(level));
		return "UserTraineeModeCreationTest_2";
	} 	
	////
	@RequestMapping({ "/test_run" })
	public String test_run(String personalqnumber, HttpServletRequest request, Model pageModel) {
		//setting received number of question in Test
		if(personalqnumber != null){
			mTest.setqNum(Integer.parseInt(personalqnumber));
			//test creation
			String xmlStr = personalService.getTraineeQuestions(mTest.getCategoryName(), mTest.getLevel(), mTest.getqNum());
			getDataFromXML(xmlStr);
		}
		////
		String answer = request.getParameter("answer");
		Question quest = mQstnList.get(counter);
		List<Answer> answersList = getAnswersList(quest);
		if (answer != null) {
			for (Answer ans : answersList) {
				if (ans.getAnswerText().compareTo(answer) == 0) {
					receivedAnswers.add(ans);
					break;
				}
			}
			counter++;
		}

		if (counter >= mQstnList.size()) {
			String durTime = getTestDurationTime(mTest);
			mTest = getTestResults(receivedAnswers, mTest);
			pageModel.addAttribute("time", durTime);
			pageModel.addAttribute("resultsList", mTest.getTestResultList());
			pageModel.addAttribute("wrongAnswers",
					mTest.getWrongAnswerCounter());
			pageModel.addAttribute("rightAnswers",
					mTest.getRightAnswerCounter());
			clearTest();
			return "UserTestResultPage";
		}
		quest = mQstnList.get(counter);
		answersList = getAnswersList(quest);
		List<String> strAnswers = new ArrayList<String>();
		for (int i = 0; i < answersList.size(); i++) {
			String my_new_str = answersList.get(i).getAnswerText().replaceAll("<", "&lt;").replaceAll(">", "&gt;");
			strAnswers.add(my_new_str);
		}

		pageModel.addAttribute("testId", mTest.getTestId());
		pageModel.addAttribute("test", mTest);
		pageModel.addAttribute("question", quest.getQuestion());
		pageModel.addAttribute("answersList", strAnswers);
		mTest.setStartTimeMillis(System.currentTimeMillis());
		return "UserTraineeMode";
	}
	//
	private void clearTest() {
		counter = 0;
		mTest.setWrongAnswerCounter(0);
		mTest.setRightAnswerCounter(0);
	}
	//
	private void getDataFromXML(String xmlStr) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Document document = null;
		try {
			builder = factory.newDocumentBuilder();
			document = builder.parse(new InputSource(new StringReader(xmlStr)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Long> qstnIDsList = new ArrayList<Long>();
		List<String> answIDsList = new ArrayList<String>();
		mQstnList = new ArrayList<Question>();
		mAnswList = new ArrayList<Answer>();
		document.getDocumentElement().normalize();
		NodeList qList = document.getElementsByTagName("question");
		//
		for (int temp = 0; temp < qList.getLength(); temp++) {
			Question question = new Question();
			Node qNode = qList.item(temp);
			//
			if (qNode.getNodeType() == Node.ELEMENT_NODE) {
				Element qElement = (Element) qNode;
				question.setId(Long.parseLong(qElement.getAttribute("questionID")));
				qstnIDsList.add(Long.parseLong(qElement.getAttribute("questionID")));
				question.setQuestion(qElement.getElementsByTagName("questionText").item(0).getFirstChild().getNodeValue());
				NodeList aList = qElement.getElementsByTagName("answer");
				//
				for (int j = 0; j < aList.getLength(); j++) {
					Answer answ = new Answer();
					Node aNode = aList.item(j);
					if (aNode.getNodeType() == Node.ELEMENT_NODE) {
						Element aElement = (Element) aNode;
						answ.setQuestionAnswer(question);
						answ.setQuestionAnswer(question);
						answ.setId(aElement.getAttribute("answerID"));
						answIDsList.add(aElement.getAttribute("answerID"));
						String ansText = aElement
								.getElementsByTagName("answerText").item(0)
								.getFirstChild().getNodeValue();
						answ.setAnswerText(ansText);

						answ.setIsAnswerRight(Boolean.valueOf(aElement
								.getElementsByTagName("answerStatus").item(0)
								.getFirstChild().getNodeValue()));
					} // end if (aNode.getNodeType() == Node.ELEMENT_NODE)
					mAnswList.add(answ);
				} // end for (int j = 0; j < aList.getLength(); j++)
			}// end if (qNode.getNodeType() == Node.ELEMENT_NODE)
			question.setAnswrIDsList(answIDsList);
			mQstnList.add(question);
			answIDsList.clear();
		}// end for (int temp = 0; temp < qList.getLength(); temp++)
		mTest.setQstnNmList(qstnIDsList);
	}
	//
	private List<Answer> getAnswersList(Question quest) {
		List<Answer> anslist = new ArrayList<Answer>();
		if (quest != null) {

			List<String> answersIDs = quest.getAnswrIDsList();		
			for (String answID : answersIDs) {
				for (Answer ans : mAnswList) {
					if (answID.compareTo(ans.getId()) == 0) {
						anslist.add(ans);
						break;
					}
				}
			}
		}

		return anslist;
	}
	//
	private String getTestDurationTime(Test test) {
		test.setEndTimeMillis(System.currentTimeMillis());
		test.setTotalTimeMillis(test.getEndTimeMillis()
				- test.getStartTimeMillis());

		long durationTime = test.getTotalTimeMillis();
		long milliseconds = durationTime % 1000;
		long seconds = durationTime / 1000 % 60;
		long minutes = durationTime / (60 * 1000) % 60;
		long hours = durationTime / (60 * 60 * 1000) % 24;

		return hours + " hours " + minutes + " minutes " + seconds
				+ " seconds " + milliseconds + " milliseconds";
	}
	//
	private Test getTestResults(List<Answer> answersList, Test test) {
		int wrongAnswerCounter = 0;
		int rightAnswerCounter = 0;

		List<String> resultList = new ArrayList<String>();
		String result = "";

		for (int i = 0; i < answersList.size(); i++) {
			Answer answer = answersList.get(i);
			Question question = answer.getQuestionAnswer();

			rightAnswerCounter  = answer.getIsAnswerRight() ? rightAnswerCounter + 1 : rightAnswerCounter;
			wrongAnswerCounter = answersList.size() - rightAnswerCounter;
			result = question.getQuestion() + ": " + answer.getIsAnswerRight()
					+ (answer.getIsAnswerRight() ? " Correct" : " Incorrect");
			resultList.add(result);
		}
		test.setTestResultList(resultList);
		test.setWrongAnswerCounter(wrongAnswerCounter);
		test.setRightAnswerCounter(rightAnswerCounter);
		return test;
	}	
	////
	@RequestMapping(value = "/Personal_result_view")
	public String allCategoriesAndLevelsSelection(Model model){
		List<String> allCategories = personalService.getCategoriesList();
		List<String> allLevels = personalService.getComplexityLevelList();
		model.addAttribute("categoryNames", allCategories);
		model.addAttribute("cLevels", allLevels);
		return "UserTraineeModeCreationTest_1";// rename to USER !!!
	}
	
	/*
	3.2.4. Performing Test – Control Mode
	Pre-conditions:
	1. The user has the link for the test given by a company
	2. The user uses any Hardware with the following:
	Internet connection 
	Running Internet Browser 
	Enabled Web camera
	Normal Flow:
	1. The user types the link in the Internet Browser
	2. The System launches pre-generated test with random capturing photos from the PC’s Web camera. Alternative Flow: Web Camera Disabled
	3. The System shows a question with several answers. (American method)
	4. The user selects an answer
	5. The System captures photo in random time periods.  
	6. The items 3 - 4 are repeated for all the questions of the generated test. 
	3.1.1.1. Web Camera Disabled
	Pre-Conditions:
	1. Performing test in the control mode
	2. Web camera either doesn’t exist or is disabled
	Flow:
	1. Stop test with the alert message “Web camera should be enabled. Please try again”
	 */
	///------- this action is click on the link provided in the mail ----// BEGIN //		
	@RequestMapping({"/jobSeeker_test_preparing_click_event"})
	public String jobSeeker_test_preparing_click_event(HttpServletRequest request, Model model){		
		testId = request.getQueryString();// getting  id of test from link after user click the link		
		return "Personal_LinkClickAction";
	}
	////---------- Test in control mode case --------- // BEGIN //
	private static String tableTestCreated;
	////
	@RequestMapping({"/jobSeeker_authorization_event"})
	public String jobSeeker_authorization_event(String id, String password, HttpServletRequest request, Model model){		
		StringBuffer createdTestTable = new StringBuffer();
		StringBuffer correctAnswers = new StringBuffer();
		String outResult;
		String[] testForPerson = personalService.GetTestForPerson(testId);
		////	
		long timeStartTest = 0;
		//
		if (testForPerson != null && testForPerson[0].equals(id) && testForPerson[1].equals(password)) {
			//
			timeStartTest = System.currentTimeMillis();
			System.out.println("start test time saved. testId-"+testId);
			//
			personId = testForPerson[0]; personPassword = testForPerson[1];
			String[] tempArray = testForPerson[2].split(",");

			createdTestTable.append("<form action='endPersonTest' id='formTestPerson'  method='post'><table class='tableStyle'>");
			for(int i = 0;i<tempArray.length;i++){
				//
				String tempQuestion = maintenanceService.getQuestionById(tempArray[i]);
				//
				String[] tempQuestionText = tempQuestion.split(IMaintenanceService.DELIMITER);
				//
				createdTestTable.append("<table id='tabTestPerson_"+i+"' class='tableStyle'><tr><th class'questionTextStyle' colspan='2'>"+tempQuestionText[1]+"</th></tr>");			
				if(tempQuestionText[3].length() > 15){					
					createdTestTable.append("<tr><td colspan='2'><img src='static/images/questions/"+tempQuestionText[3]+"' alt='question image'></td></tr><br>");
				}			
				//	
				
				if(tempQuestionText.length > 7 && tempQuestionText.length == 11 && tempQuestionText[9].equalsIgnoreCase("0") && tempQuestionText[10].equalsIgnoreCase("0")){
					createdTestTable.append("<tr onchange='onchangeClick(1)'><td><p>A. <input type='checkbox' name='answerschecked' value='A'>&nbsp;&nbsp; "+tempQuestionText[7]+"</p></td> "
							+ "<td><p>B. <input type='checkbox' name='answerschecked' value='B'>&nbsp;&nbsp; "+tempQuestionText[8]+"</p></td></tr>");		
				}else if(tempQuestionText.length > 7 && tempQuestionText.length == 11 && !tempQuestionText[9].equalsIgnoreCase("0") && !tempQuestionText[10].equalsIgnoreCase("0")){
					createdTestTable.append("<tr onchange='onchangeClick(1)'><td><p>A. <input type='checkbox' name='answerschecked' value='A'>&nbsp;&nbsp; "+tempQuestionText[7]+"</p></td> "
							+ "<td><p>B. <input type='checkbox' name='answerschecked' value='B'>&nbsp;&nbsp; "+tempQuestionText[8]+"</p></td></tr>");	
					createdTestTable.append("<tr onchange='onchangeClick(1)'><td><p>C. <input type='checkbox' name='answerschecked' value='C'>&nbsp;&nbsp; "+tempQuestionText[7]+"</p></td>"
							+ "<td><p>D. <input type='checkbox' name='answerschecked' value='D'>&nbsp;&nbsp; "+tempQuestionText[8]+"</p></td></tr>");		
				}else if(tempQuestionText.length > 7 && tempQuestionText.length > 11){
					createdTestTable.append("<tr onchange='onchangeClick(1)'><td><p>A. <input type='checkbox' name='answerschecked' value='A'>&nbsp;&nbsp; "+tempQuestionText[7]+"</p></td> "
							+ "<td><p>B. <input type='checkbox' name='answerschecked' value='B'>&nbsp;&nbsp; "+tempQuestionText[8]+"</p></td></tr>");	
					createdTestTable.append("<tr onchange='onchangeClick(1)'><td><p>C. <input type='checkbox' name='answerschecked' value='C'>&nbsp;&nbsp; "+tempQuestionText[7]+"</p></td>"
							+ "<td><p>D. <input type='checkbox' name='answerschecked' value='D'>&nbsp;&nbsp; "+tempQuestionText[8]+"</p></td></tr>");
					//
					createdTestTable.append("<tr onchange='onchangeClick(1)'><td><p>E. <input type='checkbox' name='answerschecked' value='E'>&nbsp;&nbsp; "+tempQuestionText[7]+"</p></td> "
							+ "<td><p>F. <input type='checkbox' name='answerschecked' value='F'>&nbsp;&nbsp; "+tempQuestionText[8]+"</p></td></tr>");		
					createdTestTable.append("<tr><td><p>C. <input type='checkbox' name='answerschecked' value='C'>&nbsp;&nbsp; "+tempQuestionText[7]+"</p></td></tr> "
							+ "<tr><td><p>G. <input type='checkbox' name='answerschecked' value='G'>&nbsp;&nbsp; "+tempQuestionText[8]+"</p></td></tr>");						
				} 	
				//
				correctAnswers.append(tempQuestionText[6] + ",");
			}	//end for
			//				
			createdTestTable.append("</table><br><input type='text' hidden='hidden' name='testID' value='"+testId+"'><input  type='submit' value='Send Test'></form>");  
			correctAnswers.append("----" + testForPerson[0] + "----" + testForPerson[1]);// TO DO on BES split for save action
			//
			setTableTest(createdTestTable);
			outResult = "PersonalTestWebCamFlow";
		}else{
			outResult = "Personal_LinkClickAction";
			tableTestCreated = "";
			// Create log about failed created test for person 
		}
		//// end if else
		String corrAnsw = correctAnswers.toString();
		if(!personalService.SaveStartPersonTestParam(testId, corrAnsw, timeStartTest)){System.out.println("time start saved");}// method for save parameters of test to generated test 
		////
		return outResult;		
	}
	////	
	private void setTableTest(StringBuffer createdTestTable) {
		tableTestCreated = createdTestTable.toString();		
	}
	public static String GetTestTable(){		
		return tableTestCreated;
	}
	////
	@RequestMapping(value = "/endPersonTest", method = RequestMethod.POST)
	String outResultTestToPerson(String answerschecked, String imageLinkText, String testID, Model model){
		//
		System.out.println("end test time saved. testId-"+testID);
		long timeEndTest = System.currentTimeMillis();
		//
		if(!personalService.SaveEndPersonTestResult(testID, answerschecked, "TO DO", timeEndTest)){
			String arg1 = "Sorry test is not sended, try again.";
			model.addAttribute("result", arg1 );
			System.out.println("time end saved");
			return "PersonTestResultPage";	
		}
		return "UserSignIn";	// end of control mode test flow	
	}
	////---------- Test in control mode case --------- // END //
	////----------  action click on the link provided in the mail ----------------// END //
}

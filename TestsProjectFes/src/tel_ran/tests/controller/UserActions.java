package tel_ran.tests.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tel_ran.tests.model.Test;
import tel_ran.tests.services.interfaces.IMaintenanceService;
import tel_ran.tests.services.interfaces.IUserActionService;
@Controller
@Scope("session")
@RequestMapping({"/","/UserActions"})
public class UserActions{ 	

	@Autowired
	IUserActionService userService; 
	@Autowired
	IMaintenanceService maintenanceService;	
	@RequestMapping({"/"})
	public String Index(){     return "UserSignIn";       }// this mapping to index page !!!!!! for all users and all company !!!!
	//--------------------- fields of this class ---------------------------
	private List<String> testResultList;
	private List<String> questionList = null;
	private static  StringBuffer nextQuestionInTest;	
	private Test userTest = null;	
	private static int counter = 0;
	private String userMailForSession;

	/*
	 3.2. User Actions 
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
			boolean getUser = userService.IsUserExist(userEmail, password);

			if (getUser) {	
				userMailForSession = userEmail;
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
		//
		if (email != null) {				
			String[] userArgs = { firstname, lastname, email,  password};			
			boolean actionRes = userService.AddingNewUser(userArgs);
			if(actionRes){
				userMailForSession = email;
				outPage = "UserAccountPage";
			}else{
				outPage = "UserSignIn";
				model.addAttribute("logedUser","Registration is Failed !");
			}
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
	////------------------ Filling test parameters  ------------------// BEGIN //
	@RequestMapping(value = "/createTestForUser")
	public String allCategoriesAndLevelsSelection(Model model){
		List<String> allCategories = userService.getCategoriesList();
		List<String> allLevels = userService.getComplexityLevelList();
		model.addAttribute("categoryNames", allCategories);
		model.addAttribute("cLevels", allLevels);
		return "UserTraineeModeCreationTest_1";// rename to USER !!!
	}	
	////
	@RequestMapping({"addQuestionsCount"})
	public String addCategoryLevelMaxQuestionsNumber(String catName, String levelName, Model model){
		userTest = new Test();
		testResultList = new ArrayList<String>();
		userTest.setCategoryName(catName);
		userTest.setLevel(Integer.parseInt(levelName));
		userTest.setUserMailAddress(userMailForSession);
		////
		String questionsCount = userService.getMaxCategoryLevelQuestions(catName, levelName);
		model.addAttribute("catName", catName);
		model.addAttribute("levelName", levelName);
		model.addAttribute("questionsCountByCategoryLevel", questionsCount);
		return "UserTraineeModeCreationTest_2";
	} 	
	////------------------ Filling test parameters  ------------------// END //
	//// ------------------ creation test for User ------------------// BEGIN //
	public static String AutoGenForm(){	return nextQuestionInTest.toString();	}// getter for text to page test for user
	////
	@RequestMapping({ "/startUserTest" })
	public String StartUserTest(String questionCountNumber, Model model) {
		//setting received number of question in Test
		if(questionCountNumber != null){
			userTest.setCountOfQuestionsFromUser(Integer.parseInt(questionCountNumber));
			userTest.setStartTimeMillis(System.currentTimeMillis());
			//test creation
			String questionsInText = userService.getTraineeQuestions(userTest.getCategoryName(), userTest.getLevel(), userTest.getCountOfQuestionsFromUser());
			//
			nextQuestionInTest = new StringBuffer();
			String testAttributes = "<p>Category  - "+userTest.getCategoryName()+"</p>"
					+ "<p>Complexity Level - "+userTest.getLevel()+"</p>"
					+ "<p>Count Questions - "+userTest.getCountOfQuestionsFromUser()+"</p>";
			model.addAttribute("question", testAttributes );
			nextQuestionInTest.append("<br> <input type='submit' value='Start The Test' />");
			CreationTestForUser(questionsInText);		
		}else{
			return "UserTraineeModeCreationTest_1";
		}
		return "UserTraineeMode";		
	}
	////
	@RequestMapping({ "/UserTestLoop" })
	public String test_run(HttpServletRequest request, Model model) {
		String answerschecked = request.getParameter("answerschecked");	// getting answer from user 		
		if(userTest.getUserAnswers() == null){// counter and writer for User answers 
			userTest.setUserAnswers(answerschecked );
		}else{
			String userAnswers = userTest.getUserAnswers()+","+answerschecked;
			userTest.setUserAnswers(userAnswers );
		}
		////
		nextQuestionInTest = new StringBuffer();
		////
		if (counter >= questionList.size()) {			
			String durTime = getTestDurationTime(userTest);
			userTest.setTestResultList(testResultList);
			//
			model.addAttribute("time", durTime);
			model.addAttribute("resultsList", testResultList);	
			//model.addAttribute("resultsList", testResultList);	
			model.addAttribute("wrongAnswers",userTest.getUserAnswers());
			model.addAttribute("rightAnswers",userTest.getRightAnswersChars());
			clearTest();
			return "UserTestResultPage";
		}else{
			//
			String tempQuestion = questionList.get(counter++);
			//// --- Creation Test Page HTML Text  witch Parameters ------
			String[] questionAttributes = tempQuestion.split(IMaintenanceService.DELIMITER);
			//	
			if(userTest.getRightAnswersChars() == null){
				userTest.setRightAnswersChars(questionAttributes[3]);
			}else{
				String rightAnswerChars = userTest.getRightAnswersChars() +","+ questionAttributes[3];
				userTest.setRightAnswersChars(rightAnswerChars);
			}
			//
			model.addAttribute("question", "' "+questionAttributes[0]+" '");
			//
			String[] res = maintenanceService.getQuestionById(questionAttributes[1], IUserActionService.ACTION_GET_ARRAY);
			if(res[1].length() > 15){ 
				nextQuestionInTest.append("<br><img class='imageClass' src='" + res[1] + "' alt='image not supported'>");// image text in coding Base64 
				testResultList.add("<p>" + questionAttributes[0] + "</p><img class='imageClass' src='" + res[1] + "' alt='no image'><p>Correct Answer : " + questionAttributes[3] 
						+"&nbsp;&nbsp;&nbsp;&nbsp; Your Answer : </p>");// code for view result for user after the test 
			}else{
				testResultList.add(questionAttributes[0]);// that flow if question no image only text !!
			}
			////
			if(questionAttributes.length > 4){				
				String[] answers = CreateAnswers(questionAttributes);
				int countAnswersOnPic = Integer.parseInt(questionAttributes[2]);
				switch(countAnswersOnPic){
				case 2:nextQuestionInTest.append("<p>A. <input type='checkbox' name='answerschecked' value='A'>&nbsp;&nbsp;" + answers[0] + "</p>"
						+ "<p>B. <input type='checkbox' name='answerschecked' value='B'>&nbsp;&nbsp;" + answers[1] + "</p>");break;
				case 4:nextQuestionInTest.append("<p>A. <input type='checkbox' name='answerschecked' value='A'>&nbsp;&nbsp;" + answers[0] + "</p>"
						+ "<p>B. <input type='checkbox' name='answerschecked' value='B'>&nbsp;&nbsp;" + answers[1] + "</p>"
						+ "<p>C. <input type='checkbox' name='answerschecked' value='C'>&nbsp;&nbsp;" + answers[2] + "</p>"
						+ "<p>D. <input type='checkbox' name='answerschecked' value='D'>&nbsp;&nbsp;" + answers[3] + "</p>");break;
				default : ;
				}
			}else{
				int countAnswersOnPic = Integer.parseInt(questionAttributes[2]);
				switch(countAnswersOnPic){
				case 2:nextQuestionInTest.append("<p>A. <input type='checkbox' name='answerschecked' value='A'>&nbsp;&nbsp;</p>"
						+ "<p>B. <input type='checkbox' name='answerschecked' value='B'>&nbsp;&nbsp;</p>");break;
				case 4:nextQuestionInTest.append("<p>A. <input type='checkbox' name='answerschecked' value='A'>&nbsp;&nbsp;</p>"
						+ "<p>B. <input type='checkbox' name='answerschecked' value='B'>&nbsp;&nbsp;</p>"
						+ "<p>C. <input type='checkbox' name='answerschecked' value='C'>&nbsp;&nbsp;</p>"
						+ "<p>D. <input type='checkbox' name='answerschecked' value='D'>&nbsp;&nbsp;</p>");break;
				case 5:nextQuestionInTest.append("<p>A. <input type='checkbox' name='answerschecked' value='A'>&nbsp;&nbsp;</p>"
						+ "<p>B. <input type='checkbox' name='answerschecked' value='B'>&nbsp;&nbsp;</p>"
						+ "<p>C. <input type='checkbox' name='answerschecked' value='C'>&nbsp;&nbsp;</p>"
						+ "<p>D. <input type='checkbox' name='answerschecked' value='D'>&nbsp;&nbsp;</p>"
						+ "<p>E. <input type='checkbox' name='answerschecked' value='E'>&nbsp;&nbsp;</p>");break;
				default : ;
				}
			}
			nextQuestionInTest.append("<br> <input type='submit' value='Next Question' />");				
		}
		//
		return "UserTraineeMode";
	}	
	////
	private String[] CreateAnswers(String[] questionAttributes) {
		String[] answers = new String[4];
		int j=0;
		for (int i = 0; i < questionAttributes.length; i++) {
			if(i == 4 || i == 5 ||i == 6 || i == 7){				
				String my_new_str = questionAttributes[i].replaceAll("<", "&lt;").replaceAll(">", "&gt;");
				answers[j] = my_new_str;				
				j++;
			}
		}
		return answers;
	}
	////
	private void CreationTestForUser(String questionsInText) {
		String[] questionsArray = questionsInText.split(",");
		questionList = new ArrayList<String>();
		for(int i=0;i<questionsArray.length;i++){
			questionList.add(questionsArray[i]);
		}		
	}
	////
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
	////
	private void clearTest() {
		counter = 0;
		userTest = null;
		testResultList = null;
	}
	////------------------ creation test for User ------------------// END //
}

package tel_ran.tests.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tel_ran.tests.model.Test;
import tel_ran.tests.services.interfaces.IUserActionService;
@Controller
@Scope("session")
@RequestMapping({"/","/UserActions"})
public class UserActions implements Serializable{ 	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	IUserActionService userService; 
	
	public UserActions() {
		
	}

	
	//--------------------- fields of this class ---------------------------
	private List<String> testResultList;
	private List<String> questionList = null;
	private static  StringBuffer nextQuestionInTest;	
	private Test userTest = null;	
	private static int counter = 0;
	private String userMailForSession;

	
	////------------------ Filling test parameters  ------------------// BEGIN //
	@RequestMapping(value = "/createTestForUser")
	public String allCategoriesAndLevelsSelection(Model model){
		List<String> allCategories = userService.getAllMetaCategoriesFromDataBase("");
		List<String> allLevels = userService.getComplexityLevelList();
		model.addAttribute("categoryNames", allCategories);
		model.addAttribute("cLevels", allLevels);
		return "user/UserTraineeModeCreationTest_1";// rename to USER !!!
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
		return "user/UserTraineeModeCreationTest_2";
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
			List<String> questionsInText = userService.getTraineeQuestions(userTest.getCategoryName(), userTest.getLevel(), userTest.getCountOfQuestionsFromUser());
			//
			nextQuestionInTest = new StringBuffer();
			String testAttributes = "<p>Category  - "+userTest.getCategoryName()+"</p>"
					+ "<p>Complexity Level - "+userTest.getLevel()+"</p>"
					+ "<p>Count Questions - "+userTest.getCountOfQuestionsFromUser()+"</p>";
			model.addAttribute("question", testAttributes );
			nextQuestionInTest.append("<br> <input type='submit' value='Start The Test' />");
			CreationTestForUser(questionsInText);		
		}else{
			return "user/UserTraineeModeCreationTest_1";
		}
		return "user/UserTraineeMode";		
	}
	////-------------------------------------------------------------------------------- test loop method new question reload all page
	@RequestMapping({ "/UserTestLoop" })
	public String test_run(HttpServletRequest request, Model model) {
		String answerschecked = request.getParameter("answerschecked");	// getting answer from user 		
		if(userTest.getUserAnswers() == null){// counter and writer for User answers 
			userTest.setUserAnswers(answerschecked );
		}else{
			String userAnswers = userTest.getUserAnswers()+","+answerschecked;
			userTest.setUserAnswers(userAnswers);
		}
		////
		nextQuestionInTest = new StringBuffer();
		//// ---------------------------------  out test result on result page 
		if (counter >= questionList.size()) {			
			String durTime = getTestDurationTime(userTest);
			userTest.setTestResultList(testResultList);
			//
			model.addAttribute("time", durTime);
			model.addAttribute("resultsList", testResultList);				
			model.addAttribute("wrongAnswers",userTest.getUserAnswers());
			model.addAttribute("rightAnswers",userTest.getRightAnswersChars());
			clearTest();
			return "user/UserTestResultPage";
		}else{// ---------------- TO DO REST FOR the case  bilding question for user on test

			String tempQuestion = questionList.get(counter++);
			String[] questionAttributes = tempQuestion.split(" ");
			if(userTest.getRightAnswersChars() == null && questionAttributes[5] != null){
				userTest.setRightAnswersChars(questionAttributes[5]);
			}else if(userTest.getRightAnswersChars() != null && questionAttributes[5] != null){
				String rightAnswerChars = userTest.getRightAnswersChars() +","+ questionAttributes[5];
				userTest.setRightAnswersChars(rightAnswerChars);
			}
			/* 0 = id
			 * 1 = question
			 * 2 = description
			 * 3 = code text
			 * 4 = lang cod			
			 * 5 = corr answer
			 * 6 = num ansers on picture
			 * 7 = meta category 
			 *** when question not included meta cetgory ! meta cat = language name			 
			 * */
			// ----------------------------- question text
			model.addAttribute("question", "' "+questionAttributes[1]+" '"); 
			//// ----------------------------  description text	
			if(questionAttributes[2] != null && questionAttributes[2].length() > 10 && !questionAttributes[2].equals(" ")){					
				model.addAttribute("descriptionText", "' "+questionAttributes[2]+" '");
			}
			////  -------------------------  question witch code: code text 
			if(questionAttributes[3] != null && questionAttributes[3].length() > 10 && !questionAttributes[3].equals(" ")){						
				if(questionAttributes[6].equalsIgnoreCase("0")){		
					nextQuestionInTest.append("<div class='send_button'><span class='buttons'>handler-code</span></div><br>");					
				} 
				nextQuestionInTest.append("Code text<br><textarea id='codeText' rows='20' cols='25'>" + questionAttributes[3] + "</textarea>");
			}
			////  ------------------------- 
			String[] res = userService.getQuestionById(questionAttributes[0], IUserActionService.ACTION_GET_ARRAY);
			if(res[1] != null && res[1].length() > 15){
				String[] tempres = res[0].split(" ");  		

				nextQuestionInTest.append("<br><img class='imageClass' src='" + res[1] + "' alt='image not supported'>");// image text in coding Base64 
				// -------------- testResultList.add: view result for user after the test 
				testResultList.add("<p>" + questionAttributes[0] + "</p><img class='imageClass' src='" + res[1] + "' alt='no image'><p>Correct Answer : " + questionAttributes[5] +"</p>");
				if(tempres[3] != null && tempres[3].length() > 20){
					nextQuestionInTest.append("<br><textarea rows='20' cols='25'>"+tempres[3]+"</textarea>");
				}
			}
			//// ----------------- answers check boxses bilder 
			if(res[3] != null && res[3].length() > 10){	
				String[] tempAnswers = res[3].split(IUserActionService.DELIMITER);
				for(int i=0; i < tempAnswers.length ;i++){					
					char tempChar = IUserActionService.ANSWER_CHAR_ARRAY[i];
					nextQuestionInTest.append("<p>"+tempChar+". <input type='checkbox' name='answerschecked' value='"+tempChar+"'>&nbsp;&nbsp;" + tempAnswers[i] + "</p>");
				}
			}else{
				int countAnswersOnPic = Integer.parseInt(questionAttributes[6]);				
				for(int i=0; i < countAnswersOnPic ;i++){					
					char tempChar = IUserActionService.ANSWER_CHAR_ARRAY[i];
					nextQuestionInTest.append("<p>"+tempChar+". <input type='checkbox' name='answerschecked' value='"+tempChar+"'></p>");
				}				
			}
			nextQuestionInTest.append("<br> <input type='submit' value='Next Question' />");				
		}
		// ------ end bilding question
		return "user/UserTraineeMode";
	}	
	//// ----------------------------------- 
	private void CreationTestForUser(List<String> questionsInText) {		
		questionList = new ArrayList<String>();		
		questionList.addAll(questionsInText);
	}
	//// ------------------------------------
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
	@RequestMapping(value="/handler-user-code", method=RequestMethod.POST)
	public @ResponseBody JsonResponse HandlerCode(HttpServletRequest request) {  		
		String userCode = request.getParameter("userCode");		
		JsonResponse res = new JsonResponse(); 
		String tRes = userService.TestCodeQuestionUserCase(userCode);
		if(tRes != null){    			
			res.setStatus("SUCCESS");
			res.setResult(tRes); 			
		} else{
			res.setStatus("ERROR");	
			res.setResult(tRes); 
		}
		return res;
	}
	////static resurse class for JSON, Ajax on company add page 
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
}

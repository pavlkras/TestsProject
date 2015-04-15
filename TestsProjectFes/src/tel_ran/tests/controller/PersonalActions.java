package tel_ran.tests.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	//--------------------- fields of this class ---------------------------
	String personId = "";
	String personPassword = "";
	String testId  = "";	
	////
	@RequestMapping({"/PersonalActions"})
	public String startPageToPerson(){		return "UserSignIn";	}

	/*
	3.2.4. Performing Test – Control Mode
	Pre-conditions:
	1. The Person has the link for the test given by a company
	2. The Person uses any Hardware with the following:
	Internet connection 
	Running Internet Browser 
	Enabled Web camera
	Normal Flow:
	1. The Person types the link in the Internet Browser
	2. The System launches pre-generated test with random capturing photos from the PC’s Web camera. Alternative Flow: Web Camera Disabled
	3. The System shows a question with several answers. (American method)
	4. The Person selects an answer
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
		String outResult = " ";
		String[] testForPerson = personalService.GetTestForPerson(testId);
		for(int i=0;i<testForPerson.length;i++){
			System.out.println("testForPerson-"+testForPerson[i]);
		}
		////	
		long timeStartTest = 0;
		//
		if (testForPerson != null && testForPerson[0].equals(id) && testForPerson[1].equals(password)) {
			//
			timeStartTest = System.currentTimeMillis();
			System.out.println("start test time saved. testId-"+testId);//----------------------------------- sysout
			//
			personId = testForPerson[0];
			personPassword = testForPerson[1];
			String[] tempArray = testForPerson[2].split(",");// split string witch numbers id of question (long)

			createdTestTable.append("<div class='contentFormDiv'><form action='endPersonTest' id='formTestPerson'  method='post'>"
					+ "<table>");// table of tables witch questions N-1
			////
			for(int i = 0;i<tempArray.length;i++){// Cycle long id questions for create one new question and add in out text
				//
				String tempQuestion = maintenanceService.getQuestionById(tempArray[i]);// getting one question by id from BES	
				System.out.println("tempQuestion-"+tempQuestion);//-------------------sysout
				/////
				String[] tempQuestionText = tempQuestion.split(IMaintenanceService.DELIMITER);// Splitting text by delimiter
				////				
				createdTestTable.append("<tr><td><table id='tabTestPerson_"+i+"' class='tableStyle'>"
						+ "<tr><th class='questionTextStyle' colspan='2'>"+tempQuestionText[0]+"</th></tr>");// table of question and attributes of question table N-2			
				if(tempQuestionText[2].length() > 15){	// image link	
					
					String workingDir = System.getProperty("user.dir").replaceAll("\\\\", "/");
					String replacedText = tempQuestionText[2].replaceAll("\\\\", "/");
					String imageLink = workingDir + "/questions" + replacedText;
					System.err.println(imageLink);
					createdTestTable.append("<tr><td colspan='2'><img src='" + imageLink + "' alt='Image not supported'></td></tr>");
				}			
				////
				if(tempQuestionText.length > 7){
					String[] answers = CreateAnswers(tempQuestionText);
					if(tempQuestionText[6].equalsIgnoreCase("2")){	
						//
						createdTestTable.append("<tr onchange='onchangeClick(1)'>"
								+ "<td><p class='answersCharParam'>A. <input type='checkbox' name='answerschecked' value='A'>&nbsp;&nbsp;<span class='textAnswerSpan'>" + answers[0] + "</span></p></td>"
								+ "<td><p class='answersCharParam'>B. <input type='checkbox' name='answerschecked' value='B'>&nbsp;&nbsp;<span class='textAnswerSpan'>" + answers[1] + "</span></p></td>"
								+ "</tr>");
						//
					}else if(tempQuestionText[6].equalsIgnoreCase("4") && answers[2].equalsIgnoreCase("0") && answers[3].equalsIgnoreCase("0")){
						//
						createdTestTable.append("<tr onchange='onchangeClick(1)'>"
								+ "<td><p class='answersCharParam'>A. <input type='checkbox' name='answerschecked' value='A'>&nbsp;&nbsp;<span class='textAnswerSpan'>" + answers[0] + "</span></p></td>"
								+ "<td><p class='answersCharParam'>B. <input type='checkbox' name='answerschecked' value='B'>&nbsp;&nbsp;<span class='textAnswerSpan'>" + answers[1] + "</span></p></td>"
								+ "</tr>");
						//
					}else if(tempQuestionText[6].equalsIgnoreCase("4") && !answers[2].equalsIgnoreCase("0") && !answers[3].equalsIgnoreCase("0")){
						//
						createdTestTable.append("<tr onchange='onchangeClick(1)'>"
								+ "<td><p class='answersCharParam'>A. <input type='checkbox' name='answerschecked' value='A'>&nbsp;&nbsp;<span class='textAnswerSpan'>" + answers[1] + "</span></p></td>"
								+ "<td><p class='answersCharParam'>B. <input type='checkbox' name='answerschecked' value='B'>&nbsp;&nbsp;<span class='textAnswerSpan'>" + answers[1] + "</span></p></td>"
								+ "</tr><tr onchange='onchangeClick(1)'>"
								+ "<td><p class='answersCharParam'>C. <input type='checkbox' name='answerschecked' value='C'>&nbsp;&nbsp;<span class='textAnswerSpan'>" + answers[2] + "</span></p></td>"
								+ "<td><p class='answersCharParam'>D. <input type='checkbox' name='answerschecked' value='D'>&nbsp;&nbsp;<span class='textAnswerSpan'>" + answers[3] + "</span></p></td>"
								+ "</tr>");
					}
					////
				}else if(tempQuestionText.length == 7){
					if(tempQuestionText[6].equalsIgnoreCase("2")){
						//
						createdTestTable.append("<tr onchange='onchangeClick(1)'><td>"
								+ "<p class='answersCharParam'>A. <input type='checkbox' name='answerschecked' value='A'>&nbsp;&nbsp;</p></td><td>"
								+ "<p class='answersCharParam'>B. <input type='checkbox' name='answerschecked' value='B'>&nbsp;&nbsp;</p></td></tr>");
						//
					}else if(tempQuestionText[6].equalsIgnoreCase("4")){
						//
						createdTestTable.append("<tr onchange='onchangeClick(1)'>"
								+ "<td><p class='answersCharParam'>A. <input type='checkbox' name='answerschecked' value='A'>&nbsp;&nbsp;</p></td>"
								+ "<td><p class='answersCharParam'>B. <input type='checkbox' name='answerschecked' value='B'>&nbsp;&nbsp;</p></td>"
								+ "</tr><tr onchange='onchangeClick(1)'>"
								+ "<td><p class='answersCharParam'>C. <input type='checkbox' name='answerschecked' value='C'>&nbsp;&nbsp;</p></td>"
								+ "<td><p class='answersCharParam'>D. <input type='checkbox' name='answerschecked' value='D'>&nbsp;&nbsp;</p></td>");
						////
					}else if(tempQuestionText[6].equalsIgnoreCase("5")){
						//
						createdTestTable.append("<tr onchange='onchangeClick(1)'>"
								+ "<td><p class='answersCharParam'>A. <input type='checkbox' name='answerschecked' value='A'>&nbsp;&nbsp;</p></td>"
								+ "<td><p class='answersCharParam'>B. <input type='checkbox' name='answerschecked' value='B'>&nbsp;&nbsp;</p></td>"
								+ "</tr><tr onchange='onchangeClick(1)'>"
								+ "<td><p class='answersCharParam'>C. <input type='checkbox' name='answerschecked' value='C'>&nbsp;&nbsp;</p></td>"
								+ "<td><p class='answersCharParam'>D. <input type='checkbox' name='answerschecked' value='D'>&nbsp;&nbsp;</p></td>"
								+ "<td><p class='answersCharParam'>E. <input type='checkbox' name='answerschecked' value='E'>&nbsp;&nbsp;</p></td></tr>");
					}
				}
				////
				createdTestTable.append("</table></td></tr>");
				correctAnswers.append(tempQuestionText[5]);
			}	//end for
			//				
			createdTestTable.append("</table><br>"
					+ "<input type='text' hidden='hidden' name='testID' value='"+testId+"'>"
					+ "<input id='sendTestButton' type='submit' value='Send Test'>"
					+ "<textArea name='imageLinkText' hidden='hidden'  id='imageLinkText'></textArea></form></div>"); 	// adding image links text (base64 decoding) as string witch delimiter beetwin links
			// delimiter is --  @end_of_link@
			//
			setTableTest(createdTestTable);// that created table of questions for one test 
			outResult = "PersonalTestWebCamFlow";
		}else{
			outResult = "Personal_LinkClickAction";
			tableTestCreated = "not good :(";
			// Create log about failed created test for person 
		}
		//// end if else
		//testId +="----" + testForPerson[0] + "----" + testForPerson[1];// TO DO on BES split for save action
		String corrAnsw = correctAnswers.toString();
		if(!personalService.SaveStartPersonTestParam(testId, corrAnsw, timeStartTest)){System.out.println("time start saved-"+timeStartTest);}// method for save parameters of test to generated test 
		////*/
		return outResult;		
	}
	////
	private String[] CreateAnswers(String[] questionAttributes) {
		String[] answers = new String[4];
		int j=0;
		for (int i = 0; i < questionAttributes.length; i++) {
			if(i == 7 || i == 8 ||i == 9 || i == 10){		// by default this answers in text 		
				String my_new_str = questionAttributes[i].replaceAll("<", "&lt;").replaceAll(">", "&gt;");
				answers[j] = my_new_str;				
				j++;
			}
		}
		return answers;
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
		String newAnswerString = answerschecked.replaceAll(",,", "").replaceAll(",", "");
		//
		long timeEndTest = System.currentTimeMillis();
		System.out.println("end time test save-"+ timeEndTest + "; id test-"+testID + "; length of text witch img links=" + imageLinkText.length()+" simbols");//----------------------------------- sysout
		//
		if(!personalService.SaveEndPersonTestResult(testID, newAnswerString, imageLinkText, timeEndTest)){
			String arg1 = "Sorry test is not sended, try again.";
			model.addAttribute("result", arg1 );
			System.out.println("time end saved");
			return "PersonTestResultPage";	
		}
		//
		clearTheTest();
		return "UserSignIn";	// end of control mode test flow	
	}
	////---------- Test in control mode case --------- // END //	
	////----------  action click on the link provided in the mail ----------------// END //	

	private void clearTheTest() {		
		// TODO Auto-generated method stub
	}
}

package tel_ran.tests.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tel_ran.tests.model.Test;
import tel_ran.tests.services.interfaces.IMaintenanceService;
import tel_ran.tests.services.interfaces.IPersonalActionsService;

@Controller
@Scope("session")
@RequestMapping({"/","/PersonalActions"})
public class PersonalActions {
	private static final int counter = 0;
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
			personId = testForPerson[0];
			personPassword = testForPerson[1];
			String[] tempArray = testForPerson[2].split(",");// split string witch numbers id of question (long)

			createdTestTable.append("<form action='endPersonTest' id='formTestPerson'  method='post'>"
					+ "<table class='tableStyle'><tr>");// table of tables witch questions N-1
			////
			for(int i = 0;i<tempArray.length;i++){// Cycle long id questions for create one new question and add in out text
				//
				String tempQuestion = maintenanceService.getQuestionById(tempArray[i]);// getting one question by id from BES
				String[] tempQuestionText = tempQuestion.split(IMaintenanceService.DELIMITER);// Splitting text by delimiter
				////
				for(int f=0;f<tempQuestionText.length;f++)
					System.out.println(tempQuestionText[f]);
		/*		createdTestTable.append("<table id='tabTestPerson_"+i+"' class='tableStyle'>"
						+ "<tr><th class'questionTextStyle' colspan='2'>"+tempQuestionText[1]+"</th></tr>");// table of question and attributes of question table N-2			
				if(tempQuestionText[3].length() > 15){					
					createdTestTable.append("<tr><td colspan='2'><img src='static/images/questions/"+tempQuestionText[3]+"' alt='question image'></td></tr><br>");
				}			
				//----------------------
				if(tempQuestionText.length > 4){
					String[] answers = CreateAnswers(tempQuestionText);
					if(tempQuestionText[2].equalsIgnoreCase("2")){					
						createdTestTable.append("<p>A. <input type='checkbox' name='answerschecked' value='A'>&nbsp;&nbsp;" + answers[0] + "</p>"
								+ "<p>B. <input type='checkbox' name='answerschecked' value='B'>&nbsp;&nbsp;" + answers[1] + "</p>");
					}else if(tempQuestionText[2].equalsIgnoreCase("4")){
						createdTestTable.append("<p>A. <input type='checkbox' name='answerschecked' value='A'>&nbsp;&nbsp;" + answers[0] + "</p>"
								+ "<p>B. <input type='checkbox' name='answerschecked' value='B'>&nbsp;&nbsp;" + answers[1] + "</p>");
						createdTestTable.append("<p>C. <input type='checkbox' name='answerschecked' value='C'>&nbsp;&nbsp;" + answers[2] + "</p>"
								+ "<p>D. <input type='checkbox' name='answerschecked' value='D'>&nbsp;&nbsp;" + answers[3] + "</p>");
					}				
				}else 
					if(tempQuestionText.length == 4){
						if(tempQuestionText[2].equalsIgnoreCase("2")){
							createdTestTable.append("<p>A. <input type='checkbox' name='answerschecked' value='A'>&nbsp;&nbsp;</p>"
									+ "<p>B. <input type='checkbox' name='answerschecked' value='B'>&nbsp;&nbsp;</p>");
						}else if(tempQuestionText[2].equalsIgnoreCase("4") && tempQuestionText.length == 4){
							createdTestTable.append("<p>A. <input type='checkbox' name='answerschecked' value='A'>&nbsp;&nbsp;</p>"
									+ "<p>B. <input type='checkbox' name='answerschecked' value='B'>&nbsp;&nbsp;</p>");
							createdTestTable.append("<p>C. <input type='checkbox' name='answerschecked' value='C'>&nbsp;&nbsp;</p>"
									+ "<p>D. <input type='checkbox' name='answerschecked' value='D'>&nbsp;&nbsp;</p>");
						}
					}
				//--------------------
				if((tempQuestionText.length > 7 && tempQuestionText.length == 11 && tempQuestionText[9].equalsIgnoreCase("0") && tempQuestionText[10].equalsIgnoreCase("0")) || numAnswersInImg == 2){
					createdTestTable.append("<tr onchange='onchangeClick(1)'><td><p>A. <input type='checkbox' name='answerschecked' value='A'>&nbsp;&nbsp; "+tempQuestionText[7]+"</p></td> "
							+ "<td><p>B. <input type='checkbox' name='answerschecked' value='B'>&nbsp;&nbsp; "+tempQuestionText[8]+"</p></td></tr>");		
				}else if((tempQuestionText.length > 7 && tempQuestionText.length == 11 && !tempQuestionText[9].equalsIgnoreCase("0") && !tempQuestionText[10].equalsIgnoreCase("0")) || numAnswersInImg == 4){
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
				} */	
				//
				//correctAnswers.append(tempQuestionText[6] + ",");
			}	//end for
			//				
			/*createdTestTable.append("</table><br>"
					+ "<input type='text' hidden='hidden' name='testID' value='"+testId+"'>"
					+ "<input  type='submit' value='Send Test'></form>");  
			correctAnswers.append("----" + testForPerson[0] + "----" + testForPerson[1]);// TO DO on BES split for save action
			//
			setTableTest(createdTestTable);// that created table of questions for one test 
*/			outResult = "PersonalTestWebCamFlow";
		}else{
			outResult = "Personal_LinkClickAction";
			tableTestCreated = "not good :(";
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

	////----------  action click on the link provided in the mail ----------------// END //
	//// ------------------ creation test for User ------------------// BEGIN //
	/*	if(questionAttributes.length > 4){
				String[] answers = CreateAnswers(questionAttributes);
				if(questionAttributes[2].equalsIgnoreCase("2")){					
					tableTestCreated.append("<p>A. <input type='checkbox' name='answerschecked' value='A'>&nbsp;&nbsp;" + answers[0] + "</p>"
							+ "<p>B. <input type='checkbox' name='answerschecked' value='B'>&nbsp;&nbsp;" + answers[1] + "</p>");
				}else if(questionAttributes[2].equalsIgnoreCase("4")){
					tableTestCreated.append("<p>A. <input type='checkbox' name='answerschecked' value='A'>&nbsp;&nbsp;" + answers[0] + "</p>"
							+ "<p>B. <input type='checkbox' name='answerschecked' value='B'>&nbsp;&nbsp;" + answers[1] + "</p>");
					tableTestCreated.append("<p>C. <input type='checkbox' name='answerschecked' value='C'>&nbsp;&nbsp;" + answers[2] + "</p>"
							+ "<p>D. <input type='checkbox' name='answerschecked' value='D'>&nbsp;&nbsp;" + answers[3] + "</p>");
				}				
			}else 
				if(questionAttributes.length == 4){
					if(questionAttributes[2].equalsIgnoreCase("2")){
						tableTestCreated.append("<p>A. <input type='checkbox' name='answerschecked' value='A'>&nbsp;&nbsp;</p>"
								+ "<p>B. <input type='checkbox' name='answerschecked' value='B'>&nbsp;&nbsp;</p>");
					}else if(questionAttributes[2].equalsIgnoreCase("4") && questionAttributes.length == 4){
						tableTestCreated.append("<p>A. <input type='checkbox' name='answerschecked' value='A'>&nbsp;&nbsp;</p>"
								+ "<p>B. <input type='checkbox' name='answerschecked' value='B'>&nbsp;&nbsp;</p>");
						tableTestCreated.append("<p>C. <input type='checkbox' name='answerschecked' value='C'>&nbsp;&nbsp;</p>"
								+ "<p>D. <input type='checkbox' name='answerschecked' value='D'>&nbsp;&nbsp;</p>");
					}
				}
			tableTestCreated.append("<br> <input type='submit' value='Next Question' />");				
		}	
	 */
	////------------------ creation test for User ------------------// END //
}

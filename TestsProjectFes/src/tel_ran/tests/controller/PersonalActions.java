package tel_ran.tests.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	private	String testId  = "";	
	private static String wrongResponse = "";
	private static String tableTestCreated;
	////
	@RequestMapping({"/PersonalActions"})
	public String startPageToPerson(){ 		return "user/UserSignIn";     } 
	/*  3.2.4. Performing Test – Control Mode  */
	///------- this action is click on the link provided in the mail ----// BEGIN //		
	@RequestMapping({"/jobSeeker_test_preparing_click_event"})
	public String jobSeeker_test_preparing_click_event(HttpServletRequest request, Model model){		
		testId = request.getQueryString();// getting  id of test from link after user click the link			
		return "person/Personal_LinkClickAction";
	}
	////---------- Test in control mode case --------- // BEGIN //	
	@RequestMapping({"/jobSeeker_authorization_event"})
	public String jobSeeker_authorization_event(String id, String password, HttpServletRequest request){	
		String returnToPage = "person/Personal_LinkClickAction";	
		StringBuffer createdTestTable = new StringBuffer();
		StringBuffer correctAnswers = new StringBuffer();
		/**pattern array is:
		 * testForPerson[0] - id,
		 * testForPerson[1] - password,
		 * testForPerson[2] - id questions in list<long>,
		 * testForPerson[3] - Time start,
		 * testForPerson[4] - Time end 
		 */		 
		String[] testForPerson = personalService.GetTestForPerson(testId);	

		if(testForPerson != null && testForPerson.length == 5){
			if (testForPerson != null && testForPerson[0].equals(id) && testForPerson[1].equals(password) && testForPerson[4].length() < 3) {						
				String[] tempArray = testForPerson[2].split(",");// split string witch numbers id of question (long)				
				createdTestTable.append("<div class='contentBox'>"
						+ "<form action='endPersonTest' id='formTestPerson'  method='post'>"// Test table form
						+ "<table>");// table of tables witch questions 
				////
				for(int i = 0;i<tempArray.length;i++){// Cycle long id questions for create one new question and add in out text				
					String[] tempQueryResult = maintenanceService.getQuestionById(tempArray[i], IPersonalActionsService.ACTION_GET_ARRAY);// getting one question by id from BES								
					String[] tempQuestionText = tempQueryResult[0].split(IPersonalActionsService.DELIMITER);// Splitting text by delimiter
					////	
					String tempIdQ = tempArray[i];
					createdTestTable.append("<tr><td>"// table question begin 
							+ "<table id='tabTestPerson_"+i+"' class='tableStyle'>"
							+ "<tr><th class='questionTextStyle' colspan='2'>"
							+ tempQuestionText[0]+"</th></tr>");
					if(tempQueryResult[1] != null && tempQueryResult[1].length() > 25){	// image link					
						createdTestTable.append("<tr><td colspan='2'><img src='" + tempQueryResult[1] + "' alt='Image not supported'></td></tr>");
					}
					////
					if(tempQuestionText[3] != null && tempQuestionText[3].length() > 10){	// code question code 					
						createdTestTable.append("<tr><td><textarea rows='20' cols='25' id='codeText_"+i+"'>" + tempQuestionText[3] + "</textarea>");
						if(tempQuestionText[2].equalsIgnoreCase("0")){		
							createdTestTable.append("<p hidden='hidden' id='idQuestion_"+i+"'>"+tempIdQ+"</p></td>"
									+ "<td><div class='send_button'><span class='buttons'>handler-code</span></div></td></tr>");	
						}
					}
					////				
					if(tempQuestionText != null && tempQuestionText.length > 4){
						String[] answers = CreateAnswers(tempQuestionText);
						if(tempQuestionText[2].equalsIgnoreCase("2")){	
							createdTestTable.append("<tr onchange='onchangeClick(1)'>"
									+ "<td><p class='answersCharParam'>A. <input type='checkbox' name='answerschecked' value='A'>&nbsp;&nbsp;<span class='textAnswerSpan'>" + answers[0] + "</span></p></td>"
									+ "<td><p class='answersCharParam'>B. <input type='checkbox' name='answerschecked' value='B'>&nbsp;&nbsp;<span class='textAnswerSpan'>" + answers[1] + "</span></p></td>"
									+ "</tr>");
						}else if(tempQuestionText[2].equalsIgnoreCase("4") && answers[2].equalsIgnoreCase("0") && answers[3].equalsIgnoreCase("0")){
							createdTestTable.append("<tr onchange='onchangeClick(1)'>"
									+ "<td><p class='answersCharParam'>A. <input type='checkbox' name='answerschecked' value='A'>&nbsp;&nbsp;<span class='textAnswerSpan'>" + answers[0] + "</span></p></td>"
									+ "<td><p class='answersCharParam'>B. <input type='checkbox' name='answerschecked' value='B'>&nbsp;&nbsp;<span class='textAnswerSpan'>" + answers[1] + "</span></p></td>"
									+ "</tr>");
						}else if(tempQuestionText[2].equalsIgnoreCase("4") && !answers[2].equalsIgnoreCase("0") && !answers[3].equalsIgnoreCase("0")){
							createdTestTable.append("<tr onchange='onchangeClick(1)'>"
									+ "<td><p class='answersCharParam'>A. <input type='checkbox' name='answerschecked' value='A'>&nbsp;&nbsp;<span class='textAnswerSpan'>" + answers[1] + "</span></p></td>"
									+ "<td><p class='answersCharParam'>B. <input type='checkbox' name='answerschecked' value='B'>&nbsp;&nbsp;<span class='textAnswerSpan'>" + answers[1] + "</span></p></td>"
									+ "</tr><tr onchange='onchangeClick(1)'>"
									+ "<td><p class='answersCharParam'>C. <input type='checkbox' name='answerschecked' value='C'>&nbsp;&nbsp;<span class='textAnswerSpan'>" + answers[2] + "</span></p></td>"
									+ "<td><p class='answersCharParam'>D. <input type='checkbox' name='answerschecked' value='D'>&nbsp;&nbsp;<span class='textAnswerSpan'>" + answers[3] + "</span></p></td>"
									+ "</tr>");
						}
					}else if(tempQuestionText != null && tempQuestionText.length == 4){
						if(tempQuestionText[2].equalsIgnoreCase("2")){
							createdTestTable.append("<tr onchange='onchangeClick(1)'><td>"
									+ "<p class='answersCharParam'>A. <input type='checkbox' name='answerschecked' value='A'>&nbsp;&nbsp;</p></td><td>"
									+ "<p class='answersCharParam'>B. <input type='checkbox' name='answerschecked' value='B'>&nbsp;&nbsp;</p></td></tr>");
						}else if(tempQuestionText[2].equalsIgnoreCase("4")){
							createdTestTable.append("<tr onchange='onchangeClick(1)'>"
									+ "<td><p class='answersCharParam'>A. <input type='checkbox' name='answerschecked' value='A'>&nbsp;&nbsp;</p></td>"
									+ "<td><p class='answersCharParam'>B. <input type='checkbox' name='answerschecked' value='B'>&nbsp;&nbsp;</p></td>"
									+ "</tr><tr onchange='onchangeClick(1)'>"
									+ "<td><p class='answersCharParam'>C. <input type='checkbox' name='answerschecked' value='C'>&nbsp;&nbsp;</p></td>"
									+ "<td><p class='answersCharParam'>D. <input type='checkbox' name='answerschecked' value='D'>&nbsp;&nbsp;</p></td>");
						}else if(tempQuestionText[2].equalsIgnoreCase("5")){
							createdTestTable.append("<tr onchange='onchangeClick(1)'>"
									+ "<td><p class='answersCharParam'>A. <input type='checkbox' name='answerschecked' value='A'>&nbsp;&nbsp;</p></td>"
									+ "<td><p class='answersCharParam'>B. <input type='checkbox' name='answerschecked' value='B'>&nbsp;&nbsp;</p></td>"
									+ "</tr><tr onchange='onchangeClick(1)'>"
									+ "<td><p class='answersCharParam'>C. <input type='checkbox' name='answerschecked' value='C'>&nbsp;&nbsp;</p></td>"
									+ "<td><p class='answersCharParam'>D. <input type='checkbox' name='answerschecked' value='D'>&nbsp;&nbsp;</p></td>"
									+ "<td><p class='answersCharParam'>E. <input type='checkbox' name='answerschecked' value='E'>&nbsp;&nbsp;</p></td></tr>");
						}					
					}			
					createdTestTable.append("</table></td></tr>");// table close
					if(tempQuestionText[1] != null && !tempQuestionText[1].equals(" "))
						correctAnswers.append(tempQuestionText[1]);				
				}	//end for
				//				
				createdTestTable.append("</table><br>"
						+ "<input type='text' hidden='hidden' name='testID' value='"+testId+"'>"					
						+ "<input id='sendTestButton' type='submit' value='Send Test'>"
						+ "<textArea name='imageLinkText' hidden='hidden'  id='imageLinkText'></textArea></form></div>"); 	// adding image links text (base64 decoding) as string witch delimiter beetwin links
				// delimiter is --  @end_of_link@		
				setTableTest(createdTestTable);// that created table of questions for one test 
				returnToPage = "person/PersonalTestWebCamFlow";
				////			
				String corrAnsw = correctAnswers.toString();// method for save parameters of test to data base 
				long timeStartTest = System.currentTimeMillis();
				personalService.SaveStartPersonTestParam(testId, corrAnsw, timeStartTest );				
				////
			}else{			
				//// case for wotching if person already passed test or not? Actions on page
				if(testForPerson[4].length() > 3){				
					wrongResponse = "You have already passed this test.<br> For more information, contact your company.<br>"
							+ "<p id='test_status' style='display:none;'>test_exist</p>";// 
				}else if(!testForPerson[0].equalsIgnoreCase(id) && testForPerson[1].equalsIgnoreCase(password)){
					wrongResponse = "Your Passport is incorrect !!! Try again.";
				}else if(testForPerson[0].equalsIgnoreCase(id) && !testForPerson[1].equalsIgnoreCase(password)){
					wrongResponse = "Password is incorrect !!! Try again.";
				}else if(!testForPerson[1].equalsIgnoreCase(password) && !testForPerson[0].equalsIgnoreCase(id)){
					wrongResponse = "Your Passport and Password is incorrect !!! Try again.";
				}	
				returnToPage = "person/Personal_LinkClickAction";
			}//// end if else iternal	
		}else if(testForPerson != null && testForPerson.length != 5){// if test not exist or id not correct 
			wrongResponse = "You have a problem, this test not correct.<br> For more information, contact your company.<br>"
					+ "<p id='test_status' style='display:none;'>test_exist</p>";//
		}	
		return returnToPage;		
	}
	////
	public static String WrongResponse(){
		String outRes =  wrongResponse;
		wrongResponse = "";		
		return outRes;		  
	}
	////
	@RequestMapping(value = "/endPersonTest", method = RequestMethod.POST)
	String EndingTestActions(String answerschecked, String imageLinkText, String testID, Model model){	
		String pageOut = "user/UserSignIn";		
		String newAnswerString = null;
		if(answerschecked != null){
			newAnswerString = answerschecked.replaceAll(",,", "").replaceAll(",", "");	
		}
		long timeEndTest = System.currentTimeMillis();
		if(!personalService.SaveEndPersonTestResult(testID, newAnswerString, imageLinkText, timeEndTest)){			
			wrongResponse = "Sorry test is not sended, try again.";				
			pageOut = "person/Personal_LinkClickAction";			
		}/*else{
			wrongResponse = "Sorry test is broken, please call to you'r company.";				
			personalService.SaveEndPersonTestResult(testID, null, null, timeEndTest);
			pageOut = "person/Personal_LinkClickAction";
			counterOfFaultSavingActions = 1;

		}*/
		//		
		return pageOut;	// end of control mode test flow	
	}

	////---------- Test in control mode case --------- // END //	
	////----------  action click on the link provided in the Email ----------------// END //	
	///// ---- private methods -----
	////
	private void setTableTest(StringBuffer createdTestTable) {		
		tableTestCreated = createdTestTable.toString();		
	}
	////
	public static String GetTestTable(){		
		return tableTestCreated;
	}
	////
	private String[] CreateAnswers(String[] questionAttributes) {
		String[] answers = new String[4];
		int j = 3;
		int length = questionAttributes.length;
		for (int i = 0; i < questionAttributes.length; i++) {
			if(j != -1){		// by default this answers in text 		
				String my_new_str = questionAttributes[length-1].replaceAll("<", "&lt;").replaceAll(">", "&gt;");
				answers[j] = my_new_str;				
				j--;
				length--;
			}
		}
		return answers;
	}	
	////
	@RequestMapping(value="/handler-code", method=RequestMethod.POST)
	public @ResponseBody JsonResponse HandlerCode(HttpServletRequest request) { 
		boolean tRes = false;
		JsonResponse res = new JsonResponse(); 
		try{
			String personCode = request.getParameter("personCode");
			String questionID = request.getParameter("questionID");
			long idTest = (long)Integer.parseInt(testId);
			tRes = personalService.TestCodeQuestionCase(personCode, questionID, idTest);
		}catch(Exception e){
			System.out.println("FES AJAX method");
		}

		if(tRes){    			
			res.setStatus("SUCCESS");
			res.setResult(true); 			
		} else{
			res.setStatus("ERROR");	
			res.setResult(false); 
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

package tel_ran.tests.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import tel_ran.tests.services.interfaces.IMaintenanceService;

@Controller
@Scope("session")
@RequestMapping({ "/", "/Maintenance" })
public class Maintenance {


	// use case 3.3.3 Test Maintenance
	public Maintenance(){
		AutoGeneratedHTMLFormText = new StringBuffer();
		AutoGeneratedInformationTextHTML = new StringBuffer();
	}
	@Autowired
	IMaintenanceService maintenanceService;
	//
	private static  StringBuffer AutoGeneratedInformationTextHTML;
	private static StringBuffer AutoGeneratedHTMLFormText;
	private int rowsCounter = 0;
	private String loginForm = "<form class='myButton' action = 'maintenance_login_action'  method = 'get'>"
			+ "Name: <input type='text'  name='username'><br><br>"
			+ "Password: <input type='password'  name='password'><br><br>"
			+ "<input type='submit' class='myButton' value='Administrators Login'></form>";

	/*************************************/
	@RequestMapping({ "/Maintenance" ,"/Ma"})
	public String mappingFromIndexPage(Model model) {
		clearStringBuffer();	
		AutoGeneratedHTMLFormText.append(loginForm);
		return "MaintenanceSignInPage";
	}
	/*************************************/
	@RequestMapping({ "/maintenance_login_action" })
	public String authorize(String username, String password, Model model) {
		clearStringBuffer();	
		boolean result = maintenanceService.setAutorization(username,password);// setter flAutorization on Service.

		if (result) {
			AutoGeneratedHTMLFormText.append("Hello "+username+". Glad to see you again"
					+ "<style type='text/css'>#menuButton{display:block;}</style>");
		} else {
			AutoGeneratedHTMLFormText.append(loginForm +"<br><p>User name or Password incorrect!<br> Try again.</p>");
		}
		return "MaintenanceSignInPage";// other resurs 
	}
	/**************************************/
	@RequestMapping({ "/maintenanceadd" })
	public String addingPage() {
		return "MaintenanceAddingPage";
	}
	/**************************************/
	@RequestMapping({ "/update" })
	public String UpdatePage() {
		return "MaintenanceUpdatePage";
	}
	/**************************************/
	@RequestMapping({ "/otherResursCreationMethod" })
	public String MaintenanceOtherResurses() {
		clearStringBuffer();
		BuildingCategoryCheckBoxTextHTML();
		return "MaintenanceOtherResurses";
	}	
	/*
	 * 3.3.1. Adding test question Pre-Conditions: 
	 * 1. The System is running up
	 * 2. The Administrator (the user with username â€œadminâ€� and password â€œ12345.comâ€�) is signed in Normal Flow: 
	 * 1. The System shows internal link with the text â€œcreate new questionâ€� 
	 * (under the link there will be table with existing questions but it is used in this flow) 
	 * 2. User presses the link 
	 * 3. The system shows form for filling the following data: 
	 * â€¢ Question text (typing)
	 * â€¢ Category (selection)
	 * â€¢ Complexity level (selection from 1 to 5) 
	 * â€¢ 4 answers with numbers (1-4) 
	 * â€¢ Number of the right question 
	 * 4. User types/select required data and press submit button 
	 * 5. The System saves the question data in the Database with message â€œ the question <question text> has been added successfullyâ€�
	 */
	@RequestMapping({ "/add_actions" })
	public String AddProcessingPage(String questionText, String category,
			String levelOfDifficulty, String at1, String at2, String at3,
			String at4, String correctAnswer, String questionIndex, String imageLink,  Model model) {		
		boolean actionRes = false; // flag work action
		List<String> answers = null;
		if(at1.length() > 1 && at3.length() > 1){
			answers = new ArrayList<String>();		
			answers.add(at1);		answers.add(at2);		answers.add(at3);		answers.add(at4);
		}
		//
		try {			
			int questioNumber = Integer.parseInt(questionIndex);// question ID number if question already exist in DB
			int numberOfResponsesInThePicture = 4;// number of responses in the picture by default = 4
			//
			actionRes = maintenanceService.CreateNewQuestion( imageLink ,  questionText,  category,  Integer.parseInt(levelOfDifficulty), answers,  correctAnswer.charAt(0), questioNumber , numberOfResponsesInThePicture );
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("maintenance addProcessingPage :method: Exception");
		}
		// ==========================================
		if (actionRes) {
			// write alternative flow !!!
			model.addAttribute("result", "<p> Question successfully added</p>");// out
			actionRes = false;
		} else {
			// write alternative flow !!!
			model.addAttribute("result",
					"<p> Error adding the question, the question already exists. Try again</p>");// out
		}
		return "MaintenanceAddingPage"; // return too page after action
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// use case 3.3.2 Update Test Question
	/*
	 * Pre-Conditions: 1. The System is running up 2. The Administrator (the
	 * user with username â€œadminâ€� and password â€œ12345.comâ€�) is signed in Normal
	 * Flow: 1. The user requests all questions containing specific text part 2.
	 * The System shows table with scrolling containing all questions sorted by
	 * the questions text 3. User selects required for update question 4. The
	 * System shows form with filled data (see Adding Test Question Use Case) 5.
	 * User updates the question data (There may be updated any fields) and
	 * press submit button 6. The System updates the question data in the
	 * Database
	 */
	//
	@RequestMapping({ "/updateOneQuestion" })
	public String UpdateProcessingPage(String questionID, String questionText,
			String category, String levelOfDifficulty, String at1, String at2,
			String at3, String at4, String correctAnswer, String QuTextNumber,
			String imageLinkText, Model model) {
		//
		clearStringBuffer();
		////
		List<String> answers = null;
		if(at1.length() > 1 && at3.length() > 1){
			answers = new ArrayList<String>();			answers.add(at1);			answers.add(at2);			answers.add(at3);			answers.add(at4);
		}
		//
		boolean result = maintenanceService.UpdateTextQuestionInDataBase( questionID,  imageLinkText,  questionText,  category,  Integer.parseInt(levelOfDifficulty),  answers,  correctAnswer.charAt(0));
		String outRes = "";
		if (result) {
			outRes = "<p>Changed Question successfully added</p>";
		} else {
			outRes = "<p>Error Question no changed!!!</p>";
		}
		AutoGeneratedHTMLFormText.append(outRes);// text on page for testing
		return "MaintenanceUpdatePage";// return too page after action
	}

	////--------------------- Search in to data base by category witch levelOfDifficulty or free ( returned all questions from DB) -----//
	@RequestMapping({ "/search_actions" })
	public String SearchProcessingPage(String category,	String levelOfDifficulti, Model model) {
		clearStringBuffer();
		try{
			List<String> resultDB = maintenanceService.SearchAllQuestionInDataBase(category, Integer.parseInt(levelOfDifficulti));			
			AutoGeneratedHTMLFormText.append("<table><tr><th>N</th><th>Question</th><th>Category</th><th>Level of difficulty</th></tr>");
			////
			for (String questionLine : resultDB) {					
				String[] element = questionLine.split(IMaintenanceService.DELIMITER);
				AutoGeneratedHTMLFormText.append("<tr onclick='test(" + element[1] + ")'>"
						+ "<td> "+ element[1]+ " </td>"
						+ "<td> "+ element[0]+ " </td>"
						+ "<td> "+ element[3]+ " </td>"
						+ "<td> "+ element[4]+ " </td></tr>");
				rowsCounter++;				
			}
			////
			AutoGeneratedHTMLFormText.append("</table><br>");				
		}catch(Exception e){
			//model.addAttribute("resulttt", "Bad Balance :( ");
		}
		return "MaintenanceUpdatePage";// return too page after action
	}

	//------- getting question and attributes for change ---------// Begin //
	@RequestMapping({ "/fillFormForUpdateQuestion" })
	public String getInformationDB(String questionID, Model model) {	
		clearStringBuffer();
		try {
			if (rowsCounter > (Integer.parseInt(questionID) - 1)) {				
				String tempQueryRessult = maintenanceService.getQuestionById(questionID);
				System.out.println(tempQueryRessult);  
				String[] dataFromTables = tempQueryRessult.split(IMaintenanceService.DELIMITER);
				//
				AutoGeneratedHTMLFormText.append("<form name='formTag' action='updateOneQuestion' ><h2>Question Number: " + dataFromTables[0] + ".</h2><br>");
				//
				AutoGeneratedHTMLFormText.append("Question text<br><textarea name='questionText' rows='7'>" + dataFromTables[1] + "</textarea><br>");
				////
				if(dataFromTables[3].length() > 15){
					AutoGeneratedHTMLFormText.append("Image Link<br><textarea name='imageLinkText'>" + dataFromTables[3] + "</textarea><br>");
				}
				//
				AutoGeneratedHTMLFormText.append("Question Category<br> <input type='text' name='category' value='"	+ dataFromTables[4] + "'><br>");
				// --- adding level check box
				if(dataFromTables.length != 8 && dataFromTables.length == 12){				
					String check = dataFromTables[5];
					int checkRes = Integer.parseInt(check);				
					AutoGeneratedHTMLFormText.append("Question Level<br>");
					for(int i=1 ; i<6 ; i++){
						if (checkRes == i) {
							AutoGeneratedHTMLFormText.append("<input checked='checked' type='radio' name='levelOfDifficulty' value=1>" + i);
						} else {
							AutoGeneratedHTMLFormText.append("<input type='radio' name='levelOfDifficulty' value=1>" + i);
						}
					}
				}
				////  adding answers list if exist
				if(dataFromTables.length == 12){
					if(dataFromTables[7].equalsIgnoreCase("2")){
						AutoGeneratedHTMLFormText.append("<br> Answers for Question <br>");
						AutoGeneratedHTMLFormText.append(" A. <textarea name='at1'>"
								+ dataFromTables[8] + "</textarea><br>");
						AutoGeneratedHTMLFormText.append(" B. <textarea name='at2'>"
								+ dataFromTables[9] + "</textarea><br>");					
					}else if(dataFromTables[7].equalsIgnoreCase("4")){
						AutoGeneratedHTMLFormText.append("<br> Answers for Question <br>");
						AutoGeneratedHTMLFormText.append(" A. <textarea name='at1'>"
								+ dataFromTables[8] + "</textarea><br>");
						AutoGeneratedHTMLFormText.append(" B. <textarea name='at2'>"
								+ dataFromTables[9] + "</textarea><br>");
						AutoGeneratedHTMLFormText.append(" C. <textarea name='at3'>"
								+ dataFromTables[10] + "</textarea><br>");
						AutoGeneratedHTMLFormText.append(" D. <textarea name='at4'>"
								+ dataFromTables[11] + "</textarea>");
					}
				}else{
					// if not exist
					AutoGeneratedHTMLFormText.append("<p> No Answers For this question</p>");
				}
				//// ---- adding correct answer char
				AutoGeneratedHTMLFormText.append("<br>Correct Answer<br>");			
				AutoGeneratedHTMLFormText.append("<input type='text' name='correctAnswer' value='" + dataFromTables[6] + "' size='2'><br>");
				//// ----- adding flag ID and submit button 
				AutoGeneratedHTMLFormText.append("<input type='text' name='questionID' value='" + dataFromTables[0] + "' style='visibility: hidden;'><br>");
				AutoGeneratedHTMLFormText.append("<input type='submit' value='Change Question'>");
				AutoGeneratedHTMLFormText.append("</form>");
				////			
			} else {
				AutoGeneratedHTMLFormText.append("Number of Question is Wrong. Input real number of question");
			}
		} catch (NumberFormatException e) {
			//e.printStackTrace();
			System.out.println("catch create form for update question FES");
			AutoGeneratedHTMLFormText.append("Number of Question is Empty. Input number of question");
		}				
		return "MaintenanceUpdatePage";
	}
	//------- getting question and attributes for change ---------// END //

	// -------- method delete from DB Tables Question and Answer By ID -----// Begin //
	@RequestMapping({ "/deleteAction" })
	public String deleteFromTablesQuestionAndAnswer(String questionIDdelete, Model model) {
		clearStringBuffer();
		String tempQueryRessult = maintenanceService.deleteQuetionById(questionIDdelete);
		AutoGeneratedHTMLFormText.append(tempQueryRessult);// out text to Page
		return "MaintenanceUpdatePage";
	}
	// -------- method delete from DB Tables Question and Answer By ID -----// END //
	// 
	// use case 3.3.3 Bulk entering test data
	// --------- adding questions from txt file on user computer -----//  Begin  //
	////
	// This methods work as getter for text to jsp page, in text HTML for sam page
	public static String AutoGeneratedHTMLForm(){		
		String outTextResult = "";
		////
		if(AutoGeneratedHTMLFormText != null){
			outTextResult = AutoGeneratedHTMLFormText.toString();
		}else{
			outTextResult = "<h3>404 Page Not Found </h3>";		
		}			
		return outTextResult;		
	}	
	////
	public static String AutoGeneratedInformationTextHTML(){	
		String outTextResult = "";
		////
		if(AutoGeneratedInformationTextHTML != null){
			outTextResult = AutoGeneratedInformationTextHTML.toString();				
		}	
		AutoGeneratedInformationTextHTML.delete(0, AutoGeneratedHTMLFormText.length());// clear stringbuffer
		return outTextResult;		
	}	
	////
	@RequestMapping({ "/add_from_file_actions" })
	public String addFromFileProcessingPage(String textfromfile, Model model) {
		//sample for text in file question(one line!!!)
		//questionText----imageLink----category----levelOfDifficulti----answer1----answer2----answer3----answer4----correctAnswerChar----questionIndexNumber@end_line@ // as delimiter .00.0
		boolean actionRes = false;		
		if(textfromfile != null && textfromfile.length() > 12){	
			List<String> res = new ArrayList<String>();
			String[] lRes = textfromfile.split("@end_line@");			
			for(int i = 0;i < lRes.length;i++){				
				res.add(lRes[i]);
			}			
			try{		
				actionRes = maintenanceService.FillDataBaseFromTextResource(res);	// adding case on BES
				AutoInformationTextHTML("<br><p class='informationTextP'>Adding Questions is - " + actionRes + "</p>");			
			} catch (Exception e) {
				//e.printStackTrace();
				System.out.println("catch of creating from file questions in FES");
			} 
		}else{
			AutoInformationTextHTML("<br><p class='informationTextP'>Adding Questions is - " + actionRes + "</p>");	// out text to Page
		}
		return "MaintenanceOtherResurses";// return too page after action
	}
	// --------- adding questions from any.txt file on user computer -----//  END  //

	// -------------- Module For Building Questions in to DB ----------------////
	@RequestMapping({ "/moduleForBuildingQuestions" })
	public String ModuleForBuildingQuestions(String category, String nQuestions, Model model) {		
		int nQuest = Integer.parseInt(nQuestions);
		//
		try{
			maintenanceService.ModuleForBuildingQuestions(category, nQuest);	
			AutoInformationTextHTML("<br><p class='informationTextP'>your request: to build |" + nQuestions	+ "| questions made</p>");		
		}catch(Exception e){
			//e.printStackTrace();
			AutoInformationTextHTML("<br><p class='informationTextP'>your request: to build |" + nQuestions	+ "| questions failed</p>");
			System.out.println("catch of creating generated questions in FES");
		}

		return "MaintenanceOtherResurses";// return too page after action
	}
	////////////////////////////////////////// PRIVATE METHODS FOR THIS CLASS /////////////////////////////////////////////////////
	// ---------------------- Building html text of checkBoxe Category form on Page --------------//
	private String BuildingCategoryCheckBoxTextHTML() {		
		StringBuffer checkedFlyButtons = new StringBuffer();
		try {
			List<String> categoryList = maintenanceService.getAllCategoriesFromDataBase();
			if (categoryList != null) {
				int counter = 8;
				for (String tresR : categoryList) {
					if (counter == 8) {
						counter = 0;
						checkedFlyButtons.append("<br>");
					}
					checkedFlyButtons.append(tresR + "&nbsp;-&nbsp;<input type='checkbox' name='category' value='" + tresR + "'> ");
					counter++;
				}
				AutoGeneratedHTMLFormText.append(checkedFlyButtons);
			} else {
				AutoGeneratedHTMLFormText.append("<b>No Categories in Data Base</b>");
			}
		} catch (Exception e) {
			//e.printStackTrace();
			AutoGeneratedHTMLFormText.append("<b>No Categories in Data Base</b>");
			System.out.println("no data base found");
		}
		return "MaintenanceOtherResurses";// return too page after action
	}
	////
	private void AutoInformationTextHTML(String string) {
		AutoGeneratedInformationTextHTML.append(string);
	}

	// ---------------------- formating data in to string buffer  --------------// 
	private void clearStringBuffer() {
		AutoGeneratedHTMLFormText.delete(0, AutoGeneratedHTMLFormText.length());// clear stringbuffer		
	}

}

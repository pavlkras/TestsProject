package tel_ran.tests.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
	@Autowired
	IMaintenanceService maintenanceService;
	/**************************************/
	private String loginForm = "<form class='myButton' action = 'maintenance_login_action'  method = 'get'>"
			+ "Name: <input type='text'  name='username'><br><br>"
			+ "Password: <input type='password'  name='password'><br><br>"
			+ "<input type='submit' class='myButton' value='Administrators Login'></form>";

	/*************************************/
	@RequestMapping({ "/Maintenance" ,"/Ma"})
	public String mappingFromIndexPage(Model model) {
		model.addAttribute("loginText", loginForm);
		model.addAttribute("result","for login User name and Password is EMPTY.");
		return "MaintenanceSignInPage";
	}

	/*************************************/
	@RequestMapping({ "/maintenance_login_action" })
	public String authorize(String username, String password, Model model) {

		boolean result = maintenanceService.setAutorization(username,password);// setter flAutorization on Service.

		if (result) {
			model.addAttribute("result","Hello "+username+". Glad to see you again");
		} else {
			model.addAttribute("loginText", loginForm);
			model.addAttribute("result","User name or Password incorrect!<br> Try again.");
		}
		return "MaintenanceSignInPage";
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
		model.addAttribute("result", outRes);// text on page for testing
		return "MaintenanceUpdatePage";// return too page after action
	}

	////--------------------- Search in to data base by category witch levelOfDifficulty or free ( returned all questions from DB) -----//
	private int rowsCounter = 0;
	//
	@RequestMapping({ "/search_actions" })
	public String SearchProcessingPage(String category,	String levelOfDifficulti, Model model) {
		try{
			List<String> resultDB = maintenanceService.SearchAllQuestionInDataBase(category, Integer.parseInt(levelOfDifficulti));
			StringBuffer str = new StringBuffer();
			str.append("<table><tr><th>N</th><th>Question</th><th>Category</th><th>Level of difficulty</th></tr>");
			////
			for (String questionLine : resultDB) {				
				String[] element = questionLine.split(IMaintenanceService.DELIMITER);
				str.append("<tr onclick='test(" + element[1] + ")'>"
						+ "<td> "+ element[1]+ " </td>"
						+ "<td> "+ element[0]+ " </td>"
						+ "<td> "+ element[3]+ " </td>"
						+ "<td> "+ element[4]+ " </td></tr>");
				rowsCounter++;				
			}
			////
			str.append("</table><br>");
			model.addAttribute("result", str.toString()); 
		}catch(Exception e){
			model.addAttribute("result", "Bad Balance :( ");
		}
		return "MaintenanceUpdatePage";// return too page after action
	}

	//------- getting question and attributes for change ---------// Begin //
	@RequestMapping({ "/fillFormForUpdateQuestion" })
	public String getInformationDB(String questionID, Model model) {		
		try {
			if (rowsCounter > (Integer.parseInt(questionID) - 1)) {
				StringBuffer stringBufferOutResult = new StringBuffer();
				String tempQueryRessult = maintenanceService.getQuestionById(questionID);
				String[] dataFromTables = tempQueryRessult.split(IMaintenanceService.DELIMITER);
				//
				stringBufferOutResult.append("<form name='formTag' action='updateOneQuestion' ><br>Question Number:  "
						+ dataFromTables[0] + ".<br>");
				//
				stringBufferOutResult.append("Question text<br><textarea name='questionText' rows='7'>"
						+ dataFromTables[1] + "</textarea><br>");
				//
				if(dataFromTables.length != 6 && dataFromTables.length == 11){
					stringBufferOutResult.append("Image Link<br><textarea name='imageLinkText'>"
							+ dataFromTables[3] + "</textarea><br>");
					//
					stringBufferOutResult.append("Question Category<br> <input type='text' name='category' value='"
							+ dataFromTables[4] + "'><br>");
					String check = dataFromTables[5];
					int checkRes = Integer.parseInt(check);
					//
					stringBufferOutResult.append("Question Level<br>");
					if (checkRes == 1) {
						stringBufferOutResult.append("<input checked='checked' type='radio' name='levelOfDifficulty' value=1>1");
					} else {
						stringBufferOutResult.append("<input type='radio' name='levelOfDifficulty' value=1>1");
					}
					if (checkRes == 2) {
						stringBufferOutResult.append("<input checked='checked' type='radio' name='levelOfDifficulty' value=2>2");
					} else {
						stringBufferOutResult.append("<input type='radio' name='levelOfDifficulty' value=2>2");
					}
					if (checkRes == 3) {
						stringBufferOutResult.append("<input checked='checked' type='radio' name='levelOfDifficulty' value=3>3");
					} else {
						stringBufferOutResult.append("<input type='radio' name='levelOfDifficulty' value=3>3");
					}
					if (checkRes == 4) {
						stringBufferOutResult.append("<input checked='checked' type='radio' name='levelOfDifficulty' value=4>4");
					} else {
						stringBufferOutResult.append("<input type='radio' name='levelOfDifficulty' value=4>4");
					}
					if (checkRes == 5) {
						stringBufferOutResult.append("<input checked='checked' type='radio' name='levelOfDifficulty' value=5>5");
					} else {
						stringBufferOutResult.append("<input type='radio' name='levelOfDifficulty' value=5>5");
					}				
					stringBufferOutResult.append("<br>Correct Answer<br>");			
					stringBufferOutResult.append("<input type='text' name='correctAnswer' value='" + dataFromTables[6] + "' size='2'><br>");
					//
				}
				if(dataFromTables.length > 6 && dataFromTables.length == 11){
					stringBufferOutResult.append("<br> Answers for Question <br>");
					stringBufferOutResult.append(" A. <textarea name='at1'>"
							+ dataFromTables[7] + "</textarea><br>");
					stringBufferOutResult.append(" B. <textarea name='at2'>"
							+ dataFromTables[8] + "</textarea><br>");
					stringBufferOutResult.append(" C. <textarea name='at3'>"
							+ dataFromTables[9] + "</textarea><br>");
					stringBufferOutResult.append(" D. <textarea name='at4'>"
							+ dataFromTables[10] + "</textarea><br>");
				}	
				stringBufferOutResult.append("<input type='text' name='questionID' value='" + dataFromTables[0] + "' style='visibility: hidden;'><br>");
				stringBufferOutResult.append("<input type='submit' value='Change Question'>");
				stringBufferOutResult.append("</form>");
				//
				model.addAttribute("result", stringBufferOutResult.toString());
			} else {
				model.addAttribute("result","Number of Question is Wrong. Input real number of question");
			}
		} catch (NumberFormatException e) {
			//e.printStackTrace();
			System.out.println("catch create form for update question FES");
			model.addAttribute("result","Number of Question is Empty. Input number of question");
		}				
		return "MaintenanceUpdatePage";
	}
	//------- getting question and attributes for change ---------// END //

	// -------- method delete from DB Tables Question and Answer By ID -----// Begin //
	@RequestMapping({ "/deleteAction" })
	public String deleteFromTablesQuestionAndAnswer(String questionIDdelete,
			Model model) {
		String tempQueryRessult = maintenanceService
				.deleteQuetionById(questionIDdelete);
		model.addAttribute("result", tempQueryRessult);// out text to Page
		return "MaintenanceUpdatePage";
	}
	// -------- method delete from DB Tables Question and Answer By ID -----// END //
	// 
	// use case 3.3.3 Bulk entering test data
	// --------- adding questions from txt file on user computer -----//  Begin  //
	@SuppressWarnings("resource")
	@RequestMapping({ "/add_from_file_actions" })
	public String addFromFileProcessingPage(String textfromfile, Model model) {
		//questionText----imageLink----category----levelOfDifficulti----answer1----answer2----answer3----answer4----correctAnswerChar----questionIndexNumber
		System.out.println("----------------------------------------------------------------------\n"+textfromfile);
		List<String> res = new ArrayList<String>();
		String line;
		BufferedReader input;
		try {
			input = new BufferedReader(	new FileReader(	"D:/developer-workspaces/GitHub/repository/tr-project/text.txt"));
			while ((line = input.readLine()) != null) {
				res.add(line);
			}
			boolean actionRes = maintenanceService.FillDataBaseFromTextResource(res);
			//
			model.addAttribute("result", " Adding Questions is - " + actionRes);
		} catch (ClassCastException e) {
			model.addAttribute("result", "User is not authorized"); // out text to Page
		} catch (FileNotFoundException e) {
			model.addAttribute("result", "Exception: file not found");
		} catch (IOException e) {
			model.addAttribute("result", "Exception: can't read from file");
		}
		return "MaintenanceSignInPage";// return too page after action
	}
	// --------- adding questions from any.txt file on user computer -----//  END  //

	// ----------------------Building Questions Page --------------//  Begin  //
	@RequestMapping({ "/moduleAutoCreationQuestion" })
	public String BuildingQuestionsPage(Model model) {
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
				model.addAttribute("formCategory", checkedFlyButtons.toString());
			} else {

			}
		} catch (Exception e) {
			System.out.println("no data base found");
			model.addAttribute("formCategory", "no data base found");
		}
		return "MaintenanceSignInPage";// return too page after action
	}
	// ----------------------Building Questions Page --------------//  Begin  //

	// -------------- Module For Building Questions ----------------////
	@RequestMapping({ "/moduleForBuildingQuestions" })
	public String ModuleForBuildingQuestions(String category,
			String nQuestions, Model model) {
		int nQuest = Integer.parseInt(nQuestions);

		boolean flagAction = maintenanceService.ModuleForBuildingQuestions(
				category, nQuest);

		if (flagAction) {
			model.addAttribute("result", "your request: to build " + nQuestions
					+ " questions made");
		} else {
			model.addAttribute("result", "your request: to build " + nQuestions
					+ " questions failed");
		}
		return "MaintenanceSignInPage";// return too page after action
	}
}

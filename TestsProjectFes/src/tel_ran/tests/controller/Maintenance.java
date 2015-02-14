package tel_ran.tests.controller;

import java.io.BufferedReader;
import java.io.FileReader;
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
@RequestMapping({"/","/Maintenance"})
public class Maintenance {
	boolean flAdminAuthorized = false;
	@Autowired// аннотация делающая автоматический  вызов через тег в бин.хмл файле.
	IMaintenanceService maintenanceService;// Это связь интерфейсов , эта переменная дает нам все методы общего интерфейса 
	/*@RequestMapping({"/Maintenance"})
	public String authorize(){return "MaintenanceSignInPage";}	*/
	/** когда нажимаем на кнопку add question! этот метод только вызывает страницу adding page здесь писать ничего не надо!! */
	@RequestMapping({"/maintenanceadd"})
	public String addingPage() {return "MaintenanceAddingPage";}
	/** когда нажимаем на кнопку  update question!  этот метод только вызывает страницу updating view table page здесь писать ничего не надо!! !! */
	@RequestMapping({"/update"})
	public String UpdatePage(){	return "MaintenanceUpdatePage";}

	/** когда нажимаем на кнопку add from file! этот метод только вызывает страницу adding page  здесь писать ничего не надо!!!! */
	@RequestMapping({"/addfromfile"})
	public String specificDataPage(){return "MaintenanceAutoComplete";}

	//use case 3.3.3 Test Maintenance
	/*3.3.1.	Adding test question
	Pre-Conditions:
	1.	The System is running up
	2.	The Administrator (the user with username “admin” and password “12345.com”) is signed in
	Normal Flow:
	1.	The System shows internal link with the text “create new question” (under the link there will be table with existing questions but it is used in this flow)
	2.	User presses the link
	3.	The system shows form for filling the following data:
	•	Question text (typing)
	•	Category (selection)
	•	Complexity level (selection from 1 to 5)
	•	4 answers with numbers (1-4)
	•	Number of the right question
	4.	User types/select required data and press submit button
	5.	The System saves the question data in the Database with message “ the question <question text> has been added successfully” 
	 */
	/** ДОБАВЛЕНИЕ НОВОГО ВОПРОСА В БАЗУ ДАННЫХ */
	@RequestMapping({"/add_actions"})
	public String addProcessingPage(String questionText,String descriptionText,String category,int question_level,
			String answer_text_1,String answer_text_2,String answer_text_3,String answer_text_4 ,int trueAnswerNumber,Model model){
		/**Имена переменных приходящих в этот метод !!! ВАЖНО!!! Это Аттрибут тага: name="" должно быть соответствие полное!!!
		 * количество неограничено.
		 * Имя  же самого метода public String addProcessingPage() и других методов этого класса,
		 * пока нигде не применялось и существенно роль походу не играет//спросить Юрия//
		 */
		List<String> answer = new ArrayList<String>();
		answer.add(answer_text_1);		answer.add(answer_text_2);
		answer.add(answer_text_3);		answer.add(answer_text_4);

		boolean actionRes = false; // флаг работы апликации
		try {
			actionRes = maintenanceService.createQuestion(questionText,descriptionText,
					category, question_level, answer, trueAnswerNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// альтернативный путь , если ответа от сервиса небыло по любым причинам.
		if (actionRes) {
			// написать альтернативный путь !!!
			model.addAttribute("result","<p> Question successfully added</p>");// вывод текста
			actionRes=false;
		}else{
			// написать альтернативный путь !!!
			model.addAttribute("result","<p> Error adding the question, the question already exists. Try again</p>");// вывод текста
		}
		/**ВАЖНО!!! Принимает параметер только стринг и стрингБуфер!!!, метод фреймворка Спринг!! 
		 * Метод вывода текста на  ХТМЛ  страницу через джава скрипт  model.addAttribute("result",ВАЖНО!! чтобы имя написанное в методе как 1 параметр, 
		 * И написанное на ХТМЛ странице в скипте имя в фигурных скобках  document.write("${result}"); совпадали полностью !!!
		 * */
		return "MaintenanceAddingPage"; // return too page after action
	}	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//use case 3.3.2 Update Test Question
	/*Pre-Conditions:
		1.	The System is running up
		2.	The Administrator (the user with username “admin” and password “12345.com”) is signed in
		Normal Flow:
		1.	The user requests all questions containing specific text part
		2.	The System shows table with scrolling containing all questions sorted by the questions text
		3.	User selects required for update question
		4.	The System shows form with filled data (see Adding Test Question Use Case)
		5.	User updates the question data (There may be updated any fields) and press submit button
		6.	The System updates the question data in the Database
	 */
	/***  ОБНОВЛЕНИЕ ВОПРОСОВ действия разрешены Администратору системы  */
	@RequestMapping({"/update_actions"})
	public String updateProcessingPage(String questionText,String descriptionText,
			String category,int question_level,
			String answer_text_1,String answer_text_2,String answer_text_3,String answer_text_4 ,
			int trueAnswerNumber,String questionID,Model model){	
		//when you submit, form sendin query to DB, and update question witch this ID 		
		List<String> answer = new ArrayList<String>();
		answer.add(answer_text_1);		answer.add(answer_text_2);
		answer.add(answer_text_3);		answer.add(answer_text_4);
		boolean result = maintenanceService.UpdateQuestionInDataBase(questionID, questionText, descriptionText, category, question_level, answer, trueAnswerNumber);
		String outRes = "";
		if(result){
			outRes = "<p>Changed Question successfully added</p>";
		}else{
			outRes = "<p>Error Question no changed!!!</p>";
		}		
		model.addAttribute("result", outRes);// text on page for testing
		return "MaintenanceUpdatePage";// return too page after action		
	}	
	/***  ПОИСК ВОПРОСОВ: действия разрешены Администратору системы  */
	private int rowsCounter=0;
	@RequestMapping({"/search_actions"})
	public String searchProcessingPage(String category, String free_question, Model model){		
		/** это метод обновления вопроса, принимает String free_question: Это текст в свободной форме, для поиска вопроса.*/
		List<String> resultDB = maintenanceService.SearchQuestionInDataBase(free_question, category);	
		StringBuffer str = new StringBuffer();
		str.append("<table><tr><td>CATEGORY</td><td>QUESTION</td></tr>");		
		for( String questionLine :resultDB ){	
			String line = questionLine.toString();
			String[] element = line.split("----");
			str.append("<tr onclick='test("+element[0]+")'><td>"+element[3]+"</td><td>"+element[0]+". "+element[1]+"</td></tr>");
			rowsCounter++;
		}	
		str.append("</table><br>");			
		model.addAttribute("result", str.toString());// text on page for testing
		return "MaintenanceUpdatePage";// return too page after action		
	}
	/** Промежуточный поиск вопроса для заполнения формы для изменения вопроса : действия системы*/
	@RequestMapping({"/getArrayFromDB"})
	public String getInformationDB(String questionID,Model model){
		try{
		if(rowsCounter > (Integer.parseInt(questionID)-1)){
			StringBuffer  stringBufferOutResult = new StringBuffer();
			String tempQueryRessult = maintenanceService.getQuestionById(questionID);		
			String[] dataFromTables = tempQueryRessult.split("----");

			stringBufferOutResult.append("<form name='formTag' action='update_actions' ><br>Question Number:  "+dataFromTables[0]+".<br>");
			stringBufferOutResult.append("Question text<br><textarea name='questionText' rows='7'>"+dataFromTables[1]+"</textarea><br>");
			stringBufferOutResult.append("Description text<br><textarea name='descriptionText'>"+dataFromTables[2]+"</textarea><br>");
			stringBufferOutResult.append("Question Category<br> <input type='text' name='category' value='"+dataFromTables[3]+"'><br>");

			String check = dataFromTables[4];// the true answer number
			int checkRes = Integer.parseInt(check);// parse string to integer
			stringBufferOutResult.append("Question Level<br>");		
			if(checkRes == 1){
				stringBufferOutResult.append("<input checked='checked' type='radio' name='question_level' value=1>1");
			}else {stringBufferOutResult.append("<input type='radio' name='question_level' value=1>1");}
			if(checkRes == 2){
				stringBufferOutResult.append("<input checked='checked' type='radio' name='question_level' value=2>2");
			}else {stringBufferOutResult.append("<input type='radio' name='question_level' value=2>2");}
			if(checkRes == 3){
				stringBufferOutResult.append("<input checked='checked' type='radio' name='question_level' value=3>3");			
			}else {stringBufferOutResult.append("<input type='radio' name='question_level' value=3>3");}
			if(checkRes == 4){
				stringBufferOutResult.append("<input checked='checked' type='radio' name='question_level' value=4>4");
			}else {stringBufferOutResult.append("<input type='radio' name='question_level' value=4>4");}
			if(checkRes == 5){
				stringBufferOutResult.append("<input checked='checked' type='radio' name='question_level' value=5>5");
			}else {stringBufferOutResult.append("<input type='radio' name='question_level' value=5>5");}
			stringBufferOutResult.append("<br> Answers for Question <br>");	
			stringBufferOutResult.append(" Answer 1 <textarea name='answer_text_1'>"+dataFromTables[5]+"</textarea><br>");
			stringBufferOutResult.append(" Answer 2 <textarea name='answer_text_2'>"+dataFromTables[7]+"</textarea><br>");
			stringBufferOutResult.append(" Answer 3 <textarea name='answer_text_3'>"+dataFromTables[9]+"</textarea><br>");
			stringBufferOutResult.append(" Answer 4 <textarea name='answer_text_4'>"+dataFromTables[11]+"</textarea><br>");		

			stringBufferOutResult.append(" Please input number a right question answer<br>");
			String trueAnswNum = "0"; 
			if(dataFromTables[6].equals("true"))
				trueAnswNum = "1";
			if(dataFromTables[8].equals("true")) 
				trueAnswNum = "2";
			if(dataFromTables[10].equals("true"))
				trueAnswNum = "3";
			if(dataFromTables[12].equals("true"))
				trueAnswNum = "4";

			stringBufferOutResult.append("<input	type='text' name='trueAnswerNumber' value='"+trueAnswNum +"' size='2'><br>");
			stringBufferOutResult.append("<input	type='text' name='questionID' value='"+dataFromTables[0]+"' style='visibility: hidden;'><br>");
			stringBufferOutResult.append("<input type='submit' value='Change Question'>");
			stringBufferOutResult.append("</form>");
			stringBufferOutResult.append("<form action='deleteAction'>Delete Question number:<input type='submit' name='questionid' value='"+dataFromTables[0]+"'></form>");

			model.addAttribute("result",stringBufferOutResult.toString());// вывод текста	
		}else{
			model.addAttribute("result","Number of Question is Wrong. Input real number of question");// вывод текста wrong number format	
		}
		}catch(NumberFormatException e){
			model.addAttribute("result","Number of Question is Empty. Input number of question");// вывод текста	empty input 
		}
		return "MaintenanceUpdatePage";		
	}
	/////////////////////////////////// method delete from DB Tables Question and Answer By ID /////////////////////////////
	////////////////////////////////// returned to MaintenanceUpdatePage. ///////////////////////////////////
	@RequestMapping({"/deleteAction"})
	public String deleteFromTablesQuestionAndAnswer(String questionid, Model model){
		String tempQueryRessult = maintenanceService.deleteQuetionById(questionid);
		model.addAttribute("result",tempQueryRessult);// вывод текста		
		return "MaintenanceUpdatePage";		
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//use case 3.3.3 Bulk entering test data
	/*Pre-Conditions:
		1.	The System is running up
		2.	The Administrator (the user with username “admin” and password “12345.com”) is signed in
		3.	The file with proper question data exists in the file system on the WEB Server
		Normal Flow:
		1.	The System shows internal link with text “Adding questions from file”
		2.	The User press on the link
		3.	The System shows file browser with button “Add”
		4.	The user choose the file with questions data and press button “Add”
		5.	The System reads the selected file, parsers data and adds new questions to Database. 
		6.	The System shows the number of the added questions. Note: Added questions  may be seen in the table after browser refresh*/
	/** ДОБАВЛЕНИЕ БОЛЬШОГО КОЛИЧЕСТВА ВОПРОСОВ ОДНОВРЕМЕННО С ПОМОЩЬЮ ФАЙЛА :действия разрешены Администратору системы */
	@SuppressWarnings("resource")
	@RequestMapping({"/add_from_file_actions"})
	public String addFromFileProcessingPage(String file_name, Model model){		
		List<String> res = new ArrayList<String>();
		String line; 
		BufferedReader input;
		try {
			input = new BufferedReader(new FileReader(file_name));			
			while((line = input.readLine()) != null){ 
				res.add(line);
			}
			boolean actionRes = maintenanceService.FillDataBaseFromTextResource(res);	
			model.addAttribute("result"," Adding Questions is - "+actionRes);// вывод текста
		} catch (Exception e) {			
			model.addAttribute("result","File not Found");// вывод текста
		} 	
		return 	"MaintenanceSignInPage";// return too page after action
	}
}

package tel_ran.tests.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import tel_ran.tests.services.common.IPublicStrings;
import tel_ran.tests.strings.IMessages;
import tel_ran.tests.users.Visitor;

@Controller
@Scope("session") /*session timer default = 20min*/
@RequestMapping({"/"})
@SessionAttributes({"role","visitor"})
public class QuestionsManagement extends AController {
	
	@RequestMapping({"/autoGeneration"})
	public String maintenanceOtherResurses() {
						
		return "questions/auto_generation";
		
	}	
	
	// -------------------------- CREATING QUESTIONS ------------------------------------------------------------ //
	
	// - AUTO ----------------------------------------------------------------------------------------------------//
	
	/**
	 * CREATE questions by auto generation
	 * @param category = list of metaCategories
	 * @param nQuestions = total number of questions to create
	 * @param levelOfDifficulty = list of dif.Level 
	 * @param model
	 */
	@RequestMapping({"/callAutoGeneration"})
	public String moduleForBuildingQuestions(@ModelAttribute Visitor visitor, String category, String nQuestions, Model model) {	
//		System.out.println("In new controller " + visitor.getRoleNumber());
//		boolean actionRes = false;
//		try {
//			actionRes = visitor.handler.generateAutoQuestions(category, null, 5, nQuestions);			
//		} catch (Exception e) {
//			System.out.println("catch call maintenanceaction from FES moduleForBuildingQuestions");//----------------------------------------------------sysout
//			//e.printStackTrace();
//		}
//				
//		StringBuilder result = new StringBuilder("<br><p class='informationTextP'>");
//		result.append(IMessages.YOUR_REQUEST).append(" |").append(nQuestions).
//			append("| ").append(IMessages.QUESTION_ITEMS).append(" ");
//		if(actionRes){				
//			result.append(IMessages.MADE); 		
//		}else{			
//			result.append(IMessages.FAILED);
//		}
//		result.append("</p>");
//		model.addAttribute(RESULT, result.toString());
////		addTextHTML(result.toString());	
//		
		return "questions/auto_generation";
	}
		
	@RequestMapping(value="/categoryList", method=RequestMethod.POST)  
	public @ResponseBody JsonResponse handlerCode(@ModelAttribute Visitor visitor, HttpServletRequest request) {	
		JsonResponse res = new JsonResponse(); 
		if(AController.metaCategoryListHTML==null || AController.metaCategoryListHTML.length()<3) {
			AController.metaCategoryListHTML = getOptionsFromList(visitor.handler.getPossibleMetaCaterories(),
					IMessages.NO_AUTO_CATEGORIES);		
		} 		
		String list = AController.metaCategoryListHTML;		
		res.setStatus("SUCCESS"); 
		res.setResult(list);
		return res;
	}
	
	
	// - MANUAL ----------------------------------------------------------------------------------------------------//

	@RequestMapping({ "/add_question" })
	public String addingQuestion(@ModelAttribute Visitor visitor, Model model) {	
		List<String> userCategories;
		String result;
		try {
			userCategories = visitor.handler.getUsersCategories();
			result = getOptionsFromList(userCategories, getStringInBold(IMessages.NO_CATEGORIES_IN_DB));
		} catch  (Exception e) {
			result = getStringInBold(IMessages.NO_DATA_BASE);	
			System.out.println("no data base found");//----------------------------------------------------sysout
		}
		
		model.addAttribute("categoryList", result);
		
		return "questions/create_question"; 
	}	
	
	@RequestMapping(value = "/add_question_action" , method = RequestMethod.POST)
	@ResponseBody
	public String addQuestionAction(@ModelAttribute Visitor visitor, String descriptionText, String codeText,
			String  category1, String metaCategory, String category2, String  compcategory, String levelOfDifficulty, 
			String fileLocationLink, String correctAnswer, String at1, String at2, String at3, String at4,  Model model)
	{			
				
		// creating list of answers
		int countAnswersOptions = 0;
		List<String> answers = new ArrayList<>();
		String[] answerOptions = {at1, at2, at3, at4};
		
		for(int i = 0; i < answerOptions.length; i++) {			
			if(answerOptions[i]!=null && answerOptions[i].length() > 0) {
				countAnswersOptions++;
				answers.add(answerOptions[i]);
			}
		}
		
		// check if the list of answer options is correct
		String message = "";
		boolean isError = false;		
		if(countAnswersOptions==0) {
			answers = null;
			if(metaCategory==IPublicStrings.COMPANY_AMERICAN_TEST) { 
				message = "<p class='outTextInfo'> Error adding the question. You should fill in 2 or more answer options</p>";
				isError = true;
			}			
		} else if (countAnswersOptions == 1) {
			message = "<p class='outTextInfo'> Error adding the question. Should be more than 1 answer options</p>";
			isError = true;
		}
		
		boolean actionRes = false;
		
		if(!isError) {
		
			//check if the new category was added		
			if(category1.startsWith("Create company category")) {
				category1 = compcategory;
			}
					
			//check if the new category2 was added	
			String repCategory = null;
			if(category2!=null) {
				repCategory = category2.replaceAll(",", "").replaceAll("none", "");
			}
			
			//check level of difficulty
			int lvl = 3;
			try {				
				if(levelOfDifficulty!=null)					
					lvl = Integer.parseInt(levelOfDifficulty);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("maintenance addProcessingPage :method: Exception");//----------------------------------------------------sysout
			}
			
			
			//ADDING QUESTION			
			actionRes = visitor.handler.createNewQuestion(fileLocationLink, metaCategory, category1, lvl, answers, correctAnswer, 
					countAnswersOptions, descriptionText, codeText, repCategory);
						
					
			if (actionRes) {
				message = "<p class='outTextInfoAdded'> Question successfully added</p>";			
				actionRes = false;
			} else {
				// write alternative flow !!!
				message = "<p class='outTextInfo'> Error adding the question, the question already exists. Try again</p>";// out
			}
			
						
		}
		System.out.println(message);
		model.addAttribute("result",message);
		addingQuestion(visitor, model);			
		return message; // return too page after action	
	}
	
	// ------------------------------------------------------------------------------------------------------- //
	
	// -------------------------- QUESTIONS UPDATE AND VIEW --------------------------------------------------- //
	
	@RequestMapping({ "/questions_update" })
	public String updatePage(@ModelAttribute Visitor visitor, String view_mode, Model model) {
		
		List<String> userCategories;
		String result;
		try {
			userCategories = visitor.handler.getUsersCategories();
			result = getOptionsFromList(userCategories, getStringInBold(IMessages.NO_CATEGORIES_IN_DB));
		} catch  (Exception e) {
			result = getStringInBold(IMessages.NO_DATA_BASE);	
			System.out.println("no data base found");//----------------------------------------------------sysout
		}
		
		model.addAttribute("categoryList", result);
				
		String res = visitor.handler.getAllQuestionsList(view_mode);	
		System.out.println(res);
		model.addAttribute(RESULT, res);		
		
		return "questions/update_page";
	}
	
	@RequestMapping({"/question_see" + "/{questId}"})
	public String seeQuestion(@ModelAttribute Visitor visitor, @PathVariable long questId,  Model model) {
		String question = visitor.handler.getQuestionById(questId);
		model.addAttribute(QUESTION, question);
		// TO DO !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		
		return "questions/update_page";
	}
	
}

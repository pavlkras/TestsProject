package tel_ran.tests.controller;

import handlers.IHandler;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import tel_ran.tests.controller.AbstractAdminActions.JsonResponse;
import tel_ran.tests.strings.IMessages;
import tel_ran.tests.users.Visitor;

@Controller
@Scope("session") /*session timer default = 20min*/
@RequestMapping({"/"})
@SessionAttributes({"role","visitor"})
public class QuestionsManagement extends AController {
	
	@RequestMapping({"/autoGeneration"})
	public String maintenanceOtherResurses(@ModelAttribute Visitor visitor) {
		System.out.println("In new controller " + visitor.getRoleNumber() + " " + visitor.handler.getClass().toString());				
		return "questions/auto_generation";
		
	}	
	
	/**
	 * CREATE questions by auto generation
	 * @param category = list of metaCategories
	 * @param nQuestions = total number of questions to create
	 * @param levelOfDifficulty = list of dif.Level 
	 * @param model
	 */
	@RequestMapping({"/callAutoGeneration"})
	public String moduleForBuildingQuestions(@ModelAttribute Visitor visitor, String category, String nQuestions, Model model) {	
		System.out.println("In new controller " + visitor.getRoleNumber());
		boolean actionRes = false;
		try {
			actionRes = visitor.handler.generateAutoQuestions(category, null, 5, nQuestions);			
		} catch (Exception e) {
			System.out.println("catch call maintenanceaction from FES moduleForBuildingQuestions");//----------------------------------------------------sysout
			//e.printStackTrace();
		}
				
		StringBuilder result = new StringBuilder("<br><p class='informationTextP'>");
		result.append(IMessages.YOUR_REQUEST).append(" |").append(nQuestions).
			append("| ").append(IMessages.QUESTION_ITEMS).append(" ");
		if(actionRes){				
			result.append(IMessages.MADE); 		
		}else{			
			result.append(IMessages.FAILED);
		}
		result.append("</p>");
		model.addAttribute(RESULT, result.toString());
//		addTextHTML(result.toString());	
		
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
	
	


}

package tel_ran.tests.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import tel_ran.tests.services.common.IPublicStrings;
import tel_ran.tests.users.Visitor;
import tel_ran.tests.utils.AppProps;

@Controller
@Scope("session") /*session timer default = 20min*/
@RequestMapping({"/"})
@SessionAttributes({"role","visitor"})
public class TestManagement extends AController {
	
	@Autowired
	AppProps appPropps;
	

	@RequestMapping({"/testGeneration"})
	public String testGeneration(@ModelAttribute Visitor visitor, Model model) {
		StringBuilder categoryHtmlText = new StringBuilder();
		
		List<String> resultCategory = visitor.handler.getMetaCategoriesFromDB();			
		
		categoryHtmlText.append("<table class='table_ind'><tr><th colspan='2'>Category of Question:</th><th>Level of difficulty</th></tr>");
		for(String catBox:resultCategory){					
			categoryHtmlText.append("<tr class='tr_ind'>");
			categoryHtmlText.append("<td>").append(catBox).append(":</td>");
			categoryHtmlText.append("<td><input class='category' type='checkbox' name='category' value='").
				append(catBox).append("' /></td>");
			categoryHtmlText.append("<td><select name='level_num' disabled>").
				append("<option value='1'>1</option>").append("<option value='2'>2</option>").
				append("<option value='3'>3</option>").append("<option value='4'>4</option>").
				append("<option value='5'>5</option>").append("</select></td></tr>");						
		}
		categoryHtmlText.append("</table>");
		model.addAttribute("categoryFill", categoryHtmlText.toString());
		String userQuestions = visitor.handler.getAllQuestionsList("user");
		model.addAttribute("userQuestions", userQuestions);
		model.addAttribute("token", visitor.getToken());
	
		return "company/CompanyGenerateTest";
	}
	
	/**
	 * TEST TEMPLATES VIEW
	 */
	@RequestMapping({"/test_templates"})
	public String testTemplatesView(@ModelAttribute Visitor visitor, Model model) {
		model.addAttribute("token", visitor.getToken());
		
		//list of custom categories of the company
		model.addAttribute("companyCategories", visitor.handler.getUsersCategories());
		
		// list of auto generated categories (metaCategory + category1) in JSON format 
		// see IHandler for details
		model.addAttribute("autoCategories", visitor.handler.getPossibleAutoCaterories());
		
		// list of custom categories created by Admin (category1 + category2 + metaCategory) in JSON format		
		model.addAttribute("commonCategories", visitor.handler.getCommonCustomCategories());	
		
		return "tests/templates";
	}
	
	/**
	 * CREATE NEW TEST for person
	 * 
	 * @param metaCategory 
	 * @param category1 - for programming language
	 * @param level_num
	 * @param personId
	 * @param personName
	 * @param personSurname
	 * @param personEmail 
	 * @param selectCountQuestions
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping({"/add_test"})
	public String createTest(@ModelAttribute Visitor visitor, String category, String category1, String level_num, String personId, 
			String personName, String personSurname,String personEmail, String selectCountQuestions, String questionsId,
			Model model, HttpServletRequest request) {	
		
		String url = request.getRequestURL().toString();
		String pathToServer = url.replace("add_test", "jobSeeker_test_preparing_click_event");
		////
		System.out.println("personId--" + personId);
		System.out.println("level_num--"+level_num);//-------------------------------sysout
		System.out.println("category--"+category);//-------------------------------sysout
		if(category1!=null)
			System.out.println("language -- " + category1);		
		List<Long> questionsIdList = null;
		if(questionsId!=null) {
			questionsIdList = new LinkedList<Long>();
			String[] qIds = questionsId.split(",");
			for(String q : qIds) {
				questionsIdList.add(Long.parseLong(q));
			}
		}
		
		String[] answer = visitor.handler.createNewTest(questionsIdList, category, category1, level_num, selectCountQuestions, 
				personId, personName, personSurname, personEmail, pathToServer);
		
		
		
		
//		List<Long> listIdQuestions = companyService.createSetQuestions(metaCategory, category1, level_num, counterOfQuestions);
//		int personID = companyService.createPerson(Integer.parseInt(personId), personName, personSurname,personEmail);
//		boolean isCreated = companyService.CreateTest(listIdQuestions, personID, password, metaCategory, level_num);	//------------ TO DO levels change !!	add company name for
		////
				
		String messageText;
		switch(answer[0]) {			
			case "0" : 
				messageText = "<a href='" + answer[1] + "'><h2><b>Test link</b></h2></a><br>" + "<H1>" + 
						IPublicStrings.ERRORS_TEXT[0] + "</H1>";
					break;
			case "1" :
			case "2" :
			case "3" :
				messageText = "<H1>" + IPublicStrings.ERRORS_TEXT[Integer.parseInt(answer[0])] + "</H1>";
				break;
			case "5" :					
				messageText = "<a href='" + answer[1] + "'><h2><b>Test link</b></h2></a><br>" + "<H1>" + 
						IPublicStrings.ERRORS_TEXT[Integer.parseInt(answer[0])] + "</H1>";
				break;
			default :
				messageText = "<H1>" + IPublicStrings.ERRORS_TEXT[4] + "</H1>";			
		}
			
		model.addAttribute("myResult", messageText);	
		model.addAttribute("pathForTest", pathToServer);
			
		return "company/Company_TestLink";
	}
	
	
	/*-------------Viewing test results----------------*/
	@RequestMapping({"/view_results"})
	public String viewResults(@ModelAttribute Visitor visitor, Model model){
			model.addAttribute("token", visitor.getToken());
		return "company/ViewTestsResults";
	}	
	
	@RequestMapping({"/view_template_based_results"})
	public String viewTemplateBasedResults(@ModelAttribute Visitor visitor, Model model){
			model.addAttribute("token", visitor.getToken());
		return "company/ViewTestsResultsTemplateBased";
	}	
	/*------------END Viewing test results-------------*/
	
	@RequestMapping(value="/create_template")
	public String createTemplate(@ModelAttribute Visitor visitor, Model model){
		model.addAttribute("token", visitor.getToken());
		return "tests/create_template";
	}
	
	@RequestMapping(value="/test_by_template")
	public String createTestByTemplate(@ModelAttribute Visitor visitor, Model model) {
		model.addAttribute("token", visitor.getToken());
		String linkForTest = this.appPropps.getClientname().concat("/jobSeeker_test_preparing_click_event");
		System.out.println("link test - "+linkForTest);
		model.addAttribute("link", linkForTest);
		return "tests/create_test_by_template";
	}
	
	
	
}

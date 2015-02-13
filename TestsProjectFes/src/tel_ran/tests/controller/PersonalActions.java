package tel_ran.tests.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import tel_ran.tests.services.interfaces.IPersonalActionsService;

@Controller
@Scope("session")
@RequestMapping({"/","/PersonalActions"})
public class PersonalActions {
	
	int personId;
	private String categoryName = null;
	private String chosenQuestionsQuantity = null;
	
	@Autowired
	IPersonalActionsService personalService;

	@RequestMapping({"/PersonalActions"})
	public String signIn(){
		return "PersonalSignIn";
	}
	
	@RequestMapping(value = "/Personal_result_view")
	public String allCategoriesSelection(Model model){
		
		List<String> allCategories = personalService.getCategoriesList();
		model.addAttribute("categoryNames", allCategories);		
		return "Personal_result_view";
	}
	
	@RequestMapping({"add_questions_count"})
	public String addCategoryMaxQuestionsNumber(String catName, Model model){
		categoryName = catName;
		String questionsCountByCategory = personalService.getMaxCategoryQuestions(catName);
		model.addAttribute("catName", categoryName);
		model.addAttribute("questionsCountByCategory", questionsCountByCategory);
		return "Personal_questions_count_view";
	}
	
	@RequestMapping({"test_preparing"})
	public String testPreparing(String qnumber, Model model){
		chosenQuestionsQuantity = qnumber;
		model.addAttribute("param1", categoryName);
		model.addAttribute("param2", chosenQuestionsQuantity);
		return "Personal_test_preparing_view";
	}
}

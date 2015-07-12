package tel_ran.tests.controller;

import javax.servlet.http.HttpServletRequest;

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
	@Autowired
	IPersonalActionsService personalService;
	////
	/*@RequestMapping({"/PersonalActions"})
	public String startPageToPerson(){ 		return "user/UserSignIn";     } */
	/*  3.2.4. Performing Test – Control Mode  */
	///------- this action is click on the link provided in the mail	
	
	@RequestMapping({"/jobSeeker_test_preparing_click_event"})
	//@RequestMapping({"/PersonalActions"})
	public String jobSeeker_test_preparing_click_event(HttpServletRequest request, Model model){	
		String outPage = "user/UserSignIn";
		String passwordForCreatedTest = request.getQueryString();
		String token = personalService.getToken(passwordForCreatedTest);
		if(token != null){
			model.addAttribute("token", token);
			outPage = "person/PersonTestPage";
		}
//		boolean isReady = personalService.GetTestForPerson(passwordForCreatedTest);
//		if(!isReady)// stub for create test page
		return outPage;
	}	
}
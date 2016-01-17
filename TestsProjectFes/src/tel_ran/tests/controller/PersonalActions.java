package tel_ran.tests.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import handlers.Guest;
import handlers.IHandler;


@Controller
@Scope("session")
@RequestMapping({"/","/PersonalActions"})
public class PersonalActions {
	
	String token;	
	
	@Resource(name="handler")
	IHandler handler;
	
	
	@RequestMapping({"/jobSeeker_test_preparing_click_event"})	
	public String jobSeeker_test_preparing_click_event(HttpServletRequest request, Model model){	
		String outPage = "user/UserSignIn";		
	
		String passwordForCreatedTest = request.getQueryString();				
		String token = ((Guest)handler).getTokenFromTest(passwordForCreatedTest);
		if(token != null){
								
			model.addAttribute("token", token);
			outPage = "person/PersonTestPage";
		}		
		
		return outPage;
	}	
	
	
}

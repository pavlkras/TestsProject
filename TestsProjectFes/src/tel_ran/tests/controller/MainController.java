package tel_ran.tests.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import tel_ran.tests.users.Visitor;


@Controller
@Scope("session") /*session timer default = 20min*/
@RequestMapping({"/"})
@SessionAttributes({"role","visitor"})
public class MainController extends AController {

			
	// ------------------  MAIN PAGE  ----------------------------------------------------- // 
	@RequestMapping({"/"}) // to index page !
	public String Index(){	
		return "index";      
	}
	
	// ------------------  LOG IN  -- SIGN UP --------------------------------------------- // 	
	// USER & ADMIN ------------------------------------------------------------------------//
	@RequestMapping(value = "/login")
	public String userlogin(@ModelAttribute Visitor visitor) {		
		
		return visitor.handler.logInPage();		
	}
	
	@RequestMapping(value = "/login_action", method = {RequestMethod.POST, RequestMethod.GET})
	public String login_action(@ModelAttribute Visitor visitor, String userEmail, String password, HttpServletRequest request, Model pageModel) {
						
		String sign_up = request.getParameter("sign_up");
		if(sign_up != null){			
			return "user/UserRegistration";
		}
		
		Map<String, Object> resultLogin = visitor.handler.logInAction(userEmail, password);
		
		if(resultLogin.containsKey("error")) {
			String response = (String)  resultLogin.get("error");
			pageModel.addAttribute("logedUser", response);
		} else {
			Visitor newVisitor = (Visitor) resultLogin.get("result");
			visitor = newVisitor;
			changeAttributes(newVisitor, pageModel);
			
		} 
//		System.out.println("I'm here!!! My visitor " + visitor.getCompanyName());
		return visitor.handler.logInPage();			
	}
	
	@RequestMapping(value = "/signup_action", method = RequestMethod.POST)
	public String signup_action(@ModelAttribute Visitor visitor, String firstname, String lastname,String email, String password, String nickname, Model model) {
		
		if (email == null || password == null) {				
			return "user/UserRegistration";
		}
		
		return visitor.handler.signUpAction(firstname, lastname, email, password, nickname, model);
	}
	
	// COMPANY ------------------------------------------------------------------------//
	@RequestMapping({"/companyLogin"})
	public String companyLogIn(@ModelAttribute Visitor visitor) {		
		return visitor.handler.companyLogInPage();  
	}
	
	@RequestMapping("/loginProcessing")
	public String companyLoginAction(@ModelAttribute Visitor visitor, String companyName, String password, Model model){
		
		Map<String,Object> resultLogin = visitor.handler.companyLoginAction(companyName, password);
		
		if(resultLogin.containsKey("error")) {
			String response = (String)  resultLogin.get("error");
			model.addAttribute(RESULT, response);
		} else {
			Visitor newVisitor = (Visitor) resultLogin.get("result");
			visitor = newVisitor;
			changeAttributes(newVisitor, model);
			String info = visitor.handler.getAccountInformation(visitor);
//			System.out.println(info);
			model.addAttribute(ACCOUNT_INFO, info);
			return "company/Company_main";
		} 
		
		return "companyLogin";	
		
	}
	
	@RequestMapping({"/companyadd"})
	public String addCompany() {
		return "company/Company_add_form";
	}
	
	@RequestMapping({"/add_processing"})
	public String addProcessing(@ModelAttribute Visitor visitor, String C_Name,String C_Site, String C_Specialization,String C_AmountEmployes,String C_Password,Model model) {
		if (C_Name == null || C_Password == null) {		
			return "user/UserRegistration";
		}
		
		return visitor.handler.companySignUp(C_Name, C_Site, C_Specialization, C_AmountEmployes, C_Password, model);
	
	}
		
	//// method response JSON, Ajax on company add page 
	@RequestMapping(value="/add_processing_ajax",method=RequestMethod.POST)
	public @ResponseBody JsonResponse ajaxRequestStream(@ModelAttribute Visitor visitor, HttpServletRequest request) {   
		String name_company = request.getParameter("name");
		boolean result = visitor.handler.checkCompanyName(name_company);		
		JsonResponse res = new JsonResponse(); 		
		if(!result){    			
			res.setStatus("SUCCESS");
			res.setResult(name_company); 			
		} else{
			res.setStatus("ERROR");	
			res.setResult(name_company); 
		}
		return res;
	}
	
		
	// -------------------- ACCOUNT PAGES ---------------------------------------------------- //
	
	@RequestMapping({"/company_main"})
	public String loginSucceessCompany(@ModelAttribute Visitor visitor, Model model){	
//		System.out.println(visitor.getCompanyName());
		model.addAttribute(ACCOUNT_INFO, visitor.handler.getAccountInformation(visitor));
		return "company/Company_main";
	}
	
	// -------------------- LOG OUT --------------------------------------------------------- //
	
	@RequestMapping({"/logout"})
	public String logOut(Model model) {
		Visitor visitor = new Visitor(0);
		changeAttributes(visitor, model);
		return "index";
	}
	

	
	// ------------------------- OTHERS ------------------------------------------------------- //
	
	
	// ------------------------- COMPANY SEARCH ----------------------------------------------- //
	@RequestMapping({"/search_form"})
	public String query() {
		return "company/Company_search_form";
	}
	
	@RequestMapping({"/query_processing"})
	public String queryProcessing(@ModelAttribute Visitor visitor, String jpaStr, Model model) {
		// -- TO TEST -------------------------------------------------------------------------- //
		// -- ADMIN FLOW
		String[] result = visitor.handler.findCompaniesByName(jpaStr);		
		StringBuilder buf = new StringBuilder();
		for (String str : result)
			buf.append(str).append("<br>");
		model.addAttribute("myResult", buf.toString());
		return "company/Company_search_form";
	}
	
	// -------------- TOKEN FOR TESTS ---------------------------------------------------------- //
	
}

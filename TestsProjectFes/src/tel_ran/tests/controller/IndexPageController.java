package tel_ran.tests.controller;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@Scope("session")
@RequestMapping({"/"})
public class IndexPageController {
	HashMap<String,String>personalDB=new HashMap<String,String>();
	HashMap<String,String>adminDB=new HashMap<String,String>();
	HashMap<String,String>companyDB=new HashMap<String,String>();
	private static boolean flCompanyAutorized = false;
	private static boolean flPersonAutorized = false;
	private static boolean flAdminAuthorized = false;	
	/**когда запускаем аппликации, метод дает домашнюю страницу !! */
	@RequestMapping({"/"})
	public String Index(Model model){		
		adminDB.put("amir", "1.com");
		adminDB.put("cristina", "1.com");
		adminDB.put("valeriya", "1.com");
		companyDB.put("alex", "1.com");
		personalDB.put("person", "1.com");     
		String description = "<p>for work propertly use name and password<br>"
				+ "administrators case<br>"
				+ "names: amir,cristina,valeriya<br>"
				+ "Company case<br>"
				+ "names: alex<br>"
				+ "Personal case<br>"
				+ "names: person<br>"
				+ "password for all : 1.com</p>";
		model.addAttribute("result",description);// вывод текста
		return "index";
	}
	/////////////////////////////////////////////////////////////////////////////AOP/////////////
	@RequestMapping({"/SignInAction"})
	public String signIn(Model model,String username,String password){					
		Set<Entry<String, String>> tempASet = adminDB.entrySet();
		for(Entry<String, String> s:tempASet){
			if(s.getKey().equalsIgnoreCase(username) && s.getValue().equalsIgnoreCase(password)){
				flAdminAuthorized = true;						
			}		
		}
		Set<Entry<String, String>> tempUSet = personalDB.entrySet();
		for(Entry<String, String> s:tempUSet){
			if(s.getKey().equalsIgnoreCase(username) && s.getValue().equalsIgnoreCase(password)){
				flPersonAutorized = true;						
			}		
		}
		Set<Entry<String, String>> tempCoSet = companyDB.entrySet();
		for(Entry<String, String> s:tempCoSet){
			if(s.getKey().equalsIgnoreCase(username) && s.getValue().equalsIgnoreCase(password)){
				flCompanyAutorized = true;						
			}		
		}
		/////////////////////		
		if(flAdminAuthorized){				
			flAdminAuthorized=false;
			return  "MaintenanceSignInPage";
		}
		////////////////////
		if(flPersonAutorized){
			flPersonAutorized=false;
			return "PersonalSignIn";
		}
		////////////////////////
		if(flCompanyAutorized){
			flCompanyAutorized=false;
			return "CompanySignIn";
		}
		model.addAttribute("result","Autorization Incorrect");// вывод текста
		return "index";
	}
}

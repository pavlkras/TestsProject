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
		adminDB.put("amir", "");
		adminDB.put("cristina", "");
		adminDB.put("valeriya", "");
		adminDB.put("Paula", "");
		companyDB.put("alex", "");
		personalDB.put("person", ""); 
		
		String description = "<p>for LOGIN use name<br>"
				+ "Maintenace Case<br>"
				+ "names->: amir,cristina,valeriya,paula,roman<br>"
				+ "Company case<br>"
				+ "names->: alex<br>"
				+ "Personal case<br>"
				+ "names->: person<br>"
				+ "password->: NONE PASSWORD</p>";
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

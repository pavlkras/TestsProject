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
	HashMap<String,String>DB=new HashMap<String,String>();
	private static boolean flCompanyAutorized = false;
	private static boolean flPersonAutorized = false;
	private static boolean flAdminAuthorized = false;	
	/**когда запускаем аппликации, метод дает домашнюю страницу !! */
	@RequestMapping({"/"})
	public String Index(){
		DB.put("amir", "1.com");
		DB.put("cristina", "1.com");
		DB.put("alex", "1.com");
		DB.put("user", "1.com");
		return "index";
	}
	/////////////////////////////////////////////////////////////////////////////AOP/////////////
	@RequestMapping({"/SignInAction"})
	public String signIn(Model model,String username,String password){			
		String outPage = "index";
		String links = "";			
		Set<Entry<String, String>> tempSet = DB.entrySet();
		for(Entry<String, String> s:tempSet){
			if(s.getKey().equalsIgnoreCase(username) && s.getValue().equalsIgnoreCase(password)){
				flAdminAuthorized = true;						
			}		
		}					
		/////////////////////		
		if(flAdminAuthorized){			
			links = "<br><a href='http://localhost:8080/TestsProjectFes/maintenanceadd'>"
					+ "1. Create new question</a><br><a href='http://localhost:8080/TestsProjectFes/update'>"
					+ "2. Update Question</a><br><a href='http://localhost:8080/TestsProjectFes/addfromfile'>"
					+ "3. Adding questions from file</a><br>";
			
			model.addAttribute("result",links);// вывод текста
			flAdminAuthorized=false;
			return  "MaintenanceSignInPage";
		}else{			
			links = "Autorization Incorrect";			
		}
		////////////////////
		if(flPersonAutorized){
			flPersonAutorized=false;
			outPage = "PersonalSignIn";
		}else{
			links = "Autorization Incorrect";
		}
		////////////////////////
		if(flCompanyAutorized){
			flCompanyAutorized=false;
			outPage = "CompanySignIn";
		}else{
			links = "Autorization Incorrect";
		}

		model.addAttribute("result",links);// вывод текста
		return outPage;
	}

	@RequestMapping({"/registracionAction"})
	public String registracionActionn(Model model,String username,String password){
		String links = DB.put(username, password);
		model.addAttribute("result",links);// вывод текста
		return "index";	
	}
}

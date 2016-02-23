package tel_ran.tests.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tel_ran.tests.services.AbstractServiceGetter;
import tel_ran.tests.services.AuthorizationService;
import tel_ran.tests.services.utils.SpringApplicationContext;
import tel_ran.tests.token_cipher.TokenProcessor;

import com.fasterxml.jackson.annotation.JsonRawValue;

@Controller
@RequestMapping({"/guest"})
public class GuestAccessRestController {

	@Autowired
	TokenProcessor tokenProcessor;
	
	/**
	 * 
	 * @param answer = String with JSON:
	 * -- email
	 * -- password
	 * @return String with JSON with:
	 * -- JSONKeys.AUTO_ID
	 * -- JSONKeys.AUTO_ROLENUMBER
	 * -- JSONKeys.AUTO_ROLE
	 * OR:
	 * -- JSONKeys.ERROR
	 */
	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody @JsonRawValue
	String logInAction(@RequestBody String answer){		
		AuthorizationService service = (AuthorizationService) SpringApplicationContext.getBean(AbstractServiceGetter.BEAN_AUTHORIZATION_SERVICE);	
		return service.testLogIn(answer);
	}

	
	/**
	 * 
	 * @param answer = String with JSON:
	 * -- JSONKeys.SIGNUP_EMAIL - required
	 * -- JSONKeys.SIGNUP_PASSWORD  - required
	 * -- JSONKeys.SIGNUP_FIRSTNAME - can be empty
	 * -- JSONKeys.SIGNUP_LASTNAME - can be empty
	 * -- JSONKeys.SIGNUP_NICKNAME  - can be empty
	 * @return String with JSON with:
	 * -- 
	 * OR:
	 * -- JSONKeys.ERROR
	 */
	@RequestMapping(value="/signin", method=RequestMethod.POST)
	@ResponseBody @JsonRawValue
	String signInAction(@RequestBody String answer){		
		AuthorizationService service = (AuthorizationService) SpringApplicationContext.getBean(AbstractServiceGetter.BEAN_AUTHORIZATION_SERVICE);	
		
		return service.testSignIn(answer);
	}

	@RequestMapping(value="/company_login", method=RequestMethod.POST)
	@ResponseBody @JsonRawValue
	String companyLogin(@RequestBody String request) {
		AuthorizationService service = (AuthorizationService) SpringApplicationContext.getBean(AbstractServiceGetter.BEAN_AUTHORIZATION_SERVICE);	
		return service.companyLogIn(request);
	}
	
	
	@RequestMapping(value="/company_signup", method=RequestMethod.POST)
	@ResponseBody @JsonRawValue
	String companySignUp(@RequestBody String request){		
		AuthorizationService service = (AuthorizationService) SpringApplicationContext.getBean(AbstractServiceGetter.BEAN_AUTHORIZATION_SERVICE);				
		return service.companySignUp(request);
	}

	@RequestMapping(value="/if_company_exist"+"/{name_company}", method=RequestMethod.GET)
	@ResponseBody @JsonRawValue
	boolean findCompany(@PathVariable String name_company){		
		AuthorizationService service = (AuthorizationService) SpringApplicationContext.getBean(AbstractServiceGetter.BEAN_AUTHORIZATION_SERVICE);				
		return service.findCompany(name_company);
	}
	
	@RequestMapping(value="/getToken", method=RequestMethod.POST)
	@ResponseBody
	String getTokenForTest(@RequestHeader(value="Key") String password) {
		
		AuthorizationService service = (AuthorizationService) SpringApplicationContext.getBean(AbstractServiceGetter.BEAN_AUTHORIZATION_SERVICE);	
		System.out.println(password);
		return service.getTokenByTest(password);
		
	}
	
}

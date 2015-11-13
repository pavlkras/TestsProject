package tel_ran.tests.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonRawValue;

import tel_ran.tests.data_loader.IDataLoader;
import tel_ran.tests.services.AutorizationService;
import tel_ran.tests.services.TestTemplateService;
import tel_ran.tests.services.fields.Role;
import tel_ran.tests.services.utils.SpringApplicationContext;
import tel_ran.tests.token_cipher.TokenProcessor;
import tel_ran.tests.token_cipher.User;

@Controller
@RequestMapping({"/tests"})
public class TemplatesManagementRestController {

	@Autowired
	TokenProcessor tokenProcessor;
	
	/**
	 * Returns lists of categories that are available for test-template generation
	 * Lists are in JSON format.
	 * [{metaCategory : 'nameMC', categories1 : ''
	 * @param token
	 * @return
	 */
	@RequestMapping(value="/categoriesList", method=RequestMethod.GET)
	@ResponseBody @JsonRawValue
	public String getCategories(@RequestHeader(value="Authorization") String token){
		// TO DO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		String result = "{}";
		User user = tokenProcessor.decodeRoleToken(token);
		if(user.isAutorized()) {
			result = getService().getCategories(user);			
		}		
		return result;
	}
	
	
	/**
	 * Returns lists of categories that are available for test-template generation.
	 * Result doesn't depend from the user-access, but only registred companies and admins can get this information 
	 * Lists are in JSON format.
	 * [{cat_parent : 'nameMC', cat_children : [{cat_child : 'nameC'}] 
	 * @return
	 */
	@RequestMapping(value="/autoList", method=RequestMethod.GET)
	@ResponseBody @JsonRawValue
	public String getAutoList(@RequestHeader(value="Authorization") String token) {
		String result = "{}";
		User user = tokenProcessor.decodeRoleToken(token);
		if(user.isAutorized() && (user.getRole().equals(Role.ADMINISTRATOR) || user.getRole().equals(Role.COMPANY))) {
			result = TestTemplateService.getAutoCategories();
		}		
		
		return result;		
	}
	
	private TestTemplateService getService() {
		return (TestTemplateService) SpringApplicationContext.getBean("templateService");
	}
	
}

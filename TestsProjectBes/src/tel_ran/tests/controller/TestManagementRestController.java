package tel_ran.tests.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonRawValue;

import tel_ran.tests.data_loader.IDataLoader;
import tel_ran.tests.services.AutorizationService;
import tel_ran.tests.services.TestService;
import tel_ran.tests.services.fields.Role;
import tel_ran.tests.services.utils.SpringApplicationContext;
import tel_ran.tests.token_cipher.TokenProcessor;
import tel_ran.tests.token_cipher.User;

@Controller
@RequestMapping({"/tests"})
public class TestManagementRestController {

	@Autowired
	TokenProcessor tokenProcessor;
	
	/**
	 * LIST of COMPANY CUSTOM categories
	 * Lists of categories that are available for test-template generation
	 * Lists are in JSON format. JSONArray:
	 * [{cat_parent : 'category1name', cat_children : [{cat_child : 'category2name', cat_mc_array : [{cat_mc : 'mcName'}]}] }]
	 * By KEY:
	 * cat_parent = name of category1 (String)
	 * cat_children = array of categories2 that are in this category1 (JSONArray)
	 * cat_child = name of category2 (String)
	 * cat_mc_array = array of types of questions in this category (AMERICAN TEST or OPEN QUESTION)
	 * cat_mc = name of type (metaCategory) (String)
	 * @param token
	 * @return
	 */
	@RequestMapping(value="/categoriesList", method=RequestMethod.GET)
	@ResponseBody @JsonRawValue
	public String getCategories(@RequestHeader(value="Authorization") String token){		
		String result = "{}";
		User user = tokenProcessor.decodeRoleToken(token);
		if(user.isAutorized()) {
			result = getService().getCategories(user);			
		}		
		return result;
	}
	
	
	/**
	 * List of AUTO - categories that are available for test-template generation.
	 * Result doesn't depend from the user-access, but only registred companies and admins can get this information 
	 * Lists are in JSON format.
	 * [{cat_parent : 'nameMC', cat_children : [{cat_child : 'nameC'}] 	 * 
	 */
	@RequestMapping(value="/autoList", method=RequestMethod.GET)
	@ResponseBody @JsonRawValue
	public String getAutoList(@RequestHeader(value="Authorization") String token) {
		String result = "{}";
		User user = tokenProcessor.decodeRoleToken(token);
		if(user.isAutorized()) {
			result = TestService.getAutoCategories();
		}		
		
		return result;		
	}
	
	/**
	 * LIST of ADMIN categories
	 * Returns list of categories that were created by Admin
	 * Result doesn't depend on the user. But only registred users can get information
	 *  Lists are in JSON format. JSONArray:
	 * [{cat_parent : 'category1name', cat_children : [{cat_child : 'category2name', cat_mc_array : [{cat_mc : 'mcName'}]}] }]
	 * By KEY:
	 * cat_parent = name of category1 (String)
	 * cat_children = array of categories2 that are in this category1 (JSONArray)
	 * cat_child = name of category2 (String)
	 * cat_mc_array = array of types of questions in this category (AMERICAN TEST or OPEN QUESTION)
	 * cat_mc = name of type (metaCategory) (String)
	 * @return
	 */
	@RequestMapping(value="/adminList", method=RequestMethod.GET)
	@ResponseBody @JsonRawValue
	public String getAdminCategoryList(@RequestHeader(value="Authorization") String token) {
		String result = "{}";
		User user = tokenProcessor.decodeRoleToken(token);
		if(user.isAutorized()) {
			result = getService().getAdminCategories();
		}
		return result;
		
	}

	
	/**
	 * POST new TEST 
	 * 
	 * @param testInfo = JSON (required!)
	 * This Json has info:
	 * 
	 * (about person)
	 * per_passport = number of person's passport (REQUIRED) (String)
	 * per_mail = e-mail of Person (REQUIRED) (String)
	 * per_fname = firstname of Person (String)
	 * per_lname = lastname of Person (String)
	 * 
	 * (about template)
	 * template_name = name of template (if the user want to save it) (String)
	 * template_save = true if the template should be saved; false - otherwise
	 * 
	 * (about content of template)
	 * 
	 * (list of given questions by its id)
	 * template_questions = JSONArray
	 * template_quest_id = id of question in array (long)
	 * 
	 * (list of params for random generation)
	 * template_categories = JSONArray
	 * metaCategory = name of MetaCategory (REQUIRED) (String)
	 * category1 = name of category1 (String)
	 * category2 = name of category2 (String)
	 * level = level of difficulty (int) 
	 * quantity = quantity of questions to create in these categories (REQUIRED) (int)
	 *   
	 * @return String with JSON
	 * in case of SUCCESS:
	 * test_id = id of new test (long)
	 * code = 0 (success) (int)
	 * 
	 * in case of ERROR:
	 * error = description of error (String)
	 * code = number of error > 0 (int) 
	 */
	@RequestMapping(value="/createTest", method=RequestMethod.POST)
	@ResponseBody
	public String createTestAndPerson(@RequestHeader(value="Authorization") String token, @RequestBody String testInfo) {
		String result = "{}";
		User user = tokenProcessor.decodeRoleToken(token);
		if(user.isAutorized() && testInfo!=null) {
			TestService service = getService();
			service.setUser(user);
			result = service.createTestAndPerson(testInfo);
		}
		
		return result;
	}
		
	/**
	 * SEND test to person e-mail
	 * Company can send only its tests
	 * Administrator can send any tests	 
	 * 
	 * @param token = token
	 * @param link = JSON with field:
	 * path = first part of path (path to Client + mapping) (String)	 
	 * 
	 * @return 
	 * in case of SUCCESS:
	 * code = 0 (int)
	 * 
	 * in case of ERROR:
	 * code = number of error > 0 (int)
	 * error = description of error (String)	 * 
	 */
	@RequestMapping(value="/sendTestByMail" + "/{testId}", method=RequestMethod.POST)
	@ResponseBody
	public String sendTestToEmail(@RequestHeader(value="Authorization") String token, @PathVariable long testId, @RequestBody String link) {
		String result = "";
		User user = tokenProcessor.decodeRoleToken(token);
		if(user.isAutorized() && link!=null) {
			TestService service = getService();
			service.setUser(user);
			result = service.sendTestByMail(link, testId);
		}
		
		
		return result;		
	}
	
	
	private TestService getService() {
		return (TestService) SpringApplicationContext.getBean("templateService");
	}
	
}

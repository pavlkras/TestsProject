package tel_ran.tests.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonRawValue;

import tel_ran.tests.services.AbstractServiceGetter;
import tel_ran.tests.services.AbstractService;
import tel_ran.tests.services.TestService;
import tel_ran.tests.token_cipher.User;
import tel_ran.tests.utils.errors.AccessException;

@Controller
@RequestMapping({"/tests"})
public class TestManagementRestController {

	
	
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
				
		try {
			AbstractService service = (AbstractService) AbstractServiceGetter.getService(token, "customCategoriesService");
			return service.getAllElements();	
		} catch (AccessException e) {
			e.printStackTrace();
			return e.getString();			
		}	
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
		
		try {
			AbstractService service = (AbstractService) AbstractServiceGetter.getService(token, "autoCategoriesService");
			return service.getAllElements();
		} catch (AccessException e) {
			e.printStackTrace();
			return e.getString();			
		} catch (NullPointerException ne) {
			ne.printStackTrace();
			return "";
		}
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
				
		try {
			AbstractService service = (AbstractService) AbstractServiceGetter.getService(token, "customCategoriesService");
			service.setUser(User.getAdminUser());
			return service.getAllElements();
		} catch (AccessException e) {
			e.printStackTrace();
			return e.getString();			
		}			
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
	 * type = source of questions (Custom, SiteBase, Generation)
	 *   
	 * @return String with JSON
	 * in case of SUCCESS:
	 * test_id = id of new test (long)
	 * code = 0 (success) (int)
	 * 
	 * in case of ERROR:
	 * response = description of error (String)
	 * code = number of error > 0 (int) 
	 */
	@RequestMapping(value="/createTest", method=RequestMethod.POST)
	@ResponseBody
	public String createTestAndPerson(@RequestHeader(value="Authorization") String token, @RequestBody String testInfo) {
				
		try {
			AbstractService service = (AbstractService) AbstractServiceGetter.getService(token, "testService");
			return service.createNewElement(testInfo);
		} catch (AccessException e) {
			e.printStackTrace();
			return e.getString();			
		}		
	}
	
	
	/**
	 * POST new TEMPLATE 
	 * 
	 * @param testInfo = JSON (required!)
	 * This Json has info:
	 *
	 * (about template)
	 * template_name = name of template (if the user want to save it) (String)
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
	 * type = source of questions (Custom, SiteBase, Generation)
	 *   
	 * @return String with JSON
	 * in case of SUCCESS:
	 * test_id = id of new test (long)
	 * code = 0 (success) (int)
	 * 
	 * 
	 * in case of ERROR:
	 * response = description of error (String)
	 * code = number of error > 0 (int) 
	 */
	@RequestMapping(value="/createTemplate", method=RequestMethod.POST)
	@ResponseBody
	public String createTemplate(@RequestHeader(value="Authorization") String token, @RequestBody String testInfo) {
				System.out.println(testInfo);
		try {
			AbstractService service = AbstractServiceGetter.getService(token, AbstractServiceGetter.BEAN_TEMPLATE_SERVICE);
			return service.createNewElement(testInfo);
		} catch (AccessException e) {
			e.printStackTrace();
			return e.getString();			
		}		
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
	 * response = description of error (String)	 * 
	 */
	@RequestMapping(value="/sendTestByMail" + "/{testId}", method=RequestMethod.POST)
	@ResponseBody
	public String sendTestToEmail(@RequestHeader(value="Authorization") String token, @PathVariable long testId, @RequestBody String link) {
				
		try {
			TestService service = (TestService) AbstractServiceGetter.getService(token, "testService");
			return service.sendTestByMail(link, testId);
		} catch (AccessException e) {
			e.printStackTrace();
			return e.getString();			
		}
		
	}
	
	/**
	 * LIST of all questions by ID of company. For Administrator returns list of admin questions
	 * Return JSONArray with json objects:
	 * id = id of question (long)
	 * metaCategory (String)
	 * category1 (String)
	 * category2 (String)
	 * is_image = true if the question has image (boolean)
	 * shortText = short version of question text (String)
	 * level = level of difficulty (int) 
	 * 
	 * If the token is incorrect - returns error message:
	 * code = number of error = 8
	 * response = description of error
	 * @param token
	 */
	@RequestMapping(value="/questionList", method=RequestMethod.GET)
	@ResponseBody
	public String getQuestionsByCompany(@RequestHeader(value="Authorization") String token) {
		
		try {
			AbstractService service = (AbstractService) AbstractServiceGetter.getService(token, 
					AbstractServiceGetter.BEAN_QUESTIONS_SERVICE);
			return service.getAllElements();
		} catch (AccessException e) {
			e.printStackTrace();
			return e.getString();			
		}		
	}
	
	/**
	 * LIST of all templates by ID of company.
	 * Return JSONArray with json objects:
	 * template_id = id of template (long)
	 * template_name = name of template (String)
	 * 
	 * If the token is incorrect - returns error message:
	 * code = number of error = 8
	 * response = description of error
	 * @param token
	 */
	@RequestMapping(value="/listTemplates", method=RequestMethod.GET)
	@ResponseBody
	public String getTemplatesByCompany(@RequestHeader(value="Authorization") String token) {
		
		try {
			AbstractService service = (AbstractService) AbstractServiceGetter.getService(token, AbstractServiceGetter.BEAN_TEMPLATE_SERVICE);
			System.out.println(service.getAllElements());
			return service.getAllElements();
		} catch (AccessException e) {
			e.printStackTrace();
			return e.getString();			
		}
	}
	
	
	/**
	 * POST new TEST by TEMPLATE
	 * 
	 * @param testInfo = JSON (required!)
	 * This Json has info:
	 * 
	 * test_persons - ARRAY with person data:
	 * (about person)
	 * per_passport = number of person's passport (REQUIRED) (String)
	 * per_mail = e-mail of Person (REQUIRED) (String)
	 * per_fname = firstname of Person (String)
	 * per_lname = lastname of Person (String)
	 * 
	 * (about template)
	 * template_id = id of template (if the user want to save it) (String)
	 * 
	 * (about link to test)
	 * path = first part of link to tests (FES + mapping)
	 *   
	 * @return String with JSON
	 * in case of SUCCESS:
	 * ARRAY Json width fields:
	 * per_mail = email of person
	 * test_link = link to new test
	 * test_is_sent = if it was sent or not
	 * 
	 * in case of ERROR:
	 * response = description of error (String)
	 * code = number of error > 0 (int) 
	 */
	@RequestMapping(value="/createTestByTemplate", method=RequestMethod.POST)
	@ResponseBody
	public String createTestByTemplate(@RequestHeader(value="Authorization") String token, @RequestBody String testInfo) {
		System.out.println("In controller");
		try {
			AbstractService service = AbstractServiceGetter.getService(token, "testTemplateService");
			return service.createNewElement(testInfo);
		} catch (AccessException e) {
			e.printStackTrace();
			return e.getString();			
		}		
	}
		
}

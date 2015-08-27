package tel_ran.tests.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tel_ran.tests.services.PersonalActionsService;
import tel_ran.tests.services.interfaces.IPersonalActionsService;
import tel_ran.tests.token_cipher.TokenProcessor;

import com.fasterxml.jackson.annotation.JsonRawValue;

@Controller
@RequestMapping({"/persontest"})
public class PersonTestRESTController {
	@Autowired
	IPersonalActionsService personal;
	@Autowired
	TokenProcessor tokenProcessor;
	
	private static final String LOG = PersonTestRESTController.class.getSimpleName();
	
//	Now it isn't used
//	
//	@RequestMapping(value="/saveprev_getnext", method=RequestMethod.POST)
//	@ResponseBody @JsonRawValue
//	String getNextAndSavePrevious(@RequestHeader(value="Authorization") String token, @RequestBody String answer){
//		long testId = tokenProcessor.decodeAndCheckToken(token);
//		String res = null;
//
//		if(testId != -1){
//			if(!personal.testIsPassed(testId)){
//				if(answer != null && !answer.equalsIgnoreCase("")){
//					personal.setAnswer(testId, answer);
//				}
//				res = personal.getNextQuestion(testId);
//				if(res == null){
//					res = "{\"error\":\"test is already passed\",\"isPassed\":true}";
//				}
//			} else {
//				res = "{\"error\":\"test is already passed\",\"isPassed\":true}";
//			}
//		} else {
//			res = "error - wrong token";
//		}
//		return res;
//	}
	
	/**
	 * New function that returns full list of questions to FES
	 * It also writes the start time of the test in DB
	 * FES controls the queue of questions and their save
	 * @param authorization = token
	 * @param str = JSON with answer and test-question ID (EntityTestQuestions)
	 * @return JSON with all questions
	 */
	@RequestMapping(value="/getquestions", method=RequestMethod.POST)
	@ResponseBody 
	String getAllQuestions(@RequestHeader(value="Authorization", defaultValue="foo") String authorization,
			@RequestBody String str){
		System.out.println("I catch it! " + authorization);
		long testId = tokenProcessor.decodeAndCheckToken(authorization);
		String res = null;
		
		if(testId != -1){
			if(!personal.testIsPassed(testId)){				
								
				res = personal.getAllTestQuestions(testId);
				
				if(res == null){
					res = "{\"error\":\"test is already passed\",\"isPassed\":true}";
				}
			} else {
				res = "{\"error\":\"test is already passed\",\"isPassed\":true}";
			}
		} else {
			res = "error - wrong token";
		}
		return res;
	}
	
	/**
	 * New method gets ONE answer and start saving it in the DB.
	 * If the given answer is last in the test ("finished": true) this flow start checking if the answers are correct 
	 * @param token = token
	 * @param answers = JSON with answer, test-question Id (for EntityTestQuestion), finished (true of false)
	 * @return true if the token is correct and the flow has started
	 */
	@RequestMapping(value="/postAnswers", method=RequestMethod.POST)
	@ResponseBody @JsonRawValue
	boolean getAnswers(@RequestHeader(value="Authorization") String token, @RequestBody String answers) {
		long testId = tokenProcessor.decodeAndCheckToken(token);
		if(testId!=-1) {
			personal.setAnswer(testId, answers);
			return true;
		}		
		return false;
	}

	
	@RequestMapping(value="/save_image", method=RequestMethod.POST)
	@ResponseBody @JsonRawValue
	String saveImage(@RequestHeader(value="Authorization") String token, @RequestBody String image){
		System.out.println(LOG + " - 92: in method saveImage");
		long testId = tokenProcessor.decodeAndCheckToken(token);
		if(testId != -1){
			personal.saveImage(testId, image);
		}		
		return "";
	}
}
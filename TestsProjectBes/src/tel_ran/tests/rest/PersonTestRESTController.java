package tel_ran.tests.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
	
	@RequestMapping(value="/saveprev_getnext", method=RequestMethod.POST)
	@ResponseBody @JsonRawValue
	String getNextAndSavePrevious(@RequestHeader(value="Authorization") String token, @RequestBody String answer){
		long testId = tokenProcessor.decodeAndCheckToken(token);
		String res = null;

		if(testId != -1){
			if(!personal.testIsPassed(testId)){
				if(answer != null && !answer.equalsIgnoreCase("")){
					personal.setAnswer(testId, answer);
				}
				res = personal.getNextQuestion(testId);
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
	
	@RequestMapping(value="/save_image", method=RequestMethod.POST)
	@ResponseBody @JsonRawValue
	String saveImage(@RequestHeader(value="Authorization") String token, @RequestBody String image){
		long testId = tokenProcessor.decodeAndCheckToken(token);
		if(testId != -1){
			personal.saveImage(testId, image);
		}
		return "";
	}
}
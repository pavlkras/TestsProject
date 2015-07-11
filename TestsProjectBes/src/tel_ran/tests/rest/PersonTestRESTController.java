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
	IPersonalActionsService personalActionsService;
	@Autowired
	TokenProcessor tokenProcessor;
	
	@RequestMapping(value="/saveprev_getnext", method=RequestMethod.POST)
	@ResponseBody @JsonRawValue
	String getNextAndSavePrevious(@RequestHeader(value="Authorization") String token, @RequestBody String answer){
		long testId = tokenProcessor.decodeAndCheckToken(token);
		String res = "";
		////FOR TESTING PURPOSE/////
		testId = Long.parseLong(token);
		////////////////////////////
		
		if(testId != -1){
			if(answer != null && !answer.equalsIgnoreCase("")){
				personalActionsService.setAnswer(testId, answer);
			}
			res = personalActionsService.getNextQuestion(testId);
		}
		return res;
	}
}
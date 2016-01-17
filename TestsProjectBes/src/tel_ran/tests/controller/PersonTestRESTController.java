package tel_ran.tests.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import tel_ran.tests.services.AbstractServiceGetter;
import tel_ran.tests.services.TestService;

import tel_ran.tests.token_cipher.TokenProcessor;
import tel_ran.tests.utils.errors.DataException;

import com.fasterxml.jackson.annotation.JsonRawValue;

@Controller
@RequestMapping({"/persontest"})
public class PersonTestRESTController {
	
	@Autowired
	TokenProcessor tokenProcessor;
		
	
	
	
	@RequestMapping(value="/saveprev_getnext", method=RequestMethod.POST)
	@ResponseBody @JsonRawValue
	String getNextAndSavePrevious(@RequestHeader(value="Authorization") String token, @RequestBody String answer){
		
		TestService service = (TestService) AbstractServiceGetter.getService(AbstractServiceGetter.BEAN_TEST_SERVICE);
		try {
			service.setTestId(tokenProcessor.decodeAndCheckToken(token));
			return service.getElement(answer);
		} catch (DataException e) {
			return e.getString();
		}	
	}
	
	
	@RequestMapping(value="/save_image", method=RequestMethod.POST)
	@ResponseBody @JsonRawValue
	String saveImage(@RequestHeader(value="Authorization") String token, @RequestBody String image){	
		TestService service = (TestService) AbstractServiceGetter.getService(AbstractServiceGetter.BEAN_TEST_SERVICE);
		
		try {
			service.setTestId(tokenProcessor.decodeAndCheckToken(token));		
			service.saveImage(image);
			
		} catch (DataException e) {
			return e.getString();
		}		
	
		return "";
	}
}
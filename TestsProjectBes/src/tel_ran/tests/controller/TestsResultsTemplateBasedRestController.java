package tel_ran.tests.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonRawValue;

import tel_ran.tests.services.AbstractService;
import tel_ran.tests.services.AbstractServiceGetter;
import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.utils.errors.AccessException;

@Controller
@RequestMapping({"/view_templatebased_results_rest"})
public class TestsResultsTemplateBasedRestController {
	
	public static final String LOG = TestsResultsTemplateBasedRestController.class.getSimpleName();
	
	@RequestMapping(value=ICommonData.TESTS_RESULTS, method=RequestMethod.GET)
	@ResponseBody @JsonRawValue
	String getTemplate(@RequestHeader(value="Authorization") String token, HttpServletResponse response){		
		response.setDateHeader("Expires",  System.currentTimeMillis() + 300000L);
		
		try {
			AbstractService service = (AbstractService) AbstractServiceGetter.getService(token, AbstractServiceGetter.BEAN_TEMPLATE_SERVICE);
			return service.getAllElements();	
		} catch (AccessException e) {
			e.printStackTrace();
			return e.getString();			
		}	
	}
		
}

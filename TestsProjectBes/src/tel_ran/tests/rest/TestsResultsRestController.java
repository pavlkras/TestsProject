package tel_ran.tests.rest.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tel_ran.tests.services.EntityTestResultCommon;
import tel_ran.tests.services.interfaces.ICompanyActionsService;

@Controller
@RequestMapping({"/view_results"})
public class TestsResultsRestController {
	@Autowired
	ICompanyActionsService company;
	
	@RequestMapping(value=ICommonData.TESTS_RESULTS + "/{companyName}", method=RequestMethod.GET)
	@ResponseBody List<String> getAllTestsResults(@PathVariable String companyName){   
		List<String> res = company.getTestsResultsAll(companyName);
		return company.getTestsResultsAll(companyName);
	}
	
}

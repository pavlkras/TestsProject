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
import tel_ran.tests.constants.ICommonConstants;

@Controller
@RequestMapping({"/view_results"})
public class TestsResultsRestController {
	@Autowired
	ICompanyActionsService company;
	
	@RequestMapping(value=ICommonConstants.TESTS_RESULTS + "/{companyName}", method=RequestMethod.GET)
	@ResponseBody List<String> getAllTestsResults(@PathVariable String companyName){   
		String name="qwerty";
		System.out.println("in function " + companyName);
		List<String> res = company.getTestsResultsAll(name);
		for(String str:res){
			System.out.println(str);
		}
		return company.getTestsResultsAll(name);
		//return res;
	}
/*	
	@RequestMapping(value=ICommonConstants.TESTS_RESULTS_BY_DATES + "/{date1}" + "&" + "{date2}", method=RequestMethod.GET)
	@ResponseBody List<String> getTestsResultsByDates(@PathVariable String companyId, Date date1, Date date2){   
		return companyModel.getTestsResultsForTimeInterval(companyId, date1, date2);
	}
	
	@RequestMapping(value=ICommonConstants.TESTS_RESULTS_BY_PERSON_ID + "/{personId}", method=RequestMethod.GET)
	@ResponseBody List<String> getTestsResultsByPersonId(@PathVariable String companyId, int personId){         //s pomosch'yu @PathVariable berem iz URL znachenie peremennoi isbn
		return companyModel.getTestsResultsForPersonID(companyId, personId);
	}
	*/
	
}

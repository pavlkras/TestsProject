package tel_ran.tests.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.interfaces.ICompanyActionsService;

@Controller
@RequestMapping({"/view_results_rest"})
public class TestsResultsRestController {
	@Autowired
	ICompanyActionsService company;
	
	@RequestMapping(value=ICommonData.TESTS_RESULTS + "/{companyName}", method=RequestMethod.GET)
	@ResponseBody List<String> getAllTestsResults(@PathVariable String companyName){   
		return company.getTestsResultsAll(companyName);
	}
	
	@RequestMapping(value=ICommonData.TESTS_RESULTS_BY_PERSON_ID + "/{companyName}" + "/{personId}", method=RequestMethod.GET)
	@ResponseBody List<String> getTestsResultsByPersonId(@PathVariable String companyName, @PathVariable int personId){         
		return company.getTestsResultsForPersonID(companyName, personId);
	}
	
	@RequestMapping(value=ICommonData.TESTS_RESULTS_BY_DATES + "/{companyName}" + "/{date1}" + "/{date2}", method=RequestMethod.GET)
	//if format of Date dd-MM-yyyy 
	@ResponseBody List<String> getTestsResultsByDates(@PathVariable String companyName, @PathVariable String date1, @PathVariable String date2){   
		List<String> res = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		try {
			Date date_from = dateFormat.parse(date1);
			Date date_until = dateFormat.parse(date2);
			res = company.getTestsResultsForTimeInterval(companyName, date_from, date_until);
		} catch (ParseException e) {}
		return res;
	}
}

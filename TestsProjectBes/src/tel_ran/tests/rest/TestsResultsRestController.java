package tel_ran.tests.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonRawValue;
import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.interfaces.ICompanyActionsService;

@Controller
@RequestMapping({"/view_results_rest"})
public class TestsResultsRestController {
	@Autowired
	ICompanyActionsService company;
	
	@RequestMapping(value=ICommonData.TESTS_RESULTS + "/{token}", method=RequestMethod.GET)
	@ResponseBody @JsonRawValue
	String all(@PathVariable String token){
		String companyName = token; //dummy - change to decoding
		return company.getTestsResultsAll(companyName);
	}
	
	@RequestMapping(value=ICommonData.TESTS_RESULTS_BY_PERSON_ID + "/{personId}"+ "/{token}" , method=RequestMethod.GET)
	@ResponseBody @JsonRawValue 
	String byPersonId(@PathVariable int personId, @PathVariable String token){  
		String companyName = token; //dummy - change to decoding
		return company.getTestsResultsForPersonID(companyName, personId);
	}
	
	@RequestMapping(value=ICommonData.TESTS_RESULTS_BY_DATES + "/{date1}" + "/{date2}" + "/{token}", method=RequestMethod.GET)
	//if format of Date dd-MM-yyyy 
	@ResponseBody @JsonRawValue
	String byDates(@PathVariable String date1, @PathVariable String date2, @PathVariable String token){   
		String companyName = token; //dummy - change to decoding	
		String res = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		try {
			Date date_from = dateFormat.parse(date1);
			Date date_until = dateFormat.parse(date2);
			res = company.getTestsResultsForTimeInterval(companyName, date_from, date_until);
		} catch (ParseException e) {}
		return res;
	}
}

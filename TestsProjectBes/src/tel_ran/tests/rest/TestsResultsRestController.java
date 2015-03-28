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
		long companyId = decodeToken(token);
		String res = "";
		if(companyId != -1){
			res = company.getTestsResultsAll(companyId);
		}
		return res;
	}
	
	@RequestMapping(value=ICommonData.TESTS_RESULTS_BY_PERSON_ID + "/{personId}" + "/{token}", method=RequestMethod.GET)
	@ResponseBody @JsonRawValue 
	String byPersonId(@PathVariable int personId, @PathVariable String token){  
		long companyId = decodeToken(token);
		String res = "";
		if(companyId != -1){
			res = company.getTestsResultsForPersonID(companyId, personId);
		}
		return res;
	}
	
	@RequestMapping(value=ICommonData.TESTS_RESULTS_BY_DATES + "/{date1}" + "/{date2}" + "/{token}", method=RequestMethod.GET)
	@ResponseBody @JsonRawValue
	String byDates(@PathVariable String date1, @PathVariable String date2, @PathVariable String token){ 
		long companyId = decodeToken(token);
		String res = "";
		if(companyId != -1){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			try {
				Date date_from = dateFormat.parse(date1);
				Date date_until = dateFormat.parse(date2);
				res = company.getTestsResultsForTimeInterval(companyId, date_from, date_until);
			} catch (ParseException e) {}
		}
		return res;
	}

	private long decodeToken(String token) {
		//TODO Token processing
		// ERRORSTATE = -1
		//Temporary code
		long companyId = Long.parseLong(token);//for test
		return companyId;
	}
}

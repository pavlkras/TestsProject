package tel_ran.tests.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.interfaces.ICompanyActionsService;
import tel_ran.tests.token_cipher.TokenProcessor;

import com.fasterxml.jackson.annotation.JsonRawValue;

@Controller
@Scope("session")
@RequestMapping({"/view_results_rest"})
public class TestsResultsRestController {
	@Autowired
	ICompanyActionsService company;
	@Autowired
	TokenProcessor tokenProcessor;
	
	private long companyId;
	
	public static final String LOG = TestsResultsRestController.class.getSimpleName();
	
	@RequestMapping(value=ICommonData.TESTS_RESULTS, method=RequestMethod.GET)
	@ResponseBody @JsonRawValue
	String all(@RequestHeader(value="TimeZone") String timeZone, @RequestHeader(value="Authorization") String token){		
		
		companyId = tokenProcessor.decodeRoleToken(token).getId();
		String res = "";
		if(companyId != -1){				
			res = company.getTestsResultsAll(companyId, timeZone);			
		} else {
			res = getJsonErrorMessage();
		}
		return res;
	}
	
	@RequestMapping(value=ICommonData.TESTS_RESULTS_BY_PERSON_ID + "/{personId}", method=RequestMethod.GET)
	@ResponseBody @JsonRawValue 
	String byPersonId(@PathVariable int personId, @RequestHeader(value="TimeZone") String timeZone, @RequestHeader(value="Authorization") String token){  
		long companyId = tokenProcessor.decodeRoleToken(token).getId();
		String res = "";
		if(companyId != -1){
			res = company.getTestsResultsForPersonID(companyId, personId, timeZone);
		} else {
			res = getJsonErrorMessage();
		}
		return res;
	}
	
	@RequestMapping(value=ICommonData.TESTS_RESULTS_BY_DATES + "/{date1}" + "/{date2}", method=RequestMethod.GET)
	@ResponseBody @JsonRawValue
	String byDates(@PathVariable String date1, @PathVariable String date2, @RequestHeader(value="TimeZone") String timeZone,
			@RequestHeader(value="Authorization") String token){ 
		companyId = tokenProcessor.decodeRoleToken(token).getId();
		String res = "";
		if(companyId != -1){
			SimpleDateFormat dateFormat = new SimpleDateFormat(ICommonData.DATE_FORMAT);

			try {
				Calendar calend_from = new GregorianCalendar(TimeZone.getTimeZone(timeZone));
				calend_from.setTime(dateFormat.parse(date1));
				calend_from.set(Calendar.HOUR_OF_DAY, 0);
				calend_from.set(Calendar.MINUTE, 0);
				calend_from.set(Calendar.SECOND, 0);
				calend_from.set(Calendar.MILLISECOND, 0);
				
				Calendar calend_until = new GregorianCalendar(TimeZone.getTimeZone(timeZone));
				calend_until.setTime(dateFormat.parse(date2));
				calend_until.set(Calendar.HOUR_OF_DAY, 24);
				calend_until.set(Calendar.MINUTE, 0);
				calend_until.set(Calendar.SECOND, 0);
				calend_until.set(Calendar.MILLISECOND, 0);
								
				res = company.getTestsResultsForTimeInterval(companyId, calend_from.getTimeInMillis(), calend_until.getTimeInMillis(), timeZone);
			} catch (ParseException e) {}
		} else {
			res = getJsonErrorMessage();
		}
		return res;
	}
	
	
	@RequestMapping(value=ICommonData.TEST_RESULT_DETAILS + "/{testId}", method=RequestMethod.GET)
	@ResponseBody @JsonRawValue
	String testDetails(@PathVariable long testId, @RequestHeader(value="Authorization") String token){
		companyId = tokenProcessor.decodeRoleToken(token).getId();
		String res = "";
		if(companyId != -1){
			res = company.getTestResultDetails(companyId, testId);
		} else {
			res = getJsonErrorMessage();
		}
		return res;
	}
	
	/**
	 * Return JSON with Question details
	 * URL = /question_details/{questId}
	 * @param questId = test-question id (for EntityTestQuestion), that can be find in the results of Query testDetails
	 * @param token
	 * @return String
	 */
	@RequestMapping(value=ICommonData.TEST_QUESTION_DETAILS + "/{questId}", method=RequestMethod.GET)
	@ResponseBody @JsonRawValue
	String questionDetails(@PathVariable long questId, @RequestHeader(value="Authorization") String token){
		long companyId = tokenProcessor.decodeRoleToken(token).getId();
		String res = "";
		if(companyId != -1){
			res = company.getQuestionDetails(companyId, questId);
		} else {
			res = getJsonErrorMessage();
		}
		return res;
	}
	
	/**
	 * Return JSON = List of unchecked questions
	 * URL = /unchecked_questions/{testId}
	 * @return
	 */
	@RequestMapping(value=ICommonData.TEST_RESULTS_UNCHECKED_LIST + "/{testId}", method=RequestMethod.GET)
	@ResponseBody @JsonRawValue
	String uncheckedQuestions(@PathVariable long testId, @RequestHeader(value="Authorization") String token) {
		long companyId = tokenProcessor.decodeRoleToken(token).getId();
		String res = "";
		if(companyId != -1) 
			res = company.getListOfUncheckedQuestions(companyId, testId);
		else
			res = getJsonErrorMessage();
		return res;
	}
	
	private String getJsonErrorMessage(){
		return "{\"Error\":\"Please relogin\"}";
	}
	
	/**
	 * CHECK for unchecked person's question
	 * The method receives String = JSON:
	 * -- "mark" = correct or incorrect or other (from gradeOptions)
	 * -- "id" - id of test-question 
	 * It returns a new value of the question status
	 * @param token = token 
	 * @return String = status of the question in the DB or ""
	 */
	@RequestMapping(value="/check_answer", method=RequestMethod.POST)
	@ResponseBody @JsonRawValue
	String checkAnswer(@RequestHeader(value="Authorization") String token, @RequestBody String mark) {
		long companyId = tokenProcessor.decodeRoleToken(token).getId();
		String res = "";
		if(companyId!=-1) {
			res = company.checkAnswer(companyId, mark);
		} else {
			res = getJsonErrorMessage();
		}
		return res;
	}
	
}

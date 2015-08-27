package tel_ran.tests.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonRawValue;

import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.interfaces.IPersonalActionsService;

@Controller
@Scope("session")
@RequestMapping({"/","/PersonalActions"})
public class PersonalActions {
	
	String token;
	
	private boolean listOfQuestionsHasBeenAsked = false;
	private JSONArray arrayOfquestions;
	private JSONObject[] allQuestions;
	private String[] answers;
	private Long[] idQuestions;
	private RestTemplate restTemplate;
	private int sizeJsn;
	private HeaderRequestInterceptor interceptor;
	
	@Autowired
	IPersonalActionsService personalService;
	////
	/*@RequestMapping({"/PersonalActions"})
	public String startPageToPerson(){ 		return "user/UserSignIn";     } */
	/*  3.2.4. Performing Test – Control Mode  */
	///------- this action is click on the link provided in the mail	
	
	@RequestMapping({"/jobSeeker_test_preparing_click_event"})	
	public String jobSeeker_test_preparing_click_event(HttpServletRequest request, Model model){	
		listOfQuestionsHasBeenAsked = true; 
		String outPage = "user/UserSignIn";
		String passwordForCreatedTest = request.getQueryString();
		String token = personalService.getToken(passwordForCreatedTest);
		if(token != null){
			this.token = token;
			this.arrayOfquestions = new JSONArray();			
			restTemplate = new RestTemplate();
			List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
			interceptor = new HeaderRequestInterceptor("Authorization", token);
			interceptors.add(interceptor);			
			restTemplate.setInterceptors(interceptors);
			
			String result = restTemplate.postForObject("http://localhost:8080/TestsProjectBes/persontest/getquestions", 
					null, String.class);
			
			try {
				arrayOfquestions = new JSONArray(result);
				sizeJsn = arrayOfquestions.length();
				answers = new String[sizeJsn];
				idQuestions = new Long[sizeJsn];
				allQuestions = new JSONObject[sizeJsn];
				for(int i = 0; i < sizeJsn; i++) {
					JSONObject jsn = (JSONObject) arrayOfquestions.get(i);
					allQuestions[i] = jsn;
					idQuestions[i] = jsn.getLong(ICommonData.JSN_INTEST_QUESTION_ID);
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
								
			model.addAttribute("token", token);
			outPage = "person/PersonTestPage";
		}
//		boolean isReady = personalService.GetTestForPerson(passwordForCreatedTest);
//		if(!isReady)// stub for create test page
		return outPage;
	}	
	
	@RequestMapping(value="/getNext", method=RequestMethod.POST)
	@ResponseBody @JsonRawValue
	public String getNext(@RequestBody String dataObj) {		
		
		if(!listOfQuestionsHasBeenAsked)
			return null;
		
		String result = null;
		
		JSONObject jsn = null;
		int index;
		boolean flag = false;
		
		try {
			jsn = new JSONObject(dataObj);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		JSONObject jsnNew = new JSONObject();
			try {
				index = jsn.getInt("index");
				answers[index] = jsn.getString(ICommonData.JSN_INTEST_ANSWER);				
				jsnNew.put(ICommonData.JSN_INTEST_QUESTION_ID, idQuestions[index]);
				jsnNew.put(ICommonData.JSN_INTEST_ANSWER, answers[index]);
				flag = true;
			} catch (JSONException e) {
				if(sizeJsn!=0 && (answers[sizeJsn-1]==null || answers[sizeJsn-1].length() < 1)) {					
					index = -1;
				}
				else
					index = sizeJsn;
			}			
			
			try {
				if(index>=(sizeJsn-1)) {				
					jsnNew.put("finished", true);
					JSONObject answer = new JSONObject();
					answer.put("isPassed", true);
					result = answer.toString();
				} else {
					jsnNew.put("finished", false);
					result = allQuestions[++index].toString();					
					System.out.println(allQuestions[index].toString());
				}	
					
			} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}				
					
			if(flag) {
				boolean success = restTemplate.postForObject("http://localhost:8080/TestsProjectBes/persontest/postAnswers", 
					jsnNew.toString(), Boolean.class);
			}
					
		return result;
	}
	
	
}

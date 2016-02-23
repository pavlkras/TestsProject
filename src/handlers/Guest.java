package handlers;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import tel_ran.tests.controller.HeaderRequestInterceptor;
import tel_ran.tests.services.fields.Role;
import tel_ran.tests.strings.JSONKeys;

public class Guest extends AbstractHandler {
	
	
	
	
	public Guest() {
		this.role = Role.GUEST;		
		this.roleNumber = Role.GUEST.ordinal();
	}
	

	@Override
	public List<String> getPossibleMetaCaterories() {		
		return null;
	}
	
	@Override
	public List<String> getUsersCategories() {		
		return null;
	}

	@Override
	public boolean createNewQuestion(String fileLocationLink, String metaCategory, String category1,
			int lvl, List<String> answers, String correctAnswer,
			int countAnswersOptions, String descriptionText, String codeText,
			String repCategory) {
		return false;
	}
	
	@Override
	public String getAllQuestionsList(String view_mode) {	
		return null;
	}
	
	@Override
	public String getQuestionById(long questId) {
	// TO DO !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		return null;
	}
	
	public List<String> getMetaCategoriesFromDB() {		
		return null;
	}
	
	@Override
	public String[] createNewTest(List<Long> questionsIdList, String category,
			String category1, String level_num, String selectCountQuestions,
			String parseInt, String personName, String personSurname,
			String personEmail, String pathToServer) {
				
		return null;
	}

	public String getTokenFromTest(String password) {
				
		RestTemplate restTemplate = new RestTemplate();
		HeaderRequestInterceptor interceptor = new HeaderRequestInterceptor("Key", password);		
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(interceptor);			
		restTemplate.setInterceptors(interceptors);
		String token = null;
		
		//2 - get info
		try {
			String result = restTemplate.postForObject(this.hostname+"/guest/getToken", null, String.class);
			JSONObject jsn = new JSONObject(result);
			if(jsn.has(JSONKeys.TEST_KEY))
				token = jsn.getString(JSONKeys.TEST_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		return token;
	}
	
}

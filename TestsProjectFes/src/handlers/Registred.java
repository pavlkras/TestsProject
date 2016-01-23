package handlers;

import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.interfaces.ICommonAdminService;
import tel_ran.tests.strings.JSONKeys;

public abstract class Registred extends AbstractHandler {

	protected ICommonAdminService commonService;
	

	public void setCommonService(ICommonAdminService commonService) {
		this.commonService = commonService;
	}

	
	@Override
	public List<String> getPossibleMetaCaterories() {
		List<String> result = commonService.getPossibleMetaCaterories();
		return result;
	}
	
	@Override
	public List<String> getUsersCategories() {
		List<String> result = commonService.getUsersCategories1FromDataBase(token);				
		return result;
	}
	
	@Override
	public boolean createNewQuestion(String fileLocationLink, String metaCategory, String category1,
			int lvl, List<String> answers, String correctAnswer,
			int countAnswersOptions, String descriptionText, String codeText,
			String repCategory) {
			
		System.out.println("Category1 in handler " + category1);
		
		JSONObject jsn = new JSONObject();		
		
		
		if(fileLocationLink!=null)
			try {
				jsn.put(JSONKeys.QUESTION_IMAGE, fileLocationLink);
				jsn.put(JSONKeys.QUESTION_META_CATEGORY, metaCategory);
				jsn.put(JSONKeys.QUESTION_CATEGORY1, category1);
				jsn.put(JSONKeys.QUESTION_DIFFICULTY_LVL, lvl);
				if(correctAnswer!=null)
					jsn.put(JSONKeys.QUESTION_CORRECT_ANSWER_CHAR, correctAnswer);
				if(countAnswersOptions>0)
					jsn.put(JSONKeys.QUESTION_ANSWERS_NUMBER, countAnswersOptions);
				jsn.put(JSONKeys.QUESTION_DESCRIPTION, descriptionText);
				if(codeText!=null)
					jsn.put(JSONKeys.QUESTION_CODE_SIMPLE, codeText);
				if(repCategory!=null)
					jsn.put(JSONKeys.QUESTION_CATEGORY2, repCategory);
				if(answers!=null && answers.size()>0) {
					JSONArray array = new JSONArray();
					for(String str : answers) {
						JSONObject jsn2 = new JSONObject();
						jsn2.put(JSONKeys.QUESTION_ONE_OPTION, str);
						array.put(jsn2);
					}
					jsn.put(JSONKeys.QUESTION_ANSWER_OPTIONS, array);					
					
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
				
		return commonService.createNewQuestion(token, jsn.toString());
	}

	@Override
	public String getAllQuestionsList(String view_mode) {
		String res;
		if(view_mode==null) {
			res = commonService.getAllQuestionsList(token, ICommonData.TYPE_OF_QUESTION_USERS, null, null);
		} else {
		switch(view_mode) {		
		case "all": 
			res = commonService.getAllQuestionsList(token, null, null, null);
			break;
		case "user":
			res = commonService.getAllQuestionsList(token, ICommonData.TYPE_OF_QUESTION_USERS, null, null);
			break;
		case "auto" :
			res = commonService.getAllQuestionsList(token, ICommonData.TYPE_OF_QUESTION_AUTO, null, null);
			break;
		default:
			res = commonService.getAllQuestionsList(token, ICommonData.TYPE_OF_QUESTION_USERS, null, null); 
		
		}
		}
		
		return res;
	}

	@Override
	public List<String> getMetaCategoriesFromDB() {		
		return commonService.getAllMetaCategoriesFromDataBase(token);
	}
	
	@Override
	public String[] createNewTest(List<Long> questionsIdList, String category,
			String category1, String level_num, String selectCountQuestions,
			String personId, String personName, String personSurname,
			String personEmail, String pathToServer) {
							
		String password = getRandomPassword();
		int result = commonService.createTestForPersonFullWithQuestions(token, questionsIdList, category,
				category1, level_num, selectCountQuestions, personId, personName, personSurname, personEmail, password);	
		String link = null;
		if(result==0) {
			link = pathToServer + "?" + password; // -------------------------------------------------------------------------------------TO DO real address NOT text in string !!!
			if(!sendEmail(link,personEmail))
				result = 5;
		}
		String[] answer = new String[2];
		answer[0] = Integer.toString(result);
		answer[1] = link;
		
		return answer;
	}
	
	private String getRandomPassword() {
		String uuid = UUID.randomUUID().toString();		
		return uuid	;
	}	
	
	@Override
	public String getQuestionById(long questId) {
	// TO DO !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		return commonService.getJsonQuestionById(questId);
	}
	
	
	
}

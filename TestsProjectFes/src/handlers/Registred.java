package handlers;

import java.util.List;

import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.interfaces.ICommonAdminService;

public abstract class Registred extends AbstractHandler {

	protected ICommonAdminService commonService;

	public void setCommonService(ICommonAdminService commonService) {
		this.commonService = commonService;
	}

	@Override
	public boolean generateAutoQuestions(String metaCategory, String category2, int levelOfDifficulty,
			String nQuestions) {
		// TODO Auto-generated method stub
		int num = Integer.parseInt(nQuestions);
		return ((ICommonAdminService)commonService).moduleForBuildingQuestions(token, metaCategory, category2, levelOfDifficulty , num);	
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
	public boolean createNewQuestion(String questionText,
			String fileLocationLink, String metaCategory, String category1,
			int lvl, List<String> answers, String correctAnswer, int i,
			int countAnswersOptions, String descriptionText, String codeText,
			String repCategory) {
					
		return commonService.createNewQuestion(token, questionText, fileLocationLink, metaCategory, category1, lvl, answers, correctAnswer, 
					0, countAnswersOptions, descriptionText, codeText, repCategory);
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
	public String getQuestionById(long questId) {
	// TO DO !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		return commonService.getJsonQuestionById(questId);
	}
	
}

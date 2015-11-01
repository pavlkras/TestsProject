package handlers;


import java.util.List;

import tel_ran.tests.services.fields.Role;

public class Guest extends AbstractHandler {
	
	
	
	
	public Guest() {
		this.role = Role.GUEST;		
		this.roleNumber = Role.GUEST.ordinal();
	}
	
	@Override
	public boolean generateAutoQuestions(String metaCategory, String category2, int levelOfDifficulty,
			String nQuestions) {
		return false;		
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
	public boolean createNewQuestion(String questionText,
			String fileLocationLink, String metaCategory, String category1,
			int lvl, List<String> answers, String correctAnswer, int i,
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
			long parseInt, String personName, String personSurname,
			String personEmail) {
				
		return null;
	}

	
}

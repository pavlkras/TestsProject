package tel_ran.tests.services;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;

import json_models.QuestionModel;
import tel_ran.tests.dao.IDataTestsQuestions;
import tel_ran.tests.entitys.EntityQuestionAttributes;

public class QuestionsService extends AbstractService {
	
	@Autowired
	IDataTestsQuestions testQuestsionsData;	
	
	@Override
	public String getAllElements() {
		List<EntityQuestionAttributes> questions = this.testQuestsionsData.getQuestionListByParams(this.user.getRole(),
				this.user.getId());
		
		return getStringFromQuestionList(questions);
	}

	@Override
	public String createNewElement(String dataJson) {
		//TO DO
		
		return null;
		
	}
	
	public EntityQuestionAttributes findQuestionById(Long id) {		
		return testQuestsionsData.findQuestionById(id);
	}
	
	private String getStringFromQuestionList(List<EntityQuestionAttributes> questions) {
		JSONArray jsonArray = new JSONArray();
		for(EntityQuestionAttributes question : questions) {
			try {
				jsonArray.put(new QuestionModel(question).getJSON());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return jsonArray.toString();
	}
	
	public List<EntityQuestionAttributes> getElementsByParams(
			String metaCategory, String category1, String category2, int difficulty, boolean isAdmin) {		
	
		return this.testQuestsionsData.getQuestionListByParams(metaCategory, category1, category2, difficulty, this.user.getRole(),
				(int)this.user.getId(), isAdmin);
	}
	
	

}

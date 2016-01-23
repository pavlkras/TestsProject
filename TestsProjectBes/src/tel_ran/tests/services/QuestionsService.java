package tel_ran.tests.services;


import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import tel_ran.tests.dao.IDataTestsQuestions;
import tel_ran.tests.entitys.Category;
import tel_ran.tests.entitys.CategoryCustom;
import tel_ran.tests.entitys.Company;
import tel_ran.tests.entitys.Question;
import tel_ran.tests.entitys.QuestionCustom;
import tel_ran.tests.entitys.Texts;

@Component("questionsService")
@Scope("prototype")
public class QuestionsService extends AbstractService {
	
	@Autowired
	IDataTestsQuestions testQuestsionsData;	
	
	@Override
	public String getAllElements() {		
		List<Question> questions = this.testQuestsionsData.getAllQuestionsByParams(null, -1, this.user.getRole(), (int)this.user.getId(), false);
				
		return getStringFromQuestionList(questions);
	}

	@Override
	public String createNewElement(String dataJson) {
		
		QuestionCustom question;
		try {
			question = QuestionCustom.getInstanceByJson(dataJson);
		} catch (JSONException e) {			
			e.printStackTrace();
			return null;
		}
		
		if(question!=null) {
			
			try {
				Company company = testQuestsionsData.getCompanyById((int)this.user.getId(), this.user.getRole());
				
				
				CategoryCustom category = new CategoryCustom(dataJson);				
				category.setQuestionType(question);
										
				List<Texts> texts = question.setJsonData(dataJson);
				Category savedCategory = testQuestsionsData.createCategory(category, company);				
				question.setCategory(savedCategory);				
				question.setCompany(company);				
				testQuestsionsData.saveNewCustomQuestion(question, texts);
			} catch (JSONException e) {				
				e.printStackTrace();
				return null;
			}		
			
		} 
		
		return null;		
	}
	
	
	private String getStringFromQuestionList(List<Question> questions) {
		JSONArray jsonArray = new JSONArray();
		for(Question question : questions) {
			try {
				jsonArray.put(question.getShortJSON());
			} catch (JSONException e) {				
				e.printStackTrace();
			}
		}		
		
		return jsonArray.toString();
	}
	
	public List<Question> getElementsByParams(int categoryId, boolean isContainAmericanTests, boolean isContainOpenQuestion, int difficulty, boolean isAdmin) {		
	
		Category category = this.testQuestsionsData.getCategory(categoryId);
		if(category instanceof CategoryCustom) {
			if(isContainAmericanTests && isContainOpenQuestion) {
				return this.testQuestsionsData.getAllQuestionsByParams(category, difficulty, this.user.getRole(),
						(int)this.user.getId(), isAdmin);
			}
			if(isContainAmericanTests) {
				return this.testQuestsionsData.getAmericanTestsByParams(category, difficulty, this.user.getRole(), (int)this.user.getId(), isAdmin);
			}
			if(isContainOpenQuestion) {
				return this.testQuestsionsData.getOpenQuestionsByParams(category, difficulty, this.user.getRole(), (int)this.user.getId(), isAdmin);
			}
			return null;
		}
		
		return this.testQuestsionsData.getAllQuestionsByParams(category, difficulty, this.user.getRole(),
				(int)this.user.getId(), isAdmin);
	}

	@Override
	public List<String> getSimpleList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInformation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getElement(String params) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}

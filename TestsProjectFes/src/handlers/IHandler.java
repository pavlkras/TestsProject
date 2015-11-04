package handlers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import tel_ran.tests.controller.MainController;
import tel_ran.tests.users.Visitor;

public interface IHandler {
	
	int getRoleNumber();
	String logInPage(); 
	String companyLogInPage(); 
	Map<String, Object> logInAction(String userEmail, String password);
	Map<String, Object> companyLoginAction(String companyName, String password);
	String signUpAction(String firstname, String lastname,String email, String password, String nickname, Model model);
	String companySignUp(String C_Name, String C_Site, String C_Specialization, String C_AmountEmployes, 
			String C_Password, Model model);
	void setToken(String token);	
	String getAccountInformation(Visitor visitor);
	boolean checkCompanyName(String name_company);
	String[] findCompaniesByName(String jpaStr);
	boolean generateAutoQuestions(String metaCategory, String category2, int levelOfDifficulty,
			String nQuestions);
	
	List<String> getPossibleMetaCaterories();
	List<String> getUsersCategories();
	boolean createNewQuestion(String questionText, String fileLocationLink,
			String metaCategory, String category1, int lvl,
			List<String> answers, String correctAnswer, int i,
			int countAnswersOptions, String descriptionText, String codeText,
			String repCategory);
	String getAllQuestionsList(String view_mode);
	String getQuestionById(long questId);
	List<String> getMetaCategoriesFromDB();
	String[] createNewTest(List<Long> questionsIdList, String category,
			String category1, String level_num, String selectCountQuestions,
			String personId, String personName, String personSurname,
			String personEmail, String pathToServer);
	
}



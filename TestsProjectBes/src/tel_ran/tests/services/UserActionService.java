package tel_ran.tests.services;

import java.util.List;
import java.util.Random;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tel_ran.tests.entitys.EntityAnswersText;
import tel_ran.tests.entitys.EntityQuestion;
import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.entitys.EntityUser;
import tel_ran.tests.services.interfaces.IMaintenanceService;
import tel_ran.tests.services.interfaces.IUserActionService;
@SuppressWarnings("unchecked")
public class UserActionService extends TestsPersistence implements IUserActionService {
	////------ User Authorization and Registration case -----------// BEGIN //
	@Override
	public boolean IsUserExist(String userMail, String userPassword) {
		boolean actionResult = false;			
		String[] authorizationTest = GetUserByMail(userMail);		
		if(authorizationTest != null && authorizationTest[PASSWORD].equals(userPassword)){			
			actionResult = true;
		}else{
			System.out.println("obj is null -UserActionService-IsUserExist BES");
		}
		return actionResult;
	}	
	////
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean AddingNewUser(String[] args) {		
		boolean result = false;
		if (em.find(EntityUser.class, args[EMAIL]) == null) {
			EntityUser user = new EntityUser();
			user.setFirstName(args[FIRSTNAME]);
			user.setName(args[LASTTNAME]);
			user.setPassword(args[PASSWORD]);
			user.setEmail(args[EMAIL]);
			em.persist(user);
			result = true;
		}
		return result;
	}
	////
	@Override
	public String[] GetUserByMail(String userMail) {		
		String[] result;
		EntityUser resUser = em.find(EntityUser.class, userMail);		
		if (resUser != null) {
			result = new String[4];
			result[FIRSTNAME] = resUser.getFirstName();
			result[LASTTNAME] = resUser.getLastName();
			result[PASSWORD] = resUser.getPassword();
			result[EMAIL] = resUser.getEmail();
		} else{
			result = null;
		}		
		return result;
	}
	////------- User Authorization and Registration case -------------------// END //	

	////------- Test mode Test for User case ----------------// BEGIN //
	@Override
	public List<String> getCategoriesList() {
		String query = "Select DISTINCT q.category FROM EntityQuestionAttributes q ORDER BY q.category";
		Query q = em.createQuery(query);		
		List<String> allCategories = q.getResultList();
		return allCategories;
	}
	////
	@Override
	public List<String> getComplexityLevelList() {
		String query = "Select DISTINCT q.levelOfDifficulty FROM EntityQuestionAttributes q ORDER BY q.levelOfDifficulty";
		Query q = em.createQuery(query);
		List<String> allLevels = q.getResultList();
		return allLevels;
	}
	////
	@Override
	public String getMaxCategoryLevelQuestions(String catName,
			String complexityLevel) {
		String query = "SELECT q FROM EntityQuestionAttributes q WHERE q.category=?1 AND q.levelOfDifficulty=?2";
		Query q = em.createQuery(query);
		q.setParameter(1, catName);
		q.setParameter(2, Integer.parseInt(complexityLevel));
		List<EntityQuestion> qlist = q.getResultList();
		String res = String.valueOf(qlist.size());
		return res;
	}
	////
	@Override
	public String getMaxCategoryQuestions(String catName, String level) {
		String query = "SELECT q FROM EntityQuestionAttributes q WHERE q.category=?1 AND q.levelOfDifficulty=?2";
		Query q = em.createQuery(query);
		q.setParameter(1, catName);
		q.setParameter(2, Integer.parseInt(level));
		List<EntityQuestion> qlist = q.getResultList();
		String res = String.valueOf(qlist.size());
		return res;
	}
	////	
	@Override
	public String getTraineeQuestions(String category, int level, int qAmount) {
		String query = "SELECT q FROM EntityQuestionAttributes q WHERE q.category=?1 AND q.levelOfDifficulty=?2";
		Query q = em.createQuery(query);
		q.setParameter(1, category);
		q.setParameter(2, level);
		////
		List<EntityQuestionAttributes> questionAttributesList = q.getResultList();
		////	
		StringBuffer outTextResult = new StringBuffer();
		// if required number of the questions is less then questionList obtain
		// then randomly choose some questions from list and fill new array till
		// it reach required size		
		if (qAmount != 0 && qAmount < questionAttributesList.size()) {
			Random rand = new Random();		
			for (int j = 0; j < qAmount; j++) {
				// selection random number of the index 				
				int num = rand.nextInt(questionAttributesList.size());
				//
				EntityQuestionAttributes tRes = questionAttributesList.get(num);
				outTextResult.append(tRes.getQuestionId().getQuestionText() + IMaintenanceService.DELIMITER);
				outTextResult.append(tRes.getImageLink() + IMaintenanceService.DELIMITER);
				outTextResult.append(tRes.getNumberOfResponsesInThePicture() + IMaintenanceService.DELIMITER);
				outTextResult.append(tRes.getCorrectAnswer());
				//
				if(tRes.getQuestionAnswersList() != null){					
					List<EntityAnswersText> anRes = tRes.getQuestionAnswersList();
					for(EntityAnswersText rRes :anRes){
						outTextResult.append(IMaintenanceService.DELIMITER + rRes.getAnswerText());
					}
				}
				// removing this question from questionList
				outTextResult.append(",");
				questionAttributesList.remove(num);
			}			
		}
		return outTextResult.toString();	
	}
	////------- Test mode Test for User case ----------------// END //
}

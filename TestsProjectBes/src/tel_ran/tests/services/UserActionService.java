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
	public String getTraineeQuestions(String category, int levelOfDifficulty, int qAmount) {
		StringBuffer outTextResult = new StringBuffer();	
		int level = 1;	
		if(qAmount > 0 && category != null){		
			for(int i=0; i < qAmount ;){// -- cycle works on the number of questions -nQuestion	
				if(levelOfDifficulty != level)			
					level++;// -- condition: the end of the array with the categories, the following passage levels of difficulty: +1.	
				List<EntityQuestionAttributes> questionAttrList = em.createQuery("SELECT c FROM EntityQuestionAttributes c WHERE "
						+ "(c.levelOfDifficulty="+level+") AND (c.category='"+category+"')").getResultList();
				if(questionAttrList.size() > 0){//  -- condition: if the questionAttrList.size is greater than zero.
					Random rnd = new Random();
					int rand =  rnd.nextInt(questionAttrList.size());							
					EntityQuestionAttributes tmpRes = questionAttrList.get(rand);							
					outTextResult.append(tmpRes.getQuestionId().getQuestionText() + DELIMITER);
					////									
					outTextResult.append(tmpRes.getId() + DELIMITER);
					////
					outTextResult.append(tmpRes.getNumberOfResponsesInThePicture() + DELIMITER);
					outTextResult.append(tmpRes.getCorrectAnswer() + DELIMITER);
					outTextResult.append(tmpRes.getLineCod());
					//
					if(tmpRes.getQuestionAnswersList() != null){					
						List<EntityAnswersText> anRes = tmpRes.getQuestionAnswersList();
						for(EntityAnswersText rRes :anRes){
							outTextResult.append(DELIMITER + rRes.getAnswerText());
						}
					}					
					////				
					outTextResult.append(",");					
					i++;// -- cycle works on the number of questions -nQuestion	!!!!!!!! WITCH WRONG LOOP  !!!!!!!!!!
				}else{//  -- condition: if the questionAttrList.size is equal to or less than zero.							
					System.out.println("BES User test else condition i-" + i);//---------------------------sysout	
				}
			}			   
		}		
		return outTextResult.toString();	
	}
	////------- Test mode Test for User case ----------------// END //
}

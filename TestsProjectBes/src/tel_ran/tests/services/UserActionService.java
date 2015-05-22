package tel_ran.tests.services;

import java.util.ArrayList;
import java.util.List;

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
	////	//  --------------------------------------------   TO DO factory method for this case !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	@Override
	public List<String> getTraineeQuestions(String category, int levelOfDifficulty, int qAmount) {
		List<String> outTextResult = new ArrayList<String>();	

		if(qAmount > 0 && category != null){
			for(int i=0; i < qAmount ; i++){// -- cycle works on the number of questions -nQuestion							
				List<EntityQuestionAttributes> questionAttrList = em.createQuery("SELECT c FROM EntityQuestionAttributes c WHERE (c.levelOfDifficulty="+levelOfDifficulty+") AND (c.category='"+category+"')").getResultList();		

				if(questionAttrList != null && questionAttrList.size() > 0){
					EntityQuestionAttributes tmpRes = questionAttrList.get(i);
					outTextResult.add(tmpRes.getId() + DELIMITER                   // - 0 = id
							+ tmpRes.getQuestionId().getQuestionText() + DELIMITER // - 1 = question
							+ tmpRes.getQuestionId().getDescription() + DELIMITER  // - 2 = description
							+ tmpRes.getLineCod() + DELIMITER                      // - 3 = code text
							+ tmpRes.getLanguageName() + DELIMITER				   // - 4 = lang cod			
							+ tmpRes.getCorrectAnswer() + DELIMITER                // - 5 = corr answer
							+ tmpRes.getNumberOfResponsesInThePicture() + DELIMITER// - 6 = num ansers on picture
							+ tmpRes.getMetaCategory() + DELIMITER                 // - 7 = meta category 
							// when question not include meta cetgory ! meta cat = language code
							+ GetAnswers(tmpRes));                   // optionaly 8-12
				}else{					
					System.out.println("BES User test else condition i-" + i);//---------------------------sysout	
				}
			}			   
		}		
		return outTextResult;	
	}
	private String GetAnswers(EntityQuestionAttributes tmpRes) {
		List<EntityAnswersText> anRes;
		String outRes = "";
		if(tmpRes.getQuestionAnswersList() != null){					
			anRes = tmpRes.getQuestionAnswersList();
			for(EntityAnswersText rRes :anRes){
				outRes += rRes.getAnswerText() + DELIMITER;
			}
		}
		return outRes;
	}
	////------- Test mode Test for User case ----------------// END //

	////-------  Test Code From Users and Persons Case  ----------------// BEGIN //		
	////  //  --------------------------------------------   TO DO factory method for this case !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	@Override
	public String TestCodeQuestionUserCase(String codeText) {
		String result = "BES User Code Test Case "
				+ "RESPONSE Transfer is OK\n" + codeText;	     // TO DO !!!!!!!!!  for Intelege case
		return result;
	}
	////-------  Test Code From Users and Persons Case  ----------------// END //	
}

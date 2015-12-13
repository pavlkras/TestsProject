package tel_ran.tests.controller_burlap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tel_ran.tests.entitys.EntityCompany;
import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.entitys.EntityTitleQuestion;
import tel_ran.tests.entitys.EntityUser;
import tel_ran.tests.interfaces.IConstants;
import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.interfaces.IUserActionService;

@SuppressWarnings("unchecked")
public class UserActionService extends CommonServices implements IUserActionService {
	
	private EntityUser entityUser;
	


	////------- Test mode Test for User case ----------------// BEGIN //
	

	@Override
	protected String getLimitsForQuery() {		
		return "companyId is null";
	}
		
	////
	@Override
	public List<String> getComplexityLevelList() {
		String query = "Select DISTINCT q.levelOfDifficulty FROM EntityQuestionAttributes q";
		String q = getLimitsForQuery();
		if(q!=null)
			query = query.concat(" WHERE ").concat(q);
		query = query.concat(" ORDER BY q.levelOfDifficulty");
		Query sqlQ = em.createQuery(query);
		List<String> allLevels = sqlQ.getResultList();
		return allLevels;
	}
	////
	@Override
	public String getMaxCategoryLevelQuestions(String catName,
			String complexityLevel) {
		String query = "SELECT q FROM EntityQuestionAttributes q WHERE q.metaCategory=?1 AND q.levelOfDifficulty=?2";
		String str = getLimitsForQuery();
		if(str!=null)
			query = query.concat(" AND q.").concat(str);

		Query q = em.createQuery(query);
		q.setParameter(1, catName);
		q.setParameter(2, Integer.parseInt(complexityLevel));

		List<EntityTitleQuestion> qlist = q.getResultList();
		String res = String.valueOf(qlist.size());
		return res;
	}
	////
	@Override
	public String getMaxCategoryQuestions(String catName, String level) {
		String query = "SELECT q FROM EntityQuestionAttributes q WHERE q.category=?1 AND q.levelOfDifficulty=?2";
		String temp = getLimitsForQuery();
		if(temp!=null)
			query = query.concat(" AND q.").concat(temp);
		
		Query q = em.createQuery(query);
		q.setParameter(1, catName);
		q.setParameter(2, Integer.parseInt(level));
		List<EntityTitleQuestion> qlist = q.getResultList();
		String res = String.valueOf(qlist.size());
		return res;
	}
	
	////	//  --------------------------------------------   TO DO factory method for this case !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	@Override
	public List<String> getTraineeQuestions(String category, int levelOfDifficulty, int qAmount) {
		List<String> outTextResult = new ArrayList<String>();	
		StringBuilder queryText;

		if(qAmount > 0 && category != null){
			for(int i=0; i < qAmount ; i++){// -- cycle works on the number of questions -nQuestion		
				queryText = new StringBuilder("SELECT c FROM EntityQuestionAttributes c WHERE c.levelOfDifficulty='");
				queryText.append(levelOfDifficulty).append("' AND c.metaCategory='").append(category).append("'");
				String str = getLimitsForQuery();
				if(str!=null)
					queryText.append(" AND c.").append(str);				
				
				List<EntityQuestionAttributes> questionAttrList = em.createQuery(queryText.toString()).getResultList();		

				if(questionAttrList != null && questionAttrList.size() > 0){
					EntityQuestionAttributes tmpRes = questionAttrList.get(i);
					outTextResult.add(getQuestionWithDelimeters(tmpRes)); 					
				}else{					
					System.out.println("BES User test else condition i-" + i);//---------------------------sysout	
				}
			}			   
		}		
		return outTextResult;	
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
	@Override
	protected boolean ifAllowed(EntityQuestionAttributes question) {
		if(question.getEntityCompany() == null)
			return true;
		return false;
	}
	
	// ------------ USER INFORMATION ------------------------------------ //
	
		
	@Override
	public String getUserInformation(String arg0) {
		
		
		
		return null;
	}
	
	
}

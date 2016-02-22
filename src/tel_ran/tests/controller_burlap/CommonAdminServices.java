package tel_ran.tests.controller_burlap;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import json_models.IJsonModels;
import json_models.SimpleArray;

import org.json.JSONException;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tel_ran.tests.entitys.Texts;
import tel_ran.tests.entitys.Company;
import tel_ran.tests.entitys.EntityQuestionAttributes;

import tel_ran.tests.services.AbstractService;
import tel_ran.tests.services.AbstractServiceGetter;
import tel_ran.tests.services.interfaces.ICommonAdminService;
import tel_ran.tests.services.utils.FileManagerService;
import tel_ran.tests.token_cipher.User;
import tel_ran.tests.utils.errors.AccessException;

public abstract class CommonAdminServices extends CommonServices implements
		ICommonAdminService {
	
	protected static int MIN_NUMBER_OF_CATEGORIES = 1;
	protected static boolean FLAG_AUTHORIZATION = false;	
	
	
	
	//RENEWD ******************************
	// USED IN FES !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	@Override
	public String getUserInformation(String token) {
		
		try {
			AbstractService service = AbstractServiceGetter.getService(token, "companyAccount");
			return service.getInformation();
		} catch (AccessException e) {
			e.printStackTrace();
			return e.getString();			
		}	
		
		
		
	}
	
	
	@Override
	public String getAllQuestionsList(String token, Boolean typeOfQuestion, String metaCategory, String category1) {
		
		String result = "";	
		User user = tokenProcessor.decodeRoleToken(token);
		
		if(user.isAutorized()) {
			List<IJsonModels> list = testQuestsionsData.getQuesionsList(typeOfQuestion, metaCategory, category1, (int)user.getId(), user.getRole());
			
			SimpleArray array = new SimpleArray();	
			if(list!=null)
				array.addAll(list);
			try {
				result = array.getString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}				
		return result;
	}
		
	

	
	////--------------  Adding ONE Question into DB Case ----------// BEGIN  //


	// USED IN FES !!!!!!!!!!!!!!!!!!!!!!!!!!!
	@Override
	public boolean createNewQuestion(String token, String jsonQuestion) {	
		System.out.println(jsonQuestion);
		try {
			AbstractService service = (AbstractService) AbstractServiceGetter.getService(token, "questionsService");
			service.createNewElement(jsonQuestion);
			return true;
		} catch (AccessException e) {
			e.printStackTrace();
			return false;			
		}
				
	}

	//**************************************************************
	
		

	////-------------- Search Method by Category or Categories and level of difficulty ----------// BEGIN  //
	@SuppressWarnings("unchecked")	

	@Override
	public List<String> searchAllQuestionInDataBase(String metaCategory,
			String category1, String category2, int levelOfDifficulty) {   // !!!!!!!!!!!!!!!!!!!!!!!!!!!! not work in mazila		
		
		List<String> outResult = new ArrayList<String>();		
		String query = "SELECT c FROM EntityQuestionAttributes c";
		int count = 0;
		if(metaCategory!=null) {
			query = query.concat(" WHERE ").concat("(c.metaCategory='" + metaCategory + "')");
			count++;
		}
		if(category1!=null) {
			if(count==0)
				query.concat(" WHERE ");
			else
				query.concat(" AND ");
			query.concat("(c.category1='" + category1 + "')");				
		}
		if(category2!=null) {
			if(count==0)
				query.concat(" WHERE ");
			else
				query.concat(" AND ");
			query.concat("(c.category2='" + category2 + "')");		
		}
		if(levelOfDifficulty!=0) {
			if(count==0)
				query.concat(" WHERE ");
			else
				query.concat(" AND ");
			query.concat("(c.levelOfDifficulty='" + levelOfDifficulty + "')");		
		}
		Company ec = getCompany();
		if(ec!=null) {
			if(count==0)
				query.concat(" WHERE ");
			else
				query.concat(" AND ");
			query.concat("(c.levelOfDifficulty='" + Long.toString(ec.getId()) + "')");
		}
		
		
		try {
			List<EntityQuestionAttributes> queryResult = (List<EntityQuestionAttributes>) em.createQuery(query);
			
			for(EntityQuestionAttributes tempRes: queryResult) {	
				
				outResult.add(tempRes.getId() + DELIMITER
						+ tempRes.getEntityTitleQuestion().getQuestionText() + DELIMITER 
						+ tempRes.getCategory2());
			}
		} catch (StringIndexOutOfBoundsException e) {
			System.out.println(" BES Search all question in category ' catch case");
			e.printStackTrace();
		}	
					
		return outResult;// return to client 
	}
	////-------------- Search Method by Category or Categories and level of difficulty ----------// END  //

	
/////-------------- Update  ONE Question into DB Case ----------// BEGIN  //


	public boolean updateTextQuestionInDataBase(String questionID, 
			String questionText, String descriptionText, String codeText, String category1, 
			String metaCategory, String category2, int levelOfDifficulty, List<String> answers, String correctAnswer, 
			String fileLocationPath, String numAnswersOnPictures){		
		boolean flagAction = false;	
//		long id = (long)Integer.parseInt(questionID);
//		////  ---- chang question data
//		try{
//			EntityQuestionAttributes elem = em.find(EntityQuestionAttributes.class, id);
//			elem.getEntityTitleQuestion().setQuestionText(questionText);	
//			elem.setDescription(descriptionText);
//			elem.setMetaCategory(metaCategory);
//			elem.setCategory2(category2);
////			elem.setLineCod(codeText);
//			elem.setCategory1(category1);	
//			elem.setLevelOfDifficulty(levelOfDifficulty);
//			elem.setCorrectAnswer(correctAnswer);
//			elem.setFileLocationLink(fileLocationPath);
//			int numberOfResponsesInThePicture = Integer.parseInt(numAnswersOnPictures);
//			elem.setNumberOfResponsesInThePicture(numberOfResponsesInThePicture );
//			// 
//			List<Texts> oldAnswersList = elem.getQuestionAnswersList();
//			List<Texts> newAnswersList;
//			
//			if(answers!=null || codeText!=null) {
//					newAnswersList = new ArrayList();
//			} else {
//				newAnswersList = null;
//			}
//			
//			int newListSize = 0;
//			
//			if(answers!=null)
//				newListSize += answers.size();
//			if(codeText!=null)
//				newListSize += 1;
//			
//			if(oldAnswersList!=null) {
//				int oldListSize = oldAnswersList.size();
//				int max = oldListSize;
//				if(newListSize<max)
//					max = newListSize;
//				for(int i = 0; i < max; i++) {
//					Texts text = oldAnswersList.get(i);
//					text.setText(answers.get(i));		
//					em.persist(text);
//					newAnswersList.add(text);
//				}
//				if(oldListSize > newListSize)
//					for(int i = max; i <oldListSize; i++) {
//						Texts text = oldAnswersList.get(i);
//						em.remove(text);
//						em.flush();
//					}
//				
//				if(oldListSize < newListSize)
//					for (int i = max; i < newListSize; i++) {
//						Texts text = new Texts();
//						text.setText(answers.get(i));
//						text.setEntityQuestionAttributes(elem);
//						em.persist(text);
//						newAnswersList.add(text);
//					}				
//			}
//			
//			elem.setQuestionAnswersList(newAnswersList);		
//						
//			em.persist(elem);
//
//			flagAction = true;
//		}catch(Exception e){
//			e.printStackTrace();
//			System.out.println("BES not good in maintenance service update question");
//		}
		return flagAction;// return to client 
	}	
	
	////-------------- Update  ONE Question into DB Case ----------// END  //
	

	////-------------- Method for delete question in DB ----------// BEGIN  //
	
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW)	
	public String deleteQuetionById(String questionID){// !!!!!!!!!  delete one question with all questions attributes and image in folder !!!!!!!
		String outMessageTextToJSP_Page = "";
		try {
			long id = Integer.parseInt(questionID);		
			EntityQuestionAttributes objEntQue = em.find(EntityQuestionAttributes.class, id);
			String linkForDelete = objEntQue.getFileLocationLink();
			//
			List<Texts> liEntAns = objEntQue.getQuestionAnswersList();
			for(Texts entAns:liEntAns){
				em.remove(entAns);
				em.flush();
			}
			
			em.remove(objEntQue);
			em.flush();
			if(linkForDelete != null && linkForDelete.length() > 4){
				DeleteImageFromFolder(linkForDelete);
			}
			outMessageTextToJSP_Page = "Object Question By ID="+questionID+". Has been Deleted";// return to client 
		} catch (Exception e) {
			outMessageTextToJSP_Page = "Error Deleting Object by ID"+questionID+". This Object Already DELETED";
		}
		return outMessageTextToJSP_Page ;// return to client 
	}
	
	protected void DeleteImageFromFolder(String linkForDelete) {		
		try {
			Files.delete(Paths.get(FileManagerService.BASE_DIR_IMAGES + linkForDelete));
		} catch (IOException e) {
			System.out.println("BES delete image from folder catch IOException, image is not exist !!!");// ------------------------------------------------------- sysout
			//e.printStackTrace();   
		}
	}
	////-------------- Method for delete question into DB ----------// END  //

	
	@Override
	protected String getLimitsForQuery() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static boolean isAuthorized(){
		return FLAG_AUTHORIZATION;
	}

	
	
	///--------------- INNER PROTECTED METHODS -------------------------------- ///
	
	abstract protected boolean ifAllowed(EntityQuestionAttributes eqa);
	
	abstract protected Company getCompany();
	
	abstract protected Company renewCompany();
		
	
	
	protected long getNumberQuestion() {
		String query = "SELECT COUNT(eqa) from EntityQuestionAttributes eqa";
		String limit = getLimitsForQuery();
		if(limit!=null)
			query = query.concat(" WHERE eqa.").concat(limit);
		System.out.println(query);
		Long result = (Long)em.createQuery(query).getSingleResult();
		System.out.println("Number of questions = " + result); // ---------------------------------SYSO - !!!!!!!!!!!!!!!!!!!!!!
		return result;
	}

	

	
	
}

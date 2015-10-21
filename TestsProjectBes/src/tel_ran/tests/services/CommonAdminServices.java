package tel_ran.tests.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tel_ran.tests.entitys.EntityTexts;
import tel_ran.tests.entitys.EntityCompany;
import tel_ran.tests.entitys.EntityTitleQuestion;
import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.processor.TestProcessor;
import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.common.IPublicStrings;
import tel_ran.tests.services.interfaces.ICommonAdminService;
import tel_ran.tests.services.utils.FileManagerService;
import tel_ran.tests.token_cipher.User;

public abstract class CommonAdminServices extends CommonServices implements
		ICommonAdminService {
	
	protected static int MIN_NUMBER_OF_CATEGORIES = 1;
	protected static boolean FLAG_AUTHORIZATION = false;	
	
	@Override
	public String getUserInformation(String token) {
		String result = "";	
		User user = tokenProcessor.decodeRoleToken(token);
		
		if(user.isAutorized()) {
			int numberQuestions = this.testQuestsionsData.getNumberQuestions(user.getId(), user.getRole());
			int numberTests = this.testQuestsionsData.getNumberTests(user.getId(), user.getRole());
			
			JSONObject jsn = new JSONObject();
			try {
				jsn.put(ICommonData.MAP_ACCOUNT_QUESTION_NUMBER, numberQuestions);
				jsn.put(ICommonData.MAP_ACCOUNT_TESTS_NUM, numberTests);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			result = jsn.toString();
		}
			
		return result;
	}

	
	@Override
	public long getCompanyByName(String companyName) {
		long result = -1;			
		EntityCompany tempCompanyEntity = null;

		try {
			tempCompanyEntity = (EntityCompany) em.createQuery("Select c from EntityCompany c where c.C_Name='" + companyName+"'" ).getSingleResult();			
		} catch (Exception e) {
			//e.printStackTrace();
		}		
		if(tempCompanyEntity != null && tempCompanyEntity.getC_Name().equalsIgnoreCase(companyName)){
			result = tempCompanyEntity.getId();
		}
		return result;
	}


	
	////-------------- MANUAL Creation and Adding ONE Question into DB Case ----------// BEGIN  //
	
	@Override	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW) 
	public boolean createNewAmericanTest(String token, String category, int levelOfDifficulty,
			List<String> answerOptions, String correctAnswer,
			String description, String fileLocationLink) {
		//1 - default settings
		String questionText = IPublicStrings.COMPANY_AMERICAN_TEST_QUESTION;
		String metaCategory = IPublicStrings.COMPANY_AMERICAN_TEST;
		int answerOptionsNumber = answerOptions.size();
		
		//2 - question save
		return fullCreateNewQuestion(token, questionText, metaCategory, category, null, levelOfDifficulty,
				description, fileLocationLink, answerOptions, correctAnswer, answerOptionsNumber);
	}

	@Override	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW) 
	public boolean createNewOpenQuestion(String token, String category1, int levelOfDifficulty,
			String description) {
		//1 - default settings
		String questionText = IPublicStrings.COMPANY_QUESTION_QUESTION;
		String metaCategory = IPublicStrings.COMPANY_QUESTION;
		//2 - question save
		return fullCreateNewQuestion(token, questionText, metaCategory, category1, null,
				levelOfDifficulty, description, null, null, null, 0);		
	}
	

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW) 
	public boolean createNewQuestion(String token, String questionText,
			String fileLocationLink, String metaCategory, String category1, 
			int levelOfDifficulty, List<String> answers, String correctAnswerChar,
			int questionNumber, int numberOfResponsesInThePicture, String description, 
			String codeText, String category2){

		if(metaCategory == null) {
			if(answers==null || answers.size() == 0)
				metaCategory = IPublicStrings.COMPANY_QUESTION;
			else
				metaCategory = IPublicStrings.COMPANY_AMERICAN_TEST;
		}
		
		
		if(codeText!=null) {
			if(answers==null) {
				answers = new ArrayList<String>();				
			}
			answers.add(codeText);
		}
		
				
		if(numberOfResponsesInThePicture==0 && answers!=null) {
			numberOfResponsesInThePicture = answers.size();

		}
			
		boolean flagAction = false;	

		
		return fullCreateNewQuestion(token, questionText, metaCategory, category1, category2, 
				levelOfDifficulty, description, fileLocationLink, answers, correctAnswerChar, numberOfResponsesInThePicture);	
	}
	
	////-------------- MANUAL Creation and Adding ONE Question into DB Case ----------// END  //
	
	//// ------------- Build Data 
	////-------------- AUTO Creation and Adding MANY Questions into DB from Generated Question Case ----------// BEGIN  //

	/** Что оно делает:
	 * 1. Генерирует задачи в том числе для программирования (для запуска надо указать константу
	 * TestProcessor.PROGRAMMING или можно использовать 4-е (индекс=3) значение из списка метакатегорий
	 * 2. Пишет архивы по указанному пути (создает папку Programming Task, в нее кладет архивы)
	 * 3. архивы формата zip содержат три файла: Readme.txt с содержанием (сейчас везде одинаково):
	 * Interface: SCalculator.java
	 * JUnit: SCalculator_Test.java		
	 * 4. Функция генерации (это все тот же метод startProcess) возвращает лист массивов стрингов (как и прежде)		 
	 */	

	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW) 
	public boolean moduleForBuildingQuestions(String token, String byCategory, String byCategory1,
			int diffLevel, int nQuestions) {
		
		boolean flagAction = false;	
		
		//1 - questions generation
		List<String> answers;	
		List<String[]> listQuestions = null;		
		TestProcessor proc = new TestProcessor();		
		try {
			String workingDir = FileManagerService.BASE_DIR_IMAGES;			
			Files.createDirectories(Paths.get(workingDir));	
			if(byCategory1==null) {
				listQuestions = proc.processStart(byCategory, nQuestions, workingDir + File.separator, diffLevel);
			} else {
				listQuestions = proc.processStart(byCategory, byCategory1, nQuestions, workingDir + File.separator, diffLevel);
			}
		} catch (Exception e1) {
			System.err.println("catch of TestProcessor.testProcessStart(String category, int, String, int);");
			e1.printStackTrace();
		}		
				
		if(listQuestions != null){
			for(String[] fres :listQuestions){	
				//for(int i=0;i<fres.length;i++){	System.out.println(i+ " - "+fres[i]+"\n");}// -------------------------------------------- test - susout
	
				answers = null;		
				
				////
				/* 0 - question text  ("Реализуйте интерфейс")
				 * 1 - description (текст интерфейса и траляля) - тут был прежде линк на картинку( this field may bee null!!!)
				 * 2 - category (маленькой. тут - Калькулятор)
				 * 3 - level of difficulty = 1-5
				 * 4 - char right answer = ( this field may bee null!!!)
				 * 5 - number responses on pictures = 1-...
				 * 6 - file location linc (for all saving files)  ( this field may bee null!!!)
				 * 7 - languageName	(bee as meta category for question witch code example)  ( this field may bee null!!!)
				 * 8 - code pattern	 (pattern code for Person)	( this field may bee null!!!)
				 */				

				// 0
				String questionText = fres[0].replace("'", "");					
				// 1
				String description = fres[1];
				// 2
				String category2 = fres[2];					
				// 3
				int levelOfDifficulty = Integer.parseInt(fres[3]);				
				//4
				String correctAnswer = fres[4];				
				// 5
				int numberOfResponsesInThePicture;
				if(fres[5]==null)
					numberOfResponsesInThePicture = 0;
				else
					numberOfResponsesInThePicture = Integer.parseInt(fres[5]);	
				
				// 6
				String fileLocationLink = fres[6];
				// 7
				String category1 = fres[7];
				// 8
								
				if(fres[8]!=null && fres[8].length() > 1) {
					System.out.println(LOG + " - 231 M: module for bulding questions " + fres[8]);
					answers = new ArrayList<String>();					
					answers.add(fres[8]);
				} else {
					System.out.println(LOG + " - 235 M: module for bulding questions - NO CODE");
				}
				//
				String metaCategory = byCategory;
								
				
				flagAction = fullCreateNewQuestion(token, questionText, metaCategory, category1, category2,
						levelOfDifficulty, description, fileLocationLink, answers, correctAnswer, numberOfResponsesInThePicture);					
				
			}
		}
		
		return flagAction;

	}
	
	////-------------- Creation and Adding MANY Questions into DB from Generated Question Case ----------// END  //
	
	

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
		EntityCompany ec = getCompany();
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
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW)	
	public boolean updateTextQuestionInDataBase(String questionID, 
			String questionText, String descriptionText, String codeText, String category1, 
			String metaCategory, String category2, int levelOfDifficulty, List<String> answers, String correctAnswer, 
			String fileLocationPath, String numAnswersOnPictures){		
		boolean flagAction = false;	
		long id = (long)Integer.parseInt(questionID);
		////  ---- chang question data
		try{
			EntityQuestionAttributes elem = em.find(EntityQuestionAttributes.class, id);
			elem.getEntityTitleQuestion().setQuestionText(questionText);	
			elem.setDescription(descriptionText);
			elem.setMetaCategory(metaCategory);
			elem.setCategory2(category2);
//			elem.setLineCod(codeText);
			elem.setCategory1(category1);	
			elem.setLevelOfDifficulty(levelOfDifficulty);
			elem.setCorrectAnswer(correctAnswer);
			elem.setFileLocationLink(fileLocationPath);
			int numberOfResponsesInThePicture = Integer.parseInt(numAnswersOnPictures);
			elem.setNumberOfResponsesInThePicture(numberOfResponsesInThePicture );
			// 
			List<EntityTexts> oldAnswersList = elem.getQuestionAnswersList();
			List<EntityTexts> newAnswersList;
			
			if(answers!=null || codeText!=null) {
					newAnswersList = new ArrayList();
			} else {
				newAnswersList = null;
			}
			
			int newListSize = 0;
			
			if(answers!=null)
				newListSize += answers.size();
			if(codeText!=null)
				newListSize += 1;
			
			if(oldAnswersList!=null) {
				int oldListSize = oldAnswersList.size();
				int max = oldListSize;
				if(newListSize<max)
					max = newListSize;
				for(int i = 0; i < max; i++) {
					EntityTexts text = oldAnswersList.get(i);
					text.setAnswerText(answers.get(i));		
					em.persist(text);
					newAnswersList.add(text);
				}
				if(oldListSize > newListSize)
					for(int i = max; i <oldListSize; i++) {
						EntityTexts text = oldAnswersList.get(i);
						em.remove(text);
						em.flush();
					}
				
				if(oldListSize < newListSize)
					for (int i = max; i < newListSize; i++) {
						EntityTexts text = new EntityTexts();
						text.setAnswerText(answers.get(i));
						text.setEntityQuestionAttributes(elem);
						em.persist(text);
						newAnswersList.add(text);
					}				
			}
			
			elem.setQuestionAnswersList(newAnswersList);		
						
			em.persist(elem);

			flagAction = true;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("BES not good in maintenance service update question");
		}
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
			List<EntityTexts> liEntAns = objEntQue.getQuestionAnswersList();
			for(EntityTexts entAns:liEntAns){
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
	
	abstract protected EntityCompany getCompany();
	
	abstract protected EntityCompany renewCompany();
	
//	@Transactional(readOnly=false, propagation=Propagation.REQUIRED) 
//	protected long createQuestion(String questionText) {
//		EntityTitleQuestion objectQuestion;		
//		
//		if(questionText == null) {
//			questionText = ICommonData.NO_QUESTION;
//		}
//		//// query if question exist as text in Data Base
//		Query tempRes = em.createQuery("SELECT q FROM EntityQuestion q WHERE q.questionText='"+questionText+"'");
//		try{			
//			objectQuestion = (EntityTitleQuestion) tempRes.getSingleResult();			
//			
//		}catch(javax.persistence.NoResultException e){				
//			objectQuestion = new EntityTitleQuestion();	
//			objectQuestion.setQuestionText(questionText);			
//			em.persist(objectQuestion);			
//		}								
//		return objectQuestion.getId();		
//		
//	}
	
//	@Transactional(readOnly=false,propagation=Propagation.REQUIRED) 
//	protected EntityQuestionAttributes createAttributes(String fileLocationLink,
//			String metaCategory, String category1, String category2, int levelOfDifficulty,
//			List<String> answers, String correctAnswerChar, int answerOptionsNumber, String description,
//			EntityTitleQuestion objectQuestion, EntityCompany company){
//		
//		EntityQuestionAttributes questionAttributesList = new EntityQuestionAttributes();// new question attributes creation		
//				
//		questionAttributesList.setDescription(description);		
//		if(company!=null) {
//	
//			questionAttributesList.setCompanyId(company);
//		}
//		questionAttributesList.setEntityTitleQuestion(objectQuestion);	
//		questionAttributesList.setFileLocationLink(fileLocationLink);// file location path (string) 		
//		questionAttributesList.setMetaCategory(metaCategory);
//		questionAttributesList.setCategory1(category1);		
//		questionAttributesList.setCategory2(category2);
//		questionAttributesList.setLevelOfDifficulty(levelOfDifficulty);	
//		questionAttributesList.setCorrectAnswer(correctAnswerChar);		
//		questionAttributesList.setNumberOfResponsesInThePicture(answerOptionsNumber);
//
//		em.persist(questionAttributesList);	
//		
//		if(answers != null)	{			
//			List<EntityTexts> answersList = new ArrayList<EntityTexts>();
//			for (String answerText : answers) {	
//				
//				if(answerText!=null && answerText.length()>0) {
//					EntityTexts ans = writeNewAnswer(answerText, questionAttributesList); 
//					answersList.add(ans);
//				}
//			}			
//			questionAttributesList.setQuestionAnswersList(answersList);// mapping to answers
//			em.merge(questionAttributesList);	
//		}
//				
//		return questionAttributesList;
//	}
	
//	@Transactional(readOnly=false,propagation=Propagation.REQUIRED) 
//	protected EntityTexts writeNewAnswer(String answer, EntityQuestionAttributes qAttrId){		
//		EntityTexts temp = new EntityTexts();
//		System.out.println(LOG + " 560 " + answer);
//		temp.setAnswerText(answer);		
//		temp.setEntityQuestionAttributes(qAttrId);	
//		em.persist(temp);	
//		em.clear();
//		return temp;
//	}
	
//	@Transactional(readOnly=false,propagation=Propagation.REQUIRED) 
//	protected EntityTexts writeNewAnswer(String answer, long keyAttr){		
//		return writeNewAnswer(answer, em.find(EntityQuestionAttributes.class, keyAttr));		
//	}	
	
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED) 
	protected boolean fullCreateNewQuestion(String token, String questionText,
			String metaCategory, String category1, String category2,
			int levelOfDifficulty, String description, String fileLocationLink, List<String> answers, String correctAnswerChar,
			int answerOptionsNumber) {				
		
		boolean result = false;
		
		//1 - check token
		User user = tokenProcessor.decodeRoleToken(token);	
		if(!user.isAutorized()) return false;
		
		//2 - check if the questionText is specified		
		if(questionText == null) {
			if (metaCategory.equals(IPublicStrings.COMPANY_AMERICAN_TEST))
				questionText = IPublicStrings.COMPANY_AMERICAN_TEST_QUESTION;
			else if (metaCategory.equals(IPublicStrings.COMPANY_QUESTION))
				questionText = IPublicStrings.COMPANY_QUESTION_QUESTION;			
		}
			
		//3 - save files			
		if (metaCategory.equals(IPublicStrings.COMPANY_AMERICAN_TEST) || metaCategory.equals(IPublicStrings.COMPANY_QUESTION)) {
			if(fileLocationLink!=null && fileLocationLink.length()>5) {								
				try {
					fileLocationLink = FileManagerService.saveImageForUserTests(metaCategory, user.getUniqueName(), fileLocationLink);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					fileLocationLink = null;
					System.out.println(e);
				}				
			} else {
				fileLocationLink = null;
			}				
		}
		
		//4 - create question
		result = testQuestsionsData.saveNewQuestion(fileLocationLink, metaCategory, category1, category2, levelOfDifficulty,
				answers, correctAnswerChar, answerOptionsNumber, description, questionText, user.getId(), user.getRole());
				

		return result;
	}
		
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public String[] getAnySingleQuery(String strQuery) {
//		if(em.find(EntityAdministrators.class,"qqq@qqq.qq") == null){
//			EntityAdministrators emad = new EntityAdministrators();
//			emad.setPassportNumber("12345");
//			emad.setUserMail("qqq@qqq.qq");
//			emad.setUserPassword("12345");
//			emad.setUserName("test");
//			emad.setUserAddress("californy");
//			em.persist(emad);
//		}
//		String[] outResult;
//		List<EntityCompany> result = em.createQuery(
//				"SELECT c FROM EntityCompany c WHERE c.C_Name LIKE :custName").setParameter("custName","%"+strQuery+"%").getResultList();// return to client result of operation
//		int len_gth = result.size();
//		outResult = new String[len_gth];
//		int flCount = 0;
//		for(EntityCompany q: result){		
//			if(flCount != len_gth){
//				outResult[flCount++] = q.toString();
//			}
//		}
//		return outResult;// return to client
		return null;
	}
	
	
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
	

	@Override
	public String getAllQuestionsList(Boolean typeOfQuestion, String metaCategory, String category1) {
		StringBuilder query = new StringBuilder("SELECT c FROM EntityQuestionAttributes c WHERE ");
		int num = 0;
		int numOfAnd = 0;
		String limit = getLimitsForQuery();
		if(limit!=null) {
			query.append("c.").append(limit);
			num++;			
		}
		
		if(metaCategory==null) {
			if(typeOfQuestion!=null) {			
				numOfAnd = checkIfAnd(num, numOfAnd, query);
				num++;
				
				if(typeOfQuestion) {		
				
				query.append("(c.metaCategory='").append(IPublicStrings.COMPANY_AMERICAN_TEST)
						.append("' OR c.metaCategory='").append(IPublicStrings.COMPANY_QUESTION)
						.append("')");
				} else {
					List<String> cat = TestProcessor.getMetaCategory();
					int count = cat.size();
					query.append("(");
					for(String c : cat) {
						query.append("c.metaCategory='").append(c).append("' OR ");					
					}
					int len = query.length();						
					query.delete(len-4, len);
					query.append(")");				
				}
			}
		} else {
			numOfAnd = checkIfAnd(num, numOfAnd, query);			
			query.append("c.metaCategory=").append(metaCategory);
			num++;
		}
		
		if(category1!=null) {
			numOfAnd = checkIfAnd(num, numOfAnd, query);	
			query.append("c.category1=").append(category1);
			num++;
		}
		
		query.append(" ORDER BY c.id DESC");
		System.out.println(query.toString()); // ---------------------------------------- SYSO ---- !!!!!!!!!!!!!!!!!!!!!
		
		List<EntityQuestionAttributes> listOfEqa = em.createQuery(query.toString()).getResultList();
		
		JSONObject resultJsn = new JSONObject();
		JSONArray arrayResult = new JSONArray();		
		
		for(EntityQuestionAttributes eqa : listOfEqa) {
			try {
				arrayResult.put(getShortQuestionJson(eqa));
				resultJsn.put(ICommonData.JSN_LIST_OF_RESULT, arrayResult);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				
		
		return resultJsn.toString();
	}
	
	private int checkIfAnd(int num, int andNum, StringBuilder str) {
		if(andNum < num) {
			andNum++;
			str.append(" AND ");
		}
		return andNum;
	}
	
	private JSONObject getShortQuestionJson(EntityQuestionAttributes eqa) throws JSONException {
		JSONObject jsn = null;
		if(eqa!=null) {
			jsn = getPartOfJSON(eqa);	
			String shrt = eqa.getDescription();
			if(shrt==null) {
				
				jsn.put(ICommonData.JSN_QUESTION_SHORT_DESCRIPTION, "See the image");
			} else {
				shrt = shrt.replaceAll("\\n", "  ");
				int len = shrt.length();
				if(len>ICommonData.SHORT_DESCR_LEN) 
					shrt = shrt.substring(0, ICommonData.SHORT_DESCR_LEN);		
				jsn.put(ICommonData.JSN_QUESTION_SHORT_DESCRIPTION, shrt);
			}
			System.out.println("JSON " + jsn.toString()); // ---------------------------------------- SYSO --- !!!!!!!!!!!!!!!!!!!!!!!!!!
		} 
		return jsn;
	}
	
}

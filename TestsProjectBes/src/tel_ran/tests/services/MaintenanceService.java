package tel_ran.tests.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tel_ran.tests.entitys.EntityAdministrators;
import tel_ran.tests.entitys.EntityAnswersText;
import tel_ran.tests.entitys.EntityCompany;
import tel_ran.tests.entitys.EntityQuestion;
import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.processor.TestProcessor;
import tel_ran.tests.services.common.ICommonData;
import tel_ran.tests.services.common.IPublicStrings;
import tel_ran.tests.services.interfaces.IMaintenanceService;

/**
 * Main Class for Administration services
 * 
 */
public class MaintenanceService extends CommonServices implements IMaintenanceService {	
	//
//	private static int NUMBERofRESPONSESinThePICTURE = 4;// number of responses in the picture, for text question`s default = 4
	private static int MIN_NUMBER_OF_CATEGORIES = 1;
	private static boolean FLAG_AUTHORIZATION = false;	
	
	protected EntityCompany renewCompany() {
		return null;
	}
	
	//
	////-------------- Authorization Case ----------// Begin  //		
	// ---------- stub method authorization ------------------//
	@Override	
	public boolean setAutorization(String username, String password) { 
		////
		EntityAdministrators tmpUser = null;
		try {
			tmpUser = em.find(EntityAdministrators.class, username); 
		} catch (Exception e) {
			System.out.println("administrator catch em.find() action");//------------------------------------------ susout
			//e.printStackTrace();
		}
		//
		if(tmpUser != null && tmpUser.getUserPassword().equalsIgnoreCase(password)){			
			FLAG_AUTHORIZATION = true;	
			System.out.println("administrator is ok");//------------------------------------------ susout
		}else{
			FLAG_AUTHORIZATION = true;	   
			System.out.println("administrator is wrong TO DO ADDING to EntityAdministrators !!!! BES");//--------------------------------------- susout
		}
		//
		return MaintenanceService.FLAG_AUTHORIZATION;	
	}
	
	public static boolean isAuthorized(){
		return FLAG_AUTHORIZATION;
	}
	////-------------- Authorization users like Administrator DB ----------// END  //
	
	


	
	///--------------- INNER PROTECTED METHODS -------------------------------- ///
		
	
	protected boolean ifAllowed(EntityQuestionAttributes eqa) {
		return true;
	}
		
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED) 
	protected long createQuestion(String questionText) {
		EntityQuestion objectQuestion;		
		
		if(questionText == null) {
			questionText = ICommonData.NO_QUESTION;
		}
		//// query if question exist as text in Data Base
		Query tempRes = em.createQuery("SELECT q FROM EntityQuestion q WHERE q.questionText='"+questionText+"'");
		try{			
			objectQuestion = (EntityQuestion) tempRes.getSingleResult();			
			
		}catch(javax.persistence.NoResultException e){				
			objectQuestion = new EntityQuestion();	
			objectQuestion.setQuestionText(questionText);	
			
			em.persist(objectQuestion);
			
		}				
				
		return objectQuestion.getId();		
		
	}
	
		
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED) 
	protected EntityQuestionAttributes createAttributes(String fileLocationLink,
			String metaCategory, String category1, String category2, int levelOfDifficulty,
			List<String> answers, String correctAnswerChar, int answerOptionsNumber, String description,
			EntityQuestion objectQuestion, EntityCompany company){
		
		EntityQuestionAttributes questionAttributesList = new EntityQuestionAttributes();// new question attributes creation		
				
		questionAttributesList.setDescription(description);		
		if(company!=null) {
			System.out.println(company.getId());
			questionAttributesList.setCompanyId(company);
		}
		questionAttributesList.setQuestionId(objectQuestion);	
		questionAttributesList.setFileLocationLink(fileLocationLink);// file location path (string) 		
		questionAttributesList.setMetaCategory(metaCategory);
		questionAttributesList.setCategory1(category1);		
		questionAttributesList.setCategory2(category2);
		questionAttributesList.setLevelOfDifficulty(levelOfDifficulty);	
		questionAttributesList.setCorrectAnswer(correctAnswerChar);		
		questionAttributesList.setNumberOfResponsesInThePicture(answerOptionsNumber);

		em.persist(questionAttributesList);	
		
		if(answers != null)	{			
			List<EntityAnswersText> answersList = new ArrayList<EntityAnswersText>();
			for (String answerText : answers) {					
				EntityAnswersText ans = writeNewAnswer(answerText, questionAttributesList); 
				answersList.add(ans);  				
			}			
			questionAttributesList.setQuestionAnswersList(answersList);// mapping to answers
			em.merge(questionAttributesList);	
		}
				
		return questionAttributesList;
	}
	
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED) 
	protected EntityAnswersText writeNewAnswer(String answer, EntityQuestionAttributes qAttrId){		
		EntityAnswersText temp = new EntityAnswersText();
		temp.setAnswerText(answer);		
		temp.setQuestionAttributeId(qAttrId);	
		em.persist(temp);	
		em.clear();
		return temp;
	}
	
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED) 
	protected EntityAnswersText writeNewAnswer(String answer, long keyAttr){		
		return writeNewAnswer(answer, em.find(EntityQuestionAttributes.class, keyAttr));		
	}	
	
	protected EntityCompany getCompany() {
		return null;
	}
		
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW) 
	protected boolean fullCreateNewQuestion(String questionText,
			String metaCategory, String category1, String category2,
			int levelOfDifficulty, String description, String fileLocationLink, List<String> answers, String correctAnswerChar,
			int answerOptionsNumber) {
		////		
		
		if(questionText == null) {
			if (metaCategory.equals(IPublicStrings.COMPANY_AMERICAN_TEST))
				questionText = IPublicStrings.COMPANY_AMERICAN_TEST_QUESTION;
			else if (metaCategory.equals(IPublicStrings.COMPANY_QUESTION))
				questionText = IPublicStrings.COMPANY_QUESTION_QUESTION;			
		}
	
		long questionId = createQuestion(questionText);
		EntityQuestion objectQuestion = em.find(EntityQuestion.class, questionId);
				
		EntityCompany objectCompany = renewCompany();
		System.out.println("My class = " + this.getClass());
		

		EntityQuestionAttributes questionAttributes = createAttributes(fileLocationLink, metaCategory, category1, 
				category2, levelOfDifficulty, answers, correctAnswerChar, answerOptionsNumber, description, objectQuestion, 
				objectCompany);		

		objectQuestion = em.find(EntityQuestion.class, questionId);
		objectQuestion.addQuestionAttributes(questionAttributes);
		em.merge(questionAttributes);
		em.merge(objectQuestion);	
		
		if(objectCompany!=null) {
			objectCompany.addQuestionAttributes(questionAttributes);
			em.merge(objectCompany);
		}


		return true;
	}
		
		
	/// ----------- PUBLIC METHODS ---------------------------------------------------- ///
	
	////-------------- Creation and Adding ONE Question into DB Case ----------// BEGIN  //
		
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW) 
	public boolean CreateNewQuestion(String questionText,
			String fileLocationLink, String metaCategory, String category1, 
			int levelOfDifficulty, List<String> answers, String correctAnswerChar,
			int questionNumber, int numberOfResponsesInThePicture, String description, 
			String codeText, String category2){
		////

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

		
		fullCreateNewQuestion(questionText, metaCategory, category1, category2, 
				levelOfDifficulty, description, fileLocationLink, answers, correctAnswerChar, numberOfResponsesInThePicture);	
		flagAction = true;

		em.clear();
		return flagAction;
	}
	
	@Override	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW) 
	public boolean CreateNewOpenQuestion(String category1, int levelOfDifficulty,
			String description) {
		
		String questionText = IPublicStrings.COMPANY_QUESTION_QUESTION;
		String metaCategory = IPublicStrings.COMPANY_QUESTION;
		
		fullCreateNewQuestion(questionText, metaCategory, category1, null,
				levelOfDifficulty, description, null, null, null, 0);		
		
		return true;
	}
	
	

	@Override	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW) 
	public boolean CreateNewAmericanTest(String category, int levelOfDifficulty,
			List<String> answerOptions, String correctAnswer,
			String description, String fileLocationLink) {
		
		String questionText = IPublicStrings.COMPANY_AMERICAN_TEST_QUESTION;
		String metaCategory = IPublicStrings.COMPANY_AMERICAN_TEST;
		int answerOptionsNumber = answerOptions.size();
		
		fullCreateNewQuestion(questionText, metaCategory, category, null, levelOfDifficulty,
				description, fileLocationLink, answerOptions, correctAnswer, answerOptionsNumber);
		
		
		return true;
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
	public boolean ModuleForBuildingQuestions(String byCategory, int diffLevel, int nQuestions) {

		return ModuleForBuildingQuestions(byCategory, null, diffLevel, nQuestions);
	}
	
	
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW) 
	public boolean ModuleForBuildingQuestions(String byCategory, String byCategory1,
			int diffLevel, int nQuestions) {
		boolean flagAction = false;	
		List<String> answers;	
		List<String[]> listQuestions = null;		
		TestProcessor proc = new TestProcessor();
		////
		try {
			String workingDir = System.getProperty("user.dir") + File.separator + NAME_FOLDER_FOR_SAVENG_QUESTIONS_FILES;			
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
		////		
		
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
				
				if(fres[8]!=null && fres[8].length() > 3) {
					
					answers = new ArrayList<String>();
					answers.add(fres[8]);
				}
				//
				String metaCategory = byCategory;// TO DO !!!!!!!!!!!!!!!!!! by what category that bee save ???
								
				
				if(fullCreateNewQuestion(questionText, metaCategory, category1, category2,
						levelOfDifficulty, description, fileLocationLink, answers, correctAnswer, numberOfResponsesInThePicture))
					flagAction = true;
				
			}
		}
		
		return flagAction;

	}
	
	
	////-------------- Creation and Adding MANY Questions into DB from Generated Question Case ----------// END  //
	
	

	////-------------- Reading from file and Adding Questions into DB Case ----------// BEGIN  //
	//  //  --------------------------------------------   TO DO factory method for this case !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//
//	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW)	
//	public boolean FillDataBaseFromTextResource(List<String> inputTextFromFile){
//		// !!!!!!!!!!! worked only in Mozila and Hrome !!!!!!!!!!!
//		/*
//		 *  sample for text in file witch questions (THIS ONLY ONE LINE!!!)
//		 * 
//		 * questionText----description----fileLocationLink----
//		 * metaCategory----category----levelOfDifficulty----
//		 * answer1----answer2----answer3----answer4----
//		 * correctAnswerChar----questionIndexNumber----languageName
//		 * 
//		 */
//		boolean flagAction = false;
//		String fileLocationLink = null;
//		String questionText = null;
//		String category = null;
//		int levelOfDifficulty = 1;
//		List<String> answers = null;
//		String correctAnswer = null;
//		int questionNumber = 0;
//		String languageName = null;
//		String metaCategory = null;
//		String description = null; 
//		String codeText = null;
//		//
//		try{			
//			for(String line: inputTextFromFile){ 			
//				String[] question_Parts = line.split(DELIMITER); //delimiter for text, from interface IMaintenanceService
//				//
//				if(question_Parts.length > 6){		
//					questionText = question_Parts[0];
//					fileLocationLink = question_Parts[1];			
//					category = question_Parts[2];
//					levelOfDifficulty = Integer.parseInt(question_Parts[3]);
//					//
//					answers = new ArrayList<String>();									
//					answers.add(question_Parts[4]);	
//					answers.add(question_Parts[5]);
//					answers.add(question_Parts[6]);
//					answers.add(question_Parts[7]);				
//					//
//					correctAnswer = question_Parts[8];
//					questionNumber = 0;	
//					languageName = null;
//					metaCategory = null;
//					description = null; 
//					if(question_Parts[8] != null && question_Parts[8].length() > 3){
//						codeText = question_Parts[8];
//					}
//				}else{
//					//When question exist method added only new attributes for this question
//					//else if question not exist method added a new question full
//					questionNumber = Integer.parseInt(question_Parts[5]);	
//					questionText = question_Parts[0];
//					fileLocationLink = question_Parts[1];			
//					category = question_Parts[2];
//					levelOfDifficulty = Integer.parseInt(question_Parts[3]);
//					correctAnswer = question_Parts[4];
//					languageName = null;
//					metaCategory = null;// TO DO !!!!! create conections for field witch txt file
//					description = null; 
//					if(question_Parts[8] != null && question_Parts[8].length() > 3){
//						codeText = question_Parts[8];
//					}
//				}
//				//   -------------- !!! TO DO get to user question.sql file for fill users database !!!--------  ------------------  -----  //NUMBERofRESPONSESinThePICTURE ----- default = 4  
//				/*(questionText, fileLocationLink, metaCategory, category, levelOfDifficulty, answers, correctAnswer, questionNumber, numberOfResponsesInThePicture, description, codeText, languageName*/
//				flagAction = CreateNewQuestion(questionText, fileLocationLink, metaCategory, category, levelOfDifficulty, answers, correctAnswer, questionNumber, NUMBERofRESPONSESinThePICTURE, description ,  codeText, languageName );
//			}			
//		}catch(Exception e){
//			//e.printStackTrace();
//			System.out.println("catch from adding from file method BES");// ----- -------------- sysout
//		}
//		//
//		return flagAction;
//	}
	////-------------- Reading from file and Adding Questions into DB Case ----------// END  //
	
	
	////-------------- internal method for filling in the form update issue ----------// BEGIN  //
	
	
	////

	////-------------- internal method for filling in the form update issue ----------// END  //	
	//// ------------- Build Data end ---
	
	
	////-------------- Builder of page witch categories check box ----------// BEGIN  //	
//	@Override	
//	public List<String> getAllCategories1FromDataBase() {
//		String query = "Select DISTINCT q.category1 FROM EntityQuestionAttributes q ORDER BY q.category1";
//		return getQuery(query);
//	}
//
//	@Override
//	public List<String> getAllCategories2FromDataBase() {
//		String query = "Select DISTINCT q.category2 FROM EntityQuestionAttributes q ORDER BY q.category2";
//		return getQuery(query);
//	}
//	@Override
//	public List<String> getAllMetaCategoriesFromDataBase() {
//		String query = "Select DISTINCT q.metaCategory FROM EntityQuestionAttributes q ORDER BY q.metaCategory";
//		return getQuery(query);
//	}	
	////-------------- Builder of page witch categories check box ----------// END  //
	
	
	////
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
			List<EntityAnswersText> liEntAns = objEntQue.getQuestionAnswersList();
			for(EntityAnswersText entAns:liEntAns){
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
	////
	protected void DeleteImageFromFolder(String linkForDelete) {		
		try {
			Files.delete(Paths.get(System.getProperty("user.dir") + "\\" + NAME_FOLDER_FOR_SAVENG_QUESTIONS_FILES + linkForDelete));
		} catch (IOException e) {
			System.out.println("BES delete image from folder catch IOException, image is not exist !!!");// ------------------------------------------------------- sysout
			//e.printStackTrace();   
		}
	}
	////-------------- Method for delete question into DB ----------// END  //

	
	
	////-------------- Search Method by Category or Categories and level of difficulty ----------// BEGIN  //
	@SuppressWarnings("unchecked")	

	@Override
	public List<String> SearchAllQuestionInDataBase(String metaCategory,
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
						+ tempRes.getQuestionId().getQuestionText() + DELIMITER 
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
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW)	
	public boolean UpdateTextQuestionInDataBase(String questionID, 
			String questionText, String descriptionText, String codeText, String category1, 
			String metaCategory, String category2, int levelOfDifficulty, List<String> answers, String correctAnswer, 
			String fileLocationPath, String numAnswersOnPictures){		
		boolean flagAction = false;	
		long id = (long)Integer.parseInt(questionID);
		////  ---- chang question data
		try{
			EntityQuestionAttributes elem = em.find(EntityQuestionAttributes.class, id);
			elem.getQuestionId().setQuestionText(questionText);	
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
			List<EntityAnswersText> oldAnswersList = elem.getQuestionAnswersList();
			List<EntityAnswersText> newAnswersList;
			
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
					EntityAnswersText text = oldAnswersList.get(i);
					text.setAnswerText(answers.get(i));		
					em.persist(text);
					newAnswersList.add(text);
				}
				if(oldListSize > newListSize)
					for(int i = max; i <oldListSize; i++) {
						EntityAnswersText text = oldAnswersList.get(i);
						em.remove(text);
						em.flush();
					}
				
				if(oldListSize < newListSize)
					for (int i = max; i < newListSize; i++) {
						EntityAnswersText text = new EntityAnswersText();
						text.setAnswerText(answers.get(i));
						text.setQuestionAttributeId(elem);
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
	
	
	
	

	
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public boolean CreateCompany(String C_Name, String C_Site, String C_Specialization, String C_AmountEmployes, String C_Password) {
		boolean result=false;		
		try {
			EntityCompany comp = new EntityCompany();
			comp.setC_Name(C_Name);
			comp.setC_Site(C_Site);
			comp.setC_Specialization(C_Specialization);
			comp.setC_AmountEmployes(C_AmountEmployes);
			comp.setPassword(C_Password);
			em.persist(comp);
			result=true;
		} catch (Throwable e) {
			result=false;
			System.out.println("catch from CREATE COMPANY BES");
			e.printStackTrace();
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

	@Override
	protected String getLimitsForQuery() {
		return null;
	}




	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public String[] getAnySingleQuery(String strQuery) {
		if(em.find(EntityAdministrators.class,"qqq@qqq.qq") == null){
			EntityAdministrators emad = new EntityAdministrators();
			emad.setPassportNumber("12345");
			emad.setUserMail("qqq@qqq.qq");
			emad.setUserPassword("12345");
			emad.setUserName("test");
			emad.setUserAddress("californy");
			em.persist(emad);
		}
		String[] outResult;
		List<EntityCompany> result = em.createQuery(
				"SELECT c FROM EntityCompany c WHERE c.C_Name LIKE :custName").setParameter("custName","%"+strQuery+"%").getResultList();// return to client result of operation
		int len_gth = result.size();
		outResult = new String[len_gth];
		int flCount = 0;
		for(EntityCompany q: result){		
			if(flCount != len_gth){
				outResult[flCount++] = q.toString();
			}
		}
		return outResult;// return to client 
	}


			
}

//// ----- END Code -----
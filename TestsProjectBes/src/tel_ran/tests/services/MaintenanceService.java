package tel_ran.tests.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tel_ran.tests.entitys.EntityAdministrators;
import tel_ran.tests.entitys.EntityAnswersText;
import tel_ran.tests.entitys.EntityQuestion;
import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.processor.TestProcessor;
import tel_ran.tests.services.interfaces.IMaintenanceService;


public class MaintenanceService extends TestsPersistence implements IMaintenanceService {	
	//
	private static int NUMBERofRESPONSESinThePICTURE = 4;// number of responses in the picture, for text question`s default = 4
	private static int MIN_NUMBER_OF_CATEGORIES = 1;
	private static boolean FLAG_AUTHORIZATION = false;	
	//
	////-------------- Authorization Case ----------// Begin  //	
	// ---------- stub method authorization ------------------//
	@Override	
	public boolean setAutorization(String userMail, String password) { 
		////
		EntityAdministrators tmpUser = null;
		try {
			tmpUser = em.find(EntityAdministrators.class, userMail); 
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
	public static boolean isAuthorized(){return FLAG_AUTHORIZATION;}
	////-------------- Authorization users like Administrator DB ----------// END  //

	////-------------- Creation and Adding ONE Question into DB Case ----------// BEGIN  //
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW) 
	public boolean CreateNewQuestion(String questionText,
			String fileLocationLink, String metaCategory, String category, 
			int levelOfDifficulty, List<String> answers, String correctAnswer,
			int questionNumber, int numberOfResponsesInThePicture, String description, 
			String codeText, String languageName){
		////
		boolean flagAction = false;	
		long keyQuestion = 0;
		EntityQuestion objectQuestion;	
		Object queryTempObj;
		//// query if question exist as text in Data Base
		Query tempRes = em.createQuery("SELECT q FROM EntityQuestion q WHERE q.questionText='"+questionText+"'");
		try{
			tempRes.getSingleResult();
			queryTempObj = tempRes.getSingleResult();				
		}catch(javax.persistence.NoResultException e){
			queryTempObj = null;			
		}
		//// if question not exist in DB creating a new question full flow
		if(queryTempObj == null && em.find(EntityQuestion.class,(long) questionNumber) == null){				
			objectQuestion = new EntityQuestion();	
			objectQuestion.setQuestionText(questionText);
			objectQuestion.setDescription(description);
			em.persist(objectQuestion);
			//
			keyQuestion = objectQuestion.getId(); 
			//
			List<EntityQuestionAttributes> questionAttributes = new ArrayList<EntityQuestionAttributes>();
			questionAttributes.add(createAttributes(fileLocationLink, category,  levelOfDifficulty, 
					correctAnswer, answers, keyQuestion, numberOfResponsesInThePicture, codeText, languageName, metaCategory));			
			objectQuestion.setQuestionAttributes(questionAttributes);
			em.persist(objectQuestion);
			flagAction = true;			
		}
		////  When the question exists, create new attributes question and add in DB in to question by 'questionNumber' that ID of question
		if(queryTempObj != null && (objectQuestion = em.find(EntityQuestion.class,(long) questionNumber)) != null){
			objectQuestion
			.getQuestionAttributes()
			.add(createAttributes(fileLocationLink, category,  levelOfDifficulty, correctAnswer,	
					answers, objectQuestion.getId(),numberOfResponsesInThePicture, codeText, metaCategory, metaCategory));		
			em.persist(objectQuestion);
			flagAction = true;
		}
		em.clear();	
		//		
		return flagAction;
	}
	////
	private EntityQuestionAttributes createAttributes(String fileLocationFullPath, 
			String category,
			int levelOfDifficulty, 
			String correctAnswer, 
			List<String> answers, 
			long keyQuestion,
			int numberOfResponsesInThePicture, 
			String codeText, 
			String languageName, 
			String metaCategory){
		EntityQuestionAttributes questionAttributesList = new EntityQuestionAttributes();// new question attributes creation		
		EntityQuestion objectQuestion = em.find(EntityQuestion.class, keyQuestion);	//selecting from DB question by id (long)
		////
		questionAttributesList.setQuestionId(objectQuestion);		
		questionAttributesList.setFileLocationLink(fileLocationFullPath);// file location path (string) 
		questionAttributesList.setCategory(category);
		questionAttributesList.setLevelOfDifficulty(levelOfDifficulty);	
		questionAttributesList.setCorrectAnswer(correctAnswer);		
		questionAttributesList.setNumberOfResponsesInThePicture(numberOfResponsesInThePicture);
		questionAttributesList.setLineCod(codeText);// example code text for question witch any language sintax qwestion ,user write any code by this pattern !	
		questionAttributesList.setLanguageName(languageName);
		questionAttributesList.setMetaCategory(metaCategory);
		////
		em.persist(questionAttributesList);	
		long keyAttr = questionAttributesList.getId();
		if(answers != null)	{			
			List<EntityAnswersText> answersList = new ArrayList<EntityAnswersText>();
			for (String answerText : answers) {					
				EntityAnswersText ans = WriteNewAnswer(answerText, keyAttr); 
				answersList.add(ans);  				
			}			
			questionAttributesList.setQuestionAnswersList(answersList);// mapping to answers
		}
		em.persist(questionAttributesList);	
		return questionAttributesList;
	}
	////
	private EntityAnswersText WriteNewAnswer(String answer, long keyAttr){		
		EntityAnswersText temp = new EntityAnswersText();
		EntityQuestionAttributes qAttrId = em.find(EntityQuestionAttributes.class, keyAttr);
		temp.setAnswerText(answer);		
		temp.setQuestionAttributeId(qAttrId);	
		em.persist(temp);		
		return temp;
	}
	////-------------- Creation and Adding ONE Question into DB Case ----------// END  //
	//// ------------- Build Data 
	////-------------- Creation and Adding MANY Questions into DB from Generated Question Case ----------// BEGIN  //
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
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW)	
	public boolean ModuleForBuildingQuestions(String byCategory, int diffLevel, int nQuestions) {
		boolean flagAction = false;	
		int DIF_LEVEL = diffLevel;
		List<String> answers;	
		List<String[]> listQuestions = null;		
		TestProcessor proc = new TestProcessor();
		////
		try {
			String workingDir = System.getProperty("user.dir") + File.separator + NAME_FOLDER_FOR_SAVENG_QUESTIONS_FILES;			
			Files.createDirectories(Paths.get(workingDir));
			listQuestions = proc.processStart(byCategory, nQuestions, workingDir + File.separator, DIF_LEVEL);
		} catch (Exception e1) {
			System.err.println("catch of TestProcessor.testProcessStart(String category, int, String, int);");
			e1.printStackTrace();
		}		
		////
		if(listQuestions != null){
			for(String[] fres :listQuestions){	
				//for(int i=0;i<fres.length;i++){	System.out.println(i+ " - "+fres[i]+"\n");}// -------------------------------------------- test - susout
				int EXIST_QUESTION_NUM = 0;
				answers = new ArrayList<String>();						
				int numberOfResponsesInThePicture = Integer.parseInt(fres[5]);
				Object queryTempObj;
				//// query if question exist as text in Data Base
				String questionText = fres[0].replace("'", "");
				Query tempRes = em.createQuery("SELECT q FROM EntityQuestion q WHERE q.questionText='" + questionText + "'");
				try{
					tempRes.getSingleResult();
					queryTempObj = tempRes.getSingleResult();	
					EntityQuestion enTq = (EntityQuestion) queryTempObj;
					EXIST_QUESTION_NUM = (int)enTq.getId();
				}catch(javax.persistence.NoResultException e){
					queryTempObj = null;			
				}
				////
				/* 0 - question text  ("Реализуйте интерфейс")
				 * 1 - description (текст интерфейса и траляля) - тут был прежде линк на картинку( this field may bee null!!!)
				 * 2 - category (маленькой. тут - Калькулятор)
				 * 3 - level of difficulty = 1-5
				 * 4 - char rhite answer = ( this field may bee null!!!)
				 * 5 - number responses on pictures = 1-...
				 * 6 - file location linc (for all saving files)  ( this field may bee null!!!)
				 * 7 - languageName	(bee as meta category for question witch code example)  ( this field may bee null!!!)
				 * 8 - code pattern	 (pattern code for Person)	( this field may bee null!!!)
				 */				

				if(queryTempObj != null){					
					questionText = fres[0].replace("'", "");
					String fileLocationLink = fres[6];
					String metaCategory = byCategory;// TO DO !!!!!!!!!!!!!!!!!! by what category that bee save ???
					String category = fres[2];				
					int levelOfDifficulty = Integer.parseInt(fres[3]);
					String correctAnswer = fres[4];
					String codeText = "";
					if(fres[8] != null && fres[8].length() > 3){
						codeText = fres[8];
					}
					String description = fres[1];
					String languageName = fres[7];
					//                           /*questionText, fileLocationLink, metaCategory, category, levelOfDifficulty, answers, correctAnswer, questionNumber, numberOfResponsesInThePicture, description, codeText, languageName*/					
					flagAction = CreateNewQuestion(questionText, fileLocationLink, metaCategory, category, levelOfDifficulty, answers, correctAnswer, 
							EXIST_QUESTION_NUM, numberOfResponsesInThePicture, description, codeText, languageName);
				}else{
					questionText = fres[0].replace("'", "");
					String fileLocationLink = fres[6];
					String metaCategory = byCategory;// TO DO !!!!!!!!!!!!!!!!!! by what category that bee save ???
					String category = fres[2];				
					int levelOfDifficulty = Integer.parseInt(fres[3]);
					String correctAnswer = fres[4];
					String codeText = "";
					if(fres[8] != null && fres[8].length() > 3){
						codeText = fres[8];
					}
					String description = fres[1];
					String languageName = fres[7];
					//                            /*questionText, fileLocationLink, metaCategory, category, levelOfDifficulty, answers, correctAnswer, questionNumber, numberOfResponsesInThePicture, description, codeText, languageName*/	
					flagAction = CreateNewQuestion(questionText, fileLocationLink, metaCategory, category, levelOfDifficulty, answers, 	correctAnswer, 
							NEW_QUESTION, numberOfResponsesInThePicture, description, codeText, languageName);
				}
			}
		}
		return flagAction;
	}
	////-------------- Creation and Adding MANY Questions into DB from Generated Question Case ----------// END  //

	////-------------- Reading from file and Adding Questions into DB Case ----------// BEGIN  //
	//  //  --------------------------------------------   TO DO factory method for this case !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW)	
	public boolean FillDataBaseFromTextResource(List<String> inputTextFromFile){
		// !!!!!!!!!!! worked only in Mozila and Hrome !!!!!!!!!!!
		/*
		 *  sample for text in file witch questions (THIS ONLY ONE LINE!!!)
		 * 
		 * questionText----description----fileLocationLink----
		 * metaCategory----category----levelOfDifficulty----
		 * answer1----answer2----answer3----answer4----
		 * correctAnswerChar----questionIndexNumber----languageName
		 * 
		 */
		boolean flagAction = false;
		String fileLocationLink = null;
		String questionText = null;
		String category = null;
		int levelOfDifficulty = 1;
		List<String> answers = null;
		String correctAnswer = null;
		int questionNumber = 0;
		String languageName = null;
		String metaCategory = null;
		String description = null; 
		String codeText = null;
		//
		try{			
			for(String line: inputTextFromFile){ 			
				String[] question_Parts = line.split(DELIMITER); //delimiter for text, from interface IMaintenanceService
				//
				if(question_Parts.length > 6){		
					questionText = question_Parts[0];
					fileLocationLink = question_Parts[1];			
					category = question_Parts[2];
					levelOfDifficulty = Integer.parseInt(question_Parts[3]);
					//
					answers = new ArrayList<String>();									
					answers.add(question_Parts[4]);	
					answers.add(question_Parts[5]);
					answers.add(question_Parts[6]);
					answers.add(question_Parts[7]);				
					//
					correctAnswer = question_Parts[8];
					questionNumber = 0;	
					languageName = null;
					metaCategory = null;
					description = null; 
					if(question_Parts[8] != null && question_Parts[8].length() > 3){
						codeText = question_Parts[8];
					}
				}else{
					//When question exist method added only new attributes for this question
					//else if question not exist method added a new question full
					questionNumber = Integer.parseInt(question_Parts[5]);	
					questionText = question_Parts[0];
					fileLocationLink = question_Parts[1];			
					category = question_Parts[2];
					levelOfDifficulty = Integer.parseInt(question_Parts[3]);
					correctAnswer = question_Parts[4];
					languageName = null;
					metaCategory = null;// TO DO !!!!! create conections for field witch txt file
					description = null; 
					if(question_Parts[8] != null && question_Parts[8].length() > 3){
						codeText = question_Parts[8];
					}
				}
				//   -------------- !!! TO DO get to user question.sql file for fill users database !!!--------  ------------------  -----  //NUMBERofRESPONSESinThePICTURE ----- default = 4  
				/*(questionText, fileLocationLink, metaCategory, category, levelOfDifficulty, answers, correctAnswer, questionNumber, numberOfResponsesInThePicture, description, codeText, languageName*/
				flagAction = CreateNewQuestion(questionText, fileLocationLink, metaCategory, category, levelOfDifficulty, answers, correctAnswer, questionNumber, NUMBERofRESPONSESinThePICTURE, description ,  codeText, languageName );
			}			
		}catch(Exception e){
			//e.printStackTrace();
			System.out.println("catch from adding from file method BES");// ----- -------------- sysout
		}
		//
		return flagAction;
	}
	////-------------- Reading from file and Adding Questions into DB Case ----------// END  //
	////-------------- internal method for filling in the form update issue ----------// BEGIN  //
	@Override
	public String[]  getQuestionById(String questionID, int actionKey) {// getting question attributes by ID !!!!!!!!!!!!!!!!!!!!!!!!!  
		String[] outArray = new String[4];
		String  outRes = "";
		String outAnsRes = " ";		
		List<EntityAnswersText> answers;
		long id = (long)Integer.parseInt(questionID);
		EntityQuestionAttributes question = em.find(EntityQuestionAttributes.class, id);
		////
		if(question != null){
			answers = question.getQuestionAnswersList();
			String imageBase64Text=null;
			String fileLocation;
			////
			if((fileLocation = question.getFileLocationLink()) != null && question.getFileLocationLink().length() > 25 && !question.getMetaCategory().equals("Programming_Task")){
				imageBase64Text = encodeImage(NAME_FOLDER_FOR_SAVENG_QUESTIONS_FILES  + fileLocation);
				outArray[1] = "data:image/png;base64," + imageBase64Text; // TO DO delete!!! hard code -- data:image/png;base64,
			}else{
				outArray[1] = null;
			}
			////
			switch(actionKey){
			case ACTION_GET_ARRAY: outRes = question.getQuestionId().getQuestionText() + DELIMITER // text of question or Description to picture or code									
					+ question.getCorrectAnswer() + DELIMITER // correct answer char 
					+ question.getNumberOfResponsesInThePicture() + DELIMITER // number of answers chars on image
					+ question.getLineCod() + DELIMITER // code question text
					+ question.getMetaCategory();
			break;		
			case ACTION_GET_FULL_ARRAY: outRes = question.getQuestionId().getQuestionText() + DELIMITER// text of question
					+ question.getQuestionId().getDescription() + DELIMITER  	// text of  Description 	
					+ question.getLineCod() + DELIMITER // code question text	
					+ question.getLanguageName() + DELIMITER // language of sintax code in question
					+ question.getMetaCategory() + DELIMITER // meta category 
					+ question.getCategory() + DELIMITER// category of question
					+ question.getId() + DELIMITER// static information
					+ question.getCorrectAnswer() + DELIMITER // correct answer char 
					+ question.getNumberOfResponsesInThePicture() + DELIMITER// number of answers chars on image					
					+ question.getLevelOfDifficulty();// level of difficulty for question
			break;
			default:System.out.println(" default switch");
			}				
			if(answers != null){
				for(EntityAnswersText tAn :answers){
					outAnsRes += tAn.getAnswerText() + DELIMITER;	// answers on text if exist!!!
				}
				outArray[3] = outAnsRes.toString();
			}	
			outArray[0] = outRes;// question attributes in text			
			outArray[2] = NAME_FOLDER_FOR_SAVENG_QUESTIONS_FILES + fileLocation;// that static parameter for one operation!. when question is update from admin panel !!!
		}else{
			outArray[1] = " "; // TO DO delete!!! hard code -- data:image/png;base64,
			outArray[0] = "wrong request q.Id-"+questionID;// out text stub for tests 
		}		
		return outArray ;// return to client 
	}
	////
	private String encodeImage(String imageLink) {	//method getting jpg file and converting to base64 for sending to FES 		
		String res = null;
		byte[] bytes = null;
		FileInputStream file;
		try {
			file = new FileInputStream(imageLink);
			bytes = new byte[file.available()];
			file.read(bytes);
			file.close();
			res = Base64.getEncoder().encodeToString(bytes);
		} catch (FileNotFoundException e) {	} 
		catch (IOException e) {
			System.out.println("file not found");//-------------------------------------------------------------------------sysout	
		} 
		catch (NullPointerException e) {
			System.out.println("Null Pointer Exception");//-----------------------------------------------------------------sysout	
		}
		return res;
	}
	////-------------- internal method for filling in the form update issue ----------// END  //
	//// ------------- Build Data end ---
	////-------------- Builder of page witch categories check box ----------// BEGIN  //
	@Override
	@SuppressWarnings("unchecked")
	public List<String> getAllCategoriesFromDataBase() {
		String query = "Select DISTINCT q.category FROM EntityQuestionAttributes q ORDER BY q.category";
		Query q = em.createQuery(query);
		List<String> allCategories = q.getResultList();		
		return allCategories;
	}
	////-------------- Builder of page witch categories check box ----------// END  //
	////
	////-------------- Method for delete question into DB ----------// BEGIN  //
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW)	
	public String deleteQuetionById(String questionID){// !!!!!!!!!  delete one question witch all questions attributes and image in folder !!!!!!!
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
	private void DeleteImageFromFolder(String linkForDelete) {		
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
	public List<String> SearchAllQuestionInDataBase(String category, int levelOfDifficulty) {	// !!!!!!!!!!!!!!!!!!!!!!!!!!!! not work in mazila		
		List<String> outResult = new ArrayList<String>();		
		////
		if(category != null && !category.equalsIgnoreCase("")){		
			//
			try {
				List<EntityQuestionAttributes> query = em.createQuery("SELECT c FROM EntityQuestionAttributes c WHERE "
						+ "(c.levelOfDifficulty="+levelOfDifficulty+") AND (c.category='"+category+"')").getResultList();
				////
				for(EntityQuestionAttributes tempRes: query){				
					outResult.add(tempRes.getId() + DELIMITER
							+ tempRes.getQuestionId().getQuestionText() + DELIMITER 
							+ tempRes.getCategory());
				}
			} catch (StringIndexOutOfBoundsException e) {
				System.out.println(" BES Search all question in category ' catch case");
				e.printStackTrace();
			}		
		}else{
			outResult = null;
		}		
		return outResult;// return to client 
	}
	////-------------- Search Method by Category or Categories and level of difficulty ----------// END  //

	/////-------------- Update  ONE Question into DB Case ----------// BEGIN  //
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW)	
	public boolean UpdateTextQuestionInDataBase(String questionID, 
			String questionText, String descriptionText, String codeText, String languageName, 
			String metaCategory, String category, int levelOfDifficulty, List<String> answers, String correctAnswer, 
			String fileLocationPath, String numAnswersOnPictures){		
		boolean flagAction = false;	
		long id = (long)Integer.parseInt(questionID);
		////  ---- chang question data
		try{
			EntityQuestionAttributes elem = em.find(EntityQuestionAttributes.class, id);
			elem.getQuestionId().setQuestionText(questionText);	
			elem.getQuestionId().setDescription(descriptionText);
			elem.setMetaCategory(metaCategory);
			elem.setCategory(category);
			elem.setLineCod(codeText);
			elem.setLanguageName(languageName);	
			elem.setLevelOfDifficulty(levelOfDifficulty);
			elem.setCorrectAnswer(correctAnswer);
			elem.setFileLocationLink(fileLocationPath);
			int numberOfResponsesInThePicture = Integer.parseInt(numAnswersOnPictures);
			elem.setNumberOfResponsesInThePicture(numberOfResponsesInThePicture );
			//      
			if(answers != null){
				List<EntityAnswersText> answersList = elem.getQuestionAnswersList();	 	
				int i=0;			
				for(EntityAnswersText text:answersList){					
					text.setAnswerText(answers.get(i++));					
					em.persist(text);
				}  
			}					
			em.persist(elem);

			flagAction = true;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("BES not good in maintenance service update question");
		}
		return flagAction;// return to client 
	}	
	////-------------- Update  ONE Question into DB Case ----------// END  //
	////
	////-------------- Method for test case group AlexFoox Company return id of unique set questions ----------// BEGIN  //
	@SuppressWarnings("unchecked")	
	@Override  
	public List<Long> getUniqueSetQuestionsForTest(String category, String levelsOfDifficulty,  Long nQuestion){
		List<Long> result = new ArrayList<Long>();
		if(nQuestion > 0 && category != null){		
			String[] categoryArray = category.split(",");	
			String[] levelsArray = levelsOfDifficulty.split(",");
			StringBuffer condition = new StringBuffer();
			if(categoryArray.length > MIN_NUMBER_OF_CATEGORIES){      
				for(int i=0, j=1; j<categoryArray.length; i=i+2, j++){
					condition = condition.append(" OR ((c.levelOfDifficulty=?" + (i+3) + ") AND (c.category=?" + (i+4) + "))");
				}
			}
			Query query = em.createQuery("SELECT c.id FROM EntityQuestionAttributes c WHERE ((c.levelOfDifficulty=?1) AND (c.category=?2))" + condition.toString());
			query.setParameter(1, Integer.parseInt(levelsArray[0]));
			query.setParameter(2, categoryArray[0]);
			if(categoryArray.length > MIN_NUMBER_OF_CATEGORIES){ 
				for(int i=0, j=1; j<categoryArray.length; i=i+2, j++){				
					query.setParameter((i+3), Integer.parseInt(levelsArray[j]));
					query.setParameter((i+4), categoryArray[j]);
				}
			}
			List<Long> allAttributeQuestionsId = query.getResultList();	
			result = randomAttributeQuestionsId(allAttributeQuestionsId, nQuestion);
		}
		return result;
	}
	////
	private static List<Long> randomAttributeQuestionsId(List<Long> allAttributeQuestionsId, Long nQuestion){
		List<Long> result = new ArrayList<Long>();
		if(allAttributeQuestionsId.size() > 0){
			if(nQuestion >= allAttributeQuestionsId.size()){
				result = allAttributeQuestionsId;
			}else{
				for(int i=0; i<nQuestion;){	
					Random rnd = new Random();
					int rand =  rnd.nextInt(allAttributeQuestionsId.size());							
					long questionAttributeId = allAttributeQuestionsId.get(rand);
					if(result.size() > 1){
						int flag = 0;          
						for(Long num: result){
							if(num == questionAttributeId){
								flag = 1;
							}
						}	
						if(flag == 0){
							result.add(questionAttributeId);
							i++;	
						}						
					}else{
						result.add(questionAttributeId);
						i++;
					}
				}				
			}
		}
		return result;
	}////-------------- Method for test case group AlexFoox Company return id of unique set questions ----------// END  //

	@Override
	public List<String> GetGeneratedExistCategory(){
		// Новый метод (статический) - TestProcessor.getMetaCategory() - возвращает лист стрингов с названием мета-категорий
		return  TestProcessor.getMetaCategory();
	}	
}

//// ----- END Code -----
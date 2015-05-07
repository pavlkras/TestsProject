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
	private static int NUMBERofRESPONSESinThePICTURE = 4;// number of responses in the picture, for text questions default = 4
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
			System.out.println("administratir catch em.find() action");
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
	public boolean CreateNewQuestion(String imageLink, String questionText, 
			String category, int levelOfDifficulty, List<String> answers, 
			String correctAnswer,int questionNumber, int numberOfResponsesInThePicture, String codeText){
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
			em.persist(objectQuestion);
			//
			keyQuestion = objectQuestion.getId(); 
			//
			List<EntityQuestionAttributes> questionAttributes = new ArrayList<EntityQuestionAttributes>();
			EntityQuestionAttributes qattr = addQuestionAttributes(imageLink, category,  levelOfDifficulty, correctAnswer, answers, keyQuestion, numberOfResponsesInThePicture, codeText);
			questionAttributes.add(qattr);
			//
			objectQuestion.setQuestionAttributes(questionAttributes);
			//
			em.persist(objectQuestion);
			flagAction = true;			
		}
		////  if the question exists, create new attributes question and add in DB in to question by 'questionNumber' that ID of question
		if(queryTempObj != null && (objectQuestion = em.find(EntityQuestion.class,(long) questionNumber)) != null){	
			keyQuestion = objectQuestion.getId(); 
			//
			List<EntityQuestionAttributes> questionAttributes = objectQuestion.getQuestionAttributes();
			EntityQuestionAttributes qattr = addQuestionAttributes(imageLink, category,  levelOfDifficulty, correctAnswer,answers, keyQuestion,numberOfResponsesInThePicture, codeText);
			questionAttributes.add(qattr);
			objectQuestion.setQuestionAttributes(questionAttributes);
			em.persist(objectQuestion);
			flagAction = true;
		}
		em.clear();	
		//		
		return flagAction;
	}
	////
	private EntityQuestionAttributes addQuestionAttributes(String imageLink, String category,
			int levelOfDifficulty, String correctAnswer, List<String> answers, long keyQuestion,
			int numberOfResponsesInThePicture, String codeText) {
		EntityQuestionAttributes questionAttributesList = new EntityQuestionAttributes();
		EntityQuestion objectQuestion = em.find(EntityQuestion.class, keyQuestion);			
		////
		if(imageLink != null && imageLink.length() > 15){
			questionAttributesList.setImageLink(imageLink);
		}
		////
		if(codeText != null && codeText.length() > 10){		// parsing code for save in to db 				
			questionAttributesList.setLineCod(codeText);			
		}else{
			questionAttributesList.setLineCod(null);
		}
		////
		questionAttributesList.setCategory(category);
		questionAttributesList.setLevelOfDifficulty(levelOfDifficulty);
		questionAttributesList.setCorrectAnswer(correctAnswer);
		questionAttributesList.setNumberOfResponsesInThePicture(numberOfResponsesInThePicture);
		questionAttributesList.setQuestionId(objectQuestion);	
		////
		em.persist(questionAttributesList);	

		if(answers != null){
			List<EntityAnswersText> answersList = new ArrayList<EntityAnswersText>();
			for (String answerText : answers) {				
				long keyAttr = questionAttributesList.getId();
				EntityAnswersText ans = addAnswersList(answerText, keyAttr ); 
				answersList.add(ans);  				
			}			
			questionAttributesList.setQuestionAnswersList(answersList);// mapping to answers
		}
		return questionAttributesList;
	}
	////
	private EntityAnswersText addAnswersList(String answer, long keyAttr) {		
		EntityAnswersText temp = new EntityAnswersText();
		EntityQuestionAttributes qAttrId = em.find(EntityQuestionAttributes.class, keyAttr);
		temp.setAnswerText(answer);		
		temp.setQuestionAttributeId(qAttrId);	
		em.persist(temp);
		return temp;
	}

	////-------------- Creation and Adding ONE Question into DB Case ----------// END  //
	//// ------------- Build Data 
	////-------------- Creation and Adding MANY Questions into DB from Generated Question Case ----------// BEGIN  //-------------------------------------------------------------------

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW)	
	public boolean ModuleForBuildingQuestions(String byCategory, int nQuestions) {
		/*------------------------------------------------------------------------------- TO DO add parameter for diff level of generated questions !!
		 * |question text|| image link || category of question || level of complexity ||correct answer char || number answers on image ( A,B or A,B,C,D and ...) |
		 * Sample - String[] oneQuestion = {"In which row the sequence is incorrect? ","\Attention Test\cdc922b32a0496f7b401cc3eedeaa59.jpg","LongAttentionLines","5","A","5"};
		 * question.length = 6. //// +4 or > for answers in text "a51","a52","a53","a54"  that bee letar */

		boolean flagAction = false;	
		int DIF_LEVEL = 3;
		int selectedCategory = 0;
		List<String> answers;	
		List<String[]> listQuestions = null;		
		TestProcessor proc = new TestProcessor();// creating object of case generated question
		////
		if(byCategory.equalsIgnoreCase("abstract")){
			selectedCategory = TestProcessor.ATTENTION;
		}else if(byCategory.equalsIgnoreCase("attention")){
			selectedCategory = TestProcessor.ABSTRACT;
		}else if(byCategory.equalsIgnoreCase("quantative")){
			selectedCategory = TestProcessor.QUANTATIVE;
		}
		////
		try {			
			/* TestProcessor proc = new TestProcessor();// creating object of case generated question
			 * proc.processStart(TYPE, NUM, PATH, DIF_LEVEL); 
			 * TYPE = TestProcessor.ATTENTION //TestProcessor.ABSTRACT //TestProcessor.QUANTATIVE
			 * NUM - numbers of question for build
			 * PATH - full path for saving images for questions
			 * DIF_LEVEL - level of complexity
			 * returned List<String[]>*/
			String workingDir = System.getProperty("user.dir");
			Files.createDirectories(Paths.get(workingDir + File.separator + NAME_FOLDER_FOR_SAVENG_QUESTION_PICTURES));
			listQuestions =	proc.processStart(selectedCategory, nQuestions, workingDir + File.separator + NAME_FOLDER_FOR_SAVENG_QUESTION_PICTURES + File.separator, DIF_LEVEL );// - вызвать генерацию.
		} catch (Exception e) {
			System.out.println(" catch of test case generated q = ModuleForBuildingQuestions= ");
			e.printStackTrace();
		}  
		////
		if(listQuestions != null){
			for(String[] fres :listQuestions){				
				answers = new ArrayList<String>();
				if(fres.length > 6){				
					answers.add(fres[6]);answers.add(fres[7]);answers.add(fres[8]);answers.add(fres[9]);
				}			
				int numberOfResponsesInThePicture = Integer.parseInt(fres[5]);
				Object queryTempObj;
				//// query if question exist as text in Data Base
				String questionT = fres[0].replace("'", "");
				Query tempRes = em.createQuery("SELECT q FROM EntityQuestion q WHERE q.questionText='" + questionT + "'");
				try{
					tempRes.getSingleResult();
					queryTempObj = tempRes.getSingleResult();				
				}catch(javax.persistence.NoResultException e){
					queryTempObj = null;			
				}
				////
				if(queryTempObj != null){
					questionT = fres[0].replace("'", "");
					EntityQuestion enTq = (EntityQuestion) queryTempObj;
					flagAction = CreateNewQuestion(fres[1], questionT, fres[2], Integer.parseInt(fres[3]), answers, fres[4], (int)enTq.getId(), numberOfResponsesInThePicture, null);
				}else{
					questionT = fres[0].replace("'", "");
					flagAction = CreateNewQuestion(fres[1], questionT, fres[2], Integer.parseInt(fres[3]), answers, fres[4], NEW_QUESTION, numberOfResponsesInThePicture, null);
				}
			}
		}
		return flagAction;
	}
	////-------------- Creation and Adding MANY Questions into DB from Generated Question Case ----------// END  //

	////-------------- Reading from file and Adding Questions into DB Case ----------// BEGIN  //
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW)	
	public boolean FillDataBaseFromTextResource(List<String> inputTextFromFile){// !!!!!!!!!!! worked only in Mozila and Hrome !!!!!!!!!!!
		//sample for text in file question(one line!!!)
		//questionText----imageLink----category----levelOfDifficulty----answer1----answer2----answer3----answer4----correctAnswerChar----questionIndexNumber
		//		
		boolean flagAction = false;
		String imageLink = null;
		String questionText = null;
		String category = null;
		int levelOfDifficulty = 1;
		List<String> answers = null;
		String correctAnswer = null;
		int questionNumber = 0;
		//
		try{			
			for(String line: inputTextFromFile){ 			
				String[] question_Parts = line.split(DELIMITER); //delimiter for text, from interface IMaintenanceService
				//
				if(question_Parts.length > 6){		
					questionText = question_Parts[0];
					imageLink = question_Parts[1];			
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
				}else{
					//if question exist method added only new attributes for this question
					//else if question not exist method added a new question full
					questionNumber = Integer.parseInt(question_Parts[5]);	
					questionText = question_Parts[0];
					imageLink = question_Parts[1];			
					category = question_Parts[2];
					levelOfDifficulty = Integer.parseInt(question_Parts[3]);
					correctAnswer = question_Parts[4];						
				}
				//   --------------  --------------  --------------  ----------------  ------------------  -----  //NUMBERofRESPONSESinThePICTURE ----- default = 4  
				flagAction = CreateNewQuestion(imageLink, questionText, category, levelOfDifficulty, answers, correctAnswer, questionNumber, NUMBERofRESPONSESinThePICTURE, null );
			}			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("catch from adding from file method BES");
		}
		//
		return flagAction;
	}
	////-------------- Reading from file and Adding Questions into DB Case ----------// END  //
	////-------------- internal method for filling in the form update issue ----------// BEGIN  //
	@Override
	public String[]  getQuestionById(String questionID, int actionKey) {// getting question attributes by ID !!!!!!!!!!!!!!!!!!!!!!!!!  
		String[] outArray = new String[3];
		String  outRes = "";
		long id = (long)Integer.parseInt(questionID);
		EntityQuestionAttributes question = em.find(EntityQuestionAttributes.class, id);
		////
		if(question != null){
			String imageBase64Text=null;
			String imageLink;
			////
			if((imageLink = question.getImageLink()) != null && question.getImageLink().length() > 25){
				imageBase64Text = encodeImage(NAME_FOLDER_FOR_SAVENG_QUESTION_PICTURES  + imageLink);
				outArray[1] = "data:image/png;base64," + imageBase64Text; // TO DO delete!!! hard code -- data:image/png;base64,
			}
			////
			switch(actionKey){
			case ACTION_GET_ARRAY: outRes = question.getQuestionId().getQuestionText() + DELIMITER // text of question or Description to picture or code									
					+ question.getCorrectAnswer() + DELIMITER // correct answer char 
					+ question.getNumberOfResponsesInThePicture() + DELIMITER // number of answers chars on image
					+ question.getLineCod();// code question text
			break;
			case ACTION_GET_FULL_ARRAY: outRes = question.getQuestionId().getQuestionText() + DELIMITER // text of question or Description to pictures									
					+ question.getCorrectAnswer() + DELIMITER // correct answer char 
					+ question.getNumberOfResponsesInThePicture() + DELIMITER// number of answers chars on image
					+ question.getId() + DELIMITER// static information
					+ question.getCategory() + DELIMITER// static information
					+ question.getLevelOfDifficulty() + DELIMITER// static information
					+ question.getNumberOfResponsesInThePicture() + DELIMITER// static information
					+ question.getLineCod();// static information
			break;
			default:System.out.println(" default switch");
			}

			List<EntityAnswersText> answers = question.getQuestionAnswersList();
			if(answers != null){
				for(EntityAnswersText tAn :answers){
					outRes += DELIMITER + tAn;	// answers on text if exist!!!
				}
			}	
			outArray[0] = outRes;// question attributes in text			
			outArray[2] = NAME_FOLDER_FOR_SAVENG_QUESTION_PICTURES + imageLink;// that static parameter for one operation!. when question is update from admin panel !!!
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
			String linkForDelete = objEntQue.getImageLink();
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
			Files.delete(Paths.get(System.getProperty("user.dir") + "\\" + NAME_FOLDER_FOR_SAVENG_QUESTION_PICTURES + linkForDelete));
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
	public boolean UpdateTextQuestionInDataBase(String questionID, String imageLink, String questionText, 
			String category, int levelOfDifficulty, List<String> answers, String correctAnswer, String codeText, String numAnswersOnPictures){
		boolean flagAction = false;
		//
		long id = (long)Integer.parseInt(questionID);
		//
		try{
			Object res = em.createQuery("SELECT c FROM EntityQuestionAttributes c WHERE c.id="+id).getSingleResult();// element question table getting by ID
			EntityQuestionAttributes elem = (EntityQuestionAttributes) res;
			elem.getQuestionId().setQuestionText(questionText);			
			elem.setCategory(category);
			elem.setCorrectAnswer(correctAnswer);
			elem.setImageLink(imageLink);
			elem.setLineCod(codeText);
			elem.setLevelOfDifficulty(levelOfDifficulty);
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
		System.out.println("category-"+category+"  levelsOfDifficulty-"+levelsOfDifficulty+"  nQuestion-"+nQuestion);// ---------------------------sysout
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
		System.out.println("res--- in  bes --"+result);
		return result;
	}

	private static List<Long> randomAttributeQuestionsId(List<Long> allAttributeQuestionsId, Long nQuestion){
		List<Long> result = new ArrayList<Long>();
		if(allAttributeQuestionsId.size() > 0){
			if(nQuestion >= allAttributeQuestionsId.size()){
				result = allAttributeQuestionsId;
			}
			else{
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
					}
					else{
						result.add(questionAttributeId);
						i++;
					}
				}				
			}
		}
		return result;
	}
	////-------------- Method for test case group AlexFoox Company return id of unique set questions ----------// END  //
}
//// ----- END Code -----

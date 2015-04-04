package tel_ran.tests.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import stubgeneratequestions.IGenerationQuestionsService;
import stubgeneratequestions.StubCreateQuestion;
import tel_ran.tests.entitys.EntityAnswersText;
import tel_ran.tests.entitys.EntityQuestion;
import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.entitys.EntityUser;
import tel_ran.tests.services.interfaces.IMaintenanceService;

public class MaintenanceService extends TestsPersistence implements IMaintenanceService {	
	////-------------- Authorization Case ----------// Begin  //
	private static boolean flAdminAuthorized = false;
	// ---------- stub method authorization ------------------//
	@Override	
	public boolean setAutorization(String userMail, String password) {
		////
		EntityUser tmpUser = em.find(EntityUser.class, userMail);
		//
		if(tmpUser != null && tmpUser.getPassword().equalsIgnoreCase(password)){			
			flAdminAuthorized = true;		
		}else{
			flAdminAuthorized = true;	 
		}
		//
		return MaintenanceService.flAdminAuthorized;	
	}
	public static boolean isAuthorized(){return flAdminAuthorized;}
	////-------------- Authorization users like Administrator DB ----------// END  //
	////-------------- Creation and Adding ONE Question into DB Case ----------// BEGIN  //
	//
	private int NUMBERofRESPONSESinThePICTURE = 4;// number of responses in the picture, for text questions default = 4
	//
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW) 
	public boolean CreateNewQuestion(String imageLink, String questionText, 
			String category, int complexityLevel, List<String> answers, 
			char correctAnswer,int questionNumber, int numberOfResponsesInThePicture) throws javax.persistence.RollbackException {
		////
		boolean flagAction = false;	
		long keyQuestion = 0;
		EntityQuestion objectQuestion;		
		////	
		try{	
			if((objectQuestion = em.find(EntityQuestion.class,(long) questionNumber)) == null){		
				objectQuestion = new EntityQuestion();	
				objectQuestion.setQuestionText(questionText);	

				em.persist(objectQuestion);// sending to database (commit) TO DO -- if Question exist bat!!! em.find(EntityQuestion.class,(long) questionNumber)) returned  null !!!
				//

				keyQuestion = objectQuestion.getId(); 
				//
				List<EntityQuestionAttributes> questionAttributes = new ArrayList<EntityQuestionAttributes>();
				EntityQuestionAttributes qattr = addQuestionAttributes(imageLink, category,  complexityLevel, correctAnswer,answers, keyQuestion,numberOfResponsesInThePicture);
				questionAttributes.add(qattr);
				//
				objectQuestion.setQuestionAttributes(questionAttributes);
				//
				em.persist(objectQuestion);
				flagAction = true;			
			}else{	
				keyQuestion = objectQuestion.getId(); 
				//
				List<EntityQuestionAttributes> questionAttributes = objectQuestion.getQuestionAttributes();
				EntityQuestionAttributes qattr = addQuestionAttributes(imageLink, category,  complexityLevel, correctAnswer,answers, keyQuestion,numberOfResponsesInThePicture);
				questionAttributes.add(qattr);
				objectQuestion.setQuestionAttributes(questionAttributes);
				em.persist(objectQuestion);
				flagAction = true;
			}
			em.clear();	
		}catch(javax.persistence.PersistenceException e){
			flagAction = false;
		}catch(org.hibernate.exception.ConstraintViolationException e){
			flagAction = false;
		}
		//
		return flagAction;
	}
	////
	private EntityQuestionAttributes addQuestionAttributes(String imageLink, String category, int complexityLevel, char correctAnswer, List<String> answers, long keyQuestion, int numberOfResponsesInThePicture) {
		EntityQuestionAttributes questionAttributesList = new EntityQuestionAttributes();
		EntityQuestion objectQuestion = em.find(EntityQuestion.class, keyQuestion);			
		////
		if(imageLink.length() > 15 && imageLink != null){
			questionAttributesList.setImageLink(imageLink);
		}
		////
		questionAttributesList.setCategory(category);
		questionAttributesList.setComplexityLevel(complexityLevel);
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
	public boolean ModuleForBuildingQuestions(String byCategory, int nQuestions) throws javax.persistence.RollbackException {
		//question text|| image link || category of question || level of complexity || correct answer char || number of question text if exist question in db witch that text || number answers on image ( A,B or A,B,C,D and ...) |
		/*Sample - String[] question = {"What Wrong witch Code:","E11842F520AE11842F520AA24589A2458992AE532883CFA45EE4.png","logical","1","E","0","2"}; ////,"a51","a52","a53","a54" ??*/
		////	question.length = 7
		boolean flagAction = false;	
		List<String> answers;		
		IGenerationQuestionsService gen = new StubCreateQuestion();
		List<String[]> listQuestions = gen.methodToCreateQuestionsByCategory(byCategory, nQuestions);
		//
		for(String[] fres :listQuestions){				
			answers = new ArrayList<String>();
			if(fres.length > 7){				
				answers.add(fres[7]);answers.add(fres[8]);answers.add(fres[9]);answers.add(fres[10]);
			}
			int numberOfResponsesInThePicture = Integer.parseInt(fres[6]);
			flagAction = CreateNewQuestion(fres[1], fres[0], fres[2], Integer.parseInt(fres[3]), answers, fres[4].charAt(0), Integer.parseInt(fres[5]), numberOfResponsesInThePicture);
		}
		return flagAction;
	}
	////-------------- Creation and Adding MANY Questions into DB from Generated Question Case ----------// END  //
	////-------------- Reading from file and Adding Questions into DB Case ----------// BEGIN  //
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW)	
	public boolean FillDataBaseFromTextResource(List<String> inputTextFromFile) throws javax.persistence.RollbackException {
		//sample for text in file question(one line!!!)
		//questionText----imageLink----category----levelOfDifficulty----answer1----answer2----answer3----answer4----correctAnswerChar----questionIndexNumber
		//		
		boolean flagAction = false;
		String imageLink = "";
		String questionText = "";
		String category = "";
		int levelOfDifficulty = 1;
		List<String> answers = null;
		char correctAnswer = ' ';
		int questionNumber = 0;
		//
		try{			
		for(String line: inputTextFromFile){ 
			System.out.println("line--"+line);
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
				correctAnswer = question_Parts[8].charAt(0);
				questionNumber = 0;				
			}else{
				//if question exist method added only new attributes for this question
				//else if question not exist method added a new question full
				questionNumber = Integer.parseInt(question_Parts[5]);	
				questionText = question_Parts[0];
				imageLink = question_Parts[1];			
				category = question_Parts[2];
				levelOfDifficulty = Integer.parseInt(question_Parts[3]);
				correctAnswer = question_Parts[4].charAt(0);						
			}
			//----------------------------------------------------------------------------------------------------------------------------// this default = 4  
			CreateNewQuestion(imageLink, questionText, category, levelOfDifficulty, answers, correctAnswer, questionNumber, NUMBERofRESPONSESinThePICTURE );
		}	
		flagAction = true;
		}catch(Exception e){
			System.out.println("catch from adding from file method BES");
		}
		//
		return flagAction;
	}
	////-------------- Reading from file and Adding Questions into DB Case ----------// END  //
	////-------------- internal method for filling in the form update issue ----------// BEGIN  //
	@Override
	public String getQuestionById(String questionID) {// method return all attributes from Question and Answer Tables in string line  
		StringBuffer  outRes = new StringBuffer();
		long id = (long)Integer.parseInt(questionID);
		//
		EntityQuestion question = em.find(EntityQuestion.class, id);
		outRes.append(question + DELIMITER);
		//
		List<EntityQuestionAttributes> attr = question.getQuestionAttributes();
		for(EntityQuestionAttributes textAttr:attr){
			outRes.append(textAttr + DELIMITER);		
			List<EntityAnswersText> answers = textAttr.getQuestionAnswersList();
			if(answers != null){
				for(EntityAnswersText tAn :answers){
					outRes.append(tAn + DELIMITER);	
				}
			}		
		}
		return outRes.toString();// return to client 
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
	public String deleteQuetionById(String questionID){
		String outMessageTextToJSP_Page = "";
		try {
			long id = Integer.parseInt(questionID);		
			EntityQuestion objEntQue = em.find(EntityQuestion.class, id);
			//
			List<EntityQuestionAttributes> liEntAttr = objEntQue.getQuestionAttributes();
			for(EntityQuestionAttributes entAttr:liEntAttr){
				//	
				List<EntityAnswersText> liEntAns = entAttr.getQuestionAnswersList();
				for(EntityAnswersText entAns:liEntAns){
					em.remove(entAns);
					em.flush();
				}
				em.remove(entAttr);
				em.flush();
			}				
			//
			em.remove(objEntQue);
			em.flush();
			outMessageTextToJSP_Page = "Object Question By ID="+questionID+". Has been Deleted";// return to client 
		} catch (Exception e) {
			outMessageTextToJSP_Page = "Error Deleting Object by ID"+questionID+". This Object Already DELETED";
		}
		return outMessageTextToJSP_Page ;// return to client 
	}
	////-------------- Method for delete question into DB ----------// END  //
	////-------------- Search Method by Category or Categories and level of difficulty ----------// BEGIN  //
	@SuppressWarnings("unchecked")		
	public List<String> SearchAllQuestionInDataBase(String category, int complexityLevel) {	
		List<String> outResult = new ArrayList<String>();
		List<EntityQuestionAttributes> result = em.createQuery(	"SELECT c FROM EntityQuestionAttributes c WHERE c.category LIKE :custName").setParameter("custName","%"+category+"%").getResultList();
		for(EntityQuestionAttributes q: result){
			// TO DO query get by category and level of difficulty 
			outResult.add(q.getQuestionId().getQuestionText() + IMaintenanceService.DELIMITER + q.toString());			
		}
		return outResult;// return to client 
	}
	////-------------- Search Method by Category or Categories and level of difficulty ----------// END  //
	////
	/////-------------- Update  ONE Question into DB Case ----------// BEGIN  //
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW)	
	public boolean UpdateTextQuestionInDataBase(String questionID, String imageLink, String questionText, 
			String category, int complexityLevel, List<String> answers, char correctAnswer){
		boolean flagAction = false;
		//
		long id = (long)Integer.parseInt(questionID);
		//
		try{
			Object res = em.createQuery("SELECT c FROM EntityQuestion c WHERE c.id="+id).getSingleResult();// element question table getting by ID
			EntityQuestion elem = (EntityQuestion) res;
			elem.setQuestionText(questionText);
			List<EntityQuestionAttributes> questionAttributes = elem.getQuestionAttributes();
			for(EntityQuestionAttributes qattr:questionAttributes){
				qattr.setCategory(category);
				qattr.setCorrectAnswer(correctAnswer);
				qattr.setImageLink(imageLink);
				qattr.setComplexityLevel(complexityLevel);
				//
				if(answers != null){
					List<EntityAnswersText> answersList = qattr.getQuestionAnswersList();	 	
					int i=0;			
					for(EntityAnswersText text:answersList){					
						text.setAnswerText(answers.get(i++));					
						em.persist(text);
					}  
				}				
			}			
			elem.setQuestionAttributes(questionAttributes);
			em.persist(elem);

			flagAction = true;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("not good in maintenance service update question");
		}
		return flagAction;// return to client 
	}	
	////-------------- Update  ONE Question into DB Case ----------// END  //
	////
	////-------------- Method for test case group AlexFoox Company return id of unique set questions ----------// BEGIN  //
	@SuppressWarnings("unchecked")	
	@Override  
	public List<Long> getUniqueSetQuestionsForTest(String category,String level,Long nQuestion){
		List<Long> outRes = new ArrayList<Long>();	
		if(nQuestion > 0){
			try{
				int levelQuestion = Integer.parseInt(level);
				int numbQuestions = nQuestion.intValue();
				String[] categories = category.split(",");
				StringBuffer[] categories1 = new StringBuffer[categories.length];
				StringBuffer condition = new StringBuffer();
				for(int i=0; i<categories.length; i++){
					String str = "%" + categories[i] + "%";
					categories1[i] = new StringBuffer(str);
					if(i < (categories.length - 1)){
						condition.append("(c.category LIKE ?" + (i+2) + ") OR "); 
					}
					else{
						condition.append("(c.category LIKE ?" + (i+2) + ")");
					}
				}
				Query query = em.createQuery("SELECT c.id FROM EntityQuestionAttributes c WHERE (c.complexityLevel=?1) AND (" + condition.toString() + ")");
				query.setParameter(1, levelQuestion);
				for(int i=0; i<categories.length; i++){
					query.setParameter((i+2), categories1[i].toString());
				}
				List<Long> res = query.getResultList();
				if(res.size() > 0){ 
					if(res.size() <= numbQuestions){
						outRes = res;
					}
					else{
						for(int i=0; i<numbQuestions; i++){
							Random rnd = new Random();
							int rand =  rnd.nextInt(res.size());
							outRes.add(res.get(rand));
						}
					}
				}
			}catch (NumberFormatException ex){
				outRes = null;
			}
		}
		return outRes;
	}
	////-------------- Method for test case group AlexFoox Company return id of unique set questions ----------// END  //
}
//// ----- END Code -----
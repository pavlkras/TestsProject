package tel_ran.tests.services;

import java.util.ArrayList;
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
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW) 
	public boolean CreateNewQuestion(String imageLink, String questionText, String category, int levelOfDifficulty, List<String> answers, char correctAnswer,int questionNumber) {
		////
		boolean flagAction = false;	
		long keyQuestion = 0;
		EntityQuestion objectQuestion;		
		////
		if((objectQuestion = em.find(EntityQuestion.class,(long) questionNumber)) == null ){
			objectQuestion = new EntityQuestion();	
			objectQuestion.setQuestionText(questionText);		
			em.persist(objectQuestion);// sending to database (commit)
			//
			keyQuestion = objectQuestion.getId(); 
			//
			System.out.println("creating new question id="+keyQuestion);//--------------
			//
			List<EntityQuestionAttributes> questionAttributes = new ArrayList<EntityQuestionAttributes>();
			EntityQuestionAttributes qattr = addQuestionAttributes(imageLink, category,  levelOfDifficulty, correctAnswer,answers, keyQuestion);
			questionAttributes.add(qattr);
			//
			objectQuestion.setQuestionAttributes(questionAttributes);
			//
			em.persist(objectQuestion);
			flagAction = true;			
		}else{	
			keyQuestion = objectQuestion.getId(); 
			//
			System.out.println("adding attributes to exist question id="+keyQuestion);	//------------	
			//
			List<EntityQuestionAttributes> questionAttributes = objectQuestion.getQuestionAttributes();
			EntityQuestionAttributes qattr = addQuestionAttributes(imageLink, category,  levelOfDifficulty, correctAnswer,answers, keyQuestion);
			questionAttributes.add(qattr);
			objectQuestion.setQuestionAttributes(questionAttributes);
			em.persist(objectQuestion);
			flagAction = true;
		}
		em.clear();	

		return flagAction;
	}
	////
	private EntityQuestionAttributes addQuestionAttributes(String imageLink, String category, int levelOfDifficulty,char correctAnswer, List<String> answers, long keyQuestion) {
		EntityQuestionAttributes questionAttributesList = new EntityQuestionAttributes();
		EntityQuestion objectQuestion = em.find(EntityQuestion.class, keyQuestion);			
		////
		if(imageLink.length() > 15 && imageLink != null){
			System.out.println("adding link");//-------------
			questionAttributesList.setImageLink(imageLink);
		}
		////
		questionAttributesList.setCategory(category);
		questionAttributesList.setLevelOfDifficulty(levelOfDifficulty);
		questionAttributesList.setCorrectAnswer(correctAnswer);
		questionAttributesList.setQuestionId(objectQuestion);	
		////
		em.persist(questionAttributesList);	

		if(answers != null){
			System.out.println("adding answers ->");//-----------
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
	////
	////-------------- Creation and Adding MANY Questions into DB from Generated Question Case ----------// BEGIN  //
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW)	
	public boolean ModuleForBuildingQuestions(String byCategory, int nQuestions) {
		boolean ff = false;
		List<String> answers;		
		List<String[]> listQuestions = getGeneratedTemplateList(byCategory, nQuestions);
		//
		for(String[] fres :listQuestions){	
			System.out.println(fres.length+" <- razmer masiva");
			answers = new ArrayList<String>();
			if(fres.length > 6){				
				answers.add(fres[6]);answers.add(fres[7]);answers.add(fres[8]);answers.add(fres[9]);
			}
			ff = CreateNewQuestion(fres[1], fres[0], fres[2], Integer.parseInt(fres[3]), answers, fres[4].charAt(0), Integer.parseInt(fres[5]));
		}
		return ff;
	}
	//--------------- stub methods -------------------///
	private List<String[]> getGeneratedTemplateList(String category,int nQuestions) {

		List<String[]> outResult = new ArrayList<String[]>();
		String[] question1 = {"Whot Wrong witch Code:","82D39ED_QuestionText1_AEA6AE8F7201706D430E824FD2F0.jpg","MATH","1","A","0","a11","a12","a13","a14"};
		String[] question2 = {"QuestionText1","E11842F_QuestionText2_520AA2458992AE532883CFA45EE4.jpg","MATH","1","B","0"};
		String[] question3 = {"QuestionText2","82D39ED_QuestionText3_AtA6AE8F7201706D430E824FD2F0.jpg","MATH","1","C","0"};
		String[] question4 = {"QuestionText3","E11842F_QuestionText4_520AA2458992AE532883CFA45EE4.jpg","MATH","1","D","0","a41","a42","a43","a44"};
		String[] question5 = {"QuestionText4","null","MATH","1","E","0","a51","a52","a53","a54"};
		outResult.add(question1);
		outResult.add(question2);
		outResult.add(question3);
		outResult.add(question4);
		outResult.add(question5);
		return outResult;
	}		
	//-------------------end stubs -------------///
	////-------------- Creation and Adding MANY Questions into DB from Generated Question Case ----------// END  //
	////	   
	////-------------- Reading from file and Adding Questions into DB Case ----------// BEGIN  //
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW)	
	public boolean FillDataBaseFromTextResource(List<String> inputTextFromFile) {
		//pattern for text in file question(one line!!!)
		//questionText----imageLink----category----levelOfDifficulti----answer1----answer2----answer3----answer4----correctAnswerChar----questionIndexNumber
		//		
		boolean flagAction = false;
		//
		for(String line: inputTextFromFile){ 
			//
			String[] question_Parts = line.split(DELIMITER); //delimiter for text, from interface IMaintenanceService
			//
			String questionText = question_Parts[0];
			String imageLink = question_Parts[1];			
			String category = question_Parts[2];
			int levelOfDifficulti = Integer.parseInt(question_Parts[3]);
			//
			List<String> answers = new ArrayList<String>();
			answers.add(question_Parts[4]);	
			answers.add(question_Parts[5]);
			answers.add(question_Parts[6]);
			answers.add(question_Parts[7]);			
			//
			char correctAnswer = question_Parts[8].charAt(0);
			int questionNumber = Integer.parseInt(question_Parts[9]);
			//
			flagAction = CreateNewQuestion(imageLink, questionText, category, levelOfDifficulti, answers, correctAnswer, questionNumber);			
		}	
		return flagAction;
	}
	////-------------- Reading from file and Adding Questions into DB Case ----------// END  //
	////
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
	////
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
	////
	////-------------- Search Method by Category or Categories and level of difficulty ----------// BEGIN  //
	@SuppressWarnings("unchecked")		
	public List<String> SearchAllQuestionInDataBase(String category, int levelOfDifficulty) {	
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
	public boolean UpdateTextQuestionInDataBase(String questionID, String imageLink, String questionText, String category, int levelOfDifficulty, List<String> answers, char correctAnswer) {
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
				qattr.setLevelOfDifficulty(levelOfDifficulty);
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
				Query query = em.createQuery("SELECT c.id FROM EntityQuestionAttributes c WHERE (c.levelOfDifficulti=?1) AND (" + condition.toString() + ")");
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
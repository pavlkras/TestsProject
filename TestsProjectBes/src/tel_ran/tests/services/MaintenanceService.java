package tel_ran.tests.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tel_ran.tests.services.interfaces.IMaintenanceService;

public class MaintenanceService extends TestsPersistence implements IMaintenanceService {
	private static boolean flAdminAuthorized = false;
	public static final String DELIMITER = "----";// delimiter for entity's and filling from file
	@Override
	public void setAutorization(boolean auth) {
		MaintenanceService.flAdminAuthorized = auth;
	}
	public static boolean isAuthorized(){
		return flAdminAuthorized;
	}
	private int j=1;// счетчик правильного ответа 
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW) //аннотация для правильной обработки транзакций между Клиентом и БД
	public boolean createQuestion(String questionText,String descriptionText, String category,	int level, List<String> answers, int trueAnswerNumber) {
		boolean flagAction = false;		
		List<EntityQuestion> res;
		try{
			res = em.createQuery(// searching  if question not exist	
					"SELECT c FROM EntityQuestion c WHERE c.questionText LIKE :custName").setParameter("custName",questionText).getResultList();
		}catch(Exception e){
			res = null;
		}
		if(res == null || res.size() == 0){
			// creating table question and setting data//
			EntityQuestion qwtemp = new EntityQuestion();			
			qwtemp.setQuestion(questionText);
			qwtemp.setDescription(descriptionText);
			qwtemp.setCategory(category);
			qwtemp.setLevel(level);
			em.persist(qwtemp);// sending to database (commit)
			/**
			 * получаем Id внесенного вопроса для привязки к ответам, заносим результат в таблицу Answer-колонку-QuestionKey
			 * Id get introduced to link the issue to the answers, enter the results in Table Answer-column-QuestionKey 
			 * */
			List<EntityAnswer> answersList = new ArrayList<EntityAnswer>();
			long keyQuestion = qwtemp.getId(); 			
			//обходим лист стрингов который пришел как параметер  List<String> answers и добавляем ответы в БД
			for (String answerText : answers) {				
				EntityAnswer ans = addAnswersList(answerText, trueAnswerNumber, keyQuestion); 
				answersList.add(ans);
				j++;// счетчик правильного ответа 
			}
			qwtemp.setAnswers(answersList);
			j = 1;
			flagAction = true;
		}
		else{
			em.clear();
		}
		return flagAction;// return to client 
	}
	

	
	
	////////////////////////////////////////////////////////////////////////////////////
	/** method for Creating Table Answer in DB 	
	 * @return */
	private EntityAnswer addAnswersList(String answer, int trueAnswerNumber, long keyQuestion) {
		// creating table answer and setting data//
		EntityAnswer temp = new EntityAnswer();
		EntityQuestion quest = em.find(EntityQuestion.class, keyQuestion);		
		temp.setAnswerText(answer);// adding text answer 
		temp.setQuestionId(quest);// setting  mapBy  
		if(trueAnswerNumber == (int)j){
			temp.setAnswer(true);//  adding boolean true if this answer  true
		}else{
			temp.setAnswer(false);//  adding boolean false if this answer not true
		}
		em.persist(temp);// sending to DB добавляем данные в БД
		return temp;
	}
	//////////////////////////////////////////////////////////////////////////////////////
	/** Метод апдейт , берет вопрос и обновляет его данными полученными от администратора CHANGE Question */
	@SuppressWarnings("unchecked")
	@Override	
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW)	
	public boolean UpdateQuestionInDataBase(String questionID,String questionText,String descriptionText,String category, int level,List<String> answers,int trueAnswerNumber) {
		boolean flagAction = false;
		// changing Question table attribute
		long id = (long)Integer.parseInt(questionID);
		Object res = em.createQuery("SELECT c FROM EntityQuestion c WHERE c.id="+id).getSingleResult();// element question table getting by ID
		EntityQuestion elem=(EntityQuestion) res;	
		elem.setQuestion(questionText);
		elem.setDescription(descriptionText);
		elem.setCategory(category);
		elem.setLevel(level);
		em.persist(elem);
		// changing table Answer, adding text 
		List<EntityAnswer> answersList = elem.getAnswers();//em.createQuery("SELECT c FROM EntityAnswer c WHERE c.questionid="+elem.getId()).getResultList();//searching in DB is question not exist
		int i=0;	
		j=1;// counter for answers   
		for(EntityAnswer text:answersList){					
			text.setAnswerText(answers.get(i++));// getting and adding text to column AnswerText 		
			if(trueAnswerNumber == (int)j++){
				text.setAnswer(true);// adding boolean  true if this answer true 
			}else{
				text.setAnswer(false);// adding boolean false if this answer not true
			}				
			em.persist(text);// добавляем данные в БД
		}			
		flagAction = true;

		return flagAction;// return to client 
	}	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/** ЗАПРОС В БД По вопросу, словам из вопроса, или букве(нескольким буквам типа  J2EE) SEARCH Question  */
	@SuppressWarnings("unchecked")
	@Override	
	public List<String> SearchQuestionInDataBase(String question, String category) {	
		List<String> outResult = new ArrayList<String>();
		List<EntityQuestion> result = em.createQuery(
				"SELECT c FROM EntityQuestion c WHERE c.questionText LIKE :custName").setParameter("custName","%"+question+"%").getResultList();// return to client result of operation
		for(EntityQuestion q: result){
			outResult.add(q.toString());
		}
		return outResult;// return to client 
	}
	///////////////////////////////internal method for filling in the form update issue/////////////////////////////////////
	///////////////////////////////внутренний метод для заполнения формы обновления вопроса/////////////////////////////////////
	@SuppressWarnings("unchecked")
	@Override
	public String getQuestionById(String questionID) {// method return all attributes from Question and Answer Tables in string line  
		StringBuffer  outRes = new StringBuffer();
		long id = (long)Integer.parseInt(questionID);
		EntityQuestion question = (EntityQuestion) em.createQuery("SELECT c FROM EntityQuestion c WHERE c.id="+id).getSingleResult();
		outRes.append(question);
		List<EntityAnswer> answers = question.getAnswers(); //em.createQuery("SELECT c FROM EntityAnswer c WHERE c.questionid="+id).getResultList();// return to client result of operation
		for(EntityAnswer tAn :answers)
			outRes.append(tAn);			
		return outRes.toString();// return to client 
	}
	//////////////////////////////////////////////method for delete question into DB/////////////////////////////////////////////
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW)	
	public String deleteQuetionById(String questionID){
		String outMessageTextToJSP_Page = "";
		try {
			long id =Integer.parseInt(questionID);			
			List<EntityAnswer> liEntAns = em.createQuery("SELECT c FROM EntityAnswer c WHERE c.questionid="+id).getResultList();
			for(EntityAnswer entAns:liEntAns){
				em.remove(entAns);
				em.flush();
			}
			Object objEntQue = em.createQuery("SELECT c FROM EntityQuestion c WHERE c.id="+id).getSingleResult();
			em.remove(objEntQue);
			em.flush();			
			return "Object Question By ID="+questionID+". Has been Deleted";// return to client 
		} catch (Exception e) {
			outMessageTextToJSP_Page = "Error Deleting Object by ID"+questionID+". This Object Already DELETED";
		}
		return outMessageTextToJSP_Page ;// return to client 
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// method for test case group AlexFoox Company return id of unique set questions 
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
				Query query = em.createQuery("SELECT c.id FROM EntityQuestion c WHERE (c.level=?1) AND (" + condition.toString() + ")");
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
							int rand =  rnd.nextInt(res.size()) + 1;
							outRes.add(res.get(rand));
						}
					}
				}
			}catch (NumberFormatException ex){
				outRes = null;
			}
		}
		return outRes;      // return to any application case client, specific id question
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW)	// работа с транзакциями
	public boolean FillDataBaseFromTextResource(List<String> inputParsedText) {//adding in database questions and answers from local file
		boolean flagAction = false;
		for(String line: inputParsedText){ 
			String[] question_Parts = line.split(DELIMITER); //delimiter for text
			Integer trueAnswerNumber = Integer.parseInt(question_Parts[8]); 
			List<String> answers = new ArrayList<String>();
			answers.add(question_Parts[4]);		answers.add(question_Parts[5]);
			answers.add(question_Parts[6]);		answers.add(question_Parts[7]);

			int level = Integer.parseInt(question_Parts[3]);
			flagAction = createQuestion(question_Parts[0], question_Parts[1], question_Parts[2], level, answers, trueAnswerNumber);			
		}	
		return flagAction;
	}

}

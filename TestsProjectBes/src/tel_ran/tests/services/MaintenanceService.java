package tel_ran.tests.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tel_ran.tests.services.interfaces.IMaintenanceService;

public class MaintenanceService extends TestsPersistence implements IMaintenanceService {
	
	private int j=1;// счетчик правильного ответа 
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW) //аннотация для правильной обработки транзакций между Клиентом и БД
	public boolean createQuestion(String questionText,String descriptionText, String category,	int level, List<String> answers, int trueAnswerNumber) {
		// creating table question and setting data//

		boolean flagAction = false;		
		List<EntityQuestion> res = em.createQuery(// searching  if question not exist
				"SELECT c FROM MaintenanceQuestion c WHERE c.questionText LIKE :custName").setParameter("custName",questionText).getResultList();
		if(res.size() == 0){								
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
			long keyQuestion = qwtemp.getId(); 			
			//обходим лист стрингов который пришел как параметер  List<String> answers и добавляем ответы в БД
			for (String answerText : answers) {				
				addAnswersList(answerText, trueAnswerNumber, keyQuestion); 
				j++;// счетчик правильного ответа 
			}
			j = 1;
			flagAction = true;
		}
		else{
			em.clear();
		}
		return flagAction;// return to client 
	}
	////////////////////////////////////////////////////////////////////////////////////
	/** method for Creating Table Answer in DB 	*/
	private void addAnswersList(String answer, int trueAnswerNumber, long keyQuestion) {// private method		

		EntityAnswer temp = new EntityAnswer();// creating table answer	
		EntityQuestion quest = em.find(EntityQuestion.class, keyQuestion);
		temp.setAnswerText(answer);// adding text answer 
		temp.setQuest(quest);// adding  keyQuestion
		if(trueAnswerNumber == (int)j){
			temp.setAnswer(true);//  adding boolean true if this answer  true
		}else{
			temp.setAnswer(false);//  adding boolean false if this answer not true
		}
		em.persist(temp);// добавляем данные в БД
	}
	//////////////////////////////////////////////////////////////////////////////////////
	/** Метод апдейт , берет вопрос и обновляет его данными полученными от администратора CHANGE Question */
	@SuppressWarnings("unchecked")
	@Override	
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW)	// работа с транзакциями //logger.log(str);
	public boolean UpdateQuestionInDataBase(String questionID,String questionText,String descriptionText,String category, int level,List<String> answers,int trueAnswerNumber) {
		boolean flagAction = false;
		// changing Question table attribute
		long id = (long)Integer.parseInt(questionID);
		List<EntityQuestion> res = em.createQuery(
				"SELECT c FROM MaintenanceQuestion c WHERE c.id LIKE :custName").setParameter("custName",id).getResultList();// element question table getting by ID
		for(EntityQuestion elem:res){	
			elem.setQuestion(questionText);
			elem.setDescription(descriptionText);
			elem.setCategory(category);
			elem.setLevel(level);
			em.persist(elem);
			// changing table Answer, adding text 
			List<EntityAnswer> answersList = em.createQuery(
					"SELECT c FROM MaintenanceAnswer c WHERE c.keyQuestion LIKE :custName").setParameter("custName",elem.getId()).getResultList();//searching in DB is question not exist
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
		}		
		return flagAction;// return to client 
	}	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/** ЗАПРОС В БД По вопросу, словам из вопроса, или букве(нескольким буквам типа  J2EE) SEARCH Question  */
	@SuppressWarnings("unchecked")
	@Override	
	public List<String> SearchQuestionInDataBase(String question, String category) {	
		List<String> outResult = new ArrayList<String>();
		List<EntityQuestion> result = em.createQuery(
				"SELECT c FROM MaintenanceQuestion c WHERE c.questionText LIKE :custName").setParameter("custName","%"+question+"%").getResultList();// return to client result of operation
		for(EntityQuestion q: result){
			outResult.add(q.toString());
		}
		return outResult;// return to client 
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@SuppressWarnings("unchecked")
	@Override
	public String getInformation(String questionKey) {// method return all attributes from Question and Answer Tables in string line  
		StringBuffer  outRes = new StringBuffer();
		long id = (long)Integer.parseInt(questionKey);
		List<EntityQuestion> question = em.createQuery(
				"SELECT c FROM MaintenanceQuestion c WHERE c.id LIKE :custName").setParameter("custName",id).getResultList();
		List<EntityAnswer> answers = em.createQuery(
				"SELECT c FROM MaintenanceAnswer c WHERE c.keyQuestion LIKE :custName").setParameter("custName",id).getResultList();	
		for(EntityQuestion q: question){
			outRes.append(q);
		}
		for(EntityAnswer an:answers){
			outRes.append(an);
		}			
		return outRes.toString();// return to client 
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// method for test case group AlexFoox Company
	@SuppressWarnings("unchecked")
	@Override  	
	public List<String> generatedTestQuestion(String category, String level) {
		List<String> outRes = new ArrayList<String>();
		long id = 0;
		List<EntityQuestion> question = em.createQuery(
				"SELECT c FROM MaintenanceQuestion c WHERE c.category LIKE :custName").setParameter("custName",category).getResultList();
		for(EntityQuestion q: question){
			if(Integer.parseInt(level) == q.getLevel()){
				String temp = q.toString();
				id = q.getId();
				temp += getAnswers(id);
				outRes.add(temp);
			}
		}
		return outRes;// return to any application case client, specific query.
	}
	@SuppressWarnings("unchecked")
	private String getAnswers(long id) {// private method 
		List<EntityAnswer> answers = em.createQuery(
				"SELECT c FROM MaintenanceAnswer c WHERE c.keyQuestion LIKE :custName").setParameter("custName",id).getResultList();	
		String outRes = "";	
		for(EntityAnswer an:answers){
			outRes += an.toString();
		}	
		return outRes;
	}
	////////////////////////!!!!! temporarily unemployed !!!!!!///////////////////////////////////////////////////
	//@Override  
	public List<String> getUniqueSetQuestionsForTest(String category,String level){
		List<String> result = null;
		return result;// return to client coming soon
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW)	// работа с транзакциями
	public boolean FillDataBaseFromTextResource(List<String> inputParsedText) {//adding in database questions and answers from local file
		boolean flagAction = false;
		for(String line: inputParsedText){ 
			String[] question_Parts = line.split(":;;:"); 
			Integer trueAnswerNumber = Integer.parseInt(question_Parts[8]); 
			List<String> answers = new ArrayList<String>();
			answers.add(question_Parts[4]);		answers.add(question_Parts[5]);
			answers.add(question_Parts[6]);		answers.add(question_Parts[7]);

			int level = Integer.parseInt(question_Parts[3]);
			flagAction = createQuestion(question_Parts[0], question_Parts[1], question_Parts[2], level, answers, trueAnswerNumber);			
		}	
		return flagAction;
	}
	@Override
	public void setAutorization(boolean arg0) {
		// TODO Auto-generated method stub
		
	}
}

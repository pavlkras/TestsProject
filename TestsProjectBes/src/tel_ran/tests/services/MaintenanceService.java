package tel_ran.tests.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tel_ran.tests.services.interfaces.IMaintenanceService;

public class MaintenanceService extends TestsPersistence implements IMaintenanceService {
	private int j=1;// счетчик  номера правильного вопроса 
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW)	// работа с транзакциями 
	public boolean createQuestion(String questionText,String descriptionText, String category,	int level, List<String> answers, int trueAnswerNumber) {
		// creating table question and setting data//

		boolean result = false;		
		List<Question> res = em.createQuery(// searching  if question not exist
				"SELECT c FROM Question c WHERE c.questionText LIKE :custName").setParameter("custName",questionText).getResultList();
		if(res.size() == 0){								
			Question qwtemp = new Question();			
			qwtemp.setQuestion(questionText);
			qwtemp.setDescription(descriptionText);
			qwtemp.setCategory(category);
			qwtemp.setLevel(level);
			em.persist(qwtemp);// sending to database (commit)

			long keyQuestion = qwtemp.getId();			
			//обходим лист стрингов который пришел как параметер  List<String> answers и добавляем ответы в БД
			for (String str : answers) {				
				addAnswersList(str, trueAnswerNumber, keyQuestion); // с помощью Этого метода // adding answer
				j++;
			}
			j = 1;

			result = true;// return to client result of operation
		}else{
			em.clear();				
		}
		return result;
	}
	////////////////////////////////////////////////////////////////////////////////////
	/** method for Creating Table Answer in DB 	 */
	private void addAnswersList(String answer, int trueAnswerNumber, long keyQuestion) {		
		Answer temp = new Answer();// creating table answer		
		temp.setAnswerText(answer);// adding text answer 
		temp.setKeyQuestion(keyQuestion);
		if(trueAnswerNumber == (int)j){
			temp.setAnswer(true);// adding boolean if true/false this answer 
		}else{
			temp.setAnswer(false);// adding boolean if true/false this answer 
		}
		em.persist(temp);// добавляем данные в БД

	}
	//////////////////////////////////////////////////////////////////////////////////////
	/** Метод апдейт , берет вопрос и обновляет его данными полученными от администратора CHANGE Question */
	@SuppressWarnings("unchecked")
	@Override	
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW)	// работа с транзакциями //logger.log(str);
	public String UpdateQuestionInDataBase(String questionID,String questionText,String descriptionText,String category, int level,List<String> answers,int trueAnswerNumber) {

		StringBuffer str = new StringBuffer();
		str.append("<p style='border:0.1em solid black;'>This text from WorkActionClass line 76 </p>");
		// changing Question table attribute
		long id = (long)Integer.parseInt(questionID);
		List<Question> res = em.createQuery(
				"SELECT c FROM Question c WHERE c.id LIKE :custName").setParameter("custName",id).getResultList();// element question table getting by ID
		List<Question> testing = em.createQuery(
				"SELECT c FROM Question c WHERE c.questionText LIKE :custName").setParameter("custName",questionText).getResultList();//searching in DB is question not exist
		if(testing.size() == 0){
			for(Question elem:res){	
				elem.setQuestion(questionText);
				elem.setDescription(descriptionText);
				elem.setCategory(category);
				elem.setLevel(level);			
				em.persist(elem);
				// changing table Answer, adding text 
				List<Answer> answersList = em.createQuery(
						"SELECT c FROM Answer c WHERE c.keyQuestion LIKE :custName").setParameter("custName",elem.getId()).getResultList();//searching in DB is question not exist

				int i=0;	
				j=1;// counter for answers ,  
				for(Answer text:answersList){					
					text.setAnswerText(answers.get(i++));// getting and adding text to column AnswerText 		
					if(trueAnswerNumber == (int)j++){
						text.setAnswer(true);// adding boolean if true/false this answer 
					}else{
						text.setAnswer(false);// adding boolean if true/false this answer 
					}				
					em.persist(text);// добавляем данные в БД
				}
				str.delete(0, str.length());
				str.append("<p>Changed Question successfully added</p>");
			}
		}else{
			str.delete(0, str.length());
			str.append("<p>ERROR !!!  This Question already exist</p>");
		}
		return str.toString();
	}	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	/** ЗАПРОС В БД По вопросу, словам из вопроса, или букве(нескольким буквам типа  J2EE) SEARCH Question  */
	@SuppressWarnings("unchecked")
	@Override	
	public String SearchQuestionInDataBase(String question, String category) {
		StringBuffer str;		
		List<Question> res = em.createQuery(
				"SELECT c FROM Question c WHERE c.questionText LIKE :custName").setParameter("custName","%"+question+"%").getResultList();// return to client result of operation			
		str = new StringBuffer();
		str.append("<table>");
		for( Question questionLine :res){	
			String line = questionLine.toString();
			String[] element = line.split(":");
			str.append("<tr><td onclick='test(value)' value='"+element[0]+"'>"+element[0]+". "+element[1]+"</td></tr>");
		}	
		str.append("</table><br>");			
		return str.toString();
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@SuppressWarnings("unchecked")
	@Override
	public String getInformation(String questionKey) {// method return all attributes from Question and Answer Tables in string line  
		StringBuffer  outRes = new StringBuffer();
		long id = (long)Integer.parseInt(questionKey);
		List<Question> question = em.createQuery(
				"SELECT c FROM Question c WHERE c.id LIKE :custName").setParameter("custName",id).getResultList();
		List<Answer> answers = em.createQuery(
				"SELECT c FROM Answer c WHERE c.keyQuestion LIKE :custName").setParameter("custName",id).getResultList();	
		for(Question q: question){
			outRes.append(q);
		}
		for(Answer an:answers){
			outRes.append(an);
		}			
		return outRes.toString();	// return to client result of operation
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@SuppressWarnings("unchecked")
	@Override  // method for test case group Alex Fox
	public List<String> GeneratedTestQuestion(String category, String level) {
		List<String> outRes = new ArrayList<String>();
		long id = 0;
		List<Question> question = em.createQuery(
				"SELECT c FROM Question c WHERE c.category LIKE :custName").setParameter("custName",category).getResultList();
		for(Question q: question){
			String temp = q.toString();
			id = q.getId();
			temp += getAnswers(id);
			outRes.add(temp);
		}
		return outRes;
	}

	@SuppressWarnings("unchecked")
	private String getAnswers(long id) {
		List<Answer> answers = em.createQuery(
				"SELECT c FROM Answer c WHERE c.keyQuestion LIKE :custName").setParameter("custName",id).getResultList();	
		String outRes = null;
		for(Answer an:answers){
			outRes += an.toString();
		}	
		return outRes;
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

}

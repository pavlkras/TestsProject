package tel_ran.tests.services;

import java.io.StringWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.Query;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import tel_ran.tests.entitys.EntityAnswersText;
import tel_ran.tests.entitys.EntityQuestion;
import tel_ran.tests.entitys.EntityQuestionAttributes;
import tel_ran.tests.entitys.EntityTest;
import tel_ran.tests.entitys.EntityUser;
import tel_ran.tests.services.interfaces.IPersonalActionsService;

@SuppressWarnings("unchecked")
public class PersonalActionsService extends TestsPersistence implements	IPersonalActionsService {
	////------ User Authorization and Registration case -----------// BEGIN //
	@Override
	public boolean IsUserExist(String userMail, String userPassword) {
		boolean actionResult = false;	
		System.out.println("userMail-"+userMail+" userPassword-"+userPassword);
		String[] authorizationTest = GetUserByMail(userMail);		
		if(authorizationTest != null && authorizationTest[PASSWORD].equals(userPassword)){			
			actionResult = true;
		}else{
			System.out.println("obj is null -");
		}
		return actionResult;
	}	

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean AddingNewUser(String[] args) {		
		boolean result = false;

		if (em.find(EntityUser.class, args[EMAIL]) == null) {
			EntityUser user = new EntityUser();
			user.setFirstName(args[FIRSTNAME]);
			user.setName(args[LASTTNAME]);
			user.setPassword(args[PASSWORD]);
			user.setEmail(args[EMAIL]);
			em.persist(user);


			result = true;
		}
		return result;
	}

	@Override
	public String[] GetUserByMail(String userMail) {		
		String[] result;
		EntityUser resUser = em.find(EntityUser.class, userMail);
		System.out.println(resUser);
		if (resUser != null) {
			result = new String[4];
			result[FIRSTNAME] = resUser.getFirstName();
			result[LASTTNAME] = resUser.getLastName();
			result[PASSWORD] = resUser.getPassword();
			result[EMAIL] = resUser.getEmail();
		} else{
			result = null;
		}

		return result;
	}
	////------- User Authorization and Registration case -------------------// END //	

	////------- Test mode Test for User case ----------------// BEGIN //
	@Override
	public List<String> getCategoriesList() {
		String query = "Select DISTINCT q.category FROM EntityQuestionAttributes q ORDER BY q.category";
		Query q = em.createQuery(query);		
		List<String> allCategories = q.getResultList();

		return allCategories;
	}

	@Override
	public List<String> getComplexityLevelList() {
		String query = "Select DISTINCT q.levelOfDifficulty FROM EntityQuestionAttributes q ORDER BY q.levelOfDifficulty";
		Query q = em.createQuery(query);
		List<String> allLevels = q.getResultList();

		return allLevels;
	}

	@Override
	public String getMaxCategoryLevelQuestions(String catName,
			String complexityLevel) {
		String query = "SELECT q FROM EntityQuestionAttributes q WHERE q.category=?1 AND q.levelOfDifficulty=?2";
		Query q = em.createQuery(query);
		q.setParameter(1, catName);
		q.setParameter(2, Integer.parseInt(complexityLevel));
		List<EntityQuestion> qlist = q.getResultList();
		String res = String.valueOf(qlist.size());
		return res;
	}

	@Override
	public String getMaxCategoryQuestions(String catName, String level) {
		String query = "SELECT q FROM EntityQuestionAttributes q WHERE q.category=?1 AND q.levelOfDifficulty=?2";
		Query q = em.createQuery(query);
		q.setParameter(1, catName);
		q.setParameter(2, Integer.parseInt(level));
		List<EntityQuestion> qlist = q.getResultList();
		String res = String.valueOf(qlist.size());
		return res;
	}

	@Override
	public String getTraineeQuestions(String category, int level, int qAmount) {
		String query = "SELECT q FROM EntityQuestionAttributes q WHERE q.category=?1 AND q.levelOfDifficulty=?2";
		Query q = em.createQuery(query);
		q.setParameter(1, category);
		q.setParameter(2, level);
		////
		List<EntityQuestionAttributes> questionAttributesList = q.getResultList();
		////		
		List<EntityQuestion> questionList = new ArrayList<EntityQuestion>();
		////
		for(EntityQuestionAttributes sqattr : questionAttributesList){
			EntityQuestion tempQuestion = sqattr.getQuestionId();
			questionList.add(tempQuestion);
		}

		// if required number of the questions is less then questionList obtain
		// then randomly choose some questions from list and fill new array till
		// it reach required size
		if (qAmount != 0 && qAmount < questionList.size()) {

			Random rand = new Random();

			List<EntityQuestion> newQuestList = new ArrayList<EntityQuestion>();
			for (int j = 0; j < qAmount; j++) {
				// selection random number of the index to be added to
				// newQuestList;
				int num = rand.nextInt(questionList.size());
				// adding question
				newQuestList.add(questionList.get(num));
				// removing this question from questionList
				questionList.remove(num);
			}
			questionList = setQuestList(newQuestList);
		}
		return loadXMLQuestions(questionList);	
	}

	private List<EntityQuestion> setQuestList(List<EntityQuestion> newQuestList) {
		List<EntityQuestion> questionList = new ArrayList<EntityQuestion>();
		for (int i = 0; i < newQuestList.size(); i++) {
			questionList.add(newQuestList.get(i));
		}
		return questionList;
	}
	private String loadXMLQuestions(List<EntityQuestion> qList) {
		String strXML = null;	
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			// root elements
			Document doc = docBuilder.newDocument();
			Element elemQuestions = doc.createElement("questions");
			doc.appendChild(elemQuestions);
			if (qList != null) {
				for (int i = 0; i < qList.size(); i++) {
					Element elemQ = doc.createElement("question");
					elemQuestions.appendChild(elemQ);
					// set attribute to staff element
					EntityQuestion question = qList.get(i);
					//
					List<EntityAnswersText> answerList = null;
					List<EntityQuestionAttributes> t2qattr = question.getQuestionAttributes();
					char correctAnswerChar = ' ';
					for(EntityQuestionAttributes tempAttr:t2qattr){
						answerList = tempAttr.getQuestionAnswersList();
						correctAnswerChar = tempAttr.getCorrectAnswer();
					}
					//
					Attr questAttr = doc.createAttribute("questionID");
					questAttr.setValue(String.valueOf(question.getId()));
					elemQ.setAttributeNode(questAttr);
					elemQ.appendChild(getElements(doc, elemQ, "questionText",question.getQuestionText()));

					for (int j = 0; j < answerList.size(); j++) {
						EntityAnswersText answer = answerList.get(j);
						Element elemA = doc.createElement("answer");
						elemQ.appendChild(elemA);
						Attr ansAttr = doc.createAttribute("answerID");
						ansAttr.setValue(question.getId() + "_" + answer.getId());
						elemA.setAttributeNode(ansAttr);
						elemA.appendChild(getElements(doc, elemA, "answerText",answer.getAnswerText()));						
						elemA.appendChild(getElements(doc, elemA,"answerStatus",String.valueOf(correctAnswerChar)));
					}
				}
			}
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer;
			transformer = tf.newTransformer();
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			strXML = writer.getBuffer().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strXML;
	}
	////
	private static Node getElements(Document doc, Element element, String name,
			String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}
	////------- Test mode Test for User case ----------------// END //

	////------- Control mode Test for Person case ----------------// BEGIN //
	@Override
	public String[] GetTestForPerson(String testId) {	
		long testID = (long)Integer.parseInt(testId);		
		EntityTest personTest = em.find(EntityTest.class, testID);		
		int peRson = personTest.getEntityPerson().getPersonId();
		String pass = personTest.getPassword();	
		String questions = personTest.getQuestion();

		String[] outResult = {peRson+"", pass, questions};
		return outResult;
	}
	////	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean SaveStartPersonTestParam(String testId, String correctAnswers, long timeStartTest) {
		boolean resAction = false;
		
		try{
			long testID = (long)Integer.parseInt(testId);		
			EntityTest personTest = em.find(EntityTest.class, testID);		
			personTest.setCorrectAnswers(correctAnswers.toCharArray());			
			personTest.setTestDate(new Date(timeStartTest));	
			int amountOfCorrectAnswers = personTest.getCorrectAnswers().length;
			personTest.setAmountOfCorrectAnswers(amountOfCorrectAnswers );
			em.persist(personTest);
			resAction = true;
		}catch(Exception e){
			//e.printStackTrace();
			System.out.println("catch save start test");
		}
		return resAction;
	}
	////
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean SaveEndPersonTestResult(String testId, String personAnswers,	String imagesLinks, long timeEndTest) {
		System.out.println("personAnswers-"+personAnswers);
		boolean resAction = false;
		try{
			long testID = (long)Integer.parseInt(testId);		
			EntityTest personTest = em.find(EntityTest.class, testID);		
			personTest.setPersonAnswers(personAnswers.toCharArray());
			personTest.setPictures(imagesLinks);		
			personTest.setTestDate(new Date(timeEndTest));				
			em.persist(personTest);
			resAction = true;
		}catch(Exception e){
			//e.printStackTrace();
			System.out.println("catch save end test");
		}
		return resAction;
	}
	////------- Control mode Test for Person case ----------------// END //
}

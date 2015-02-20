package tel_ran.tests.services;

import java.io.StringWriter;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tel_ran.tests.services.interfaces.IPersonalActionsService;

public class PersonalActionsService extends TestsPersistence implements
		IPersonalActionsService {

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean saveUserService(String[] userArgs) {
		boolean result = false;

		if (em.find(EntityUser.class, userArgs[ID]) == null) {
			EntityUser user = new EntityUser();
			user.setId(userArgs[ID]);
			user.setName(userArgs[NAME]);
			user.setPassword(userArgs[PASSWORD]);
			user.setEmail(userArgs[EMAIL]);
			em.persist(user);

			
			result = true;
		}
		return result;
	}
	
	@Override
	public String[] loadUserservice(String userId) {
		String[] result = new String[USER_FIELDS];
		EntityUser resUser = new EntityUser();
		if ((resUser = em.find(EntityUser.class, userId)) != null) {
			result[ID] = resUser.getId();
			result[NAME] = resUser.getName();
			result[PASSWORD] = resUser.getPassword();
			result[EMAIL] = resUser.getEmail();
		} else
			result = null;

		return result;
	}
	
	@Override
	public List<String> getCategoriesList() {
		String query = "Select DISTINCT q.category FROM EntityQuestion q ORDER BY q.category";
		Query q = em.createQuery(query);
		List<String> allCategories = q.getResultList();

		return allCategories;
	}

	@Override
	public List<String> getComplexityLevelList() {
		String query = "Select DISTINCT q.level FROM EntityQuestion q ORDER BY q.level";
		Query q = em.createQuery(query);
		List<String> allLevels = q.getResultList();

		return allLevels;
	}

	@Override
	public String getMaxCategoryLevelQuestions(String catName,
			String complexityLevel) {
		String query = "SELECT q FROM EntityQuestion q WHERE q.category=?1 AND q.level=?2";
		Query q = em.createQuery(query);
		q.setParameter(1, catName);
		q.setParameter(2, Integer.parseInt(complexityLevel));
		List<EntityQuestion> qlist = q.getResultList();
		String res = String.valueOf(qlist.size());
		return res;
	}

	@Override
	public String getMaxCategoryQuestions(String catName, String level) {
		String query = "SELECT q FROM EntityQuestion q WHERE q.category=?1 AND q.level=?2";
		Query q = em.createQuery(query);
		q.setParameter(1, catName);
		q.setParameter(2, Integer.parseInt(level));
		List<EntityQuestion> qlist = q.getResultList();
		String res = String.valueOf(qlist.size());
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getTraineeQuestions(String category, int level, int qAmount) {
		String query = "SELECT q FROM EntityQuestion q WHERE q.category=?1 AND q.level=?2";
		Query q = em.createQuery(query);
		q.setParameter(1, category);
		q.setParameter(2, level);

		List<EntityQuestion> questionList = q.getResultList();

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
		
		ObjectMapper mapper = new ObjectMapper();
		String text = "";
		try {
			text = mapper.writeValueAsString(questionList);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(text);
//		loadXMLQuestions(questionList);
		return text;
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

		// qList = getAnswersForQuestions(qList);

		EntityQuestion q = qList.get(0);
		List<EntityAnswer> answerL = q.getAnswers();
		
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
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
					List<EntityAnswer> answerList = question.getAnswers();

					Attr questAttr = doc.createAttribute("questionID");
					questAttr.setValue(String.valueOf(question.getId()));
					elemQ.setAttributeNode(questAttr);

					elemQ.appendChild(getElements(doc, elemQ, "questionText",
							question.getQuestion()));

					// Element qstnText = doc.createElement("questionText");
					// qstnText.appendChild(doc.createTextNode(question.getQuestion()));
					// elemQ.appendChild(qstnText);

					for (int j = 0; j < answerList.size(); j++) {
						EntityAnswer answer = answerList.get(j);

						Element elemA = doc.createElement("answer");
						elemQ.appendChild(elemA);

						Attr ansAttr = doc.createAttribute("answerID");
						ansAttr.setValue(question.getId() + "_" + answer.getId());
						elemA.setAttributeNode(ansAttr);

						elemA.appendChild(getElements(doc, elemA, "answerText",
								answer.getAnswerText()));

						elemA.appendChild(getElements(doc, elemA,
								"answerStatus",
								String.valueOf(answer.isAnswer())));
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
	
	private static Node getElements(Document doc, Element element, String name,
			String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}


	@Override
	public String loadXMLTest(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}

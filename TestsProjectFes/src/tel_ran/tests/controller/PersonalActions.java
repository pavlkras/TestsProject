package tel_ran.tests.controller;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import tel_ran.tests.model.Answer;
import tel_ran.tests.model.Question;
import tel_ran.tests.model.Test;
import tel_ran.tests.model.User;
import tel_ran.tests.services.interfaces.IPersonalActionsService;

@Controller
@Scope("session")
@RequestMapping({"/","/PersonalActions"})
public class PersonalActions {

	@Autowired
	IPersonalActionsService personalService;

	int personId;
	private String categoryName = null;
	private String level = null;
	private String chosenQuestionsQuantity = null;

	private Test mTest = null;

	private List<Question> mQstnList = null;

	private List<Answer> mAnswList = null;

	private int counter = 0;

	private List<Answer> receivedAnswers = new ArrayList<Answer>();

	//
	//	@RequestMapping({"/PersonalActions"})
	//	public String signIn(String usernamep,String passwordp){
	//		
	//		return "PersonalSignIn";
	//	}

	@RequestMapping({ "/PersonalActions" })
	public String login_page(Map<String, Object> model,
			HttpServletRequest request, Model pageModel) {
		User sessionUser = (User) request.getSession()
				.getAttribute("logedUser");
		if (sessionUser != null) {

			return "Personal_user_home";
		}
		User userForm = new User();
		model.put("userForm", userForm);

		String login = request.getParameter("login");


		return login != null ? "Personal_login_page" : "Personal_sign_up";
	}

	@RequestMapping(value = "/login_action", method = {RequestMethod.POST, RequestMethod.GET})
	public String login_action(@ModelAttribute("userForm") User user, HttpServletRequest request,
			Map<String, Object> model, Model pageModel) {
		String sign_up = request.getParameter("sign_up");
		if(sign_up != null){
			return "Personal_sign_up";
		}
		String[] getUser = personalService.loadUserservice(user.getId());
		if (getUser != null) {
			User inDB = new User(getUser);
			if (user.getPassword().equals(inDB.getPassword())) {
				pageModel.addAttribute("logedUser", user);
				return "Personal_user_home";
			} else
				return "Personal_wrong_password";
		}
		pageModel.addAttribute("logedUser", "user's not found");

		return "Personal_login_page";
	}

	@RequestMapping(value = "/signup_action", method = RequestMethod.POST)
	public String signup_action(@ModelAttribute("userForm") User user, HttpServletRequest request,
			Map<String, Object> model, Model pageModel) {
		String login = request.getParameter("login");
		if(login != null){
			return "Personal_login_page";
		}
		if (personalService.loadUserservice(user.getId()) == null) {
			String id = user.getId();
			String name = user.getName();
			String password = user.getPassword();
			String email = user.getEmail();
			String[] userArgs = { id, name, password, email };
			personalService.saveUserService(userArgs);
			pageModel.addAttribute("logedUser", user);

			return "Personal_user_home";
		}

		else if (user.getId() != null)
			return "Personal_existing_user";

		return "Personal_signup_failure";
	}
	/////////////////////////////////////////////////////
	@RequestMapping({ "/web_cam" })
	public String web_cam(Map<String, Object> model) {
		User userForm = new User();
		model.put("userForm", userForm);

		return "web_cam";
	}
	///////////////////////////////////////////////

	@RequestMapping(value = "/Personal_result_view")
	public String allCategoriesAndLevelsSelection(Model model){

		List<String> allCategories = personalService.getCategoriesList();
		List<String> allLevels = personalService.getComplexityLevelList();
		model.addAttribute("categoryNames", allCategories);
		model.addAttribute("cLevels", allLevels);
		return "Personal_result_view";
	}

	@RequestMapping({"add_questions_count"})
	public String addCategoryLevelMaxQuestionsNumber(String catName, String levelName, Model model){
		mTest = new Test();
		categoryName = catName;
		level = levelName;

		String questionsCountByCategoryLevel = personalService.getMaxCategoryLevelQuestions(categoryName, level);
		model.addAttribute("catName", categoryName);
		model.addAttribute("levelName", level);
		model.addAttribute("questionsCountByCategoryLevel", questionsCountByCategoryLevel);
		mTest.setCategoryName(categoryName);
		mTest.setLevel(Integer.parseInt(level));
		return "Personal_questions_count_view";
	}

	@RequestMapping({ "/test_preparing" })
	public String starting_test(String qnumber, HttpServletRequest request, Model pageModel) {

		//setting received number of question in Test
		mTest.setqNum(Integer.parseInt(qnumber));

		//test creation
		String xmlStr = personalService.getTraineeQuestions(mTest.getCategoryName(), mTest.getLevel(), mTest.getqNum());
		// JSONObject jsonObject = service.loadJSONTest(testId);

		getDataFromXML(xmlStr);

		pageModel.addAttribute("category", mTest.getCategoryName());
		pageModel.addAttribute("level", mTest.getLevel());
		pageModel.addAttribute("quantity", mTest.getqNum());

		pageModel.addAttribute("testId", 1);
		return "Personal_test_preparing_view";
	}

	private void getDataFromXML(String xmlStr) {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Document document = null;
		try {
			builder = factory.newDocumentBuilder();
			document = builder.parse(new InputSource(new StringReader(xmlStr)));

		} catch (Exception e) {
			e.printStackTrace();
		}

		List<Long> qstnIDsList = new ArrayList<Long>();
		List<String> answIDsList = new ArrayList<String>();
		mQstnList = new ArrayList<Question>();
		mAnswList = new ArrayList<Answer>();

		System.out.println("Root element : "
				+ document.getDocumentElement().getNodeName());

		document.getDocumentElement().normalize();
		NodeList qList = document.getElementsByTagName("question");

		for (int temp = 0; temp < qList.getLength(); temp++) {

			Question question = new Question();
			Node qNode = qList.item(temp);

			if (qNode.getNodeType() == Node.ELEMENT_NODE) {

				Element qElement = (Element) qNode;
				question.setId(Long.parseLong(qElement.getAttribute("questionID")));
				qstnIDsList.add(Long.parseLong(qElement.getAttribute("questionID")));

				question.setQuestion(qElement
						.getElementsByTagName("questionText").item(0)
						.getFirstChild().getNodeValue());

				NodeList aList = qElement.getElementsByTagName("answer");

				for (int j = 0; j < aList.getLength(); j++) {
					Answer answ = new Answer();
					Node aNode = aList.item(j);

					if (aNode.getNodeType() == Node.ELEMENT_NODE) {

						Element aElement = (Element) aNode;
						answ.setQuestionAnswer(question);

						answ.setQuestionAnswer(question);
						answ.setId(aElement.getAttribute("answerID"));
						answIDsList.add(aElement.getAttribute("answerID"));



						String ansText = aElement
								.getElementsByTagName("answerText").item(0)
								.getFirstChild().getNodeValue();
						answ.setAnswerText(ansText);

						answ.setIsAnswerRight(Boolean.valueOf(aElement
								.getElementsByTagName("answerStatus").item(0)
								.getFirstChild().getNodeValue()));
					} // end if (aNode.getNodeType() == Node.ELEMENT_NODE)

					mAnswList.add(answ);

				} // end for (int j = 0; j < aList.getLength(); j++)
			}// end if (qNode.getNodeType() == Node.ELEMENT_NODE)
			question.setAnswrIDsList(answIDsList);

			mQstnList.add(question);
			answIDsList.clear();
		}// end for (int temp = 0; temp < qList.getLength(); temp++)
		mTest.setQstnNmList(qstnIDsList);

	}

	//	@RequestMapping("/get_link/{id}")
	//	public String get_link(@PathVariable int id, Model pageModel) {
	//		String test = service.loadTestService(id)[IUserService.TEST_ID];
	//		System.out.println(test);
	//		// pageModel.addAttribute("testId", test.getTestId());
	//		counter = 0;
	//		return "choose_test";
	//	}

	@RequestMapping({ "/test_run" })
	public String test_run(HttpServletRequest request, Model pageModel) {

		String answer = request.getParameter("answer");
		Question quest = mQstnList.get(counter);
		List<Answer> answersList = getAnswersList(quest);
		if (answer != null) {
			for (Answer ans : answersList) {
				if (ans.getAnswerText().compareTo(answer) == 0) {
					receivedAnswers.add(ans);
					break;
				}
			}
			counter++;
		}
		// pageModel.addAttribute("test", test);
		if (counter >= mQstnList.size()) {
			String durTime = getTestDurationTime(mTest);

			mTest = getTestResults(receivedAnswers, mTest);

			pageModel.addAttribute("time", durTime);
			pageModel.addAttribute("resultsList", mTest.getTestResultList());
			pageModel.addAttribute("wrongAnswers",
					mTest.getWrongAnswerCounter());
			pageModel.addAttribute("rightAnswers",
					mTest.getRightAnswerCounter());
			clearTest();
			return "Personal_result_mode";
		}
		quest = mQstnList.get(counter);
		answersList = getAnswersList(quest);
		List<String> strAnswers = new ArrayList<String>();
		for (int i = 0; i < answersList.size(); i++) {
			String my_new_str = answersList.get(i).getAnswerText().replaceAll("<", "&lt;").replaceAll(">", "&gt;");

			strAnswers.add(my_new_str);

		}

		pageModel.addAttribute("testId", mTest.getTestId());
		pageModel.addAttribute("test", mTest);
		pageModel.addAttribute("question", quest.getQuestion());
		pageModel.addAttribute("answersList", strAnswers);

		mTest.setStartTimeMillis(System.currentTimeMillis());

		return "Personal_test_mode";
	}

	private void clearTest() {
		counter = 0;
		mTest.setWrongAnswerCounter(0);
		mTest.setRightAnswerCounter(0);


	}

	private List<Answer> getAnswersList(Question quest) {
		List<Answer> anslist = new ArrayList<Answer>();
		if (quest != null) {

			List<String> answersIDs = quest.getAnswrIDsList();
			for (String answID : answersIDs) {
				for (Answer ans : mAnswList) {
					if (answID.compareTo(ans.getId()) == 0) {
						anslist.add(ans);
						break;
					}
				}
			}
		}

		return anslist;
	}

	private String getTestDurationTime(Test test) {
		test.setEndTimeMillis(System.currentTimeMillis());
		test.setTotalTimeMillis(test.getEndTimeMillis()
				- test.getStartTimeMillis());

		long durationTime = test.getTotalTimeMillis();
		long milliseconds = durationTime % 1000;
		long seconds = durationTime / 1000 % 60;
		long minutes = durationTime / (60 * 1000) % 60;
		long hours = durationTime / (60 * 60 * 1000) % 24;

		return hours + " hours " + minutes + " minutes " + seconds
				+ " seconds " + milliseconds + " milliseconds";
	}

	private Test getTestResults(List<Answer> answersList, Test test) {
		int wrongAnswerCounter = 0;
		int rightAnswerCounter = 0;
		//		mTest.clearTestResultList();
		List<String> resultList = new ArrayList<String>();
		String result = "";

		for (int i = 0; i < answersList.size(); i++) {
			Answer answer = answersList.get(i);
			Question question = answer.getQuestionAnswer();

			rightAnswerCounter  = answer.getIsAnswerRight() ? rightAnswerCounter + 1 : rightAnswerCounter;
			wrongAnswerCounter = answersList.size() - rightAnswerCounter;
			result = question.getQuestion() + ": " + answer.getIsAnswerRight()
					+ (answer.getIsAnswerRight() ? " Correct" : " Incorrect");
			resultList.add(result);
		}
		test.setTestResultList(resultList);
		test.setWrongAnswerCounter(wrongAnswerCounter);
		test.setRightAnswerCounter(rightAnswerCounter);
		return test;
	}

	@RequestMapping("/result_mod")
	public String result_mode(@PathVariable int id, HttpServletRequest request,
			Model pageModel) {

		return "Personal_result_mode";
	}

}

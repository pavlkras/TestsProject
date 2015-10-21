package tel_ran.tests.services.common;

public interface ICommonData {
	static final String TESTS_RESULTS = "/all_tests_results";
	static final String TESTS_RESULTS_BY_DATES = "/tests_results_by_dates";
	static final String TESTS_RESULTS_BY_PERSON_ID = "/tests_results_by_person";
	static final String TEST_RESULT_DETAILS = "/test_details";
	static final String TEST_RESULTS_UNCHECKED_LIST = "/unchecked_questions";
	static final String TEST_QUESTION_DETAILS="/question_details"; // QUERY for the question's details (with answer of the person) for the company look
	static final String TEST_CHECK_ANSWER = "/check_answer";
	
	static final String DATE_FORMAT = "yyyy-MM-dd";
	static final String delimiter = ",";
	static final int TOKEN_VALID_IN_SECONDS = 300;
	static final String URI_GET_TEST = "/TestsProjectBes/persontest/saveprev_getnext";
	static final String HEADER_AUTORIZATION = "Authorization";
		
	static final String NO_CATEGORY1 = "null";
	

	static final String NO_QUESTION = "  ";
	
	/**
	 * number of responses in the picture, for American test question`s. Default = 4
	 */
	static final int NUMBERofRESPONSESinThePICTURE = 4;	
	static final String[] USER_CATEGORY = {IPublicStrings.COMPANY_AMERICAN_TEST, IPublicStrings.COMPANY_QUESTION};
	
	static final boolean TYPE_OF_QUESTION_USERS = true;
	static final boolean TYPE_OF_QUESTION_AUTO = false;

	// --------------------- JSON - FIELDS ------------------------------------------ //
	
	static final String JSN_QUESTION_ID = "id"; // id in EntityQuestionAttributes = long
	static final String JSN_QUESTION_INDEX = "index"; 
	static final String JSN_QUESTION_TYPE = "question_type"; // user or auto 
	static final String JSN_META_CATEGORY = "metaCategory"; // = String
	static final String JSN_CATEGORY1 = "category1"; // user's category or programming language = String
	static final String JSN_CATEGORY2 = "category2"; // used for auto-question only = String
	static final String JSN_QUESTION_TEXT = "questionText"; // common question = title of question = String	
	static final String JSN_QUESTION_DESCRIPTION = "description"; // long text with the question's body = String
	static final String JSN_ANSWER_OPTIONS = "options"; //JSONArray for answers in American Test = List	
	static final String JSN_ONE_OPTION = "option"; 
	static final String JSN_CODE_SIMPLE = "code"; // stub for programming tasks = String
	static final String JSN_CORRECT_ANSWER_CHAR = "correctAnswer"; // char of the correct answer in American Test = String
	static final String JSN_ANSWERS_NUMBER = "answerNumber"; // number of answer options for American test = int
	static final String JSN_IMAGE = "image"; // = Base64
	static final String JSN_IS_IMAGE = "is_image"; // = boolean
	static final String JSN_LIST_OF_RESULT = "results"; // array
	static final String JSN_QUESTION_SHORT_DESCRIPTION = "shortText"; // String
	static final String JSN_DIFFICULTY_LVL = "level"; //int
	static final int SHORT_DESCR_LEN = 100;
	
	// ----------------------- TEST FOR PERSON -------------------------------------------------------- //
	
	// FIELDS IN SHORT VERSION OF QUESTIONS INCLUDES:	
	static final String JSN_INTEST_QUESTION_ID = "testQuestId"; // long
	static final String JSN_INTEST_INDEX = JSN_QUESTION_INDEX; //
	static final String JSN_INTEST_QUESTION_TEXT = "text"; // String = short global question
	static final String JSN_INTEST_IMAGE = JSN_IMAGE; // Base64 
	static final String JSN_INTEST_TYPE = "type"; //int
	static final String JSN_INTEST_ONE_ANSWER_OPTION = "answerOption"; // String 
	static final String JSN_INTEST_ALL_ANSWER_OPTIONS = JSN_ANSWER_OPTIONS; // JSONArray
	static final String JSN_INTEST_CODE = JSN_CODE_SIMPLE; // String
	static final String JSN_INTEST_RESULTS_LIST = JSN_LIST_OF_RESULT; // JSONArray
	static final String JSN_INTEST_DESCRIPTION = "description"; // JSONArray String
	static final String JSN_INTEST_OPTIONS_CHARS = "answers"; //JSONArray
	static final String JSN_INTEST_STATUS = "status";
	
	//FIELDS IN THE RESPONSE FROM CLIENT IN TEST
	static final String JSN_INTEST_ANSWER = "answer"; // String	
	static final String JSN_INTEST_IF_FINISHED = "finished"; // boolean
	
	//TYPES FOR DISPLAY QUESTIONS
	static final int QUESTION_TYPE_ALL_IN_IMAGE = 1; //no descriptions and answers options
	static final int QUESTION_TYPE_CODE = 2; //no image; there're description and field with stub
	static final int QUESTION_TYPE_AMERICAN_TEST = 3; //can be description, image and answers options
	static final int QUESTION_TYPE_OPEN = 4; //only description and image and text field
	
		
	// -------------------------- TEST RESULTS FOR COMPANY ------------------------------ //
	
	static final String JSN_TEST_IS_CHECKED = "checked"; // boolean = if the test was checked by the company
	static final String JSN_TEST_CORRECT_ANSWERS = "amountOfCorrectAnswers"; // int - number of correct answers
		
	static final String JSN_TEST_ID = "id"; // id of test = long
	static final String JSN_TEST_QUESTION_NUMBER = "amountOfQuestions"; // number of question in the test = int
	static final String JSN_TEST_IS_PASSED = "passed"; // if the test is passed = boolean
	static final String JSN_TEST_PERSON_SURNAME = "pers_surname"; // surname of the person = String
	static final String JSN_TEST_PERSON_NAME = "pers_name"; // name of the person = String
	static final String JSN_TEST_PERSON_ID = "pers_id"; // = int
	static final String JSN_TEST_PERCENT_OF_CORRECT = "persentOfRightAnswers"; // String 
	
	
	// ------------------------- TEST DETAILS FOR COMPANY ------------------------------- //
	
	static final String JSN_TESTDETAILS_DURATION = "duration"; // String
	static final String JSN_TESTDETAILS_QUESTIONS_NUMBER = JSN_TEST_QUESTION_NUMBER; // int - number of all questions in the test
	static final String JSN_TESTDETAILS_CORRECT_QUESTIONS_NUMBER = "amountOfCorrectAnswers"; // int
	static final String JSN_TESTDETAILS_UNCHECKED_QUESTIONS_NUMBER = "unchecked"; //int
	static final String JSN_TESTDETAILS_INCORRECT_ANSWERS_NUMBER = "incorret";
	static final String JSN_TESTDETAILS_PHOTO = "pictures"; //String = array of JSON with base64 pictures
	static final String JSN_TESTDETAILS_LIST_OF_QUESTIONS = "questions"; // String = array of JSON with details
	
	//details in list of questions
	static final String JSN_TESTDETAILS_QUESTION_ID = "questionId"; // long - id for EntityTestQuestion 
	static final String JSN_TESTDETAILS_QUESTION_METACATEGORY = "metaCategory"; // String
	static final String JSN_TESTDETAILS_QUESTION_CATEGORY1 = "category"; // String
	static final String JSN_TESTDETAILS_QUESTION_STATUS_NUM = "statusNumber"; // int for the array IPublicStrings.QUESTION_STATUS[]
	static final String JSN_TESTDETAILS_QUESTION_STATUS_STR = "statusString"; // String
	static final String JSN_TESTDETAILS_QUESTION_INDEX = "index"; // int
	
	// --------------------------- QUESTIONS DETAILS FOR COMPANY ---------------------------- //
	
	static final String JSN_QUESTDET_QUESTION_ID = "questionId"; // long = id for EntityTestQuestion 
	static final String JSN_QUESTDET_METACATEGORY = "metaCategory"; // String = metaCategory of the question
	static final String JSN_QUESTDET_CATEGORY1 = "category"; // String = category of the question
	static final String JSN_QUESTDET_STATUS_NUM = "statusNumber"; // int for the array IPublicStrings.QUESTION_STATUS[]
	static final String JSN_QUESTDET_STATUS_STR = "statusString"; //String = "correct", "incorrect"...
	static final String JSN_QUESTDET_INDEX = "index"; // int = index of the question in the given test
	static final String JSN_QUESTDET_TEXT = "text"; //String = short question(title)
	static final String JSN_QUESTDET_IMAGE = "image"; //String=base64 
	static final String JSN_QUESTDET_TYPE = "type"; // int - type to display question. See QUESTION_TYPE_... in this file
	static final String JSN_QUESTDET_ANSWER_OPTIONS_LIST = "options"; // JSONArray. Contains "answerOption" and "letter"
	static final String JSN_QUESTDET_ANSWER_OPTION = "answerOption"; // String - one option of the answer (American Test)
	static final String JSN_QUESTDET_ANSWER_OPTION_LETTER = "letter"; // String = number of the option (A, B, C or D)
	static final String JSN_QUESTDET_CODE_STUB = "code"; //JSONArray of String. One String = one line
	static final String JSN_QUESTDET_DESCRIPTION = "description"; //JSONArray of String. One String = one line
	static final String JSN_QUESTDET_ANSWERS_LETTERS = "letters"; //JSONArray of String for American Question (A, B, C, D)	
	static final String JSN_QUESTDET_CORRECT_ANSWER = "correctAnswer"; //String
	
	//ANSWER
	static final String JSN_QUESTDET_ANSWER = "answer"; //JSONArray of String. It may contain one String or some String (for code and other many-lines answers)
	
	//GRADE (MARK)	
	static final String JSN_QUESTDET_GRADE_TYPE = "gradeType"; //int = see GRAFE_TYPE_... below
	static final String JSN_QUESTDET_GRADE_OPTIONS = "gradeOptions"; //JSONArray of String for 0 and 1 types (see IPublicStrings.GRADE_OPTIONS)
	
	static final int GRADE_TYPE_CORRECT = 0; // correct OR incorrect
	static final int GRADE_TYPE_5BALLS = 1; // 1 - 2 - 3 - 4 - 5
	static final int GRADE_TYPE_PERCENT = 2; // from 0% to 100%	
	
	// -------------------- CHECK QUESTION --------------------------------------------- //
	static final String JSN_CHECK_MARK = "mark"; //String = mark
	static final String JSN_CHECK_ID = "id"; //long = if for EntityTestQuestions
	
	
	// -------------------- JSN - FIELDS ---------------------------------------------- //
	
	
	static final String MAP_ACCOUNT_QUESTION_NUMBER = "question_num"; // only for company and maintenance
	static final String MAP_ACCOUNT_TESTS_NUM = "tests_num"; // only for company
//	static final String MAP_ACCOUNT_EMAIL = "email"; // only for users
//	static final String MAP_ACCOUNT_FIRST_NAME = "first_name"; // only for users
//	static final String MAP_ACCOUNT_LAST_NAME = "last_name"; // only for users
	
	// ------------------- STATUS FOR QUESTIONS IN THE TEST ---------------------------- //
	
	static final int STATUS_CORRECT = 3;
	static final int STATUS_INCORRECT = 2;
	static final int STATUS_UNCHECKED = 1;
	static final int STATUS_NO_ANSWER = 0;
	
}

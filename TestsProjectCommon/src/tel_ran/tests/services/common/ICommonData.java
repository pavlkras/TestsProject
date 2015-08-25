package tel_ran.tests.services.common;

public interface ICommonData {
	static final String TESTS_RESULTS = "/all_tests_results";
	static final String TESTS_RESULTS_BY_DATES = "/tests_results_by_dates";
	static final String TESTS_RESULTS_BY_PERSON_ID = "/tests_results_by_person";
	static final String TEST_RESULT_DETAILS = "/test_details";
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
	static final String JSN_ANSWER_OPTIONS = "answers"; //JSONArray for answers in American Test = List	
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
	static final String JSN_INTEST_DESCRIPTION = "description"; // String
	static final String JSN_INTEST_OPTIONS_CHARS = "options"; //JSONArray
	static final String JSN_INTEST_STATUS = "status";
	
	//FIELDS IN THE RESPONSE FROM CLIENT IN TEST
	static final String JSN_INTEST_ANSWER = "answer"; // String
	static final String JSN_INTEST_CHAR = "char"; //String
	
	//TYPES FOR SHOWING QUESTIONS
	static final int QUESTION_TYPE_ALL_IN_IMAGE = 1; //no descriptions and answers options
	static final int QUESTION_TYPE_CODE = 2; //no image; there're description and field with stub
	static final int QUESTION_TYPE_AMERICAN_TEST = 3; //can be description, image and answers options
	static final int QUESTION_TYPE_OPEN = 4; //only description and image and text field
	
		
	// -------------------------- TEST RESULTS FOR COMPANY ------------------------------ //
	
	static final String JSN_TEST_IS_CHECKED = "checked"; // boolean = if the test was checked by the company
	static final String JSN_TEST_CORRECT_ANSWERS = "correct"; // int - number of correct answers
	static final String JSN_TEST_INCORRECT_ANSWERS = "incorrect"; //int - number of incorrec answers
	static final String JSN_TEST_UNANSWERED_ANSWERS = "unanswered"; //int
	static final String JSN_TEST_UNCHECKED_ANSWERS = "unchecked"; //int
	static final String JSN_TEST_LIST_ANSWERS_TO_CHECK = "toCheck"; //array of long id
	static final String JSN_TEST_QUESTION_ID = "questionId"; //long - id for EntityTestQuestion to check
	
	static final String JSN_TEST_ID = "id"; // id of test = long
	static final String JSN_TEST_QUESTION_NUMBER = "question_num"; // number of question in the test = int
	static final String JSN_TEST_IS_PASSED = "passed"; // if the test is passed = boolean
	static final String JSN_TEST_PERSON_SURNAME = "pers_surname"; // surname of the person = String
	static final String JSN_TEST_PERSON_NAME = "pers_name"; // name of the person = String
	static final String JSN_TEST_PERSON_ID = "pers_id"; // = int
	
	
	
	// -------------------- JSN - FIELDS ---------------------------------------------- //
	
	static final String MAP_ACCOUNT_NAME = "name";
	static final String MAP_ACCOUNT_WEB = "website"; // only for compamy
	static final String MAP_ACCOUNT_QUESTION_NUMBER = "question_num"; // only for company and maintenance
	static final String MAP_ACCOUNT_TESTS_NUM = "tests_num"; // only for company
	static final String MAP_ACCOUNT_EMAIL = "email"; // only for users
	static final String MAP_ACCOUNT_FIRST_NAME = "first_name"; // only for users
	static final String MAP_ACCOUNT_LAST_NAME = "last_name"; // only for users
	
	// ------------------- STATUS FOR QUESTIONS IN THE TEST ---------------------------- //
	
	static final int STATUS_CORRECT = 3;
	static final int STATUS_INCORRECT = 2;
	static final int STATUS_UNCHECKED = 1;
	static final int STATUS_NO_ANSWER = 0;
	
}

package tel_ran.tests.services.common;

public interface ICommonData {
	static final String TESTS_RESULTS = "/all_tests_results";
	static final String TESTS_RESULTS_BY_DATES = "/tests_results_by_dates";
	static final String TESTS_RESULTS_BY_PERSON_ID = "/tests_results_by_person";
	static final String TEST_RESULT_DETAILS = "/test_details";
	static final String DATE_FORMAT = "yyyy-MM-dd";
	static final String delimiter = ",";
	static final int TOKEN_VALID_IN_SECONDS = 300;
		
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
	
	static final String JSN_QUESTION_ID = "id"; // id in EntityQuestionAttributes
	static final String JSN_QUESTION_TYPE = "question_type"; // user or auto
	static final String JSN_META_CATEGORY = "metaCategory";
	static final String JSN_CATEGORY1 = "category1"; // user's category or programming language
	static final String JSN_CATEGORY2 = "category2"; // used for auto-question only
	static final String JSN_QUESTION_TEXT = "questionText"; // common question = title of question
	static final String JSN_QUESTION_DESCRIPTION = "description"; // long text with the question's body
	static final String JSN_ANSWER_OPTIONS = "answers"; //JSONArray for answers in American Test
	static final String JSN_ONE_OPTION = "option"; // one option in answers
	static final String JSN_CODE_SIMPLE = "code"; // stub for programming tasks
	static final String JSN_CORRECT_ANSWER_CHAR = "correctAnswer"; // char of the correct answer in American Test
	static final String JSN_ANSWERS_NUMBER = "answerNumber"; // number of answer options for American test 
	static final String JSN_IMAGE = "image";
	
	// -------------------- MAP - FIELDS ---------------------------------------------- //
	
	static final String MAP_ACCOUNT_NAME = "name";
	static final String MAP_ACCOUNT_WEB = "website"; // only for compamy
	static final String MAP_ACCOUNT_QUESTION_NUMBER = "question_num"; // only for company and maintenance
	static final String MAP_ACCOUNT_TESTS_NUM = "tests_num"; // only for company
	static final String MAP_ACCOUNT_EMAIL = "email"; // only for users
	static final String MAP_ACCOUNT_FIRST_NAME = "first_name"; // only for users
	static final String MAP_ACCOUNT_LAST_NAME = "last_name"; // only for users
	
	
}

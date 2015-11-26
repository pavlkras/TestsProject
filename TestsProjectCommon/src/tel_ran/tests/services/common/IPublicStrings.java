package tel_ran.tests.services.common;

/**
 * Interface with String constants that can be shown to users (Company, Person, User, etc)
 */
public final class IPublicStrings {
	
	//-------------- USER'S (COMPANY'S) META CATEGORIES ------------------------------------- //
	
	/**
	 * DB Name of User's meta-category "American Test" (1 question, some answer options).
	 * It's used in DB.
	 * Warning: Don't change it if you aren't going to drop the Data Base  
	 */
	public static final String COMPANY_AMERICAN_TEST = "American Test";
	
	/**
	 * DB Name of User's meta-category "Open Question" (1 question)
	 * It's used in DB
	 * Warning: Don't change it if you aren't going to drop the Data Base  
	 */
	public static final String COMPANY_QUESTION = "Open Question";
	
	/**
	 * General question (Questions Text) for MetaCategory "American Test"
	 * Attention: If you change it the general questions in old created question-items won't be changed. But
	 * it won't affect the program in general
	 */
	public static final String COMPANY_AMERICAN_TEST_QUESTION = "Select the most correct answer from the following options";
	
	/**
	 * General question (Questions Text) for MetaCategory "Open Question"
	 * Attention: If you change it the general questions in old created question-items won't be changed. But
	 * it won't affect the program in general
	 */
	public static final String COMPANY_QUESTION_QUESTION = "Type your answer in the textbox bellow";
	


	
	//------------- ERRORS MESSAGES ------------------------------------------------------ //
	

	public static final int TEST_SUCCESS = 0;
	public static final int TEST_NOT_ENOUGH_QUESTIONS = 1;
	public static final int TEST_INVALID_EMAIL = 2;
	public static final int TEST_NOT_ENOUGH_INFO = 3;
	public static final int TEST_SOME_TROUBLE = 4;
	public static final int TEST_NOT_SENDED = 5;
	public static final int TEST_NOT_CORRECT_PERSON_INFO = 6;
	public static final int TEST_NO_RIGHT_TO_SEND = 7;
	
	/**
	 * Messages about errors that can be during test creation
	 */
	public static final String[] CREATE_TEST_ERROR;
	
	static {
		CREATE_TEST_ERROR = new String[8];
		CREATE_TEST_ERROR[TEST_SUCCESS] = "The test was successfully created and sended by e-mail"; // 0
		CREATE_TEST_ERROR[TEST_NOT_ENOUGH_QUESTIONS] = "Not enough questions in the database to generate the test." 
				+ "Please fill in the database or create one another test with fewer number of questions";
		CREATE_TEST_ERROR[TEST_INVALID_EMAIL] = "Invalid value in the E-mail box";  // - 2
		CREATE_TEST_ERROR[TEST_NOT_ENOUGH_INFO] = "Not enough data to generate the text./n For category * should be specified sub-category"; // - 3
		CREATE_TEST_ERROR[TEST_SOME_TROUBLE] = "The test was not created. There're some problem. Please contact your support"; // - 4
		CREATE_TEST_ERROR[TEST_NOT_SENDED] = "The message wasn't sended"; // - 5
		CREATE_TEST_ERROR[TEST_NOT_CORRECT_PERSON_INFO] = "The information about the Person is not correct. The test was not created"; //6
		CREATE_TEST_ERROR[TEST_NO_RIGHT_TO_SEND] = "You are not allowed to send this test"; // 7
	}	
	
	
	
	// ------------- USER'S INFORMATION FIELDS AND TEXTS ------------------------------------ //
	
	public static final String USR_MAINTENANCE = "Administrator";
	
	// ------------- LETTERS FOR ANSWER OPTIONS IN USER AMERICAN TESTS ----------------------- //
	
	public static final String[] LETTERS = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

	// ------------- STATUS OF QUESTIONS TO SHOW --------------------------------------------- //
	
	public static final String UNCHECKED_QUESTION = "unchecked";
	public static final String CORRECT_QUESTION = "correct";
	public static final String INCORRECT_QUESTION = "incorrect";
	public static final String[] QUESTION_STATUS = {"unanswered", UNCHECKED_QUESTION, INCORRECT_QUESTION, CORRECT_QUESTION};
	
	
	// ------------- GRADE OPTIONS ------------------------------------------------------------ //
	
	public static final String[][] GRADE_OPTIONS = {
												{INCORRECT_QUESTION, CORRECT_QUESTION}, // 0 - GRADE_OPTIONS_CORRECT
												{"1", "2", "3", "4", "5"}, // 1 -  5-BALLS
												{} //2 - PERCENT
											};
	
		
}

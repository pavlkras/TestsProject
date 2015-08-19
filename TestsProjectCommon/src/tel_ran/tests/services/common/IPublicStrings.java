package tel_ran.tests.services.common;

/**
 * Interface with String constants that can be shown to users (Company, Person, User, etc)
 */
public interface IPublicStrings {
	
	//-------------- USER'S (COMPANY'S) META CATEGORIES ------------------------------------- //
	
	/**
	 * DB Name of User's meta-category "American Test" (1 question, some answer options).
	 * It's used in DB.
	 * Warning: Don't change it if you aren't going to drop the Data Base  
	 */
	static final String COMPANY_AMERICAN_TEST = "American Test";
	
	/**
	 * DB Name of User's meta-category "Open Question" (1 question)
	 * It's used in DB
	 * Warning: Don't change it if you aren't going to drop the Data Base  
	 */
	static final String COMPANY_QUESTION = "Open Question";
	
	/**
	 * General question (Questions Text) for MetaCategory "American Test"
	 * Attention: If you change it the general questions in old created question-items won't be changed. But
	 * it won't affect the program in general
	 */
	static final String COMPANY_AMERICAN_TEST_QUESTION = "Select the most correct answer from the following options";
	
	/**
	 * General question (Questions Text) for MetaCategory "Open Question"
	 * Attention: If you change it the general questions in old created question-items won't be changed. But
	 * it won't affect the program in general
	 */
	static final String COMPANY_QUESTION_QUESTION = "Type your answer in the textbox bellow";
	


	
	//------------- ERRORS MESSAGES ------------------------------------------------------ //
	
	/**
	 * Messages about errors that can be during test creation
	 */
	static final String[] CREATE_TEST_ERROR = {"The test was successfully created and sended by e-mail", // - 0
		"Not enough questions in the database to generate the test." 
		+ "Please fill in the database or create one another test with fewer number of questions", // - 1
		"Invalid value in the E-mail box",  // - 2 
		"Not enough data to generate the text./n For category * should be specified sub-category", // - 3
		"The test was not created. There're some problem. Please contact your support", // - 4
		"The message wasn't sended"}; // - 5

	
	// ------------- USER'S INFORMATION FIELDS AND TEXTS ------------------------------------ //
	
	static final String USR_MAINTENANCE = "Administrator";
	

	
}

package tel_ran.tests.services.common;

/**
 * Interface with String constants that can be shown to users (Company, Person, User, etc)
 */
public interface IPublicStrings {
	
	static final String COMPANY_AMERICAN_TEST_QUESTION = "Select the most correct answer from the following options";
	static final String COMPANY_QUESTION_QUESTION = "Type your answer in the textbox bellow";
	
	static final String[] CREATE_TEST_ERROR = {"The test was successfully created and sended by e-mail", // - 0
		"Not enough questions in the database to generate the test." 
		+ "Please fill in the database or create one another test with fewer number of questions", // - 1
		"Invalid value in the E-mail box",  // - 2 
		"Not enough data to generate the text./n For category * should be specified sub-category", // - 3
		"The test was not created. There're some problem. Please contact your support", // - 4
		"The message wasn't sended"}; // - 5

	static final String COMPANY_AMERICAN_TEST = "American Test";
	static final String COMPANY_QUESTION = "Open Question";

}

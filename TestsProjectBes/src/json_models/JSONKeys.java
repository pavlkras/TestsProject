package json_models;

public final class JSONKeys {
	
	public static final String DATE_TEMPLATE = "dd/MM/yyyy";
	
	public static final String AUTO_EMAIL = "email";
	public static final String AUTO_PASSWORD = "password";	
	public static final String AUTO_TOKEN = "token";
	public static final String AUTO_ADMIN = "admin";
	public static final String AUTO_ROLENUMBER = "roleNumber";
	public static final String AUTO_EMPL_NUMBER = "empNumber";	
	public static final String AUTO_WEBSITE = "web";	
	public static final String AUTO_SPECIALIZATION = "spec";	
	public static final String AUTO_ADDRESS = "address";	
	public static final String AUTO_BIRTHDATE = "birthDate";	
	public static final String AUTO_FIRSTNAME = "firstName";	
	public static final String AUTO_LASTNAME = "lastName";	
	public static final String AUTO_NICKNAME = "nick";	
	public static final String AUTO_PASSPORTID = "passport";	
	public static final String AUTO_COMPANY_NAME = "company";	
		
	public static final String ERROR = "error";
	public static final String ERROR_CODE = "code";
	public static final String RESPONSE_TEXT = "response";
	public static final String RESPONSE_CODE = ERROR_CODE;
	
	public static final String SIGNUP_USER_EXIST = "userExist";
	public static final String SIGNUP_SUCCESS = "success";
	
	// COMMON
	
	static final String ARRAY_OF_RESULT = "results"; // array
	
	
	// QUESTIONS
	
	public static final String QUESTION_ID = "id"; // id in EntityQuestionAttributes = long
	public static final String QUESTION_INDEX = "index"; 
	public static final String QUESTION_TYPE = "question_type"; // open or american (only for custom!) 
	public static final String QUESTION_META_CATEGORY = "metaCategory"; // = String
	public static final String QUESTION_CATEGORY1 = "category1"; // user's category or programming language = String
	public static final String QUESTION_CATEGORY2 = "category2"; // used for auto-question only = String
	public static final String QUESTION_TITLE = "questionText"; // common question = title of question = String	
	public static final String QUESTION_DESCRIPTION = "description"; // long text with the question's body = String
	public static final String QUESTION_ANSWER_OPTIONS = "options"; //JSONArray for answers in American Test = List	
	public static final String QUESTION_ONE_OPTION = "option"; 
	public static final String QUESTION_CODE_SIMPLE = "code"; // stub for programming tasks = String
	public static final String QUESTION_CORRECT_ANSWER_CHAR = "correctAnswer"; // char of the correct answer in American Test = String
	public static final String QUESTION_ANSWERS_NUMBER = "answerNumber"; // number of answer options for American test = int
	public static final String QUESTION_IMAGE = "image"; // = Base64
	public static final String QUESTION_HAS_IMAGE = "is_image"; // = boolean	
	public static final String QUESTION_SHORT_DESCRIPTION = "shortText"; // String
	public static final String QUESTION_DIFFICULTY_LVL = "level"; //int
	
	// CATEGORIES LISTS
	
	public static final String CATEGORY_ID = "cat_id"; // int
	public static final String CATEGORY_PARENT = "cat_parent"; // String - name of MetaCategory or Category1
	public static final String CATEGORY_CHILDREN = "cat_children"; //String - name of JSONArray
	public static final String CATEGORY_CHILD = "cat_child"; //String - name of sub-category
	public static final String CATEGORY_MC = "cat_mc"; //String - meta category for custom questions (=type of question)
	public static final String CATEGORY_MC_LIST = "cat_mc_array"; //JSONArray with mc

	// PERSON INFO
	
	public static final String PERSON_ID = "per_id"; // id in EntityPerson = long
	public static final String PERSON_MAIL = "per_mail"; // e-mail of Person = String
	public static final String PERSON_FNAME = "per_fname"; // name of Person = String
	public static final String PERSON_LNAME = "per_lname"; // lastname of Person = String
	public static final String PERSON_PASSPORT = "per_passport"; // number pf person's passport = String
	
	
	// TEST TEMPLATE
	
	public static final String TEMPLATE_ID = "template_id"; //id in EntityTestTemmplate = int
	public static final String TEMPLATE_NAME = "template_name"; //name of Template = String
	public static final String TEMPLATE_SAVE = "template_save"; //true if the template should be saved
	public static final String TEMPLATE_QUESTIONS = "template_questions"; //array of id of questions
	public static final String TEMPLATE_QUESTION_ID = "template_quest_id"; //id of question
	public static final String TEMPLATE_CATEGORIES = "template_categories"; //array of categories with params
	public static final String TEMPLATE_META_CATEGORY = QUESTION_META_CATEGORY;
	public static final String TEMPLATE_CATEGORY1 = QUESTION_CATEGORY1;
	public static final String TEMPLATE_CATEGORY2 = QUESTION_CATEGORY2;
	public static final String TEMPLATE_DIFFICULTY = QUESTION_DIFFICULTY_LVL;
	public static final String TEMPLATE_QUANTITY = "quantity";  
	public static final String TEMPLATE_SOURCE = "type";
	public static final String TEMPLATE_SOURCE_CUSTOM = "Custom";
	public static final String TEMPLATE_TYPE_OF_QUESTION = "typeQuestion"; //open question OR american test
	
	//TEST
	
	public static final String TEST_ID = "test_id"; //id in EntityTest = long
	public static final String TEST_PASSWORD = "test_password"; // String
	public static final String TEST_LINK_TO_SEND = "test_link"; // String
	public static final String TEST_FOR_PERSONS = "test_persons"; // String
	public static final String TEST_WAS_SENT= "test_is_sent"; //boolean
	public static final String TEST_KEY = "test_key"; //String

	
	//RESULTS OF TESTS
	
	public static final String RESULTS_CATEGORY_TYPE = "type"; //String = auto OR custom
	public static final String RESULTS_CATEGORY_ID = "category_id"; // int
	public static final String RESULTS_CATEGORY_NUM_QUESTIONS = "num_questions"; //int 
	public static final String RESULTS_CATEGORY_NUM_ANSWERS = "num_answers"; //int
	public static final String RESULTS_CATEGORY_NUM_CORRECT_ANSWERS = "correct_answers"; //int
	public static final String RESULTS_CATEGORY_NUM_UNCHECKED_ANSWERS = "unchecked_answers"; //int
	public static final String RESULTS_TEST_ID = "test_id"; //long
	public static final String RESULTS_TEST_NUM_QUESTIONS = "total_num_questions"; //int
	public static final String RESULTS_TEST_NUM_ANSWERS = "total_num_answers"; //int
	public static final String RESULTS_TEST_TOTAL_RESULT = "total_result"; //float
	public static final String RESULTS_TEST_MANUAL_CHECK_PENDING = "manual_check_pending"; //boolean
	public static final String RESULTS_BY_CATEGORY = "results_by_categories"; //JSONArray
	public static final String RESULTS_TEST_DATA = "common_test_data"; // JSONObject
	public static final String RESULTS_PERSON_DATA = "person_data"; //JSONObject
	
	public static final String PERSON_NAME = "name"; //String
	public static final String PERSON_SURNAME = "surname"; //String
	public static final String PERSON_id = "person_id"; //long
	
}

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
	
	public static final String SIGNUP_USER_EXIST = "userExist";
	public static final String SIGNUP_SUCCESS = "success";
	
	// COMMON
	
	static final String ARRAY_OF_RESULT = "results"; // array
	
	
	// QUESTIONS
	
	public static final String QUESTION_ID = "id"; // id in EntityQuestionAttributes = long
	public static final String QUESTION_INDEX = "index"; 
	public static final String QUESTION_TYPE = "question_type"; // user or auto 
	public static final String QUESTION_META_CATEGORY = "metaCategory"; // = String
	public static final String QUESTION_CATEGORY1 = "category1"; // user's category or programming language = String
	public static final String QUESTION_CATEGORY2 = "category2"; // used for auto-question only = String
	public static final String QUESTION_TEXT = "questionText"; // common question = title of question = String	
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
	
	public static final String CATEGORY_PARENT = "cat_parent"; // String - name of MetaCategory or Category1
	public static final String CATEGORY_CHILDREN = "cat_children"; //String - name of JSONArray
	public static final String CATEGORY_CHILD = "cat_child"; //String - name of sub-category

}

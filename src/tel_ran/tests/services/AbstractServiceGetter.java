package tel_ran.tests.services;


import tel_ran.tests.services.utils.SpringApplicationContext;
import tel_ran.tests.token_cipher.TokenProcessor;
import tel_ran.tests.token_cipher.User;
import tel_ran.tests.utils.errors.AccessException;


public class AbstractServiceGetter {
	
	public static final String BEAN_QUESTIONS_SERVICE = "questionsService";
	public static final String BEAN_TEMPLATE_SERVICE = "templateService";
	public static final String BEAN_TEST_TEMPLATE_SERVICE = "testTemplateService";
	public static final String BEAN_CATEGORIES_SERVICE = "categoriesService";
	public static final String BEAN_AUTO_CATEGORIES = "autoCatService";
	public static final String BEAN_CUSTOM_CATEGORIES = "customCatService";
	public static final String BEAN_TEST_SERVICE = "testService";
	public static final String BEAN_TEST_RESULT_SERVICE = "testResultService";
	public static final String BEAN_AUTHORIZATION_SERVICE = "authorization";
	public static final String BEAN_TEST_RESULTS_BY_TEMPLATE = "testResultTemplate";
	public static final String BEAN_TEST_RESULTS = "testResults";
	
	private static TokenProcessor tokenProcessor;
	
	static {
		tokenProcessor = (TokenProcessor) SpringApplicationContext.getBean("tokenProc");
	}
		
	
	public static AbstractService getService(String beanName) {
		AbstractService result = getServiceByBeanName(beanName);
		return result;
	}
	
	public static AbstractService getService(String token, String beanName) throws AccessException {
		User user = encodeToken(token);
		
		
		if(user!=null && user.isAutorized()) {
			AbstractService result = getServiceByBeanName(beanName);
			
			intiateService(result, user);
			
			return result;
		} else {
			throw new AccessException();
		}
	}
	
	public static AbstractService getService(User user, String beanName) {
		AbstractService result = getServiceByBeanName(beanName);
		intiateService(result, user);
		return result;
	}

	private static void intiateService(AbstractService result, User user) {
		result.setUser(user);
		
	}

	private static AbstractService getServiceByBeanName(String beanName) {		
		return (AbstractService) SpringApplicationContext.getBean(beanName);
	}

	
	
	private static User encodeToken(String token) {		
		return tokenProcessor.decodeRoleToken(token);
	}

	public static TestResultsService getResultsService(String token, String beanName) throws AccessException {
				
		User user = encodeToken(token);
				
		if(user!=null && user.isAutorized()) {
			TestResultsService result = getResultServiceByBean(beanName);			
			intiateService(result, user);	
			
			return result;
		} else {
			throw new AccessException();
		}
	
	}

	private static TestResultsService getResultServiceByBean(String beanName) {	
		
		return (TestResultsService) SpringApplicationContext.getBean(beanName);
	}
	
}

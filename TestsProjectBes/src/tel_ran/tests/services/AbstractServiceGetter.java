package tel_ran.tests.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tel_ran.tests.services.AbstractService;
import tel_ran.tests.services.fields.Role;
import tel_ran.tests.services.utils.SpringApplicationContext;
import tel_ran.tests.token_cipher.TokenProcessor;
import tel_ran.tests.token_cipher.User;
import tel_ran.tests.utils.errors.AccessException;

@Component
public class AbstractServiceGetter {
	
	public static final String BEAN_QUESTIONS_SERVICE = "questionsService";
	public static final String BEAN_TEMPLATE_SERVICE = "templateService";
	public static final String BEAN_TEST_TEMPLATE_SERVICE = "testTemplateService";
	
	private static TokenProcessor tokenProcessor;
	
	static {
		tokenProcessor = (TokenProcessor) SpringApplicationContext.getBean("tokenProc");
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
	
}

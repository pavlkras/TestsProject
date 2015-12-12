

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

	@Autowired
	private static TokenProcessor tokenProcessor;
		
	public static AbstractService getService(String token, String beanName) throws AccessException {
//		User user = encodeToken(token);
		User user = new User(1L, Role.ADMINISTRATOR.ordinal());
		
		if(user!=null && user.isAutorized()) {
			AbstractService result = getServiceByBeanName(beanName);
			
			intiateService(result, user);
			
			return result;
		} else {
			throw new AccessException();
		}
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
